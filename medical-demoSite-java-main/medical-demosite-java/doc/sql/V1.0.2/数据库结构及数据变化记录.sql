#
2023/8/11 是否已同步更新：是
ALTER TABLE `sys_table_config`
    ADD COLUMN `sync_condition` tinyint(1) NULL DEFAULT 0 COMMENT '是否存在同步需要满足的条件' AFTER `need_sync`;
#
2023/8/11 是否已同步更新：是
ALTER TABLE `sys_branch`
    MODIFY COLUMN `is_default` tinyint(1) NULL DEFAULT 0 COMMENT '只能有一个为1的分中心,当前数据库的分中心' AFTER `address`,
    MODIFY COLUMN `f_pay_online` tinyint(1) NULL DEFAULT 0 COMMENT '是否支持在线支付：0.否 1.是' AFTER `branch_type`,
    MODIFY COLUMN `is_show` tinyint(1) NULL DEFAULT 0 COMMENT '是否展示：0.否 1.是' AFTER `pyjm`,
    MODIFY COLUMN `is_wechat_app` tinyint(1) NULL DEFAULT 0 COMMENT 'is_wechat_app' AFTER `brief_fzx`;
ALTER TABLE `sys_branch`
    MODIFY COLUMN `is_delete` tinyint(1) ZEROFILL NULL DEFAULT 0 COMMENT '是否删除：0.未删除 1.已删除' AFTER `pid`,
    MODIFY COLUMN `is_default` tinyint(1) ZEROFILL NULL DEFAULT 0 COMMENT '只能有一个为1的分中心,当前数据库的分中心' AFTER `address`,
    MODIFY COLUMN `f_pay_online` tinyint(1) ZEROFILL NULL DEFAULT 0 COMMENT '是否支持在线支付：0.否 1.是' AFTER `branch_type`,
    MODIFY COLUMN `is_show` tinyint(1) ZEROFILL NULL DEFAULT 0 COMMENT '是否展示：0.否 1.是' AFTER `pyjm`,
    MODIFY COLUMN `is_wechat_app` tinyint(1) ZEROFILL NULL DEFAULT 0 COMMENT 'is_wechat_app' AFTER `brief_fzx`;
#
2023/8/17 是否已同步更新：是
ALTER TABLE `md_createmeal`
    MODIFY COLUMN `zhjgapp ` decimal(16, 4) DEFAULT NULL COMMENT '团惠价，优惠价不包括备选项目（app使用）'

# 2023/8/23 更新md_reservation_setting主键 是否已同步更新：否
ALTER TABLE `md_reservation_setting`
    MODIFY COLUMN `id` bigint UNSIGNED NOT NULL COMMENT 'id' FIRST;

#
2023/8/24 是否已同步更新：是
ALTER TABLE `md_report`
    ADD COLUMN `sign_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '签名图片路径';

#
2023/8/24 是否已同步更新：是
ALTER TABLE `md_bk_patient`
    MODIFY COLUMN `phone ` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话';

#
2023/8/24 是否已同步更新：是
ALTER TABLE `md_createmeal`
    MODIFY COLUMN `fzxid` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID：创建者所在的中心' AFTER `xsjlid`;

#
2023/8/25 是否已同步更新：是
ALTER TABLE `md_createorder`
    MODIFY COLUMN `bgmemo` text COMMENT '变更备注';

#
2023/8/24 是否已同步更新：是
ALTER TABLE `md_fx_completion`
    ADD COLUMN `idcardno` varchar(64) DEFAULT NULL COMMENT '身份证号';

#
2023/8/24 是否已同步更新：是
ALTER TABLE `md_bk_patient`
    MODIFY COLUMN `phone ` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话';

#
2023/8/24 是否已同步更新：是
ALTER TABLE `md_createmeal`
    MODIFY COLUMN `fzxid` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID：创建者所在的中心' AFTER `xsjlid`;

#
2023/8/30 是否已同步更新：是
ALTER TABLE `md_reservation_setting`
    MODIFY COLUMN `id` varchar(32) NOT NULL COMMENT 'id' FIRST;
ALTER TABLE `md_reservation`
    MODIFY COLUMN `time_id` varchar(32) NOT NULL COMMENT '预约时间段ID' AFTER `reservation_date`;
ALTER TABLE `md_reservation_default`
    MODIFY COLUMN `id` varchar(32) NOT NULL COMMENT 'id' FIRST;
ALTER TABLE `md_reservation`
    MODIFY COLUMN `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '预约id' FIRST;

#
2023/8/30 是否已同步更新：是
DELETE
FROM `md_reservation_setting`;
ALTER TABLE `md_reservation_setting`
    MODIFY COLUMN `start_time` varchar(16) NOT NULL COMMENT '开始时间' AFTER `reservation_date`,
    MODIFY COLUMN `end_time` varchar(16) NOT NULL COMMENT '结束时间' AFTER `start_time`;
ALTER TABLE `md_sync_data`
    MODIFY COLUMN `biz_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联的数据ID集合' AFTER `biz_table`;
ALTER TABLE `md_reservation`
    MODIFY COLUMN `status` tinyint(1) NOT NULL COMMENT '预约状态：-2.没来检 -1.预约失败 1.待预约 2.已预约 3.预约结束 ' AFTER `remark`,
    MODIFY COLUMN `sex` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '性别：0.男 1.女 2.未知' AFTER `f_usecodehiden`,
    ADD COLUMN `finished_time` datetime NULL COMMENT '到检时间' AFTER `operator`;

#
2023/8/31 是否已同步更新：是
CREATE TABLE `md_reservation_group`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团体预约id',
    `branch_id`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分中心ID',
    `level_id`         int                                                          NOT NULL COMMENT '预约类型ID',
    `level_name`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '预约类型名称',
    `reservation_date` datetime                                                     NOT NULL COMMENT '预约时间',
    `order_num`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '订单号',
    `order_name`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '订单名称',
    `combo_id`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '套餐ID',
    `tjtcmc`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '套餐名称',
    `xsjl_id`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '销售经理ID',
    `xsjl_name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '销售经理',
    `f_usecodehiden`   tinyint(1) unsigned zerofill                                  DEFAULT '0' COMMENT '订单类型：0.个检 1.团检',
    `count_am`         int                                                          NOT NULL COMMENT '预约人数(上午)',
    `count_pm`         int                                                          NOT NULL COMMENT '预约人数(下午)',
    `finished_am`      int                                                          NOT NULL COMMENT '来捡人数(上午)',
    `finished_pm`      int                                                          NOT NULL COMMENT '来捡人数(下午)',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `status`           tinyint(1)                                                   NOT NULL COMMENT '来捡状态：1.暂无来捡 2.已部分来捡 3.全部来捡',
    `is_delete`        tinyint(1) unsigned zerofill                                  DEFAULT '0' COMMENT '是否已删除：0.否 1.是',
    `creator_id`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '预约人id',
    `creator`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '预约人',
    `createdate`       datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `modifydate`       datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `modifier`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '更新者id',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_reservation_date` (`reservation_date`) USING BTREE,
    KEY `idx_level_id` (`level_id`) USING BTREE,
    KEY `idx_order_num` (`order_num`) USING BTREE,
    KEY `idx_xsjl_id` (`xsjl_id`) USING BTREE,
    KEY `idx_is_delete` (`is_delete`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='团体预约记录';

INSERT INTO `sys_table_config` (`id`, `db_name`, `table_name`, `key_name`, `cid_name`, `pid`, `pkey_name`,
                                `cur_key_name`, `sync_type`, `need_sync`, `sync_condition`, `createdate`, `modifydate`)
VALUES (515, 'medicalcenter', 'md_reservation_group', 'id', 'branch_id', NULL, NULL, NULL, 3, 2, 0,
        '2023-08-31 15:49:59', NULL);

INSERT INTO `sys_table_config` (`id`, `db_name`, `table_name`, `key_name`, `cid_name`, `pid`, `pkey_name`,
                                `cur_key_name`, `sync_type`, `need_sync`, `sync_condition`, `createdate`, `modifydate`)
VALUES (516, 'medicalcenter', 'md_code_corresponding', 'id', NULL, NULL, NULL, NULL, 0, 0, 0, '2023-08-31 15:52:40',
        '2023-08-31 15:52:52');


#
2023/09/01 是否已同步更新：是
ALTER TABLE `md_report_content`
    ADD COLUMN `pic` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '图片集合';

#
2023/8/31 是否已同步更新：是
ALTER TABLE `md_reservation`
    ADD COLUMN `order_num`        varchar(32) NULL COMMENT '体检系统订单号(团检客户)' AFTER `user_id`,
    ADD COLUMN `reserve_group_id` varchar(32) NULL COMMENT '团检预约ID' AFTER `order_num`;


#
2023/09/01 是否已同步更新：否,只同步了沃德内网prod
CREATE TABLE `peis_dyd_ct_seq`
(
    `id`          VARCHAR(32) CHARACTER
        SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `patientcode` VARCHAR(64) CHARACTER
        SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '体检号',
    `seq`         VARCHAR(64) CHARACTER
        SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '排序',
    `createdate`  datetime                     DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime                     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_patientcode_seq` (`patientcode`, `seq`) USING BTREE COMMENT '体检号和排序组成的唯一记录'
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '体检者导引单CT排序';

#
2023/9/02 是否已同步更新：是
ALTER TABLE `md_reservation`
    MODIFY COLUMN `real_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名' AFTER `biz_order_num`,
    MODIFY COLUMN `idcard` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号' AFTER `real_name`;
ALTER TABLE `md_reservation_group`
    ADD COLUMN `id_org` varchar(64) NOT NULL COMMENT '团体ID' AFTER `reservation_date`,
    MODIFY COLUMN `order_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号' AFTER `reservation_date`,
    MODIFY COLUMN `xsjl_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '销售经理ID' AFTER `tjtcmc`,
    MODIFY COLUMN `creator_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '预约人id' AFTER `is_delete`;

ALTER TABLE `md_reservation_setting`
    ADD COLUMN `am_or_pm` tinyint(1) NULL DEFAULT 0 COMMENT '上午还是下午：0上午 1下午' AFTER `branch_id`;

ALTER TABLE `md_reservation_default`
    ADD COLUMN `am_or_pm` tinyint(1) NULL DEFAULT 0 COMMENT '上午还是下午：0上午 1下午' AFTER `branch_id`;

ALTER TABLE `md_reservation_setting`
    MODIFY COLUMN `able_num` int NULL DEFAULT 0 COMMENT '剩余可预约人数' AFTER `max_num`;


#
2023/9/02 是否已同步更新：是
UPDATE sys_table_config
SET sync_type = 3
WHERE table_name IN ('md_peisorgreservation', 'md_peisorgreservationgroup') # 2023/9/02 是否已同步更新：否（锦都未更新）
UPDATE `sys_table_config`
SET `need_sync` = 0
WHERE `id` = 229;
ALTER TABLE `md_savefilepath`
    ADD COLUMN `fileflag` tinyint(1) NULL DEFAULT NULL COMMENT '文件来源：1.合同 2.企业环境监测报告' AFTER `ggid`;

#
2023/9/12 是否已同步更新：否（锦都未更新）
ALTER TABLE `md_sync_data`
    ADD INDEX `idx_status_all` (`is_all`, `status`) USING BTREE;

CREATE TABLE `md_sync_file`
(
    `id`         bigint NOT NULL AUTO_INCREMENT COMMENT '操作ID',
    `is_all`     tinyint(1) DEFAULT NULL COMMENT '是否全部中心同步：0.否 1.是',
    `branch_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '同步到指定中心的ID集合',
    `status`     int        DEFAULT '0' COMMENT '同步状态：0.待同步 1.同步中 2.已完成同步 3.同步失败待重新同步 4.失效',
    `image_url`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '图片路径，多张以,隔开',
    `createdate` datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate` datetime   DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='同步文件操作';

#
2023/9/12 是否已同步更新：否
ALTER TABLE `sys_table_config`
    ADD COLUMN `file_key_name` varchar(255) NULL DEFAULT NULL COMMENT '存储文件的字段，多个以逗号隔开' AFTER `sync_condition`;


#
2023/9/12 是否已同步更新：是
CREATE TABLE `md_failed_dicom_file`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `patientcode`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '体检号',
    `jklx`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '接口类型',
    `body_part`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '部位',
    `file_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '文件名',
    `create_time`  datetime                                                     NOT NULL COMMENT '创建时间',
    `is_processed` tinyint unsigned                                             NOT NULL DEFAULT '0' COMMENT '是否已处理  0否 1是',
    `update_time`  datetime                                                     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_patientcode` (`patientcode`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_jklx` (`jklx`),
    KEY `idx_body_part` (`body_part`),
    KEY `idx_is_processed` (`is_processed`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='接收失败的dicom文件';

UPDATE `sys_table_config`
SET `cur_key_name` = 'user_no'
WHERE `id` = 328;

#
2023/9/12 是否已同步更新：否
DROP TABLE IF EXISTS `md_sync_file`;
CREATE TABLE `md_sync_file`
(
    `id`          bigint                                                NOT NULL AUTO_INCREMENT COMMENT '操作ID',
    `is_all`      tinyint(1)                                            NULL DEFAULT NULL COMMENT '是否全部中心同步：0.否 1.是',
    `branch_ids`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '同步到指定中心的ID集合',
    `image_url`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图片路径，多张以,隔开',
    `option_type` tinyint(1)                                            NULL DEFAULT NULL COMMENT '操作类型：1.新增 2.删除',
    `status`      int                                                   NULL DEFAULT 0 COMMENT '同步状态：0.待同步 1.同步中 2.已完成同步 3.同步失败待重新同步 4.失效',
    `createdate`  datetime                                              NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate`  datetime                                              NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_status` (`status` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 475
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '同步文件操作'
  ROW_FORMAT = DYNAMIC;


#
2023/9/13 是否已同步更新：是 添加索引
CREATE
    INDEX index_patientcode ON md_section_result_main (patientcode);
CREATE
    INDEX index_dep_id ON md_section_result_main (dep_id);
CREATE
    INDEX index_dept_no ON sys_dept (dept_no);
CREATE
    INDEX index_user_no ON sys_user (user_no);

#
2023/9/15 是否已同步更新：否（锦都未更新） 增加一个新字段
ALTER TABLE `sys_business_source`
    ADD COLUMN `setting` text NULL COMMENT '配置设置' AFTER `logo`;


#
2023/9/19 是否已同步更新：否 （锦都未更新）
CREATE TABLE `md_report_share_main`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `path`            varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分享链接',
    `path_name`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接名称',
    `extracted_code`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '提取码',
    `num`             int                                                           DEFAULT NULL COMMENT '已选人数',
    `expiration_date` int                                                           DEFAULT NULL COMMENT '有效期,7,14,30,999',
    `expiration_time` datetime                                                      DEFAULT NULL COMMENT '过期时间',
    `autofill`        tinyint(1)                                                    DEFAULT NULL COMMENT '自动填充 0否1是',
    `status`          tinyint(1)                                                    DEFAULT NULL COMMENT '状态 0生效1过期',
    `createdate`      datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `create_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '创建人姓名',
    `create_id`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '创建人id',
    `modifydate`      datetime                                                      DEFAULT NULL COMMENT '修改时间',
    `modify_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '修改人姓名',
    `modify_id`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '修改人id',
    `visits`          int                                                           DEFAULT NULL COMMENT '访问次数',
    `last_ip`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '最近一次访问的ip',
    `last_time`       datetime                                                      DEFAULT NULL COMMENT '最近一次访问的时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='报告分享主表';


CREATE TABLE `md_report_share_two`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `main_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'md_report_share_main表的id',
    `patientcode`  varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '体检号',
    `id_org`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '团体ID',
    `org_name`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '团体名称',
    `patientname`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
    `id_sex`       tinyint(1)                                                   DEFAULT NULL COMMENT '性别ID',
    `age`          int                                                          DEFAULT NULL COMMENT '年龄',
    `dateregister` datetime                                                     DEFAULT NULL COMMENT '登记时间',
    `createdate`   datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `create_name`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人姓名',
    `create_id`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='报告分享和报告关系表';

#
2023/9/19 是否已同步更新：否(东区线上线下prod已更新) 同步这俩个表
sys_business_source、sys_code_config

# 2023/9/21 是否已同步更新：否(东区线上线下prod已更新)
DROP TABLE IF EXISTS `md_reservation_1`;
DROP TABLE IF EXISTS `md_reservation_notify`;
CREATE TABLE `md_reservation_notify`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `biz_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '预约记录ID',
    `system_id`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统ID',
    `status`     tinyint(1) DEFAULT '0' COMMENT '状态：0.待通知 1.已通知',
    `createdate` datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate` datetime   DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `id_status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='预约信息变更通知记录';
ALTER TABLE `medical_prod`.`md_reservation`
    CHANGE COLUMN `sysytem_id` `system_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方系统ID' AFTER `mobile`;

#
2023/9/12 是否已同步更新：否(锦都未更新)
ALTER TABLE `md_peispatient_consultation`
    ADD COLUMN `other` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '其他';

#
2023/10/13 是否已同步更新：否(锦都未更新)
UPDATE `sys_table_config`
SET `sync_type` = 3
WHERE `id` = 159;
UPDATE `sys_table_config`
SET `sync_type` = 3
WHERE `id` = 158;
UPDATE `sys_table_config`
SET `pid`          = 84,
    `pkey_name`    = 'id',
    `cur_key_name` = 'dd_id',
    `sync_type`    = 3,
    `need_sync`    = 2
WHERE `id` = 14;
UPDATE `sys_table_config`
SET `sync_type` = 3
WHERE `id` = 83;
UPDATE `medical_prod`.`sys_table_config`
SET `sync_type` = 3
WHERE `id` = 19;
UPDATE `medical_prod`.`sys_table_config`
SET `sync_type` = 3
WHERE `id` = 143;
UPDATE `medical_prod`.`sys_table_config`
SET `sync_type` = 3
WHERE `id` = 142 # 2023/10/13 是否已同步更新：否(锦都未更新)
ALTER TABLE `medical_prod`.`md_peispatient_and_fzx`
    MODIFY COLUMN `patient_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者ID(md_peispatient的id)' AFTER `modifydate`;


#
2023/10/19 是否已同步更新：否(锦都未更新)
ALTER TABLE `medical_prod`.`sys_config`
    MODIFY COLUMN `config_value` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '参数键值';


#2023
/10/19 是否已同步更新：是
CREATE
    INDEX index_branch_id ON sys_branch (branch_id);


#2023
/10/27 是否已同步更新：是
UPDATE `sys_table_config`
SET `pkey_name` = 'user_no'
WHERE `id` = 330 # 2023/11/04 是否已同步更新：否（锦都未更新）
UPDATE `medical_prod`.`sys_table_config`
SET `db_name`        = 'medicalcenter',
    `table_name`     = 'md_user_harm_class',
    `key_name`       = 'id',
    `cid_name`       = NULL,
    `pid`            = 328,
    `pkey_name`      = 'user_no',
    `cur_key_name`   = 'user_id',
    `sync_type`      = 1,
    `need_sync`      = 2,
    `sync_condition` = 0,
    `file_key_name`  = NULL,
    `createdate`     = '2023-07-10 11:19:06',
    `modifydate`     = '2023-11-04 10:33:46'
WHERE `id` = 261;
UPDATE `medical_prod`.`sys_table_config`
SET `db_name`        = 'medicalcenter',
    `table_name`     = 'sys_user',
    `key_name`       = 'user_id',
    `cid_name`       = '',
    `pid`            = 329,
    `pkey_name`      = 'user_id',
    `cur_key_name`   = 'user_no',
    `sync_type`      = 1,
    `need_sync`      = 2,
    `sync_condition` = 0,
    `file_key_name`  = 'avatar,picture,sign_pic',
    `createdate`     = '2023-07-10 11:19:07',
    `modifydate`     = '2023-09-13 19:02:55'
WHERE `id` = 328;
UPDATE `medical_prod`.`sys_table_config`
SET `db_name`        = 'medicalcenter',
    `table_name`     = 'sys_user_dep',
    `key_name`       = 'id',
    `cid_name`       = NULL,
    `pid`            = 328,
    `pkey_name`      = 'user_no',
    `cur_key_name`   = 'user_id',
    `sync_type`      = 1,
    `need_sync`      = 2,
    `sync_condition` = 0,
    `file_key_name`  = '',
    `createdate`     = '2023-07-10 11:19:07',
    `modifydate`     = '2023-11-04 10:33:45'
WHERE `id` = 330;

#
2023/11/06 是否已同步更新：否（锦都未更新）
ALTER TABLE `md_peisorgreservationreceipt`
    ADD COLUMN `fzxid` varchar(64) NOT NULL COMMENT '分中心ID' AFTER `id`,
    ADD INDEX `ix_fzxid` (`fzxid`);


#
2023/11/07 是否已同步更新：是
CREATE TABLE `md_specimen_type`
(
    `id`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `code`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '代码',
    `specimen_name`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '样本名称',
    `national_standard_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国标代码',
    `his_code`               varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'his代码',
    `status`                 tinyint                                                       DEFAULT '1' COMMENT '状态：-1.删除 0.下线 1.正常',
    `pinyin_code`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '拼音码',
    `five_stroke_code`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '五笔码',
    `custom_code`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '自定义码',
    `category`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '类别',
    `createdate`             datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `modifydate`             datetime                                                      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='标本种类';


#
2023/11/06 是否已同步更新：是
ALTER TABLE `md_items`
    ADD COLUMN `specimen_type_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标本种类代码，md_specimen_type表中的code';


#
2023/11/10 是否已同步更新：否（锦都未更新）
ALTER TABLE `md_createcombo`
    MODIFY COLUMN `tjtcsrm` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '体检套餐输入码',

    # 2023/11/11 是否已同步更新：否（锦都未更新）
    ALTER TABLE `md_sync_data`
    MODIFY COLUMN `biz_id` longtext NOT NULL COMMENT '关联的数据ID集合' AFTER `biz_table`;
ALTER TABLE `md_peispatient`
    MODIFY COLUMN `id_payway` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '支付方式ID' AFTER `have_private`;

#
2023/11/14 是否已同步更新：否（锦都未更新）
CREATE TABLE `md_workflow`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'ID',
    `fzxid`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '分中心ID',
    `flow_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作流名称',
    `type_flag`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '类型标识',
    `remark`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
    `status`     tinyint(1)                                                    DEFAULT NULL COMMENT '状态：0.关闭 1.启用',
    `createdate` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `creator`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '创建人',
    `modifydate` datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `modifier`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `ix_fzxid_type_flag` (`fzxid`, `type_flag`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='工作流';

CREATE TABLE `md_workflow_case`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '实例ID',
    `fzxid`      varchar(64) COLLATE utf8mb4_general_ci                        NOT NULL COMMENT '分中心ID',
    `biz_id`     varchar(64) COLLATE utf8mb4_general_ci                        DEFAULT NULL COMMENT '关联的业务ID',
    `flow_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '工作流ID',
    `case_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
    `type_flag`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '类型标识',
    `remark`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
    `fail_text`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '驳回的原因',
    `all_level`  int                                                           DEFAULT '1' COMMENT '总进度',
    `cur_level`  int                                                           DEFAULT '1' COMMENT '当前处理进度（已处理完的，0表示尚未开始）',
    `status`     tinyint(1)                                                    DEFAULT '0' COMMENT '状态：0.等待开始 1.进行中 2.已通过 3.被驳回',
    `createdate` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `creator`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '创建人',
    `modifydate` datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `modifier`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `ix_flow_id` (`flow_id`) USING BTREE,
    KEY `ix_status_type_flag` (`status`, `type_flag`) USING BTREE,
    KEY `ix_fzxid` (`fzxid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='工作流实例记录';

CREATE TABLE `md_workflow_case_item`
(
    `item_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '实例节点ID',
    `case_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '实例ID',
    `item_name`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '节点名称',
    `user_no`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '操作人编号',
    `user_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '操作人',
    `level`         int                                                           DEFAULT '1' COMMENT '层级',
    `level_flag`    tinyint(1)                                                    DEFAULT NULL COMMENT '层级标识：0.中间层级 1.第一级 2.最后一级 3.只有一级',
    `remark`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
    `notify_status` tinyint(1)                                                    DEFAULT NULL COMMENT '通知状态：0.待通知 1.已通知',
    `notify_time`   datetime                                                      DEFAULT NULL COMMENT '通知时间',
    `status`        tinyint(1)                                                    DEFAULT '0' COMMENT '状态：0.等待开始(上一级还未通过) 1.进行中(上一级已通过) 2.已通过 3.被驳回',
    `createdate`    datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime                                                      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`item_id`) USING BTREE,
    KEY `ix_case_id` (`case_id`) USING BTREE,
    KEY `ix_user_no` (`user_no`) USING BTREE,
    KEY `ix_status` (`status`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='实例节点';

CREATE TABLE `md_workflow_item`
(
    `item_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '工作流节点ID',
    `flow_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '工作流ID',
    `item_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '节点名称',
    `user_no`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '操作人编号',
    `user_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '操作人',
    `level`      int        DEFAULT '1' COMMENT '层级',
    `level_flag` tinyint(1) DEFAULT NULL COMMENT '层级标识：0.中间层级 1.第一级 2.最后一级 3.只有一级',
    `createdate` datetime   DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime   DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`item_id`) USING BTREE,
    KEY `ix_flow_id` (`flow_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='工作流节点';

CREATE TABLE `md_workflow_type`
(
    `type_id`    int                                                          NOT NULL AUTO_INCREMENT COMMENT '类型ID',
    `type_flag`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型标识',
    `type_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '类型名称',
    `remark`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT '' COMMENT '备注',
    `createdate` datetime                                                              DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime                                                              DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`type_id`) USING BTREE,
    UNIQUE KEY `ix_type_flag` (`type_flag`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='工作流类型';

INSERT INTO `sys_table_config` (`id`, `db_name`, `table_name`, `key_name`, `cid_name`, `pid`, `pkey_name`,
                                `cur_key_name`, `sync_type`, `need_sync`, `sync_condition`, `file_key_name`,
                                `createdate`, `modifydate`)
VALUES (519, 'medicalcenter', 'md_workflow_type', 'type_id', NULL, NULL, NULL, NULL, 3, 0, 0, NULL,
        '2023-11-10 11:19:06', '2023-11-10 15:31:38');
INSERT INTO `sys_table_config` (`id`, `db_name`, `table_name`, `key_name`, `cid_name`, `pid`, `pkey_name`,
                                `cur_key_name`, `sync_type`, `need_sync`, `sync_condition`, `file_key_name`,
                                `createdate`, `modifydate`)
VALUES (520, 'medicalcenter', 'md_workflow', 'id', 'fzxid', NULL, NULL, NULL, 1, 2, 0, NULL, '2023-11-10 11:19:06',
        '2023-11-10 15:31:38');
INSERT INTO `sys_table_config` (`id`, `db_name`, `table_name`, `key_name`, `cid_name`, `pid`, `pkey_name`,
                                `cur_key_name`, `sync_type`, `need_sync`, `sync_condition`, `file_key_name`,
                                `createdate`, `modifydate`)
VALUES (521, 'medicalcenter', 'md_workflow_item', 'item_id', NULL, 520, 'id', 'flow_id', 1, 2, 0, NULL,
        '2023-11-10 11:19:06', '2023-11-14 15:47:54');
INSERT INTO `sys_table_config` (`id`, `db_name`, `table_name`, `key_name`, `cid_name`, `pid`, `pkey_name`,
                                `cur_key_name`, `sync_type`, `need_sync`, `sync_condition`, `file_key_name`,
                                `createdate`, `modifydate`)
VALUES (522, 'medicalcenter', 'md_workflow_case', 'id', 'fzxid', NULL, NULL, NULL, 1, 2, 0, NULL, '2023-11-10 11:19:06',
        '2023-11-10 15:31:38');
INSERT INTO `sys_table_config` (`id`, `db_name`, `table_name`, `key_name`, `cid_name`, `pid`, `pkey_name`,
                                `cur_key_name`, `sync_type`, `need_sync`, `sync_condition`, `file_key_name`,
                                `createdate`, `modifydate`)
VALUES (523, 'medicalcenter', 'md_workflow_case_item', 'item_id', NULL, 522, 'id', 'case_id', 1, 2, 0, NULL,
        '2023-11-10 11:19:06', '2023-11-14 15:47:49');

ALTER TABLE `md_workflow_item`
    ADD COLUMN `notify_type` tinyint(1) NULL COMMENT '通知方式：1.短信 2.公众号 3.小程序 4.APP推送 5.站内信 ' AFTER `level_flag`;
ALTER TABLE `md_workflow_item`
    MODIFY COLUMN `notify_type` tinyint(1) NULL DEFAULT 5 COMMENT '通知方式：1.短信 2.公众号 3.小程序 4.APP推送 5.站内信 ' AFTER `level_flag`;
UPDATE `md_workflow_item`
SET `notify_type` = 5;
ALTER TABLE `md_workflow_case_item`
    ADD COLUMN `notify_type` tinyint(1) NULL COMMENT '通知方式：1.短信 2.公众号 3.小程序 4.APP推送 5.站内信 ' AFTER `level_flag`;
ALTER TABLE `md_workflow_case_item`
    MODIFY COLUMN `notify_type` tinyint(1) NULL DEFAULT 5 COMMENT '通知方式：1.短信 2.公众号 3.小程序 4.APP推送 5.站内信 ' AFTER `level_flag`;
UPDATE `md_workflow_case_item`
SET `notify_type` = 5;

#
2023/11/23 是否已同步更新：否
ALTER TABLE `md_ks_ip`
    ADD COLUMN `is_enable_update` tinyint UNSIGNED NULL DEFAULT 0 COMMENT '是否开启自动更新 0否 1是' AFTER `update_time`,
    ADD COLUMN `update_type`      tinyint UNSIGNED NULL COMMENT '更新类型' AFTER `is_enable_update`;

ALTER TABLE `md_ks_ip`
    ADD COLUMN `deploy_startup_command` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '启动更新服务命令' AFTER `update_type`;


ALTER TABLE `md_ks_ip`
    ADD COLUMN `service_startup_command` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '服务启动命令' AFTER `deploy_startup_command`;

ALTER TABLE `md_ks_ip`
    ADD COLUMN `deploy_service_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '自动更新服务地址' AFTER `service_startup_command`;

ALTER TABLE `md_ks_ip`
    ADD COLUMN `jar_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '下载到本地后的jar包位置，带文件名字' AFTER `deploy_service_addr`;


ALTER TABLE `md_ks_ip`
    ADD COLUMN `service_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '被更新服务地址' AFTER `jar_path`;

#
2023/11/27 是否已同步更新：否
ALTER TABLE `medical_prod`.`md_workflow_case_item`
    MODIFY COLUMN `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态：0.等待开始(上一级还未通过) 1.进行中(上一级已通过) 2.已通过 3.被驳回' AFTER `notify_time`;
ALTER TABLE `medical_prod`.`md_workflow_case`
    MODIFY COLUMN `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态：0.等待开始 1.进行中 2.已通过 3.被驳回' AFTER `cur_level`;



2023/12/04 是否已同步更新：否
CREATE TABLE `md_questionnaire`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`    datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime                                                      DEFAULT NULL COMMENT '更新时间',
    `patientcode`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '体检号',
    `patientname`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '姓名',
    `id_sex`        varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   DEFAULT NULL COMMENT '性别 0男1女',
    `birthdate`     datetime                                                      DEFAULT NULL COMMENT '出生日期',
    `idcardno`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '身份证号',
    `is_han`        varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   DEFAULT NULL COMMENT '民族  1汉族0少数民族',
    `id_marriage`   tinyint(1)                                                    DEFAULT NULL COMMENT '婚姻',
    `phone`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '电话',
    `cultural`      tinyint(1)                                                    DEFAULT NULL COMMENT '文化水平，详见：enums.com.center.medical.bean.CulturalLevel',
    `career`        varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '职业',
    `home_address`  varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '家庭住址',
    `work_address`  varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL COMMENT '工作住址',
    `creator`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
    `question1`     varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question2`     varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question3`     varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question4`     varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question5`     varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question6`     varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question7`     varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question8`     varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question9`     varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question10`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question11`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question12`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question13`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question14`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question15`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question16`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question17`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question18`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question19`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question20`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question21`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question22`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question23`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question24`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question25`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question26`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question27`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question28`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question29`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `question30`    varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `live_hours`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '居住时长',
    `working_hours` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '工作时长',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `IDX_QUES2_CODE` (`patientcode`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='小程序问卷';

2023/12/06 是否已同步更新：否
ALTER TABLE `sys_table_config`
    ADD COLUMN `sync_now` tinyint(1) NULL DEFAULT 0 COMMENT '是否即时同步：0.否 1.是' AFTER `file_key_name`;
UPDATE `sys_table_config`
SET sync_now=0;
UPDATE `sys_table_config`
SET sync_now=1
WHERE table_name IN
      ('base_dictionary', 'base_dictionary_class', 'base_dictionary_match', 'crm_clientcommon', 'crm_customer_transfer',
       'crm_customerfollow', 'crm_receiveandsell', 'kd_remittance', 'kd_payway', 'kd_organization', 'kd_department',
       'kd_customer', 'crm_sellprintteams', 'crm_sellpact', 'crm_sellcustomer', 'kd_reser', 'md_base_guide_mealitem',
       'md_base_guide_meal', 'md_base_attachment_config', 'md_base_adicon_exam_item_code', 'md_basconclusiontype',
       'md_basconclusion', 'md_attachment_group', 'md_attachment_config', 'md_area', 'md_advance_visit_write',
       'md_advance_visit', 'kd_saleer', 'kd_reserway', 'md_base_industry', 'md_base_unit', 'md_base_worktype',
       'md_base_zone', 'md_base_zone_qd', 'md_basexamltem', 'md_basexamltem_sign', 'md_card_recheck_record',
       'md_card_payway', 'md_card_member_medical', 'md_card', 'sys_business_source', 'md_business_cat',
       'md_basexamltemtype', 'md_devicetype_position_checkitem', 'md_createorder', 'md_createmeal',
       'md_createcombo_type', 'md_createcombo', 'md_create_order_qtxz', 'md_conclusion_and_fzx', 'md_comboexamitem',
       'md_comboanditem', 'md_comboandfzx', 'md_chest', 'md_carmanagefr', 'md_carmanage', 'md_card_type', 'md_harm',
       'md_group_and_fzx', 'md_fylx', 'md_expresscompany', 'md_exam_and_fzx', 'md_emphasis_ask_symptom', 'md_dw_harm',
       'md_drugstore_drug', 'md_drugstore_class', 'md_drug_disease_type', 'md_drug_disease', 'md_drug_and_fzx',
       'md_drinking_type', 'md_dictpayway', 'md_notificationmethod', 'md_nation', 'md_mealanditem', 'md_mealandfzx',
       'md_limit_operation', 'md_ks_ip', 'md_items_barcode_class', 'md_items_and_fzx', 'md_items',
       'md_item_related_information', 'md_interface_account', 'md_inspect_charge', 'md_harm_package_match',
       'md_harm_and_fzx', 'md_pacs_items', 'md_pacs_item_part', 'md_pacs_inspect_charge', 'md_pacs_basexamltem_sign',
       'md_pacs_basexamltem', 'md_pacs_base_part', 'md_pacs_basconclusion', 'md_ordersummary', 'md_orderandfzx',
       'md_orderandcombo', 'md_occupation_symptom', 'md_occupation_drug', 'md_occupation_diseast_class',
       'md_occupation_diseast', 'md_peispatient_photo', 'md_peispatient_charge_record', 'md_peispatient_charge_other',
       'md_peispatient_charge_main', 'md_peispatient_and_fzx', 'md_peispatient', 'md_peisorgreservationreceipt',
       'md_peisorgreservationgroup', 'md_peisorgreservation', 'md_peis_reser_payway', 'md_patienttype',
       'md_patient_users', 'md_pacs_peispatientfeeitem', 'md_pacs_peispatient', 'md_short_message_type',
       'md_review_notification_format', 'md_reservation_setting', 'md_reservation_group', 'md_reservation_default',
       'md_reservation', 'md_report_config', 'md_receipt_type', 'md_qyjjlx', 'md_printtype', 'md_peispatientfeeitem',
       'md_peispatientcharge', 'md_peispatientarchive', 'md_peispatient_reservation_charge', 'md_userauthcode',
       'md_user_saleer', 'md_user_mapping_sys', 'md_user_mapping', 'md_user_level_bind', 'md_user_level',
       'md_user_harm_class', 'md_user', 'md_upperower', 'md_trade_record', 'md_tjdw_branch', 'md_stencil_maintain',
       'md_sshy', 'md_sortexam_limit', 'md_zy_summary', 'md_zy_occupation_org', 'md_zy_harm_class',
       'md_zy_fhcs_gr_class', 'md_zy_fhcs_gc_class', 'md_zy_fhcs_gc', 'md_zy_fhcl_gr', 'md_year', 'md_yblx',
       'md_wz_jzb', 'md_wz_jwb', 'md_wsjg', 'md_whysqzfw', 'md_vation_and_fzx', 'sys_post', 'sys_notice', 'sys_menu',
       'sys_dict_type', 'sys_dict_data', 'sys_dept', 'sys_deploy_version_branch', 'sys_deploy_version',
       'sys_deploy_record', 'sys_department', 'sys_code_config', 'sys_cen_dep', 'sys_branch', 'md_zy_vs_summary',
       'sys_user_role', 'sys_user_post', 'sys_user_dep', 'sys_user_branch', 'sys_user', 'sys_table_config',
       'sys_role_menu', 'sys_role_dept', 'sys_role'
          );


2023/12/07 是否已同步更新：是
CREATE TABLE `md_order_history_statistics`
(
    `id`                    varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
    `createdate`            datetime                                                  DEFAULT NULL,
    `modifydate`            datetime                                                  DEFAULT NULL,
    `int_id`                decimal(10, 0)                                            NOT NULL COMMENT '单位id',
    `year`                  varchar(4) CHARACTER SET utf8mb3 COLLATE utf8_general_ci  NOT NULL COMMENT '年份yyyy',
    `fzx_id`                varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '分中心id',
    `total_paid`            decimal(12, 2)                                            NOT NULL COMMENT '总实收',
    `average_discount_rate` decimal(8, 2)                                             DEFAULT NULL COMMENT '平均折扣率',
    `variable_cost_rate`    decimal(8, 2)                                             DEFAULT NULL COMMENT '变动成本率',
    `id_org`                varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    KEY `IDX_ORDER_H_STA_INT_ID` (`int_id`),
    KEY `IDX_ORDER_H_STA_YEAR` (`year`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3 COMMENT ='订单历史平均折扣、变动成本率统计表';



2023/12/08 是否已同步更新：是
ALTER TABLE `medical_prod`.`md_handle_new_projects`
    MODIFY COLUMN `is_handle` tinyint(1) NULL DEFAULT 0 COMMENT '是否已处理：0.未处理 1.已处理' AFTER `add_doctor_id`;



2023/12/16 是否已同步更新：是
ALTER TABLE `medical_prod`.`md_peispatient_consultation`
    ADD COLUMN `kiss_month`  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-饮酒月数' AFTER `other`,
    ADD COLUMN `smoke_month` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '吸烟史-吸烟月数' AFTER `kiss_month`;

2023/12/19 是否已同步更新：否 开发区和线上已更新

DROP TABLE IF EXISTS `sys_deploy_version_branch`;
CREATE TABLE `sys_deploy_version_branch`
(
    `id`                  int unsigned                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `createdate`          datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate`          datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `branch_id`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分中心编码',
    `version_id`          int unsigned                                                 NOT NULL COMMENT 'sys_deploy_version.id',
    `is_delete`           tinyint unsigned                                             NOT NULL DEFAULT '0' COMMENT '是否删除 0否 1是',
    `is_sql_updated`      tinyint unsigned                                             NOT NULL DEFAULT '0' COMMENT '数据库更新状态 0未更新 1更新成功 2更新失败',
    `is_service_updated`  tinyint unsigned                                             NOT NULL DEFAULT '0' COMMENT '所有服务是否已更新 0否 1是',
    `sql_update_time`     datetime                                                              DEFAULT NULL COMMENT '数据库更新时间',
    `service_update_time` datetime                                                              DEFAULT NULL COMMENT '所有服务完成更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='自动部署-版本更新分中心关联表';



DROP TABLE IF EXISTS `sys_deploy_version`;
CREATE TABLE `sys_deploy_version`
(
    `id`             int unsigned                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `version_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '版本号',
    `version_name`   varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '版本名称',
    `execute_time`   datetime                                                              DEFAULT NULL COMMENT '要求更新时间',
    `notes`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '备注',
    `update_type`    tinyint unsigned                                                      DEFAULT NULL COMMENT '更新类型',
    `file_path`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '更新包位置',
    `update_sql`     varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        DEFAULT NULL COMMENT '数据库更新语句',
    `is_delete`      tinyint unsigned                                             NOT NULL DEFAULT '0' COMMENT '是否删除 0否 1是',
    `creator`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建者user_name',
    `modifier`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '修改者user_name',
    `is_enable`      tinyint unsigned                                             NOT NULL DEFAULT '0' COMMENT '是否启用 0否 1是',
    `createdate`     datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate`     datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='自动部署-更新版本信息';



DROP TABLE IF EXISTS `sys_deploy_record`;
CREATE TABLE `sys_deploy_record`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`         datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate`         datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_success`         tinyint                                                               DEFAULT NULL COMMENT '是否更新成功 0失败 1成功',
    `update_time`        datetime                                                              DEFAULT NULL COMMENT '更新时间',
    `version_id`         int unsigned                                                 NOT NULL COMMENT 'sys_deploy_version.id',
    `ks_ip_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'md_ks_ip.id',
    `message`            varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        DEFAULT NULL COMMENT '错误信息',
    `address`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT 'ip',
    `host_name`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '主机名',
    `branch_id`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '分中心编码',
    `creator`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '创建人用户名（人工处理）',
    `modifier`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '修改人用户名（人工处理）',
    `remark`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT '' COMMENT '备注（人工处理）',
    `manual_update_time` datetime                                                              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '人工处理时间',
    `is_manual_success`  tinyint unsigned                                                      DEFAULT NULL COMMENT '人工处理结果 0失败 1成功',
    `is_manual`          tinyint unsigned                                             NOT NULL DEFAULT '0' COMMENT '是否人工处理  1是0否',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_deployrecord` (`version_id`, `ks_ip_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='自动部署-更新记录';

INSERT INTO `sys_table_config`(id, db_name, table_name, key_name, cid_name, pid, pkey_name, cur_key_name, sync_type,
                               need_sync, sync_condition, file_key_name, sync_now, createdate, modifydate)
VALUES (525, 'medicalcenter', 'sys_deploy_record', 'id', 'branch_id', null, null, null, 3, 2, null, null, 1, SYSDATE(),
        SYSDATE());

INSERT INTO `sys_table_config`(id, db_name, table_name, key_name, cid_name, pid, pkey_name, cur_key_name, sync_type,
                               need_sync, sync_condition, file_key_name, sync_now, createdate, modifydate)
VALUES (526, 'medicalcenter', 'sys_deploy_version', 'id', null, 527, 'version_id', 'id', 1, 2, null, 'file_path', 1,
        SYSDATE(), SYSDATE());

INSERT INTO `sys_table_config`(id, db_name, table_name, key_name, cid_name, pid, pkey_name, cur_key_name, sync_type,
                               need_sync, sync_condition, file_key_name, sync_now, createdate, modifydate)
VALUES (527, 'medicalcenter', 'sys_deploy_version_branch', 'id', 'branch_id', null, null, null, 3, 2, null, null, 1,
        SYSDATE(), SYSDATE());



2023/12/21 是否已同步更新：是
ALTER TABLE `medical_prod`.`md_peispatient`
    MODIFY COLUMN `f_readytofinal` tinyint(1) ZEROFILL NULL DEFAULT 0 COMMENT '分检完成' AFTER `f_examstarted`;



2024/1/4 是否已同步更新：是
ALTER TABLE `medical_prod`.`md_lung`
    DROP
        INDEX `IDX_LUNG_PATIENTCODE`,
    ADD UNIQUE INDEX `IDX_LUNG_PATIENTCODE` (`patientcode`) USING BTREE;



2024/1/4 是否已同步更新：否(锦都未更新)
CREATE TABLE `md_surrender_documents`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `fzxid`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分中心id',
    `patientname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '姓名',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '体检号',
    `submit_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '提交人id',
    `presenter`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '提交人',
    `submitdate`  datetime                                                      DEFAULT NULL COMMENT '交单时间',
    `createdate`  datetime                                                      DEFAULT NULL COMMENT '创建日期',
    `modifydate`  datetime                                                      DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `IDX_PATIENTCODE` (`patientcode`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='交单记录表';

2024/1/6 是否已同步更新：否(线上未更新)
ALTER TABLE `md_attachment`
    MODIFY COLUMN `file_path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径' AFTER `file_sort`,
    ADD INDEX `idx_fee_file_path` (`file_path`) USING BTREE;

2024/1/10 是否已同步更新：是
ALTER TABLE `md_peispatient`
    CHANGE COLUMN `id_feetype` `have_private` tinyint(1) NULL DEFAULT 0 COMMENT '是否有隐私项目：0.无 1.有' AFTER `workno`;
ALTER TABLE `md_peispatienthistory`
    CHANGE COLUMN `id_feetype` `have_private` tinyint(1) NULL DEFAULT 0 COMMENT '是否有隐私项目：0.无 1.有' AFTER `workno`;


2024/1/10 是否已同步更新：是
ALTER TABLE `md_questionnaire`
    ADD COLUMN `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签名图片，有图片代表放弃回答问卷' AFTER `fzxid`;

2024/1/11 是否已同步更新：否(线上已更新)
DROP TABLE IF EXISTS `md_meal_external_info`;
CREATE TABLE `md_meal_external_info`
(
    `id`           int UNSIGNED                                                 NOT NULL AUTO_INCREMENT,
    `meal_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '体检系统套餐ID',
    `createdate`   datetime                                                     NOT NULL COMMENT '记录时间',
    `modifydate`   datetime                                                     NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `source_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '第三方ID',
    `biz_combo_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '第三方套餐ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '套餐第三方关联信息表'
  ROW_FORMAT = Dynamic;

2024/1/16 是否已同步更新：否(线下和锦都未更新)
ALTER TABLE `md_peispatient`
    MODIFY COLUMN `f_isforprepare` tinyint(1) NULL DEFAULT 0 COMMENT '预定（已备单）' AFTER `resarea`,
    MODIFY COLUMN `f_isforreserve` tinyint(1) NULL DEFAULT 0 COMMENT '预约' AFTER `f_isforprepare`,
    MODIFY COLUMN `f_guidanceprinted` tinyint(1) NULL DEFAULT 0 COMMENT '指引单已打' AFTER `doctorapply`,
    MODIFY COLUMN `f_paused` tinyint(1) NULL DEFAULT 0 COMMENT '禁检' AFTER `f_readytofinal`,
    MODIFY COLUMN `f_finallocked` tinyint(1) NULL DEFAULT 0 COMMENT '总检锁定' AFTER `f_paused`,
    MODIFY COLUMN `f_finalexamed` decimal(1, 0) NULL DEFAULT 0 COMMENT '总检完成' AFTER `f_finallocked`,
    MODIFY COLUMN `cjjgsfyhf` tinyint(1) NULL DEFAULT 0 COMMENT '迟检是否已回访：0:未回访 1：已回访' AFTER `trades`,
    MODIFY COLUMN `yxjgsfyhf` tinyint(1) NULL DEFAULT 0 COMMENT '阳性结果是否已回访：0:未回访 1：已回访' AFTER `bhgybsfyhf`,
    MODIFY COLUMN `is_noticed` tinyint(1) NULL DEFAULT 0 COMMENT '是否预约通知：0或null.否 1.是' AFTER `short_code`,
    MODIFY COLUMN `checkout_status` tinyint(1) NULL DEFAULT 0 COMMENT '结账状态 0或者Null：未结账 1：已结账' AFTER `checkout_date`;



2024/1/16 是否已同步更新：否(只更新了锦都)
ALTER TABLE `md_peispatientfeeitem`
    ADD INDEX `idx_ppfi_id_patient_severedegree` (`id_patient`, `severedegree`) USING BTREE;



2024/3/1 是否已同步更新：否(只更新了东区)
ALTER TABLE `medical_prod`.`md_peispatient`
    MODIFY COLUMN `tmyd` int NULL DEFAULT 0 COMMENT '条码打印次数' AFTER `zytjzt`,
    MODIFY COLUMN `guide_signle_count` int NULL DEFAULT 0 COMMENT '导引单打印次数' AFTER `readytofinal_date`;




2024/3/7 是否已同步更新：是
ALTER TABLE `medical_prod`.`md_workflow_case`
    MODIFY COLUMN `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联的业务ID' AFTER `fzxid`,
    ADD COLUMN `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '根据审批流的情况存一个相应的值，比如业务ID，比如对象等等' AFTER `modifier`;



2024/3/7 是否已同步更新：否（只更新了东区）
CREATE TABLE `md_workflow_reservation_type` (
    `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `case_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '工作流实例记录ID',
    `patientcode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '体检号',
    `id_patientclass` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '体检者类型ID',
    `moneyamount` decimal(15,2) DEFAULT NULL COMMENT '应付金额',
    `createdate` datetime DEFAULT NULL COMMENT '创建时间',
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `order_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单id',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_case_id` (`case_id`) USING BTREE,
    KEY `idx_patientcode` (`patientcode`) USING BTREE,
    KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='工作流-预约会员类型';




CREATE TABLE `md_reservation_group_code` (
     `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
     `extracted_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '提取码',
     `group_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组id',
     `id_org` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '团体ID',
     `id_patientclass` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '体检者类型ID',
     `expiration_date` int DEFAULT NULL COMMENT '有效期,7,14,30,999',
     `expiration_time` datetime DEFAULT NULL COMMENT '过期时间',
     `status` tinyint(1) DEFAULT NULL COMMENT '状态 0生效1过期',
     `createdate` datetime DEFAULT NULL COMMENT '创建时间',
     `create_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人id',
     `modifydate` datetime DEFAULT NULL COMMENT '修改时间',
     `modify_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人id',
     PRIMARY KEY (`id`) USING BTREE,
     KEY `idx_group_id` (`group_id`) USING BTREE,
     KEY `idx_id_org` (`id_org`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='团体预约分享码';




2024/3/13 小程序添加表
CREATE TABLE `md_family_list` (
      `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
      `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '本系统userId',
      `patientname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
      `idcardno` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号',
      `id_sex` int DEFAULT NULL COMMENT '性别ID',
      `age` int DEFAULT NULL COMMENT '年龄',
      `countreportoccupationxml` tinyint DEFAULT NULL COMMENT '客户证件类型',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `update_time` datetime DEFAULT NULL COMMENT '更新时间',
      `from_online` int DEFAULT NULL COMMENT '是否是从线上系统插入的 1是0否',
      PRIMARY KEY (`id`) USING BTREE,
      KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='家人列表';




2024/3/15 是否已同步更新：否（只更新了东区和锦都）
ALTER TABLE `medical_prod`.`md_questionnaire`
    ADD COLUMN `countreportoccupationxml` int NULL DEFAULT NULL COMMENT '客户证件类型' AFTER `pic`;