package ru.kardo.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kardo.model.UserRequest;

@Repository
public interface UserRequestRepo extends JpaRepository<UserRequest, Long> {

    @Query(value = "SELECT u.id FROM UserRequest u WHERE u.event.id = :eventId")
    Long findUserRequestIdByEventId(@Param("eventId") Long eventId);
}
