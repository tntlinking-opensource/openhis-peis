--团检报告添加分中心id
ALTER TABLE `medical_prod`.`md_ball_check_report`
    ADD COLUMN `fzxid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID' AFTER `reason`;



--修改同步配置
UPDATE `medical_prod`.`sys_table_config` SET `db_name` = 'medicalcenter', `table_name` = 'md_ball_check_report', `key_name` = 'id', `cid_name` = 'fzxid', `pid` = NULL, `pkey_name` = NULL, `cur_key_name` = NULL, `sync_type` = 3, `need_sync` = 2, `sync_condition` = 0, `file_key_name` = NULL, `sync_now` = 1, `createdate` = '2023-12-07 16:14:31', `modifydate` = '2024-04-17 10:28:01' WHERE `id` = 41;
UPDATE `medical_prod`.`sys_table_config` SET `db_name` = 'medicalcenter', `table_name` = 'md_sample_person', `key_name` = 'id', `cid_name` = NULL, `pid` = 191, `pkey_name` = 'patientcode', `cur_key_name` = 'patientcode', `sync_type` = 3, `need_sync` = 2, `sync_condition` = 0, `file_key_name` = NULL, `sync_now` = 1, `createdate` = '2023-12-07 16:14:31', `modifydate` = '2024-04-17 09:23:15' WHERE `id` = 227;
