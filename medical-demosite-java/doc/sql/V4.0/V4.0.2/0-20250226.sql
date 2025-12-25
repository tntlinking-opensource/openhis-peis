-- 添加通知配置表，已全部更新
CREATE TABLE `sys_notice_config` (
     `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
     `notice_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '通知名称',
     `notice_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '通知正文',
     `createdate` datetime DEFAULT NULL COMMENT '创建时间',
     `modifydate` datetime DEFAULT NULL COMMENT '修改时间',
     `note` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
     `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除：0.未删除 1.已删除',
     `template_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模板ID',
     `appid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'APPID',
     `role_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色id，可以存多个',
     `operator` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作者',
     PRIMARY KEY (`id`) USING BTREE,
     KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='通知配置表';



CREATE TABLE `sys_notification` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `tar_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '目标用户 uuid',
    `notice_config_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '消息类型',
    `content` varchar(2000) DEFAULT NULL COMMENT '消息内容',
    `is_read` int DEFAULT NULL COMMENT '是否已读',
    `createdate` datetime DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_tar_user_id` (`tar_user_id`),
    KEY `idx_notice_config_id` (`notice_config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知记录表';
