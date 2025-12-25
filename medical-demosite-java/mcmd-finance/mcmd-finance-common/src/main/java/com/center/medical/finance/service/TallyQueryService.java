package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.PayParam;
import com.center.medical.bean.param.RefundParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.TQPageParam;
import com.center.medical.finance.bean.param.TQSaOrUpParam;
import com.center.medical.finance.bean.param.TallyQueryPayParam;
import com.center.medical.finance.bean.vo.RemitterVo;
import com.center.medical.finance.bean.vo.TQPageVo;

import java.util.List;

/**
 * 记账管理-记账结算(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
public interface TallyQueryService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TQPageVo> getList(PageParam<TQPageVo> page, TQPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 记帐查询数据导出
     *
     * @param param
     * @return
     */
    List<TQPageVo> getExportData(TQPageParam param);

    /**
     * 卡扣款
     *
     * @param param
     * @return
     */
    Boolean saveOrUpdateFee(PayParam param);

    /**
     * 结算-保存
     *
     * @param param
     * @param flag  是否需要重新计算结算记录的总金额：false不需要，true需要
     * @return
     */
    Boolean saOrUp(TQSaOrUpParam param, Boolean flag);

    /**
     * 退款：体检卡、会员卡等
     *
     * @param param 退款参数
     * @return
     */
    Boolean refund(RefundParam param);

    /**
     * 获取汇款单位名单
     * @param page
     * @param key
     * @return
     */
    IPage<RemitterVo> getRemitter(PageParam<RemitterVo> page, String key);

    /**
     * 支付
     * @param param
     * @return
     */
    PayResultDto pay(TallyQueryPayParam param);
}

