package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdRemittance;
import com.center.medical.finance.bean.param.KingdeeRemittanceParam;

/**
 * kingdeeremittance(KdRemittance)表服务接口
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
public interface KdRemittanceService extends IService<KdRemittance> {

    /**
     * 分页查询[kingdeeremittance]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KdRemittance> getPage(PageParam<KdRemittance> page, KdRemittance param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键fid
     * @return 详情信息
     */
    KdRemittance getInfoById(String id);

    /**
     * 银行交易流水更新
     *
     * @param param
     */
    void upgradeKingdeeRemittanceByKingdee(KingdeeRemittanceParam param);
}

