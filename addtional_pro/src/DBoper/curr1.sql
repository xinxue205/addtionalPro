grant select,insert,update,delete on nutch.* to mysql@"%" Identified by  "mysql123";

INSERT INTO `TB_SM_WORKPOST` VALUES ('93', '100000', '省中心一级管理工位', '930000000', '0', 'S0A', '超级管理员使用工位，不能删改', 'N', '-1', '', '93', '0', '2012-09-08 19:06:55', '', '');
select * from TB_SM_STAFFPOST

select serv_openTime,serv_name,serv_addr,serv_lines,serv_decrip from serv_pub_04 where serv_enable=1 and serv_level=1
insert into serv_pub_04 values('wxx',17,18,1,1,'开开心心1',1,'192.168.1.1','好开心',1,0,'');
insert into serv_pub_04 values('wxx',17,18,1,1,'开开心心2',1,'192.168.1.1','好开心',1,0,'');
insert into serv_pub_04 values('wxx',17,18,1,2,'开开心心3',2,'192.168.1.1','好开心',1,0,'');
insert into serv_pub_04 values('wxx',17,18,1,2,'开开心心4',2,'192.168.1.1','好开心',1,0,'');
insert into serv_pub_04 values('wxx',17,18,1,1,'开开心心5',1,'192.168.1.1','好开心',1,0,'');

select * from serv_pub_03;
update user set Index_priv='Y' where User='publishDB';

--------------------------------------
create database publishDB;
create table publishDB.serv_pub_04(
	serv_managerid VARCHAR(32),
	serv_pubTime int,
	serv_openTime int,
	serv_keepTime int,
	serv_level int,
	serv_name VARCHAR(32),
	serv_lines VARCHAR (16),
	serv_addr VARCHAR (32),
	serv_decrip VARCHAR(64),
	serv_enable int,
	srev_bakint int,
	srev_bakstr VARCHAR(32)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
create index idx_serv_pub_04_01 on serv_pub_04 (serv_openTime);

--消费记录表
--time 时间;userid 用户ID;money 消费金额（元）；sys_id 系统ID:game-0,publish-1,trans-2；
--game_area game区; game_money game币;remark 备注
CREATE TABLE fengnetDB.consume_record (
	time VARCHAR(32) NOT NULL,
	userid VARCHAR(32) NOT NULL,
	money INT NOT NULL,
	sys_id INT NOT NULL,
	game_area INT,
	game_money DOUBLE,
	remark VARCHAR(64),
	spare_int1 INT, 
	spare_int2 INT, 
	spare_str1 VARCHAR(32), 
	spare_str2 VARCHAR(64) 
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
create unique index idx_consume_record_01 on consume_record (time);
create index idx_consume_record_02 on consume_record (userid,money,sys_id,game_area);

--系统参数表
--sys_id 系统ID:game-0,publish-1,trans-2; param_id 参数ID
--param_name 参数名; param_int int参数; param_str 参数 
CREATE TABLE fengnetDB.sys_params (
	sys_id INT NOT NULL,
	param_id INT NOT NULL,
	param_name VARCHAR(32),
	param_int INT,
	param_str VARCHAR(64),
	spare_int1 INT, 
	spare_int2 INT, 
	spare_str1 VARCHAR(32), 
	spare_str2 VARCHAR(64)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
create unique index idx_sys_params_01 on sys_params (sys_id,param_id);

--数据字段表
--dd_name 数据名； dd_id 编号； dd_text 内容； dd_desc 描述；
drop table sys_datadict;
CREATE TABLE fengnetDB.sys_datadict (
	dd_name VARCHAR(32) NOT NULL,
	dd_id INT NOT NULL,
	dd_text VARCHAR(64),
	dd_desc VARCHAR(64),
	dd_flag  INT,
	dd_order INT, 
	dd_enable INT, 
	spare_int INT, 
	spare_str VARCHAR(64)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;
create unique index idx_sys_datadict_01 on sys_datadict (dd_name,dd_id);
insert into sys_datadict values ('configFileForIpChange',1,'D:\MirServer\Config.ini','主程序配置文件',0,1,1,0,null);
insert into sys_datadict values ('configFileForIpChange',2,'D:\MirServer\DBServer\!addrtable.txt','数据服务配置文件',0,1,1,0,null);
insert into sys_datadict values ('configFileForIpChange',3,'D:\MirServer\DBServer\!serverinfo.txt','数据服务配置文件',0,2,1,0,null);
insert into sys_datadict values ('configFileForIpChange',4,'D:\MirServer\LoginSrv\!serveraddr.txt','登陆服务配置文件',0,3,1,0,null);
insert into sys_datadict values ('configFileForIpChange',5,'D:\MirServer\LoginSrv\!addrtable.txt','登陆服务配置文件',0,4,1,0,null);
insert into sys_datadict values ('configFileForIpChange',6,'D:\MirServer\Mir200\!ServerTable.txt','M2服务配置文件',0,5,1,0,null);
insert into sys_datadict values ('restartServiceForIpChange',1,'DBServer','数据服务',1,1,1,0,null);
insert into sys_datadict values ('restartServiceForIpChange',2,'LoginSrv','登陆服务',1,2,1,0,null);
insert into sys_datadict values ('restartServiceForIpChange',3,'M2Server','M2服务',1,3,1,0,null);


select dd_id,dd_text from sys_datadict where dd_name='configFileForIpChange' and dd_enable=1
