package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.kardo.dto.feed.CreateFeedDto;
import ru.kardo.dto.feed.FeedFullDto;
import ru.kardo.dto.feed.UpdateFeedDto;
import ru.kardo.dto.profile.ProfileFullDtoResponse;
import ru.kardo.dto.profile.ProfileUpdateDtoRequest;
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

@SpringBootTest(properties = "db.name=test", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("ci,test")
@Transactional
class FeedServiceImplTest {

    private final UserService userService;
    private final ProfileService profileService;
    private final FeedService feedService;

    private UserDtoResponse user1 = new UserDtoResponse();

    private ProfileFullDtoResponse profile1 = new ProfileFullDtoResponse();
    private ProfileFullDtoResponse profile2 = new ProfileFullDtoResponse();
    private ProfileFullDtoResponse profile3 = new ProfileFullDtoResponse();

    private final CreateFeedDto feed1Create = new CreateFeedDto();
    private final CreateFeedDto feed2Create = new CreateFeedDto();
    private final CreateFeedDto feed3Create = new CreateFeedDto();

    private final UpdateFeedDto feed1Update = new UpdateFeedDto();
    private final UpdateFeedDto feed2Update = new UpdateFeedDto();
    private final UpdateFeedDto feed3Update = new UpdateFeedDto();

    private FeedFullDto feed1 = new FeedFullDto();
    private FeedFullDto feed2 = new FeedFullDto();
    private FeedFullDto feed3 = new FeedFullDto();

    private final Long unknownProfileId = 1000L;
    private final Long unknownFeedId = 2000L;

    @BeforeEach
    void init() {
        UserDtoRequest userToRegister1 = createUserRequest(3, EnumAuth.PARTICIPANT);
        UserDtoRequest userToRegister2 = createUserRequest(5, EnumAuth.EXPERT);
        UserDtoRequest userToRegister3 = createUserRequest(8, EnumAuth.PARTICIPANT);

        user1 = userService.addUser(userToRegister1);
        UserDtoResponse user2 = userService.addUser(userToRegister2);
        UserDtoResponse user3 = userService.addUser(userToRegister3);

        ProfileUpdateDtoRequest profileToRegister1 = createProfileRequest(1, Set.of(DirectionEnum.BMX), "Russia");
        ProfileUpdateDtoRequest profileToRegister2 = createProfileRequest(2, Set.of(DirectionEnum.PARKOUR), "Russia");

        ProfileUpdateDtoRequest profileToRegister3 = createProfileRequest(3, Set.of(DirectionEnum.HIP_HOP), "Belarus");
        profileToRegister3.setIsChild(true);

        profile1 = profileService.personalInformationUpdate(user1.getId(), profileToRegister1);
        profile2 = profileService.personalInformationUpdate(user2.getId(), profileToRegister2);
        profile3 = profileService.personalInformationUpdate(user3.getId(), profileToRegister3);

        profileService.subscribe(profile2.getId(), profile1.getId());
        profileService.subscribe(profile3.getId(), profile1.getId());
        profileService.subscribe(profile2.getId(), profile3.getId());
    }

    @Test
    void addNewPost() throws IOException {
        feed1Create.setDescription("some desc");
        feed1Create.setMedia(Set.of(TestUtil.createMultipartFile("src/test/resources/template.pdf")));

        feed1 = feedService.addNewPost(profile1.getId(), feed1Create);
        List<FeedFullDto> resp = feedService.getFeed(profile2.getId(), 0, 5);

        assertThat(feed1.getId(), equalTo(resp.get(0).getId()));
        TestUtil.deleteAll(user1.getEmail(), feed1.getMedia().stream().findFirst().get().getLink());

        //создание поста с неизвестным пользователем
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> feedService.addNewPost(unknownProfileId, feed1Create));
        assertThat(e.getMessage(), equalTo("Profile for user with id " + unknownProfileId + " not found"));
    }

    @Test
    void updatePost() throws IOException {
        feed1Create.setDescription("some desc");
        feed1Create.setMedia(Set.of(TestUtil.createMultipartFile("src/test/resources/template.pdf")));
        feed1 = feedService.addNewPost(profile1.getId(), feed1Create);
        List<FeedFullDto> resp = feedService.getFeed(profile2.getId(), 0, 5);
        assertThat(feed1.getId(), equalTo(resp.get(0).getId()));

        feed1Update.setDescription("updated description");
        feed1 = feedService.updatePost(profile1.getId(), feed1.getId(), feed1Update);
        List<FeedFullDto> respUpdate = feedService.getFeed(profile2.getId(), 0, 5);
        assertThat(respUpdate.get(0).getDescription(), equalTo(feed1Update.getDescription()));

        TestUtil.deleteAll(user1.getEmail(), feed1.getMedia().stream().findFirst().get().getLink());

        //обновление поста неизвестным пользователем
        feed1Update.setDescription("update");
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> feedService.updatePost(unknownProfileId, feed1.getId(), feed1Update));
        assertThat(e.getMessage(), equalTo("Profile for user with id " + unknownProfileId + " not found"));

        //обновление поста неизвестным идентификатором
        e = assertThrows(NotFoundValidationException.class,
                () -> feedService.updatePost(profile1.getId(), unknownFeedId, feed1Update));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));

        //обновление поста не автором этого поста
        e = assertThrows(ConflictException.class,
                () -> feedService.updatePost(profile2.getId(), feed1.getId(), feed1Update));
        assertThat(e.getMessage(), equalTo("User with profile id " + profile2.getId() + "cannot update/delete this!"));
    }

    @Test
    void getFeed() {
        feed1Create.setDescription("some desc1");
        feed1 = feedService.addNewPost(profile1.getId(), feed1Create);
        List<FeedFullDto> resp1 = feedService.getFeed(profile2.getId(), 0, 5);

        assertThat(resp1.size(), equalTo(1));
        assertThat(feed1.getId(), equalTo(resp1.get(0).getId()));

        feed3Create.setDescription("some desc3");
        feed3 = feedService.addNewPost(profile3.getId(), feed3Create);
        List<FeedFullDto> resp2 = feedService.getFeed(profile2.getId(), 0, 5);
        assertThat(resp2.size(), equalTo(2));

        //получение ленты пользователем с неизвестным идентификатором
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> feedService.getFeed(unknownProfileId, 0, 5));
        assertThat(e.getMessage(), equalTo("Profile for user with id " + unknownProfileId + " not found"));
    }

    @Test
    void getById() {
        feed1Create.setDescription("some desc1");
        feed1 = feedService.addNewPost(profile1.getId(), feed1Create);

        feed3Create.setDescription("some desc3");
        feed3 = feedService.addNewPost(profile3.getId(), feed3Create);

        feed2 = feedService.getById(feed3.getId());
        assertThat(feed2.getId(), equalTo(feed3.getId()));

        feed2 = feedService.getById(feed1.getId());
        assertThat(feed2.getId(), equalTo(feed1.getId()));

        //получение поста с неизвестным идентификатором
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> feedService.getById(unknownFeedId));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));
    }

    @Test
    void deleteById() {
        feed1Create.setDescription("some desc1");
        feed1 = feedService.addNewPost(profile1.getId(), feed1Create);
        List<FeedFullDto> resp1 = feedService.getFeed(profile2.getId(), 0, 5);

        assertThat(resp1.size(), equalTo(1));
        assertThat(feed1.getId(), equalTo(resp1.get(0).getId()));

        feedService.deleteById(profile1.getId(), feed1.getId());

        //удаление поста с неизвестным идентификатором
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> feedService.getById(feed1.getId()));
        assertThat(e.getMessage(), equalTo("Feed with id " + feed1.getId() + " not found"));

        //попытка удалить пост не автором этого поста
        feed2Create.setDescription("desc");
        feed2 = feedService.addNewPost(profile1.getId(), feed2Create);

        e = assertThrows(ConflictException.class,
                () -> feedService.deleteById(profile2.getId(), feed2.getId()));
        assertThat(e.getMessage(), equalTo("User with profile id " + profile2.getId() + "cannot update/delete this!"));
    }

    @Test
    void addLike() {
        feed1Create.setDescription("some desc1");
        feed1 = feedService.addNewPost(profile1.getId(), feed1Create);

        feedService.addLike(profile2.getId(), feed1.getId());
        feedService.addLike(profile3.getId(), feed1.getId());
        feed2 = feedService.getById(feed1.getId());

        assertThat(feed2.getLikes().size(), equalTo(2));

        feedService.addLike(profile1.getId(), feed1.getId());
        feed2 = feedService.getById(feed1.getId());

        assertThat(feed2.getLikes().size(), equalTo(3));

        feedService.addLike(profile1.getId(), feed1.getId());
        feed2 = feedService.getById(feed1.getId());

        assertThat(feed2.getLikes().size(), equalTo(2));

        //попытка поставить лайк неизвестным пользователем
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> feedService.addLike(unknownProfileId, feed1.getId()));
        assertThat(e.getMessage(), equalTo("Profile for user with id " + unknownProfileId + " not found"));

        //попытка поставить лайк посту с неизвестным идентификатором
        e = assertThrows(NotFoundValidationException.class,
                () -> feedService.addLike(profile1.getId(), unknownFeedId));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));
    }

    private static UserDtoRequest createUserRequest(Integer id, EnumAuth authority) {
        UserDtoRequest user = new UserDtoRequest();
        user.setEmail(id + "@email.ru");
        user.setPassword(id.toString());
        user.setEnumAuth(authority);
        return user;
    }

    private static ProfileUpdateDtoRequest createProfileRequest(Integer id, Set<DirectionEnum> directions, String country) {
        ProfileUpdateDtoRequest profile = new ProfileUpdateDtoRequest();
        profile.setGender(id % 2 == 0 ? Gender.MAN : Gender.WOMAN);
        profile.setDirections(directions);
        profile.setCountry(country);
        return profile;
    }
}