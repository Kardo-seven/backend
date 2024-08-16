package ru.kardo.dto.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FeedMediaDto {
    private Long id;

    private String title;

    private String type;

    private String link;
}
