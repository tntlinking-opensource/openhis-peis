-- 修改crm_order_plan的branch_id长度
ALTER TABLE `medical_prod`.`crm_order_plan`
    MODIFY COLUMN `branch_id` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分中心ID' AFTER `dept_id`;