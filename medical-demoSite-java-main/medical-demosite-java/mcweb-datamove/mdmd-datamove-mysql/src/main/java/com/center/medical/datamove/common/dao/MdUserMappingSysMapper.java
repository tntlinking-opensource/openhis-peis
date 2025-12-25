package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdUserMappingSys;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 用户映射系统(MdUserMappingSys)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:23
 */
public interface MdUserMappingSysMapper extends BaseMapper<MdUserMappingSys> {

    /**
     * 分页查询[用户映射系统]列表
     *
     * @param page  分页参数
     * @param param MdUserMappingSys查询参数
     * @return 分页数据
     */
    IPage<MdUserMappingSys> getPage(PageParam<MdUserMappingSys> page, @Param("param") MdUserMappingSys param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键systemId
     * @return 详情信息
     */
    MdUserMappingSys getInfoById(@Param("id") String id);

}
