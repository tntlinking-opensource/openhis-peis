package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFamilyCardCharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 家庭卡充值记录(MdFamilyCardCharge)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
public interface MdFamilyCardChargeService extends IService<MdFamilyCardCharge> {

    /**
     * 分页查询[家庭卡充值记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFamilyCardCharge> getPage(PageParam<MdFamilyCardCharge> page, MdFamilyCardCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFamilyCardCharge getInfoById(String id);

}

