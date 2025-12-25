package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.CustomerOperateHistory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 记录客户释放、领取、流失、分配、领导释放、线程释放(CustomerOperateHistory)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:01
 */
public interface CustomerOperateHistoryMapper extends BaseMapper<CustomerOperateHistory> {

    /**
     * 分页查询[记录客户释放、领取、流失、分配、领导释放、线程释放]列表
     *
     * @param page  分页参数
     * @param param CustomerOperateHistory查询参数
     * @return 分页数据
     */
    IPage<CustomerOperateHistory> getPage(PageParam<CustomerOperateHistory> page, @Param("param") CustomerOperateHistory param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CustomerOperateHistory getInfoById(@Param("id") String id);

}
