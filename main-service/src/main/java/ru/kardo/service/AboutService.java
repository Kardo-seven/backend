package ru.kardo.service;

import ru.kardo.dto.document.DocumentDto;
import ru.kardo.dto.news.NewsDto;
import ru.kardo.dto.resource.ResourceDto;

import java.util.List;

public interface AboutService {
    List<NewsDto> getNews();

    List<DocumentDto> getDocuments();

    List<ResourceDto> getResources();
}
