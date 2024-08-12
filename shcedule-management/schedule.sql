create table schedule_list(
                              id int not null primary key auto_increment,
                              to_do varchar(100) not null,
                              manager varchar(20),
                              pw varchar(20) not null,
                              created_at datetime default current_timestamp,
                              updated_at timestamp default current_timestamp on update current_timestamp
);



create table manager(
                        id int not null primary key auto_increment,
                        name varchar(100),
                        email varchar(150) not null,
                        created_at datetime default current_timestamp,
                        updated_at timestamp default current_timestamp on update current_timestamp
);

drop table schedule_list;

create table schedule_list(
                              id int not null primary key auto_increment,
                              to_do varchar(100) not null,
                              manager_id int,
                              pw varchar(20) not null,
                              created_at datetime default current_timestamp,
                              updated_at timestamp default current_timestamp on update current_timestamp,
                              foreign key (manager_id) references manager(id)
);
