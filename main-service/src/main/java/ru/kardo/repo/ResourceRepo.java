package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Resource;

@Repository
public interface ResourceRepo extends JpaRepository<Resource, Long> {
}
