package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.kardo.dto.user.UserDtoRequest;
import ru.kardo.dto.user.UserDtoResponse;
import ru.kardo.exception.ConflictException;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.model.User;
import ru.kardo.model.enums.EnumAuth;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "db.name=test", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("ci,test")
@Transactional
class UserServiceImplTest {

    private final UserService service;

    private static final UserDtoRequest participant = new UserDtoRequest();
    private static final UserDtoRequest expert = new UserDtoRequest();
    private UserDtoResponse response = new UserDtoResponse();


    @BeforeAll
    static void init() {
        participant.setEmail("participant@email.ru");
        participant.setPassword("participant");

        expert.setEmail("expert@email.ru");
        expert.setPassword("expert");
        expert.setEnumAuth(EnumAuth.EXPERT);
    }

    @Test
    void shouldAddUserSuccessfully() {
        //добавление нового участника
        response = service.addUser(participant);

        assertThat(response.getEmail(), equalTo(participant.getEmail()));
        assertThat(response.getAuthoritySet().size(), equalTo(1));
        assertThat(response.getAuthoritySet().stream().iterator().next().getAuthority(), equalTo(EnumAuth.PARTICIPANT));

        //добавление нового эксперта
        response = service.addUser(expert);

        assertThat(response.getEmail(), equalTo(expert.getEmail()));
        assertThat(response.getAuthoritySet().size(), equalTo(1));
        assertThat(response.getAuthoritySet().stream().iterator().next().getAuthority(), equalTo(EnumAuth.EXPERT));
    }

    @Test
    void shouldAddUserWithSameEmailFail() {
        response = service.addUser(participant);

        assertThat(response.getEmail(), equalTo(participant.getEmail()));
        assertThat(response.getAuthoritySet().size(), equalTo(1));
        assertThat(response.getAuthoritySet().stream().iterator().next().getAuthority(), equalTo(EnumAuth.PARTICIPANT));

        //попытка зарегистрировать пользователя с существующим email
        Exception e = assertThrows(ConflictException.class,
                () -> service.addUser(participant));
        assertThat(e.getMessage(), equalTo("Email: " + participant.getEmail() + ", already used"));
    }

    @Test
    void shouldFindUserByEmailSuccessfully() {
        //поиск пользователя по его email
        response = service.addUser(participant);
        User user = service.findUserByEmail(response.getEmail());

        assertThat(user.getEmail(), equalTo(participant.getEmail()));
    }

    @Test
    void shouldFindUserByEmailFail() {
        //попытка найти пользователя по несуществующему email
        String email = "newUnknownEmail@email.ru";
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> service.findUserByEmail(email));
        assertThat(e.getMessage(), equalTo("User with email " + email + " not found"));
    }
}