/*
    SQL Dialect : H2
    DB Version : 0.0.1.2
    Description : Added all other domain objects data test
 */


insert into CurvePoint (curveId, term, value)
values (63, 45.2, 63.31),
        (41, 98.3, 4.14),
        (85, 19.24, 85.27),
        (32, 64.78, 34.24);
        
insert into Rating (moodysRating, sandPRating,fitchRating,orderNumber)
values  ('moody1', 'sandP1', 'fitch1',1),
        ('moody2', 'sandP2', 'fitch2',2),
        ('moody3', 'sandP3', 'fitch3',3),
        ('moody4', 'sandP4', 'fitch4',4);

insert into RuleName (name, description, json, template,sqlStr, sqlPart)
values  ('name1','description1', 'json1', 'template1','sqlStr1', 'sqlPart1'),
        ('name2','description2', 'json2', 'template2','sqlStr2', 'sqlPart2'),
        ('name3', 'description3', 'json3', 'template3','sqlStr3', 'sqlPart3');

insert into Trade (account, type)
values  ('account_1','type_1'),
        ('account_2','type_2'),
        ('account_3','type_3'),
        ('account_4','type_4'),
        ('account_5','type_5'),
        ('account_6','type_6');

insert into Users (fullname, username, password,role)
values  ('User WithNameB','userB', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER'),
        ('User WithNameC','userC', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER');