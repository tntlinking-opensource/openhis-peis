package com.center.medical.center.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.center.common.bean.dto.LisJinweiDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 金卫Lis（华欧）
 * @author xhp
 * @since 2024-05-06 10:29
 */
@Repository
public interface LisJinweiMapper extends BaseMapper<Peispatientexamitem> {

    List<LisJinweiDto> selectList(@Param("patientcode8") String patientcode8);
}
