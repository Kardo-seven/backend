package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
}
