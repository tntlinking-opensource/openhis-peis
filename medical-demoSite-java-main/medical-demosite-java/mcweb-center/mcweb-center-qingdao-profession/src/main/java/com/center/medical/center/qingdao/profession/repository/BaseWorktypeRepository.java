package com.center.medical.center.qingdao.profession.repository;

import com.center.medical.center.qingdao.profession.entity.persistent.BaseWorktype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseWorktypeRepository extends JpaRepository<BaseWorktype, String> {
    List<BaseWorktype> findByIsDelete(Integer isDelete); // 查询 is_delete 等于指定值的数据

}
