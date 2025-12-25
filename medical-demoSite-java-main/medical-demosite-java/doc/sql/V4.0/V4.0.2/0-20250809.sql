--添加md_peispatient表添加字段，只更新了测试、线上、东区、西区、华欧、城阳、胶州、平度、潍坊、长沙、霸州
ALTER TABLE md_peispatient
    ADD COLUMN sabc VARCHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'SABC等级';

