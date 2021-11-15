create table user(
	no int not null auto_increment,
    name varchar(45) not null,
    id varchar(45) not null,
    password varchar(45) not null,
    join_date date not null,
    primary key(no),
    unique key(id)
);
create table blog(
	no int not null,
    title varchar(200) not null,
    logo varchar(200) not null default '/images/default-logo.jpg',
    primary key(no),
    foreign key(no) references user(no) on delete cascade
);

create table category(
	no int not null auto_increment,
    name varchar(200) not null,
    `desc` text,
    is_default boolean not null default false,
    blog_no int not null,
    primary key(no),
    foreign key(blog_no) references blog(no) on delete cascade
);

create table post(
	no int not null auto_increment,
    title varchar(200) not null,
    contents text,
    reg_date datetime not null default now(),
    category_no int not null,
    primary key(no),
    foreign key(category_no) references category(no)
);