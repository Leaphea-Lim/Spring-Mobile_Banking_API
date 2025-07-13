package kh.edu.cstad.springa_4_bankingapi.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAccountRequest(

        String accountNumber,

        Integer accountTypeId,

        Double balance,

        String accountCurrency,

        Integer customerId

){
}
