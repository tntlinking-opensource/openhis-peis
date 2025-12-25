package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.AppCustomerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 环信客服人员(AppCustomerService)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:11:26
 */
public interface AppCustomerServiceMapper extends BaseMapper<AppCustomerService> {

    /**
     * 分页查询[环信客服人员]列表
     *
     * @param page  分页参数
     * @param param AppCustomerService查询参数
     * @return 分页数据
     */
    IPage<AppCustomerService> getPage(PageParam<AppCustomerService> page, @Param("param") AppCustomerService param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppCustomerService getInfoById(@Param("id") String id);

}
