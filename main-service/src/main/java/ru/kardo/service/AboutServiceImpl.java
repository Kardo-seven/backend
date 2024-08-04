package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kardo.dto.document.DocumentDto;
import ru.kardo.dto.news.NewsDto;
import ru.kardo.dto.resource.ResourceDto;
import ru.kardo.mapper.DocumentMapper;
import ru.kardo.mapper.NewsMapper;
import ru.kardo.mapper.ResourceMapper;
import ru.kardo.model.Document;
import ru.kardo.model.News;
import ru.kardo.model.Resource;
import ru.kardo.repo.DocumentRepo;
import ru.kardo.repo.NewsRepo;
import ru.kardo.repo.ResourceRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AboutServiceImpl implements AboutService {

    private final NewsRepo newsRepo;
    private final DocumentRepo documentRepo;
    private final ResourceRepo resourceRepo;

    private final NewsMapper newsMapper;
    private final DocumentMapper docMapper;
    private final ResourceMapper resourceMapper;

    @Override
    public List<NewsDto> getNews() {
        List<News> news = newsRepo.getNews();
        return news.stream()
                .map(newsMapper::toNewsDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<DocumentDto> getDocuments() {
        List<Document> documents = documentRepo.findAll();
        return documents.stream()
                .map(docMapper::toDocumentDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<ResourceDto> getResources() {
        List<Resource> resources = resourceRepo.findAll();
        return resources.stream()
                .map(resourceMapper::toResourceDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
