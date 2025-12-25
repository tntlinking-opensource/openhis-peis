-- 添加第三方条码字段，城阳、线上、东区、华欧已加，锦都、西区运行中
ALTER TABLE `medical_prod`.`md_peispatient`
    ADD COLUMN `third_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方条码  城阳健康证用到了' AFTER `modifydate`;