package com.center.medical.enterprise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.enterprise.bean.model.Report;
import com.center.medical.enterprise.bean.param.OrderListDataParam;
import com.center.medical.enterprise.bean.param.ReportListDataParam;
import com.center.medical.enterprise.bean.vo.*;
import com.center.medical.enterprise.common.util.PageParam;

import java.util.List;


/**
 * BG报告主表(MdReport)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
public interface ReportService extends IService<Report> {

    /**
     * 分页查询[BG报告主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ReportListDataVo> getPage(PageParam<ReportListDataVo> page, ReportListDataParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Report getInfoById(String id);

    /**
     * 获取导出数据
     * @param param
     * @return
     */
    List<ReportExportVo> getExportData(ReportListDataParam param);

    /**
     * 职业结论导出
     * @param param
     * @return
     */
    List<ReportExportZyVo> getExportZyData(ReportListDataParam param);

    /**
     * 对比报告数据
     * @param id
     * @return
     */
    List<CompareDataVo> getCompareData(String id);

    /**
     * 获取订单信息
     * @param page
     * @param param
     * @return
     */
    IPage<OrderListDataVo> getOrderListData(PageParam<OrderListDataVo> page, OrderListDataParam param);
}

