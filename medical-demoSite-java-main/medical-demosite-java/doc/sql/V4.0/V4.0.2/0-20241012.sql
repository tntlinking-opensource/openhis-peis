-- 添加体检者异常忽视关联表（只更新了线上）
CREATE TABLE `md_peispatient_abnormal_ignore` (
      `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
      `createdate` datetime DEFAULT NULL COMMENT '创建时间',
      `patient_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '体检者ID(md_peispatient的id)',
      `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者id',
      PRIMARY KEY (`id`) USING BTREE,
      KEY `idx_paf_patient_id` (`patient_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='体检者异常忽视关联表';