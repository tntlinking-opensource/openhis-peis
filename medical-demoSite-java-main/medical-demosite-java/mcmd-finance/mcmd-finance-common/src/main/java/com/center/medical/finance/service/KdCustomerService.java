package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdCustomer;
import com.center.medical.finance.bean.vo.KdCustomerVo;

/**
 * 金碟账户(KdCustomer)表服务接口
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:42
 */
public interface KdCustomerService extends IService<KdCustomer> {

    /**
     * 分页查询[金碟账户]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KdCustomer> getPage(PageParam<KdCustomer> page, KdCustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    KdCustomer getInfoById(String id);

    /**
     * 客户升级时调用,获取金蝶系统客户
     * @return
     */
    String upgradeIdentityByKingdee();

    /**
     * 金蝶客户数据更新
     */
    void upgradeCustomer(String branchId);

    /**
     * 获取金蝶客户
     * @param page
     * @param key
     * @return
     */
    IPage<KdCustomerVo> getKingdeeCustomerData(PageParam<KdCustomerVo> page, String key);
}

