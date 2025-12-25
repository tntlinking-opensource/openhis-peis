package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.Comboexamitem;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboexamitemRepository extends JpaRepositoryImplementation<Comboexamitem,String> {
}
