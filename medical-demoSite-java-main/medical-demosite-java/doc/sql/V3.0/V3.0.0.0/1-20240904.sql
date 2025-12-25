--综合分析-人员一览表 添加身份证号字段 全部分中心都已添加
ALTER TABLE `medical_prod`.`md_fx_personnelview`
    ADD COLUMN `idcardno` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号' AFTER `modifydate`;

