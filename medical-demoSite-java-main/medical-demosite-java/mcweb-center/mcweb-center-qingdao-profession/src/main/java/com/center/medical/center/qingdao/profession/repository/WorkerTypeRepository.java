package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.dto.WorkerType;

import java.util.List;

public interface WorkerTypeRepository {
    List<WorkerType> queryAllWorkerTypeList();
}
