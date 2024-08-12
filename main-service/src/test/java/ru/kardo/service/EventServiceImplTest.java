package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.kardo.dto.event.EventDtoResponse;
import ru.kardo.dto.event.GrandFinalEventDtoResponse;
import ru.kardo.exception.NotFoundValidationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.kardo.model.enums.EventType.GRAND_FINAL;
import static ru.kardo.model.enums.EventType.STREET_CULTURE_AND_SPORTS_PROGRAM;

@SpringBootTest(properties = "db.name=test", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("ci,test")
@Transactional
class EventServiceImplTest {

    private final EventService service;

    @Test
    void shouldGetAllEventsSuccessfully() {
        //получение списка всех событий
        List<EventDtoResponse> list = service.getAllEvents();
        assertThat(list.size(), equalTo(6));
    }

    @Test
    void shouldGetEventSuccessfully() {
        //получение события по его идентификатору
        Long id = new Random().nextLong(1,7);
        EventDtoResponse event = service.getEvent(id);

        assertThat(event.getId(), equalTo(id));
    }

    @Test
    void shouldGetEventFail() {
        //получение события по неизвестному идентификатору
        Long eventId = 10000L;
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> service.getEvent(eventId));
        assertThat(e.getMessage(), equalTo("Event with id: " + eventId + " not found"));
    }

    @Test
    void shouldGetGrandFinalEventsSuccessfully() {
        //получение гранд-финала по заданным фильтрам
        LocalDate date = LocalDate.of(2024, 8, 23);
        List<GrandFinalEventDtoResponse> list =
                service.getGrandFinalEvents(date, STREET_CULTURE_AND_SPORTS_PROGRAM.name(), null, 0, 5);

        assertThat(list.size(), equalTo(1));
    }

    @Test
    void shouldGetGrandFinalEventsFail() {
        //гранд-финал не найден по заданным фильтрам
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> service.getGrandFinalEvents(LocalDate.of(2024, 8, 23),
                        GRAND_FINAL.name(), null, 0, 5));
        assertThat(e.getMessage(), equalTo("Nothing found"));
    }
}