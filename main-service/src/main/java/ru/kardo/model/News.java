package ru.kardo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long newsId;

    private String description;

    private LocalDateTime created;

    private String title;

    @Column(name = "event_time")
    private LocalDateTime eventTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "news_banner_id", referencedColumnName = "news_banner_id")
    private NewsBanner banner;
}
