package kh.edu.cstad.springa_4_bankingapi.controller;

import jakarta.validation.Valid;
import kh.edu.cstad.springa_4_bankingapi.dto.account.AccountResponse;
import kh.edu.cstad.springa_4_bankingapi.dto.account.CreateAccountRequest;
import kh.edu.cstad.springa_4_bankingapi.dto.account.UpdateAccountRequest;
import kh.edu.cstad.springa_4_bankingapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    //todo Create a new account
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createNewAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest){
        return accountService.createNewAccount(createAccountRequest);
    }

    //todo Find all accounts
    @GetMapping
    public List<AccountResponse> findAllAccounts(){
        return accountService.findAllAccounts();
    }

    //todo Find an account by actNo
    @GetMapping("/{accountNumber}")
    public AccountResponse findAccountByAccountNumber(@PathVariable String accountNumber){
        return accountService.findAccountByAccountNumber(accountNumber);
    }

    //todo Find accounts by customer
    @GetMapping("/{customerID}")
    public List<AccountResponse> findAccountByCustomer(@PathVariable Integer customerId){
        return accountService.findAccountByCustomer(customerId);
    }

    //todo Update an account information by actNo
    @PatchMapping("/{accountNumber}")
    public AccountResponse updateAccountInfoByAccountNumber(@PathVariable String accountNumber,
                                                    @Valid @RequestBody UpdateAccountRequest updateAccountRequest){
        return accountService.updateAccountInfoByAccountNumber(accountNumber, updateAccountRequest);
    }

    //todo Delete an account by actNo
    @DeleteMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountByActNo(@PathVariable String accountNumber){
        accountService.deleteAccountByAccountNumber(accountNumber);
    }

    //todo Disable an account by actNo
    @PutMapping("/{accountNumber}/{disable}")
    public void disableAccountByAccountNumber(@PathVariable String accountNumber){
        accountService.disableAccountByAccountNumber(accountNumber);
    }
}
