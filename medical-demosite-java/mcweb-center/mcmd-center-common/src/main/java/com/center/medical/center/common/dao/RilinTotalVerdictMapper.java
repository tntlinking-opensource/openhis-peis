package com.center.medical.center.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.center.common.bean.model.RilinTotalVerdict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ZJ总检结论词表(TotalVerdict)数据库访问层
 * @since 2025-03-24 11:21:21
 */
public interface RilinTotalVerdictMapper extends BaseMapper<RilinTotalVerdict> {

    void removeByPatientcodes(@Param("patientcodes")List<String> patientcodes);
}
