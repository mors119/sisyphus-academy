package com.sisyphus.backend.note.dummy;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class DummyImageLoader {

    private static final String BASE_DIR = "dummy-images";

    public ClassPathResource open(String filename) {
        return new ClassPathResource(BASE_DIR + "/" + filename);
    }
}