-- 对接瑞林萨尔健康管理系统
-- 东区、城阳、西区、淮南、平度、胶州、华欧已更新
CREATE TABLE `rilin_sync_time`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `createdate` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modifydate` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `table_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名',
  `last_sync_time` datetime NOT NULL COMMENT '上一次上传数据所用时间段的止时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对接瑞林萨尔健康管理系统，用于记录上一次上传数据的止时间。' ROW_FORMAT = DYNAMIC;

CREATE TABLE `rilin_sync_result`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
  `createdate` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modifydate` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_success` tinyint NOT NULL COMMENT '1上传成功 0上传失败',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传失败的错误信息',
  `table_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名',
  `table_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传失败的表ID',
  `patientcode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传失败的体检号',
  `start_time` datetime NULL DEFAULT NULL COMMENT '本次上传起始时间',
  `end_time` datetime NOT NULL COMMENT '本次上传止时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_rilin_result_is_success`(`is_success` ASC) USING BTREE,
  INDEX `idx_rilin_result_table_name`(`table_name` ASC) USING BTREE,
  INDEX `idx_rilin_result_end_time`(`end_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对接瑞林萨尔健康管理系统,记录数据上传结果' ROW_FORMAT = DYNAMIC;

