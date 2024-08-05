package ru.kardo.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import ru.kardo.model.Authority;
import ru.kardo.model.Profile;
import ru.kardo.model.enums.DirectionEnum;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Long> {

    Profile findProfileByUserId(Long id);

    @Query("SELECT p " +
            "FROM Profile p " +
            "JOIN p.seasons s " +
            "JOIN p.directions d " +
            "JOIN p.user.authoritySet a " +
            "WHERE ( (s IN (?1)) OR ((?1) is null) ) " +
            "AND ( (d IN (?2)) OR ((?2) is null) ) " +
            "AND ( (a IN (?3)) OR ((?3) is null) ) " +
            "AND ( (p.country IN (?4)) OR ((?4) is null) ) " +
            "AND (p.isChild=?5)" +
            "AND (p.isChildExpert=?6)" +
            "GROUP BY p.id " +
            "ORDER BY p.id")
    List<Profile> findStaff(Set<String> seasons, Set<DirectionEnum> directions, Set<Authority> authoritySet,
                            Set<String> countries, Boolean isChild, Boolean isChildExpert, Pageable page);


    List<Profile> findAllByIsChildTrueOrIsChildExpertTrue();
//    List<Profile> findBySeasonsInAndDirectionsInAndUserAuthoritySetInAndCountryInAndIsChildAndIsChildExpertOrderByIdDesc(
//            Set<String> seasons, Set<DirectionEnum> directions, Set<Authority> authoritySet, Set<String> countries,
//            Boolean isChild, Boolean isChildExpert, Pageable page);
}
