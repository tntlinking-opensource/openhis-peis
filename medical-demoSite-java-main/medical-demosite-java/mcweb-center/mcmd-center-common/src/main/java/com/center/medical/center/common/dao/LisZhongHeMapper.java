package com.center.medical.center.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.center.common.bean.dto.LisZhongHeDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 瑞美Lis（锦都）
 * @author xhp
 * @since 2023-07-26 11:11
 */
@Repository
public interface LisZhongHeMapper extends BaseMapper<Peispatientexamitem> {

    List<LisZhongHeDto> selectList(@Param("patientcode8") String patientcode8);

}
