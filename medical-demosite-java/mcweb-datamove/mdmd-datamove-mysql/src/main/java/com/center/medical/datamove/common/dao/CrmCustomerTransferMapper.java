package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.CrmCustomerTransfer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。(CrmCustomerTransfer)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:44:58
 */
public interface CrmCustomerTransferMapper extends BaseMapper<CrmCustomerTransfer> {

    /**
     * 分页查询[客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。]列表
     *
     * @param page  分页参数
     * @param param CrmCustomerTransfer查询参数
     * @return 分页数据
     */
    IPage<CrmCustomerTransfer> getPage(PageParam<CrmCustomerTransfer> page, @Param("param") CrmCustomerTransfer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CrmCustomerTransfer getInfoById(@Param("id") String id);

}
