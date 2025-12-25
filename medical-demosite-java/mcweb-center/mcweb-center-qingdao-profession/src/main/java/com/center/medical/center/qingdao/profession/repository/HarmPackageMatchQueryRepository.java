package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.dto.HarmPackageMatchDto;

import java.util.List;

public interface HarmPackageMatchQueryRepository {
    List<HarmPackageMatchDto> query(String tcid, String jhys);
}
