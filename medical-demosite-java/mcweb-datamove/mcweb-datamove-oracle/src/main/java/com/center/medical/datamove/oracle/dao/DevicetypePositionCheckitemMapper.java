package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.DevicetypePositionCheckitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (DevicetypePositionCheckitem)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:10
 */
public interface DevicetypePositionCheckitemMapper extends BaseMapper<DevicetypePositionCheckitem> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param DevicetypePositionCheckitem查询参数
     * @return 分页数据
     */
    IPage<DevicetypePositionCheckitem> getPage(PageParam<DevicetypePositionCheckitem> page, @Param("param") DevicetypePositionCheckitem param);


}
