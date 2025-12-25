package com.center.medical.query.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.query.bean.dto.OverViewDto;
import org.apache.ibatis.annotations.Param;

/**
 * 可视化大屏(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-05-20 11:00:31
 */
public interface VisualLargeScreenMapper extends BaseMapper<Peispatient> {


    /**
     * 体检中心概况
     * @param param
     * @return
     */
    OverViewDto getOverViewDto(@Param("param") BaseParam param);
}
