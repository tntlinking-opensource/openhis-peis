-- 接收易影云胶片二维码地址，东区已更新
ALTER TABLE `medical_prod`.`md_pacs_result_third`
ADD COLUMN `qr_code_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '云胶片二维码内容（网址）' AFTER `report_status`;