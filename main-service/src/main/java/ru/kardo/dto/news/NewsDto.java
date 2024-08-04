package ru.kardo.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kardo.dto.newsBanner.NewsBannerDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
    private Long newsId;

    private String description;

    private LocalDateTime created;

    private String title;

    private LocalDateTime eventTime;

    private NewsBannerDto banner;
}
