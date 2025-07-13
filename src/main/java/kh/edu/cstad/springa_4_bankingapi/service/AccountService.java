package kh.edu.cstad.springa_4_bankingapi.service;

import kh.edu.cstad.springa_4_bankingapi.dto.account.AccountResponse;
import kh.edu.cstad.springa_4_bankingapi.dto.account.CreateAccountRequest;
import kh.edu.cstad.springa_4_bankingapi.dto.account.UpdateAccountRequest;

import java.util.List;

public interface AccountService {

    List<AccountResponse> findAllAccounts();
    AccountResponse createNewAccount(CreateAccountRequest createAccountRequest);
    AccountResponse findAccountByAccountNumber(String accountNumber);
    List<AccountResponse> findAccountByCustomer(Integer customerId);
    AccountResponse updateAccountInfoByAccountNumber(String accountNumber, UpdateAccountRequest updateAccountRequest);
    void deleteAccountByAccountNumber(String accountNumber);
    void disableAccountByAccountNumber(String accountNumber);

}
