-- 修改职业最终结论 is_delete数据类型和默认值 西区、华欧、锦都未加
ALTER TABLE `medical_prod`.`md_zy_final_summary`
    MODIFY COLUMN `is_delete` tinyint(1) NULL DEFAULT 0 AFTER `memo`;