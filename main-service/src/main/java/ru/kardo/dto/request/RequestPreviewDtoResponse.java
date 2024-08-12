package ru.kardo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPreviewDtoResponse {


    private Long id;

    private String title;

    private String type;

    private String link;

    private Long userRequestId;
}
