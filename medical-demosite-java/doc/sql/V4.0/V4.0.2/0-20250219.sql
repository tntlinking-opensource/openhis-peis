-- 角色表，添加角色类型（除城阳外，已全部更新）
ALTER TABLE md_orderandcombo
    ADD CONSTRAINT unx_ddid_tcid UNIQUE (ddid, tcid);





-- 添加项目成本价合计字段、已全部更新
ALTER TABLE `medical_prod`.`md_createcombo`
    ADD COLUMN `total_costprice` decimal(16, 4) NULL DEFAULT NULL COMMENT '项目成本价合计' AFTER `modifier`;