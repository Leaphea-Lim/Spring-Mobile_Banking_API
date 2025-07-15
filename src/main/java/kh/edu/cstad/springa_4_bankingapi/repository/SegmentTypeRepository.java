package kh.edu.cstad.springa_4_bankingapi.repository;

import kh.edu.cstad.springa_4_bankingapi.domain.SegmentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SegmentTypeRepository extends JpaRepository<SegmentType, Integer> {
    Optional<SegmentType> findBySegmentTypeIgnoreCase(String segmentType);
}