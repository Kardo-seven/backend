package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Comment;

import java.util.Set;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    Set<Comment> findAllByFeedIdOrderByIdDesc(Long feedId);
}
