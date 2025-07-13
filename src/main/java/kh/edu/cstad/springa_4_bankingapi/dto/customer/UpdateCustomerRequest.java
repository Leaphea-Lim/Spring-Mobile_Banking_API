package kh.edu.cstad.springa_4_bankingapi.dto.customer;

public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
) {
}
