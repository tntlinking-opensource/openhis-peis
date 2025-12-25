package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BaseZoneQd;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 所属地区(BaseZoneQd)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:41
 */
public interface BaseZoneQdMapper extends BaseMapper<BaseZoneQd> {

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param BaseZoneQd查询参数
     * @return 分页数据
     */
    IPage<BaseZoneQd> getPage(PageParam<BaseZoneQd> page, @Param("param") BaseZoneQd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseZoneQd getInfoById(@Param("id") String id);

}
