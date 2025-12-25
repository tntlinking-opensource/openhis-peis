package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.BsSettlement;
import com.center.medical.appadmin.bean.param.BsSettlementParam;
import com.center.medical.appadmin.bean.vo.BSGetTotalVo;
import com.center.medical.appadmin.bean.vo.BsSettlementVo;
import com.center.medical.common.utils.page.PageParam;

/**
 * 业务结算表(BsSettlement)服务接口
 *
 * @author ay
 * @since 2024-06-17 16:01:58
 */
public interface BsSettlementService extends IService<BsSettlement> {

    /**
     * 分页查询[业务结算表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BsSettlementVo> getPage(PageParam<BsSettlementVo> page, BsSettlementParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键settlementId
     * @return 详情信息
     */
    BsSettlement getInfoById(String id);

    /**
     * 获取总额
     * @param param
     * @return
     */
    BSGetTotalVo getTotal(BsSettlementParam param);
}

