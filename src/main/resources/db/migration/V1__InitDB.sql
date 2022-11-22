create sequence comment_id_seq start 1 increment 1;
create sequence post_id_seq start 1 increment 1;
create sequence role_id_seq start 1 increment 1;
create sequence subreddit_id_seq start 1 increment 1;
create sequence token_id_seq start 1 increment 1;
create sequence user_id_seq start 1 increment 1;
create sequence vote_id_seq start 1 increment 1;
create table comments (
       id int8 not null,
        date_created timestamp,
        text varchar(255),
        post_id int8,
        user_id int8,
        primary key (id)
    );
create table posts (
       id int8 not null,
        date_created date,
        post_name varchar(255),
        text varchar(255),
        url varchar(255),
        vote_count int4,
        subreddit_id int8,
        user_id int8,
        primary key (id)
    );
create table roles (
       id int8 not null,
        name varchar(255),
        primary key (id)
    );
create table subreddits (
       id int8 not null,
        date_created date,
        description varchar(255),
        name varchar(255),
        user_id int8,
        primary key (id)
    );
create table tokens (
       id int8 not null,
        date_created date,
        token varchar(255),
        user_id int8,
        primary key (id)
    );
create table users (
       id int8 not null,
        birthday date,
        country varchar(255),
        date_created date,
        email varchar(255),
        enabled boolean not null,
        name varchar(255),
        password varchar(255),
        surname varchar(255),
        username varchar(255),
        primary key (id)
    );
create table users_roles (
       user_id int8 not null,
        role_id int8 not null
    );
create table votes (
       id int8 not null,
        vote_type varchar(255),
        post_id int8,
        user_id int8,
        primary key (id)
    );
alter table users 
       add constraint user_email_uq unique (email);
alter table users 
       add constraint user_username_uq unique (username);
alter table comments 
       add constraint post_id_fk 
       foreign key (post_id) 
       references posts;
alter table comments 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
alter table posts 
       add constraint subreddit_id_fk 
       foreign key (subreddit_id) 
       references subreddits;
alter table posts 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
alter table subreddits 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
alter table tokens 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
alter table users_roles 
       add constraint role_id_fk 
       foreign key (role_id) 
       references roles;
alter table users_roles 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
alter table votes 
       add constraint post_id_fk 
       foreign key (post_id) 
       references posts;
alter table votes 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;