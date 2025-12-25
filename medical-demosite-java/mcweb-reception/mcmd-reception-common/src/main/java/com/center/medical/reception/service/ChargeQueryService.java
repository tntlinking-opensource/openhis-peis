package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-02-07 11:31:04
 */
public interface ChargeQueryService extends IService<Peispatient> {

    /**
     * 每日客服报表统计[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CQListVo> getList(PageParam<CQListVo> page, CQListParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 查询导出数据
     *
     * @param param
     * @return
     */
    void exportData(CQListParam param);

    /**
     * 今日费用结算情况
     *
     * @param page
     * @param param
     * @return
     */
    IPage<BackInfoDataVo> getBackInfoData(PageParam<BackInfoDataVo> page, BackInfoDataParam param);

    /**
     * 每日记账报表统计
     *
     * @param page
     * @param param
     * @return
     */
    IPage<EveryDayJZVo> getEveryDayJZDataList(PageParam<EveryDayJZVo> page, EveryDayJZParam param);

    /**
     * 每日自助机通联明细
     *
     * @param page
     * @param param
     * @return
     */
    IPage<TonglianDataVo> getTonglianDataList(PageParam<TonglianDataVo> page, TongLianDataParam param);

    /**
     * 导出每日自助通联明细
     *
     * @param param
     * @return
     */
    List<TonglianDataVo> exportTongLianData(TongLianDataParam param);

    /**
     * 当日所有检查实收费用统计导出数据
     *
     * @param param
     * @return
     */
    List<SummaryDataVo> getSummaryData(TongLianDataParam param);

    /**
     * 当日所有检查统收的统计
     *
     * @param param
     * @return
     */
    EDToTongVo getEveryDayToTongDataSql(EDToTongParam param);

    /**
     * 当日所有检查实收的费用统计
     *
     * @param param
     * @return
     */
    ToPayWayVo getEveryDayToPayWayData(BaseParam param);

    /**
     * 当日团体非统收汇总
     *
     * @param page
     * @param param
     * @return
     */
    IPage<FeiTongPayVo> getFeiTongPayData(PageParam<FeiTongPayVo> page, BaseParam param);

    /**
     * 当日团体非统收汇总总金额
     *
     * @param param
     * @return
     */
    BigDecimal getFeiTongPayDataSummarySql(BaseParam param);

    /**
     * 获取数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<CQListDataVo> getListData(PageParam<CQListDataVo> page, CQListDataParam param);

    /**
     * 导出(疫苗费用)
     *
     * @param param
     * @return
     */
    List<ExportVaccineVo> exportVaccine(ExportVaccineParam param);

    /**
     * 导出(疫苗名单)
     *
     * @param param
     * @return
     */
    List<ExportVaccineNameVo> exportVaccineName(ExportVaccineParam param);

    /**
     * 导出(金蝶名单)
     *
     * @param param
     * @return
     */
    List<ExportKingdeeNameVo> exportKingdeeName(ExportVaccineParam param);

    /**
     * 导出Excel(记账报表)
     *
     * @param param
     * @return
     */
    List<ExportEveryDayVo> exportEveryDay(ExportEveryDayParam param);

    /**
     * 获取通联支付总金额
     * @param param
     * @return
     */
    String getTongLianLimit(TongLianDataParam param);

    /**
     * 每日疫苗收费统计
     * @param page
     * @param param
     * @return
     */
    IPage<GetVaccinumVo> getVaccinum(PageParam<GetVaccinumVo> page, GetVaccinumParam param);

    /**
     * 导出每日疫苗收费统计
     * @param param
     * @return
     */
    List<GetVaccinumVo> exportVaccinum(GetVaccinumParam param);
}

