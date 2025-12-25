package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.*;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-02-07 11:31:04
 */
public interface ChargeQueryMapper extends BaseMapper<Peispatient> {

    /**
     * 每日客服报表统计
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<CQListVo> getList(PageParam<CQListVo> page, @Param("param") CQListParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 查询导出数据
     *
     * @param param
     * @return
     */
    List<CQListVo> exportData(@Param("param") CQListParam param);

    /**
     * 今日费用结算情况
     *
     * @param page
     * @param param
     * @return
     */
    IPage<BackInfoDataVo> getBackInfoData(PageParam<BackInfoDataVo> page, @Param("param") BackInfoDataParam param);

    /**
     * 每日记账报表统计
     *
     * @param page
     * @param param
     * @return
     */
    IPage<EveryDayJZVo> getEveryDayJZDataList(PageParam<EveryDayJZVo> page, @Param("param") EveryDayJZParam param);

    /**
     * 每日自助机通联明细
     *
     * @param page
     * @param param
     * @return
     */
    IPage<TonglianDataVo> getTonglianDataList(PageParam<TonglianDataVo> page, @Param("param") TongLianDataParam param);

    /**
     * 导出每日自助通联明细
     *
     * @param param
     * @return
     */
    List<TonglianDataVo> exportTongLianData(@Param("param") TongLianDataParam param);

    /**
     * 当日所有检查实收费用统计导出数据
     *
     * @param param
     * @return
     */
    List<SummaryDataVo> getSummaryData(@Param("param") TongLianDataParam param);

    /**
     * 当日所有检查统收的统计
     *
     * @param param
     * @return
     */
    List<EDToTongDto> getEveryDayToTongDataSql(@Param("param") EDToTongParam param);

    /**
     * 查询总金额
     *
     * @param param
     * @return
     */
    BigDecimal getRtotal(@Param("param") EDToTongParam param);

    /**
     * 当日所有检查实收的费用统计
     *
     * @param param
     * @return
     */
    List<ToPayWayDto> getEveryDayToPayWayData(@Param("param") BaseParam param);

    /**
     * 当日所有检查实收数据总数
     *
     * @param param
     * @return
     */
    BigDecimal getEveryDayToPayWaySize(@Param("param") BaseParam param);

    /**
     * 当日团体非统收汇总
     *
     * @param page
     * @param param
     * @return
     */
    IPage<FeiTongPayVo> getFeiTongPayData(PageParam<FeiTongPayVo> page, @Param("param") BaseParam param);

    /**
     * 当日团体非统收汇总sql
     *
     * @param param
     * @return
     */
    BigDecimal getFeiTongPayDataSummarySql(@Param("param") BaseParam param);

    /**
     * 获取数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<CQListDataVo> getListData(PageParam<CQListDataVo> page, @Param("param") CQListDataParam param);

    /**
     * 导出(疫苗费用)
     *
     * @param param
     * @return
     */
    List<ExportVaccineVo> exportVaccine(@Param("param") ExportVaccineParam param);


    /**
     * 疫苗实收费用统计数据
     *
     * @param param
     * @return
     */
    List<ExportVaccineVo> getVaccineSql(@Param("param") ExportVaccineParam param);

    /**
     * 导出(疫苗名单)
     *
     * @param param
     * @return
     */
    List<ExportVaccineNameVo> exportVaccineName(@Param("param") ExportVaccineParam param);

    /**
     * 导出(金蝶名单)
     *
     * @param param
     * @return
     */
    List<ExportKingdeeNameVo> exportKingdeeName(@Param("param") ExportVaccineParam param);

    /**
     * 导出Excel(记账报表)
     *
     * @param param
     * @return
     */
    List<ExportEveryDayVo> exportEveryDay(@Param("param") ExportEveryDayParam param);


    /**
     * 获取结算金额
     *
     * @param param
     * @return
     */
    List<JzjsSqlDto> getJzjsSql(@Param("param") CQListParam param);

    /**
     * 查询记账sql
     *
     * @param param
     * @return
     */
    List<JzSqlDto> getJzSql(@Param("param") CQListParam param);

    /**
     * 收费日报-结算明细导出
     *
     * @param param
     * @return
     */
    List<EveryDayBillingDataDto> getEveryDayBillingData(@Param("param") CQListParam param);

    /**
     * 当日团体非统收汇总sql
     *
     * @param param
     * @return
     */
    List<FeiTongPayDataDto> getFeiTongPayDataSql(@Param("param") CQListParam param);

    /**
     * 获取通联支付总金额
     * @param param
     * @return
     */
    String getTongLianLimit(@Param("param") TongLianDataParam param);

    /**
     * 每日疫苗收费统计
     * @param page
     * @param param
     * @return
     */
    IPage<GetVaccinumVo> getVaccinum(PageParam<GetVaccinumVo> page, @Param("param") GetVaccinumParam param);

    /**
     * 导出每日疫苗收费统计
     * @param param
     * @return
     */
    List<GetVaccinumVo> exportVaccinum( @Param("param") GetVaccinumParam param);
}
