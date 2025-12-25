package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.BaseDictionaryMatch;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDictionaryMatchRepository extends JpaRepositoryImplementation<BaseDictionaryMatch, String> {
}
