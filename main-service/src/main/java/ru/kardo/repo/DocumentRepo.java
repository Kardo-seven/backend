package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Document;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {
}
