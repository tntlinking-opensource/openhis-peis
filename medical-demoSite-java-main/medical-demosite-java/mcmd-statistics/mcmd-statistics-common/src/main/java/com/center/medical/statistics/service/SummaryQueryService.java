package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ComponyQueryParam;
import com.center.medical.statistics.bean.param.SQPageParam;
import com.center.medical.statistics.bean.param.SummaryDataParam;
import com.center.medical.statistics.bean.vo.ComponyQueryVo;
import com.center.medical.statistics.bean.vo.HarmQueryVo;
import com.center.medical.statistics.bean.vo.SQPageVo;
import com.center.medical.statistics.bean.vo.SummaryDataVo;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface SummaryQueryService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SQPageVo> getList(PageParam<SQPageVo> page, SQPageParam param);



    /**
     * 导出本次职业健康检查拒检补检人员
     * @param param
     * @return
     */
    List<SQPageVo> getExportData(SQPageParam param);

    /**
     * 分页职业健康检查职业禁忌证人员名单
     * @param page
     * @param param
     * @return
     */
    IPage<SummaryDataVo> getSummaryData(PageParam<SummaryDataVo> page, SummaryDataParam param);

    /**
     * 导出禁忌症
     * @param param
     * @return
     */
    List<SummaryDataVo> exportJjz(SummaryDataParam param);

    /**
     * 分页查询职业健康检查结果汇总表 (按单位)
     * @param page
     * @param param
     * @return
     */
    IPage<ComponyQueryVo> getComponyQuery(PageParam<ComponyQueryVo> page, ComponyQueryParam param);

    /**
     * 导出单位职业健康检查结果附表（按用人单位统计）
     * @param param
     * @return
     */
    List<ComponyQueryVo> exportComponyQuery(ComponyQueryParam param);

    /**
     * 查询职业健康检查结果汇总表 (按危害因素)
     * @param param
     * @return
     */
    List<HarmQueryVo> getHarmQuery(ComponyQueryParam param);

}

