CREATE TABLE users (
                       id bigint NOT NULL AUTO_INCREMENT,
                       created_at datetime DEFAULT NULL,
                       last_modified_at datetime DEFAULT NULL,
                       email varchar(255) DEFAULT NULL,
                       name varchar(255) DEFAULT NULL,
                       nickname varchar(255) DEFAULT NULL,
                       user_role enum('ADMIN','USER') DEFAULT NULL,
                       password varchar(255) DEFAULT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE posts (
                       id bigint NOT NULL AUTO_INCREMENT,
                       created_at datetime DEFAULT NULL,
                       last_modified_at datetime DEFAULT NULL,
                       content text,
                       user_id bigint DEFAULT NULL,
                       PRIMARY KEY (id),

--                        KEY FK5lidm6cqbc7u4xhqpxm898qme (user_id),
                       CONSTRAINT FK5lidm6cqbc7u4xhqpxm898qme FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE comments (
    id bigint NOT NULL AUTO_INCREMENT,
    created_at datetime DEFAULT NULL,
    last_modified_at datetime DEFAULT NULL,
    content text,
    post_id bigint DEFAULT NULL,
    user_id bigint DEFAULT NULL,
    PRIMARY KEY (id),
--     KEY FKh4c7lvsc298whoyd4w9ta25cr (post_id),
--     KEY FK8omq0tc18jd43bu5tjh6jvraq (user_id),
    CONSTRAINT FK8omq0tc18jd43bu5tjh6jvraq FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FKh4c7lvsc298whoyd4w9ta25cr FOREIGN KEY (post_id) REFERENCES posts (id)
);


CREATE TABLE follows (
   id bigint NOT NULL AUTO_INCREMENT,
   follow_at datetime DEFAULT NULL,
   from_user_id bigint DEFAULT NULL,
   to_user_id bigint DEFAULT NULL,
   PRIMARY KEY (id),
--    KEY FKq3p7bn58m0d429tt9y998posw (from_user_id),
--    KEY FKeu9xok1bsf64gftb3jct86v2j (to_user_id),
   CONSTRAINT FKeu9xok1bsf64gftb3jct86v2j FOREIGN KEY (to_user_id) REFERENCES users (id),
   CONSTRAINT FKq3p7bn58m0d429tt9y998posw FOREIGN KEY (from_user_id) REFERENCES users (id)
);

CREATE TABLE likes_comment (
     id bigint NOT NULL AUTO_INCREMENT,
     like_at datetime DEFAULT NULL,
     comment_id bigint DEFAULT NULL,
     user_id bigint DEFAULT NULL,
     PRIMARY KEY (id),
--      KEY FKcl8gnxy7i8550a45l5rmwrx1y (comment_id),
--      KEY FKgh1t09wuupn26qk177kv4qgci (user_id),
     CONSTRAINT FKcl8gnxy7i8550a45l5rmwrx1y FOREIGN KEY (comment_id) REFERENCES comments (id),
     CONSTRAINT FKgh1t09wuupn26qk177kv4qgci FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE likes_post (
      id bigint NOT NULL AUTO_INCREMENT,
      like_at datetime DEFAULT NULL,
      post_id bigint DEFAULT NULL,
      user_id bigint DEFAULT NULL,
      PRIMARY KEY (id),
--       KEY FKbswmbb7u6nwr0d5e82pitdnch (post_id),
--       KEY FKmvakcttjv2lmx1ms4h46f4vfc (user_id),
      CONSTRAINT FKbswmbb7u6nwr0d5e82pitdnch FOREIGN KEY (post_id) REFERENCES posts (id),
      CONSTRAINT FKmvakcttjv2lmx1ms4h46f4vfc FOREIGN KEY (user_id) REFERENCES users (id)
);

