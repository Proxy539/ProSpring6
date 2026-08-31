CREATE TABLE singer (
       ID INT NOT NULL AUTO_INCREMENT
     , FIRST_NAME VARCHAR(60) NOT NULL
     , LAST_NAME VARCHAR(40) NOT NULL
     , BIRTH_DATE DATE
     , UNIQUE (FIRST_NAME, LAST_NAME)
     , PRIMARY KEY (ID)
);

insert into singer (id, first_name, last_name, birth_date) values (1, 'John', 'Mayer', '1977-10-16');
insert into singer (id, first_name, last_name, birth_date) values (2, 'John', 'Butler', '1975-04-01');
insert into singer (id, first_name, last_name, birth_date) values (3, 'Farrokh', 'Bulsara', '1946-09-05');
insert into singer (id, first_name, last_name, birth_date) values (4, 'Beyonce', 'Knowles-Carter', '1981-09-04');
