package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.dto.PeiUploadData;

import java.util.List;

public interface PeisStateQueryRepository {
    List<PeiUploadData> queryByPatientCodes(List<String> patientCodes);
}
