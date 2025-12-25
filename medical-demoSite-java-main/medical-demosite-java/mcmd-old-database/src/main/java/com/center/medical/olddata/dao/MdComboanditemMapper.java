package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdComboanditem;
import org.apache.ibatis.annotations.Param;

/**
 * 维护最小套餐与收费项目关联表(MdComboanditem)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 22:05:52
 */
public interface MdComboanditemMapper extends BaseMapper<MdComboanditem> {

    /**
     * 分页查询[维护最小套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param MdComboanditem查询参数
     * @return 分页数据
     */
    IPage<MdComboanditem> getPage(PageParam<MdComboanditem> page, @Param("param") MdComboanditem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdComboanditem getInfoById(@Param("id") String id);

}
