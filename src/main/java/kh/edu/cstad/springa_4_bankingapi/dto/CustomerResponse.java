package kh.edu.cstad.springa_4_bankingapi.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String fullName,
        String gender,
        String email,
        String remark
) {
}
