package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Event;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

}
