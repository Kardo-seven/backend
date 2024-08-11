package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Feed;

@Repository
public interface FeedRepo extends JpaRepository<Feed, Long> {

}
