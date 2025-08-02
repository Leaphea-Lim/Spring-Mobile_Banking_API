package kh.edu.cstad.springa_4_bankingapi.dto;

import lombok.Builder;

@Builder
public record MediaResponse(
        String name,
        String mimeTypeFile,
        String uri,
        Long size
) {
}
