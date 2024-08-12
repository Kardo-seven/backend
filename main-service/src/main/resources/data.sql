INSERT INTO events VALUES (1,
                           'AWARD',
                           'Премия');
INSERT INTO events VALUES (2,
                           'CHILDREN',
                           'Дети');
INSERT INTO events VALUES (3,
                           'COMPETITIONS',
                           'Соревнования');
INSERT INTO events VALUES (4,
                           'VIDEO_CHALLENGE',
                           'Видеоконкурс');
INSERT INTO events VALUES (5,
                           'GRAND_FINAL',
                           'Гранд-финал');
INSERT INTO events VALUES (6,
                           'PROJECTS',
                           'Проекты');

INSERT INTO event_images VALUES (1, 1, 'AWARD.jpg', 'image/jpeg', 'resources/events/AWARD.jpg');
INSERT INTO event_images VALUES (2, 2, 'CHILDREN.jpg', 'image/jpeg', 'resources/events/CHILDREN.jpg');
INSERT INTO event_images VALUES (3, 3, 'COMPETITIONS.jpg', 'image/jpeg', 'resources/events/COMPETITIONS.jpg');
INSERT INTO event_images VALUES (4, 4, 'VIDEO_CHALLENGE.jpg', 'image/jpeg', 'resources/events/VIDEO_CHALLENGE.jpg');
INSERT INTO event_images VALUES (5, 5, 'GRAND_FINAL.jpg', 'image/jpeg', 'resources/events/GRAND_FINAL.jpg');
INSERT INTO event_images VALUES (6, 6, 'PROJECTS.jpg', 'image/jpeg', 'resources/events/PROJECTS.jpg');

-- INSERT INTO event_directions VALUES (1, 'BMX');
-- INSERT INTO event_directions VALUES (1, 'SKATEBOARDING');
-- INSERT INTO event_directions VALUES (1, 'PARKOUR');
-- INSERT INTO event_directions VALUES (1, 'BREAKING');
-- INSERT INTO event_directions VALUES (1, 'WORKOUT');
-- INSERT INTO event_directions VALUES (1, 'GRAFFITI');
-- INSERT INTO event_directions VALUES (1, 'DJING');
-- INSERT INTO event_directions VALUES (1, 'STUNK_SCOOTER');
-- INSERT INTO event_directions VALUES (1, 'TRICKING');
-- INSERT INTO event_directions VALUES (1, 'FREERAN');
-- INSERT INTO event_directions VALUES (1, 'HIP_HOP');
--
-- INSERT INTO event_directions VALUES (2, 'BMX');
-- INSERT INTO event_directions VALUES (2, 'SKATEBOARDING');
-- INSERT INTO event_directions VALUES (2, 'PARKOUR');
-- INSERT INTO event_directions VALUES (2, 'BREAKING');
-- INSERT INTO event_directions VALUES (2, 'WORKOUT');
-- INSERT INTO event_directions VALUES (2, 'GRAFFITI');
-- INSERT INTO event_directions VALUES (2, 'DJING');
-- INSERT INTO event_directions VALUES (2, 'STUNK_SCOOTER');
-- INSERT INTO event_directions VALUES (2, 'TRICKING');
-- INSERT INTO event_directions VALUES (2, 'FREERAN');
-- INSERT INTO event_directions VALUES (2, 'HIP_HOP');
--
-- INSERT INTO event_directions VALUES (3, 'BMX');
-- INSERT INTO event_directions VALUES (3, 'SKATEBOARDING');
-- INSERT INTO event_directions VALUES (3, 'PARKOUR');
-- INSERT INTO event_directions VALUES (3, 'BREAKING');
-- INSERT INTO event_directions VALUES (3, 'WORKOUT');
-- INSERT INTO event_directions VALUES (3, 'GRAFFITI');
-- INSERT INTO event_directions VALUES (3, 'DJING');
-- INSERT INTO event_directions VALUES (3, 'STUNK_SCOOTER');
-- INSERT INTO event_directions VALUES (3, 'TRICKING');
-- INSERT INTO event_directions VALUES (3, 'FREERAN');
-- INSERT INTO event_directions VALUES (3, 'HIP_HOP');
--
-- INSERT INTO event_directions VALUES (4, 'BMX');
-- INSERT INTO event_directions VALUES (4, 'SKATEBOARDING');
-- INSERT INTO event_directions VALUES (4, 'PARKOUR');
-- INSERT INTO event_directions VALUES (4, 'BREAKING');
-- INSERT INTO event_directions VALUES (4, 'WORKOUT');
-- INSERT INTO event_directions VALUES (4, 'GRAFFITI');
-- INSERT INTO event_directions VALUES (4, 'DJING');
-- INSERT INTO event_directions VALUES (4, 'STUNK_SCOOTER');
-- INSERT INTO event_directions VALUES (4, 'TRICKING');
-- INSERT INTO event_directions VALUES (4, 'FREERAN');
-- INSERT INTO event_directions VALUES (4, 'HIP_HOP');
--
-- INSERT INTO event_directions VALUES (5, 'BMX');
-- INSERT INTO event_directions VALUES (5, 'SKATEBOARDING');
-- INSERT INTO event_directions VALUES (5, 'PARKOUR');
-- INSERT INTO event_directions VALUES (5, 'BREAKING');
-- INSERT INTO event_directions VALUES (5, 'WORKOUT');
-- INSERT INTO event_directions VALUES (5, 'GRAFFITI');
-- INSERT INTO event_directions VALUES (5, 'DJING');
-- INSERT INTO event_directions VALUES (5, 'STUNK_SCOOTER');
-- INSERT INTO event_directions VALUES (5, 'TRICKING');
-- INSERT INTO event_directions VALUES (5, 'FREERAN');
-- INSERT INTO event_directions VALUES (5, 'HIP_HOP');
--
-- INSERT INTO event_directions VALUES (6, 'BMX');
-- INSERT INTO event_directions VALUES (6, 'SKATEBOARDING');
-- INSERT INTO event_directions VALUES (6, 'PARKOUR');
-- INSERT INTO event_directions VALUES (6, 'BREAKING');
-- INSERT INTO event_directions VALUES (6, 'WORKOUT');
-- INSERT INTO event_directions VALUES (6, 'GRAFFITI');
-- INSERT INTO event_directions VALUES (6, 'DJING');
-- INSERT INTO event_directions VALUES (6, 'STUNK_SCOOTER');
-- INSERT INTO event_directions VALUES (6, 'TRICKING');
-- INSERT INTO event_directions VALUES (6, 'FREERAN');
-- INSERT INTO event_directions VALUES (6, 'HIP_HOP');

INSERT INTO grand_final_events VALUES (1, '2024-08-22', '2024-08-22 19:00:00',
                           '2025-08-22 22:00:00',
                           'Шоу «Город КАРДО» Присоединяйся к главному герою в его путешествии по городу КАРДО и узнай, как одна мечта может изменить мир! Это шоу для всех, кто любит мечтать, творить и жить на полную! Хэдлайнером станет легендарная хардкор-рэп-группа «ONYX«!',
                           'ENTERTAINMENT_PROGRAM',
                           'Главное событие дня: Церемония открытия Международной Конкурс-премии «КАРДО»',
                           'Главная сцена. Площадь Ленина');

INSERT INTO grand_final_event_directions VALUES (1, 'BMX');
INSERT INTO grand_final_event_directions VALUES (1, 'SKATEBOARDING');
INSERT INTO grand_final_event_directions VALUES (1, 'PARKOUR');
INSERT INTO grand_final_event_directions VALUES (1, 'BREAKING');
INSERT INTO grand_final_event_directions VALUES (1, 'WORKOUT');
INSERT INTO grand_final_event_directions VALUES (1, 'GRAFFITI');
INSERT INTO grand_final_event_directions VALUES (1, 'DJING');
INSERT INTO grand_final_event_directions VALUES (1, 'STUNK_SCOOTER');
INSERT INTO grand_final_event_directions VALUES (1, 'TRICKING');
INSERT INTO grand_final_event_directions VALUES (1, 'FREERAN');
INSERT INTO grand_final_event_directions VALUES (1, 'HIP_HOP');

INSERT INTO grand_final_events VALUES (2, '2024-08-22', '2024-08-22 16:00:00',
                           '2025-08-22 17:30:00',
                           'В рамках фестиваля «КАРДО» на площадке школы танцев OFFBEATS DANCE SCHOOL вас ждут:

* Открытые мастер-классы по хип-хопу и брейк-дансу  от опытных преподавателей.
* Незабываемая атмосфера уличной культуры:  музыка, танцы, яркие эмоции — ты почувствуешь себя настоящим уличным танцором!
* Возможность попробовать новые стили танцев:  открой для себя мир хип-хопа и брейк-данса!

Приходи и погрузись в мир уличных танцев!',
                           'STREET_CULTURE_AND_SPORTS_PROGRAM',
                           'Мастер-классы по хип-хопу и брейкингу от школы танцев OFFBEATS DANCE SCHOOL',
                           'Площадка Школы танцев OFFBEATS DANCE SCHOOL (Площадь Ленина)');

INSERT INTO grand_final_event_directions VALUES (2, 'BREAKING');
INSERT INTO grand_final_event_directions VALUES (2, 'HIP_HOP');

INSERT INTO grand_final_events VALUES (3, '2024-08-23', '2024-08-22 16:00:00',
                           '2025-08-22 20:00:00',
                           'Школа граффити и стрит-арта «Мастерская» приглашает вас окунуться в захватывающий мир уличного искусства!

Что вас ждёт?

Впечатляющая выставка работ ставропольских граффити-художников на огромных холстах! Представьте себе масштаб — до трёх квадратных метров чистого творчества!
Уникальная возможность познакомиться с мастерами своего дела: Nas, Ewar, Nest, Chosnne — опытные райтеры с десятилетним стажем, чьи работы украшают стены от Владивостока до Сочи, станут вашими проводниками в мир граффити.
Не упустите шанс прикоснуться к искусству улиц и открыть для себя нечто новое!',
                           'STREET_CULTURE_AND_SPORTS_PROGRAM',
                           'Выставка стрит-арт работ Ставропольских граффити художников',
                           'Площадка школы граффити и стрит-арта «Мастерская» (Площадь Ленина)');

INSERT INTO grand_final_event_directions VALUES (3, 'GRAFFITI');

INSERT INTO grand_final_events VALUES (4, '2024-08-22', '2024-08-22 16:00:00',
                           '2025-08-22 20:00:00',
                           'Школа граффити и стрит-арта «Мастерская» приглашает вас окунуться в захватывающий мир уличного искусства!

Что вас ждёт?

Впечатляющая выставка работ ставропольских граффити-художников на огромных холстах! Представьте себе масштаб — до трёх квадратных метров чистого творчества!
Уникальная возможность познакомиться с мастерами своего дела: Nas, Ewar, Nest, Chosnne — опытные райтеры с десятилетним стажем, чьи работы украшают стены от Владивостока до Сочи, станут вашими проводниками в мир граффити.
Не упустите шанс прикоснуться к искусству улиц и открыть для себя нечто новое!',
                           'STREET_CULTURE_AND_SPORTS_PROGRAM',
                           'Выставка стрит-арт работ Ставропольских граффити художников',
                           'Площадка школы граффити и стрит-арта «Мастерская» (Площадь Ленина)');


INSERT INTO grand_final_event_directions VALUES (4, 'GRAFFITI');

INSERT INTO grand_final_events VALUES (5, '2024-08-25', '2024-08-25 19:30:00',
                                       '2025-08-25 22:00:00',
                                       'Приглашаем вас на концерт звезд VK RECORDS, который состоится под открытым небом на Главной сцене!

Kumie
Артист сочетает в своих треках жанры хип-хопа и поп-музыки. С детства одержимый музыкой, преодолел множество препятствий, включая сопротивление близких, чтобы следовать своему призванию. В своих текстах он говорит о жизненных целях, ценностях и любви.

SAMPLIN
Хип-хоп артист, чья музыка отличается оригинальным звучанием и глубокими текстами. Его творчество сочетает в себе разнообразные музыкальные стили, артист добавляет в свою музыку элементы рэпа, соула и R&B.

Мутки
Жизненные истории с самого дна жизни, неправильные решения и резкие движения, приводящие к непредсказуемым последствиям. Сочетание электронного звука, шаманского вокала и раскатистой читки — это мутки.',
                                       'LEISURE_PROGRAM',
                                       'Вечеринка VK Records',
                                       'Главная сцена. Площадь Ленина');



INSERT INTO documents (document_id, title, type, link) VALUES (
    1,
    'hip_hop_kardo-7_second-stage.pdf',
    'application/pdf',
    'resources/document/hip_hop_kardo-7_second-stage.pdf'
);

INSERT INTO documents (document_id, title, type, link) VALUES (
    2,
    'graffiti_kardo-7_second-stage.pdf',
    'application/pdf',
    'resources/document/graffiti_kardo-7_second-stage.pdf'
);

INSERT INTO documents (document_id, title, type, link) VALUES (
    3,
    'dj_kardo-7_second-stage.pdf',
    'application/pdf',
    'resources/document/dj_kardo-7_second-stage.pdf'
);

INSERT INTO documents (document_id, title, type, link) VALUES (
    4,
    'workout_kardo-7_second-stage.pdf',
    'application/pdf',
    'resources/document/workout_kardo-7_second-stage.pdf'
);

INSERT INTO documents (document_id, title, type, link) VALUES (
    5,
    'award_laureates_kardo-7.pdf',
    'application/pdf',
    'resources/document/award_laureates_kardo-7.pdf'
);

INSERT INTO documents (document_id, title, type, link) VALUES (
    6,
    'tricking_kardo-7_second-stage.pdf',
    'application/pdf',
    'resources/document/tricking_kardo-7_second-stage.pdf'
);

INSERT INTO news_banner (news_banner_id, link, title, type) VALUES (
    1,
    'resources/news/reg_mos_obl_kardo_24.jpg',
    'reg_mos_obl_kardo_24.jpg',
    'image/jpeg'
);

INSERT INTO news (news_id, description, created, title, event_time, banner_id) VALUES (
    1,
    'Московская область готова собрать представителей улиц в одном месте!

Покажи себя на региональном этапе в городе Дмитров. Соревнования пройдут по направлениям: скейтбординг, трюковой самокат, BMX, воркаут, трикинг, брейкинг.

Участники будут бороться за право поехать с 22-25 августа на Гранд-финал «КАРДО» [7] в Ставрополе.

Время и место проведения:
— 14 июля, г. Дмитров, МБУ ПКиО «Березовая роща», 14:00',
    '2024-02-14 11:00:00',
    'РЕГИОНАЛЬНЫЙ ЭТАП: МОСКОВСКАЯ ОБЛАСТЬ',
    '2024-07-14 14:00:00',
    1
);

INSERT INTO news_banner (news_banner_id, link, title, type) VALUES (
    2,
    'resources/news/reg_tatarstan_kardo_24.jpg',
    'reg_tatarstan_kardo_24.jpg',
    'image/jpeg'
);

INSERT INTO news (news_id, description, created, title, event_time, banner_id) VALUES (
    2,
    'Покажи себя на региональном этапе в Казани! 29 июня пройдут соревнования по направлениям: трюковой самокат, BMX, воркаут, паркур, фриран.

Топы получат возможность приехать 22-25 августа на Гранд-финал «КАРДО» в Ставрополь.

Время и место проведения:
— Экстрим-парк «Урам», с 10:00.',
    '2024-02-12 12:00:00',
    'РЕГИОНАЛЬНЫЙ ЭТАП: РЕСПУБЛИКА ТАТАРСТАН',
    '2024-06-29 10:00:00',
    2
);

INSERT INTO news_banner (news_banner_id, link, title, type) VALUES (
    3,
    'resources/news/reg_smolensk_kardo_24.jpg',
    'reg_smolensk_kardo_24.jpg',
    'image/jpeg'
);

INSERT INTO news (news_id, description, created, title, event_time, banner_id) VALUES (
    3,
    'Смоленская область готова почувствовать вайб КАРДО и собрать представителей улиц в одном месте.

Покажи себя на региональном этапе в Смоленске. 29 июня пройдут соревнования по направлениям: скейтбординг, трюковой самокат, BMX, хип-хоп, брейкинг, граффити.

Топы получат возможность приехать 22-25 августа на Гранд-финал «КАРДО» в Ставрополь.

Время и место проведения:
— Дворец спорта «Юбилейный» с 12:00.',
    '2024-02-10 13:00:00',
    'РЕГИОНАЛЬНЫЙ ЭТАП: СМОЛЕНСКАЯ ОБЛАСТЬ',
    '2024-06-29 12:00:00',
    3
);

INSERT INTO resources (resource_id, title, type, link) VALUES (
    1,
    'KARDO_7_Intro_FHD.mp4',
    'video/mp4',
    'resources/resource/KARDO_7_Intro_FHD.mp4'
);

INSERT INTO resources (resource_id, title, type, link) VALUES (
    2,
    'KARDO_7_Outro_FHD.mp4',
    'video/mp4',
    'resources/resource/KARDO_7_Outro_FHD.mp4'
);

INSERT INTO resources (resource_id, title, type, link) VALUES (
    3,
    'KARDO_7_Intro_9х16.mp4',
    'video/mp4',
    'resources/resource/KARDO_7_Intro_9х16.mp4'
);

INSERT INTO resources (resource_id, title, type, link) VALUES (
    4,
    'KARDO_7_Outro_9х16.mp4',
    'video/mp4',
    'resources/resource/KARDO_7_Outro_9х16.mp4'
);


insert into users(user_id, email, password) values(10001, '10001@mail.ru', 'pass10001');
insert into users(user_id, email, password) values(10002, '10002@mail.ru', 'pass10002');
insert into users(user_id, email, password) values(10003, '10003@mail.ru', 'pass10003');
insert into users(user_id, email, password) values(10004, '10004@mail.ru', 'pass10004');
insert into users(user_id, email, password) values(10005, '10005@mail.ru', 'pass10005');


insert into authorities(user_id, authority) values(10001, 'PARTICIPANT');
insert into authorities(user_id, authority) values(10002, 'PARTICIPANT');
insert into authorities(user_id, authority) values(10003, 'PARTICIPANT');
insert into authorities(user_id, authority) values(10004, 'PARTICIPANT');
insert into authorities(user_id, authority) values(10005, 'PARTICIPANT');


insert into avatars(avatar_id, title, type, link) values(10001, 'user1_avatar.jpg', 'image/jpeg',
'resources/10001@mail.ru/avatar/user1_avatar.jpg');
insert into avatars(avatar_id, title, type, link) values(10002, 'user2_avatar.jpg', 'image/jpeg',
'resources/10002@mail.ru/avatar/user2_avatar.jpg');
insert into avatars(avatar_id, title, type, link) values(10003, 'user3_avatar.jpg', 'image/jpeg',
'resources/10003@mail.ru/avatar/user3_avatar.jpg');
insert into avatars(avatar_id, title, type, link) values(10004, 'user4_avatar.jpg', 'image/jpeg',
'resources/10004@mail.ru/avatar/user4_avatar.jpg');
insert into avatars(avatar_id, title, type, link) values(10005, 'user5_avatar.jpg', 'image/jpeg',
'resources/10005@mail.ru/avatar/user5_avatar.jpg');


insert into profile(name, last_name, sur_name, phone, birthday, gender, country,
 region, city, citizenship, user_id, avatar_id, about, is_child, is_child_expert)
 values('name10001', 'lastName', 'surName', 'phone10001', '2024-02-09', 'MAN', 'RUSSIA', 'MOSCOW',  'MOSCOW',
  'RUSSIA', 10001, 10001, 'about', 'true', 'true');

insert into profile(name, last_name, sur_name, phone, birthday, gender, country,
 region, city, citizenship, user_id, avatar_id, about, is_child, is_child_expert)
 values('name10002', 'lastName', 'surName', 'phone10002', '2024-02-09', 'MAN', 'RUSSIA', 'MOSCOW',  'MOSCOW',
  'RUSSIA', 10002, 10002, 'about', 'false','true');

insert into profile(name, last_name, sur_name, phone, birthday, gender, country,
 region, city, citizenship, user_id, avatar_id, about, is_child, is_child_expert)
 values('name10003', 'lastName', 'surName', 'phone10003', '2024-02-09', 'MAN', 'RUSSIA', 'MOSCOW',  'MOSCOW',
  'RUSSIA', 10003, 10003, 'about', 'true', 'false');

insert into profile(name, last_name, sur_name, phone, birthday, gender, country,
 region, city, citizenship, user_id, avatar_id, about, is_child, is_child_expert)
 values('name10004', 'lastName', 'surName', 'phone10004', '2024-02-09', 'MAN', 'RUSSIA', 'MOSCOW',  'MOSCOW',
  'RUSSIA', 10004, 10004, 'about', 'false', 'false');

insert into profile(name, last_name, sur_name, phone, birthday, gender, country,
 region, city, citizenship, user_id, avatar_id, about, is_child, is_child_expert)
 values('name10005', 'lastName', 'surName', 'phone10005', '2024-02-09', 'MAN', 'RUSSIA', 'MOSCOW',  'MOSCOW',
  'RUSSIA', 10005, 10005, 'about', 'false', 'false');


insert into social_network_links(user_id, link) values(10001, 'link10001');
insert into social_network_links(user_id, link) values(10002, 'link10002');
insert into social_network_links(user_id, link) values(10003, 'link10003');
insert into social_network_links(user_id, link) values(10004, 'link10004');
insert into social_network_links(user_id, link) values(10005, 'link10005');


insert into profile_seasons(user_id, season) values(10001, 'season1');
insert into profile_seasons(user_id, season) values(10001, 'season2');
insert into profile_seasons(user_id, season) values(10001, 'season3');

insert into profile_seasons(user_id, season) values(10002, 'season1');
insert into profile_seasons(user_id, season) values(10002, 'season2');

insert into profile_seasons(user_id, season) values(10003, 'season1');
insert into profile_seasons(user_id, season) values(10003, 'season3');

insert into profile_seasons(user_id, season) values(10004, 'season1');
insert into profile_seasons(user_id, season) values(10004, 'season4');

insert into profile_seasons(user_id, season) values(10005, 'season1');
insert into profile_seasons(user_id, season) values(10005, 'season4');


insert into profile_directions(user_id, direction) values(10001, 'PARKOUR');
insert into profile_directions(user_id, direction) values(10001, 'BMX');
insert into profile_directions(user_id, direction) values(10001, 'SKATEBOARDING');

insert into profile_directions(user_id, direction) values(10002, 'SKATEBOARDING');
insert into profile_directions(user_id, direction) values(10002, 'PARKOUR');

insert into profile_directions(user_id, direction) values(10003, 'BMX');

insert into profile_directions(user_id, direction) values(10004, 'BMX');

insert into profile_directions(user_id, direction) values(10005, 'BMX');


insert into subscriptions values (10001, 10001, 10002);
insert into subscriptions values (10002, 10001, 10003);
insert into subscriptions values (10003, 10001, 10004);
insert into subscriptions values (10004, 10001, 10005);

insert into subscriptions values (10005, 10002, 10001);
insert into subscriptions values (10006, 10002, 10003);
insert into subscriptions values (10007, 10002, 10005);

insert into subscriptions values (10008, 10003, 10001);
insert into subscriptions values (10009, 10003, 10002);
insert into subscriptions values (10010, 10003, 10005);

insert into subscriptions values (10011, 10004, 10002);
insert into subscriptions values (10012, 10004, 10005);

insert into subscriptions values (10013, 10005, 10002);
insert into subscriptions values (10014, 10005, 10001);


insert into publications values(10001, 10001, 'img1.jpg', 'image/jpeg',
'resources/10001@mail.ru/publications/img1.jpg', 'description');
insert into publications values(10002, 10001, 'img2.jpg', 'image/jpeg',
'resources/10001@mail.ru/publications/img2.jpg', 'description');
insert into publications values(10003, 10001, 'img3.jpg', 'image/jpeg',
'resources/10001@mail.ru/publications/img3.jpg', 'description');

insert into publications values(10004, 10002, 'img1.jpg', 'image/jpeg',
'resources/10002@mail.ru/publications/img1.jpg', 'description');
insert into publications values(10005, 10002, 'img2.jpg', 'image/jpeg',
'resources/10002@mail.ru/publications/img2.jpg', 'description');
insert into publications values(10006, 10002, 'img3.jpg', 'image/jpeg',
'resources/10002@mail.ru/publications/img3.jpg', 'description');

insert into publications values(10007, 10003, 'img1.jpg', 'image/jpeg',
'resources/10003@mail.ru/publications/img1.jpg', 'description');
insert into publications values(10008, 10003, 'img2.jpg', 'image/jpeg',
'resources/10003@mail.ru/publications/img2.jpg', 'description');
insert into publications values(10009, 10003, 'img3.jpg', 'image/jpeg',
'resources/10003@mail.ru/publications/img3.jpg', 'description');

insert into publications values(10010, 10004, 'img1.jpg', 'image/jpeg',
'resources/10004@mail.ru/publications/img1.jpg', 'description');
insert into publications values(10011, 10004, 'img2.jpg', 'image/jpeg',
'resources/10004@mail.ru/publications/img2.jpg', 'description');
insert into publications values(10012, 10004, 'img3.jpg', 'image/jpeg',
'resources/10004@mail.ru/publications/img3.jpg', 'description');

insert into publications values(10013, 10005, 'img1.jpg', 'image/jpeg',
'resources/10005@mail.ru/publications/img1.jpg', 'description');
insert into publications values(10014, 10005, 'img2.jpg', 'image/jpeg',
'resources/10005@mail.ru/publications/img2.jpg', 'description');
insert into publications values(10015, 10005, 'img3.jpg', 'image/jpeg',
'resources/10005@mail.ru/publications/img3.jpg', 'description');