package ru.kardo.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Feed;

import java.util.List;

@Repository
public interface FeedRepo extends JpaRepository<Feed, Long> {
    @Query("SELECT f " +
            "FROM Feed f " +
            "WHERE f.owner.id = ?1 ")
    List<Feed> getLatestFeed(Long id, Pageable page);
}
