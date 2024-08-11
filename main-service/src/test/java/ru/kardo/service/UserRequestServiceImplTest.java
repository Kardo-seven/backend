package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.event.EventDtoResponse;
import ru.kardo.dto.event.GrandFinalEventDtoResponse;
import ru.kardo.dto.profile.ProfileFullDtoResponse;
import ru.kardo.dto.profile.ProfileUpdateDtoRequest;
import ru.kardo.dto.request.RequestPreviewDtoResponse;
import ru.kardo.dto.request.UserRequestDtoRequest;
import ru.kardo.dto.request.UserRequestDtoResponse;
import ru.kardo.dto.user.UserDtoRequest;
import ru.kardo.dto.user.UserDtoResponse;
import ru.kardo.exception.ConflictException;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.model.enums.DirectionEnum;
import ru.kardo.model.enums.EnumAuth;
import ru.kardo.model.enums.Gender;
import util.TestUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.kardo.model.enums.DirectionEnum.BMX;
import static ru.kardo.model.enums.DirectionEnum.DJING;
import static ru.kardo.model.enums.DirectionEnum.HIP_HOP;
import static ru.kardo.model.enums.DirectionEnum.PARKOUR;
import static ru.kardo.model.enums.TypeOfSelection.NATIONAL_SELECTIONS;
import static ru.kardo.model.enums.TypeOfSelection.REGIONAL_SELECTIONS;

@SpringBootTest(properties = "db.name=test", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("ci,test")
@Transactional
class UserRequestServiceImplTest {

    private final UserRequestService requestService;
    private final EventService eventService;
    private final UserService userService;
    private final ProfileService profileService;


    UserRequestDtoRequest request = new UserRequestDtoRequest();
    UserRequestDtoResponse response = new UserRequestDtoResponse();

    RequestPreviewDtoResponse previewResponse = new RequestPreviewDtoResponse();

    private UserDtoRequest registerUser1 = new UserDtoRequest();
    private UserDtoResponse user1 = new UserDtoResponse();
    private ProfileUpdateDtoRequest profileToRegister1 = new ProfileUpdateDtoRequest();
    private ProfileFullDtoResponse profile1 = new ProfileFullDtoResponse();
    EventDtoResponse event;

    private Long unknownRequestId = 1000L;
    private Long unknownEventId = 1000L;
    private Long unknownProfileId = 1000L;

    @BeforeEach
    void start() {
        Long increment = 10L;
        registerUser1 = createUser(3 + increment, EnumAuth.PARTICIPANT);
        user1 = userService.addUser(registerUser1);

        profileToRegister1 = createProfile(1 + increment, Set.of(BMX), "Russia");
        profile1 = profileService.personalInformationUpdate(user1.getId(), profileToRegister1);

        event = eventService.getEvent(1L);

        request = createUserRequest(14L + increment, List.of(BMX));

        unknownEventId += increment;
        unknownRequestId += increment;
        unknownProfileId += increment;
    }

    @Test
    void shouldPostUserRequestSuccessfully() {
        //добавление нового запроса на event
        response = requestService.postUserRequest(profile1.getId(), event.getId(), request);

        assertThat(response.getTypeOfSelection(), equalTo(request.getTypeOfSelection()));
    }

    @Test
    void shouldGPostUserRequestFail() {
        //заявка от несуществующего профиля
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> requestService.postUserRequest(unknownProfileId, event.getId(), request));
        assertThat(e.getMessage(), equalTo("Profile with id: " + unknownProfileId + " not found"));

        //заявка на несуществующий event
        e = assertThrows(NotFoundValidationException.class,
                () -> requestService.postUserRequest(profile1.getId(), unknownEventId, request));
        assertThat(e.getMessage(), equalTo("Event with id: " + unknownEventId + " not found"));

        //повторная заявка на event
        response = requestService.postUserRequest(profile1.getId(), event.getId(), request);
        e = assertThrows(ConflictException.class,
                () -> requestService.postUserRequest(profile1.getId(), event.getId(), request));
        assertThat(e.getMessage(), equalTo("User already registered on event with id: " + event.getId()));

        //невалидная заявка с более чем 2 направлениями
        event = eventService.getEvent(5L);
        request.setDirectionEnumList(List.of(PARKOUR, HIP_HOP, DJING));
        e = assertThrows(ConflictException.class,
                () -> requestService.postUserRequest(profile1.getId(), event.getId(), request));
        assertThat(e.getMessage(), equalTo("Request cannot have more than two directions"));
    }

    @Test
    void shouldPostUserRequestToGrandFinalEventSuccessfully() {
        //заявка на гранд-финал
        response = requestService.postUserRequestToGrandFinalEvent(profile1.getId(), event.getId(), request);

        assertThat(response.getTypeOfSelection(), equalTo(request.getTypeOfSelection()));
    }

    @Test
    void shouldPostUserRequestToGrandFinalEventFail() {
        List<GrandFinalEventDtoResponse> list =
                eventService.getGrandFinalEvents(null, null, null, 0, 2);
        GrandFinalEventDtoResponse grandFinal = list.get(0);

        //заявка от несуществующего профиля
        GrandFinalEventDtoResponse finalGrandFinal = grandFinal;
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> requestService.postUserRequestToGrandFinalEvent(unknownProfileId, finalGrandFinal.getId(), request));
        assertThat(e.getMessage(), equalTo("Profile with id: " + unknownProfileId + " not found"));

        //заявка на несуществующий гранд-финал
        e = assertThrows(NotFoundValidationException.class,
                () -> requestService.postUserRequestToGrandFinalEvent(profile1.getId(), unknownEventId, request));
        assertThat(e.getMessage(), equalTo("Event with id: " + unknownEventId + " not found"));

        //повторная заявка на гранд-финал
        response = requestService.postUserRequestToGrandFinalEvent(profile1.getId(), grandFinal.getId(), request);
        e = assertThrows(ConflictException.class,
                () -> requestService.postUserRequestToGrandFinalEvent(profile1.getId(), finalGrandFinal.getId(), request));
        assertThat(e.getMessage(), equalTo("User already registered on event with id: " + grandFinal.getId()));

        //невалидная заявка с более чем 2 направлениями
        request.setDirectionEnumList(List.of(PARKOUR, HIP_HOP, DJING));
        grandFinal = list.get(1);
        GrandFinalEventDtoResponse finalGrandFinal1 = grandFinal;
        e = assertThrows(ConflictException.class,
                () -> requestService.postUserRequestToGrandFinalEvent(profile1.getId(), finalGrandFinal1.getId(), request));
        assertThat(e.getMessage(), equalTo("Request cannot have more than two directions"));
    }

    @Test
    void shouldUploadRequestPreviewSuccessfully() throws IOException {
        //загрузка preview
        MultipartFile entryFile = TestUtil.createMultipartFile("src/test/resources/avatar.jpg");
        previewResponse = requestService.uploadRequestPreview(profile1.getId(), event.getId(), entryFile);

        assertThat(previewResponse.getLink().contains(entryFile.getOriginalFilename()), equalTo(true));
    }

    @Test
    void shouldUploadRequestPreviewFail() throws IOException {
        MultipartFile entryFile = TestUtil.createMultipartFile("src/test/resources/avatar.jpg");

        //загрузка preview от неизвестного пользователя
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> requestService.uploadRequestPreview(unknownProfileId, event.getId(), entryFile));
        assertThat(e.getMessage(), equalTo("Profile with id: " + unknownProfileId + " not found"));

        //загрузка preview на неизвестный event
        e = assertThrows(NotFoundValidationException.class,
                () -> requestService.uploadRequestPreview(profile1.getId(), unknownEventId, entryFile));
        assertThat(e.getMessage(), equalTo("Event with id: " + unknownEventId + " not found"));


        response = requestService.postUserRequest(profile1.getId(), event.getId(), request);
        previewResponse = requestService.uploadRequestPreview(profile1.getId(), event.getId(), entryFile);

        //повторная загрузка preview на event
        e = assertThrows(ConflictException.class,
                () -> requestService.uploadRequestPreview(profile1.getId(), event.getId(), entryFile));
        assertThat(e.getMessage(), equalTo("User request already registered"));
    }

    @Test
    void shouldPatchUserRequestSuccessfully() {
        response = requestService.postUserRequest(profile1.getId(), event.getId(), request);

        //обновление запроса на event
        request.setEmail("some@email.ru");
        response = requestService.patchUserRequest(profile1.getId(), response.getId(), request);

        assertThat(response.getEmail(), equalTo(request.getEmail()));
    }

    @Test
    void shouldPatchUserRequestFail() {
        //обновление запроса на event от неизвестного пользователя
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> requestService.patchUserRequest(profile1.getId(), unknownRequestId, request));
        assertThat(e.getMessage(), equalTo("User request with id: " + unknownRequestId + " not found"));
    }

    @Test
    void shouldGetUserRequestsSuccessfully() {
        //получение списка запросов пользователя
        response = requestService.postUserRequest(profile1.getId(), event.getId(), request);
        event = eventService.getEvent(4L);
        response = requestService.postUserRequest(profile1.getId(), event.getId(), request);
        List<UserRequestDtoResponse> list = requestService.getUserRequests(profile1.getId());
        assertThat(list.size(), equalTo(2));
    }

    private static UserRequestDtoRequest createUserRequest(Long id, List<DirectionEnum> directions) {
        UserRequestDtoRequest request = new UserRequestDtoRequest();
        request.setDirectionEnumList(directions);
        request.setGender(id % 2 == 0 ? Gender.MAN : Gender.WOMAN);
        request.setTypeOfSelection(id % 2 == 0 ? REGIONAL_SELECTIONS : NATIONAL_SELECTIONS);
        return request;
    }

    private UserDtoRequest createUser(Long id, EnumAuth authority) {
        UserDtoRequest user = new UserDtoRequest();
        user.setEmail(id + "@email.ru");
        user.setPassword(id.toString());
        user.setEnumAuth(authority);
        return user;
    }

    private ProfileUpdateDtoRequest createProfile(Long id, Set<DirectionEnum> directions, String country) {
        ProfileUpdateDtoRequest profile = new ProfileUpdateDtoRequest();
        profile.setGender(id % 2 == 0 ? Gender.MAN : Gender.WOMAN);
        profile.setDirections(directions);
        profile.setCountry(country);
        return profile;
    }
}