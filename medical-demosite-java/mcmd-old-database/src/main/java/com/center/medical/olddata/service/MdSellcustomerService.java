package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdSellcustomer;

import java.util.List;

/**
 * 我的客户表(CrmSellcustomer)服务接口
 *
 * @author ay
 * @since 2023-07-25 21:02:12
 */
public interface MdSellcustomerService extends IService<MdSellcustomer> {

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSellcustomer> getPage(PageParam<MdSellcustomer> page, MdSellcustomer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSellcustomer getInfoById(String id);

    /**
     * 添加或更新
     *
     * @param map
     */
    void saOrUp(MdSellcustomer map);

    /**
     * 批量保存
     * @param mdSellcustomerList
     */
    void saOrUpList(List<MdSellcustomer> mdSellcustomerList);
}

