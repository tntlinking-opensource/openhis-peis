package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.CustomerOperateHistory;
import com.center.medical.sellcrm.bean.param.CustomerOperateHistoryParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放(CustomerOperateHistory)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:24
 */
public interface CustomerOperateHistoryMapper extends BaseMapper<CustomerOperateHistory> {

    /**
     * 分页查询[客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放]列表
     *
     * @param page  分页参数
     * @param customerOperateHistoryParam CustomerOperateHistory查询参数
     * @return 分页数据
     */
    IPage<CustomerOperateHistory> getList(PageParam<CustomerOperateHistory> page, @Param("param") CustomerOperateHistoryParam customerOperateHistoryParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CustomerOperateHistory getInfoById(@Param("id") String id);

}
