--添加体检者上传状态表唯一索引，都未更新
ALTER TABLE md_peis_state
    ADD CONSTRAINT unx_patientcode UNIQUE (patientcode);


--增大团检报告分中心长度，都未更新
ALTER TABLE `medical_prod`.`md_ball_check_report`
    MODIFY COLUMN `fzxid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID' AFTER `reason`;