#
修改团体任务表和体检者分组表同步设置，是否已同步更新：是
UPDATE `sys_table_config`
SET `cid_name`     = NULL,
    `pid`          = 121,
    `pkey_name`    = 'group_id',
    `cur_key_name` = 'id'
WHERE `id` = 189;

UPDATE `sys_table_config`
SET `pid`          = 269,
    `cid_name`     = NULL,
    `pkey_name`    = 'vation_id',
    `cur_key_name` = 'id'
WHERE `id` = 188;

#
系统授权相关数据库变化操作，是否已同步更新：是
INSERT INTO `sys_config`(`config_id`, `config_name`, `config_key`, `config_value`, `config_type`,
                                       `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (144, '系统授权信息', 'system_auth_info', NULL, 'N', '', NULL, '', NULL, NULL);

DROP TABLE IF EXISTS `sys_auth_log`;
CREATE TABLE `sys_auth_log`
(
    `id`               bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `institution_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构ID，对应md_business_source中的source_id',
    `institution_name` varchar(64) COLLATE utf8mb4_general_ci                        DEFAULT NULL COMMENT '机构名称',
    `auth_type`        tinyint(1) DEFAULT '1' COMMENT '授权类型：0.永久授权 1.有期限收取',
    `auth_code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '授权码，对应sys_code_config中的auth_code',
    `auth_value`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '授权密钥：授权信息经过rsa加密后的值',
    `remark`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授权备注',
    `start_time`       datetime                                                     NOT NULL COMMENT '开始日期',
    `expired_time`     datetime                                                     NOT NULL COMMENT '到期时间',
    `version`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '授权版本号',
    `can_use`          tinyint(1) DEFAULT '0' COMMENT '到期是否可用：0.不可用 1.可用',
    `status`           tinyint(1) DEFAULT '0' COMMENT '状态：0.无效 1.正常',
    `createdate`       datetime                                                      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate`       datetime                                                      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY                `idx_scc_expired_time` (`expired_time`) USING BTREE,
    KEY                `idx_scc_institution_id` (`institution_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='系统授权记录'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `md_business_source`;
DROP TABLE IF EXISTS `sys_business_source`;
CREATE TABLE `sys_business_source`
(
    `id`         bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `source_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '第三方ID',
    `type_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '第三方名称',
    `account`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号',
    `password`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
    `logo`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'logo',
    `setting`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '配置设置',
    `tip`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '介绍',
    `seq`        int NULL DEFAULT 0 COMMENT '排序',
    `status`     tinyint NULL DEFAULT 1 COMMENT '状态：-1.删除 0.关闭 1.开放中',
    `createdate` datetime                                                     NOT NULL COMMENT '记录时间',
    `modifydate` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_bs_type_name` (`type_name` ASC) USING BTREE,
    UNIQUE INDEX `idx_bs_source_id` (`source_id` ASC) USING BTREE,
    INDEX        `idx_bs_status` (`status` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '合作第三方'
  ROW_FORMAT = DYNAMIC;

INSERT INTO `md_business_source`
VALUES (1, 'app0715', '沃德预约小程序', 'medicalapp', '123456', NULL,
        '{\"host\":\"https://medicalappapi.world.com\"}', '沃德预约小程序', 1, 1, '2023-09-15 17:29:17', NULL);
INSERT INTO `md_business_source`
VALUES (2, 'kt0815', '康淘小程序', 'kangtao', '123456', NULL, '{\"host\":\"http://localhost:8084\"}', '康淘小程序', 1,
        1, '2023-09-15 17:29:17', NULL);
INSERT INTO `md_business_source`
VALUES (3, 'pa0915', '平安好医生', 'pingan', '123456', NULL, '{\"host\":\"http://localhost:8084\"}', '平安好医生', 1, 1,
        '2023-09-15 17:29:17', NULL);
INSERT INTO `md_business_source`
VALUES (4, 'rs2024', '人寿保险', 'renshou', '123456', NULL, NULL, '人寿保险', 1, 1, '2024-01-08 15:12:37', NULL);



