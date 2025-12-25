package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDrinkingType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 饮酒种类(MdDrinkingType)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
public interface MdDrinkingTypeMapper extends BaseMapper<MdDrinkingType> {

    /**
     * 分页查询[饮酒种类]列表
     *
     * @param page  分页参数
     * @param param MdDrinkingType查询参数
     * @return 分页数据
     */
    IPage<MdDrinkingType> getPage(PageParam<MdDrinkingType> page, @Param("param") MdDrinkingType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDrinkingType getInfoById(@Param("id") String id);

}
