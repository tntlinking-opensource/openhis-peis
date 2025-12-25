package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.Sellcustomer;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SellcustomerRepository extends JpaRepositoryImplementation<Sellcustomer, String> {
    List<Sellcustomer> findAllByIdIn(Collection<String> id);

}
