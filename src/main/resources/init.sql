create table `user` (
  username varchar(20) unique not null,
  password varchar(20) not null,
  id varchar(20),
  nickname varchar(20) not null,
  primary key(id)
);

insert into `user` (username, password, nickname,id) values ('admin', 'password', 'admin','23');