package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.BaseZoneQd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseZoneQdRepository extends JpaRepository<BaseZoneQd,String> {
    BaseZoneQd findByZoneCode(String zoneCode);
}
