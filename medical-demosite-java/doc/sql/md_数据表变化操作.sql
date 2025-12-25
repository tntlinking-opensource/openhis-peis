-- 更改`md_peispatient`.`numorgresv`字段为varchar类型
ALTER TABLE `medicalcenter`.`md_peispatient`
    MODIFY COLUMN `numorgresv` varchar (64) NULL DEFAULT NULL COMMENT '订单号' AFTER `numorg`;
-- 更改`md_peispatient`.`md_peispatient`字段为tinyint类型
ALTER TABLE `medicalcenter`.`md_peispatient`
    MODIFY COLUMN `id_marriage` tinyint(1) NULL DEFAULT NULL COMMENT '婚姻状况：1.未婚 2.已婚 3.离异 4.丧偶 5.其他' AFTER `ageofreal`;

-- JC检查项目表：md_basexamltem
-- 字段变化
ALTER TABLE `medical_dev`.`md_basexamltem`
    ADD COLUMN `item_flag` tinyint(1) NULL COMMENT '项目标识：0.普通 1.特殊' AFTER `add_unit`;

-- JC收费项目表：md_items
-- 修改字段名称
ALTER TABLE `medical_dev`.`md_items`
    CHANGE COLUMN `x_xxdm` `creater` varchar (64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人' AFTER `group_order`;

-- 基础收费项目和分中心关联：md_items_and_fzx
-- 删除和新增编辑字段
ALTER TABLE `medical_dev`.`md_items_and_fzx`
DROP
COLUMN `tbzt`,
MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID' FIRST,
MODIFY COLUMN `items_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收费项目ID' AFTER `id`,
MODIFY COLUMN `fzx_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分中心ID' AFTER `items_id`,
ADD COLUMN `f_discountdisabled` tinyint(1) NULL DEFAULT 0 COMMENT '是否禁止打折：0.否 1.是' AFTER `fzx_id`,
ADD COLUMN `unitprice` decimal(10, 2) NULL COMMENT '价格' AFTER `f_discountdisabled`,
ADD COLUMN `suiteprice` decimal(10, 2) NULL COMMENT '套餐价格' AFTER `unitprice`,
ADD COLUMN `materialprice` decimal(10, 2) NULL COMMENT '材料价格' AFTER `suiteprice`,
ADD COLUMN `costprice` decimal(10, 2) NULL COMMENT '成本价格' AFTER `materialprice`,
ADD COLUMN `coopprice` decimal(10, 2) NULL COMMENT '外部价格' AFTER `costprice`,
ADD COLUMN `id_cooporg` varchar(255) NULL COMMENT '外送机构ID' AFTER `coopprice`,
ADD COLUMN `zk` decimal(2, 2) NULL COMMENT '折扣' AFTER `id_cooporg`,
ADD COLUMN `wbjg` decimal(10, 2) NULL COMMENT '外宾价' AFTER `zk`,
ADD COLUMN `ydjg` decimal(10, 2) NULL COMMENT '优待价' AFTER `wbjg`,
ADD COLUMN `nbj` decimal(10, 2) NULL COMMENT '内部价' AFTER `ydjg`,
ADD COLUMN `is_ban` tinyint(1) NULL DEFAULT 0 COMMENT '是否禁检：0.否 1.是' AFTER `nbj`,
ADD UNIQUE INDEX `idx_items_id_fzx_id`(`items_id`, `fzx_id`) USING BTREE;

-- 基础收费项目和分中心关联：md_exam_and_fzx
-- 修改名字：md_exam_and_fzx  ->  md_basexamltem_and_fzx
-- 删除和新增编辑字段
ALTER TABLE `medical_dev`.`md_basexamltem_and_fzx`
DROP
COLUMN `tbzt`,
MODIFY COLUMN `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '记录ID' FIRST,
ADD COLUMN `interface_code` varchar(255) NULL COMMENT '接口代码' AFTER `fzx_id`,
ADD COLUMN `is_out` tinyint(1) NULL COMMENT '是否外送：0.否 1.是' AFTER `interface_code`,
MODIFY COLUMN `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间' AFTER `is_out`,
MODIFY COLUMN `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间' AFTER `createdate`;

-- CRM相关的表调整
-- 客户公共池表：md_clientcommon -> 客户公共池：crm_clientcommon
ALTER TABLE `medicalcenter`.`md_clientcommon`
DROP
COLUMN `zybwhysrs`,
DROP
COLUMN `zybwhzycss`,
DROP
COLUMN `zybwhyslb`,
DROP
COLUMN `zybwhys`,
DROP
COLUMN `gylc`,
DROP
COLUMN `zyyfl`,
DROP
COLUMN `tjttlx`,
DROP
COLUMN `zycp`,
MODIFY COLUMN `khdwmc` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户单位名称' AFTER `id`,
MODIFY COLUMN `khdwsrm` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '客户单位输入码' AFTER `khdwmc`,
MODIFY COLUMN `fzxid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心ID' AFTER `khdwdz`,
MODIFY COLUMN `bz` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注' AFTER `scgrs`,
MODIFY COLUMN `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除' AFTER `khsctjdwdz`,
MODIFY COLUMN `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间' AFTER `is_delete`,
MODIFY COLUMN `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期' AFTER `createdate`,
ADD INDEX `idx_khdwmc`(`khdwmc`),
ADD INDEX `idx_khdwsrm`(`khdwsrm`),
ADD INDEX `idx_is_delete`(`is_delete`),
ADD INDEX `idx_fzxid`(`fzxid`);

-- 我的客户表：md_sellcustomer
-- 修改名字：crm_sellcustomer -> md_sellcustomer

-- 客户公共池领取与领取人员关联表：md_receiveandsell
-- 修改名字：crm_receiveandsell -> md_receiveandsell

-- 体检者表：md_peispatient
ALTER TABLE `medicalcenter`.`md_peispatient`
DROP
COLUMN `id_patient`,
DROP
COLUMN `patientcodeprn`,
DROP
COLUMN `patientarchiveno`,
DROP
COLUMN `patientcardno`,
DROP
COLUMN `dailynumberdate`,
DROP
COLUMN `dailynumber`,
DROP
COLUMN `numtotal`,
DROP
COLUMN `numyear`,
DROP
COLUMN `nummonth`,
DROP
COLUMN `numday`,
DROP
COLUMN `numorg`,
DROP
COLUMN `id_patientlinked`,
DROP
COLUMN `id_nonorg`,
DROP
COLUMN `org_departsuba`,
DROP
COLUMN `org_departsubb`,
DROP
COLUMN `org_departsubc`,
DROP
COLUMN `org_departsubd`,
DROP
COLUMN `org_departsube`,
DROP
COLUMN `offpercent`,
DROP
COLUMN `maxoffpercent`,
DROP
COLUMN `sex`,
DROP
COLUMN `id_ageunit`,
DROP
COLUMN `ageunit`,
DROP
COLUMN `ageofreal`,
DROP
COLUMN `id_education`,
DROP
COLUMN `education`,
DROP
COLUMN `id_occupation`,
DROP
COLUMN `occupation`,
DROP
COLUMN `dateinorganization`,
DROP
COLUMN `datecreated`,
DROP
COLUMN `position_code`,
DROP
COLUMN `jobtype_code`,
DROP
COLUMN `moneyamount`,
DROP
COLUMN `moneyamountpaid`,
DROP
COLUMN `workno`,
DROP
COLUMN `id_doctorconclusion`,
DROP
COLUMN `doctorconclusion`,
DROP
COLUMN `f_feecharged`,
DROP
COLUMN `id_doctorfee`,
DROP
COLUMN `doctorfee`,
DROP
COLUMN `f_finalapproved`,
DROP
COLUMN `id_doctorfinalapproved`,
DROP
COLUMN `datefinalapproved`,
DROP
COLUMN `f_cardissued`,
DROP
COLUMN `f_cardreturned`,
DROP
COLUMN `f_coverprinted`,
DROP
COLUMN `f_reportprinted`,
DROP
COLUMN `id_reportprintedby`,
DROP
COLUMN `datereportprinted`,
DROP
COLUMN `f_reportinformed`,
DROP
COLUMN `datereportinformed`,
DROP
COLUMN `f_reportfetched`,
DROP
COLUMN `datereportfetched`,
DROP
COLUMN `f_issevere`,
DROP
COLUMN `f_needtraced`,
DROP
COLUMN `f_diffperson`,
DROP
COLUMN `confidentiallevel`,
DROP
COLUMN `f_settleall`,
DROP
COLUMN `signature`,
DROP
COLUMN `dt_lastmodifiedthisat`,
DROP
COLUMN `f_inneroper`,
DROP
COLUMN `severedegree`,
DROP
COLUMN `severedegreenote`,
DROP
COLUMN `f_severeinformed`,
DROP
COLUMN `severeinformtime`,
DROP
COLUMN `id_severeinformby`,
DROP
COLUMN `conclusion`,
DROP
COLUMN `conclusionsummary`,
DROP
COLUMN `suggestion`,
DROP
COLUMN `conclusionrich`,
DROP
COLUMN `dietguide`,
DROP
COLUMN `sportguide`,
DROP
COLUMN `message`,
DROP
COLUMN `positivesummary`,
DROP
COLUMN `resultcompare`,
DROP
COLUMN `interfacemarks`,
DROP
COLUMN `patientflag`,
DROP
COLUMN `timeresultlastchange`,
DROP
COLUMN `timeresultlastarchive`,
DROP
COLUMN `timeresultlastolap`,
DROP
COLUMN `hospitalname`,
DROP
COLUMN `f_pdfcreated`,
DROP
COLUMN `f_wordcreated`,
DROP
COLUMN `id_patientclass2`,
DROP
COLUMN `f_printcomparingreport`,
DROP
COLUMN `f_guidancereturned`,
DROP
COLUMN `id_guidancereturnedby`,
DROP
COLUMN `f_forpreparefinancialconfirm`,
DROP
COLUMN `keybirthplace`,
DROP
COLUMN `keybloodtype`,
DROP
COLUMN `exammethod`,
DROP
COLUMN `keypayway`,
DROP
COLUMN `healthcard`,
DROP
COLUMN `exampoint`,
DROP
COLUMN `fingerprint`,
DROP
COLUMN `countreportcoverprinted`,
DROP
COLUMN `countreportprinted`,
DROP
COLUMN `countreportpdf`,
DROP
COLUMN `countreportword`,
DROP
COLUMN `countreportcomparepdf`,
DROP
COLUMN `countreportcompareword`,
DROP
COLUMN `countreportcomparexml`,
DROP
COLUMN `countreportoccupationword`,
DROP
COLUMN `ever_of_disease`,
DROP
COLUMN `ccnl`,
DROP
COLUMN `jq`,
DROP
COLUMN `zq`,
DROP
COLUMN `tjnl`,
DROP
COLUMN `family_number`,
DROP
COLUMN `zc`,
DROP
COLUMN `sc`,
DROP
COLUMN `lc`,
DROP
COLUMN `jt`,
DROP
COLUMN `ywrc`,
DROP
COLUMN `abstain_smoke_note`,
DROP
COLUMN `everyday_smoke_n`,
DROP
COLUMN `smoke_year`,
DROP
COLUMN `no_kiss_the_cup`,
DROP
COLUMN `between_kiss_the_cup`,
DROP
COLUMN `evermore_kiss`,
DROP
COLUMN `abstain_lost_kiss`,
DROP
COLUMN `kiss_year_n`,
DROP
COLUMN `kiss_amount`,
DROP
COLUMN `kiss_type`,
DROP
COLUMN `family_of_disease`,
DROP
COLUMN `symptom`,
DROP
COLUMN `is_audit`,
DROP
COLUMN `ever_of_disease_remark`,
DROP
COLUMN `create_report_num`,
DROP
COLUMN `picture`,
DROP
COLUMN `advice`,
DROP
COLUMN `sign_picture`,
DROP
COLUMN `is_new_pacs`,
DROP
COLUMN `committee`,
DROP
COLUMN `street`,
DROP
COLUMN `physique`,
DROP
COLUMN `doc_name`,
MODIFY COLUMN `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'XID' FIRST,
MODIFY COLUMN `examsuite_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐名称' AFTER `id_examsuite`,
MODIFY COLUMN `examsuite_alias` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家人手机号' AFTER `examsuite_name`,
MODIFY COLUMN `id_examplace` decimal(65, 30) NULL DEFAULT NULL COMMENT 'pacs登记页面已登记' AFTER `hospitalcode`,
MODIFY COLUMN `f_wordprinted` tinyint(1) NULL DEFAULT NULL COMMENT '平安报告上传标志' AFTER `patientcodehiden`,
MODIFY COLUMN `guidancenote2` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备单体检号生成人' AFTER `f_wordprinted`,
MODIFY COLUMN `f_usecodehiden` tinyint(1) NULL DEFAULT NULL COMMENT '个检团检' AFTER `guidancenote2`,
MODIFY COLUMN `id_patientclass3` int(0) NULL DEFAULT NULL COMMENT '确认来检' AFTER `f_usecodehiden`,
MODIFY COLUMN `dateregisternotime` datetime(0) NULL DEFAULT NULL COMMENT '职业总检时间' AFTER `id_patientclass3`,
MODIFY COLUMN `counterreportprinted` int(0) NULL DEFAULT NULL COMMENT '当前体检号是第几次复查' AFTER `dateregisternotime`,
MODIFY COLUMN `f_isrecheck` tinyint(1) NULL DEFAULT NULL COMMENT '是否复查：0.未复查 1. 复查' AFTER `counterreportprinted`,
MODIFY COLUMN `f_settlenone` tinyint(1) NULL DEFAULT NULL COMMENT '中间库同步标识' AFTER `f_isrecheck`,
MODIFY COLUMN `dateguidancereturned` datetime(0) NULL DEFAULT NULL COMMENT '预约时间' AFTER `f_settlenone`,
MODIFY COLUMN `f_outpatient` tinyint(1) NULL DEFAULT NULL COMMENT '第三方平台（平安、康淘等）确认：0.待确认 1.已确认' AFTER `dateguidancereturned`,
MODIFY COLUMN `patientnamereceipt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方平台（平安、康淘等）订单ID' AFTER `f_outpatient`,
MODIFY COLUMN `patientnamepinyin` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '十周年套餐id+团检专属卡套餐id+活动专属卡套餐id' AFTER `patientnamereceipt`,
MODIFY COLUMN `statusofhm` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否授权第三方平台（平安、康淘等）查看体检报告：Y.是 N.否' AFTER `patientnamepinyin`,
MODIFY COLUMN `instancetag` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方平台（平安、康淘等）验证码' AFTER `statusofhm`,
MODIFY COLUMN `inpatientno` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查体检号（第一次的体检号）' AFTER `instancetag`,
MODIFY COLUMN `insuranceno` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补检体检号（上一次体检号）' AFTER `inpatientno`,
MODIFY COLUMN `countreportxml` tinyint(1) NULL DEFAULT 0 COMMENT '是否替检：0.否 1.是' AFTER `insuranceno`,
MODIFY COLUMN `countreportcompare` int(0) NULL DEFAULT 0 COMMENT '是否交单：0.否 1.是' AFTER `countreportxml`,
MODIFY COLUMN `is_hmd` tinyint(1) NULL DEFAULT 0 COMMENT '是否黑名单：0.否 1.是' AFTER `is_hmdb`,
MODIFY COLUMN `isjj` tinyint(1) NULL DEFAULT 0 COMMENT '是否加急：0.否 1.是' AFTER `is_hmd`,
MODIFY COLUMN `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间' AFTER `original_trade`,
MODIFY COLUMN `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期' AFTER `createdate`;

--新增表md_bussiness_source
CREATE TABLE `sys_business_source`
(
    `id`         bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `source_id`  bigint               DEFAULT '0' COMMENT '第三方ID',
    `type_name`  varchar(64) NOT NULL DEFAULT '' COMMENT '第三方名称',
    `account`    varchar(64) NOT NULL DEFAULT '' COMMENT '账号',
    `password`   varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
    `logo`       varchar(255)         DEFAULT NULL COMMENT 'logo',
    `tip`        varchar(255)         DEFAULT NULL COMMENT '介绍',
    `seq`        int                  DEFAULT '0' COMMENT '排序',
    `status`     tinyint              DEFAULT '1' COMMENT '状态：-1.删除 0.关闭 1.开放中',
    `createdate` datetime    NOT NULL COMMENT '记录时间',
    `modifydate` datetime             DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_source_id` (`source_id`) USING BTREE,
    UNIQUE KEY `idx_type_name` (`type_name`) USING BTREE,
    KEY          `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='合作第三方';


--新增表md_report_config
CREATE TABLE `md_report_config`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `branch_id`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '分中心ID',
    `status`     tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '状态：0.正常 1.删除',
    `is_default` tinyint(1) DEFAULT NULL COMMENT '是否默认，1是0否，最多只能有一个',
    `content`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
    `createdate` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime                                                      DEFAULT NULL COMMENT '修改日期',
    `memo`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='报告配置';


--新增表md_code_corresponding
CREATE TABLE `md_code_corresponding`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `old_code`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '老系统体检码',
    `new_code`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '新系统体检码',
    `createdate` datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime                                                     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='新老系统体检号对应关系';