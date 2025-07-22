package kh.edu.cstad.springa_4_bankingapi.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateCustomerRequest(

        @NotBlank(message = "Full Name is required")
        String fullName,

        @NotBlank(message = "Full Name is required")
        String gender,

        @NotNull(message = "Date of birth is required")
        LocalDate dob,

        String email,
        String phoneNumber,
        String remark,

        @NotBlank(message = "National Card ID is required")
        String nationalCardId,

//        @NotBlank(message = "Segment is required")
//        String segmentType

        String customerSegment
) {
}
