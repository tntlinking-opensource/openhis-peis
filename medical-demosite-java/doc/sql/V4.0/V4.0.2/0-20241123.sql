-- 添加字段全部区已添加
ALTER TABLE `sys_code_config` ADD COLUMN `code_setting` text CHARACTER
SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '参数设置对象' AFTER `bs_flag`;
ALTER TABLE `sys_code_config` ADD COLUMN `source_id` VARCHAR ( 32 ) CHARACTER
SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '合作第三方ID（关联sys_business_source.source_id）' AFTER `value_text`;