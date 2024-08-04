package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.NewsBanner;

@Repository
public interface NewsBannerRepo extends JpaRepository<NewsBanner, Long> {
}
