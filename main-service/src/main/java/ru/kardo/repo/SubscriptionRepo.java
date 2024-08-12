package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Subscription;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {

    @Query(value = "SELECT s.subscriberId FROM Subscription s RIGHT JOIN Profile p ON  p.id = s.userId WHERE p.id = :id")
    List<Long> getAllProfileSubscribers(@Param("id") Long id);

    @Query(value = "SELECT s.userId FROM Subscription s RIGHT JOIN Profile p ON  p.id = s.subscriberId WHERE p.id = :id")
    List<Long> getAllProfileSubscriptions(@Param("id") Long id);

    Optional<Subscription> getSubscriberBySubscriberIdAndUserId(Long userId, Long subscriberId);
}
