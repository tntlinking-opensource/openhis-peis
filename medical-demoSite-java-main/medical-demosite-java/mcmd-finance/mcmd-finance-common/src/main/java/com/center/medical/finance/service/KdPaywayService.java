package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.AccountNameDto;
import com.center.medical.finance.bean.model.KdPayway;
import com.center.medical.finance.bean.vo.KingdeeReserWayVo;

import java.util.List;

/**
 * kingdeepayway(KdPayway)表服务接口
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
public interface KdPaywayService extends IService<KdPayway> {

    /**
     * 分页查询[kingdeepayway]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<KdPayway> getPage(PageParam<KdPayway> page, KdPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    KdPayway getInfoById(String id);

    /**
     * 获取金蝶支付手段
     *
     * @param page
     * @param key
     * @return
     */
    PageParam<KdPayway> getKingdeeData(PageParam<KdPayway> page, String key);

    /**
     * 获取银行结算方式列表
     *
     * @param page
     * @param key
     * @return
     */
    IPage<KingdeeReserWayVo> getKingdeeReserWay(PageParam<KingdeeReserWayVo> page, String key);

    /**
     * 收费方式-金蝶数据更新
     */
    void upgradeKingdeePayWay();

    /**
     * 获取账户名称
     * @param way
     * @return
     */
    List<AccountNameDto> getAccountName(String way);
}

