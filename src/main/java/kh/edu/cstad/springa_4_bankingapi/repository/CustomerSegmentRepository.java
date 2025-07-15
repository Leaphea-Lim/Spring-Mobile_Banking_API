package kh.edu.cstad.springa_4_bankingapi.repository;

import kh.edu.cstad.springa_4_bankingapi.domain.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSegmentRepository extends JpaRepository<CustomerSegment, Integer> {

}