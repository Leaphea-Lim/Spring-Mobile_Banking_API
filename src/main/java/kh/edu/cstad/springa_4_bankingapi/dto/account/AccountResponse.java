package kh.edu.cstad.springa_4_bankingapi.dto.account;

import lombok.Builder;

@Builder
public record AccountResponse(
        String fullName,
        String accountNumber,
        String accountCurrency,
        Double balance,
        Integer accountTypeId,
        String accountType

) {
}
