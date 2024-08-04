package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kardo.model.News;

import java.util.List;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {
    @Query("SELECT n " +
            "FROM News n " +
            "ORDER BY n.id DESC " +
            "LIMIT 100")
    List<News> getNews();
}
