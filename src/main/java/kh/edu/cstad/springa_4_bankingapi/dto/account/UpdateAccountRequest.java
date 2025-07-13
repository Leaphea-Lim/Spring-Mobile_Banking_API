package kh.edu.cstad.springa_4_bankingapi.dto.account;

public record UpdateAccountRequest(

        String accountNumber,

        Integer accountTypeId,

        Double balance,

        String accountCurrency,

        Integer customerId

){
}
