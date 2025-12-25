-- 添加套餐和分中心唯一约束 线上、东区、西区、华欧、城阳、胶州、平度、潍坊、长沙、霸州已更新
ALTER TABLE md_mealandfzx
    ADD CONSTRAINT unx_tcid_fzxid UNIQUE (tcid, fzxid);