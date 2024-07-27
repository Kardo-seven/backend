package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Avatar;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvatarRepo extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findAvatarByProfileId(Long id);

    @Query(value = "SELECT a.profile.id FROM Avatar a")
    List<Long> findAllIds();
}
