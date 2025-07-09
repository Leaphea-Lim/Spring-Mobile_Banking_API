package kh.edu.cstad.springa_4_bankingapi.repository;

import kh.edu.cstad.springa_4_bankingapi.domain.KYC;
import org.springframework.data.repository.CrudRepository;

public interface KycRepository extends CrudRepository<KYC, Integer> {
}
