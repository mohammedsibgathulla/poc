    create table artist
    (
       id integer not null,
       name varchar(255) not null,
       primary key(id)
    );

    create table album
    (
       id integer not null,
       title varchar(255) not null,
       yearOfRelease varchar(255),
       genres varchar(255),
       artist_id varchar(255),
       primary key(id),
       FOREIGN KEY (artist_id) REFERENCES artist
    );


    INSERT INTO artist (id, name) VALUES (10001,  'Ranga');
    INSERT INTO artist (id, name) VALUES (10002,  'James');