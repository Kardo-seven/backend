package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Profile;

import java.util.List;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long>, QuerydslPredicateExecutor<Profile> {

    List<Profile> findAllByIsChildFalseAndIsChildExpertFalse();

    List<Profile> findAllByIsChildTrueOrIsChildExpertTrue();
}
