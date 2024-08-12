package ru.kardo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Avatar;

@Repository
public interface AvatarRepo extends JpaRepository<Avatar, Long> {

}
