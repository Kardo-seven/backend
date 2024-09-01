package ru.kardo.dto.feed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFeedDto {
    private Long id;

    private Set<String> oldFilesLinks;

    private Set<MultipartFile> files;

    private String description;
}
