package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.kardo.dto.document.DocumentDto;
import ru.kardo.dto.news.NewsDto;
import ru.kardo.dto.resource.ResourceDto;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(properties = "db.name=test", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("ci,test")
@Transactional
class AboutServiceImplTest {

    private final AboutService service;

    @Test
    void shouldGetNewsSuccessfully() {
        //получение новостей
        List<NewsDto> list = service.getNews();
        assertThat(list.size(), equalTo(3));
    }

    @Test
    void shouldGetDocumentsSuccessfully() {
        //получение документов
        List<DocumentDto> list = service.getDocuments();
        assertThat(list.size(), equalTo(6));
    }

    @Test
    void shouldGetResourcesSuccessfully() {
        //получение ресурсов
        List<ResourceDto> list = service.getResources();
        assertThat(list.size(), equalTo(4));
    }
}