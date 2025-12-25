# 版本控制，是否已同步更新：是
DROP TABLE IF EXISTS `sys_service_branch`;
CREATE TABLE `sys_service_branch`
(
    `id`           int                                                          NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `service_id`   int UNSIGNED                                                 NOT NULL COMMENT '系统服务ID（关联数据表sys_service_type的ID）',
    `branch_id`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分中心ID',
    `is_manual`    tinyint(1)                                                   NULL DEFAULT NULL COMMENT '是否人工处理 ：0.否 1.是',
    `executer`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人工处理人员(关联sys_user表user_no)',
    `execute_time` datetime                                                     NULL DEFAULT NULL COMMENT '默认执行时间',
    `status`       tinyint(1)                                                   NULL DEFAULT 0 COMMENT '状态：0.正常 1.停用',
    `remark`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        NULL COMMENT '备注',
    `create_time`  datetime                                                     NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime                                                     NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_ssb_branch_id` (`branch_id` ASC) USING BTREE,
    INDEX `idx_ssb_service_id` (`service_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统服务-分中心关联记录'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_service_type`;
CREATE TABLE `sys_service_type`
(
    `service_id`   int                                                           NOT NULL AUTO_INCREMENT COMMENT '服务ID',
    `service_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '服务名称',
    `service_type` tinyint(1)                                                    NOT NULL COMMENT '服务类型：1.前端vue 2.后端java',
    `is_manual`    tinyint(1)                                                    NULL     DEFAULT NULL COMMENT '是否人工处理 ：0.否 1.是',
    `status`       tinyint(1)                                                    NULL     DEFAULT 0 COMMENT '状态：0.正常 1.停用',
    `remark`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '备注',
    `create_time`  datetime                                                      NULL     DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime                                                      NULL     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`service_id`) USING BTREE,
    INDEX `idx_sst_status` (`status` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统服务(记录系统服务种类)'
  ROW_FORMAT = Dynamic;

INSERT INTO `sys_service_type`
VALUES (1, '线上主系统前端服务', 1, NULL, 0, '线上主系统前端服务', '2024-02-27 16:30:26', NULL);
INSERT INTO `sys_service_type`
VALUES (2, '线上主系统后端服务', 2, NULL, 0, '线上主系统后端服务', '2024-02-27 16:30:45', NULL);
INSERT INTO `sys_service_type`
VALUES (3, '线下主系统后端服务', 2, NULL, 0, '线下主系统后端服务', '2024-02-27 16:31:08', NULL);
INSERT INTO `sys_service_type`
VALUES (4, '线下主系统前端服务', 1, NULL, 0, '线下主系统前端服务', '2024-02-27 16:31:59', NULL);

DROP TABLE IF EXISTS `sys_version`;
CREATE TABLE `sys_version`
(
    `id`             int UNSIGNED                                                   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `version_number` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '版本号',
    `version_name`   varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '版本名称',
    `execute_time`   datetime                                                       NULL     DEFAULT NULL COMMENT '要求更新时间',
    `notes`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '备注',
    `update_type`    tinyint UNSIGNED                                               NULL     DEFAULT NULL COMMENT '更新类型',
    `file_path`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '更新包位置',
    `update_sql`     varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '数据库更新语句',
    `is_delete`      tinyint UNSIGNED                                               NOT NULL DEFAULT 0 COMMENT '是否删除 0否 1是',
    `creator`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '创建者user_name',
    `modifier`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '修改者user_name',
    `is_enable`      tinyint UNSIGNED                                               NOT NULL DEFAULT 0 COMMENT '是否启用 0否 1是',
    `createdate`     datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate`     datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '自动部署-更新版本信息'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `sys_version_deploy`;
CREATE TABLE `sys_version_deploy`
(
    `id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'id',
    `branch_id`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '分中心ID',
    `version_id`          int UNSIGNED                                                  NOT NULL COMMENT '版本id(关联sys_version_info的ID)',
    `ip_address`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '服务IP地址',
    `svs_id`              int                                                           NOT NULL COMMENT '版本更新包ID(关联sys_version_service的ID)',
    `service_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '服务名称',
    `sql_file`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '数据库更新文件(多文件)',
    `sql_status`          tinyint UNSIGNED                                              NULL DEFAULT 0 COMMENT '数据库更新状态：-1.更新失败 0.未更新 1.更新部分 2.全部更新',
    `sql_remark`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库更新备注',
    `sql_update_time`     datetime                                                      NULL DEFAULT NULL COMMENT '数据库更新时间',
    `file_path`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新版本安装包文件(单文件)',
    `file_status`         tinyint(1)                                                    NULL DEFAULT NULL COMMENT '安装更新状态：-2.忽略更新 -1.更新失败 0.未更新 1.更新中 2.已更新',
    `file_remark`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '安装包更新备注',
    `file_update_time`    datetime                                                      NULL DEFAULT NULL COMMENT '安装包更新时间',
    `other_file`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '其他文件(多文件)',
    `execute_time`        datetime                                                      NULL DEFAULT NULL COMMENT '执行时间',
    `status`              tinyint(1)                                                    NULL DEFAULT 0 COMMENT '状态：-1.更新失败 0.待更新 1.更新中 2.已完成',
    `is_manual`           tinyint UNSIGNED                                              NULL DEFAULT 0 COMMENT '是否人工处理 ：0.否 1.是',
    `remark`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
    `message`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '执行结果',
    `executer`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '人工处理人员',
    `manual_execute_time` datetime                                                      NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '人工处理时间',
    `createdate`          datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate`          datetime                                                      NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `unx_sdr_deployrecord` (`version_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '版本控制-分中心版本更新记录'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `sys_version_item`;
CREATE TABLE `sys_version_item`
(
    `log_id`       int                                                           NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `version_id`   int UNSIGNED                                                  NOT NULL COMMENT '版本ID',
    `sys_type`     tinyint(1)                                                    NOT NULL COMMENT '更新类型，如[1,2,3]：1.前端 2.后端 3.数据库',
    `modify_type`  tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '修改模式：1.新增 2.优化',
    `module_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模块名称',
    `pics`         varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '图片(最多3张)',
    `content`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL COMMENT '更新内容',
    `priority`     tinyint(1)                                                    NULL     DEFAULT 4 COMMENT '优先级：1.重要且紧急 2.重要不紧急 3.紧急不重要 4.不重要不紧急',
    `dev_cycle`    int                                                           NULL     DEFAULT NULL COMMENT '开发周期(天)',
    `progress`     tinyint(1)                                                    NULL     DEFAULT 0 COMMENT '进度：0.需求确认中 1.开发中 2.测试中 3.已完成',
    `remark`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `status`       tinyint(1)                                                    NULL     DEFAULT 0 COMMENT '状态：0.正常 1.废弃',
    `developer`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT '' COMMENT '开发者',
    `develop_time` datetime                                                      NULL     DEFAULT NULL COMMENT '开发时间',
    `tester`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '测试者',
    `test_time`    datetime                                                      NULL     DEFAULT NULL COMMENT '测试时间',
    `proponent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '需求提出人ID(关联sys_user表user_no)',
    `proponent`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '需求提出人',
    `propose_time` datetime                                                      NULL     DEFAULT NULL COMMENT '提出时间',
    `confirmer_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '需求确认人ID(关联sys_user表user_no)',
    `confirmer`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '需求确认人',
    `comfirm_time` datetime                                                      NULL     DEFAULT NULL COMMENT '需求确认时间',
    `accepter_id`  varbinary(64)                                                 NULL     DEFAULT NULL COMMENT '验收人ID(关联sys_user表user_no)',
    `accepter`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '验收人',
    `accept_time`  datetime                                                      NULL     DEFAULT NULL COMMENT '验收时间',
    `create_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                      NULL     DEFAULT NULL COMMENT '创建时间',
    `update_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                      NULL     DEFAULT NULL COMMENT '更新时间',
    `finish_time`  datetime                                                      NULL     DEFAULT NULL COMMENT '完成时间',
    PRIMARY KEY (`log_id`) USING BTREE,
    INDEX `idx_svi_version_id` (`version_id` ASC) USING BTREE,
    INDEX `idx_svi_module_name` (`module_name` ASC) USING BTREE,
    INDEX `idx_svi_progress` (`progress` ASC) USING BTREE,
    INDEX `idx_svi_proponent_id` (`proponent_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统更新记录'
  ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `sys_version_service`;
CREATE TABLE `sys_version_service`
(
    `id`           int                                                           NOT NULL AUTO_INCREMENT COMMENT '版本更新包ID',
    `service_id`   int UNSIGNED                                                  NOT NULL COMMENT '系统服务ID（关联数据表sys_service_type的ID）',
    `service_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '服务名称',
    `version_id`   int UNSIGNED                                                  NOT NULL COMMENT '版本ID(关联sys_version_info的ID)',
    `service_type` tinyint(1)                                                    NOT NULL COMMENT '服务类型：1.前端vue 2.后端java',
    `file_path`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '新版本安装包文件(单文件)',
    `sql_file`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '数据库更新文件(多文件)',
    `other_file`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '其他文件(多文件)',
    `remark`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '备注',
    `status`       tinyint(1)                                                    NULL     DEFAULT 0 COMMENT '状态：0.正常 1.停用',
    `create_time`  datetime                                                      NULL     DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime                                                      NULL     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_svs_service_id` (`service_id` ASC) USING BTREE,
    INDEX `idx_svs_version_id` (`version_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '版本控制-系统服务更新包记录'
  ROW_FORMAT = Dynamic;

INSERT INTO `sys_version_service`
VALUES (1, 1, '', 2, 1, '/newimage/images/common/01/20240304/bdc6bedada5d489c8fe449e4a777e50c.txt',
        '/newimage/images/common/01/20240304/b2dfe66616864c8ab666e940c9a5a91f.txt,/newimage/images/common/01/20240304/c95420c1a41849a8b19e85a653ff6b58.txt',
        '/newimage/images/common/01/20240304/e2f89e0887914f6ea34f9c718bfa5ce0.txt,/newimage/images/common/01/20240304/98668249a8b544979b1e4bee33fce6d2.txt',
        '123123', 0, NULL, NULL);

