DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email varchar(50) unique NOT NULL,
    password varchar(200) unique NOT NULL
);

DROP TABLE IF EXISTS authorities CASCADE;
CREATE TABLE IF NOT EXISTS authorities (
    user_id BIGINT REFERENCES users(user_id) ON DELETE CASCADE,
    authority varchar(20)
);

DROP TABLE IF EXISTS avatars CASCADE;
CREATE TABLE IF NOT EXISTS avatars (
    avatar_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title varchar(250) NOT NULL,
    type varchar(50) NOT NULL,
    link varchar(1000) NOT NULL
);


DROP TABLE IF EXISTS profile CASCADE;
CREATE TABLE IF NOT EXISTS profile (
    name varchar(200),
    last_name varchar(200),
    sur_name varchar(200),
    phone varchar(200) unique,
    birthday DATE,
    gender varchar(20),
    country varchar(200),
    region varchar(200),
    city varchar(200),
    citizenship varchar(200),
    user_id BIGINT REFERENCES users(user_id) ON DELETE CASCADE NOT NULL,
    avatar_id BIGINT REFERENCES avatars(avatar_id) ON DELETE CASCADE unique,
        about varchar,
    is_child BOOLEAN,
    is_child_expert BOOLEAN,
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS subscribers CASCADE;
CREATE TABLE IF NOT EXISTS subscribers (
    user_id BIGINT REFERENCES profile(user_id) ON DELETE CASCADE,
    subscriber_id BIGINT REFERENCES profile(user_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS social_network_links CASCADE;
CREATE TABLE IF NOT EXISTS social_network_links (
    user_id BIGINT REFERENCES profile(user_id) ON DELETE CASCADE,
    link varchar(1000)
);

DROP TABLE IF EXISTS publications CASCADE;
CREATE TABLE IF NOT EXISTS publications (
    publication_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id BIGINT REFERENCES profile(user_id) ON DELETE CASCADE,
    title varchar(250) NOT NULL,
    type varchar(50) NOT NULL,
    link varchar(1000) NOT NULL,
    description varchar(5000)
);

DROP TABLE IF EXISTS events CASCADE;
CREATE TABLE IF NOT EXISTS events (
    event_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    description varchar(1000),
    event_type varchar(200),
    title varchar(200),
    location varchar(200),
    is_grand_final_event boolean
);

DROP TABLE IF EXISTS event_images CASCADE;
CREATE TABLE IF NOT EXISTS event_images (
    image_event_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    event_id BIGINT REFERENCES events(event_id) ON DELETE CASCADE,
    title varchar(250) NOT NULL,
    type varchar(50) NOT NULL,
    link varchar(1000) NOT NULL
);

DROP TABLE IF EXISTS event_directions CASCADE;
CREATE TABLE IF NOT EXISTS event_directions (
    event_id BIGINT REFERENCES events(event_id) ON DELETE CASCADE,
    direction varchar(100)
);

DROP TABLE IF EXISTS user_requests CASCADE;
CREATE TABLE IF NOT EXISTS user_requests (
    request_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(200),
    last_name varchar(200),
    sur_name varchar(200),
    phone varchar(200),
    birthday DATE,
    gender varchar(20),
    email varchar(200),
    created TIMESTAMP,
    status varchar(200),
    type_of_selection varchar(200),
    user_id BIGINT REFERENCES profile(user_id) ON DELETE CASCADE NOT NULL,
    event_id BIGINT REFERENCES events(event_id) ON DELETE CASCADE NOT NULL
);

DROP TABLE IF EXISTS request_preview CASCADE;
CREATE TABLE IF NOT EXISTS request_preview (
    request_preview_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    request_id BIGINT REFERENCES user_requests(request_id) ON DELETE CASCADE unique,
    title varchar(250) NOT NULL,
    type varchar(50) NOT NULL,
    link varchar(1000) NOT NULL
);

DROP TABLE IF EXISTS user_request_directions CASCADE;
CREATE TABLE IF NOT EXISTS user_request_directions (
   request_id BIGINT REFERENCES user_requests(request_id) ON DELETE CASCADE,
   direction varchar(100)
);

DROP TABLE IF EXISTS request_social_network_links CASCADE;
CREATE TABLE IF NOT EXISTS request_social_network_links (
    request_id BIGINT REFERENCES user_requests(request_id) ON DELETE CASCADE,
    link varchar(1000)
);

DROP TABLE IF EXISTS news_banner CASCADE;
CREATE TABLE IF NOT EXISTS news_banner (
    news_banner_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    link varchar(1000),
    title varchar,
    type varchar
);

DROP TABLE IF EXISTS news CASCADE;
CREATE TABLE IF NOT EXISTS news (
    news_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description varchar,
    created TIMESTAMP,
    title varchar,
    event_time TIMESTAMP,
    banner_id BIGINT REFERENCES news_banner(news_banner_id)
);

DROP TABLE IF EXISTS profile_seasons cascade;
CREATE TABLE IF NOT EXISTS profile_seasons (
    user_id BIGINT REFERENCES profile(user_id) ON DELETE CASCADE,
    season varchar(50)
);

DROP TABLE IF EXISTS profile_directions cascade;
CREATE TABLE IF NOT EXISTS profile_directions (
    user_id BIGINT REFERENCES profile(user_id) ON DELETE CASCADE,
    direction varchar(50)
);

DROP TABLE IF EXISTS documents;
CREATE TABLE IF NOT EXISTS documents (
    document_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title varchar(100),
    type varchar(50),
    link varchar(500)
);

DROP TABLE IF EXISTS resources;
CREATE TABLE IF NOT EXISTS resources (
    resource_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title varchar(100),
    type varchar(50),
    link varchar(500)
);