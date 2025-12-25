package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.Peispatientexamitem;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PeispatientexamitemRepository extends JpaRepositoryImplementation<Peispatientexamitem, String> {
    List<Peispatientexamitem> findAllByPatientcodeIn(Collection<String> patientcode);

    List<Peispatientexamitem> findAllByPatientcode(String patientcode);

    List<Peispatientexamitem> findAllByPatientcodeAndExamitemNameRContaining(String patientcode, String examitemNameR);
}
