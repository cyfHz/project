select t.rowid, t.* from app_resource t;
-- Add/modify columns 
alter table APP_RESOURCE add MODULE_TAG varchar2(4);
comment on column APP_RESOURCE.MODULE_TAG is '所属子系统（模块标记）';

update APP_RESOURCE r set MODULE_TAG ='PSAM' where IMAGESMALL_VALUE='icon-kmedit';

update APP_RESOURCE r set res_order =concat('00',res_order) where  MODULE_TAG ='PSAM';
update app_resource set res_name='信息管理' ,res_desc='信息管理' where res_id='PSAM_RESOURCE_PARENT_JBXX';

insert into APP_RESOURCE (RES_ID, AREA_ID, RES_NAME, RES_DESC, RES_TYPE, RES_PATHTYPE, RES_PVALUE, RES_ORDER, RES_CODE, RES_PID, DHSXS, SFYZ, ENABLED, OPRATETIME, IS_HREF, IS_MAP_OP, MAP_OP_RESOURCE, CJ_MODE, DJR, DJDW, DJSJ, XGR, XGDW, ZHXGXJ, ZXBJ, ZXRQ, MOVESIGN, IMAGEBIG_VALUE, IMAGESMALL_VALUE,MODULE_TAG)
values ('0a070b8e-8202-4b00-a669-169a75c90835', '1', '数据统计', '数据统计', null, null, '/psam/dataStatistic/', '010', 'dataStatistic', 'PSAM_RESOURCE_PARENT_JBXX', null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 'icon-kmedit','PSAM');

update APP_RESOURCE r set IMAGESMALL_VALUE ='icon-kmhome' where res_id='PSAM_RESOURCE_PARENT_ROOT';
update APP_RESOURCE r set IMAGESMALL_VALUE ='icon-kmhome' where res_id='PSAM_RESOURCE_PARENT_JBXX';
update APP_RESOURCE r set IMAGESMALL_VALUE ='icon-kmhome' where res_id='PSAM_RESOURCE_PARENT_ZZJG';
update APP_RESOURCE r set IMAGESMALL_VALUE ='icon-kmhome' where res_id='PSAM_RESOURCE_PARENT_XXCJ';
update APP_RESOURCE r set IMAGESMALL_VALUE ='icon-kmhome' where res_id='PSAM_RESOURCE_PARENT_RZGL';
update APP_RESOURCE r set IMAGESMALL_VALUE ='icon-kmhome' where res_id='PSAM_RESOURCE_PARENT_QXGL';
update APP_RESOURCE r set IMAGESMALL_VALUE ='icon-kmhome' where res_id='PSAM_RESOURCE_PARENT_PUBLIC';