--报告表添加唯一约束
ALTER TABLE md_report
    ADD CONSTRAINT unx_patientcode_disease_health UNIQUE (patientcode, disease_health);


-- 添加团体预约和预约设置关联表
CREATE TABLE `md_reservation_group_time` (
     `id` VARCHAR ( 32 ) CHARACTER
         SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
     `group_id` VARCHAR ( 32 ) CHARACTER
         SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '团体预约id',
     `time_id` VARCHAR ( 32 ) CHARACTER
         SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '预约时间段ID',
     `count` INT DEFAULT '0' COMMENT '预约人数',
     `createdate` datetime DEFAULT NULL COMMENT '创建时间',
     PRIMARY KEY ( `id` ) USING BTREE,
     KEY `idx_group_id` ( `group_id` ) USING BTREE,
     KEY `idx_time_id` ( `time_id` ) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '团体预约和预约设置关联表';



--添加同步设置
INSERT INTO `medical_prod`.`sys_table_config` (`id`, `db_name`, `table_name`, `key_name`, `cid_name`, `pid`, `pkey_name`, `cur_key_name`, `sync_type`, `need_sync`, `sync_condition`, `file_key_name`, `sync_now`, `createdate`, `modifydate`) VALUES (529, 'medicalcenter', 'md_reservation_group_time', 'id', NULL, 515, 'id', 'group_id', 3, 2, 0, NULL, 1, '2023-12-07 16:14:31', '2024-04-26 15:08:20');


