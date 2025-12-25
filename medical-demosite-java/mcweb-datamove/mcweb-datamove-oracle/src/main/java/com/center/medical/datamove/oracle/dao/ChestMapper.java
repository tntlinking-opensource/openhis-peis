package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Chest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 此表为通用表，团检(Chest)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:36
 */
public interface ChestMapper extends BaseMapper<Chest> {

    /**
     * 分页查询[此表为通用表，团检]列表
     *
     * @param page  分页参数
     * @param param Chest查询参数
     * @return 分页数据
     */
    IPage<Chest> getPage(PageParam<Chest> page, @Param("param") Chest param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Chest getInfoById(@Param("id") String id);

}
