package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.BaseDictionary;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDictionaryRepository extends JpaRepositoryImplementation<BaseDictionary, String> {
}
