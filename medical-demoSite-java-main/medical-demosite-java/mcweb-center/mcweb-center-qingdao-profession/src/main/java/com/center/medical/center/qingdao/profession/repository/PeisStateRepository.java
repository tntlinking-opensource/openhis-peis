package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.PeisState;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PeisStateRepository extends JpaRepositoryImplementation<PeisState, String> {
    PeisState findByPatientcode(String patientcode);

    List<PeisState> findAllByPatientcodeIn(Collection<String> patintcode);
}
