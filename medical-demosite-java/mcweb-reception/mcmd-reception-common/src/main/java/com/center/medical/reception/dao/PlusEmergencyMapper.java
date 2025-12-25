package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.param.PlusEmParam;
import com.center.medical.reception.bean.vo.PlusEmergencyVo;
import org.apache.ibatis.annotations.Param;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-02-02 15:30:32
 */
public interface PlusEmergencyMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page 分页参数
     * @return 分页数据
     */
    IPage<PlusEmergencyVo> getList(PageParam<PlusEmergencyVo> page, @Param("param") PlusEmParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

}
