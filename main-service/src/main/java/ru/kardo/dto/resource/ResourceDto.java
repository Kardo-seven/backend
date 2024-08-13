package ru.kardo.dto.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResourceDto {

    private Long resourceId;

    private String title;

    private String type;

    private String link;
}
