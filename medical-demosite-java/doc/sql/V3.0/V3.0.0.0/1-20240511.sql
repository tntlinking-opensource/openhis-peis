--md_pacs_result表添加唯一索引（东区、锦都已更新，西区未更新）
ALTER TABLE md_pacs_result
    ADD CONSTRAINT unx_patientcode_item_id UNIQUE (patientcode, item_id);


--电测听表添加唯一索引 （锦都、东区、西区已更新）
ALTER TABLE `medical_prod`.`md_electro_audiometer`
DROP INDEX `idx_ea_patientcode`,
ADD UNIQUE INDEX `idx_ea_patientcode`(`patientcode`) USING BTREE;




--修改输入码长度 （东区已更新，西区、锦都未更新）
ALTER TABLE `medical_prod`.`md_zy_vs_summary`
    MODIFY COLUMN `input_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码' AFTER `for_person_influence`;







