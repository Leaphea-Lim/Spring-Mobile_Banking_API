package kh.edu.cstad.springa_4_bankingapi.dto.customer;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(

        @NotBlank(message = "Full Name is required")
        String fullName,

        @NotBlank(message = "Full Name is required")
        String gender,
        String email,
        String phoneNumber,
        String remark
) {
}


//function save --> search and update