package ru.kardo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "news_banner")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_banner_id")
    private Long newsBannerId;

    @Size(max = 1000)
    private String link;

    private String title;

    private String type;
}
