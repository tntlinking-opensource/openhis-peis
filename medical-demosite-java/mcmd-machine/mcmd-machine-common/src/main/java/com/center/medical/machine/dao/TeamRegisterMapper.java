package com.center.medical.machine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.machine.bean.dto.GetAllItemsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
public interface TeamRegisterMapper extends BaseMapper<Peispatient> {


    /**
     * 获取所有检查项目
     * @param patientCode
     * @return
     */
    List<GetAllItemsDto> getAllItems(@Param("patientCode") String patientCode);

    /**
     * 获取最大的顺序
     * @param patientCode
     * @return
     */
    Integer getChargeMaxNumIndex(@Param("patientCode") String patientCode);
}
