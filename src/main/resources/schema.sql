create table if not exists photos
(
    id           int primary key not null auto_increment,
    file_name    varchar(255),
    content_type varchar(255),
    data         binary large object
);