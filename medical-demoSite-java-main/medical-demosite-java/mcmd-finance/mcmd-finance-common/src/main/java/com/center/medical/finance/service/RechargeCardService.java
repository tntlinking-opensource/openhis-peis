package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.finance.bean.param.RCSaOrUpParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.param.RechargeCardParam;
import com.center.medical.finance.bean.param.SaOrUpFeeParam;
import com.center.medical.finance.bean.vo.RechargeCardVo;

import java.util.HashMap;
import java.util.List;

/**
 * 体检卡(Card)表服务接口
 *
 * @author ay
 * @since 2023-03-16 11:30:02
 */
public interface RechargeCardService extends IService<Card> {

    /**
    * 分页查询[体检卡]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<RechargeCardVo> getList(PageParam<RechargeCardVo> page, RechargeCardParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Card getInfoById(String id);

    /**
     * 获取体检卡卡相关内容
     * @param cardId
     * @return
     */
    HashMap getCardData(String cardId);

    /**
     * 保存充值
     * @param param
     * @return
     */
    Boolean saOrUp(RCSaOrUpParam param);

    /**
     * 体检卡消费
     * @param param
     * @return
     */
    Boolean saveOrUpdateFee(SaOrUpFeeParam param);

    /**
     * 体检卡消费明细
     * @param param
     * @return
     */
    List<RechargeCardVo> getExportData(RechargeCardParam param);
}

