create table if not exists stars (
id int(10) not null,
x int(10) not null,
y int(10) not null,
z int(10) not null,
type int(10) not null,
size int(10) not null,
name varchar(20) not null,
primary key (id)
);

insert into stars (id, x, y, z, type, size, name) values
(0,-7500,-7500,-7500,1,2,'TestStar0'),
(1,7500,-7500,-7500,1,2,'TestStar1'),
(2,-7500,7500,-7500,1,2,'TestStar2'),
(3,-7500,-7500,7500,1,2,'TestStar3'),
(4,7500,7500,-7500,1,2,'TestStar4'),
(5,7500,-7500,7500,1,2,'TestStar5'),
(6,-7500,7500,7500,1,2,'TestStar6'),
(7,7500,7500,7500,1,2,'TestStar7');