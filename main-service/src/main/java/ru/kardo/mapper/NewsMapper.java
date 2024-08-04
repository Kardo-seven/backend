package ru.kardo.mapper;

import org.mapstruct.Mapper;
import ru.kardo.dto.news.NewsDto;
import ru.kardo.model.News;

@Mapper(componentModel = "spring", uses = News.class)
public interface NewsMapper {
    NewsDto toNewsDto(News news);
}
