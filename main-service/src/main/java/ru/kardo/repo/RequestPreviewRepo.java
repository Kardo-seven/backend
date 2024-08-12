package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.RequestPreview;

import java.util.Optional;

@Repository
public interface RequestPreviewRepo extends JpaRepository<RequestPreview, Long> {

    Optional<RequestPreview> findByUserRequestId(Long userRequestId);
}
