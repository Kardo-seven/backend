package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Subscriber;

import java.util.List;

@Repository
public interface SubscriberRepo extends JpaRepository<Subscriber, Long> {

    @Query(value = "SELECT s.subscriberId FROM Subscriber s RIGHT JOIN Profile p ON  p.id = s.userId WHERE p.id = :id")
    List<Long> getAllProfileSubscribers(@Param("id") Long id);
}
