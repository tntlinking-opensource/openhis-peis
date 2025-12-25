### 1.更改windows下mysql用户权限

- 使用navicat登录root账号（如远程登录则需要有远程权限）

```sql
GRANT
SELECT
ON medical_prod.v_tap TO 'username'@'%';
FLUSH
PRIVILEGES;
```

### 2.统计某个时间新增体检项目的统计数据
```sql
-- ### 2.统计某个时间新增体检项目的统计数据
SELECT
    tem.id AS ID,
    tem.examfeeitem_name AS 项目名称,
    tem.examfeeitemid AS 项目编号,
    tem.createdate AS 维护时间,
    tem.unitprice AS 原价,
    tem.costprice AS 成本价,
    COUNT(t1.id) AS 人次,
    SUM(t1.price) AS 原价合计金额,
    FORMAT(SUM(t2.moneyamountpaid / (
		SELECT SUM(price) FROM md_peispatientfeeitem WHERE id_patient=t1.id_patient
		) * t1.price), 2) AS 折扣合计金额,
    FORMAT(SUM(t1.factprice), 2) AS 实收合计金额
FROM
    (SELECT id, examfeeitem_name, examfeeitemid, createdate, unitprice, costprice
     FROM md_items
     WHERE createdate > '2024-01-01 00:00:00') tem
LEFT JOIN md_peispatientfeeitem t1 ON t1.id_examfeeitem = tem.id
LEFT JOIN md_peispatient t2 ON t1.id_patient = t2.patientcode
WHERE
    t2.f_registered = 1
--     AND t2.f_feecharged = 1
    AND t1.f_registered = 1
    AND t1.f_feecharged = 1
    AND t1.f_mark_feereturn != 1
GROUP BY
    tem.id, tem.examfeeitem_name, tem.examfeeitemid, tem.createdate, tem.unitprice, tem.costprice;
```

### 统计某个时间所有体检项目的统计数据
```sql
-- ### 2.统计某个时间所有体检项目的统计数据
SELECT
    tem.id AS ID,
    tem.examfeeitem_name AS 项目名称,
    tem.examfeeitemid AS 项目编号,
    tem.createdate AS 维护时间,
    tem.unitprice AS 原价,
    tem.costprice AS 成本价,
    tem.costprice AS 成本价,
    COUNT(t1.id) AS 人次,
    SUM(t1.price) AS 原价合计金额,
    FORMAT(SUM(t2.moneyamountpaid / (
		SELECT SUM(price) FROM md_peispatientfeeitem WHERE id_patient=t1.id_patient
		) * t1.price), 2) AS 折扣合计金额,
    FORMAT(SUM(t1.factprice), 2) AS 实收合计金额
FROM md_items tem
LEFT JOIN md_peispatientfeeitem t1 ON t1.id_examfeeitem = tem.id
LEFT JOIN md_peispatient t2 ON t1.id_patient = t2.patientcode
WHERE
		t2.dateregister  > '2024-01-01 00:00:00'
    AND t2.f_registered = 1
--     AND t2.f_feecharged = 1
    AND t1.f_registered = 1
    AND t1.f_feecharged = 1
    AND t1.f_mark_feereturn != 1
GROUP BY
    tem.id, tem.examfeeitem_name, tem.examfeeitemid, tem.createdate, tem.unitprice, tem.costprice;
```

### 3.查询体检者收费项目数量和对应套餐项目数量不一样多
```sql
SELECT tem.* FROM (
SELECT t1.patientcode, t1.numorgresv, t1.f_paused
, (SELECT count(*) FROM md_peispatientfeeitem WHERE id_patient = t1.patientcode) AS t2count
, (SELECT count(*) FROM md_mealanditem WHERE tcid = t1.id_tjtc) AS t3count
, (SELECT count(*) FROM md_comboanditem WHERE tcid = t1.id_tjtc) AS t4count
FROM (
SELECT patientcode, numorgresv, id_tjtc, f_paused FROM md_peispatient t0 
LEFT JOIN md_peisorgreservationgroup t5 ON t0.id_orgreservationgroup = t5.id
WHERE 
-- patientcode='APP0230281549'
t5.f_grouppaused=0 AND 
t0.f_registered != 1 AND (t0.f_paused=0 OR t0.f_paused IS NULL) AND t0.numorgresv != '' AND t0.numorgresv != '-1' AND t0.numorgresv IS NOT NULL
-- AND t0.createdate > '2023-01-01 00:00:00'
-- AND t0.createdate < '2024-06-01 00:00:00'
) t1

) tem
WHERE 
tem.t2count < tem.t3count AND tem.t2count>0 AND tem.t3count>0
ORDER BY tem.patientcode
-- GROUP BY tem.numorgresv
```

### 4.查询订单是否缺项
```sql
SELECT * FROM (
SELECT patientcode, patientname, t2.zhjg, SUM(t3.factprice) AS itemp FROM md_peispatient t1
LEFT JOIN md_createmeal t2 ON t1.id_tjtc = t2.id
LEFT JOIN md_peispatientfeeitem t3 ON t1.patientcode = t3.id_patient
WHERE t1.numorgresv='012424' AND t1.f_registered=0 
GROUP BY t3.id_patient
) tem
WHERE tem.zhjg != tem.itemp
```