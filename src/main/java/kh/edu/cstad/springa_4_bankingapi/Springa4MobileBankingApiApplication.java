package kh.edu.cstad.springa_4_bankingapi;

import kh.edu.cstad.springa_4_bankingapi.domain.*;
import kh.edu.cstad.springa_4_bankingapi.repository.AccountTypeRepository;
import kh.edu.cstad.springa_4_bankingapi.repository.CustomerRepository;
import kh.edu.cstad.springa_4_bankingapi.repository.TransactionRepository;
import kh.edu.cstad.springa_4_bankingapi.repository.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ResponseStatus;

@SpringBootApplication
@RequiredArgsConstructor
public class Springa4MobileBankingApiApplication implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final AccountTypeRepository accountTypeRepository;


    @Override
    public void run(String... args) throws Exception {
        if (accountTypeRepository.count() == 0) {
            AccountType savings = new AccountType();
            savings.setType("Savings");

//            AccountType current = new AccountType();
//            current.setAccountType("Current");

            accountTypeRepository.save(savings);
//            accountTypeRepository.save(current);

            System.out.println("Default account types inserted: Savings");
        }else{
            System.out.println("'Savings' account type already exists.");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Springa4MobileBankingApiApplication.class, args);
    }
}
