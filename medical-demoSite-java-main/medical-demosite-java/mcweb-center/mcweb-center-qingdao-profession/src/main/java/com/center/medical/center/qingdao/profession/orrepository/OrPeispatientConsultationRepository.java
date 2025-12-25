package com.center.medical.center.qingdao.profession.orrepository;

import com.center.medical.center.qingdao.profession.entity.oracle.OrPeispatientConsultation;
import com.center.medical.center.qingdao.profession.entity.persistent.PeispatientConsultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrPeispatientConsultationRepository extends JpaRepository<OrPeispatientConsultation,String> {
    OrPeispatientConsultation findByPatientcode(String patientcode);
}
