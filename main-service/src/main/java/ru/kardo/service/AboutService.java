package ru.kardo.service;

import ru.kardo.dto.document.DocumentDto;
import ru.kardo.dto.news.NewsDto;
import ru.kardo.dto.resource.ResourceDto;

import java.util.List;

public interface AboutService {
    public List<NewsDto> getNews();

    public List<DocumentDto> getDocuments();

    public List<ResourceDto> getResources();
}
