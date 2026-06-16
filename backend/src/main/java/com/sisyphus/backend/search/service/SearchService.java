package com.sisyphus.backend.search.service;

import com.sisyphus.backend.category.repository.CategoryRepository;
import com.sisyphus.backend.note.repository.NoteRepository;
import com.sisyphus.backend.search.dto.SearchResponse;
import com.sisyphus.backend.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<SearchResponse> search(String keyword, Long userId, Pageable pageable) {

        String k = (keyword == null) ? "" : keyword.trim();
        if (k.isBlank()) return List.of();

        List<SearchResponse> tags = tagRepository
                .searchByKeyword(k, userId, pageable)
                .stream()
                .map(SearchResponse::from)
                .toList();

        List<SearchResponse> categories = categoryRepository
                .searchByKeyword(k, userId, pageable)
                .stream()
                .map(SearchResponse::from)
                .toList();

        List<SearchResponse> notes = noteRepository
                .searchByKeyword(k, userId, pageable)
                .stream()
                .map(SearchResponse::from)
                .toList();

        return Stream.of(tags, categories, notes)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(SearchResponse::getTitle, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}
