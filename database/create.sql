drop table if exists comment;
drop table if exists article_activity;
drop table if exists article;
drop table if exists "user";
drop table if exists destination;
drop table if exists activity;
drop table if exists user_role;

create table user_role(
    id   bigserial   primary key,
    name varchar(32) not null,

    check (name <> '')
);

create table "user"(
    id         bigserial    primary key,
    first_name varchar(64)  not null,
    last_name  varchar(64)  not null,
    email      varchar(320) not null unique,
    salt       char(24)     not null,
    password   char(44)     not null,
    role_id    bigint       not null references user_role(id),
    enabled    boolean      not null default true,

    check (first_name <> ''),
    check (last_name  <> ''),
    check (email      <> ''),
    check (salt       <> ''),
    check (password   <> '')
);


create table activity(
    id   bigserial    primary key,
    name varchar(256) not null unique,

    check (name <> '')
);

create table destination(
    id          bigserial    primary key,
    name        varchar(256) not null unique,
    description text         not null,

    check (name <> '')
);

create table article(
    id             bigserial    primary key,
    title          varchar(256) not null,
    content        text         not null,
    author         bigint       not null references "user"(id),
    destination_id bigint       not null references destination(id),
    created_at     date         not null default now(),
    visits         int          not null default 0,

    check (title   <> ''),
    check (content <> '')
);

create table article_activity(
    id          bigserial primary key,
    article_id  bigint not null references article(id),
    activity_id bigint not null references activity(id)
);

create table comment(
    id         bigserial primary key,
    content    text      not null,
    author_id  bigint    not null references "user"(id),
    article_id bigint    not null references article(id),
    created_at date      not null default now(),

    check (content <> '')
);
