--系统业务功能开关控制，是否已同步更新：是
CREATE TABLE `sys_function`
(
    `function_id`   varbinary(32) NOT NULL COMMENT '业务功能ID',
    `function_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '业务功能名称',
    `status`        tinyint(1) DEFAULT '0' COMMENT '状态：0.正常 1.停用',
    `remark`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备注',
    `create_time`   datetime                                                               DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime                                                               DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`function_id`) USING BTREE,
    KEY             `idx_sst_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统业务功能';

CREATE TABLE `sys_function_branch`
(
    `id`          varbinary(32) NOT NULL COMMENT '关联ID',
    `function_id` int unsigned NOT NULL COMMENT '业务功能ID（关联数据表sys_function的ID）',
    `branch_id`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分中心ID',
    `params`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '参数设置',
    `status`      tinyint(1) DEFAULT '0' COMMENT '状态：0.正常 1.关闭',
    `remark`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备注',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `idx_ssb_branch_id` (`branch_id`) USING BTREE,
    KEY           `idx_ssb_function_id` (`function_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='业务功能-分中心关联';
