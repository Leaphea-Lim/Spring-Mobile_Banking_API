package kh.edu.cstad.springa_4_bankingapi.service.Impl;

import kh.edu.cstad.springa_4_bankingapi.domain.Account;
import kh.edu.cstad.springa_4_bankingapi.domain.AccountType;
import kh.edu.cstad.springa_4_bankingapi.domain.Customer;
import kh.edu.cstad.springa_4_bankingapi.dto.account.AccountResponse;
import kh.edu.cstad.springa_4_bankingapi.dto.account.CreateAccountRequest;
import kh.edu.cstad.springa_4_bankingapi.dto.account.UpdateAccountRequest;
import kh.edu.cstad.springa_4_bankingapi.mapper.AccountMapper;
import kh.edu.cstad.springa_4_bankingapi.repository.AccountRepository;
import kh.edu.cstad.springa_4_bankingapi.repository.AccountTypeRepository;
import kh.edu.cstad.springa_4_bankingapi.repository.CustomerRepository;
import kh.edu.cstad.springa_4_bankingapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private final CustomerRepository customerRepository;
    private final AccountTypeRepository  accountTypeRepository;

    //todo Find All Account
    @Override
    public List<AccountResponse> findAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts
                .stream()
                .map(accountMapper::fromAccount)
                .toList();
    }

    //todo Create New Account
    @Override
    public AccountResponse createNewAccount(CreateAccountRequest createAccountRequest) {
        //find customer
        Customer customer = customerRepository.findById(createAccountRequest.customerId())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer Not Found"
                ));

        if(accountRepository.existsByAccountNumber(createAccountRequest.accountNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account Number already exists!");
        }

        if(!customerRepository.existsById(createAccountRequest.customerId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found!");
        }

        AccountType accountType = accountTypeRepository.findById(createAccountRequest.accountType())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account type not found"));


        Account account = accountMapper.toAccount(createAccountRequest);
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account.setIsDeleted(false);

        accountRepository.save(account);

        return accountMapper.fromAccount(account);
    }

    //todo Find Account By Account Number
    @Override
    public AccountResponse findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(accountMapper::fromAccount)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account Not Found"
                ));
    }

    //todo Find Account By Customer ID
    @Override
    public List<AccountResponse> findAccountByCustomer(Integer customerId) {
        return accountRepository.findByCustomerId(customerId)
                .stream()
                .map(accountMapper::fromAccount)
                .toList();
    }

    //todo Update Account Information By Account Number
    @Override
    public AccountResponse updateAccountInfoByAccountNumber(String accountNumber, UpdateAccountRequest updateAccountRequest) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account Not Found"
                ));
        /*
        if (updateAccountRequest.customerId() != null &&
                !updateAccountRequest.customerId().equals(account.getCustomer().getId())) {

            Customer newCustomer = customerRepository.findById(updateAccountRequest.customerId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Customer not found"
                    ));
            account.setCustomer(newCustomer);
        }
        if (updateAccountRequest.accountType() != null &&
                !updateAccountRequest.accountType().equals(account.getAccountType().getAccountTypeId())) {

            AccountType newAccountType = accountTypeRepository.findById(updateAccountRequest.accountType())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Account type not found"
                    ));
            account.setAccountType(newAccountType);
        }

         */

        accountMapper.toAccountPartially(updateAccountRequest, account);
        accountRepository.save(account);
        return accountMapper.fromAccount(account);
    }

    //todo Delete Account
    @Override
    public void deleteAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account Not Found"
                ));

        accountRepository.delete(account);
    }

    //todo Disable Account
    @Override
    public void disableAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account Not Found"
                ));

        account.setIsDeleted(true);
        accountRepository.save(account);
    }
}
