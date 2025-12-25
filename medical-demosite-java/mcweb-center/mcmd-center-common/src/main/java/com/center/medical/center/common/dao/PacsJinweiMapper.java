package com.center.medical.center.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.center.common.bean.dto.PacsJinweiDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 金卫Pacs
 * @author xhp
 * @since 2024-05-11 8:30
 */
@Repository
public interface  PacsJinweiMapper extends BaseMapper<PacsResult> {

    List<PacsJinweiDto> selectList(@Param("patientcode8") String patientcode8);
}
