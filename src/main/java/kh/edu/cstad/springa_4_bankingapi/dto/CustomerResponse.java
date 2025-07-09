package kh.edu.cstad.springa_4_bankingapi.dto;

public record CustomerResponse(
        String fullName,
        String email,
        String gender
) {
}
