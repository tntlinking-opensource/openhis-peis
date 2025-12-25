--创建帮助文档数据表
CREATE TABLE `md_help_document` (
    `id` VARCHAR ( 32 ) CHARACTER
        SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `type` VARCHAR ( 32 ) CHARACTER
        SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型标识',
    `documentName` VARCHAR ( 100 ) CHARACTER
        SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文档名称',
    `url_pdf` text CHARACTER
        SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'pdf地址',
    `is_delete` TINYINT ( 1 ) UNSIGNED ZEROFILL DEFAULT '0' COMMENT '是否已删除：0.否 1.是',
    `createdate` datetime DEFAULT NULL,
    `modifydate` datetime DEFAULT NULL,
    PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '帮助文档';



INSERT INTO `medical_prod`.`md_help_document` (`id`, `type`, `documentName`, `url_pdf`, `is_delete`, `createdate`, `modifydate`) VALUES ('1', '1', '护理人员帮助', '/newimage/images/common/01/20240424/86ec1e5a0c664acdb407320173640864.pdf', 0, '2024-04-24 13:34:21', '2024-04-24 13:34:24');
INSERT INTO `medical_prod`.`md_help_document` (`id`, `type`, `documentName`, `url_pdf`, `is_delete`, `createdate`, `modifydate`) VALUES ('2', '1', '科室医生帮助', '/newimage/images/common/01/20240424/a46aab27fd3b4febb06d3f328864537d.pdf', 0, '2024-04-24 13:34:21', '2024-04-24 13:34:24');
INSERT INTO `medical_prod`.`md_help_document` (`id`, `type`, `documentName`, `url_pdf`, `is_delete`, `createdate`, `modifydate`) VALUES ('3', '1', '客服前台帮助', '/newimage/images/common/01/20240424/2023a75a0d3944d989819b6245b0f98d.pdf', 0, '2024-04-24 13:34:21', '2024-04-24 13:34:24');
INSERT INTO `medical_prod`.`md_help_document` (`id`, `type`, `documentName`, `url_pdf`, `is_delete`, `createdate`, `modifydate`) VALUES ('4', '1', '销售人员帮助', '/newimage/images/common/01/20240424/0b513a3b23b048a894be6541f7cb7c07.pdf', 0, '2024-04-24 13:34:21', '2024-04-24 13:34:24');
INSERT INTO `medical_prod`.`md_help_document` (`id`, `type`, `documentName`, `url_pdf`, `is_delete`, `createdate`, `modifydate`) VALUES ('5', '1', '影像医生帮助', '/newimage/images/common/01/20240424/61ed784f44ec415485f592e3a1afc4d0.pdf', 0, '2024-04-24 13:34:21', '2024-04-24 13:34:24');
INSERT INTO `medical_prod`.`md_help_document` (`id`, `type`, `documentName`, `url_pdf`, `is_delete`, `createdate`, `modifydate`) VALUES ('6', '1', '硬件运维帮助', '/newimage/images/common/01/20240424/0331f3096e2a4c208548c7a78a954ee3.pdf', 0, '2024-04-24 13:34:21', '2024-04-24 13:34:24');
INSERT INTO `medical_prod`.`md_help_document` (`id`, `type`, `documentName`, `url_pdf`, `is_delete`, `createdate`, `modifydate`) VALUES ('7', '1', '运营院长帮助', '/newimage/images/common/01/20240424/991e0577df9d4c779bfa28bc6f62661c.pdf', 0, '2024-04-24 13:34:21', '2024-04-24 13:34:24');
INSERT INTO `medical_prod`.`md_help_document` (`id`, `type`, `documentName`, `url_pdf`, `is_delete`, `createdate`, `modifydate`) VALUES ('8', '1', '总检医生帮助', '/newimage/images/common/01/20240424/4f78834e36bd47e2aaa5b28e13b99945.pdf', 0, '2024-04-24 13:34:21', '2024-04-24 13:34:24');


INSERT INTO `medical_prod`.`sys_table_config` (`id`, `db_name`, `table_name`, `key_name`, `cid_name`, `pid`, `pkey_name`, `cur_key_name`, `sync_type`, `need_sync`, `sync_condition`, `file_key_name`, `sync_now`, `createdate`, `modifydate`) VALUES (528, 'medicalcenter', 'md_help_document', 'id', NULL, NULL, NULL, NULL, 1, 1, 0, 'url_pdf', 1, '2023-12-07 16:14:31', '2023-12-07 16:14:31');
