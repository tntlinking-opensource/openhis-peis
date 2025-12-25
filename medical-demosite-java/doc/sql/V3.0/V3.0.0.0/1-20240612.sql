--添加小程序套餐表(只更新了测试环境)
CREATE TABLE `md_createmeal_app` (
     `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
     `tcid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '套餐ID',
     `type` int DEFAULT NULL COMMENT '类型',
     `thumbnail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '缩略图',
     `label` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签名,用英文逗号隔开',
     `img_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片',
     `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
     `bz` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备注',
     `status` tinyint(1) DEFAULT '0' COMMENT '状态',
     `seq` int DEFAULT NULL COMMENT '顺序',
     `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
     `createdate` datetime DEFAULT NULL COMMENT '创建时间',
     `modifydate` datetime DEFAULT NULL COMMENT '修改日期',
     PRIMARY KEY (`id`) USING BTREE,
     KEY `idx_tcid` (`tcid`) USING BTREE,
     KEY `idx_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='小程序套餐表';



--添加小程序套餐类型(只更新了测试环境)
CREATE TABLE `md_createmeal_app_type` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除：0.未删除 1.已删除',
  `createdate` datetime DEFAULT NULL COMMENT '创建日期',
  `modifydate` datetime DEFAULT NULL COMMENT '更改日期',
  `note` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `seq` int DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='小程序套餐类型';