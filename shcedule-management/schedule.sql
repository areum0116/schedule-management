create table schedule_list(
                              id int not null primary key auto_increment,
                              to_do varchar(100) not null,
                              manager varchar(20),
                              pw varchar(20) not null,
                              created_at datetime default current_timestamp,
                              updated_at timestamp default current_timestamp on update current_timestamp
);