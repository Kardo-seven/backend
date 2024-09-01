package ru.kardo.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.kardo.dto.comment.CommentFullDto;
import ru.kardo.dto.comment.CreateCommentDto;
import ru.kardo.dto.comment.UpdateCommentDto;
import ru.kardo.dto.feed.CreateFeedDto;
import ru.kardo.dto.feed.FeedFullDto;
import ru.kardo.dto.profile.ProfileFullDtoResponse;
import ru.kardo.dto.profile.ProfileUpdateDtoRequest;
import ru.kardo.dto.user.UserDtoRequest;
import ru.kardo.dto.user.UserDtoResponse;
import ru.kardo.exception.ConflictException;
import ru.kardo.exception.NotFoundValidationException;
import ru.kardo.model.enums.DirectionEnum;
import ru.kardo.model.enums.EnumAuth;
import ru.kardo.model.enums.Gender;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "db.name=test", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ActiveProfiles("ci,test")
@Transactional
class CommentServiceImplTest {

    private final UserService userService;
    private final ProfileService profileService;
    private final FeedService feedService;
    private final CommentService commentService;

    private ProfileFullDtoResponse profile1 = new ProfileFullDtoResponse();
    private ProfileFullDtoResponse profile2 = new ProfileFullDtoResponse();
    private ProfileFullDtoResponse profile3 = new ProfileFullDtoResponse();

    private final CreateFeedDto feed1Create = new CreateFeedDto();
    private final CreateFeedDto feed2Create = new CreateFeedDto();
    private final CreateFeedDto feed3Create = new CreateFeedDto();

    private FeedFullDto feed1 = new FeedFullDto();
    private FeedFullDto feed2 = new FeedFullDto();
    private FeedFullDto feed3 = new FeedFullDto();

    private final CreateCommentDto comment1Create = new CreateCommentDto();
    private final CreateCommentDto comment2Create = new CreateCommentDto();
    private final CreateCommentDto comment3Create = new CreateCommentDto();

    private CommentFullDto comment1 = new CommentFullDto();
    private CommentFullDto comment2 = new CommentFullDto();
    private CommentFullDto comment3 = new CommentFullDto();

    private final UpdateCommentDto comment1Update = new UpdateCommentDto();
    private final UpdateCommentDto comment2Update = new UpdateCommentDto();
    private final UpdateCommentDto comment3Update = new UpdateCommentDto();

    private final Long unknownProfileId = 1000L;
    private final Long unknownFeedId = 2000L;
    private final Long unknownCommentId = 3000L;

    @BeforeEach
    void init() {
        Integer newId = 31;
        UserDtoRequest userToRegister1 = createUserRequest(3 * newId, EnumAuth.PARTICIPANT);
        UserDtoRequest userToRegister2 = createUserRequest(5 * newId, EnumAuth.EXPERT);
        UserDtoRequest userToRegister3 = createUserRequest(8 * newId, EnumAuth.PARTICIPANT);

        UserDtoResponse user1 = userService.addUser(userToRegister1);
        UserDtoResponse user2 = userService.addUser(userToRegister2);
        UserDtoResponse user3 = userService.addUser(userToRegister3);

        ProfileUpdateDtoRequest profileToRegister1 = createProfileRequest(1, Set.of(DirectionEnum.BMX), "Russia");
        ProfileUpdateDtoRequest profileToRegister2 = createProfileRequest(2, Set.of(DirectionEnum.PARKOUR), "Russia");

        ProfileUpdateDtoRequest profileToRegister3 = createProfileRequest(3, Set.of(DirectionEnum.HIP_HOP), "Belarus");
        profileToRegister3.setIsChild(true);

        profile1 = profileService.personalInformationUpdate(user1.getId(), profileToRegister1);
        profile2 = profileService.personalInformationUpdate(user2.getId(), profileToRegister2);
        profile3 = profileService.personalInformationUpdate(user3.getId(), profileToRegister3);

        feed1Create.setDescription("some desc1");
        feed1 = feedService.addNewPost(profile1.getId(), feed1Create);

        feed1Create.setDescription("some desc2");
        feed2 = feedService.addNewPost(profile1.getId(), feed2Create);

        feed1Create.setDescription("some desc3");
        feed3 = feedService.addNewPost(profile1.getId(), feed3Create);
    }

    @Test
    void addNewCommentSuccessfully() {
        comment1Create.setText("new comment");
        comment1 = commentService.addNewComment(profile1.getId(), feed1.getId(), comment1Create);

        assertThat(comment1.getOwner().getId(), equalTo(profile1.getId()));
        Set<CommentFullDto> comments = commentService.getFeedComments(feed1.getId());
        assertThat(comments.size(), equalTo(1));

        //добавления комментария от неизвестного профиля
        comment2Create.setText("some");
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> commentService.addNewComment(unknownProfileId, feed1.getId(), comment2Create));
        assertThat(e.getMessage(), equalTo("Profile for user with id " + unknownProfileId + " not found"));

        //добавление комментария к неизвестному посту
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.addNewComment(profile1.getId(), unknownFeedId, comment2Create));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));
    }

    @Test
    void updateComment() {
        comment1Create.setText("new comment");
        comment1 = commentService.addNewComment(profile1.getId(), feed1.getId(), comment1Create);

        comment1Update.setText("update comment");
        comment1Update.setId(comment1.getId());

        comment1 = commentService.updateComment(profile1.getId(), feed1.getId(), comment1.getId(), comment1Update);
        assertThat(comment1.getText(), equalTo(comment1Update.getText()));



        comment2Create.setText("new comment2");
        comment2 = commentService.addNewComment(profile2.getId(), feed2.getId(), comment2Create);
        comment2Update.setText("new update");
        comment2Update.setId(comment2.getId());

        //обновление комментария от неизвестного пользователя
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> commentService.updateComment(unknownProfileId, feed2.getId(), comment2.getId(), comment2Update));
        assertThat(e.getMessage(), equalTo("Profile for user with id " + unknownProfileId + " not found"));

        //обновление комментария к неизвестному посту
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.updateComment(profile2.getId(), unknownFeedId, comment2.getId(), comment2Update));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));

        //обновления комментария с неизвестным идентификатором
        comment2Update.setId(unknownCommentId);

        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.updateComment(profile2.getId(), feed2.getId(), unknownCommentId, comment2Update));
        assertThat(e.getMessage(), equalTo("Comment with id " + unknownCommentId + " not found"));

        //Попытка обновления комментария не принадлежащего к посту
        comment2Update.setId(comment2.getId());

        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.updateComment(profile2.getId(), feed1.getId(), comment2.getId(), comment2Update));
        assertThat(e.getMessage(), equalTo("Comment with id " + comment2.getId() + " not found for this feed"));


        //попытка обновления комментария не автором этого комментария
        e = assertThrows(ConflictException.class,
                () -> commentService.updateComment(profile2.getId(), feed1.getId(), comment1.getId(), comment1Update));
        assertThat(e.getMessage(), equalTo("User with id " + profile2.getId() + " cannot update/delete this comment"));
    }

    @Test
    void deleteComment() {
        comment1Create.setText("new comment");
        comment1 = commentService.addNewComment(profile1.getId(), feed1.getId(), comment1Create);

        commentService.deleteComment(profile1.getId(), feed1.getId(), comment1.getId());
        Set<CommentFullDto> comments = commentService.getFeedComments(feed1.getId());

        assertThat(comments.size(), equalTo(0));

        //удаление комментария от неизвестного профиля
        comment1 = commentService.addNewComment(profile1.getId(), feed1.getId(), comment1Create);
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> commentService.deleteComment(unknownProfileId, feed1.getId(), comment1.getId()));
        assertThat(e.getMessage(), equalTo("Profile for user with id " + unknownProfileId + " not found"));

        //удаления комментария к неизвестному посту
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.deleteComment(profile1.getId(), unknownFeedId, comment1.getId()));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));

        //удаление комментария с неизвестным идентификатором
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.deleteComment(profile1.getId(), feed1.getId(), unknownCommentId));
        assertThat(e.getMessage(), equalTo("Comment with id " + unknownCommentId + " not found"));

        //удаление комментария не принадлежащего к данному посту
        comment3Create.setText("text3");
        comment3 = commentService.addNewComment(profile3.getId(), feed2.getId(), comment3Create);

        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.deleteComment(profile3.getId(), feed3.getId(), comment3.getId()));
        assertThat(e.getMessage(), equalTo("Comment with id " + comment3.getId() + " not found for this feed"));

        //попытка удаления комментария не автором комментария
        e = assertThrows(ConflictException.class,
                () -> commentService.deleteComment(profile3.getId(), feed1.getId(), comment1.getId()));
        assertThat(e.getMessage(), equalTo("User with id " + profile3.getId() + " cannot update/delete this comment"));
    }

    @Test
    void replyComment() {
        comment1Create.setText("new comment");
        comment1 = commentService.addNewComment(profile1.getId(), feed1.getId(), comment1Create);
        comment3Create.setText("comment3");
        comment3 = commentService.replyComment(profile3.getId(), feed1.getId(), comment1.getId(), comment1Create);

        Set<CommentFullDto> replies = commentService.getFeedCommentReplies(feed1.getId(), comment1.getId());

        assertThat(replies.size(), equalTo(1));
        assertThat(replies.stream().findFirst().get().getId(), equalTo(comment3.getId()));

        //ответ на комментарий от неизвестного пользователя
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> commentService.replyComment(unknownProfileId, feed1.getId(), comment1.getId(), comment1Create));
        assertThat(e.getMessage(), equalTo("Profile for user with id " + unknownProfileId + " not found"));

        //ответ на комментарий к неизвестному посту
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.replyComment(profile3.getId(), unknownFeedId, comment1.getId(), comment1Create));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));

        //ответ на комментарий с неизвестным идентификатором
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.replyComment(profile3.getId(), feed1.getId(), unknownCommentId, comment1Create));
        assertThat(e.getMessage(), equalTo("Comment with id " + unknownCommentId + " not found"));
    }

    @Test
    void likeFeedComment() {
        comment1Create.setText("new comment");
        comment1 = commentService.addNewComment(profile1.getId(), feed1.getId(), comment1Create);
        commentService.likeFeedComment(profile2.getId(), feed1.getId(), comment1.getId());
        Set<Long> likes = commentService.getComment(feed1.getId(), comment1.getId()).getLikes();

        assertThat(likes.size(), equalTo(1));
        assertThat(likes.stream().findFirst().get(), equalTo(profile2.getId()));

        //лайк комментарию от неизвестного пользователя
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> commentService.likeFeedComment(unknownProfileId, feed1.getId(), comment1.getId()));
        assertThat(e.getMessage(), equalTo("Profile for user with id " + unknownProfileId + " not found"));

        //лайк комментарию к неизвестному посту
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.likeFeedComment(profile2.getId(), unknownFeedId, comment1.getId()));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));

        //лайк к комментарию с неизвестным идентификатором
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.likeFeedComment(profile2.getId(), feed1.getId(), unknownCommentId));
        assertThat(e.getMessage(), equalTo("Comment with id " + unknownCommentId + " not found"));

        //лайк комментарию, не принадлежащего данному посту
        comment3Create.setText("some text");
        comment3 = commentService.addNewComment(profile3.getId(), feed3.getId(), comment3Create);

        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.likeFeedComment(profile2.getId(), feed1.getId(), comment3.getId()));
        assertThat(e.getMessage(), equalTo("Comment with id " + comment3.getId() + " not found for this feed"));
    }

    @Test
    void getFeedComments() {
        comment1Create.setText("new comment");
        comment1 = commentService.addNewComment(profile1.getId(), feed1.getId(), comment1Create);
        Set<CommentFullDto> comments = commentService.getFeedComments(feed1.getId());

        assertThat(comments.size(), equalTo(1));
        comment2Create.setText("new comment");
        comment2 = commentService.addNewComment(profile2.getId(), feed1.getId(), comment2Create);
        comments = commentService.getFeedComments(feed1.getId());

        assertThat(comments.size(), equalTo(2));

        //получение комментариев к посту с неизвестным идентификатором
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> commentService.getFeedComments(unknownFeedId));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));
    }

    @Test
    void getFeedCommentReplies() {
        comment1Create.setText("new comment");
        comment1 = commentService.addNewComment(profile1.getId(), feed1.getId(), comment1Create);
        comment2Create.setText("comment2");
        comment2 = commentService.replyComment(profile2.getId(), feed1.getId(), comment1.getId(), comment2Create);
        Set<CommentFullDto> replies = commentService.getFeedCommentReplies(feed1.getId(), comment1.getId());

        assertThat(replies.size(), equalTo(1));

        comment3Create.setText("comment3");
        comment3 = commentService.replyComment(profile3.getId(), feed1.getId(), comment1.getId(), comment3Create);
        replies = commentService.getFeedCommentReplies(feed1.getId(), comment1.getId());

        assertThat(replies.size(), equalTo(2));

        //получение ответов на комментарий к посту с неизвестным идентификатором
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> commentService.getFeedCommentReplies(unknownFeedId, comment1.getId()));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));

        //получение ответов на комментарий с неизвестным идентификатором
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.getFeedCommentReplies(feed1.getId(), unknownCommentId));
        assertThat(e.getMessage(), equalTo("Comment with id " + unknownCommentId + " not found"));

        //получение ответов на комментарий к другому посту с иным идентификатором
        comment3Create.setText("some more text");
        comment3 = commentService.addNewComment(profile3.getId(), feed3.getId(), comment3Create);

        comment2Create.setText("reply");
        comment2 = commentService.replyComment(profile3.getId(), feed3.getId(), comment3.getId(), comment2Create);

        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.getFeedCommentReplies(feed1.getId(), comment3.getId()));
        assertThat(e.getMessage(), equalTo("Comment with id " + comment3.getId() + " not found for this feed"));
    }

    @Test
    void getComment() {
        comment1Create.setText("new comment");
        comment1 = commentService.addNewComment(profile1.getId(), feed1.getId(), comment1Create);
        comment2Create.setText("comment2");
        comment2 = commentService.addNewComment(profile2.getId(), feed1.getId(), comment2Create);

        comment3 = commentService.getComment(feed1.getId(), comment1.getId());

        assertThat(comment3.getId(), equalTo(comment1.getId()));

        comment3 = commentService.getComment(feed1.getId(), comment2.getId());

        assertThat(comment3.getId(), equalTo(comment2.getId()));

        //получение комментария к посту с неизвестным идентификатором
        Exception e = assertThrows(NotFoundValidationException.class,
                () -> commentService.getComment(unknownFeedId, comment1.getId()));
        assertThat(e.getMessage(), equalTo("Feed with id " + unknownFeedId + " not found"));

        //получение комментария с неизвестным идентификатором
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.getComment(feed1.getId(), unknownCommentId));
        assertThat(e.getMessage(), equalTo("Comment with id " + unknownCommentId + " not found"));

        //получение комментария к другому посту с иным идентификатором
        comment3Create.setText("some more text");
        comment3 = commentService.addNewComment(profile3.getId(), feed3.getId(), comment3Create);
        e = assertThrows(NotFoundValidationException.class,
                () -> commentService.getComment(feed2.getId(), comment3.getId()));
        assertThat(e.getMessage(), equalTo("Comment with id " + comment3.getId() + " not found for this feed"));
    }

    private UserDtoRequest createUserRequest(Integer id, EnumAuth authority) {
        UserDtoRequest user = new UserDtoRequest();
        user.setEmail(id + "@email.ru");
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