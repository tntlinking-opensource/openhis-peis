package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.dto.ReviewPatientDTO;
import com.center.medical.center.qingdao.profession.entity.persistent.PeisState;

import java.util.Date;
import java.util.List;

public interface PeisStateExtRepository {

    List<String> findPatientCodes();

    void insert(PeisState peisState);

    void update(PeisState peisState);

    List<String> findByCreateDateRangle(Date startDate, Date endDate);

    List<String> queryByOrderNo(String orderNo);

    List<ReviewPatientDTO> findReviewByDateRangle(Date startDate, Date endDate);

}
