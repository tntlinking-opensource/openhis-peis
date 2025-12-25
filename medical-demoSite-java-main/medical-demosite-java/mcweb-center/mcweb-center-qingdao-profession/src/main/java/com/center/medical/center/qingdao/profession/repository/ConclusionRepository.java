package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.dto.ConclusionDto;

import java.util.List;

public interface ConclusionRepository {

    List<ConclusionDto> queryByPatientCode(String patientCode);

    List<ConclusionDto> queryByFirstPatientCode(String patientCode,String harmId);

    List<ConclusionDto> queryFinalByPatientCode(String patientCode,String harmId);

    String querySectionTotal(String patientCode);
}
