package kh.edu.cstad.springa_4_bankingapi.dto;

public record CreateCustomerRequest(
        String fullName,
        String gender,
        String email,
        String phoneNumber,
        String remark
) {
}


//function save --> search and update