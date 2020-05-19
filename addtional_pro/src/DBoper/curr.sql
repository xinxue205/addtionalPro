select * from systables order by nrows desc;
load from db140383_073245.txt insert into ws_case_config;
truncate trans_ammsg; truncate trans_bmmsg; truncate trans_bmmsg_day; truncate trans_ammsg_day;truncate dev_daily_05;truncate dev_daily_07;
update m30_plat_params set spare_str1='128.128.97.32:8082' , param_char='128.128.200.45:8080'  where branch_code='010000000' and param_no=94

delete from sys_account where account_id='uasstest.co';
delete from sys_accountrole where account_id='uasstest.co';
DELETE from m30_approve_flow where op_code='uasstest.co';
insert into sys_account values ('test3333','',1,'2015-09-11','2014-09-11','行内实名用户','110000000',0,1)
select * from subsys_inout_config  where subsys_code='10'
11.152.51.52:1342                                  11.152.83.67:1382 
select first 1 param_char,spare_str1 from m30_plat_params where branch_code='010000000' and param_no=94
select * from trans_monitor_CONFIG
 01                  9 01          取款冲正                     NULL       NULL NULL       NULL
 01                  9 02          存款超时
select * from trans_monitor_doubtaccount where  subsys_code='01' and stat_type='9' and type_code in ('01','02') and report_date||report_time>='2014-08-15020000';
update trans_monitor_doubtaccount set spare_str1='120000' where report_time>='120000' and report_time<'120500';

update trans_monitor_doubtaccount set spare_int1='1' where report_time>='120500' and report_time<'121000';

SELECT SKIP 0 FIRST 10 DEV_CODE,CHECK_FLAG,DEV_NUMBER,DEV_NAME,PATH_ID,CLEAR_FLAG,MONEY_COUNT,LAST_COUNT,USE_DATE,CLEAR_CAUSE,DEV_STATUS,CLEAR_DATE,ADD_COUNT,CHECK_DESC FROM CLEAR_PLAN_INFO WHERE PLAN_ID='201408060001'AND CLEAR_FLAG IN ('Y','S') ORDER BY DEV_NUMBER 
SELECT a.dev_code,a.clear_date,a.add_count,a.last_count,a.use_date,spare_int2 maint_prio,a.money_count,a.path_id,a.clear_flag from clear_plan_info a where plan_id='201408060001 '

SELECT SKIP 0 FIRST 10 a.dev_code,a.clear_date,a.add_count,a.last_count,a.use_date,spare_str2 maint_prio,a.money_count,a.path_id,a.clear_flag FROM clear_plan_info a WHERE plan_id=201408190005 ORDER BY spare_int2, dev_code  

SELECT SKIP 0 FIRST 10 P.*,DP.NOUSE_NUM,AP.PATH_NAME,D.DEV_MAKE_NAME,B.DEV_NAME FROM ADDMONEY_PATH_INFO P,M30_ADDMONEY_PATH AP, M30_DEV_PARA DP,DEV_MAKE_LIST D,DEV_BMSG B WHERE P.PATH_ID=AP.PATH_ID AND P.DEV_BRAND=D.DEV_MAKE  AND P.PLAN_ID = '201408190005' AND P.DEV_MODEL = 4 AND P.DEV_CODE=B.DEV_CODE AND P.DEV_CODE=DP.DEV_CODE ORDER BY PATH_ID, DEV_NUMBER

select spare_int1,a.endtime from subsys_msg left join (select max(total_date||total_minute) endtime from m31_last_time_info where table_name='trans_monitor_branch_01') a on 1=1 where subsys_code='"01'
select count(*) flag,'' dummy from sys_accountrole where account_id='jzsq' and role_id in (select dd_id from sys_datadictionary where dd_dictname='togeACRole')

select * from sys_accountrole where subsys_code='01' and type_code='210000000' and report_time='145400'
SELECT * FROM sys_accountrole WHERE account_id = 'uasstest.co'
select * from 
delete from sys_account where account_id='uasstest.co';
delete from sys_accountrole where account_id='uasstest.co';
select count(*) flag,'' dummy  from sys_accountrole where account_id='hh' and role_id in (select dd_id from sys_datadictionary where dd_dictname='togeACRole');
select dd_id from sys_datadictionary where dd_dictname='togeACRole'
 
select * from user_path
select branch_code,casetype,(dev_class||'-'||ptsid||'-'||stateval) falg from ws_case_config where dev_branch1='110000000'
select * from dev_bmsg where dev_code='440660101911';
insert into dev_maintplan values (440000000,440660000,1,1,440660101911,1,1,1,1,1,1,1,1,1,1,1,1);
440000000   440600000
select * from  ws_case_config where dev_branch1='110000000' order by dev_class,ptsid


select * from trans_monitor_branch_day  where report_date='2014-07-22' and report_time>='100000' and report_time<='105959' and subsys_code='10'
SELECT SKIP 0 FIRST 10 d.stat_type,a.type_code,d.type_name,a.in_sys_code,b.sys_cn_name insysname, a.out_sys_code,c.sys_cn_name outsysname,trans_count, suc_count,fail_count,timeout_count,sys_rej_count,overtime_count,bal_count, proc_time,resp_time,report_time FROM trans_monitor_branch_day a left join subsys_inout_config b on a.subsys_code=b.subsys_code and b.sys_code=a.in_sys_code left join subsys_inout_config c on a.subsys_code=c.subsys_code and c.sys_code=a.out_sys_code left join trans_monitor_config d on a.subsys_code=d.subsys_code and d.type_code=a.type_code and d.stat_type='02' where report_date='2014-07-22' and report_time>='100000' and report_time<='105959' and a.subsys_code='10' ORDER BY a.report_date,a.report_time,a.type_code 
select * from dev_params_detail where params_name in ('ATMBranchName','ATMBranchAddress')
select * from dev_params_info
select '*' type_code,'全行' type_name,1 iotype,'--' in_sys_code,'--' sys_cn_name,0 spare_int1,sum(trans_count) counts from trans_monitor_errcode where report_date||report_time='2014-07-17175100' and subsys_code='04' union all select a.type_code,b.type_name,2 iotype,'--' in_sys_code,'--' sys_cn_name,b.spare_int1,sum(a.trans_count) counts from trans_monitor_errcode a left join trans_monitor_config b on a.stat_type=b.stat_type and a.subsys_code=b.subsys_code and a.type_code=b.type_code where report_date||report_time='2014-07-17175100' and a.subsys_code='04' group by a.type_code,b.type_name,b.spare_int1 order by spare_int1 desc,counts desc

select '*' type_code,'全行' type_name,1 iotype,'--' in_sys_code,'--' sys_cn_name,0 spare_int1,0 type,sum(trans_count) counts from trans_monitor_errcode where report_date||report_time='2014-07-15165200' and subsys_code='04' 
union all 
select a.type_code,b.type_name,2 iotype,'--' in_sys_code,'--' sys_cn_name,b.spare_int1,1 type,sum(a.trans_count) counts from trans_monitor_errcode a left join trans_monitor_config b on a.stat_type=b.stat_type and a.subsys_code=b.subsys_code and a.type_code=b.type_code where report_date||report_time='2014-07-15165200' and a.subsys_code='04' group by a.type_code,b.type_name,b.spare_int1 order by type,spare_int1

select a.type_code,b.type_name,2 iotype,'--' in_sys_code,'--' sys_cn_name,'--' spare_int1,b.spare_int1,sum(a.trans_count) counts from trans_monitor_errcode a left join trans_monitor_config b on a.stat_type=b.stat_type and a.subsys_code=b.subsys_code and a.type_code=b.type_code where report_date||report_time='2014-07-15164700' and a.subsys_code='04' group by a.type_code,b.type_name,b.spare_int1 order by spare_int1
select * from subsys_inout_config where subsys_code='10'
select thirdparty_flag||'|'||sys_code id,'['||dd_text||']'||sys_cn_name text 
from subsys_inout_config,(select dd_id,dd_text from sys_datadictionary where dd_dictname='transMonitorIO') 
where thirdparty_flag=dd_id and subsys_code='10'

select * from trans_monitor_ap_
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01', 9, '01','取款冲正', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01', 9, '02','存款超时', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01', 9, '03','吞卡    ', null, null, null, null);
insert into trans_monitor_config (subsys_code, stat_type, type_code, type_name, spare_int1, spare_int2, spare_str1, spare_str2) values ('01', 9, '03','吞卡    ', null, null, null, null);

select a.type_code,b.type_name,1 iotype,a.in_sys_code,c.sys_cn_name,c.spare_int1,sum(a.trans_count) from trans_monitor_errcode a
left join trans_monitor_config b on a.stat_type=b.stat_type and a.subsys_code=b.subsys_code and a.type_code=b.type_code
left join subsys_inout_config c on a.subsys_code=c.subsys_code and a.in_sys_code=c.sys_code
where report_date||report_time='2014-07-08161000' and a.subsys_code='01' 
group by a.type_code,b.type_name,a.in_sys_code,c.sys_cn_name,c.spare_int1
union all 
select a.type_code,b.type_name,2 iotype,a.out_sys_code in_sys_code,c.sys_cn_name,c.spare_int1,sum(a.trans_count) from trans_monitor_errcode a
left join trans_monitor_config b on a.stat_type=b.stat_type and a.subsys_code=b.subsys_code and a.type_code=b.type_code
left join subsys_inout_config c on a.subsys_code=c.subsys_code and a.out_sys_code=c.sys_code
where report_date||report_time='2014-07-08161000' and a.subsys_code='01' 
group by a.type_code,b.type_name,a.out_sys_code,sys_cn_name,c.spare_int1
union all 
select '*' ,'全行',1 iotype,a.in_sys_code,c.sys_cn_name,c.spare_int1,sum(a.trans_count) from trans_monitor_errcode a
left join trans_monitor_config b on a.stat_type=b.stat_type and a.subsys_code=b.subsys_code and a.type_code=b.type_code
left join subsys_inout_config c on a.subsys_code=c.subsys_code and a.in_sys_code=c.sys_code
where report_date||report_time='2014-07-08161000' and a.subsys_code='01' 
group by a.in_sys_code,c.sys_cn_name,c.spare_int1
union all 
select '*' ,'全行',1 iotype,a.out_sys_code in_sys_code,c.sys_cn_name,c.spare_int1,sum(a.trans_count) from trans_monitor_errcode a
left join trans_monitor_config b on a.stat_type=b.stat_type and a.subsys_code=b.subsys_code and a.type_code=b.type_code
left join subsys_inout_config c on a.subsys_code=c.subsys_code and a.out_sys_code=c.sys_code
where report_date||report_time='2014-07-08161000' and a.subsys_code='01' 
group by a.out_sys_code,sys_cn_name,c.spare_int1
order by spare_int1


select type_code,in_sys_code,out_sys_code,sum(a.trans_count) from trans_monitor_errcode a
where report_date||report_time='2014-07-08161000' and a.subsys_code='01'  group by a.type_code,a.in_sys_code,a.out_sys_code
select first 5 report_date||report_time from trans_monitor_errcode where report_date||report_time<='2014-07-10111111' order by report_date||report_time desc;
select branch_code,param_no,param_name,param_int,param_char from m30_plat_params where param_no='34' and param_int='1' and branch_code!='010000000'
select branch_code,param_no,param_name,param_int,param_char from m30_plat_params where param_no='34' and param_int='1' and branch_code!='010000000' order by branch_code;
select 1 io_type,c.type_code,c.in_sys_code in_sys_code,a.sys_cn_name,a.spare_int1,sum(c.trans_count) trans_count,sum(c.suc_count) suc_count,sum(c.fail_count) fail_count,
sum(c.timeout_count) timeout_count,sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,sum(c.bal_count) bal_count,
case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,
case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev from trans_monitor_branch c 
left join subsys_inout_config a on a.subsys_code=c.subsys_code and c.in_sys_code=a.sys_code 
where c.subsys_code='10' and c.report_date||c.report_time='2014-07-08174101' group by c.type_code,c.in_sys_code,a.sys_cn_name,a.spare_int1 
union all 
select 2 io_type,c.type_code,c.out_sys_code,a.sys_cn_name,a.spare_int1,sum(c.trans_count) trans_count,sum(c.suc_count) suc_count,
sum(c.fail_count) fail_count,sum(c.timeout_count) timeout_count,sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,
sum(c.bal_count) bal_count,case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,
case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev from trans_monitor_branch c 
left join subsys_inout_config a on a.subsys_code=c.subsys_code and c.out_sys_code=a.sys_code 
where c.subsys_code='10' and c.out_sys_code!='99' and c.report_date||c.report_time='2014-07-08174101' 
group by c.type_code,c.out_sys_code,a.sys_cn_name,a.spare_int1 order by io_type,a.spare_int1

select * from trans_monitor_branch where subsys_code='10'
where trans_count='0@1@0@010000-020000@00-24'
update trans_monitor_quota set trans_count=trans_count||'@00-24'

select * from w_msgej where untid > '42200000000' and untid<'42300000000';
insert into w_msgej values ('422006201101','20140701','120129','10000001541369','郭少珍','13112298118','05','5353','3','1','0');
select * from dev_bmsg where dev_code > '42200000000' and dev_code<'42300000000'

select skip 0 first 50 ct.casetype,ct.serno,ct.stepdate1,ct.steptime1,ct.stepdate5,ct.steptime5,s.dd_text,d.* 
from wl_casestart ct,sys_datadictionary s, 
(select dev_code,dev_name,manager,phone from dev_bmsg  where dev_branch1='420000000'  and dev_branch2='422000000' ) d,ws_case_config e 
where ct.casestate=s.dd_id and s.dd_dictname='CASESTATE' and ct.untid=d.dev_code and ct.casetype=e.casetype  
and ct.stepdate1>='20140602'  and ct.stepdate1<='20140701' 

select * from ws_case_config where branch_code like '422%'
select skip 0 first 50 ct.casetype,ct.serno,ct.stepdate1,ct.steptime1,ct.stepdate5,ct.steptime5,s.dd_text,d.* from wl_casestart ct,sys_datadictionary s, (select dev_code,dev_name,manager,phone from dev_bmsg  where dev_branch1='420000000'  and dev_branch2='422000000' ) d,ws_case_config e where ct.casestate=s.dd_id and s.dd_dictname='CASESTATE' and ct.untid=d.dev_code and ct.casetype=e.casetype   and ct.stepdate1>='20140602'  and ct.stepdate1<='20140701'

select a.dd_dictname dd_id,b.dd_id||'|'||b.dd_text dd_value,b.dd_order from sys_datadictionary a,
(select dd_desc,dd_id,dd_text,dd_order FROM sys_datadictionary where dd_dictname='ChannelMonitor_Type') b 
where b.dd_desc=a.dd_id  and a.dd_text='TRANS_REL_COMPARE' order by a.dd_dictname,b.dd_order;

select * from sys_datadictionary where dd_dictname='ChannelMonitor_Type'
select * from sys_datadictionary where dd_text='TRANS_REL_COMPARE'

select spare_int1,last_check_time endtime from subsys_msg where subsys_code='01';
select param_char from  m30_plat_params where branch_code = '010000000' and param_no = 909;
update m30_plat_params set param_char='${name}你好，你正使用运维系统,本次验证码为：${code}' where branch_code = '010000000' and param_no = 909;
select param_char from  m30_plat_params where branch_code = '010000000' and param_no = 909 

select * from subsys_resp_config;
select * from dev_smsg where dev_code='110000101197';
SELECT * FROM dev_tr_list ORDER BY tr_id;
select dev_type,igr_modu,igr_code,igr_class,igr_flag from  m30_ignore_list where dev_type='4'
select * from DEV_CLASS_LIST;

select 2 io_type,c.out_sys_code,a.sys_cn_name,sum(c.trans_count) trans_count,case when sum(c.trans_count)==0 then 0 else sum(c.suc_count)*100/sum(c.trans_count) end suc_count,case when sum(c.trans_count)==0 then 0 else sum(c.fail_count)*100/sum(c.trans_count) end fail_count,sum(c.timeout_count) timeout_count,sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,sum(c.bal_count) bal_count,case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev from trans_monitor_branch c left join subsys_inout_config a on a.subsys_code=c.subsys_code and c.out_sys_code=a.sys_code and a.thirdparty_flag=2 where c.subsys_code='01' and c.type_code='110000000' and c.report_date||c.report_time='2014-06-10164100' group by c.out_sys_code,a.sys_cn_name
select * from subsys_inout_config where subsys_code='01'
select * from trans_monitor_branch where subsys_code='01' and type_code='110000000' and report_time='164100'

select * from sys_datadictionary where dd_text='TRANS_REL_COMPARE'
select type_code,type_name
delete from trans_monitor_config where subsys_code='01' and stat_type=
and stat_type=(select dd_order from sys_datadictionary where dd_dictname='ChannelMonitor_Type' and dd_id=)
select * from sys_datadictionary where dd_dictname='ChannelMonitor_Type'

delete from trans_monitor_branch where report_time<'163000'
select count(report_time) from trans_monitor_branch where report_time='164300'
select max(report_time) from trans_monitor_branch where report_date='2014-06-10'
delete from trans_monitor_branch where report_date!='2014-06-10'

select * from dev_dispatch
SELECT SKIP 0 FIRST 50 a.type_code,d.type_name,a.in_sys_code,b.sys_cn_name insysname, 
a.out_sys_code,c.sys_cn_name outsysname,trans_count, suc_count,fail_count,timeout_count,
sys_rej_count,overtime_count,bal_count, proc_time,resp_time,report_time 
FROM trans_monitor_ap_day a left join subsys_inout_config b on a.subsys_code=b.subsys_code 
and b.thirdparty_flag=1 and b.sys_code=a.in_sys_code left join subsys_inout_config c 
on a.subsys_code=c.subsys_code and c.thirdparty_flag=2 and c.sys_code=a.out_sys_code 
left join trans_monitor_config d on a.subsys_code=d.subsys_code and d.type_code=a.type_code 
where report_date='2014-06-10' and report_time>='152222' and report_time<='153000' 
and a.subsys_code='04' ORDER BY a.report_date,a.report_time,a.type_code 

update trans_monitor_quota set type_code='*' where subsys_code='01' and stat_type='2'

SELECT a.type_code,d.type_name,a.in_sys_code,b.sys_cn_name insysname, 
a.out_sys_code,c.sys_cn_name outsysname,trans_count, suc_count,fail_count,
timeout_count,sys_rej_count,overtime_count,bal_count, proc_time,resp_time,
report_time FROM trans_monitor_branch_day a left join subsys_inout_config b 
on a.subsys_code=b.subsys_code 
and b.thirdparty_flag=1 and b.sys_code=a.in_sys_code left join subsys_inout_config c 
on a.subsys_code=c.subsys_code and c.thirdparty_flag=1 and c.sys_code=a.out_sys_code 
left join trans_monitor_config d on a.subsys_code=d.subsys_code 
and d.type_code=a.type_code where report_date||report_time>='2014-06-09080000' 
and report_date||report_time<='2014-06-10090000' 
and a.subsys_code='01' ORDER BY a.report_date,a.report_time,a.type_code 

select first 2 alarm_time,alarm_context from sys_alarm_data 
where subsys_code='01' and alarm_flag=1 and stat_type=2 order by alarm_time desc,stat_type
select min(alarm_time) from sys_alarm_data

select first 2 alarm_time,alarm_context from sys_alarm_data where subsys_code='01' and alarm_flag=1 and stat_type=2 order by alarm_time desc,stat_type
select sys_chinese_name,message_send_flag,sys_service_num,sys_db_num from subsys_msg where subsys_code='01'
select max(total_date||total_minute) endtime from m31_last_time_info where table_name='trans_monitor_ap_01';
select * from subsys_msg

select * from trans_monitor_ap  
select * from trans_monitor_branch

SELECT a.type_code,d.type_name,a.in_sys_code,b.sys_cn_name insysname, 
a.out_sys_code,c.sys_cn_name outsysname,trans_count, suc_count,fail_count,
timeout_count,sys_rej_count,overtime_count,bal_count, proc_time,resp_time,
report_time FROM trans_monitor_branch_day a left join subsys_inout_config b 
on a.subsys_code=b.subsys_code 
and b.thirdparty_flag=1 and b.sys_code=a.in_sys_code left join subsys_inout_config c 
on a.subsys_code=c.subsys_code and c.thirdparty_flag=1 
and c.sys_code=a.out_sys_code 
left join trans_monitor_config d on a.subsys_code=d.subsys_code 
and d.type_code=a.type_code where report_date='2014-06-09' and report_time>='080000' 
and report_time<='090000' and a.subsys_code='01' ORDER BY a.report_date,a.report_time,a.type_code 

select a.subsys_code,a.stat_type,dd_text,a.type_code,c.type_name,trans_count,suc_count,
fail_count,timeout_count,sys_rej_count,overtime_count,bal_count,proc_time,resp_time,
inner_time,inout_count,inout_suc_count 
from (select dd_order,dd_text from sys_datadictionary where dd_dictname='ChannelMonitor_Type') b,
trans_monitor_quota a 
left join trans_monitor_config c on a.subsys_code=c.subsys_code 
and a.stat_type=c.stat_type and a.type_code=c.type_code where a.stat_type=b.dd_order 
and a.subsys_code='04'  and quota_type='2' order by a.stat_type,a.type_code

select * from trans_monitor_config where subsys_code='04'  and quota_type='2'

select a.type_code,d.type_name,a.in_sys_code,b.sys_cn_name insysname, a.out_sys_code,c.sys_cn_name outsysname,trans_count, suc_count,fail_count,timeout_count,sys_rej_count,overtime_count,bal_count, case when suc_count==0 then 0 else proc_time/suc_count end proc_time, case when suc_count==0 then 0 else resp_time/suc_count end resp_time, report_time from trans_monitor_branch_day a left join subsys_inout_config b on a.subsys_code=b.subsys_code and b.thirdparty_flag=1 and b.sys_code=a.in_sys_code left join subsys_inout_config c on a.subsys_code=c.subsys_code and c.thirdparty_flag=1 and c.sys_code=a.out_sys_code left join trans_monitor_config d on a.subsys_code=d.subsys_code and d.type_code=a.type_code where report_date||report_time>='2014-06-03080000' and report_date||report_time<='2014-06-03170000' and a.subsys_code='01' order by a.report_date,a.report_time

select * from trans_monitor_branch_his set 
update m31_last_time_info set total_minute='160000' where table_name='trans_monitor_branch_01'

select count(*) from trans_monitor_detail
select * from trans_monitor_branch_day a where report_date||report_time>='2014-06-03080000' and report_date||report_time<='2014-06-03170000' 
and a.subsys_code='01' order by a.report_date,a.report_time

select * FROM sys_datadictionary where dd_dictname='ChannelMonitor_Type'
select a.dd_dictname dd_id,b.dd_order||'|'||b.dd_text dd_value from sys_datadictionary a,
(select dd_desc,dd_order,dd_text FROM sys_datadictionary where dd_dictname='ChannelMonitor_Type') b where 
b.dd_desc=a.dd_id  and a.dd_text='TRANS_REL_COMPARE'
tname='ChannelMonitor_Type' and dd_desc in (select dd_id from sys_datadictionary where dd_text='TRANS_REL_COMPARE' and dd_dictname='01');
select * from trans_monitor_config where subsys_code=
select * from trans_monitor_quota
SELECT  type_code, type_name  FROM trans_monitor_config    WHERE   (subsys_code='04' and stat_type='trans_monitor_ap')         ORDER BY type_code

select a.subsys_code,a.stat_type,dd_text,a.type_code,c.type_name,a.quota_type,a.in_sys_code,a.out_sys_code,d.sys_cn_name,
trans_count,suc_count,fail_count,timeout_count,sys_rej_count,overtime_count,bal_count,proc_time,resp_time,inner_time 
from (select dd_order,dd_text from sys_datadictionary where dd_dictname='ChannelMonitor_Type') b, trans_monitor_quota a 
left join trans_monitor_config c on a.subsys_code=c.subsys_code and a.stat_type=c.stat_type and a.type_code=c.type_code 
left join subsys_inout_config d on a.subsys_code=d.subsys_code and a.in_sys_code=d.thirdparty_flag and a.out_sys_code=d.sys_code 
where a.stat_type=b.dd_order and a.subsys_code='01' and quota_type='2' order by a.stat_type,a.type_code

select * from dev_smsg
select * from dev_bmsg

select * from ws_case_config
select * from wd_devgztj
select * from wd_devfaultstat
select * from wd_casestart
select * from wl_casestart
select * from Wd_newcase
select * from wsmsg;
select * from wsparts;
select * from wsmsgdtl;
select * from wlparts;
select * from wlevent;
select * from ws_case;
select * from ws_timeout;
select * from ws_alarmtime
select * FROM wsdev_msgsend_control
select * from W_MSGEJ

select * FROM CARD_TYPE_LIST
select * from ws_case_config where dev_branch1 like '422%'
update ws_case_config set branch_level='2' where branch_code='422000000'

SELECT  * FROM (
select a.plan_id,a.dev_code,a.path_id,a.money_count FROM clear_plan_info a ,m30_clear_plan b  
where a.plan_id=b.plan_id and a.clear_flag in ('Y','S') and b.plan_status=3  and b.date_jc='20140701'  
AND b.branch='420000000' ) e , (select c.dev_code,c.spare_str1 as dev_number,c.dev_name 
as place,d.period as finish_date,d.dev_status as status,d.boxs_init1*d.boxs_denom1+d.boxs_init2*d.boxs_denom2+d.boxs_init3*d.boxs_denom3+d.boxs_init4*d.boxs_denom4 as addmoney 
FROM dev_bmsg c,dev_smsg d where c.dev_code=d.dev_code  AND c.dev_branch1='420000000' ) 
f where e.dev_code=f.dev_code

select * from m30_dev_para
select * from m30_dev_safeopens


select c.type_code,b.type_name,c.stat_type,sum(c.trans_count) trans_count,sum(c.suc_count) suc_count,sum(c.fail_count) fail_count,
sum(c.timeout_count) timeout_count,sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,
sum(c.bal_count) bal_count,case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,
case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev 
from trans_monitor_branch c left join trans_monitor_config b on c.subsys_code=b.subsys_code and c.stat_type=b.stat_type and 
c.type_code=b.type_code where c.subsys_code='01' and c.type_code!='99' and c.report_date||c.report_time='2014-07-04161500' 
group by c.type_code,b.type_name,c.stat_type order by c.type_code
union all 
select 'all','汇总',c.stat_type,sum(c.trans_count) trans_count,sum(c.suc_count) suc_count,sum(c.fail_count) fail_count,
sum(c.timeout_count) timeout_count,sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,sum(c.bal_count) bal_count,
case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,
case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev from trans_monitor_branch c 
where c.subsys_code='01' and c.type_code!='99' and c.report_date||c.report_time='2014-07-04161500' 
group by c.stat_type 

select 1 io_type,'*' type_code,c.in_sys_code in_sys_code,a.sys_cn_name,a.spare_int1,sum(c.trans_count) trans_count,sum(c.suc_count) suc_count,
sum(c.fail_count) fail_count,sum(c.timeout_count) timeout_count,sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,
sum(c.bal_count) bal_count,case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,
case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev from trans_monitor_branch c 
left join subsys_inout_config a on a.subsys_code=c.subsys_code and c.in_sys_code=a.sys_code where c.subsys_code='01' and c.type_code!='99' 
and c.report_date||c.report_time='2014-07-07150400' group by c.in_sys_code,a.sys_cn_name,a.spare_int1 union all 
select 2 io_type,'*' type_code,c.out_sys_code in_sys_code,a.sys_cn_name,a.spare_int1,sum(c.trans_count) trans_count,sum(c.suc_count) suc_count,
sum(c.fail_count) fail_count,sum(c.timeout_count) timeout_count,sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,
sum(c.bal_count) bal_count,case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,
case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev from trans_monitor_branch c 
left join subsys_inout_config a on a.subsys_code=c.subsys_code and c.out_sys_code=a.sys_code where c.subsys_code='01' 
and c.type_code!='99' and c.report_date||c.report_time='2014-07-07150400' group by c.out_sys_code,a.sys_cn_name,a.spare_int1
order by a.spare_int1

select c.type_code,b.type_name,c.stat_type,sum(c.trans_count) trans_count,sum(c.suc_count) suc_count,sum(c.fail_count) fail_count,
sum(c.timeout_count) timeout_count,sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,sum(c.bal_count) bal_count,
case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,
case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev from trans_monitor_errcode c 
left join trans_monitor_config b on c.subsys_code=b.subsys_code and c.stat_type=b.stat_type and c.type_code=b.type_code 
where c.subsys_code='01' and c.type_code!='99' and c.report_date||c.report_time='2014-07-07150400' 
group by c.type_code,b.type_name,c.stat_type trans_count 
union all 
select '*' type_code,'全行',c.stat_type,sum(c.trans_count) trans_count,sum(c.suc_count) suc_count,sum(c.fail_count) fail_count,
sum(c.timeout_count) timeout_count,sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,sum(c.bal_count) bal_count,
case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,
case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev from trans_monitor_errcode c 
where c.subsys_code='01' and c.type_code!='99' and c.report_date||c.report_time='2014-07-07150400' group by c.stat_type 
order by type_code

select sum(suc_count),sum(proc_time),sum(resp_time) from trans_monitor_doubtaccount c 
where c.subsys_code='01' and c.out_sys_code!='99' and c.report_date||c.report_time='2014-07-15173600' 

select sum(trans_count) from (select distinct in_sys_code,trans_count from trans_monitor_doubtaccount c 
where c.subsys_code='01' and c.out_sys_code!='99' and c.report_date||c.report_time='2014-07-15173600')

select '*' type_code,'全行' type_name,9 stat_type,'' spare_int1,a.trans_count trans_count,b.suc_count suc_count,
case when suc_count==0 then 0 else b.proc_time/b.suc_count end proc_timev,
case when suc_count==0 then 0 else b.resp_time/b.suc_count end resp_timev 
from (select sum(trans_count) trans_count from (select distinct in_sys_code,trans_count from trans_monitor_doubtaccount c where c.subsys_code='01' and c.out_sys_code!='99' and c.report_date||c.report_time='2014-07-15182500' )) a,(select sum(suc_count) suc_count,sum(proc_time) proc_time,sum(resp_time) resp_time from trans_monitor_doubtaccount c where c.subsys_code='01' and c.out_sys_code!='99' and c.report_date||c.report_time='2014-07-15182500') b

select 1 io_type,'*' type_code,c.in_sys_code in_sys_code,a.sys_cn_name,a.spare_int1,avg(c.trans_count) trans_count,
sum(c.suc_count) suc_count,sum(c.fail_count) fail_count,sum(c.timeout_count) timeout_count,
sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,sum(c.bal_count) bal_count,
case when sum(c.suc_count)==0 then 0 else sum(c.proc_time)/sum(c.suc_count) end proc_timev,
case when sum(c.suc_count)==0 then 0 else sum(c.resp_time)/sum(c.suc_count) end resp_timev 
from trans_monitor_doubtaccount c left join subsys_inout_config a on a.subsys_code=c.subsys_code and c.in_sys_code=a.sys_code where c.subsys_code='01' and c.report_date||c.report_time='2014-07-16085000' group by c.in_sys_code,a.sys_cn_name,a.spare_int1 order by in_sys_code

select * from trans_monitor_doubtaccount c where c.subsys_code='01' and c.report_date||c.report_time='2014-07-16085000'

select '*' type_code,'全行' type_name,9 stat_type,'' spare_int1,a.trans_count trans_count,b.suc_count,b.proc_time,b.resp_time from (
select sum(trans_count) trans_count from (
select distinct in_sys_code,trans_count from trans_monitor_doubtaccount c 
where c.subsys_code='01' and c.out_sys_code!='99' and c.report_date||c.report_time='2014-07-15173600'
)) a,(select sum(suc_count) suc_count,sum(proc_time) proc_time,sum(resp_time) resp_time from trans_monitor_doubtaccount c 
where c.subsys_code='01' and c.out_sys_code!='99' and c.report_date||c.report_time='2014-07-15173600') b

trans_monitor_branch_01
select '*' type_code,'全行' type_name,1 iotype,'--' in_sys_code,'--' sys_cn_name,0 spare_int1,sum(trans_count) counts from trans_monitor_errcode where report_date||report_time='2014-07-17175100' and subsys_code='04' union all select a.type_code,b.type_name,2 iotype,'--' in_sys_code,'--' sys_cn_name,b.spare_int1,sum(a.trans_count) counts from trans_monitor_errcode a left join trans_monitor_config b on a.stat_type=b.stat_type and a.subsys_code=b.subsys_code and a.type_code=b.type_code where report_date||report_time='2014-07-17175100' and a.subsys_code='04' group by a.type_code,b.type_name,b.spare_int1 order by spare_int1,counts desc

select '*' type_code,'全行' type_name,c.stat_type,'' spare_int1,avg(c.trans_count) trans_count,sum(c.suc_count) suc_count,
case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,
case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev 
from trans_monitor_doubtaccount c where c.subsys_code='01' and c.out_sys_code!='99' 
and c.report_date||c.report_time='2014-07-15173600' 
update ws_case_config set branch_level='2' where branch_code='422000000'
select * from trans_monitor_config where stat_type=5 
update trans_monitor_config set spare_int1=null where stat_type=5;
update trans_monitor_config set spare_int1=14 where stat_type=5 and type_code='00-*****';
update trans_monitor_config set spare_int1=15 where stat_type=5 and type_code='**-*****';
select max(total_date||total_minute) endtime from m31_last_time_info where table_name='trans_monitor_ap_09'
select spare_int1,a.endtime from subsys_msg left join (select max(total_date||total_minute) endtime from m31_last_time_info where table_name='trans_monitor_ap_09') a on 1=1 where subsys_code='09'

update M30_CLEAR_PLAN set plan_status='2' where plan_id='201408140003  '

select * from user_data_prompt where plan_id='201408140001  ' and dev_code='322001100802 '
SELECT SKIP 0 FIRST 10 d.stat_type,a.type_code,d.type_name,a.in_sys_code,b.sys_cn_name insysname, a.out_sys_code,c.sys_cn_name outsysname,trans_count, suc_count,fail_count,timeout_count,sys_rej_count,overtime_count,bal_count, proc_time,resp_time,report_time FROM trans_monitor_doubtaccount_day a left join subsys_inout_config b on a.subsys_code=b.subsys_code and b.sys_code=a.in_sys_code left join subsys_inout_config c on a.subsys_code=c.subsys_code and c.sys_code=a.out_sys_code left join trans_monitor_config d on a.subsys_code=d.subsys_code and d.type_code=a.type_code and d.stat_type='09' where report_date='2014-07-21' and report_time>='140000' and report_time<='145959' and a.subsys_code='01' ORDER BY a.report_date,a.report_time,a.type_code 
SELECT PLAN_ID,A.DEV_CODE,CHECK_FLAG,DEV_NUMBER,DEV_NAME,A.PATH_ID,MONEY_COUNT,A.LAST_COUNT,USE_DATE,CLEAR_CAUSE,DEV_STATUS,CLEAR_DATE,ADD_COUNT,CHECK_DESC,A.SPARE_STR1 FROM CLEAR_PLAN_INFO A,M30_DEV_PARA B WHERE A.DEV_CODE = B.DEV_CODE AND PLAN_ID='201408010003' AND CLEAR_FLAG IN ('Y','S') AND CHECK_FLAG!='1' ORDER BY CHECK_FLAG, PATH_ID, DEV_NUMBER ASC
update CLEAR_PLAN_INFO set CLEAR_FLAG='1' WHERE  PLAN_ID='201408120001' 

SELECT SKIP 0 FIRST 100 PLAN_ID,A.DEV_CODE,CHECK_FLAG,DEV_NUMBER,DEV_NAME,A.PATH_ID,MONEY_COUNT,A.LAST_COUNT,USE_DATE,CLEAR_CAUSE,DEV_STATUS,CLEAR_DATE,ADD_COUNT,CHECK_DESC,A.SPARE_STR1,CLEAR_FLAG FROM CLEAR_PLAN_INFO A WHERE PLAN_ID='201408140003' AND CLEAR_FLAG IN ('1','2') AND CHECK_FLAG!='1' ORDER BY CHECK_FLAG, PATH_ID, DEV_NUMBER ASC 
select * from CLEAR_PLAN_INFO where plan_id='201408140001 '
SELECT SKIP 0 FIRST 10 PLAN_ID,A.DEV_CODE,CHECK_FLAG,DEV_NUMBER,DEV_NAME,A.PATH_ID,MONEY_COUNT,
A.LAST_COUNT,USE_DATE,CLEAR_CAUSE,DEV_STATUS,CLEAR_DATE,ADD_COUNT,CHECK_DESC,A.SPARE_STR1,CLEAR_FLAG 
FROM CLEAR_PLAN_INFO A,M30_DEV_PARA B WHERE A.DEV_CODE = B.DEV_CODE AND PLAN_ID='201408140001' 
AND CLEAR_FLAG IN ('1','2') AND CHECK_FLAG!='1' ORDER BY CHECK_FLAG, PATH_ID, DEV_NUMBER ASC 
select * from CLEAR_PLAN_INFO
AND PLAN_ID='201408140001' AND CLEAR_FLAG IN ('1','2') AND CHECK_FLAG!='1' ORDER BY CHECK_FLAG, PATH_ID, DEV_NUMBER ASC 
select * from m31_last_time_info where table_name='trans_monitor_ap_08'
select 1 io_type,'*' type_code,c.in_sys_code in_sys_code,a.sys_cn_name,a.spare_int1,sum(c.trans_count) trans_count,
sum(c.suc_count) suc_count,sum(c.fail_count) fail_count,sum(c.timeout_count) timeout_count,
sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,sum(c.bal_count) bal_count,
case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,
case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev 
from trans_monitor_ap c left join subsys_inout_config a on a.subsys_code=c.subsys_code and c.in_sys_code=a.sys_code 
and a.thirdparty_flag=1 
where c.subsys_code='08' and c.report_date||c.report_time='2014-08-14105812' 
group by c.in_sys_code,a.sys_cn_name,a.spare_int1 
union all 
select 2 io_type,'*' type_code,c.out_sys_code in_sys_code,a.sys_cn_name,a.spare_int1,sum(c.trans_count) trans_count,sum(c.suc_count) suc_count,sum(c.fail_count) fail_count,sum(c.timeout_count) timeout_count,sum(c.sys_rej_count) sys_rej_count,sum(c.overtime_count) overtime_count,sum(c.bal_count) bal_count,case when sum(c.trans_count)==0 then 0 else sum(c.proc_time)/sum(c.trans_count) end proc_timev,case when sum(c.trans_count)==0 then 0 else sum(c.resp_time)/sum(c.trans_count) end resp_timev 
from trans_monitor_ap c left join subsys_inout_config a on a.subsys_code=c.subsys_code and c.out_sys_code=a.sys_code and a.thirdparty_flag=2 where c.subsys_code='08' and c.out_sys_code!='99' and c.report_date||c.report_time='2014-08-14105812' group by c.out_sys_code,a.sys_cn_name,a.spare_int1 order by io_type,a.spare_int1


 2      分行类
 3      AP类
 4      交易码类
 5      错误码类
 7      卡标志类
 8      卡类型类
 9      可疑账务类
 01          ATMPH                                              总行ATM交易系统
 04          ICCS                                               IC卡验证系统
 07          ETB                                                电话POS
 08          TSM                                                移动支付
 09          CQSMH                                              网点服务管理系统
 10          CQSMC                                              排队机
 03          CQSM                                               排队机系统
 
 ------curr Start-------
INSERT INTO sys_datadictionary ( dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled ) VALUES ('togeACRole','160','集中清机角色','申请人员',null,1,1);
INSERT INTO sys_datadictionary ( dd_dictname, dd_id, dd_text, dd_desc, dd_flag, dd_order, dd_enabled ) VALUES ('togeACRole','161','集中清机角色','审核人员',null,2,1);
insert into m30_plat_params (branch_code,param_no,param_name,param_int,param_char) values ('010000000','170','实名认证开关及相关信息',0,'11.152.83.60:1341');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','171','实名认证已备案的关联系统编号','01,');
insert into m30_plat_params (branch_code,param_no,param_name,param_char) values ('010000000','172','实名认证已备案的关联系统名称','TSM,');
ALTER TABLE sys_account ADD (account_inner smallint DEFAULT 0);----行内用户，1-行内，0-非行内

--回退
delete from sys_datadictionary where dd_dictname='ChannelMonitorNew_Type';
delete from sys_datadictionary where dd_dictname='togeACRole';

--iwapdemo.dwr.xml
<!-- ********行内用户申请Action类 add by wuxx 20140904 ******** -->
<create creator="new" javascript="InnerUserApply" scope="session">
	<param name="class" value="com.nantian.atmvh.system.InnerUserApply" />
</create>

--web.xml
<!--用户实名验证  add by wuxinxue 20140902 -->
	<servlet>
		<servlet-name>UserRealNameAuth</servlet-name>
		<servlet-class>com.nantian.atmvh.common.util.UserRealNameAuthServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>UserRealNameAuth</servlet-name>
		<url-pattern>/UserRealNameAuth</url-pattern>
	</servlet-mapping>
  ------curr End-------
  
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

--iwapdemo.dwr.xml
<!-- ******** 全渠道监控新方式 add by wxx 20141013 ******* -->
<create creator="new" javascript="AllChannelMonitorNew" scope="session">
	<param name="class" value="com.nantian.atmvh.monitor.syshealth.action.AllChannelMonitorNew" />
</create>
---------201411end------
