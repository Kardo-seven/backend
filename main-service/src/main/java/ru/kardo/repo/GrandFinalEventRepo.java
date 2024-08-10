package ru.kardo.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.kardo.model.GrandFinalEvent;

@Repository
public interface GrandFinalEventRepo extends JpaRepository<GrandFinalEvent, Long>,
        QuerydslPredicateExecutor<GrandFinalEvent> {
}
