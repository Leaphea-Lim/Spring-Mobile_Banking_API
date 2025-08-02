package kh.edu.cstad.springa_4_bankingapi.repository;

import kh.edu.cstad.springa_4_bankingapi.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media,Integer> {

    Optional<Media> findByNameAndExtensionAndIsDeletedFalse(String name, String extension);

    List<Media> findAllByIsDeletedFalse();

    List<Media> findByNameAndIsDeletedFalse(String name);

    List<Media> findAllByExtensionAndIsDeletedFalse(String extension);
}
