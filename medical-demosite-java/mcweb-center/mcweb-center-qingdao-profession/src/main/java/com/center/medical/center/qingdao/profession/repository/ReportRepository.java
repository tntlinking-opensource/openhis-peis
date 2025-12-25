package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.Report;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepositoryImplementation<Report,String> {
    List<Report> findAllByPatientcodeIn(Collection<String> patientcode);

    Report findByPatientcode(String patientcode);
}
