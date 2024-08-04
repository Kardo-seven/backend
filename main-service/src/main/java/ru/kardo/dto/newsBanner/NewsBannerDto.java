package ru.kardo.dto.newsBanner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsBannerDto {
    private Long newsBannerId;

    private String link;

    private String title;

    private String type;
}
