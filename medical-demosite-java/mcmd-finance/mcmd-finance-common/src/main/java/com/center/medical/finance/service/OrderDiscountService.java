package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.OCPageParam;
import com.center.medical.finance.bean.vo.OCPageVo;
import com.center.medical.sellcrm.bean.model.Createorder;

/**
 * 团检订单折扣(Createorder)表服务接口
 *
 * @author ay
 * @since 2023-04-04 16:53:57
 */
public interface OrderDiscountService extends IService<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OCPageVo> getList(PageParam<OCPageVo> page, OCPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(String id);

}

