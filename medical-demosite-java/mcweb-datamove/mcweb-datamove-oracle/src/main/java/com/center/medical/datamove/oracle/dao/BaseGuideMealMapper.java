package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BaseGuideMeal;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (BaseGuideMeal)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:32
 */
public interface BaseGuideMealMapper extends BaseMapper<BaseGuideMeal> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BaseGuideMeal查询参数
     * @return 分页数据
     */
    IPage<BaseGuideMeal> getPage(PageParam<BaseGuideMeal> page, @Param("param") BaseGuideMeal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseGuideMeal getInfoById(@Param("id") String id);

}
