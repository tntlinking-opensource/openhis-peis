package com.center.medical.pacslis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.pacslis.bean.dto.MiddleDbYblxListDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xhp
 * @since 2023-02-28 13:38
 */
@Repository
public interface MiddleDbInterfaceMapper extends BaseMapper<Peispatient> {
    List<MiddleDbYblxListDto> selectYblxList(String patientcode);
}
