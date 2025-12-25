package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.BaseZone;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseZoneRepository extends JpaRepositoryImplementation<BaseZone,String> {
    List<BaseZone> findByZoneCodeContains(String zoneCode);

    default List<BaseZone> findAllQingdao() {
        return findByZoneCodeContains("3702");
    }

    List<BaseZone> findByZoneNameEquals(String zoneName);

}
