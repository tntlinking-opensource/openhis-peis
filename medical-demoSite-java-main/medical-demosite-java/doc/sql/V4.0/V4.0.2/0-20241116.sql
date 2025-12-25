-- 添加第体检者和分中心唯一索引，线上已添加
ALTER TABLE md_peispatient_and_fzx
    ADD CONSTRAINT unx_patient_id_fzx_id UNIQUE (patient_id, fzx_id);



-- 添加第订单和分中心唯一索引，线上已添加
ALTER TABLE md_orderandfzx
    ADD CONSTRAINT unx_ddid_fzxid UNIQUE (ddid, fzxid);