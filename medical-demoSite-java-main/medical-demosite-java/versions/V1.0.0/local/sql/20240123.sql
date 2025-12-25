DROP TABLE IF EXISTS `sys_version`;
CREATE TABLE `sys_version`
(
    `id`             int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `version_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '版本号',
    `version_name`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '版本名称',
    `service_id`     int UNSIGNED NOT NULL COMMENT '系统服务ID（关联数据表sys_service_type的ID）',
    `file_path`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '新版本安装包文件',
    `notes`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `execute_type`   tinyint(1) NULL DEFAULT NULL COMMENT '执行方式：0.定期执行 1.立即执行 2.手动执行',
    `execute_time`   datetime NULL DEFAULT NULL COMMENT '更新执行更新时间',
    `status`         tinyint(1) NULL DEFAULT NULL COMMENT '版本状态：-1失效 0.待更新 1.已更新部分 2.全部已更新',
    `finish_time`    datetime NULL DEFAULT NULL COMMENT '完成时间',
    `sql_file`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据库更新文件',
    `createby`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `createdate`     datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifyby`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
    `modifydate`     datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX            `idx_sdv_version_number`(`version_number` ASC) USING BTREE,
    INDEX            `idx_sdv_service_id`(`service_id` ASC) USING BTREE,
    INDEX            `idx_sdv_execute_time`(`execute_time` ASC) USING BTREE,
    INDEX            `idx_sdv_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自动部署-更新版本信息' ROW_FORMAT = Dynamic;
DROP TABLE IF EXISTS `sys_version_branch`;
CREATE TABLE `sys_version_branch`
(
    `id`          int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `branch_id`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分中心ID',
    `version_id`  int UNSIGNED NOT NULL COMMENT '版本信息ID(关联sys_deploy_version的ID)',
    `deleted`     tinyint UNSIGNED NULL DEFAULT 0 COMMENT '是否删除：0.否 1.是',
    `status`      tinyint UNSIGNED NULL DEFAULT 0 COMMENT '状态：-1.执行失败 0.待执行 1.已执行部分 2已完成',
    `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
    `createdate`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate`  datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自动部署-版本更新分中心关联表' ROW_FORMAT = Dynamic;
DROP TABLE IF EXISTS `sys_version_deploy`;
CREATE TABLE `sys_version_deploy`
(
    `id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `version_id`          int UNSIGNED NOT NULL COMMENT '版本信息ID(关联sys_deploy_version的ID)',
    `ks_ip_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '科室IP配置ID(关联md_ks_ip的ID)',
    `address`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ip',
    `host_name`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主机名',
    `branch_id`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分中心ID',
    `sql_status`          tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '数据库更新状态：-1.更新失败 0.未更新 1.更新部分 2.全部更新',
    `sql_remark`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据库更新备注',
    `sql_update_time`     datetime NULL DEFAULT NULL COMMENT '数据库更新时间',
    `is_manual`           tinyint UNSIGNED NULL DEFAULT 0 COMMENT '是否人工处理  1是0否',
    `execute_time`        datetime NULL DEFAULT NULL COMMENT '执行时间',
    `status`              tinyint(1) NULL DEFAULT 0 COMMENT '状态：-1.更新失败 0.待更新 1.正在更新 2.已完成',
    `remark`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注（人工处理）',
    `message`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行结果',
    `executer`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人工处理人员',
    `manual_execute_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '人工处理时间',
    `createdate`          datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifydate`          datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `unx_sdr_deployrecord`(`version_id` ASC, `ks_ip_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '版本控制-更新记录' ROW_FORMAT = Dynamic;
ALTER TABLE `sys_version` COMMENT = '版本控制-版本信息';
ALTER TABLE `sys_version_branch` COMMENT = '版本控制-版本分中心关联表';