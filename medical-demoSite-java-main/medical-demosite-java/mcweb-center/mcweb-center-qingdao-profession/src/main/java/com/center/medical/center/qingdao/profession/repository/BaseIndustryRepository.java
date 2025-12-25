package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.BaseIndustry;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseIndustryRepository extends JpaRepositoryImplementation<BaseIndustry, String> {
    BaseIndustry findBySub(String sub);

    BaseIndustry findBySubAndMiddleAndMajorAndCategory(String sub, String middle, String major, String category);
}
