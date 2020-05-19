---------201411start------
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_ap', 'AP类', '03', null, 3, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_branch', '分行类', '02', null, 2, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_cardsign', '卡标志类', '07', null, 7, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_cardtype', '卡类型类', '08', null, 8, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_doubtaccount', '可疑账务类', '09', null, 9, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_monitor_errcode', '错误码类', '05', null, 5, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('ChannelMonitorNew_Type', 'trans_show_trcode', '交易码类', '04', null, 4, 1);

--回退
delete from m30_plat_params where branch_code='010000000' and param_no IN ('170','171','172');
---------201411end------

---------201408start------
insert into subsys_inout_config (subsys_code, sys_code,sys_en_name,sys_cn_name, thirdparty_flag, spare_int1) values ('10   ', '99','排队情况', '排队情况',4, 1);
insert into sys_datadictionary (dd_id,dd_text,dd_dictname) values ('4','本渠道','transMonitorIO');
delete trans_monitor_config where stat_type=2 and type_code='422000000';
--回退
delete from sys_datadictionary where dd_id='4' and dd_dictname='transMonitorIO';
delete from subsys_inout_config where subsys_code='10' and sys_code='99';
---------201408end------

-----20140719begin
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '8', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '9', '*', '默认值', null, null, null, null);
update trans_monitor_config set spare_int1=14 where stat_type=5 and type_code in ('00-*****','0x00','0000','0001','0002');
update trans_monitor_config set spare_int1=15 where stat_type=5 and type_code='**-*****';
update trans_monitor_quota set trans_count=trans_count||'@09-19';
-----20140719end

-----20140628begin
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('transMonitorIO','1','接入端',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('transMonitorIO','2','接出端',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('transMonitorIO','3','第三方',3,1);
-----20140628end

-----20140614begin
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('lock_status', '0', '初始', null, null, 0, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('lock_status', '1', '激活', null, null, 1, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('lock_status', '2', '激活暂停使用', null, null, 2, 1);
insert into sys_datadictionary (dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled) values ('lock_status', '3', '报废', null, null, 3, 1);

insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','001','服务器001','电子密码锁的服务器编号',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','002','服务器002','电子密码锁的服务器编号',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','003','服务器003','电子密码锁的服务器编号',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','004','服务器004','电子密码锁的服务器编号',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','005','服务器005','电子密码锁的服务器编号',5,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('elecPwdLockServerAp','006','服务器006','电子密码锁的服务器编号',6,1);

insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_branch','分行类','02',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_ap','AP类','03',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_trcode','交易码类','04',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_errcode','错误码类','05',5,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_cardvest','卡归属类','06',6,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_desc,dd_order,dd_enabled) values ('ChannelMonitor_Type','trans_monitor_cardsign','卡标志类','07',7,1);

create table subsys_resp_config
  (
    subsys_code char(5),--子系统编号 对应subsys_msg 表中subsys_code对应 PH系统为01
	resp_id  integer,--负责人编号
	resp_name  varchar(32),--负责人姓名
    resp_mobile  varchar(16),--负责人手机号码
	resp_tel varchar(16),--负责人联系电话
	resp_alarm integer,--是否开通短信预警标志 0-关闭；1-开启
	resp_remark varchar(64),--说明
    spare_int1 integer,
    spare_int2 integer,
    spare_str1 varchar(128),
    spare_str2 varchar(128)
  )  extent size 5000 next size 5000 lock mode row;
    
create unique index idx_subsys_resp_config_01 on subsys_resp_config  (subsys_code,resp_id) using btree;
create  index idx_subsys_resp_config_02 on subsys_resp_config  (subsys_code) using btree;

insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '2', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '3', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '4', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '5', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '6', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01   ', '7', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('04   ', '3', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('04   ', '5', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('07   ', '2', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('07   ', '3', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('07   ', '4', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('07   ', '5', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('08   ', '3', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('09   ', '3', '*', '默认值', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('10   ', '2', '*', '默认值', null, null, null, null);

insert into elec_make_info values ('1','SGSG','SGSG',NULL,NULL,NULL,NULL,NULL);
-----20140614end

-----201404begin
drop table manu_std_prs_time;
CREATE TABLE manu_std_prs_time
    (
        dev_code CHAR(12) NOT NULL,  --设备编号
        stdprstime INTEGER DEFAULT 0,  --标准到场时间（单位：分钟）
        updatetime CHAR(14),  --更新时间
        updateuser CHAR(20),  --更新人ID
        spare_int1 INTEGER,
        spare_int2 INTEGER,
        spare_str1 CHAR(30),
        spare_str2 CHAR(80),
        PRIMARY KEY (dev_code)
    )in datadbs06 extent size 5000 next size 5000 lock mode row;
create index idx_manu_std_prs_time_01 on manu_std_prs_time (dev_code,updatetime,updateuser) using btree in idxdbs06;
insert into MANU_STD_PRS_TIME select dev_code,'0','','','','','','' from dev_bmsg where dev_code not in  (select dev_code from MANU_STD_PRS_TIME);

delete from sys_datadictionary where dd_dictname in ('etbTrscode','etbSource','etbTransType','etbTransRes','etbCompStatus');
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1001','签约账户类型查询',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1002','终端激活',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1003','渠道密码修改',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1004','公告更新',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1005','加密机调用接口',5,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1006','终端冲正',6,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1008','渠道密码设置',7,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1009','新签约账号',8,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1201','户主查询请求报文',9,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1204','psam卡licence加解密报文',10,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1018','电话支付交易流水查询',11,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1019','电话支付交易凭证重打',12,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1020','隔日退货查询',13,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1021','撤销(当日退货)查询',14,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1022','按订单号查询订单',15,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1023','按日期查询订单',16,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1024','明细打印',17,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1601','本机余额查询',18,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1602','余额查询',19,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1603','明细查询',20,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1604','隔日退货',21,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1605','撤销(当日退货)',22,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1606','来账查询',23,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1607','本机单笔余额查询',24,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1608','明细查询确定单笔查询',25,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1615','消费',26,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2011','刷卡建行账户转入本机手续费计算',27,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2021','本机转出到刷卡建行账户手续费计算',28,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2023','本机转出到输入建行账户手续费计算',29,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2025','本机转出到约定建行账户手续费计算',30,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2027','固话到固话转帐',31,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2031','个人转出到刷卡建行账户手续费计算',32,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2033','个人转出到输入建行账户手续费计算',33,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2034','快速转账历史查询',34,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2035','快速转账手续费计算',35,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2036','他行信用卡还款手续费计算',36,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2037','银证转帐交易(银行转证券)',37,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2039','银证转帐交易(证券转银行)',38,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2046','缴纳网通水费(电费)查询',39,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2047','缴纳网通水费(电费)缴费',40,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2056','他行信用卡还款',41,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2051','本行转账',42,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1205','账户信息维护',43,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1206','客户注销',44,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1207','查询来电号码，psam卡号',45,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1208','签约信息维护',46,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','021902','客户签约',47,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2081','预授权',48,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2082','预授权撤销',49,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2083','预授权完成',50,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2084','预授机完成撤销(消费撤销)',51,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2085','预授机完成撤销查询',52,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2032','已知联行号跨行转账手续费计算',53,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2052','跨行转账',54,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2053','跨行转账银行代码打印',55,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2054','跨行转账银行代码查询',56,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2055','跨行转账银行代码打印2',57,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2091','信用卡还款查询',59,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2092','信用卡还款',60,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2041','代缴费充值',61,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2042','代缴费缴费查询',62,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2043','代缴费',63,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2044','代缴费充值类列表查询',64,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2045','代缴费缴费类列表查询',65,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1101','重置渠道密码',67,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1102','重置渠道密码',68,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1108','重置渠道密码',69,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1618','非本机账户明细查询',70,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1124','单笔明细打印',71,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1025','单笔来账打印',73,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1610','本机结算账户余额查询',74,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1611','本机结算账户明细查询',75,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1612','本机结算账户明细单笔打印',76,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1701','公积金余额查询',77,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1702','公积金明细查询',78,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1703','公积金单笔查询打印',79,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1704','住房补贴余额查询',80,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1705','住房补贴明细查询',81,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1706','住房补贴单笔查询打印',82,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1301','易货额度查询',83,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1302','易货消费',84,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1303','易货消费撤销查询',85,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','1304','易货消费撤销',86,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3001','行情查询类获取列表',87,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3002','实时行情查询',88,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3003','持仓查询/当日委托/当日成交获取列表',89,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3004','持仓查询',90,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3005','当日委托查询',91,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3006','当日成交查询',92,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3007','实时买卖/获利委托/止损委托获取列表',93,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3008','实时买卖返显确认',94,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3009','实时买卖',95,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3010','获利委托',96,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3011','止损委托',97,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3012','委托撤销',98,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3013','证券保证金余额查询',99,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3014','银行帐户转证券保证金',100,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3015','证券保证金转银行帐户',101,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3016','当日成交明细',102,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3017','当日委托明细',103,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3018','委托撤销返回列表',104,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3020','申购委托/认购委托返显',105,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3021','申购委托',106,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3022','认购委托',107,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3023','赎回委托返显',108,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3024','赎回委托',109,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3025','定投申请返显',110,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3026','定投申请',111,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3027','定投撤销',112,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3028','转换委托返显',113,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3029','转换委托',114,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3030','申购撤销/赎回撤销返显',115,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3031','申购撤销',116,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3032','赎回撤销',117,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3033','基金代码查询',118,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3034','行情查询',119,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3035','持仓查询',120,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3036','持仓查询明细',121,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3037','定投查询',122,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3038','定投明细',123,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3039','基金当日委托查询',124,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3120','基金当日委托明细',125,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3121','基金当日成交查询',126,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3122','基金当日成交明细',127,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3123','基金委托成交返显',128,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3040','外汇买卖币种查询',129,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3041','汇率查询',130,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3042','获利委托返显确认',131,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3043','获利委托',132,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3044','外汇买卖明细查询',133,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3045','外汇单笔明细打印',134,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3046','委托撤单返显确认',135,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3047','委托撤单',136,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3048','止损委托返显确认',137,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3049','止损委托',138,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3050','双向委托返显确认',139,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3051','双向委托',140,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3052','外币余额查询',141,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3053','外汇买卖:委托校验返显',142,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3054','外汇买卖:明细查询返显',143,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3055','外汇买卖:单笔明细查询',144,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3056','外汇买卖:余额明细打印',145,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3057','未知联行号转账银行代码返显列表',146,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3058','未知联行号转账反显列表',147,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','3059','未知联行号转账手续费计算',148,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2615','货款支付',149,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2616','货款支付查询',150,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2618','货款支付查询',151,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values('etbTrscode','2605','货款支付撤消',152,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbSource','01','电话支付',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbSource','02','第三方消费',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbSource','03','第三方支付',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','0','管理',0,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','1','消费',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','2','转账',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','3','查询',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','4','缴费',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','5','贷记卡',5,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','6','预授权',6,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','7','CTS',7,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','8','账户金',8,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','9','基金',9,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','10','外汇买卖',10,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','13','助农余额查询',13,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','14','助农取款',14,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransType','15','银联跨行转账',15,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransRes','0','未果',0,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransRes','1','成功',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransRes','2','失败',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransRes','3','冲正',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbTransRes','4','预授权完成',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbCompStatus','0','未对账',0,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbCompStatus','1','对账成功',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbCompStatus','2','账务不匹配',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('etbCompStatus','3','ETB有银行没有',3,1);

DELETE FROM m30_plat_params WHERE param_no IN ('93','94','95','97','98','99','1106','111','112') AND branch_code='010000000';
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','93','IABS系统访问地址 ','11.152.83.60:1341');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','94','ATMVH系统地址及端口 ','11.152.83.67:1380');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','95','IABS用户复核需查询的角色','''211'''||','||'''212''');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','97','IABS系统访问链接 ','/Iabms_WEB/loginAction.do?ATMPARAM=');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','98','本系统报表工程访问链接 ','/reportShow/outSystemAccess.jsp?PARAM=');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','99','本系统报表工程地址 ','11.152.83.67:1382');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','1106', 'ETB流水查询菜单地址','screen/report/infoQuery/etbJournalQuery.jsp');
insert into m30_plat_params (branch_code,param_no,param_name,param_int,param_char) values ('010000000','111','表branch_running_time导入方式 ', 2, 'branch_running_time');
insert into m30_plat_params (branch_code,param_no,param_name,param_char,param_double) values ('010000000','112','设备故障联动监控报警条件 ','20','0.5');

--补丁
delete from m30_plat_params where branch_code='010000000' and param_no in (116,96,93,94);
insert into m30_plat_params (branch_code,param_no,param_name,param_int,param_char) values ('010000000',116,'办公网本系统地址 ',1,'11.152.83.67,11.152.67.66,atmvh');
insert into m30_plat_params (branch_code,param_no,param_name,param_int,param_char) values ('010000000',96,'生产网本系统地址 ',0,'11.152.51.52,11.152.35.35,scatmvh');
insert into m30_plat_params (branch_code,param_no,param_name,param_char,spare_str1) values ('010000000',94,'本系统报表工程地址','11.152.51.52:1342','11.152.83.67:1382');
insert into m30_plat_params (branch_code,param_no,param_name,param_char,spare_str1) values ('010000000',93,'IABS系统访问地址 ','11.152.51.51:1321','11.152.83.60:1341');
-----201404end

-----201403begin
update ws_case_config set BRANCH_LEVEL=(select BRANCH_LEVEL from (select branch_code branchcode,BRANCH_LEVEL from branch_msg) where branch_code=branchcode)
update ws_case_config set step5_time=(select sendwaittime3 from (select casetype case_type,waittime,managertime,sendwaittime1,sendwaittime2,sendwaittime3 from ws_timeout) where case_type=casetype);
update ws_case_config set end_time=(select endtime from (select casetype case_type,endtime from ws_alarmtime) where case_type=casetype);

insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','1','第一联系人',1,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','2','第二联系人',2,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','3','网点主任',3,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','4','二级分行',4,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','5','一级分行',5,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','6','三级维护商',6,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','7','二级维护商',7,1);
insert into sys_datadictionary (dd_dictname,dd_id,dd_text,dd_order,dd_enabled) values ('contact_level','8','一级维护商',8,1);

UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,4,LENGTH(CASENAME)) WHERE SUBSTR(CASENAME,1,3)<'999';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,5,LENGTH(CASENAME)) WHERE SUBSTR(CASENAME,1,4) IN ('北京','广东','山东');
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,3,LENGTH(CASENAME)) WHERE CASENAME LIKE '行%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,0,8) WHERE CASENAME LIKE '钞箱故障%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,0,8) WHERE CASENAME LIKE '通讯故障%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,0,8) WHERE CASENAME LIKE '模块故障%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,0,14) WHERE CASENAME LIKE '收条打印机故障%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,0,14) WHERE CASENAME LIKE '流水打印机故障%';
UPDATE WS_CASE SET CASENAME=SUBSTR(CASENAME,4,LENGTH(CASENAME)) WHERE SUBSTR(CASENAME,1,3)<'ZZZ';
UPDATE WS_CASE SET CASENAME='收条打印机故障' WHERE CASENAME='收条打印机故';
UPDATE WS_CASE SET CASENAME='设备状态未知' WHERE CASENAME='设备运行状态未知';
UPDATE WS_CASE SET CASENAME='钞箱钞尽' WHERE CASENAME in ('钞尽','钞箱状态钞尽','钞箱状态钞少','钞箱状态钞箱空');
UPDATE WS_CASE SET CASENAME='钞箱故障' WHERE CASENAME='钞箱状态故障';
UPDATE WS_CASE SET CASENAME='钞箱正常' WHERE CASENAME='钞箱状态正常';
UPDATE WS_CASE SET CASENAME='存款钞箱满' WHERE CASENAME='存款钞箱存款钞箱满';
UPDATE WS_CASE SET CASENAME='存款模块故障' WHERE CASENAME in ('存款模块严重错误','存款模块存款器故障');
UPDATE WS_CASE SET CASENAME='废钞箱故障' WHERE CASENAME='废钞箱状态不存在';
UPDATE WS_CASE SET CASENAME='废钞箱满' WHERE CASENAME='废钞箱状态废钞箱满';
UPDATE WS_CASE SET CASENAME='废钞箱故障' WHERE CASENAME='废钞箱状态故障未知';
UPDATE WS_CASE SET CASENAME='扩展模块故障' WHERE CASENAME='扩展状态故障';
UPDATE WS_CASE SET CASENAME='扩展模块正常' WHERE CASENAME='扩展状态正常';
UPDATE WS_CASE SET CASENAME='模块故障' WHERE CASENAME='模块状态故障';
UPDATE WS_CASE SET CASENAME='取款模块故障' WHERE CASENAME='取款模块严重错误';
UPDATE WS_CASE SET CASENAME='设备正常' WHERE CASENAME='设备运行状态正常运行';
UPDATE WS_CASE SET CASENAME='设备关闭' WHERE CASENAME='设备运行状态服务关闭';  
UPDATE WS_CASE SET CASENAME='设备维护' WHERE CASENAME='设备运行状态维护';
UPDATE WS_CASE SET CASENAME='设备未知故障' WHERE CASENAME='设备状态未知';
UPDATE WS_CASE SET CASENAME='通讯故障' WHERE CASENAME='通讯状态通讯故障'; 
UPDATE WS_CASE SET CASENAME='通讯正常' WHERE CASENAME='通讯状态通讯正常';  
UPDATE WS_CASE SET CASENAME='通讯中断' WHERE CASENAME='通讯状态通讯中断';
UPDATE WS_CASE SET CASENAME='读卡器故障' WHERE CASENAME='读卡器读卡器故障';
-----201403end
