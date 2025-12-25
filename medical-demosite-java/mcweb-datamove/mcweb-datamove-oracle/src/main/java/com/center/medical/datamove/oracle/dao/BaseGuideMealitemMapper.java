package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BaseGuideMealitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (BaseGuideMealitem)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:34
 */
public interface BaseGuideMealitemMapper extends BaseMapper<BaseGuideMealitem> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BaseGuideMealitem查询参数
     * @return 分页数据
     */
    IPage<BaseGuideMealitem> getPage(PageParam<BaseGuideMealitem> page, @Param("param") BaseGuideMealitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseGuideMealitem getInfoById(@Param("id") String id);

}
