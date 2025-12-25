package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.Peispatient;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface PeispatientRepository extends JpaRepositoryImplementation<Peispatient, String> {
    List<Peispatient> findAllByIdExamtypeIn(Collection<String> idExamtype);

    default List<Peispatient> findAllByIdExamtype() {
        return findAllByIdExamtypeIn(Arrays.asList("1", "2"));
    }

    Peispatient findByPatientcode(String patientcode);

    Peispatient findByInpatientnoEquals(String inpatientno);

    //查找下一次补检体检号
    Peispatient findByInsuranceno(String patientcode);
}
