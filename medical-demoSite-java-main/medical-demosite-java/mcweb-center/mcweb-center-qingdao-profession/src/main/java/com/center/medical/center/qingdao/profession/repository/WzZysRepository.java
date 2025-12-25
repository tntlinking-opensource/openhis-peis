package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.WzZys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WzZysRepository extends JpaRepository<WzZys, String> {
    List<WzZys> findAllByIdPatientarchive(String idPatientarchive);

    List<WzZys> findAllByDjls(String djls);
}
