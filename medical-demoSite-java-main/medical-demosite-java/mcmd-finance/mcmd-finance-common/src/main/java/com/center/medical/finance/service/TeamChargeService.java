package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.*;
import com.center.medical.finance.bean.param.*;
import com.center.medical.finance.bean.vo.TCAddVo;
import com.center.medical.finance.bean.vo.TCExportVo;
import com.center.medical.finance.bean.vo.TCPageVo;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;

import java.util.List;

/**
 * 团体结算(Peisorgreservation)表服务接口
 *
 * @author ay
 * @since 2023-04-03 16:32:35
 */
public interface TeamChargeService extends IService<Peisorgreservation> {

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TCPageVo> getList(PageParam<TCPageVo> page, TCPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param param 主键id
     * @return 详情信息
     */
    TCAddVo getInfoById(TCAddParam param);

    /**
     * 卡扣款
     *
     * @param param
     * @return
     */
    Boolean saveOrUpdateFee(TeamPayParam param);

    /**
     * 退款：体检卡、会员卡等
     *
     * @param param 退款参数
     * @return
     */
    Boolean refund(TeamRefundParam param);

    /**
     * 团体结算保存
     *
     * @param param
     * @return
     */
    Boolean saOrUp(TCSaOrUpParam param, Boolean flag);

    /**
     * 导出团体结算数据
     *
     * @param param
     * @return
     */
    List<TCExportVo> getExportData(TCPageParam param);
}

