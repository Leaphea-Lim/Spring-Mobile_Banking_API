package kh.edu.cstad.springa_4_bankingapi.repository;

import kh.edu.cstad.springa_4_bankingapi.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {}

