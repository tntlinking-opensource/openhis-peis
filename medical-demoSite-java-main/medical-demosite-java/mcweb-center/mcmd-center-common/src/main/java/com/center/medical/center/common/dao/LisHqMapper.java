package com.center.medical.center.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.center.common.bean.dto.LisHqDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 虹桥lis（开发区中心）
 */
@Repository
public interface LisHqMapper extends BaseMapper<Peispatientexamitem> {

    List<LisHqDto> selectList(@Param("patientcode8") String patientcode8);

}
