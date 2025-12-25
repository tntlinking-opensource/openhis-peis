package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.dto.Hazards;

import java.util.List;

public interface HazardsRepository {
    List<Hazards> queryAllHazardList();
}
