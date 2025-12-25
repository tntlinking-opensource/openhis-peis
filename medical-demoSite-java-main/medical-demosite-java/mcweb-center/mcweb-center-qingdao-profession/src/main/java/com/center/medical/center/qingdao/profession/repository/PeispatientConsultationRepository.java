package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.PeispatientConsultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeispatientConsultationRepository extends JpaRepository<PeispatientConsultation,String> {
    PeispatientConsultation findByPatientcode(String patientcode);
}
