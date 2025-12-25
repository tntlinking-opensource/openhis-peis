package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.QxUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QxUsersRepository extends JpaRepository<QxUsers, String> {
    QxUsers getByUsername(String username);
}