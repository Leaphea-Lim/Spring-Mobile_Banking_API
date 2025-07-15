package kh.edu.cstad.springa_4_bankingapi.repository;

import kh.edu.cstad.springa_4_bankingapi.domain.Customer;
import kh.edu.cstad.springa_4_bankingapi.domain.KYC;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface KYCRepository extends CrudRepository<KYC, Integer> {

    Optional<KYC> findByCustomer(Customer customer);
}
