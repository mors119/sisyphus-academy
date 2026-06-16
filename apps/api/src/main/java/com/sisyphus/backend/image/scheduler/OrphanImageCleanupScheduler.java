package com.sisyphus.backend.image.scheduler;

import com.sisyphus.backend.global.props.AppProps;
import com.sisyphus.backend.global.props.FileProps;
import com.sisyphus.backend.image.repository.ImageRepository;
import com.sisyphus.backend.image.util.PublicUrlPathResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrphanImageCleanupScheduler {

    private final ImageRepository imageRepository;
    private final FileProps fileProps;
    private final AppProps appProps;

    private static final Duration SKIP_RECENT = Duration.ofHours(2);

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional(readOnly = true)
    public void cleanupOrphanFiles() {
        Path root = Path.of(fileProps.uploadDir()).toAbsolutePath().normalize();

        if (!Files.exists(root) || !Files.isDirectory(root)) {
            log.warn("Upload dir not found or not a directory: {}", root);
            return;
        }

        List<String> urls = imageRepository.findAllUrls();

        String publicBasePath = PublicUrlPathResolver.normalizePublicBasePath(appProps.image().publicBase());

        Set<String> referencedRelativePaths = new HashSet<>(Math.max(urls.size() * 2, 16));
        for (String url : urls) {
            String rel = PublicUrlPathResolver.extractRelativePathFromPublicUrl(url, publicBasePath);
            if (rel != null && !rel.isBlank()) referencedRelativePaths.add(rel);
        }

        Instant now = Instant.now();
        int scannedCount = 0;
        int deletedCount = 0;
        int skippedRecentCount = 0;

        try (var stream = Files.walk(root)) {
            for (Path p : stream.toList()) {
                if (!Files.isRegularFile(p)) continue;

                scannedCount++;

                String relativePath = root.relativize(p).toString().replace("\\", "/");
                if (referencedRelativePaths.contains(relativePath)) continue;

                if (isRecent(p, now, SKIP_RECENT)) {
                    skippedRecentCount++;
                    continue;
                }

                try {
                    Files.deleteIfExists(p);
                    deletedCount++;
                    log.info("Deleted orphan file: {}", p);
                } catch (Exception e) {
                    log.error("Failed to delete orphan file: {}", p, e);
                }
            }
        } catch (Exception e) {
            log.error("Failed to scan upload directory: {}", root, e);
            return;
        }

        log.info(
                "Orphan cleanup finished. scanned={}, deleted={}, skippedRecent={}, referenced={}",
                scannedCount, deletedCount, skippedRecentCount, referencedRelativePaths.size()
        );
    }

    private static boolean isRecent(Path p, Instant now, Duration window) {
        try {
            FileTime lastModified = Files.getLastModifiedTime(p);
            Instant modifiedAt = lastModified.toInstant();
            return modifiedAt.isAfter(now.minus(window));
        } catch (Exception e) {
            return true;
        }
    }
}
