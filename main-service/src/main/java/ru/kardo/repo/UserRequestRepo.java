package ru.kardo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kardo.model.UserRequest;

import java.util.Optional;

@Repository
public interface UserRequestRepo extends JpaRepository<UserRequest, Long> {

    @Query(value = "SELECT u.id FROM UserRequest u WHERE u.event.id = :eventId")
    Long findUserRequestIdByEventId(@Param("eventId") Long eventId);

    UserRequest findUserRequestByEventId(Long eventId);

    Optional<UserRequest> findByProfileIdAndId(Long profileId, Long userRequestId);

    Optional<UserRequest> findUserRequestByProfileIdAndEventId(Long profileId, Long eventId);
}
