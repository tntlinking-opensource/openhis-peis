package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.Peispatientfeeitem;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PeispatientfeeitemRepository extends JpaRepositoryImplementation<Peispatientfeeitem,String> {
    List<Peispatientfeeitem> findAllByIdPatientIn(Collection<String> idPatient);

    List<Peispatientfeeitem> findAllByIdPatient(String idPatient);

    List<Peispatientfeeitem> findAllByIdPatientAndExamfeeitemNameContaining(String idPatient, String examfeeitemName);
}
