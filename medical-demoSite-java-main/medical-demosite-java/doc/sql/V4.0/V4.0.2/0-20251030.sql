-- 添加md_code_corresponding的索引
-- 线上已更新
-- 、东区、西区、华欧、城阳、胶州、平度、潍坊、长沙、霸州未更新（时间太长）
CREATE INDEX index_new_code ON md_code_corresponding (new_code);