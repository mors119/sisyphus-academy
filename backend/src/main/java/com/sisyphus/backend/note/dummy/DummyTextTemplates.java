package com.sisyphus.backend.note.dummy;

public final class DummyTextTemplates {

    private DummyTextTemplates() {}

    /**
     * 더미 노트 본문 템플릿 (포트폴리오용)
     *
     * @param index int (role: 노트 번호, type: int)
     * @return String (role: 마크다운/플레인 텍스트 본문, type: String)
     */
    public static String description(int index) {
        return """
                # Project Highlight %d
                
                This note demonstrates:
                - Clean API contract (Swagger/OpenAPI)
                - JWT-secured endpoints
                - Tag & Image relations
                - Storage abstraction (Local → Cloud)
                
                ## What I built
                - Notes CRUD with filtering & pagination
                - Image upload pipeline (compression on client, multipart upload on server)
                - Consistent domain-based package structure
                
                ## Why this matters
                I focused on maintainability:
                - Service logic separated from storage details
                - Reusable internal creation flow
                - Clear ownership checks per user
                
                ---
                Generated as dummy content for portfolio preview.
                """.formatted(index);
    }
}
