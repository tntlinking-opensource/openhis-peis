--小程序 添加随行支付配置表
CREATE TABLE `md_suixing_config` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '记录ID',
     `branch_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分中心id',
     `orgId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构编号',
     `mno` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商户编号',
     `privateKey` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '私钥',
     `createdate` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `modifydate` datetime DEFAULT NULL COMMENT '更新时间',
     PRIMARY KEY (`id`) USING BTREE,
     KEY `ix_branch_id` (`branch_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='随行支付配置参数';

