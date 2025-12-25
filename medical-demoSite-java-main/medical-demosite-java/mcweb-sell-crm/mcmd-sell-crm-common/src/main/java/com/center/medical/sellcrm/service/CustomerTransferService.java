package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.CustomerTransfer;
import com.center.medical.sellcrm.bean.param.SaveTransferParam;
import com.center.medical.system.bean.param.XsryDataParam;
import com.center.medical.system.bean.vo.XsryVo;

/**
 * 客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。(CustomerTransfer)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:50
 */
public interface CustomerTransferService extends IService<CustomerTransfer> {

    /**
     * 分页查询[客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<XsryVo> getPage(PageParam<XsryVo> page, XsryDataParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CustomerTransfer getInfoById(String id);

    /**
     * 客户转移-转移参数
     * @param param
     * @return
     */
    Boolean saveTransfer(SaveTransferParam param);
}

