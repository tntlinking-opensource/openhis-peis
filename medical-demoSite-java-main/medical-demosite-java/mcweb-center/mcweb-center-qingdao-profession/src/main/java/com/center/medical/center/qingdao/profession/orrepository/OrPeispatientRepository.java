package com.center.medical.center.qingdao.profession.orrepository;

import com.center.medical.center.qingdao.profession.entity.oracle.OrPeispatient;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface OrPeispatientRepository extends JpaRepositoryImplementation<OrPeispatient, String> {
    List<OrPeispatient> findAllByIdExamtypeIn(Collection<String> idExamtype);

    default List<OrPeispatient> findAllByIdExamtype() {
        return findAllByIdExamtypeIn(Arrays.asList("1", "2"));
    }

    OrPeispatient findByPatientcode(String patientcode);

    OrPeispatient findByInpatientnoEquals(String inpatientno);

    //查找下一次补检体检号
    OrPeispatient findByInsuranceno(String patientcode);
}
