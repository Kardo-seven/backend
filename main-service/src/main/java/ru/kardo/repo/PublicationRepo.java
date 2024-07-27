package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Publication;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepo extends JpaRepository<Publication, Long> {

    List<Publication> findAllByProfileId(Long profileId);

    Optional<Publication> findByIdAndProfileId(Long publicationId, Long profileId);
}
