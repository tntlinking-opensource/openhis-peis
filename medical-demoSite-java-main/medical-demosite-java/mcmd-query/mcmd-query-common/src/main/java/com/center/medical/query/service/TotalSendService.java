package com.center.medical.query.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.TotalSendParam;
import com.center.medical.query.bean.vo.TotalSendVo;
import com.center.medical.reception.bean.model.OutsideMain;

import java.util.List;

/**
 * KS外送登记结果主表(OutsideMain)表服务接口
 *
 * @author ay
 * @since 2023-04-12 11:59:12
 */
public interface TotalSendService extends IService<OutsideMain> {

    /**
     * 分页查询[KS外送登记结果主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TotalSendVo> getList(PageParam<TotalSendVo> page, TotalSendParam param);


    /**
     * 获取合计金额
     * @param param
     * @return
     */
    String amountTo(TotalSendParam param);


    /**
     * 导出外送统计
     * @param param
     * @return
     */
    List<TotalSendVo> getExportData(TotalSendParam param);
}

