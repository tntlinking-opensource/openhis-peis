-- 对接易影
-- 华欧、东区、西区已更新
-- 接口一视图，院内影像接收前置机在接收到检查图像时，通过PACS\RIS\HIS提供的视图，通过检查关键字段向视图进行查询，返回患者的检查申请信息
CREATE
	OR REPLACE VIEW v_yiying_patient_item AS SELECT
	pi.id,
	substring( pi.id_patient, length( pi.id_patient )- 7 ) patient_id,
	group_concat( bp.en_name SEPARATOR '/' ) en_parts,
	group_concat( bp.part_name SEPARATOR ',' ) parts,
	cd.jklx modality,
	p.patientname,
	( CASE WHEN p.id_sex = '0' THEN '男' WHEN p.id_sex = '1' THEN '女' ELSE '' END ) sex,
	p.age,
	p.idcardno,
	p.phone,
	p.birthdate,
	p.dateregister,
	'' method,
	'体检' source,
	pi.id_patient outpatient_number,
	pi.id_patient hospitalization_number,
	'' bed_number,
	p.doctorapply,
	cd.NAME dept_name,
	'' chief_complaint,
	'' medical_history,
	'' other,
	0 is_emergency
FROM
    md_peispatient p
	INNER JOIN md_peispatientfeeitem pi ON pi.id_patient = p.patientcode
	INNER JOIN sys_department cd ON cd.id = pi.id_ks
	INNER JOIN md_items i ON i.id = pi.id_examfeeitem
	INNER JOIN md_pacs_items pacsi ON pacsi.examfeeitem_code = i.examfeeitem_code
	INNER JOIN md_pacs_item_part ip ON ip.item_id = pacsi.id
	INNER JOIN md_pacs_base_part bp ON ip.part_id = bp.id
	AND bp.en_name IS NOT NULL
	AND bp.en_name != 'null'
WHERE
	pi.id_ks IN ( '173', '24', '402848e3625a920201625ff99a3404a5' )
	AND ( pi.f_giveup IS NULL OR pi.f_giveup = 0 )
	AND ( pi.sfjj IS NULL OR pi.sfjj = 0 )
	AND ( pi.change_item IS NULL OR pi.change_item = 0 )
	AND pi.f_feecharged = 1
	AND pi.f_transferedhl7 IS NULL
	AND p.f_registered = 1
	AND ( p.f_paused IS NULL OR p.f_paused != 1 )
GROUP BY
	pi.id,
	pi.id_patient,
	cd.jklx,
	p.patientname,
	p.id_sex,
	p.age,
	p.idcardno,
	p.phone,
	p.birthdate,
	p.dateregister,
	p.doctorapply,
	cd.NAME;

-- 接口二表 院内影像前置机查询云端已完成的报告，通过检查关键字段向PACS提供的视图中写入报告信息
CREATE TABLE `md_pacs_result_third`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一值(md_peispatientfeeitem表id)',
  `patient_id` varchar(14) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '体检号（8位）',
  `patientname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `examresultdesc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查结果描述',
  `examresultsummary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查结果总结',
  `examdatetime` datetime NULL DEFAULT NULL COMMENT '检查时间',
  `is_positive` tinyint(1) NULL DEFAULT NULL COMMENT '1阳性，0阴性',
  `report_doctor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告医生',
  `audit_doctor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核医生',
  `report_status` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '已申请、已报告/未审核、已审核',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_prt_patient_id`(`patient_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '易影报告回传' ROW_FORMAT = DYNAMIC;

--创建定时任务
INSERT INTO `sys_job` (`job_id`, `job_name`, `job_group`, `invoke_target`, `cron_expression`, `misfire_policy`, `concurrent`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (100, '自动获取Pas结果数据', 'DEFAULT', 'pacsTask.receiveData(60)', '1 0/20 6-18 * * ?', '1', '1', '1', 'admin', SYSDATE(), 'admin', SYSDATE(), 'XXX.XXX.XXX.XXX');
