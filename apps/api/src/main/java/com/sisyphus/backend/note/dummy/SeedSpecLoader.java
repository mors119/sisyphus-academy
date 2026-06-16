package com.sisyphus.backend.note.dummy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class SeedSpecLoader {

    private final ObjectMapper objectMapper; // role: JSON 파서, type: ObjectMapper

    public SeedNotesSpec load() {
        ClassPathResource res = new ClassPathResource("dummy-seed/notes.json");

        try (InputStream in = res.getInputStream()) {
            return objectMapper.readValue(in, SeedNotesSpec.class);
        } catch (Exception e) {
            throw new RuntimeException("dummy-seed/notes.json 로딩 실패", e);
        }
    }
}
