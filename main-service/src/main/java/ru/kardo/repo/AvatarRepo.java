package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Avatar;

import java.util.Optional;

@Repository
public interface AvatarRepo extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findAvatarByProfileId(Long id);
}
