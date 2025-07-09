package kh.edu.cstad.springa_4_bankingapi;

import kh.edu.cstad.springa_4_bankingapi.domain.Customer;
import kh.edu.cstad.springa_4_bankingapi.domain.KYC;
import kh.edu.cstad.springa_4_bankingapi.domain.Transaction;
import kh.edu.cstad.springa_4_bankingapi.domain.TransactionType;
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

    @Override
    public void run(String... args) throws Exception {

        KYC kyc = new KYC();
        Customer customer = new Customer();

        kyc.setNationalCardId("000111222");
        kyc.setIsVerified(false);
        kyc.setIsDeleted(false);
        kyc.setCustomer(customer);

        customer.setFullName("leaphea lala");
        customer.setGender("F");
        customer.setEmail("lim@gmail.com");
        customer.setPhoneNumber("123456789");
        customer.setKyc(kyc);
        customer.setRemark("STUDENT");
        customer.setIsDeleted(false);

        customerRepository.save(customer);

        //
        Customer sender = customerRepository.save(customer);
        Customer receiver = customerRepository.save(customer);

        TransactionType transferType = new TransactionType();
        transferType.setName("TRANSFER");
        transferType.setIsDeleted(false);
        transactionTypeRepository.save(transferType);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(transferType);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(150.0);
        transaction.setRemark("Loan repayment");
        transaction.setIsDeleted(false);
        transactionRepository.save(transaction);


    }

    public static void main(String[] args) {
        SpringApplication.run(Springa4MobileBankingApiApplication.class, args);
    }
}
