-- 更新收费项目字段（只更新了东西区，华欧，线上）
ALTER TABLE `kd_remittance`
    ADD COLUMN `online` tinyint(1) NULL COMMENT '是否线上创建：0.线下 1线上' AFTER `branch_id`,
    ADD COLUMN `createdate` datetime DEFAULT NULL COMMENT '创建时间' AFTER `online`,
ADD COLUMN `modifydate` datetime NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期' AFTER `createdate`;

-- 新定时任务（只更新了东西区，线上,华欧）
INSERT INTO `sys_job` (`job_id`, `job_name`, `job_group`, `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (63, '公共定时处理任务', 'DEFAULT', 'commonTask.deal(600, 63L)', '0 0/10 * * * ?', '3', '1', '0', 'admin', '2024-06-17 18:25:09', 'admin', '2024-07-06 09:10:03', 'XXX.XXX.XXX.XXX');
-- 新定时任务（只更新了东西区，线上,华欧）
UPDATE kd_remittance SET `online`=0, createdate=transdate, modifydate=transdate




--md_other_report表添加唯一索引（线上、东区、锦都、华欧、西区已更新）
ALTER TABLE md_other_report
    ADD CONSTRAINT unx_patientcode_report_type UNIQUE (patientcode, report_type);