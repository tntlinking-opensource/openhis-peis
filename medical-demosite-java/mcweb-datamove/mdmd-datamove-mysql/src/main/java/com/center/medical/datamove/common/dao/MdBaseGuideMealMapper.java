package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdBaseGuideMeal;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 基础推荐套餐(MdBaseGuideMeal)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:09
 */
public interface MdBaseGuideMealMapper extends BaseMapper<MdBaseGuideMeal> {

    /**
     * 分页查询[基础推荐套餐]列表
     *
     * @param page  分页参数
     * @param param MdBaseGuideMeal查询参数
     * @return 分页数据
     */
    IPage<MdBaseGuideMeal> getPage(PageParam<MdBaseGuideMeal> page, @Param("param") MdBaseGuideMeal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdBaseGuideMeal getInfoById(@Param("id") String id);

}
