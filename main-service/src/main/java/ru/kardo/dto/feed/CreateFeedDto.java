package ru.kardo.dto.feed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFeedDto {
    Set<MultipartFile> media;

    @Size(min = 10)
    @NotBlank
    String description;
}
