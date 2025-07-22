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
import kh.edu.cstad.springa_4_bankingapi.util.CurrencyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;


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
        Customer customer = customerRepository
                .findByPhoneNumber(createAccountRequest.phoneNumber())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Customer Phone Number Not Found"
                ));

        AccountType accountType = accountTypeRepository
                .findByType(String.valueOf(createAccountRequest.accountType()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Account type not found"));

        Account account = accountMapper.toAccount(createAccountRequest);
        account.setAccountType(accountType);
        account.setCustomer(customer);

//        String segment = customer.getSegmentType().getSegmentType();
//
//        int overLimit = switch (segment.toLowerCase()){
//            case "gold" -> 50000;
//            case "silver" -> 10000;
//            default -> 5000;
//        };

        if (account.getAccountNumber().isBlank()) { // Auto generate
            String actNo;
            do {
                actNo = String.format("%09d", new Random().nextInt(1_000_000_000)); // Max: 999,999,999
            } while (accountRepository.existsByAccountNumber(actNo));
            account.setAccountNumber(actNo);
        } else { // From DTO, check validation actNo
            if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Account number already exists");
            }
        }

        account.setIsHide(false);
        account.setIsDeleted(false);
        account.setAccountCurrency(String.valueOf(createAccountRequest.accountCurrency()));
//        account.setOverLimit(overLimit);

        if (account.getCustomer().getCustomerSegment().getSegment().equals("REGULAR")) {
            account.setOverLimit(BigDecimal.valueOf(5000));
        } else if (account.getCustomer().getCustomerSegment().getSegment().equals("SILVER")) {
            account.setOverLimit(BigDecimal.valueOf(50000));
        } else {
            account.setOverLimit(BigDecimal.valueOf(100000));
        }

        // Validate balance
        switch (createAccountRequest.accountCurrency()) {
            case CurrencyUtil.DOLLAR -> {
                if (createAccountRequest.balance().compareTo(BigDecimal.TEN) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 10 dollars");
                }
            }
            case CurrencyUtil.RIEL -> {
                if (createAccountRequest.balance().compareTo(BigDecimal.valueOf(40000)) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 40000 riels");
                }
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Currency is not available");
        }

//        if(accountRepository.existsByAccountNumber(createAccountRequest.accountNumber())){
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account Number already exists!");
//        }
//
//        if(!customerRepository.existsById(createAccountRequest.customerId())){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found!");
//        }

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
