package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.Describe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescribeRepository extends JpaRepository<Describe,String> {
    Describe findByPatientcodeAndItemId(String patientcode,String itemId);
}
