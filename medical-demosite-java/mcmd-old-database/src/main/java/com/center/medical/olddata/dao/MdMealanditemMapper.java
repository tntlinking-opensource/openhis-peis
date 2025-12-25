package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdMealanditem;
import org.apache.ibatis.annotations.Param;

/**
 * 普通套餐与收费项目关联表(MdMealanditem)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 22:26:22
 */
public interface MdMealanditemMapper extends BaseMapper<MdMealanditem> {

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param MdMealanditem查询参数
     * @return 分页数据
     */
    IPage<MdMealanditem> getPage(PageParam<MdMealanditem> page, @Param("param") MdMealanditem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdMealanditem getInfoById(@Param("id") String id);

}
