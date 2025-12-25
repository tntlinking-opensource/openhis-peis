--体检者上传状态 添加唯一索引（线上、东区已更,西区、锦都未更新）
ALTER TABLE `md_peis_state`
DROP INDEX `idx_ps_patientcode`,
ADD UNIQUE INDEX `idx_patientcode`(`patientcode`) USING BTREE;