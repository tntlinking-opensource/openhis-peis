package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDevicetypePositionCheckitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 东软pacs部位方法基础表(MdDevicetypePositionCheckitem)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:21
 */
public interface MdDevicetypePositionCheckitemMapper extends BaseMapper<MdDevicetypePositionCheckitem> {

    /**
     * 分页查询[东软pacs部位方法基础表]列表
     *
     * @param page  分页参数
     * @param param MdDevicetypePositionCheckitem查询参数
     * @return 分页数据
     */
    IPage<MdDevicetypePositionCheckitem> getPage(PageParam<MdDevicetypePositionCheckitem> page, @Param("param") MdDevicetypePositionCheckitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDevicetypePositionCheckitem getInfoById(@Param("id") String id);

}
