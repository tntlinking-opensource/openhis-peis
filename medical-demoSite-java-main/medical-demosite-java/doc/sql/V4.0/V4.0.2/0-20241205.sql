-- 收费项目是否删除添加默认值 只更新了东区和线上
ALTER TABLE `medical_prod`.`md_items`
    MODIFY COLUMN `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除：0.未删除 1.已删除' AFTER `review_matters`;