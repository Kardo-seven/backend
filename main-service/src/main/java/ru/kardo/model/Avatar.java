package ru.kardo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "avatars")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "link")
    private String link;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", unique = true)
//    private Profile profile;
}
