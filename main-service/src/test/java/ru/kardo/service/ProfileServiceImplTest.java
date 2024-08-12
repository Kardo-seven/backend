package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.kardo.dto.profile.AvatarDtoResponse;
import ru.kardo.dto.profile.ProfileAboutDto;
import ru.kardo.dto.profile.ProfileFullDtoResponse;
import ru.kardo.dto.profile.ProfilePreviewDtoResponse;
import ru.kardo.dto.profile.ProfileShortDtoResponse;
import ru.kardo.dto.profile.ProfileUpdateDtoRequest;
import ru.kardo.dto.profile.PublicationDtoResponse;
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
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "db.name=test", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("ci,test")
@Transactional
class ProfileServiceImplTest {

    private final UserService userService;
    private final ProfileService profileService;

    private static Integer newId = 31;
    Long unknownUserId = 100000000L, increment = 11L;
    MultipartFile entryFile = null;

    private ProfileUpdateDtoRequest profileToRegister1 = new ProfileUpdateDtoRequest();
    private ProfileUpdateDtoRequest profileToRegister2 = new ProfileUpdateDtoRequest();
    private ProfileUpdateDtoRequest profileToRegister3 = new ProfileUpdateDtoRequest();
    private ProfileUpdateDtoRequest profileToRegister4 = new ProfileUpdateDtoRequest();

    private UserDtoResponse user1 = new UserDtoResponse();
    private UserDtoResponse user2 = new UserDtoResponse();
    private UserDtoResponse user3 = new UserDtoResponse();
    private UserDtoResponse user4 = new UserDtoResponse();

    private ProfileFullDtoResponse profile1 = new ProfileFullDtoResponse();
    private ProfileFullDtoResponse profile2 = new ProfileFullDtoResponse();
    private ProfileFullDtoResponse profile3 = new ProfileFullDtoResponse();
    private ProfileFullDtoResponse profile4 = new ProfileFullDtoResponse();


    @BeforeEach
    void start() {
        UserDtoRequest userToRegister1 = createUserRequest(3 * newId, EnumAuth.PARTICIPANT);
        UserDtoRequest userToRegister2 = createUserRequest(5 * newId, EnumAuth.PARTICIPANT);
        UserDtoRequest userToRegister3 = createUserRequest(8 * newId, EnumAuth.PARTICIPANT);
        UserDtoRequest userToRegister4 = createUserRequest(13 * newId, EnumAuth.EXPERT);

        user1 = userService.addUser(userToRegister1);
        user2 = userService.addUser(userToRegister2);
        user3 = userService.addUser(userToRegister3);
        user4 = userService.addUser(userToRegister4);

        profileToRegister1 = createProfileRequest(1, Set.of(DirectionEnum.BMX), "Russia");
        profileToRegister2 = createProfileRequest(2, Set.of(DirectionEnum.PARKOUR), "Russia");

        profileToRegister3 = createProfileRequest(3, Set.of(DirectionEnum.HIP_HOP), "Russia");
        profileToRegister3.setIsChild(true);

        profileToRegister4 = createProfileRequest(4, Set.of(DirectionEnum.HIP_HOP), "Belarus");
        profileToRegister4.setIsChildExpert(true);

        profile1 = profileService.personalInformationUpdate(user1.getId(), profileToRegister1);
        profile2 = profileService.personalInformationUpdate(user2.getId(), profileToRegister2);
        profile3 = profileService.personalInformationUpdate(user3.getId(), profileToRegister3);
        profile4 = profileService.personalInformationUpdate(user4.getId(), profileToRegister4);

        unknownUserId += increment;
        newId++;
    }

    @Test
    void shouldUpdateProfileSuccessfully() {
        //добавление профилей
        assertThat(profile1.getUserId(), equalTo(user1.getId()));

        profileToRegister1.setName("Sergey");
        profileToRegister1.setCountry("Belarus");

        profile1 = profileService.personalInformationUpdate(user1.getId(), profileToRegister1);
        assertThat(profile1.getCountry(), equalTo(profileToRegister1.getCountry()));
    }

    @Test
    void shouldUpdateProfileFail() {
        //Обновление профиля от неизвестного пользователя
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> profileService.personalInformationUpdate(unknownUserId, profileToRegister1));
        assertThat(e.getMessage(), equalTo("Profile for user with id " + unknownUserId + " not found"));
    }

    @Test
    void shouldUploadAvatarSuccessfully() throws IOException {
        //загрузка аватара
        entryFile = TestUtil.createMultipartFile("src/test/resources/avatar.jpg");
        AvatarDtoResponse avatar =
                profileService.uploadAvatar(user1.getId(), entryFile);
        assertThat(avatar.getTitle(), endsWith(entryFile.getOriginalFilename()));

        TestUtil.deleteAll(user1.getEmail(), avatar.getLink());
    }

    @Test
    void shouldUploadAvatarUserNotFoundFail() throws IOException {
        //загрузка аватара от неизвестного пользователя
        entryFile = TestUtil.createMultipartFile("src/test/resources/avatar.jpg");

        Exception e = assertThrows(NotFoundValidationException.class,
                () -> profileService.uploadAvatar(unknownUserId, entryFile));
        assertThat(e.getMessage(), equalTo("Profile with id: " + unknownUserId + " not found"));
    }

    @Test
    void shouldUploadAvatarUserAlreadyHasAvatarFoundFail() throws IOException {
        entryFile = TestUtil.createMultipartFile("src/test/resources/avatar.jpg");
        AvatarDtoResponse avatar = profileService.uploadAvatar(user1.getId(), entryFile);

        //повторная загрузка аватара
        Exception e = assertThrows(ConflictException.class,
                () -> profileService.uploadAvatar(user1.getId(), entryFile));
        assertThat(e.getMessage(), equalTo("User already have avatar"));
        TestUtil.deleteAll(user1.getEmail(), avatar.getLink());
    }

    @Test
    void shouldUploadPublicationSuccessfully() throws IOException {
        //загрузка публикации
        entryFile = TestUtil.createMultipartFile("src/test/resources/template.pdf");
        PublicationDtoResponse publication =
                profileService.uploadPublication(user1.getId(), entryFile, "description");

        assertThat(publication.getProfileId(), equalTo(profile1.getId()));

        TestUtil.deleteAll(user1.getEmail(), publication.getLink());
    }

    @Test
    void shouldUploadPublicationFail() throws IOException {
        //загрузка публикации неизвестного пользователя
        entryFile = TestUtil.createMultipartFile("src/test/resources/template.pdf");

        Exception e = assertThrows(NotFoundValidationException.class,
                () -> profileService.uploadPublication(unknownUserId, entryFile, "description"));
        assertThat(e.getMessage(), equalTo("Profile with id: " + unknownUserId + " not found"));
    }

    @Test
    void shouldGetAvatarSuccessfully() throws IOException {
        entryFile = TestUtil.createMultipartFile("src/test/resources/avatar.jpg");
        AvatarDtoResponse avatar =
                profileService.uploadAvatar(user1.getId(), entryFile);

        //Получение(выгрузка) аватара пользователя
        AvatarDtoResponse avatar1 = profileService.getAvatar(avatar.getId());

        assertThat(avatar1.getTitle(), equalTo(avatar.getTitle()));
        assertThat(avatar1.getLink(), equalTo(avatar.getLink()));

        TestUtil.deleteAll(user1.getEmail(), avatar1.getLink());
    }

    @Test
    void shouldGetAvatarFail() {
        //Получение(выгрузка) аватара неизвестного пользователя
        Long avatarId = 999L;
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> profileService.getAvatar(avatarId));
        assertThat(e.getMessage(), equalTo("Avatar with id: " + avatarId + " not found"));
    }

    @Test
    void shouldGetPublicationsSuccessfully() throws IOException {
        entryFile = TestUtil.createMultipartFile("src/test/resources/template.pdf");
        PublicationDtoResponse publication = profileService.uploadPublication(user2.getId(), entryFile, "description1");

        entryFile = TestUtil.createMultipartFile("src/test/resources/template.pdf");
        profileService.uploadPublication(user2.getId(), entryFile, "description2");

        //получение публикаций пользователя
        List<PublicationDtoResponse> publications = profileService.getPublications(user2.getId());

        assertThat(publications.size(), equalTo(2));
        TestUtil.deleteAll(user2.getEmail(), publication.getLink());
    }

    @Test
    void shouldGetSubscribersSuccessfully() {
        //подписка на пользователя
        profileService.subscribe(profile2.getId(), profile3.getId());
        profileService.subscribe(profile1.getId(), profile3.getId());

        List<ProfilePreviewDtoResponse> list = profileService.getSubscribers(profile3.getId());

        assertThat(list.size(), equalTo(2));
    }

    @Test
    void shouldGetSubscriptionsSuccessfully() {
        //получение подписок пользователя
        profileService.subscribe(profile1.getId(), profile2.getId());
        List<ProfilePreviewDtoResponse> list = profileService.getSubscriptions(profile1.getId());

        assertThat(list.size(), equalTo(1));

        profileService.subscribe(profile1.getId(), profile3.getId());
        list = profileService.getSubscriptions(profile1.getId());

        assertThat(list.size(), equalTo(2));
    }

    @Test
    void shouldGetOwnProfileSuccessfully() {
        //получение собственного профиля
        assertThat(profile1.getUserId(), equalTo(user1.getId()));
    }

    @Test
    void shouldGetOwnProfileFail() {
        //получение собственного профиля от неизвестного пользователя
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> profileService.getOwnProfile(unknownUserId));
        assertThat(e.getMessage(), equalTo("Profile for user with id: " + unknownUserId + " not found"));
    }

    @Test
    void shouldGetProfileSuccessfully() {
        //получение профиля пользователя
        profileToRegister3.setName("Ivan");

        profile3 = profileService.personalInformationUpdate(user3.getId(), profileToRegister3);
        ProfileShortDtoResponse shortDtoResponse = profileService.getProfile(profile3.getId());

        assertThat(shortDtoResponse.getId(), equalTo(profile3.getId()));
        assertThat(shortDtoResponse.getName(), equalTo(profileToRegister3.getName()));
    }

    @Test
    void shouldGetProfileFail() {
        //получение профиля неизвестного пользователя
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> profileService.getProfile(unknownUserId));
        assertThat(e.getMessage(), equalTo("Profile for user with id: " + unknownUserId + " not found"));
    }

    @Test
    void shouldGetPublicationSuccessfully() throws IOException {
        //получение публикаций
        entryFile = TestUtil.createMultipartFile("src/test/resources/template.pdf");
        PublicationDtoResponse send = profileService.uploadPublication(user1.getId(), entryFile, "description1");
        profileToRegister1.setName("name");
        profile1 = profileService.personalInformationUpdate(user1.getId(), profileToRegister1);

        PublicationDtoResponse resp = profileService.getPublication(send.getId(), profile1.getId());

        assertThat(resp.getLink(), equalTo(resp.getLink()));
        assertThat(resp.getId(), equalTo(resp.getId()));

        TestUtil.deleteAll(user1.getEmail(), resp.getLink());
    }

    @Test
    void shouldGetPublicationFail() {
        //получение публикации по неизвестному идентификатору
        Long publicationId = 1000000L;

        Exception e = assertThrows(NotFoundValidationException.class,
                () -> profileService.getPublication(publicationId, profile1.getId()));
        assertThat(e.getMessage(), equalTo("Publication for user with id: " + publicationId +
                " for profile with id: " + profile1.getId() + " not found"));
    }

    @Test
    void shouldSubscribeSuccessfully() {
        //получение подписок пользователя
        profileService.subscribe(profile1.getId(), profile2.getId());
        List<ProfilePreviewDtoResponse> list = profileService.getSubscribers(profile2.getId());

        assertThat(list.size(), equalTo(1));
        assertThat(list.get(0).getId(), equalTo(profile1.getId()));
    }

    @Test
    void shouldSubscribeFail() {
        //подписка от неизвестного профиля
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> profileService.subscribe(unknownUserId, profile2.getId()));
        assertThat(e.getMessage(), equalTo("Profile with id: " + unknownUserId + " not found"));

        //подписка на неизвестный профиль
        e = assertThrows(NotFoundValidationException.class,
                () -> profileService.subscribe(profile2.getId(), unknownUserId));
        assertThat(e.getMessage(), equalTo("Profile with id: " + unknownUserId + " not found"));

        //подписка на самого себя
        e = assertThrows(ConflictException.class,
                () -> profileService.subscribe(profile2.getId(), profile2.getId()));
        assertThat(e.getMessage(), equalTo("Subscriber id and profile id must not be the same"));

        //повторная попытка подписки на профиль
        profileService.subscribe(profile4.getId(), profile3.getId());
        e = assertThrows(ConflictException.class,
                () -> profileService.subscribe(profile4.getId(), profile3.getId()));
        assertThat(e.getMessage(), equalTo("You already subscribe on this profile"));
    }

    @Test
    void shouldGetProfilesSuccessfully() {
        //получение профилей постранично
        List<ProfilePreviewDtoResponse> list = profileService.getProfiles(0, 50);
        assertThat(list.size(), equalTo(4));

        list = profileService.getProfiles(0, 2);
        assertThat(list.size(), equalTo(2));

        list = profileService.getProfiles(4, 2);
        assertThat(list.size(), equalTo(0));

        list = profileService.getProfiles(1, 2);
        assertThat(list.size(), equalTo(2));

        list = profileService.getProfiles(2, 2);
        assertThat(list.size(), equalTo(0));
    }

    @Test
    void shouldGetStaffAndFactsSuccessfully() {
        //получение списка экспертов из Беларуси постранично
        List<ProfileAboutDto> list =
                profileService.getStaffAndFacts(null, null,
                        Set.of(EnumAuth.EXPERT), Set.of("Belarus"), 0, 2);
        assertThat(list.size(), equalTo(1));
        assertThat(list.get(0).getId(), equalTo(profile4.getId()));

        //получение списка участников из России постранично
        list = profileService.getStaffAndFacts(null, null,
                Set.of(EnumAuth.PARTICIPANT), Set.of("Russia"), 1, 2);
        assertThat(list.size(), equalTo(1));

        list = profileService.getStaffAndFacts(null, null,
                Set.of(EnumAuth.PARTICIPANT), Set.of("Russia"), 0, 3);
        assertThat(list.size(), equalTo(3));

        //Получение общего списка сотрудников постранично
        list = profileService.getStaffAndFacts(null, null, null, null, 0, 5);
        assertThat(list.size(), equalTo(4));
    }

    @Test
    void shouldGetChildrenAndExpertsSuccessfully() {
        //получение списка детей и специалистов по работе с ними
        List<ProfileAboutDto> list = profileService.getChildrenAndExperts(null, null,
                null, null, 0, 9);
        assertThat(list.size(), equalTo(2));
    }

    private UserDtoRequest createUserRequest(Integer id, EnumAuth authority) {
        UserDtoRequest user = new UserDtoRequest();
        user.setEmail(id + "@mail.ru");
        user.setPassword(id.toString());
        user.setEnumAuth(authority);
        return user;
    }

    private ProfileUpdateDtoRequest createProfileRequest(Integer id, Set<DirectionEnum> directions, String country) {
        ProfileUpdateDtoRequest profile = new ProfileUpdateDtoRequest();
        profile.setGender(id % 2 == 0 ? Gender.MAN : Gender.WOMAN);
        profile.setDirections(directions);
        profile.setCountry(country);
        return profile;
    }
}