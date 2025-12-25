package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppLaborCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (AppLaborCharge)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:42
 */
public interface AppLaborChargeMapper extends BaseMapper<AppLaborCharge> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param AppLaborCharge查询参数
     * @return 分页数据
     */
    IPage<AppLaborCharge> getPage(PageParam<AppLaborCharge> page, @Param("param") AppLaborCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppLaborCharge getInfoById(@Param("id") String id);

}
