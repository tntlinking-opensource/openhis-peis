package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.KdCustomer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 金碟账户(KdCustomer)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:03
 */
public interface KdCustomerMapper extends BaseMapper<KdCustomer> {

    /**
     * 分页查询[金碟账户]列表
     *
     * @param page  分页参数
     * @param param KdCustomer查询参数
     * @return 分页数据
     */
    IPage<KdCustomer> getPage(PageParam<KdCustomer> page, @Param("param") KdCustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    KdCustomer getInfoById(@Param("id") String id);

}
