package ru.kardo.controller.about;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kardo.dto.document.DocumentDto;
import ru.kardo.dto.news.NewsDto;
import ru.kardo.dto.resource.ResourceDto;
import ru.kardo.service.AboutService;

import java.util.List;

@RestController
@RequestMapping("/about")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class AboutUsController {

    private final AboutService service;

    @GetMapping("/news")
    public List<NewsDto> getNews() {
        return service.getNews();
    }

    @GetMapping("/documents")
    public List<DocumentDto> getDocuments() {
        return service.getDocuments();
    }

    @GetMapping("/branding")
    public List<ResourceDto> getResources() {
        return service.getResources();
    }

}
