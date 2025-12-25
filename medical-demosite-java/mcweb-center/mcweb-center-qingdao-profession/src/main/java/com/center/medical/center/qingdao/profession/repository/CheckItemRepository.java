package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.dto.CheckItemReference;

import java.util.List;

public interface CheckItemRepository {
    List<CheckItemReference> listAll();

    boolean hasPacsSign(String patientcode,String signId);
}
