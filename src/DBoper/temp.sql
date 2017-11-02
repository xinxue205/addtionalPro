select * from trans_monitor_doubtaccount_day where  subsys_code='01' and stat_type='9' and type_code in ('01','02') and report_date||report_time>='2014-08-15020000';
select * from trans_monitor_doubtaccount_tmp;

select b.time_sec,a.trans_count,a.suc_count from time_section b left join (
select spare_str1,sum(trans_count) trans_count,sum(suc_count) suc_count from trans_monitor_doubtaccount_tmp group by spare_str1
) a on a.spare_str1=b.time_sec order by b.serial

select report_time,sum(trans_count),sum(suc_count) from trans_monitor_doubtaccount_tmp group by report_time order by report_time;

create table time_section (serial INTEGER, time_sec char(6), flag integer,  spare_int1 INTEGER,spare_int2 INTEGER,spare_str1 VARCHAR(128),spare_str2 VARCHAR(128) ,
	PRIMARY KEY (serial,flag) CONSTRAINT pk_time_section
);
insert into time_section (serial,time_sec,flag) values (0,'000000',0);
insert into time_section (serial,time_sec,flag) values (1,'000500',0);
insert into time_section (serial,time_sec,flag) values (2,'001000',0);
insert into time_section (serial,time_sec,flag) values (3,'001500',0);
insert into time_section (serial,time_sec,flag) values (4,'002000',0);
insert into time_section (serial,time_sec,flag) values (5,'002500',0);
insert into time_section (serial,time_sec,flag) values (6,'003000',0);
insert into time_section (serial,time_sec,flag) values (7,'003500',0);
insert into time_section (serial,time_sec,flag) values (8,'004000',0);
insert into time_section (serial,time_sec,flag) values (9,'004500',0);
insert into time_section (serial,time_sec,flag) values (10,'005000',0);
insert into time_section (serial,time_sec,flag) values (11,'005500',0);
insert into time_section (serial,time_sec,flag) values (12,'010000',0);
insert into time_section (serial,time_sec,flag) values (13,'010500',0);
insert into time_section (serial,time_sec,flag) values (14,'011000',0);
insert into time_section (serial,time_sec,flag) values (15,'011500',0);
insert into time_section (serial,time_sec,flag) values (16,'012000',0);
insert into time_section (serial,time_sec,flag) values (17,'012500',0);
insert into time_section (serial,time_sec,flag) values (18,'013000',0);
insert into time_section (serial,time_sec,flag) values (19,'013500',0);
insert into time_section (serial,time_sec,flag) values (20,'014000',0);
insert into time_section (serial,time_sec,flag) values (21,'014500',0);
insert into time_section (serial,time_sec,flag) values (22,'015000',0);
insert into time_section (serial,time_sec,flag) values (23,'015500',0);
insert into time_section (serial,time_sec,flag) values (24,'020000',0);
insert into time_section (serial,time_sec,flag) values (25,'020500',0);
insert into time_section (serial,time_sec,flag) values (26,'021000',0);
insert into time_section (serial,time_sec,flag) values (27,'021500',0);
insert into time_section (serial,time_sec,flag) values (28,'022000',0);
insert into time_section (serial,time_sec,flag) values (29,'022500',0);
insert into time_section (serial,time_sec,flag) values (30,'023000',0);
insert into time_section (serial,time_sec,flag) values (31,'023500',0);
insert into time_section (serial,time_sec,flag) values (32,'024000',0);
insert into time_section (serial,time_sec,flag) values (33,'024500',0);
insert into time_section (serial,time_sec,flag) values (34,'025000',0);
insert into time_section (serial,time_sec,flag) values (35,'025500',0);
insert into time_section (serial,time_sec,flag) values (36,'030000',0);
insert into time_section (serial,time_sec,flag) values (37,'030500',0);
insert into time_section (serial,time_sec,flag) values (38,'031000',0);
insert into time_section (serial,time_sec,flag) values (39,'031500',0);
insert into time_section (serial,time_sec,flag) values (40,'032000',0);
insert into time_section (serial,time_sec,flag) values (41,'032500',0);
insert into time_section (serial,time_sec,flag) values (42,'033000',0);
insert into time_section (serial,time_sec,flag) values (43,'033500',0);
insert into time_section (serial,time_sec,flag) values (44,'034000',0);
insert into time_section (serial,time_sec,flag) values (45,'034500',0);
insert into time_section (serial,time_sec,flag) values (46,'035000',0);
insert into time_section (serial,time_sec,flag) values (47,'035500',0);
insert into time_section (serial,time_sec,flag) values (48,'040000',0);
insert into time_section (serial,time_sec,flag) values (49,'040500',0);
insert into time_section (serial,time_sec,flag) values (50,'041000',0);
insert into time_section (serial,time_sec,flag) values (51,'041500',0);
insert into time_section (serial,time_sec,flag) values (52,'042000',0);
insert into time_section (serial,time_sec,flag) values (53,'042500',0);
insert into time_section (serial,time_sec,flag) values (54,'043000',0);
insert into time_section (serial,time_sec,flag) values (55,'043500',0);
insert into time_section (serial,time_sec,flag) values (56,'044000',0);
insert into time_section (serial,time_sec,flag) values (57,'044500',0);
insert into time_section (serial,time_sec,flag) values (58,'045000',0);
insert into time_section (serial,time_sec,flag) values (59,'045500',0);
insert into time_section (serial,time_sec,flag) values (60,'050000',0);
insert into time_section (serial,time_sec,flag) values (61,'050500',0);
insert into time_section (serial,time_sec,flag) values (62,'051000',0);
insert into time_section (serial,time_sec,flag) values (63,'051500',0);
insert into time_section (serial,time_sec,flag) values (64,'052000',0);
insert into time_section (serial,time_sec,flag) values (65,'052500',0);
insert into time_section (serial,time_sec,flag) values (66,'053000',0);
insert into time_section (serial,time_sec,flag) values (67,'053500',0);
insert into time_section (serial,time_sec,flag) values (68,'054000',0);
insert into time_section (serial,time_sec,flag) values (69,'054500',0);
insert into time_section (serial,time_sec,flag) values (70,'055000',0);
insert into time_section (serial,time_sec,flag) values (71,'055500',0);
insert into time_section (serial,time_sec,flag) values (72,'060000',0);
insert into time_section (serial,time_sec,flag) values (73,'060500',0);
insert into time_section (serial,time_sec,flag) values (74,'061000',0);
insert into time_section (serial,time_sec,flag) values (75,'061500',0);
insert into time_section (serial,time_sec,flag) values (76,'062000',0);
insert into time_section (serial,time_sec,flag) values (77,'062500',0);
insert into time_section (serial,time_sec,flag) values (78,'063000',0);
insert into time_section (serial,time_sec,flag) values (79,'063500',0);
insert into time_section (serial,time_sec,flag) values (80,'064000',0);
insert into time_section (serial,time_sec,flag) values (81,'064500',0);
insert into time_section (serial,time_sec,flag) values (82,'065000',0);
insert into time_section (serial,time_sec,flag) values (83,'065500',0);
insert into time_section (serial,time_sec,flag) values (84,'070000',0);
insert into time_section (serial,time_sec,flag) values (85,'070500',0);
insert into time_section (serial,time_sec,flag) values (86,'071000',0);
insert into time_section (serial,time_sec,flag) values (87,'071500',0);
insert into time_section (serial,time_sec,flag) values (88,'072000',0);
insert into time_section (serial,time_sec,flag) values (89,'072500',0);
insert into time_section (serial,time_sec,flag) values (90,'073000',0);
insert into time_section (serial,time_sec,flag) values (91,'073500',0);
insert into time_section (serial,time_sec,flag) values (92,'074000',0);
insert into time_section (serial,time_sec,flag) values (93,'074500',0);
insert into time_section (serial,time_sec,flag) values (94,'075000',0);
insert into time_section (serial,time_sec,flag) values (95,'075500',0);
insert into time_section (serial,time_sec,flag) values (96,'080000',0);
insert into time_section (serial,time_sec,flag) values (97,'080500',0);
insert into time_section (serial,time_sec,flag) values (98,'081000',0);
insert into time_section (serial,time_sec,flag) values (99,'081500',0);
insert into time_section (serial,time_sec,flag) values (100,'082000',0);
insert into time_section (serial,time_sec,flag) values (101,'082500',0);
insert into time_section (serial,time_sec,flag) values (102,'083000',0);
insert into time_section (serial,time_sec,flag) values (103,'083500',0);
insert into time_section (serial,time_sec,flag) values (104,'084000',0);
insert into time_section (serial,time_sec,flag) values (105,'084500',0);
insert into time_section (serial,time_sec,flag) values (106,'085000',0);
insert into time_section (serial,time_sec,flag) values (107,'085500',0);
insert into time_section (serial,time_sec,flag) values (108,'090000',0);
insert into time_section (serial,time_sec,flag) values (109,'090500',0);
insert into time_section (serial,time_sec,flag) values (110,'091000',0);
insert into time_section (serial,time_sec,flag) values (111,'091500',0);
insert into time_section (serial,time_sec,flag) values (112,'092000',0);
insert into time_section (serial,time_sec,flag) values (113,'092500',0);
insert into time_section (serial,time_sec,flag) values (114,'093000',0);
insert into time_section (serial,time_sec,flag) values (115,'093500',0);
insert into time_section (serial,time_sec,flag) values (116,'094000',0);
insert into time_section (serial,time_sec,flag) values (117,'094500',0);
insert into time_section (serial,time_sec,flag) values (118,'095000',0);
insert into time_section (serial,time_sec,flag) values (119,'095500',0);
insert into time_section (serial,time_sec,flag) values (120,'100000',0);
insert into time_section (serial,time_sec,flag) values (121,'100500',0);
insert into time_section (serial,time_sec,flag) values (122,'101000',0);
insert into time_section (serial,time_sec,flag) values (123,'101500',0);
insert into time_section (serial,time_sec,flag) values (124,'102000',0);
insert into time_section (serial,time_sec,flag) values (125,'102500',0);
insert into time_section (serial,time_sec,flag) values (126,'103000',0);
insert into time_section (serial,time_sec,flag) values (127,'103500',0);
insert into time_section (serial,time_sec,flag) values (128,'104000',0);
insert into time_section (serial,time_sec,flag) values (129,'104500',0);
insert into time_section (serial,time_sec,flag) values (130,'105000',0);
insert into time_section (serial,time_sec,flag) values (131,'105500',0);
insert into time_section (serial,time_sec,flag) values (132,'110000',0);
insert into time_section (serial,time_sec,flag) values (133,'110500',0);
insert into time_section (serial,time_sec,flag) values (134,'111000',0);
insert into time_section (serial,time_sec,flag) values (135,'111500',0);
insert into time_section (serial,time_sec,flag) values (136,'112000',0);
insert into time_section (serial,time_sec,flag) values (137,'112500',0);
insert into time_section (serial,time_sec,flag) values (138,'113000',0);
insert into time_section (serial,time_sec,flag) values (139,'113500',0);
insert into time_section (serial,time_sec,flag) values (140,'114000',0);
insert into time_section (serial,time_sec,flag) values (141,'114500',0);
insert into time_section (serial,time_sec,flag) values (142,'115000',0);
insert into time_section (serial,time_sec,flag) values (143,'115500',0);
insert into time_section (serial,time_sec,flag) values (144,'120000',0);
insert into time_section (serial,time_sec,flag) values (145,'120500',0);
insert into time_section (serial,time_sec,flag) values (146,'121000',0);
insert into time_section (serial,time_sec,flag) values (147,'121500',0);
insert into time_section (serial,time_sec,flag) values (148,'122000',0);
insert into time_section (serial,time_sec,flag) values (149,'122500',0);
insert into time_section (serial,time_sec,flag) values (150,'123000',0);
insert into time_section (serial,time_sec,flag) values (151,'123500',0);
insert into time_section (serial,time_sec,flag) values (152,'124000',0);
insert into time_section (serial,time_sec,flag) values (153,'124500',0);
insert into time_section (serial,time_sec,flag) values (154,'125000',0);
insert into time_section (serial,time_sec,flag) values (155,'125500',0);
insert into time_section (serial,time_sec,flag) values (156,'130000',0);
insert into time_section (serial,time_sec,flag) values (157,'130500',0);
insert into time_section (serial,time_sec,flag) values (158,'131000',0);
insert into time_section (serial,time_sec,flag) values (159,'131500',0);
insert into time_section (serial,time_sec,flag) values (160,'132000',0);
insert into time_section (serial,time_sec,flag) values (161,'132500',0);
insert into time_section (serial,time_sec,flag) values (162,'133000',0);
insert into time_section (serial,time_sec,flag) values (163,'133500',0);
insert into time_section (serial,time_sec,flag) values (164,'134000',0);
insert into time_section (serial,time_sec,flag) values (165,'134500',0);
insert into time_section (serial,time_sec,flag) values (166,'135000',0);
insert into time_section (serial,time_sec,flag) values (167,'135500',0);
insert into time_section (serial,time_sec,flag) values (168,'140000',0);
insert into time_section (serial,time_sec,flag) values (169,'140500',0);
insert into time_section (serial,time_sec,flag) values (170,'141000',0);
insert into time_section (serial,time_sec,flag) values (171,'141500',0);
insert into time_section (serial,time_sec,flag) values (172,'142000',0);
insert into time_section (serial,time_sec,flag) values (173,'142500',0);
insert into time_section (serial,time_sec,flag) values (174,'143000',0);
insert into time_section (serial,time_sec,flag) values (175,'143500',0);
insert into time_section (serial,time_sec,flag) values (176,'144000',0);
insert into time_section (serial,time_sec,flag) values (177,'144500',0);
insert into time_section (serial,time_sec,flag) values (178,'145000',0);
insert into time_section (serial,time_sec,flag) values (179,'145500',0);
insert into time_section (serial,time_sec,flag) values (180,'150000',0);
insert into time_section (serial,time_sec,flag) values (181,'150500',0);
insert into time_section (serial,time_sec,flag) values (182,'151000',0);
insert into time_section (serial,time_sec,flag) values (183,'151500',0);
insert into time_section (serial,time_sec,flag) values (184,'152000',0);
insert into time_section (serial,time_sec,flag) values (185,'152500',0);
insert into time_section (serial,time_sec,flag) values (186,'153000',0);
insert into time_section (serial,time_sec,flag) values (187,'153500',0);
insert into time_section (serial,time_sec,flag) values (188,'154000',0);
insert into time_section (serial,time_sec,flag) values (189,'154500',0);
insert into time_section (serial,time_sec,flag) values (190,'155000',0);
insert into time_section (serial,time_sec,flag) values (191,'155500',0);
insert into time_section (serial,time_sec,flag) values (192,'160000',0);
insert into time_section (serial,time_sec,flag) values (193,'160500',0);
insert into time_section (serial,time_sec,flag) values (194,'161000',0);
insert into time_section (serial,time_sec,flag) values (195,'161500',0);
insert into time_section (serial,time_sec,flag) values (196,'162000',0);
insert into time_section (serial,time_sec,flag) values (197,'162500',0);
insert into time_section (serial,time_sec,flag) values (198,'163000',0);
insert into time_section (serial,time_sec,flag) values (199,'163500',0);
insert into time_section (serial,time_sec,flag) values (200,'164000',0);
insert into time_section (serial,time_sec,flag) values (201,'164500',0);
insert into time_section (serial,time_sec,flag) values (202,'165000',0);
insert into time_section (serial,time_sec,flag) values (203,'165500',0);
insert into time_section (serial,time_sec,flag) values (204,'170000',0);
insert into time_section (serial,time_sec,flag) values (205,'170500',0);
insert into time_section (serial,time_sec,flag) values (206,'171000',0);
insert into time_section (serial,time_sec,flag) values (207,'171500',0);
insert into time_section (serial,time_sec,flag) values (208,'172000',0);
insert into time_section (serial,time_sec,flag) values (209,'172500',0);
insert into time_section (serial,time_sec,flag) values (210,'173000',0);
insert into time_section (serial,time_sec,flag) values (211,'173500',0);
insert into time_section (serial,time_sec,flag) values (212,'174000',0);
insert into time_section (serial,time_sec,flag) values (213,'174500',0);
insert into time_section (serial,time_sec,flag) values (214,'175000',0);
insert into time_section (serial,time_sec,flag) values (215,'175500',0);
insert into time_section (serial,time_sec,flag) values (216,'180000',0);
insert into time_section (serial,time_sec,flag) values (217,'180500',0);
insert into time_section (serial,time_sec,flag) values (218,'181000',0);
insert into time_section (serial,time_sec,flag) values (219,'181500',0);
insert into time_section (serial,time_sec,flag) values (220,'182000',0);
insert into time_section (serial,time_sec,flag) values (221,'182500',0);
insert into time_section (serial,time_sec,flag) values (222,'183000',0);
insert into time_section (serial,time_sec,flag) values (223,'183500',0);
insert into time_section (serial,time_sec,flag) values (224,'184000',0);
insert into time_section (serial,time_sec,flag) values (225,'184500',0);
insert into time_section (serial,time_sec,flag) values (226,'185000',0);
insert into time_section (serial,time_sec,flag) values (227,'185500',0);
insert into time_section (serial,time_sec,flag) values (228,'190000',0);
insert into time_section (serial,time_sec,flag) values (229,'190500',0);
insert into time_section (serial,time_sec,flag) values (230,'191000',0);
insert into time_section (serial,time_sec,flag) values (231,'191500',0);
insert into time_section (serial,time_sec,flag) values (232,'192000',0);
insert into time_section (serial,time_sec,flag) values (233,'192500',0);
insert into time_section (serial,time_sec,flag) values (234,'193000',0);
insert into time_section (serial,time_sec,flag) values (235,'193500',0);
insert into time_section (serial,time_sec,flag) values (236,'194000',0);
insert into time_section (serial,time_sec,flag) values (237,'194500',0);
insert into time_section (serial,time_sec,flag) values (238,'195000',0);
insert into time_section (serial,time_sec,flag) values (239,'195500',0);
insert into time_section (serial,time_sec,flag) values (240,'200000',0);
insert into time_section (serial,time_sec,flag) values (241,'200500',0);
insert into time_section (serial,time_sec,flag) values (242,'201000',0);
insert into time_section (serial,time_sec,flag) values (243,'201500',0);
insert into time_section (serial,time_sec,flag) values (244,'202000',0);
insert into time_section (serial,time_sec,flag) values (245,'202500',0);
insert into time_section (serial,time_sec,flag) values (246,'203000',0);
insert into time_section (serial,time_sec,flag) values (247,'203500',0);
insert into time_section (serial,time_sec,flag) values (248,'204000',0);
insert into time_section (serial,time_sec,flag) values (249,'204500',0);
insert into time_section (serial,time_sec,flag) values (250,'205000',0);
insert into time_section (serial,time_sec,flag) values (251,'205500',0);
insert into time_section (serial,time_sec,flag) values (252,'210000',0);
insert into time_section (serial,time_sec,flag) values (253,'210500',0);
insert into time_section (serial,time_sec,flag) values (254,'211000',0);
insert into time_section (serial,time_sec,flag) values (255,'211500',0);
insert into time_section (serial,time_sec,flag) values (256,'212000',0);
insert into time_section (serial,time_sec,flag) values (257,'212500',0);
insert into time_section (serial,time_sec,flag) values (258,'213000',0);
insert into time_section (serial,time_sec,flag) values (259,'213500',0);
insert into time_section (serial,time_sec,flag) values (260,'214000',0);
insert into time_section (serial,time_sec,flag) values (261,'214500',0);
insert into time_section (serial,time_sec,flag) values (262,'215000',0);
insert into time_section (serial,time_sec,flag) values (263,'215500',0);
insert into time_section (serial,time_sec,flag) values (264,'220000',0);
insert into time_section (serial,time_sec,flag) values (265,'220500',0);
insert into time_section (serial,time_sec,flag) values (266,'221000',0);
insert into time_section (serial,time_sec,flag) values (267,'221500',0);
insert into time_section (serial,time_sec,flag) values (268,'222000',0);
insert into time_section (serial,time_sec,flag) values (269,'222500',0);
insert into time_section (serial,time_sec,flag) values (270,'223000',0);
insert into time_section (serial,time_sec,flag) values (271,'223500',0);
insert into time_section (serial,time_sec,flag) values (272,'224000',0);
insert into time_section (serial,time_sec,flag) values (273,'224500',0);
insert into time_section (serial,time_sec,flag) values (274,'225000',0);
insert into time_section (serial,time_sec,flag) values (275,'225500',0);
insert into time_section (serial,time_sec,flag) values (276,'230000',0);
insert into time_section (serial,time_sec,flag) values (277,'230500',0);
insert into time_section (serial,time_sec,flag) values (278,'231000',0);
insert into time_section (serial,time_sec,flag) values (279,'231500',0);
insert into time_section (serial,time_sec,flag) values (280,'232000',0);
insert into time_section (serial,time_sec,flag) values (281,'232500',0);
insert into time_section (serial,time_sec,flag) values (282,'233000',0);
insert into time_section (serial,time_sec,flag) values (283,'233500',0);
insert into time_section (serial,time_sec,flag) values (284,'234000',0);
insert into time_section (serial,time_sec,flag) values (285,'234500',0);
insert into time_section (serial,time_sec,flag) values (286,'235000',0);
insert into time_section (serial,time_sec,flag) values (287,'235500',0);

select * from column_list

CREATE TABLE
    report_doubtaccount
    (										
        subsys_code CHAR(5),
        stat_type INTEGER,
        type_code VARCHAR(32),
        in_sys_code VARCHAR(32),
        out_sys_code VARCHAR(32),
        report_date DATE,
        report_time CHAR(6),
        trans_count INTEGER,
        suc_count INTEGER,
        fail_count INTEGER,
        timeout_count INTEGER,
        sys_rej_count INTEGER,
        overtime_count INTEGER,
        bal_count INTEGER,
        proc_time FLOAT,
        resp_time FLOAT,
        tot_agent VARCHAR(8),
        spare_int1 INTEGER,
        spare_int2 INTEGER,
        spare_str1 VARCHAR(128),
        spare_str2 VARCHAR(128),
        PRIMARY KEY (subsys_code, stat_type, type_code, in_sys_code, out_sys_code, report_date, report_time, tot_agent) CONSTRAINT pk_report_doubtaccount
    );



update trans_monitor_doubtaccount_tmp set spare_str1='000000' where report_time>='000000' and report_time<'000500';
update trans_monitor_doubtaccount_tmp set spare_str1='000500' where report_time>='000500' and report_time<'001000';
update trans_monitor_doubtaccount_tmp set spare_str1='001000' where report_time>='001000' and report_time<'001500';
update trans_monitor_doubtaccount_tmp set spare_str1='001500' where report_time>='001500' and report_time<'002000';
update trans_monitor_doubtaccount_tmp set spare_str1='002000' where report_time>='002000' and report_time<'002500';
update trans_monitor_doubtaccount_tmp set spare_str1='002500' where report_time>='002500' and report_time<'003000';
update trans_monitor_doubtaccount_tmp set spare_str1='003000' where report_time>='003000' and report_time<'003500';
update trans_monitor_doubtaccount_tmp set spare_str1='003500' where report_time>='003500' and report_time<'004000';
update trans_monitor_doubtaccount_tmp set spare_str1='004000' where report_time>='004000' and report_time<'004500';
update trans_monitor_doubtaccount_tmp set spare_str1='004500' where report_time>='004500' and report_time<'005000';
update trans_monitor_doubtaccount_tmp set spare_str1='005000' where report_time>='005000' and report_time<'005500';
update trans_monitor_doubtaccount_tmp set spare_str1='005500' where report_time>='005500' and report_time<'010000';
update trans_monitor_doubtaccount_tmp set spare_str1='010000' where report_time>='010000' and report_time<'010500';
update trans_monitor_doubtaccount_tmp set spare_str1='010500' where report_time>='010500' and report_time<'011000';
update trans_monitor_doubtaccount_tmp set spare_str1='011000' where report_time>='011000' and report_time<'011500';
update trans_monitor_doubtaccount_tmp set spare_str1='011500' where report_time>='011500' and report_time<'012000';
update trans_monitor_doubtaccount_tmp set spare_str1='012000' where report_time>='012000' and report_time<'012500';
update trans_monitor_doubtaccount_tmp set spare_str1='012500' where report_time>='012500' and report_time<'013000';
update trans_monitor_doubtaccount_tmp set spare_str1='013000' where report_time>='013000' and report_time<'013500';
update trans_monitor_doubtaccount_tmp set spare_str1='013500' where report_time>='013500' and report_time<'014000';
update trans_monitor_doubtaccount_tmp set spare_str1='014000' where report_time>='014000' and report_time<'014500';
update trans_monitor_doubtaccount_tmp set spare_str1='014500' where report_time>='014500' and report_time<'015000';
update trans_monitor_doubtaccount_tmp set spare_str1='015000' where report_time>='015000' and report_time<'015500';
update trans_monitor_doubtaccount_tmp set spare_str1='015500' where report_time>='015500' and report_time<'020000';
update trans_monitor_doubtaccount_tmp set spare_str1='020000' where report_time>='020000' and report_time<'020500';
update trans_monitor_doubtaccount_tmp set spare_str1='020500' where report_time>='020500' and report_time<'021000';
update trans_monitor_doubtaccount_tmp set spare_str1='021000' where report_time>='021000' and report_time<'021500';
update trans_monitor_doubtaccount_tmp set spare_str1='021500' where report_time>='021500' and report_time<'022000';
update trans_monitor_doubtaccount_tmp set spare_str1='022000' where report_time>='022000' and report_time<'022500';
update trans_monitor_doubtaccount_tmp set spare_str1='022500' where report_time>='022500' and report_time<'023000';
update trans_monitor_doubtaccount_tmp set spare_str1='023000' where report_time>='023000' and report_time<'023500';
update trans_monitor_doubtaccount_tmp set spare_str1='023500' where report_time>='023500' and report_time<'024000';
update trans_monitor_doubtaccount_tmp set spare_str1='024000' where report_time>='024000' and report_time<'024500';
update trans_monitor_doubtaccount_tmp set spare_str1='024500' where report_time>='024500' and report_time<'025000';
update trans_monitor_doubtaccount_tmp set spare_str1='025000' where report_time>='025000' and report_time<'025500';
update trans_monitor_doubtaccount_tmp set spare_str1='025500' where report_time>='025500' and report_time<'030000';
update trans_monitor_doubtaccount_tmp set spare_str1='030000' where report_time>='030000' and report_time<'030500';
update trans_monitor_doubtaccount_tmp set spare_str1='030500' where report_time>='030500' and report_time<'031000';
update trans_monitor_doubtaccount_tmp set spare_str1='031000' where report_time>='031000' and report_time<'031500';
update trans_monitor_doubtaccount_tmp set spare_str1='031500' where report_time>='031500' and report_time<'032000';
update trans_monitor_doubtaccount_tmp set spare_str1='032000' where report_time>='032000' and report_time<'032500';
update trans_monitor_doubtaccount_tmp set spare_str1='032500' where report_time>='032500' and report_time<'033000';
update trans_monitor_doubtaccount_tmp set spare_str1='033000' where report_time>='033000' and report_time<'033500';
update trans_monitor_doubtaccount_tmp set spare_str1='033500' where report_time>='033500' and report_time<'034000';
update trans_monitor_doubtaccount_tmp set spare_str1='034000' where report_time>='034000' and report_time<'034500';
update trans_monitor_doubtaccount_tmp set spare_str1='034500' where report_time>='034500' and report_time<'035000';
update trans_monitor_doubtaccount_tmp set spare_str1='035000' where report_time>='035000' and report_time<'035500';
update trans_monitor_doubtaccount_tmp set spare_str1='035500' where report_time>='035500' and report_time<'040000';
update trans_monitor_doubtaccount_tmp set spare_str1='040000' where report_time>='040000' and report_time<'040500';
update trans_monitor_doubtaccount_tmp set spare_str1='040500' where report_time>='040500' and report_time<'041000';
update trans_monitor_doubtaccount_tmp set spare_str1='041000' where report_time>='041000' and report_time<'041500';
update trans_monitor_doubtaccount_tmp set spare_str1='041500' where report_time>='041500' and report_time<'042000';
update trans_monitor_doubtaccount_tmp set spare_str1='042000' where report_time>='042000' and report_time<'042500';
update trans_monitor_doubtaccount_tmp set spare_str1='042500' where report_time>='042500' and report_time<'043000';
update trans_monitor_doubtaccount_tmp set spare_str1='043000' where report_time>='043000' and report_time<'043500';
update trans_monitor_doubtaccount_tmp set spare_str1='043500' where report_time>='043500' and report_time<'044000';
update trans_monitor_doubtaccount_tmp set spare_str1='044000' where report_time>='044000' and report_time<'044500';
update trans_monitor_doubtaccount_tmp set spare_str1='044500' where report_time>='044500' and report_time<'045000';
update trans_monitor_doubtaccount_tmp set spare_str1='045000' where report_time>='045000' and report_time<'045500';
update trans_monitor_doubtaccount_tmp set spare_str1='045500' where report_time>='045500' and report_time<'050000';
update trans_monitor_doubtaccount_tmp set spare_str1='050000' where report_time>='050000' and report_time<'050500';
update trans_monitor_doubtaccount_tmp set spare_str1='050500' where report_time>='050500' and report_time<'051000';
update trans_monitor_doubtaccount_tmp set spare_str1='051000' where report_time>='051000' and report_time<'051500';
update trans_monitor_doubtaccount_tmp set spare_str1='051500' where report_time>='051500' and report_time<'052000';
update trans_monitor_doubtaccount_tmp set spare_str1='052000' where report_time>='052000' and report_time<'052500';
update trans_monitor_doubtaccount_tmp set spare_str1='052500' where report_time>='052500' and report_time<'053000';
update trans_monitor_doubtaccount_tmp set spare_str1='053000' where report_time>='053000' and report_time<'053500';
update trans_monitor_doubtaccount_tmp set spare_str1='053500' where report_time>='053500' and report_time<'054000';
update trans_monitor_doubtaccount_tmp set spare_str1='054000' where report_time>='054000' and report_time<'054500';
update trans_monitor_doubtaccount_tmp set spare_str1='054500' where report_time>='054500' and report_time<'055000';
update trans_monitor_doubtaccount_tmp set spare_str1='055000' where report_time>='055000' and report_time<'055500';
update trans_monitor_doubtaccount_tmp set spare_str1='055500' where report_time>='055500' and report_time<'060000';
update trans_monitor_doubtaccount_tmp set spare_str1='060000' where report_time>='060000' and report_time<'060500';
update trans_monitor_doubtaccount_tmp set spare_str1='060500' where report_time>='060500' and report_time<'061000';
update trans_monitor_doubtaccount_tmp set spare_str1='061000' where report_time>='061000' and report_time<'061500';
update trans_monitor_doubtaccount_tmp set spare_str1='061500' where report_time>='061500' and report_time<'062000';
update trans_monitor_doubtaccount_tmp set spare_str1='062000' where report_time>='062000' and report_time<'062500';
update trans_monitor_doubtaccount_tmp set spare_str1='062500' where report_time>='062500' and report_time<'063000';
update trans_monitor_doubtaccount_tmp set spare_str1='063000' where report_time>='063000' and report_time<'063500';
update trans_monitor_doubtaccount_tmp set spare_str1='063500' where report_time>='063500' and report_time<'064000';
update trans_monitor_doubtaccount_tmp set spare_str1='064000' where report_time>='064000' and report_time<'064500';
update trans_monitor_doubtaccount_tmp set spare_str1='064500' where report_time>='064500' and report_time<'065000';
update trans_monitor_doubtaccount_tmp set spare_str1='065000' where report_time>='065000' and report_time<'065500';
update trans_monitor_doubtaccount_tmp set spare_str1='065500' where report_time>='065500' and report_time<'070000';
update trans_monitor_doubtaccount_tmp set spare_str1='070000' where report_time>='070000' and report_time<'070500';
update trans_monitor_doubtaccount_tmp set spare_str1='070500' where report_time>='070500' and report_time<'071000';
update trans_monitor_doubtaccount_tmp set spare_str1='071000' where report_time>='071000' and report_time<'071500';
update trans_monitor_doubtaccount_tmp set spare_str1='071500' where report_time>='071500' and report_time<'072000';
update trans_monitor_doubtaccount_tmp set spare_str1='072000' where report_time>='072000' and report_time<'072500';
update trans_monitor_doubtaccount_tmp set spare_str1='072500' where report_time>='072500' and report_time<'073000';
update trans_monitor_doubtaccount_tmp set spare_str1='073000' where report_time>='073000' and report_time<'073500';
update trans_monitor_doubtaccount_tmp set spare_str1='073500' where report_time>='073500' and report_time<'074000';
update trans_monitor_doubtaccount_tmp set spare_str1='074000' where report_time>='074000' and report_time<'074500';
update trans_monitor_doubtaccount_tmp set spare_str1='074500' where report_time>='074500' and report_time<'075000';
update trans_monitor_doubtaccount_tmp set spare_str1='075000' where report_time>='075000' and report_time<'075500';
update trans_monitor_doubtaccount_tmp set spare_str1='075500' where report_time>='075500' and report_time<'080000';
update trans_monitor_doubtaccount_tmp set spare_str1='080000' where report_time>='080000' and report_time<'080500';
update trans_monitor_doubtaccount_tmp set spare_str1='080500' where report_time>='080500' and report_time<'081000';
update trans_monitor_doubtaccount_tmp set spare_str1='081000' where report_time>='081000' and report_time<'081500';
update trans_monitor_doubtaccount_tmp set spare_str1='081500' where report_time>='081500' and report_time<'082000';
update trans_monitor_doubtaccount_tmp set spare_str1='082000' where report_time>='082000' and report_time<'082500';
update trans_monitor_doubtaccount_tmp set spare_str1='082500' where report_time>='082500' and report_time<'083000';
update trans_monitor_doubtaccount_tmp set spare_str1='083000' where report_time>='083000' and report_time<'083500';
update trans_monitor_doubtaccount_tmp set spare_str1='083500' where report_time>='083500' and report_time<'084000';
update trans_monitor_doubtaccount_tmp set spare_str1='084000' where report_time>='084000' and report_time<'084500';
update trans_monitor_doubtaccount_tmp set spare_str1='084500' where report_time>='084500' and report_time<'085000';
update trans_monitor_doubtaccount_tmp set spare_str1='085000' where report_time>='085000' and report_time<'085500';
update trans_monitor_doubtaccount_tmp set spare_str1='085500' where report_time>='085500' and report_time<'090000';
update trans_monitor_doubtaccount_tmp set spare_str1='090000' where report_time>='090000' and report_time<'090500';
update trans_monitor_doubtaccount_tmp set spare_str1='090500' where report_time>='090500' and report_time<'091000';
update trans_monitor_doubtaccount_tmp set spare_str1='091000' where report_time>='091000' and report_time<'091500';
update trans_monitor_doubtaccount_tmp set spare_str1='091500' where report_time>='091500' and report_time<'092000';
update trans_monitor_doubtaccount_tmp set spare_str1='092000' where report_time>='092000' and report_time<'092500';
update trans_monitor_doubtaccount_tmp set spare_str1='092500' where report_time>='092500' and report_time<'093000';
update trans_monitor_doubtaccount_tmp set spare_str1='093000' where report_time>='093000' and report_time<'093500';
update trans_monitor_doubtaccount_tmp set spare_str1='093500' where report_time>='093500' and report_time<'094000';
update trans_monitor_doubtaccount_tmp set spare_str1='094000' where report_time>='094000' and report_time<'094500';
update trans_monitor_doubtaccount_tmp set spare_str1='094500' where report_time>='094500' and report_time<'095000';
update trans_monitor_doubtaccount_tmp set spare_str1='095000' where report_time>='095000' and report_time<'095500';
update trans_monitor_doubtaccount_tmp set spare_str1='095500' where report_time>='095500' and report_time<'100000';
update trans_monitor_doubtaccount_tmp set spare_str1='100000' where report_time>='100000' and report_time<'100500';
update trans_monitor_doubtaccount_tmp set spare_str1='100500' where report_time>='100500' and report_time<'101000';
update trans_monitor_doubtaccount_tmp set spare_str1='101000' where report_time>='101000' and report_time<'101500';
update trans_monitor_doubtaccount_tmp set spare_str1='101500' where report_time>='101500' and report_time<'102000';
update trans_monitor_doubtaccount_tmp set spare_str1='102000' where report_time>='102000' and report_time<'102500';
update trans_monitor_doubtaccount_tmp set spare_str1='102500' where report_time>='102500' and report_time<'103000';
update trans_monitor_doubtaccount_tmp set spare_str1='103000' where report_time>='103000' and report_time<'103500';
update trans_monitor_doubtaccount_tmp set spare_str1='103500' where report_time>='103500' and report_time<'104000';
update trans_monitor_doubtaccount_tmp set spare_str1='104000' where report_time>='104000' and report_time<'104500';
update trans_monitor_doubtaccount_tmp set spare_str1='104500' where report_time>='104500' and report_time<'105000';
update trans_monitor_doubtaccount_tmp set spare_str1='105000' where report_time>='105000' and report_time<'105500';
update trans_monitor_doubtaccount_tmp set spare_str1='105500' where report_time>='105500' and report_time<'110000';
update trans_monitor_doubtaccount_tmp set spare_str1='110000' where report_time>='110000' and report_time<'110500';
update trans_monitor_doubtaccount_tmp set spare_str1='110500' where report_time>='110500' and report_time<'111000';
update trans_monitor_doubtaccount_tmp set spare_str1='111000' where report_time>='111000' and report_time<'111500';
update trans_monitor_doubtaccount_tmp set spare_str1='111500' where report_time>='111500' and report_time<'112000';
update trans_monitor_doubtaccount_tmp set spare_str1='112000' where report_time>='112000' and report_time<'112500';
update trans_monitor_doubtaccount_tmp set spare_str1='112500' where report_time>='112500' and report_time<'113000';
update trans_monitor_doubtaccount_tmp set spare_str1='113000' where report_time>='113000' and report_time<'113500';
update trans_monitor_doubtaccount_tmp set spare_str1='113500' where report_time>='113500' and report_time<'114000';
update trans_monitor_doubtaccount_tmp set spare_str1='114000' where report_time>='114000' and report_time<'114500';
update trans_monitor_doubtaccount_tmp set spare_str1='114500' where report_time>='114500' and report_time<'115000';
update trans_monitor_doubtaccount_tmp set spare_str1='115000' where report_time>='115000' and report_time<'115500';
update trans_monitor_doubtaccount_tmp set spare_str1='115500' where report_time>='115500' and report_time<'120000';
update trans_monitor_doubtaccount_tmp set spare_str1='120000' where report_time>='120000' and report_time<'120500';
update trans_monitor_doubtaccount_tmp set spare_str1='120500' where report_time>='120500' and report_time<'121000';
update trans_monitor_doubtaccount_tmp set spare_str1='121000' where report_time>='121000' and report_time<'121500';
update trans_monitor_doubtaccount_tmp set spare_str1='121500' where report_time>='121500' and report_time<'122000';
update trans_monitor_doubtaccount_tmp set spare_str1='122000' where report_time>='122000' and report_time<'122500';
update trans_monitor_doubtaccount_tmp set spare_str1='122500' where report_time>='122500' and report_time<'123000';
update trans_monitor_doubtaccount_tmp set spare_str1='123000' where report_time>='123000' and report_time<'123500';
update trans_monitor_doubtaccount_tmp set spare_str1='123500' where report_time>='123500' and report_time<'124000';
update trans_monitor_doubtaccount_tmp set spare_str1='124000' where report_time>='124000' and report_time<'124500';
update trans_monitor_doubtaccount_tmp set spare_str1='124500' where report_time>='124500' and report_time<'125000';
update trans_monitor_doubtaccount_tmp set spare_str1='125000' where report_time>='125000' and report_time<'125500';
update trans_monitor_doubtaccount_tmp set spare_str1='125500' where report_time>='125500' and report_time<'130000';
update trans_monitor_doubtaccount_tmp set spare_str1='130000' where report_time>='130000' and report_time<'130500';
update trans_monitor_doubtaccount_tmp set spare_str1='130500' where report_time>='130500' and report_time<'131000';
update trans_monitor_doubtaccount_tmp set spare_str1='131000' where report_time>='131000' and report_time<'131500';
update trans_monitor_doubtaccount_tmp set spare_str1='131500' where report_time>='131500' and report_time<'132000';
update trans_monitor_doubtaccount_tmp set spare_str1='132000' where report_time>='132000' and report_time<'132500';
update trans_monitor_doubtaccount_tmp set spare_str1='132500' where report_time>='132500' and report_time<'133000';
update trans_monitor_doubtaccount_tmp set spare_str1='133000' where report_time>='133000' and report_time<'133500';
update trans_monitor_doubtaccount_tmp set spare_str1='133500' where report_time>='133500' and report_time<'134000';
update trans_monitor_doubtaccount_tmp set spare_str1='134000' where report_time>='134000' and report_time<'134500';
update trans_monitor_doubtaccount_tmp set spare_str1='134500' where report_time>='134500' and report_time<'135000';
update trans_monitor_doubtaccount_tmp set spare_str1='135000' where report_time>='135000' and report_time<'135500';
update trans_monitor_doubtaccount_tmp set spare_str1='135500' where report_time>='135500' and report_time<'140000';
update trans_monitor_doubtaccount_tmp set spare_str1='140000' where report_time>='140000' and report_time<'140500';
update trans_monitor_doubtaccount_tmp set spare_str1='140500' where report_time>='140500' and report_time<'141000';
update trans_monitor_doubtaccount_tmp set spare_str1='141000' where report_time>='141000' and report_time<'141500';
update trans_monitor_doubtaccount_tmp set spare_str1='141500' where report_time>='141500' and report_time<'142000';
update trans_monitor_doubtaccount_tmp set spare_str1='142000' where report_time>='142000' and report_time<'142500';
update trans_monitor_doubtaccount_tmp set spare_str1='142500' where report_time>='142500' and report_time<'143000';
update trans_monitor_doubtaccount_tmp set spare_str1='143000' where report_time>='143000' and report_time<'143500';
update trans_monitor_doubtaccount_tmp set spare_str1='143500' where report_time>='143500' and report_time<'144000';
update trans_monitor_doubtaccount_tmp set spare_str1='144000' where report_time>='144000' and report_time<'144500';
update trans_monitor_doubtaccount_tmp set spare_str1='144500' where report_time>='144500' and report_time<'145000';
update trans_monitor_doubtaccount_tmp set spare_str1='145000' where report_time>='145000' and report_time<'145500';
update trans_monitor_doubtaccount_tmp set spare_str1='145500' where report_time>='145500' and report_time<'150000';
update trans_monitor_doubtaccount_tmp set spare_str1='150000' where report_time>='150000' and report_time<'150500';
update trans_monitor_doubtaccount_tmp set spare_str1='150500' where report_time>='150500' and report_time<'151000';
update trans_monitor_doubtaccount_tmp set spare_str1='151000' where report_time>='151000' and report_time<'151500';
update trans_monitor_doubtaccount_tmp set spare_str1='151500' where report_time>='151500' and report_time<'152000';
update trans_monitor_doubtaccount_tmp set spare_str1='152000' where report_time>='152000' and report_time<'152500';
update trans_monitor_doubtaccount_tmp set spare_str1='152500' where report_time>='152500' and report_time<'153000';
update trans_monitor_doubtaccount_tmp set spare_str1='153000' where report_time>='153000' and report_time<'153500';
update trans_monitor_doubtaccount_tmp set spare_str1='153500' where report_time>='153500' and report_time<'154000';
update trans_monitor_doubtaccount_tmp set spare_str1='154000' where report_time>='154000' and report_time<'154500';
update trans_monitor_doubtaccount_tmp set spare_str1='154500' where report_time>='154500' and report_time<'155000';
update trans_monitor_doubtaccount_tmp set spare_str1='155000' where report_time>='155000' and report_time<'155500';
update trans_monitor_doubtaccount_tmp set spare_str1='155500' where report_time>='155500' and report_time<'160000';
update trans_monitor_doubtaccount_tmp set spare_str1='160000' where report_time>='160000' and report_time<'160500';
update trans_monitor_doubtaccount_tmp set spare_str1='160500' where report_time>='160500' and report_time<'161000';
update trans_monitor_doubtaccount_tmp set spare_str1='161000' where report_time>='161000' and report_time<'161500';
update trans_monitor_doubtaccount_tmp set spare_str1='161500' where report_time>='161500' and report_time<'162000';
update trans_monitor_doubtaccount_tmp set spare_str1='162000' where report_time>='162000' and report_time<'162500';
update trans_monitor_doubtaccount_tmp set spare_str1='162500' where report_time>='162500' and report_time<'163000';
update trans_monitor_doubtaccount_tmp set spare_str1='163000' where report_time>='163000' and report_time<'163500';
update trans_monitor_doubtaccount_tmp set spare_str1='163500' where report_time>='163500' and report_time<'164000';
update trans_monitor_doubtaccount_tmp set spare_str1='164000' where report_time>='164000' and report_time<'164500';
update trans_monitor_doubtaccount_tmp set spare_str1='164500' where report_time>='164500' and report_time<'165000';
update trans_monitor_doubtaccount_tmp set spare_str1='165000' where report_time>='165000' and report_time<'165500';
update trans_monitor_doubtaccount_tmp set spare_str1='165500' where report_time>='165500' and report_time<'170000';
update trans_monitor_doubtaccount_tmp set spare_str1='170000' where report_time>='170000' and report_time<'170500';
update trans_monitor_doubtaccount_tmp set spare_str1='170500' where report_time>='170500' and report_time<'171000';
update trans_monitor_doubtaccount_tmp set spare_str1='171000' where report_time>='171000' and report_time<'171500';
update trans_monitor_doubtaccount_tmp set spare_str1='171500' where report_time>='171500' and report_time<'172000';
update trans_monitor_doubtaccount_tmp set spare_str1='172000' where report_time>='172000' and report_time<'172500';
update trans_monitor_doubtaccount_tmp set spare_str1='172500' where report_time>='172500' and report_time<'173000';
update trans_monitor_doubtaccount_tmp set spare_str1='173000' where report_time>='173000' and report_time<'173500';
update trans_monitor_doubtaccount_tmp set spare_str1='173500' where report_time>='173500' and report_time<'174000';
update trans_monitor_doubtaccount_tmp set spare_str1='174000' where report_time>='174000' and report_time<'174500';
update trans_monitor_doubtaccount_tmp set spare_str1='174500' where report_time>='174500' and report_time<'175000';
update trans_monitor_doubtaccount_tmp set spare_str1='175000' where report_time>='175000' and report_time<'175500';
update trans_monitor_doubtaccount_tmp set spare_str1='175500' where report_time>='175500' and report_time<'180000';
update trans_monitor_doubtaccount_tmp set spare_str1='180000' where report_time>='180000' and report_time<'180500';
update trans_monitor_doubtaccount_tmp set spare_str1='180500' where report_time>='180500' and report_time<'181000';
update trans_monitor_doubtaccount_tmp set spare_str1='181000' where report_time>='181000' and report_time<'181500';
update trans_monitor_doubtaccount_tmp set spare_str1='181500' where report_time>='181500' and report_time<'182000';
update trans_monitor_doubtaccount_tmp set spare_str1='182000' where report_time>='182000' and report_time<'182500';
update trans_monitor_doubtaccount_tmp set spare_str1='182500' where report_time>='182500' and report_time<'183000';
update trans_monitor_doubtaccount_tmp set spare_str1='183000' where report_time>='183000' and report_time<'183500';
update trans_monitor_doubtaccount_tmp set spare_str1='183500' where report_time>='183500' and report_time<'184000';
update trans_monitor_doubtaccount_tmp set spare_str1='184000' where report_time>='184000' and report_time<'184500';
update trans_monitor_doubtaccount_tmp set spare_str1='184500' where report_time>='184500' and report_time<'185000';
update trans_monitor_doubtaccount_tmp set spare_str1='185000' where report_time>='185000' and report_time<'185500';
update trans_monitor_doubtaccount_tmp set spare_str1='185500' where report_time>='185500' and report_time<'190000';
update trans_monitor_doubtaccount_tmp set spare_str1='190000' where report_time>='190000' and report_time<'190500';
update trans_monitor_doubtaccount_tmp set spare_str1='190500' where report_time>='190500' and report_time<'191000';
update trans_monitor_doubtaccount_tmp set spare_str1='191000' where report_time>='191000' and report_time<'191500';
update trans_monitor_doubtaccount_tmp set spare_str1='191500' where report_time>='191500' and report_time<'192000';
update trans_monitor_doubtaccount_tmp set spare_str1='192000' where report_time>='192000' and report_time<'192500';
update trans_monitor_doubtaccount_tmp set spare_str1='192500' where report_time>='192500' and report_time<'193000';
update trans_monitor_doubtaccount_tmp set spare_str1='193000' where report_time>='193000' and report_time<'193500';
update trans_monitor_doubtaccount_tmp set spare_str1='193500' where report_time>='193500' and report_time<'194000';
update trans_monitor_doubtaccount_tmp set spare_str1='194000' where report_time>='194000' and report_time<'194500';
update trans_monitor_doubtaccount_tmp set spare_str1='194500' where report_time>='194500' and report_time<'195000';
update trans_monitor_doubtaccount_tmp set spare_str1='195000' where report_time>='195000' and report_time<'195500';
update trans_monitor_doubtaccount_tmp set spare_str1='195500' where report_time>='195500' and report_time<'200000';
update trans_monitor_doubtaccount_tmp set spare_str1='200000' where report_time>='200000' and report_time<'200500';
update trans_monitor_doubtaccount_tmp set spare_str1='200500' where report_time>='200500' and report_time<'201000';
update trans_monitor_doubtaccount_tmp set spare_str1='201000' where report_time>='201000' and report_time<'201500';
update trans_monitor_doubtaccount_tmp set spare_str1='201500' where report_time>='201500' and report_time<'202000';
update trans_monitor_doubtaccount_tmp set spare_str1='202000' where report_time>='202000' and report_time<'202500';
update trans_monitor_doubtaccount_tmp set spare_str1='202500' where report_time>='202500' and report_time<'203000';
update trans_monitor_doubtaccount_tmp set spare_str1='203000' where report_time>='203000' and report_time<'203500';
update trans_monitor_doubtaccount_tmp set spare_str1='203500' where report_time>='203500' and report_time<'204000';
update trans_monitor_doubtaccount_tmp set spare_str1='204000' where report_time>='204000' and report_time<'204500';
update trans_monitor_doubtaccount_tmp set spare_str1='204500' where report_time>='204500' and report_time<'205000';
update trans_monitor_doubtaccount_tmp set spare_str1='205000' where report_time>='205000' and report_time<'205500';
update trans_monitor_doubtaccount_tmp set spare_str1='205500' where report_time>='205500' and report_time<'210000';
update trans_monitor_doubtaccount_tmp set spare_str1='210000' where report_time>='210000' and report_time<'210500';
update trans_monitor_doubtaccount_tmp set spare_str1='210500' where report_time>='210500' and report_time<'211000';
update trans_monitor_doubtaccount_tmp set spare_str1='211000' where report_time>='211000' and report_time<'211500';
update trans_monitor_doubtaccount_tmp set spare_str1='211500' where report_time>='211500' and report_time<'212000';
update trans_monitor_doubtaccount_tmp set spare_str1='212000' where report_time>='212000' and report_time<'212500';
update trans_monitor_doubtaccount_tmp set spare_str1='212500' where report_time>='212500' and report_time<'213000';
update trans_monitor_doubtaccount_tmp set spare_str1='213000' where report_time>='213000' and report_time<'213500';
update trans_monitor_doubtaccount_tmp set spare_str1='213500' where report_time>='213500' and report_time<'214000';
update trans_monitor_doubtaccount_tmp set spare_str1='214000' where report_time>='214000' and report_time<'214500';
update trans_monitor_doubtaccount_tmp set spare_str1='214500' where report_time>='214500' and report_time<'215000';
update trans_monitor_doubtaccount_tmp set spare_str1='215000' where report_time>='215000' and report_time<'215500';
update trans_monitor_doubtaccount_tmp set spare_str1='215500' where report_time>='215500' and report_time<'220000';
update trans_monitor_doubtaccount_tmp set spare_str1='220000' where report_time>='220000' and report_time<'220500';
update trans_monitor_doubtaccount_tmp set spare_str1='220500' where report_time>='220500' and report_time<'221000';
update trans_monitor_doubtaccount_tmp set spare_str1='221000' where report_time>='221000' and report_time<'221500';
update trans_monitor_doubtaccount_tmp set spare_str1='221500' where report_time>='221500' and report_time<'222000';
update trans_monitor_doubtaccount_tmp set spare_str1='222000' where report_time>='222000' and report_time<'222500';
update trans_monitor_doubtaccount_tmp set spare_str1='222500' where report_time>='222500' and report_time<'223000';
update trans_monitor_doubtaccount_tmp set spare_str1='223000' where report_time>='223000' and report_time<'223500';
update trans_monitor_doubtaccount_tmp set spare_str1='223500' where report_time>='223500' and report_time<'224000';
update trans_monitor_doubtaccount_tmp set spare_str1='224000' where report_time>='224000' and report_time<'224500';
update trans_monitor_doubtaccount_tmp set spare_str1='224500' where report_time>='224500' and report_time<'225000';
update trans_monitor_doubtaccount_tmp set spare_str1='225000' where report_time>='225000' and report_time<'225500';
update trans_monitor_doubtaccount_tmp set spare_str1='225500' where report_time>='225500' and report_time<'230000';
update trans_monitor_doubtaccount_tmp set spare_str1='230000' where report_time>='230000' and report_time<'230500';
update trans_monitor_doubtaccount_tmp set spare_str1='230500' where report_time>='230500' and report_time<'231000';
update trans_monitor_doubtaccount_tmp set spare_str1='231000' where report_time>='231000' and report_time<'231500';
update trans_monitor_doubtaccount_tmp set spare_str1='231500' where report_time>='231500' and report_time<'232000';
update trans_monitor_doubtaccount_tmp set spare_str1='232000' where report_time>='232000' and report_time<'232500';
update trans_monitor_doubtaccount_tmp set spare_str1='232500' where report_time>='232500' and report_time<'233000';
update trans_monitor_doubtaccount_tmp set spare_str1='233000' where report_time>='233000' and report_time<'233500';
update trans_monitor_doubtaccount_tmp set spare_str1='233500' where report_time>='233500' and report_time<'234000';
update trans_monitor_doubtaccount_tmp set spare_str1='234000' where report_time>='234000' and report_time<'234500';
update trans_monitor_doubtaccount_tmp set spare_str1='234500' where report_time>='234500' and report_time<'235000';
update trans_monitor_doubtaccount_tmp set spare_str1='235000' where report_time>='235000' and report_time<'235500';
update trans_monitor_doubtaccount_tmp set spare_str1='235500' where report_time>='235500' and report_time<'000000';

update trans_monitor_branch set trans_count=20,suc_count=0 where subsys_code=10 

select sum(trans_count) trans_count,sum(suc_count) suc_count from trans_monitor_doubtaccount_tmp where type_code='01' and report_date='2014-09-10' and  spare_str1<='100000';


select b.time_sec,a.trans_count,a.suc_count from time_section b left join (select spare_str1,sum(trans_count)/30 trans_count,sum(suc_count)/30 suc_count from trans_monitor_doubtaccount_tmp where type_code='01' and report_date>='2014-09-30' and report_date<='2014-09-09' group by spare_str1) a on a.spare_str1=b.time_sec order by b.serial

000000
000500
001000
001500
002000
002500
003000
003500
004000
004500
005000
005500
010000
010500
011000
011500
012000
012500
013000
013500
014000
014500
015000
015500
020000
020500
021000
021500
022000
022500
023000
023500
024000
024500
025000
025500
030000
030500
031000
031500
032000
032500
033000
033500
034000
034500
035000
035500
040000
040500
041000
041500
042000
042500
043000
043500
044000
044500
045000
045500
050000
050500
051000
051500
052000
052500
053000
053500
054000
054500
055000
055500
060000
060500
061000
061500
062000
062500
063000
063500
064000
064500
065000
065500
070000
070500
071000
071500
072000
072500
073000
073500
074000
074500
075000
075500
080000
080500
081000
081500
082000
082500
083000
083500
084000
084500
085000
085500
090000
090500
091000
091500
092000
092500
093000
093500
094000
094500
095000
095500
100000
100500
101000
101500
102000
102500
103000
103500
104000
104500
105000
105500
110000
110500
111000
111500
112000
112500
113000
113500
114000
114500
115000
115500
120000
120500
121000
121500
122000
122500
123000
123500
124000
124500
125000
125500
130000
130500
131000
131500
132000
132500
133000
133500
134000
134500
135000
135500
140000
140500
141000
141500
142000
142500
143000
143500
144000
144500
145000
145500
150000
150500
151000
151500
152000
152500
153000
153500
154000
154500
155000
155500
160000
160500
161000
161500
162000
162500
163000
163500
164000
164500
165000
165500
170000
170500
171000
171500
172000
172500
173000
173500
174000
174500
175000
175500
180000
180500
181000
181500
182000
182500
183000
183500
184000
184500
185000
185500
190000
190500
191000
191500
192000
192500
193000
193500
194000
194500
195000
195500
200000
200500
201000
201500
202000
202500
203000
203500
204000
204500
205000
205500
210000
210500
211000
211500
212000
212500
213000
213500
214000
214500
215000
215500
220000
220500
221000
221500
222000
222500
223000
223500
224000
224500
225000
225500
230000
230500
231000
231500
232000
232500
233000
233500
234000
234500
235000
235500

select type_code,spare_str1,sum(trans_count),sum(suc_count) from trans_monitor_doubtaccount_tmp group by type_code,spare_str1 order by spare_str1;