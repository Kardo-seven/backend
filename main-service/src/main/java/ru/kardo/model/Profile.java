package ru.kardo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import ru.kardo.model.enums.DirectionEnum;
import ru.kardo.model.enums.Gender;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "avatar")
public class Profile {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "sur_name")
    private String surName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;

    @Column(name = "citizenship")
    private String citizenship;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id", unique = true)
    private Avatar avatar;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "social_network_links", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "link")
    @AttributeOverrides({
            @AttributeOverride(name = "link", column = @Column(name = "link"))
    })
    private Set<Link> linkSet;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private List<Publication> publicationList;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "profile_directions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "direction")
    @Enumerated(EnumType.STRING)
    private Set<DirectionEnum> directions;

    private String about;

    @Column(name = "is_child")
    private Boolean isChild;

    @Column(name = "is_child_expert")
    private Boolean isChildExpert;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "profile_seasons", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "season")
    private Set<String> seasons;
}