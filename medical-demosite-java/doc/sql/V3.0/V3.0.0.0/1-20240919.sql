--sys_user_dep表添加唯一索引（线上、西区、东区、锦都、华欧已更新）
ALTER TABLE sys_user_dep
    ADD CONSTRAINT unx_user_id_dep_id UNIQUE (user_id, dep_id);


--kd_saleer添加主键（线上、西区、东区、锦都、华欧已更新）
ALTER TABLE `kd_saleer`
    MODIFY COLUMN `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'md5' AFTER `centerorgname`,
    ADD PRIMARY KEY (`md5`);


--md_user_saleer表添加唯一索引（线上、西区、东区、锦都、华欧已更新）
ALTER TABLE md_user_saleer
    ADD CONSTRAINT unx_user_id UNIQUE (user_id);


--md_review表添加唯一索引（线上、东区、锦都、西区、华欧已更新）
ALTER TABLE md_review
    ADD CONSTRAINT unx_patientcode UNIQUE (patientcode);


--md_pacs_result表添加唯一索引（线上、东区、锦都、华欧、西区已更新）
ALTER TABLE md_pacs_result
    ADD CONSTRAINT unx_patientcode_item_id UNIQUE (patientcode, item_id);



--md_peis_state表添加唯一索引（线上、东区、锦都已更新，西区、华欧未更新）
ALTER TABLE md_peis_state
    ADD CONSTRAINT unx_patientcode UNIQUE (patientcode);


