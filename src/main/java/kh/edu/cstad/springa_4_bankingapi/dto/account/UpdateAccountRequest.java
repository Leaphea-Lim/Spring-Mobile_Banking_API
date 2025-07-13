package kh.edu.cstad.springa_4_bankingapi.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAccountRequest(

        @NotBlank(message = "Currency is required")
        String accountCurrency,

        @NotNull(message = "Balance is required")
        Double balance,

        @NotNull(message = "Customer ID is required")
        Integer customerId,

        @NotNull(message = "Account Type ID is required")
        Integer accountType
){
}
