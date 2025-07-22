package kh.edu.cstad.springa_4_bankingapi.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kh.edu.cstad.springa_4_bankingapi.domain.AccountType;
import kh.edu.cstad.springa_4_bankingapi.domain.Customer;
import kh.edu.cstad.springa_4_bankingapi.util.CurrencyUtil;

import java.math.BigDecimal;

public record CreateAccountRequest(

        String accountNumber,
        String accountName,
        CurrencyUtil accountCurrency,
        BigDecimal balance,
        String phoneNumber,
        Integer accountType


) {
}
