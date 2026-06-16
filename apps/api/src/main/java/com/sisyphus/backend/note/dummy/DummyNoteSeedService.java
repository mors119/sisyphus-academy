package com.sisyphus.backend.note.dummy;

import com.sisyphus.backend.category.entity.Category;
import com.sisyphus.backend.category.repository.CategoryRepository;
import com.sisyphus.backend.image.entity.NoteImage;
import com.sisyphus.backend.image.repository.NoteImageRepository;
import com.sisyphus.backend.image.service.storage.FileStorageService;
import com.sisyphus.backend.note.entity.Note;
import com.sisyphus.backend.note.repository.NoteRepository;
import com.sisyphus.backend.tag.entity.Tag;
import com.sisyphus.backend.tag.repository.TagRepository;
import com.sisyphus.backend.user.entity.User;
import com.sisyphus.backend.user.exception.UserNotFoundException;
import com.sisyphus.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DummyNoteSeedService {

    private final SeedSpecLoader seedSpecLoader;           // role: JSON 로더, type: SeedSpecLoader
    private final DummyImageLoader dummyImageLoader;       // role: classpath 이미지 로더, type: DummyImageLoader

    private final FileStorageService fileStorageService;   // role: 스토리지 추상화, type: FileStorageService

    private final UserRepository userRepository;           // role: 유저 조회, type: UserRepository
    private final CategoryRepository categoryRepository;   // role: 카테고리 조회/생성, type: CategoryRepository
    private final TagRepository tagRepository;             // role: 태그 조회/생성, type: TagRepository

    private final NoteRepository noteRepository;           // role: 노트 저장, type: NoteRepository
    private final NoteImageRepository noteImageRepository; // role: 노트 이미지 저장, type: NoteImageRepository

    /**
     * JSON 기반 더미 노트/이미지/태그/카테고리 시딩
     *
     * @param userId Long (role: 더미를 생성할 대상 유저 id, type: Long)
     * @return int (role: 생성된 노트 개수, type: int)
     */
    @Transactional
    public int seed(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        SeedNotesSpec spec = seedSpecLoader.load();
        if (spec.notes() == null || spec.notes().isEmpty()) return 0;

        // (선택) 중복 방지: 더미 prefix로 스킵
        if (noteRepository.existsByUserIdAndTitleStartingWith(userId, "[DUMMY]")) {
            return 0;
        }

        // 1) 카테고리/태그를 먼저 “이름 기준”으로 준비(대량 최적화)
        Map<String, Category> categoryMap = preloadCategories(user, spec);
        Map<String, Tag> tagMap = preloadTags(user, spec);

        int created = 0;

        for (int i = 0; i < spec.notes().size(); i++) {
            SeedNotesSpec.SeedNoteItem item = spec.notes().get(i);

            Category category = resolveCategory(item.category(), categoryMap);
            List<Tag> tags = resolveTags(item.tags(), tagMap);

            // 2) 이미지 업로드 → NoteImage 저장
            NoteImage noteImage = null;
            if (item.image() != null && !item.image().isBlank()) {
                noteImage = uploadDummyImageAsNoteImage(item.image());
            }

            // 3) 노트 생성
            Note note = Note.of(
                    safe(item.title(), "Portfolio Note " + (i + 1)),
                    toNullable(item.subTitle()),
                    toNullable(item.description()),
                    category,
                    user
            );

            // 4) 연결
            tags.forEach(note::addTag);
            if (noteImage != null) note.addImage(noteImage);

            noteRepository.save(note);
            created++;
        }

        return created;
    }

    private Map<String, Category> preloadCategories(User user, SeedNotesSpec spec) {
        Long userId = user.getId();

        // 1) JSON에서 category 이름을 수집(정규화)
        Set<String> titles = spec.notes().stream()
                .map(SeedNotesSpec.SeedNoteItem::category)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toSet());

        if (titles.isEmpty()) return new HashMap<>();

        // 2) 기존 카테고리 일괄 조회
        List<Category> existing = categoryRepository.findAllByUserIdAndTitleIn(userId, titles);
        Map<String, Category> map = existing.stream()
                .collect(Collectors.toMap(Category::getTitle, c -> c));

        // 3) 없는 것만 생성 → saveAll
        List<Category> toCreate = new ArrayList<>();
        for (String title : titles) {
            if (!map.containsKey(title)) {
                toCreate.add(Category.of(user, title)); // role: 단일 생성 팩토리, type: Category
            }
        }

        if (!toCreate.isEmpty()) {
            List<Category> saved = categoryRepository.saveAll(toCreate);
            for (Category c : saved) {
                map.put(c.getTitle(), c);
            }
        }

        return map;
    }


    private Map<String, Tag> preloadTags(User user, SeedNotesSpec spec) {
        Long userId = user.getId();

        // 1) JSON에서 태그 이름 수집 + 정규화(소문자)
        Set<String> names = spec.notes().stream()
                .flatMap(item -> item.tags() == null ? Stream.<String>empty() : item.tags().stream())
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        if (names.isEmpty()) return new HashMap<>();

        // 2) 기존 태그 일괄 조회
        List<Tag> existing = tagRepository.findAllByUserIdAndNameIn(userId, names);
        Map<String, Tag> map = existing.stream()
                .collect(Collectors.toMap(Tag::getName, t -> t));

        // 3) 없는 것만 생성 → saveAll
        List<Tag> toCreate = new ArrayList<>();
        for (String name : names) {
            if (!map.containsKey(name)) {
                toCreate.add(Tag.of(name, user)); // role: 단일 생성 팩토리, type: Tag
            }
        }

        if (!toCreate.isEmpty()) {
            List<Tag> saved = tagRepository.saveAll(toCreate);
            for (Tag t : saved) {
                map.put(t.getName(), t);
            }
        }

        return map;
    }


    private Category resolveCategory(String name, Map<String, Category> map) {
        if (name == null || name.isBlank()) return null;
        return map.get(name.trim());
    }

    private List<Tag> resolveTags(List<String> names, Map<String, Tag> map) {
        if (names == null || names.isEmpty()) return List.of();

        List<Tag> result = new ArrayList<>();
        for (String n : names) {
            if (n == null || n.isBlank()) continue;
            Tag t = map.get(n.trim());
            if (t != null) result.add(t);
        }
        return result;
    }

    /**
     * 더미 이미지(classpath)를 업로드하고 NoteImage로 저장한다.
     *
     * @param filename String (role: dummy-images 파일명, type: String)
     * @return NoteImage (role: 저장된 NoteImage, type: NoteImage)
     */
    private NoteImage uploadDummyImageAsNoteImage(String filename) {
        ClassPathResource res = dummyImageLoader.open(filename);

        try (InputStream in = res.getInputStream()) {
            long size = res.contentLength();
            String contentType = guessContentType(filename);

            String publicUrl = fileStorageService.save(in, size, contentType, filename);

            NoteImage img = new NoteImage(
                    publicUrl,
                    filename,
                    extFromFilename(filename),
                    size
            );

            return noteImageRepository.save(img);

        } catch (Exception e) {
            throw new RuntimeException("더미 이미지 업로드 실패: " + filename, e);
        }
    }

    private static String guessContentType(String filename) {
        String ext = extFromFilename(filename);
        return switch (ext) {
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "webp" -> "image/webp";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        };
    }

    private static String extFromFilename(String filename) {
        int idx = filename.lastIndexOf('.');
        if (idx < 0 || idx == filename.length() - 1) return "";
        return filename.substring(idx + 1).toLowerCase();
    }

    private static String safe(String value, String fallback) {
        return (value == null || value.isBlank()) ? fallback : value;
    }

    private static String toNullable(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }
}
