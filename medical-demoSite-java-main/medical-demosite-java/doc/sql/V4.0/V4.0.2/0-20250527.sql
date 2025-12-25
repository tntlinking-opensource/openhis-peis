--添加问卷类型，全部已更新
ALTER TABLE `md_questionnaire`
    ADD COLUMN `type` int NULL DEFAULT 0 COMMENT '类型 0问卷1满意度' AFTER `countreportoccupationxml`;



