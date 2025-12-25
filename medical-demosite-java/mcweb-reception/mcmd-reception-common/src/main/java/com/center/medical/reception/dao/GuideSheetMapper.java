package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatient;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-20 15:01:15
 */
public interface GuideSheetMapper extends BaseMapper<Peispatient> {

    /**
     * 获取体检者对应的收费项目信息
     * @param patientcode
     * @return
     */
    List<Map<String, Object>> getModelItems(@Param("patientcode") String patientcode);
}
