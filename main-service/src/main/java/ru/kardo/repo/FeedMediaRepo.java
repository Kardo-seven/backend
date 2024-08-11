package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.FeedMedia;
@Repository
public interface FeedMediaRepo extends JpaRepository<FeedMedia, Long> {
}
