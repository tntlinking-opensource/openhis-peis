-- 修改md_peispatient的input_code长度
-- 线上、东区、华欧、城阳、胶州、平度、潍坊、长沙、霸州已更新
-- 西区、未更新（时间太长）
ALTER TABLE `medical_prod`.`md_peispatient`
    MODIFY COLUMN `input_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码' AFTER `patientname`;