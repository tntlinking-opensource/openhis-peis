package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmCustomerOperateHistory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放(CrmCustomerOperateHistory)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:44:57
 */
public interface CrmCustomerOperateHistoryMapper extends BaseMapper<CrmCustomerOperateHistory> {

    /**
     * 分页查询[客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放]列表
     *
     * @param page  分页参数
     * @param param CrmCustomerOperateHistory查询参数
     * @return 分页数据
     */
    IPage<CrmCustomerOperateHistory> getPage(PageParam<CrmCustomerOperateHistory> page, @Param("param") CrmCustomerOperateHistory param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmCustomerOperateHistory getInfoById(@Param("id") String id);

}
