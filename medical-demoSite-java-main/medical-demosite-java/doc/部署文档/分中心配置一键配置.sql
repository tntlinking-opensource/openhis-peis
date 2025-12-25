-- 以下配置是分中心需要单独配置的数据数据配置（已西区为例）

-- 一、设置定时任务的授权IP
-- 设置"数据同步任务执行器"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 4;
-- 设置"科室传图"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 8;
-- 设置"自动获取Lis结果数据"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 10;
-- 设置"每天自动生成体检号"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 11;
-- 设置"定时发送短信"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 12;
-- 设置"闲时从线下推送同步数据至线上"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 18;
-- 设置"系统操作日志缓存到本地文件"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 19;
-- 设置"数据同步记录缓存到本地文件"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 20;
-- 设置"LIS数据重发"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 21;
-- 设置"清除报告多余文件的定时任务"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 22;
-- 设置"数据同步任务执行器"定时任务授权IP
UPDATE `sys_job`
SET `remark` = 'XXX.XXX.XXX.XXX,XXX.XXX.XXX.XXX'
WHERE `job_id` = 23;


-- 二、设置参数配置，参数配置完了需要执行下更新参数缓存的接口