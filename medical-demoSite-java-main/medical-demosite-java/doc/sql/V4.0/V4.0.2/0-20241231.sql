-- 角色表，添加角色类型（全部已更新）
ALTER TABLE `sys_role`
    ADD COLUMN `role_type` int NOT NULL DEFAULT 1 COMMENT '角色类型：0是系统，1是普通' AFTER `remark`;