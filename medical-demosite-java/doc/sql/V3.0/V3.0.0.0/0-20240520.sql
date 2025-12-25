
-- 更新md_mealanditem表字段默认值
ALTER TABLE `md_mealanditem`
    MODIFY COLUMN `sfbx` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 0 COMMENT '是否备选：0或null.否 1.是' AFTER `sfxmid`,
    MODIFY COLUMN `is_system` tinyint(1) NULL DEFAULT 0 COMMENT '是否复制套餐：0或null.否 1.是' AFTER `modifydate`;









