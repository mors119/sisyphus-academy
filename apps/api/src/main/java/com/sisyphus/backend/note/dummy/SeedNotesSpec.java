package com.sisyphus.backend.note.dummy;

import java.util.List;

public record SeedNotesSpec(
        List<SeedNoteItem> notes // role: 노트 스펙 리스트, type: List<SeedNoteItem>
) {
    public record SeedNoteItem(
            String image,          // role: dummy-images 파일명, type: String
            String title,          // role: 제목, type: String
            String subTitle,       // role: 부제(Nullable), type: String
            String description,    // role: 본문(Nullable), type: String
            List<String> tags,     // role: 태그 이름 목록(Nullable/Empty), type: List<String>
            String category        // role: 카테고리 이름(Nullable), type: String
    ) {}
}