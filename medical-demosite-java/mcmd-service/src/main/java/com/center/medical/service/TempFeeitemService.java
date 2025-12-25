package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.dto.PayResultDto;
import com.center.medical.bean.model.TempFeeitem;
import com.center.medical.bean.param.GetTempFeeitemParam;
import com.center.medical.bean.param.PaymentParam;
import com.center.medical.bean.vo.GetTempFeeitemVo;
import com.center.medical.common.utils.page.PageParam;

/**
 * 科室临时加项表(TempFeeitem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:45
 */
public interface TempFeeitemService extends IService<TempFeeitem> {

    /**
     * 分页查询[科室临时加项表]列表
     *
     * @param page  分页参数
     * @param param TempFeeitem查询参数
     * @return 分页数据
     */
    IPage<TempFeeitem> getList(PageParam<TempFeeitem> page, TempFeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TempFeeitem getInfoById(String id);

    /**
     * 查询科室加项数据
     * @param param
     * @return
     */
    GetTempFeeitemVo getTempFeeitem(GetTempFeeitemParam param);

    /**
     * 付款
     * @param param
     * @return
     */
    PayResultDto payment(PaymentParam param);

    /**
     * 支付完调用
     * @param param
     * @return
     */
    Boolean callBack(PaymentParam param);
}

