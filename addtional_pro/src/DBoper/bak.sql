---------201411start------
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_ap', 'AP��', '03', null, 3, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_branch', '������', '02', null, 2, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_cardsign', '����־��', '07', null, 7, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_cardtype', '��������', '08', null, 8, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_doubtaccount', '����������', '09', null, 9, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_monitor_errcode', '��������', '05', null, 5, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_trcode', '��������', '04', null, 4, 1);

--����
delete from m30_plat_params where branch_code='010000000' and param_no IN ('170','171','172');
---------201411end------

---------201408start------
insert into subsys_inout_config (subsys_code, sys_code,sys_en_name,sys_cn_name, thirdparty_flag, spare_int1) values ('10   ', '99','�Ŷ����', '�Ŷ����',4, 1);
insert into sys_datadictionary (dd_id,dd_text,dd_dictname) values ('4','������','transMonitorIO');
delete trans_monitor_config where stat_type=2 and type_code='422000000';
--����
delete from sys_datadictionary where dd_id='4' and dd_dictname='transMonitorIO';
delete from subsys_inout_config where subsys_code='10' and sys_code='99';
---------201408end------

-----20140719begin
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '8', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '9', '*', 'Ĭ��ֵ', null, null, null, null);
update trans_monitor_config set spare_int1=14 where stat_type=5 and type_code in ('00-*****','0x00','0000','0001','0002');
update trans_monitor_config set spare_int1=15 where stat_type=5 and type_code='**-*****';
update trans_monitor_quota set trans_count=trans_count||'@09-19';
-----20140719end

-----20140628begin
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('transMonitorIO','1','�����',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('transMonitorIO','2','�ӳ���',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('transMonitorIO','3','������',3,1);
-----20140628end

-----20140614begin
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('lock_status', '0', '��ʼ', null, null, 0, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('lock_status', '1', '����', null, null, 1, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('lock_status', '2', '������ͣʹ��', null, null, 2, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('lock_status', '3', '����', null, null, 3, 1);

insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','001','������001','�����������ķ��������',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','002','������002','�����������ķ��������',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','003','������003','�����������ķ��������',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','004','������004','�����������ķ��������',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','005','������005','�����������ķ��������',5,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','006','������006','�����������ķ��������',6,1);

insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_branch','������','02',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_ap','AP��','03',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_trcode','��������','04',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_errcode','��������','05',5,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_cardvest','��������','06',6,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_cardsign','����־��','07',7,1);

create table subsys_resp_config
  (
    subsys_code char(5),--��ϵͳ��� ��Ӧsubsys_msg ����subsys_code��Ӧ PHϵͳΪ01
	resp_id  integer,--�����˱��
	resp_name  varchar(32),--����������
    resp_mobile  varchar(16),--�������ֻ�����
	resp_tel varchar(16),--��������ϵ�绰
	resp_alarm integer,--�Ƿ�ͨ����Ԥ����־ 0-�رգ�1-����
	resp_remark varchar(64),--˵��
    spare_int1 integer,
    spare_int2 integer,
    spare_str1 varchar(128),
    spare_str2 varchar(128)
  )  extent size 5000 next size 5000 lock mode row;
    
create unique index idx_subsys_resp_config_01 on subsys_resp_config  (subsys_code,resp_id) using btree;
create  index idx_subsys_resp_config_02 on subsys_resp_config  (subsys_code) using btree;

insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '2', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '3', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '4', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '5', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '6', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '7', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('04   ', '3', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('04   ', '5', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('07   ', '2', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('07   ', '3', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('07   ', '4', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('07   ', '5', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('08   ', '3', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('09   ', '3', '*', 'Ĭ��ֵ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('10   ', '2', '*', 'Ĭ��ֵ', null, null, null, null);

insert into elec_make_info values ('1','SGSG','SGSG',NULL,NULL,NULL,NULL,NULL);
-----20140614end

-----201404begin
drop table manu_std_prs_time;
CREATE TABLE manu_std_prs_time
    (
        dev_code CHAR(12) NOT NULL,  --�豸���
        stdprstime INTEGER DEFAULT 0,  --��׼����ʱ�䣨��λ�����ӣ�
        updatetime CHAR(14),  --����ʱ��
        updateuser CHAR(20),  --������ID
        spare_int1 INTEGER,
        spare_int2 INTEGER,
        spare_str1 CHAR(30),
        spare_str2 CHAR(80),
        PRIMARY KEY (dev_code)
    )in datadbs06 extent size 5000 next size 5000 lock mode row;
create index idx_manu_std_prs_time_01 on manu_std_prs_time (dev_code,updatetime,updateuser) using btree in idxdbs06;
insert into MANU_STD_PRS_TIME select dev_code,'0','','','','','','' from dev_bmsg where dev_code not in  (select dev_code from MANU_STD_PRS_TIME);

delete from sys_datadictionary where dd_dictname in ('etbTrscode','etbSource','etbTransType','etbTransRes','etbCompStatus');
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1001','ǩԼ�˻����Ͳ�ѯ',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1002','�ն˼���',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1003','���������޸�',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1004','�������',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1005','���ܻ����ýӿ�',5,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1006','�ն˳���',6,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1008','������������',7,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1009','��ǩԼ�˺�',8,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1201','������ѯ������',9,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1204','psam��licence�ӽ��ܱ���',10,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1018','�绰֧��������ˮ��ѯ',11,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1019','�绰֧������ƾ֤�ش�',12,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1020','�����˻���ѯ',13,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1021','����(�����˻�)��ѯ',14,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1022','�������Ų�ѯ����',15,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1023','�����ڲ�ѯ����',16,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1024','��ϸ��ӡ',17,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1601','��������ѯ',18,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1602','����ѯ',19,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1603','��ϸ��ѯ',20,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1604','�����˻�',21,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1605','����(�����˻�)',22,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1606','���˲�ѯ',23,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1607','������������ѯ',24,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1608','��ϸ��ѯȷ�����ʲ�ѯ',25,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1615','����',26,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2011','ˢ�������˻�ת�뱾�������Ѽ���',27,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2021','����ת����ˢ�������˻������Ѽ���',28,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2023','����ת�������뽨���˻������Ѽ���',29,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2025','����ת����Լ�������˻������Ѽ���',30,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2027','�̻����̻�ת��',31,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2031','����ת����ˢ�������˻������Ѽ���',32,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2033','����ת�������뽨���˻������Ѽ���',33,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2034','����ת����ʷ��ѯ',34,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2035','����ת�������Ѽ���',35,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2036','�������ÿ����������Ѽ���',36,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2037','��֤ת�ʽ���(����ת֤ȯ)',37,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2039','��֤ת�ʽ���(֤ȯת����)',38,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2046','������ͨˮ��(���)��ѯ',39,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2047','������ͨˮ��(���)�ɷ�',40,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2056','�������ÿ�����',41,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2051','����ת��',42,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1205','�˻���Ϣά��',43,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1206','�ͻ�ע��',44,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1207','��ѯ������룬psam����',45,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1208','ǩԼ��Ϣά��',46,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','021902','�ͻ�ǩԼ',47,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2081','Ԥ��Ȩ',48,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2082','Ԥ��Ȩ����',49,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2083','Ԥ��Ȩ���',50,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2084','Ԥ�ڻ���ɳ���(���ѳ���)',51,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2085','Ԥ�ڻ���ɳ�����ѯ',52,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2032','��֪���кſ���ת�������Ѽ���',53,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2052','����ת��',54,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2053','����ת�����д����ӡ',55,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2054','����ת�����д����ѯ',56,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2055','����ת�����д����ӡ2',57,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2091','���ÿ������ѯ',59,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2092','���ÿ�����',60,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2041','���ɷѳ�ֵ',61,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2042','���ɷѽɷѲ�ѯ',62,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2043','���ɷ�',63,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2044','���ɷѳ�ֵ���б��ѯ',64,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2045','���ɷѽɷ����б��ѯ',65,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1101','������������',67,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1102','������������',68,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1108','������������',69,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1618','�Ǳ����˻���ϸ��ѯ',70,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1124','������ϸ��ӡ',71,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1025','�������˴�ӡ',73,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1610','���������˻�����ѯ',74,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1611','���������˻���ϸ��ѯ',75,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1612','���������˻���ϸ���ʴ�ӡ',76,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1701','����������ѯ',77,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1702','��������ϸ��ѯ',78,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1703','�����𵥱ʲ�ѯ��ӡ',79,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1704','ס����������ѯ',80,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1705','ס��������ϸ��ѯ',81,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1706','ס���������ʲ�ѯ��ӡ',82,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1301','�׻���Ȳ�ѯ',83,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1302','�׻�����',84,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1303','�׻����ѳ�����ѯ',85,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1304','�׻����ѳ���',86,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3001','�����ѯ���ȡ�б�',87,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3002','ʵʱ�����ѯ',88,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3003','�ֲֲ�ѯ/����ί��/���ճɽ���ȡ�б�',89,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3004','�ֲֲ�ѯ',90,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3005','����ί�в�ѯ',91,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3006','���ճɽ���ѯ',92,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3007','ʵʱ����/����ί��/ֹ��ί�л�ȡ�б�',93,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3008','ʵʱ��������ȷ��',94,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3009','ʵʱ����',95,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3010','����ί��',96,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3011','ֹ��ί��',97,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3012','ί�г���',98,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3013','֤ȯ��֤������ѯ',99,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3014','�����ʻ�ת֤ȯ��֤��',100,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3015','֤ȯ��֤��ת�����ʻ�',101,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3016','���ճɽ���ϸ',102,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3017','����ί����ϸ',103,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3018','ί�г��������б�',104,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3020','�깺ί��/�Ϲ�ί�з���',105,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3021','�깺ί��',106,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3022','�Ϲ�ί��',107,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3023','���ί�з���',108,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3024','���ί��',109,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3025','��Ͷ���뷵��',110,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3026','��Ͷ����',111,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3027','��Ͷ����',112,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3028','ת��ί�з���',113,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3029','ת��ί��',114,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3030','�깺����/��س�������',115,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3031','�깺����',116,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3032','��س���',117,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3033','��������ѯ',118,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3034','�����ѯ',119,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3035','�ֲֲ�ѯ',120,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3036','�ֲֲ�ѯ��ϸ',121,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3037','��Ͷ��ѯ',122,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3038','��Ͷ��ϸ',123,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3039','������ί�в�ѯ',124,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3120','������ί����ϸ',125,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3121','�����ճɽ���ѯ',126,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3122','�����ճɽ���ϸ',127,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3123','����ί�гɽ�����',128,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3040','����������ֲ�ѯ',129,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3041','���ʲ�ѯ',130,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3042','����ί�з���ȷ��',131,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3043','����ί��',132,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3044','���������ϸ��ѯ',133,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3045','��㵥����ϸ��ӡ',134,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3046','ί�г�������ȷ��',135,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3047','ί�г���',136,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3048','ֹ��ί�з���ȷ��',137,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3049','ֹ��ί��',138,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3050','˫��ί�з���ȷ��',139,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3051','˫��ί��',140,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3052','�������ѯ',141,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3053','�������:ί��У�鷵��',142,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3054','�������:��ϸ��ѯ����',143,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3055','�������:������ϸ��ѯ',144,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3056','�������:�����ϸ��ӡ',145,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3057','δ֪���к�ת�����д��뷵���б�',146,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3058','δ֪���к�ת�˷����б�',147,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3059','δ֪���к�ת�������Ѽ���',148,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2615','����֧��',149,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2616','����֧����ѯ',150,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2618','����֧����ѯ',151,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2605','����֧������',152,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbSource','01','�绰֧��',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbSource','02','����������',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbSource','03','������֧��',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','0','����',0,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','1','����',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','2','ת��',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','3','��ѯ',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','4','�ɷ�',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','5','���ǿ�',5,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','6','Ԥ��Ȩ',6,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','7','CTS',7,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','8','�˻���',8,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','9','����',9,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','10','�������',10,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','13','��ũ����ѯ',13,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','14','��ũȡ��',14,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','15','��������ת��',15,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransRes','0','δ��',0,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransRes','1','�ɹ�',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransRes','2','ʧ��',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransRes','3','����',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransRes','4','Ԥ��Ȩ���',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbCompStatus','0','δ����',0,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbCompStatus','1','���˳ɹ�',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbCompStatus','2','����ƥ��',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbCompStatus','3','ETB������û��',3,1);

DELETE FROM m30_plat_params WHERE param_no IN ('93','94','95','97','98','99','1106','111','112') AND branch_code='010000000';
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','93','IABSϵͳ���ʵ�ַ ','11.152.83.60:1341');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','94','ATMVHϵͳ��ַ���˿� ','11.152.83.67:1380');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','95','IABS�û��������ѯ�Ľ�ɫ','''211'''||','||'''212''');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','97','IABSϵͳ�������� ','/Iabms_WEB/loginAction.do?ATMPARAM=');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','98','��ϵͳ�����̷������� ','/reportShow/outSystemAccess.jsp?PARAM=');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','99','��ϵͳ�����̵�ַ ','11.152.83.67:1382');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','1106', 'ETB��ˮ��ѯ�˵���ַ','screen/report/infoQuery/etbJournalQuery.jsp');
insert into m30_plat_params (branch_code,param_no,param_name,param_int,param_char) values ('010000000','111','��branch_running_time���뷽ʽ ', 2, 'branch_running_time');
insert into m30_plat_params (branch_code,param_no,param_name,param_char,param_double) values ('010000000','112','�豸����������ر������� ','20','0.5');

--����
delete from m30_plat_params where branch_code='010000000' and param_no in (116,96,93,94);
insert into m30_plat_params (branch_code,param_no,param_name,param_int,param_char) values ('010000000',116,'�칫����ϵͳ��ַ ',1,'11.152.83.67,11.152.67.66,atmvh');
insert into m30_plat_params (branch_code,param_no,param_name,param_int,param_char) values ('010000000',96,'��������ϵͳ��ַ ',0,'11.152.51.52,11.152.35.35,scatmvh');
insert into m30_plat_params (branch_code,param_no,param_name,param_char,spare_str1) values ('010000000',94,'��ϵͳ�����̵�ַ','11.152.51.52:1342','11.152.83.67:1382');
insert into m30_plat_params (branch_code,param_no,param_name,param_char,spare_str1) values ('010000000',93,'IABSϵͳ���ʵ�ַ ','11.152.51.51:1321','11.152.83.60:1341');
-----201404end

-----201403begin
update ws_case_config set BRANCH_LEVEL=(select BRANCH_LEVEL from (select branch_code branchcode,BRANCH_LEVEL from branch_msg) where branch_code=branchcode)
update ws_case_config set step5_time=(select sendwaittime3 from (select casetype case_type,waittime,managertime,sendwaittime1,sendwaittime2,sendwaittime3 from ws_timeout) where case_type=casetype);
update ws_case_config set end_time=(select endtime from (select casetype case_type,endtime from ws_alarmtime) where case_type=casetype);

insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','1','��һ��ϵ��',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','2','�ڶ���ϵ��',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','3','��������',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','4','��������',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','5','һ������',5,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','6','����ά����',6,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','7','����ά����',7,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','8','һ��ά����',8,1);

UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,4,LENGTH(CASENAME)) WHERE SUBSTR(CASENAME,1,3)<'999';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,5,LENGTH(CASENAME)) WHERE SUBSTR(CASENAME,1,4) IN ('����','�㶫','ɽ��');
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,3,LENGTH(CASENAME)) WHERE CASENAME LIKE '��%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,0,8) WHERE CASENAME LIKE '�������%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,0,8) WHERE CASENAME LIKE 'ͨѶ����%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,0,8) WHERE CASENAME LIKE 'ģ�����%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,0,14) WHERE CASENAME LIKE '������ӡ������%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,0,14) WHERE CASENAME LIKE '��ˮ��ӡ������%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,4,LENGTH(CASENAME)) WHERE SUBSTR(CASENAME,1,3)<'ZZZ';
UPDATE WS_CASE SET CASENAME='������ӡ������' WHERE CASENAME='������ӡ����';
UPDATE WS_CASE SET CASENAME='�豸״̬δ֪' WHERE CASENAME='�豸����״̬δ֪';
UPDATE WS_CASE SET CASENAME='���䳮��' WHERE CASENAME in ('����','����״̬����','����״̬����','����״̬�����');
UPDATE WS_CASE SET CASENAME='�������' WHERE CASENAME='����״̬����';
UPDATE WS_CASE SET CASENAME='��������' WHERE CASENAME='����״̬����';
UPDATE WS_CASE SET CASENAME='������' WHERE CASENAME='���������';
UPDATE WS_CASE SET CASENAME='���ģ�����' WHERE CASENAME in ('���ģ�����ش���','���ģ����������');
UPDATE WS_CASE SET CASENAME='�ϳ������' WHERE CASENAME='�ϳ���״̬������';
UPDATE WS_CASE SET CASENAME='�ϳ�����' WHERE CASENAME='�ϳ���״̬�ϳ�����';
UPDATE WS_CASE SET CASENAME='�ϳ������' WHERE CASENAME='�ϳ���״̬����δ֪';
UPDATE WS_CASE SET CASENAME='��չģ�����' WHERE CASENAME='��չ״̬����';
UPDATE WS_CASE SET CASENAME='��չģ������' WHERE CASENAME='��չ״̬����';
UPDATE WS_CASE SET CASENAME='ģ�����' WHERE CASENAME='ģ��״̬����';
UPDATE WS_CASE SET CASENAME='ȡ��ģ�����' WHERE CASENAME='ȡ��ģ�����ش���';
UPDATE WS_CASE SET CASENAME='�豸����' WHERE CASENAME='�豸����״̬��������';
UPDATE WS_CASE SET CASENAME='�豸�ر�' WHERE CASENAME='�豸����״̬����ر�';  
UPDATE WS_CASE SET CASENAME='�豸ά��' WHERE CASENAME='�豸����״̬ά��';
UPDATE WS_CASE SET CASENAME='�豸δ֪����' WHERE CASENAME='�豸״̬δ֪';
UPDATE WS_CASE SET CASENAME='ͨѶ����' WHERE CASENAME='ͨѶ״̬ͨѶ����'; 
UPDATE WS_CASE SET CASENAME='ͨѶ����' WHERE CASENAME='ͨѶ״̬ͨѶ����';  
UPDATE WS_CASE SET CASENAME='ͨѶ�ж�' WHERE CASENAME='ͨѶ״̬ͨѶ�ж�';
UPDATE WS_CASE SET CASENAME='����������' WHERE CASENAME='����������������';
-----201403end
