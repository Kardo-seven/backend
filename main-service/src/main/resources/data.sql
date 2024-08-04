INSERT INTO events VALUES (1, '2025-01-01 12:12:12',
                           '2025-04-04 13:50:12',
                           'event number 1',
                           'VIDEO_CHALLENGE',
                           'event1',
                           'location1',
                           false);
INSERT INTO events VALUES (2, '2025-04-13 13:00:00',
                           '2025-05-04 20:00:00',
                           'event number 2',
                           'GRAND_FINAL',
                           'event2',
                           'location2',
                           false);
INSERT INTO events VALUES (3, '2025-02-13 12:00:00',
                           '2025-03-04 20:00:00',
                           'event number 3',
                           'CHILDREN',
                           'event3',
                           'location3',
                           false);
INSERT INTO events VALUES (4, '2025-02-20 14:00:00',
                           '2025-02-21 18:00:00',
                           'event number 4',
                           'COMPETITIONS',
                           'event4',
                           'location4',
                           false);
INSERT INTO events VALUES (5, '2025-03-17 14:00:00',
                           '2025-03-19 18:00:00',
                           'event number 5',
                           'AWARD',
                           'event5',
                           'location5', false
                          );
INSERT INTO events VALUES (6, '2025-06-01 14:00:00',
                           '2025-07-01 18:00:00',
                           'event number 6',
                           'PROJECTS',
                           'event6',
                           'location6', false
                          );


INSERT INTO events VALUES (7, '2025-04-01 13:00:00',
                           '2025-05-01 19:00:00',
                           'event number 7',
                           null,
                           'event7',
                           'location7', true
                          );

INSERT INTO event_directions VALUES
    (7, 'BMX');

insert into users(user_id, email, password) values(1, 'ex1@mail.ru','pass1') on conflict do nothing;
insert into authorities(user_id, authority) values(1, 'PARTICIPANT') on conflict do nothing;
insert into avatars(avatar_id, title, type, link) values(1, 'avatar1','avatar1','avatar1') on conflict do nothing;
insert into profile(name, last_name, sur_name, phone, birthday, gender, country,
 region, city, citizenship, user_id, avatar_id, about, is_child, is_child_expert) values('name1', 'lastName', 'surName', 'phone1',
 '2024-02-09 09:40:20', 'MAN', 'RUSSIA', 'MOSCOW',  'MOSCOW', 'RUSSIA', 1, 1, 'about', 'true', 'true') on conflict do nothing;
insert into social_network_links(user_id, link) values(1, 'link1') on conflict do nothing;
insert into profile_seasons(user_id, season) values(1, 'season1') on conflict do nothing;
insert into profile_seasons(user_id, season) values(1, 'season2') on conflict do nothing;
insert into profile_seasons(user_id, season) values(1, 'season3') on conflict do nothing;
insert into profile_directions(user_id, direction) values(1, 'PARKOUR') on conflict do nothing;
insert into profile_directions(user_id, direction) values(1, 'BMX') on conflict do nothing;
insert into profile_directions(user_id, direction) values(1, 'SKATEBOARDING') on conflict do nothing;

insert into users(user_id, email, password) values(2, 'ex2@mail.ru','pass2') on conflict do nothing;
insert into authorities(user_id, authority) values(2, 'PARTICIPANT') on conflict do nothing;
insert into avatars(avatar_id, title, type, link) values(2, 'avatar2','avatar2','avatar2') on conflict do nothing;
insert into profile(name, last_name, sur_name, phone, birthday, gender, country,
 region, city, citizenship, user_id, avatar_id, about, is_child, is_child_expert)
 values('name2', 'lastName', 'surName', 'phone2', '2024-02-09 09:40:20', 'MAN', 'RUSSIA', 'MOSCOW',  'MOSCOW',
  'RUSSIA', 2, 2, 'about', 'false','true') on conflict do nothing;
insert into social_network_links(user_id, link) values(2, 'link2') on conflict do nothing;
insert into profile_seasons(user_id, season) values(2, 'season1') on conflict do nothing;
insert into profile_seasons(user_id, season) values(2, 'season2') on conflict do nothing;
insert into profile_directions(user_id, direction) values(2, 'SKATEBOARDING') on conflict do nothing;
insert into profile_directions(user_id, direction) values(2, 'PARKOUR') on conflict do nothing;

insert into users(user_id, email, password) values(3, 'ex3@mail.ru','pass3') on conflict do nothing;
insert into authorities(user_id, authority) values(3, 'PARTICIPANT') on conflict do nothing;
insert into avatars(avatar_id, title, type, link) values(3, 'avatar3','avatar3','avatar3') on conflict do nothing;
insert into profile(name, last_name, sur_name, phone, birthday, gender, country,
 region, city, citizenship, user_id, avatar_id, about, is_child, is_child_expert)
 values('name3', 'lastName', 'surName', 'phone3', '2024-02-09 09:40:20', 'MAN', 'RUSSIA', 'MOSCOW',  'MOSCOW',
 'RUSSIA', 3, 3, 'about', 'true', 'false') on conflict do nothing;
insert into social_network_links(user_id, link) values(3, 'link3') on conflict do nothing;
insert into profile_seasons(user_id, season) values(3, 'season1') on conflict do nothing;
insert into profile_seasons(user_id, season) values(3, 'season3') on conflict do nothing;
insert into profile_directions(user_id, direction) values(3, 'BMX') on conflict do nothing;