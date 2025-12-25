package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.CustomerTransfer;
import com.center.medical.system.bean.param.XsryDataParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。(CustomerTransfer)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:50
 */
public interface CustomerTransferMapper extends BaseMapper<CustomerTransfer> {

    /**
     * 分页查询[客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。]列表
     *
     * @param page  分页参数
     * @param param CustomerTransfer查询参数
     * @return 分页数据
     */
    IPage<CustomerTransfer> getPage(PageParam<CustomerTransfer> page, @Param("param") XsryDataParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CustomerTransfer getInfoById(@Param("id") String id);

}
