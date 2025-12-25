package com.center.medical.reception.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.ExcelUtil;
import com.center.medical.common.utils.MathUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.*;
import com.center.medical.reception.bean.param.*;
import com.center.medical.reception.bean.vo.*;
import com.center.medical.reception.dao.ChargeQueryMapper;
import com.center.medical.reception.service.ChargeQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-02-07 11:31:04
 */
@Slf4j
@Service("chargeQueryService")
@RequiredArgsConstructor
public class ChargeQueryServiceImpl extends ServiceImpl<ChargeQueryMapper, Peispatient> implements ChargeQueryService {

    private final ChargeQueryMapper chargeQueryMapper;

    /**
     * 每日客服报表统计[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CQListVo> getList(PageParam<CQListVo> page, CQListParam param) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getIdFeecharger())) {
            param.setIdFeecharger(param.getIdFeecharger().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getOrgName())) {
            param.setOrgName(param.getOrgName().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getPatientname())) {
            param.setPatientname(param.getPatientname().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return chargeQueryMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return chargeQueryMapper.getInfoById(id);
    }

    ;


    /**
     * 查询导出数据
     *
     * @param param
     * @return
     */
    @Override
    public void exportData(CQListParam param) {
        // 开始时间跟结束时间不能为空,并且不能超过6个月
        if (ObjectUtils.isEmpty(param.getStartTime()) || ObjectUtils.isEmpty(param.getEndTime())) {
            throw new ServiceException("开始时间或结束时间不能为空!");
        }
        long mouth = DateUtil.betweenMonth(param.getStartTime(), param.getEndTime(), true);
        if (mouth > 6) {
            throw new ServiceException("导出时间不能超过6个月!");
        }

        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getIdFeecharger())) {
            param.setIdFeecharger(param.getIdFeecharger().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getOrgName())) {
            param.setOrgName(param.getOrgName().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getPatientname())) {
            param.setPatientname(param.getPatientname().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //每日客服报表统计 明细
        List<CQListVo> list = chargeQueryMapper.exportData(param);
        //当日所有检查实收费用统计
        List<ToPayWayDto> payWayList = chargeQueryMapper.getEveryDayToPayWayData(param);
        //eTongs
        EDToTongParam param1 = new EDToTongParam(param.getOrgName());
        param1.setStartTime(param.getStartTime());
        param1.setEndTime(param.getEndTime());
        //统收的统计
        List<EDToTongDto> tongList = chargeQueryMapper.getEveryDayToTongDataSql(param1);


        ArrayList<ArrayList> tableData = new ArrayList<ArrayList>();
        String[] ptCode = new String[list.size()];
        for (int i = 0, l = list.size(); i < l; i++) {
            ArrayList rowList = new ArrayList();
            CQListVo obj = list.get(i);
            rowList.add(i + 1);//序号
            rowList.add(obj.getIntId());
            rowList.add(obj.getOrgName());// 团体名称
            rowList.add(obj.getPatientcode());// 体检号
            ptCode[i] = obj.getPatientcode();
            String[] tj = {"健康体检", "职业体检", "综合", "复查"};
            rowList.add(obj.getPatientname());// 体检者
            rowList.add(obj.getIdPayway());// 支付方式
            rowList.add(obj.getTjk());// 卡号
            rowList.add(obj.getMoneyamountpaid());// 实收
            rowList.add(obj.getIdFeecharger());// 收费人
            rowList.add(obj.getFeechargetime());// 收费日期
            rowList.add(ObjectUtils.isNotEmpty(obj.getIdExamtype()) ? tj[Integer.valueOf(obj.getIdExamtype())] : "");// 体检类型
            rowList.add(obj.getExamsuiteName());// 套餐名称
            rowList.add("补检".equals(obj.getDoctorapply()) ? obj.getXsjl() : obj.getDoctorapply());// 开单医生，当是补检时取订单的销售经理
            rowList.add(obj.getTimingstartedat());
            tableData.add(rowList);
        }
        // 实收费用
        ArrayList<ArrayList> tablePayWayData = new ArrayList<ArrayList>();
        if (payWayList.size() > 0) {
//        	ArrayList jlal=null;
            for (int i = 0, l = payWayList.size(); i < l; i++) {
                ArrayList rowList = new ArrayList();
                ToPayWayDto obj = payWayList.get(i);
//                if("记账".equals(obj[0])){
                rowList.add(i + 1);
//                }else{
//                	rowList.add(k+1);
//                	k++;
//                }
                rowList.add(obj.getPaywayName());// 支付方式
                double org = obj.getOrg() == null ? 0 : Double.parseDouble(obj.getOrg().toString());
                double per = obj.getPers() == null ? 0 : Double.parseDouble(obj.getPers().toString());
                double orgjs = obj.getOrgjs() == null ? 0 : Double.parseDouble(obj.getOrgjs().toString());
                double perjs = obj.getPerjs() == null ? 0 : Double.parseDouble(obj.getPerjs().toString());
                double yimiao = obj.getYimiao() == null ? 0 : Double.parseDouble(obj.getYimiao().toString());
                // TODO: 2024/5/31 去除结算金额
//                if ("记账".equals((String) obj.getPaywayName())) {
//                    List<JzjsSqlDto> jzjsl = chargeQueryMapper.getJzjsSql(param);
//                    JzjsSqlDto os = jzjsl.get(0);
//                    double perjzjs = os.getGr();
//                    double orgjzjs = os.getTt();
//                    per = per - perjzjs;
//                    org = org - orgjzjs;
//                }
                rowList.add(perjs); // 个检记账结算
                rowList.add(orgjs); // 团检记账结算
                rowList.add(per - yimiao); // 个人费用
                rowList.add(org); // 团体费用
                rowList.add(yimiao); // 疫苗
                rowList.add(org + per + orgjs + perjs); // 合计
//                if("记账".equals(obj[0])){
//                	jlal=rowList;
//                }else{
                tablePayWayData.add(rowList);
//                }
            }
//        	if(jlal!=null){
//        		tablePayWayData.add(jlal);
//        	}
        } else {
            ArrayList rowList = new ArrayList();
            rowList.add("无");// 支付方式
            rowList.add("0"); // 团体费用
            rowList.add("0"); // 个人费用
            rowList.add("0");// 合计
            tablePayWayData.add(rowList);
        }

        // 检查统收
        ArrayList<ArrayList> tableTongData = new ArrayList<ArrayList>();
        Double total = 0d;
        for (int i = 0, l = tongList.size(); i < l; i++) {
            ArrayList rowList = new ArrayList();
            EDToTongDto obj = tongList.get(i);
            rowList.add(i + 1);//序号
            rowList.add(obj.getIntId()); // 单位ID
            rowList.add(obj.getOrgName());// 体检团体
            rowList.add(obj.getPaid()); // 费用
            rowList.add(obj.getName());//客户经理名字
            tableTongData.add(rowList);
            total += Double.valueOf(obj.getPaid().toString());
        }
        ArrayList rowList1 = new ArrayList();
        rowList1.add("总计");//序号
        rowList1.add("");
        rowList1.add("");
        rowList1.add(total); // 费用
        tableTongData.add(rowList1);
        //记账信息
        List<JzSqlDto> jzList = chargeQueryMapper.getJzSql(param);
        ArrayList<ArrayList> jzData = new ArrayList<ArrayList>();
        double jzhj = 0;
        for (int i = 0, l = jzList.size(); i < l; i++) {
            ArrayList rowList = new ArrayList();
            JzSqlDto obj = jzList.get(i);
            rowList.add(i + 1);//序号
            rowList.add(obj.getJzdwr());
            rowList.add(obj.getJzdw());
            rowList.add(obj.getNote());
            rowList.add(obj.getPatientcode());
            rowList.add(obj.getPatientname());
            rowList.add(obj.getJzje());
            jzData.add(rowList);
            if (obj.getJzje() != null) {
                jzhj += Double.parseDouble(obj.getJzje().toString());
            }
        }
        ArrayList jzl = new ArrayList();
        jzl.add("");//序号
        jzl.add("合计");
        jzl.add("");
        jzl.add("");
        jzl.add("");
        jzl.add("");
        jzl.add(jzhj);
        jzData.add(jzl);


        // 结算信息
        List<EveryDayBillingDataDto> jsList = chargeQueryMapper.getEveryDayBillingData(param);
        ArrayList<ArrayList> jsData = new ArrayList<ArrayList>();
        Double jsTotal = 0d;
        for (int i = 0, l = jsList.size(); i < l; i++) {
            ArrayList rowList = new ArrayList();
            EveryDayBillingDataDto obj = jsList.get(i);
            rowList.add(i + 1);//序号
            rowList.add(obj.getPatientname()); // 体检者
            rowList.add(obj.getPatientcode());// 体检号
            rowList.add(obj.getPaywayName()); // 结算方式
            rowList.add(obj.getMoneyamountpaid()); // 结算费用
            rowList.add(obj.getMoneyamountpaiddate()); // 结算日期
            jsData.add(rowList);
            jsTotal += Double.valueOf(obj.getMoneyamountpaid().toString());
        }
        ArrayList rowList2 = new ArrayList();
        rowList2.add("");
        rowList2.add("");
        rowList2.add("");
        rowList2.add("总计");
        rowList2.add(jsTotal);
        rowList2.add("");
        jsData.add(rowList2);
        //表格脚部分
        ArrayList<String> footData = new ArrayList<String>();
        String dcr = SecurityUtils.getUsername();
        Date dcrq = new Date();
        footData.add("导出人： " + ExcelUtil.toString(dcr));
        footData.add("导出日期： " + ExcelUtil.toString(dcrq));
        //表格标题
        String[] tableCaption = {"序号", "团体ID", "团体名称", "体检号", "体检者", "收费方式", "卡号", "实收", "收费人", "收费日期", "体检类型", "套餐名称", "开单医师", "体检号生成时间"};
        String[] tablePayCaption = {"序号", "收费方式", "个检记账结算", "团检记账结算", "个检", "团检", "疫苗", "合计"};
        String[] tableTongCaption = {"序号", "团体ID", "体检团体", "汇总", "销售经理"};
        String[] jzCaption = {"序号", "记账人", "记账单位", "备注", "体检号", "体检者", "记账金额"};
        String[] jsCaption = {"序号", "体检号", "体检者", "结算方式", "结算费用", "结算日期"};
        ArrayList<String[]> tableCaptions = new ArrayList<String[]>();
        tableCaptions.add(tableCaption);
        tableCaptions.add(tablePayCaption);
        tableCaptions.add(jzCaption);
        tableCaptions.add(jsCaption);
        tableCaptions.add(tableTongCaption);
        tableCaptions.add(new String[]{"序号", "团体ID", "单位名称", "金额", "销售经理", "收费日期"});
        // sheet页标题
        String[] sheetTitles = {"每日客服报表统计"};
        Calendar calendar = Calendar.getInstance();
        String monthAndDay = (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        String[] titles = {monthAndDay + "号每日客服报表统计", "当日所有检查实收费用统计", "当日记账明细", "当日记账结算明细", "统收的统计", "当日团体非统收汇总"};
        ArrayList[] myList = new ArrayList[6];
        myList[0] = tableData;
        myList[1] = tablePayWayData;
        myList[2] = jzData;
        myList[3] = jsData;
        myList[4] = tableTongData;
        myList[5] = getFeiTongPayExportData(param);
        int[] rows = new int[6];
        rows[0] = 0;
        rows[1] = 1;
        rows[2] = 1;
        rows[3] = 1;
        rows[4] = 1;
        rows[5] = 1;
        int[] columnWidth = {1500, 2500, 6000, 3800, 2500, 2500, 2500, 1800, 6000, 4000, 6000};
        ExcelUtil.exportMore("每日客服报表统计", ExcelUtil.SheetType.Single, sheetTitles, titles, myList, tableCaptions, rows, footData, columnWidth);
    }


    public ArrayList<ArrayList> getFeiTongPayExportData(CQListParam param) {
        ArrayList<ArrayList> tableData = new ArrayList<ArrayList>();
        List<FeiTongPayDataDto> data = chargeQueryMapper.getFeiTongPayDataSql(param);
        for (int i = 0, s = data.size(); i < s; i++) {
            FeiTongPayDataDto os = data.get(i);
            ArrayList list = new ArrayList<String>();
            list.add(i + 1);
            list.add(os.getIntId());
            list.add(os.getKhdwmc());
            list.add(os.getMoneyamountpaid());
            list.add(os.getUsername());
            list.add(os.getFeechargetime());
            tableData.add(list);
        }
        return tableData;
    }

    /**
     * 今日费用结算情况
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<BackInfoDataVo> getBackInfoData(PageParam<BackInfoDataVo> page, BackInfoDataParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return chargeQueryMapper.getBackInfoData(page, param);
    }


    public static Object[] convertEntityToArray(Object entity) {
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Object> objectList = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                objectList.add(value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return objectList.toArray();
    }


    /**
     * 每日记账报表统计
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<EveryDayJZVo> getEveryDayJZDataList(PageParam<EveryDayJZVo> page, EveryDayJZParam param) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getInputCode())) {
            param.setInputCode(param.getInputCode().trim().toUpperCase());
        }
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return chargeQueryMapper.getEveryDayJZDataList(page, param);
    }


    /**
     * 每日自助机通联明细
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<TonglianDataVo> getTonglianDataList(PageParam<TonglianDataVo> page, TongLianDataParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return chargeQueryMapper.getTonglianDataList(page, param);
    }

    /**
     * 导出每日自助通联明细
     *
     * @param param
     * @return
     */
    @Override
    public List<TonglianDataVo> exportTongLianData(TongLianDataParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        List<TonglianDataVo> list = chargeQueryMapper.exportTongLianData(param);
        for (int i = 0; i < list.size(); i++) {
            TonglianDataVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        return list;
    }


    /**
     * 当日所有检查实收费用统计导出数据
     *
     * @param param
     * @return
     */
    @Override
    public List<SummaryDataVo> getSummaryData(TongLianDataParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        List<SummaryDataVo> list = chargeQueryMapper.getSummaryData(param);
        for (int i = 0; i < list.size(); i++) {
            SummaryDataVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        return list;
    }


    /**
     * 当日所有检查统收的统计
     *
     * @param param
     * @return
     */
    @Override
    public EDToTongVo getEveryDayToTongDataSql(EDToTongParam param) {
        //返回对象
        EDToTongVo vo = new EDToTongVo();
        //查询集合数据
        List<EDToTongDto> list = chargeQueryMapper.getEveryDayToTongDataSql(param);
        vo.setData(list);
        //查询总金额
        BigDecimal b = chargeQueryMapper.getRtotal(param);
        vo.setRtotal(ObjectUtils.isNotEmpty(b) ? b : new BigDecimal(0));
        return vo;
    }


    /**
     * 当日所有检查实收的费用统计
     *
     * @param param
     * @return
     */
    @Override
    public ToPayWayVo getEveryDayToPayWayData(BaseParam param) {
        ToPayWayVo vo = new ToPayWayVo();
        //当日所有检查实收数据
        List<ToPayWayDto> list = chargeQueryMapper.getEveryDayToPayWayData(param);
        for (ToPayWayDto dto : list) {
            dto.setPer(MathUtil.sub(dto.getPers(), dto.getYimiao()));
            dto.setTotal(MathUtil.add(MathUtil.add(MathUtil.add(dto.getOrg(), dto.getPers()), dto.getOrgjs()), dto.getPerjs()));
        }
        vo.setData(list);
        //当日所有检查实收数据总数
        BigDecimal size = chargeQueryMapper.getEveryDayToPayWaySize(param);
        vo.setRtotal(size);
        return vo;
    }


    /**
     * 当日团体非统收汇总
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<FeiTongPayVo> getFeiTongPayData(PageParam<FeiTongPayVo> page, BaseParam param) {
        IPage<FeiTongPayVo> iPage = chargeQueryMapper.getFeiTongPayData(page, param);
        return iPage;
    }

    /**
     * 当日团体非统收汇总总金额
     *
     * @param param
     * @return
     */
    @Override
    public BigDecimal getFeiTongPayDataSummarySql(BaseParam param) {
        //当日团体非统收汇总sql
        BigDecimal bigDecimal = chargeQueryMapper.getFeiTongPayDataSummarySql(param);
        return bigDecimal;
    }


    /**
     * 获取数据
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<CQListDataVo> getListData(PageParam<CQListDataVo> page, CQListDataParam param) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getIdFeecharger())) {
            param.setIdFeecharger(param.getIdFeecharger().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getOrgName())) {
            param.setOrgName(param.getOrgName().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getPatientname())) {
            param.setPatientname(param.getPatientname().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        return chargeQueryMapper.getListData(page, param);
    }


    /**
     * 导出(疫苗费用)
     *
     * @param param
     * @return
     */
    @Override
    public List<ExportVaccineVo> exportVaccine(ExportVaccineParam param) {
        // TODO: wait 导出多sheet
        //排除疫苗的个检 限制只有几种收费方式
        Double totalMoneyamountpaid = 0.0;
        param.setFUsecodehiden(0);
        List<ExportVaccineVo> list = chargeQueryMapper.exportVaccine(param);
        for (int i = 0; i < list.size(); i++) {
            //序号
            ExportVaccineVo vo = list.get(i);
            vo.setRownum(i + 1);
            //实收相加
            totalMoneyamountpaid += vo.getMoneyamountpaid();
        }
        ExportVaccineVo totalVo = new ExportVaccineVo("当日合计", totalMoneyamountpaid);
        list.add(totalVo);
        //排除个检的团检数据
        Double totalExcludeIndividual = 0.0;
        param.setFUsecodehiden(1);
        List<ExportVaccineVo> excludeIndividual = chargeQueryMapper.exportVaccine(param);
        for (int i = 0; i < excludeIndividual.size(); i++) {
            //序号
            ExportVaccineVo vo = excludeIndividual.get(i);
            vo.setRownum(i + 1);
            //实收相加
            totalExcludeIndividual += vo.getMoneyamountpaid();
        }
        ExportVaccineVo totalVo2 = new ExportVaccineVo("当日合计", totalExcludeIndividual);
        excludeIndividual.add(totalVo2);

        //疫苗实收费用统计数据
        Double totalVaccine = 0.0;
        List<ExportVaccineVo> vaccine = chargeQueryMapper.getVaccineSql(param);
        for (int i = 0; i < vaccine.size(); i++) {
            //序号
            ExportVaccineVo vo = vaccine.get(i);
            vo.setRownum(i + 1);
            //实收相加
            totalVaccine += vo.getMoneyamountpaid();
        }
        ExportVaccineVo totalVo3 = new ExportVaccineVo("当日合计", totalExcludeIndividual);
        excludeIndividual.add(totalVo3);
        return list;
    }


    /**
     * 导出(疫苗名单)
     *
     * @param param
     * @return
     */
    @Override
    public List<ExportVaccineNameVo> exportVaccineName(ExportVaccineParam param) {
        List<ExportVaccineNameVo> list = chargeQueryMapper.exportVaccineName(param);
        for (int i = 0; i < list.size(); i++) {
            ExportVaccineNameVo vo = list.get(i);
            //序号
            vo.setRownum(i + 1);
        }
        return list;
    }


    /**
     * 导出(金蝶名单)
     *
     * @param param
     * @return
     */
    @Override
    public List<ExportKingdeeNameVo> exportKingdeeName(ExportVaccineParam param) {
        param.setBranchId(SecurityUtils.getCId());
        List<ExportKingdeeNameVo> list = chargeQueryMapper.exportKingdeeName(param);
        for (int i = 0; i < list.size(); i++) {
            ExportKingdeeNameVo vo = list.get(i);
            //序号
            vo.setRownum(i + 1);
        }
        return list;
    }


    /**
     * 导出Excel(记账报表)
     *
     * @param param
     * @return
     */
    @Override
    public List<ExportEveryDayVo> exportEveryDay(ExportEveryDayParam param) {
        List<ExportEveryDayVo> list = chargeQueryMapper.exportEveryDay(param);
        for (int i = 0; i < list.size(); i++) {
            ExportEveryDayVo vo = list.get(i);
            //序号
            vo.setRownum(i + 1);
        }
        return list;
    }

    /**
     * 获取通联支付总金额
     * @param param
     * @return
     */
    @Override
    public String getTongLianLimit(TongLianDataParam param) {
        return chargeQueryMapper.getTongLianLimit(param);
    }

    /**
     * 每日疫苗收费统计
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<GetVaccinumVo> getVaccinum(PageParam<GetVaccinumVo> page, GetVaccinumParam param) {
        return chargeQueryMapper.getVaccinum(page,param);
    }

    /**
     * 导出每日疫苗收费统计
     * @param param
     * @return
     */
    @Override
    public List<GetVaccinumVo> exportVaccinum(GetVaccinumParam param) {
        List<GetVaccinumVo> list = chargeQueryMapper.exportVaccinum(param);
        for (int i = 0; i < list.size(); i++) {
            GetVaccinumVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        return list;
    }
}

