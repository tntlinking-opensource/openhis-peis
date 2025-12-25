package com.center.medical.finance.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BusinessCat;
import com.center.medical.data.dao.BusinessCatMapper;
import com.center.medical.datascreen.constant.DatascreenConstant;
import com.center.medical.datascreen.dao.PlatformDataMapper;
import com.center.medical.finance.bean.dto.DSVOptionDto;
import com.center.medical.finance.bean.dto.ISDataDto;
import com.center.medical.finance.bean.dto.OutputValueDto;
import com.center.medical.finance.bean.param.ISDataParam;
import com.center.medical.finance.bean.vo.IndexSituationVo;
import com.center.medical.finance.dao.DaySalesVolumeMapper;
import com.center.medical.finance.dao.FinancialReportingMapper;
import com.center.medical.finance.dao.IndexSituationMapper;
import com.center.medical.finance.service.IndexSituationService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.system.dao.SysBranchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单表(Createorder)表服务实现类
 *
 * @author ay
 * @since 2023-05-15 09:37:35
 */
@Slf4j
@Service("indexSituationService")
@RequiredArgsConstructor
public class IndexSituationServiceImpl extends ServiceImpl<IndexSituationMapper, Createorder> implements IndexSituationService {

    private final IndexSituationMapper indexSituationMapper;
    private final BusinessCatMapper businessCatMapper;
    private final SysBranchMapper sysBranchMapper;
    private final FinancialReportingMapper financialReportingMapper;
    private final PlatformDataMapper platformDataMapper;
    private final DaySalesVolumeMapper daySalesVolumeMapper;



    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Createorder> getList(PageParam<Createorder> page, Createorder param) {
        return indexSituationMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createorder getInfoById(String id) {
        return indexSituationMapper.getInfoById(id);
    }


    /**
     * 获取统计数据
     * @return
     */
    @Override
    public List<IndexSituationVo> getData(ISDataParam param) {
        Integer type = param.getType();
        //分中心名字集合
        List<SysBranch> sysBranches = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>()
                .eq(SysBranch::getIsShow, 1)
                .orderByAsc(SysBranch::getBranchSort));
        List<String> branchNameList = sysBranches.stream().map(b -> b.getFzx()).collect(Collectors.toList());
        param.setBranchNameList(branchNameList);
        List<IndexSituationVo> voList = new ArrayList<>();

        switch (type) {
            case 1:
                //产值
                IndexSituationVo outputValueDtoList = getOutputValue(param);
                voList.add(outputValueDtoList);
                break;
            case 2:
                //利润情况
                IndexSituationVo profitList = getProfit(param);
                voList.add(profitList);
                break;
            case 3:
                //现金流
                IndexSituationVo cashFlowList = getCashFlow(param);
                voList.add(cashFlowList);
                break;
            case 4:
                //产值分布
                IndexSituationVo distributionList = getDistribution(param);
                voList.add(distributionList);
                break;
            case 5:
                //客户情况
                IndexSituationVo customerList = getCustomer(param);
                voList.add(customerList);
                break;
            case 6:
                //来检人数情况
                IndexSituationVo comingInspectionList = getComingInspection(param);
                voList.add(comingInspectionList);
                break;
            case 7:
                //销售数据情况
                IndexSituationVo salesDataList = getSalesData(param);
                voList.add(salesDataList);
                break;
            case 8:
                //费用占比
                IndexSituationVo costProportion = getCostProportion(param,getOutputValue(param));
                voList.add(costProportion);
                break;
            case 9:
                //人均情况
                IndexSituationVo perCapita = getPerCapita(param,getCostProportion(param,getOutputValue(param)),getOutputValue(param),getProfit(param));
                voList.add(perCapita);
                break;
            default:
                break;
        }

        return voList;
    }

    /**
     * 人均情况
     * @param param
     * @return
     */
    private IndexSituationVo getPerCapita(ISDataParam param,IndexSituationVo costProportion
            ,IndexSituationVo outputValues,IndexSituationVo profitList) {
        IndexSituationVo vo = new IndexSituationVo();
        List<String> nameList = param.getBranchNameList();
        vo.setName(nameList);
        vo.setTitle("人均情况");
        List<OutputValueDto> outputValueDtoList = new ArrayList<>();

        //月平均人数(含兼职)
        List<Double> value1 = getFRDtos("people_number",param.getEndTime(),nameList);
        OutputValueDto outputValueDto1 = new OutputValueDto("月平均人数(含兼职)",null,value1);
        outputValueDtoList.add(outputValueDto1);

        //获取人工成本
        List<Double> laborCosts = costProportion.getList().get(6).getValue();
        //年/人均人工成本
        List<Double> value2 = divisionCalculation(laborCosts,value1);
        OutputValueDto outputValueDto2 = new OutputValueDto("人均人工成本/年",null,value2);
        outputValueDtoList.add(outputValueDto2);

        //产值不含疫苗
        List<Double> outputValue = outputValues.getList().get(2).getValue();
        //人均工作量不含疫苗
        List<Double> value3 = divisionCalculation(outputValue,value1);
        OutputValueDto outputValueDto3 = new OutputValueDto("人均产值（不含疫苗）",null,value3);
        outputValueDtoList.add(outputValueDto3);

        //利润
        List<Double> profit = profitList.getList().get(0).getValue();
        //人均利润
        List<Double> value4 = divisionCalculation(profit,value1);
        OutputValueDto outputValueDto4 = new OutputValueDto("人均利润",null,value4);
        outputValueDtoList.add(outputValueDto4);

        //产值
        List<Double> outputValue2 = outputValues.getList().get(0).getValue();
        //人均产值
        List<Double> value5 = divisionCalculation(outputValue2,value1);
        OutputValueDto outputValueDto5 = new OutputValueDto("人均产值",null,value5);
        outputValueDtoList.add(outputValueDto5);

        //总人数
        List<DSVOptionDto> list1 = daySalesVolumeMapper.findOptionCount(param.getEndTime(),null);
        List<Double> totalCount = completion(list1,nameList);
        //人均工作量
        List<Double> value6 = divisionCalculation(totalCount,value1);
        OutputValueDto outputValueDto6 = new OutputValueDto("人均工作量",null,value6);
        outputValueDtoList.add(outputValueDto6);

        vo.setList(outputValueDtoList);
        return vo;
    }

    /**
     * 除法计算
     * @param value1 被除数
     * @param value2 除数
     * @return
     */
    private List<Double> divisionCalculation(List<Double> value1, List<Double> value2) {
        List<Double> data = new ArrayList<>();
        for (int i = 0; i < value1.size(); i++) {
            Double a = value1.get(i);
            Double b = value2.get(i);
            if (a == 0.0 ||b == 0.0){
                data.add(0.0);
            }else {
                double divide = Arith.div(a, b);
                //保留两位小数
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String formattedNumber = decimalFormat.format(divide);
                double convertedNumber = Double.parseDouble(formattedNumber);
                data.add(convertedNumber);
            }
        }
        return data;
    }

    /**
     * 获取费用占比数据
     * @param param
     * @return
     */
    private IndexSituationVo getCostProportion(ISDataParam param,IndexSituationVo outputValues) {
        IndexSituationVo vo = new IndexSituationVo();
        List<String> nameList = param.getBranchNameList();
        vo.setName(nameList);
        vo.setTitle("费用比占情况");
        int thisYear = DateUtil.year(param.getEndTime());
        List<OutputValueDto> outputValueDtoList = new ArrayList<>();

        //产值今年
        List<Double> outputValue = outputValues.getList().get(0).getValue();
        //产值不含疫苗
        List<Double> outputValue2 = outputValues.getList().get(2).getValue();

        //销售费用
        List<Double> value1 = getFRDtos("costproportion_sale",param.getEndTime(),nameList);
        OutputValueDto outputValueDto1 = new OutputValueDto("销售费用",thisYear,value1);
        outputValueDtoList.add(outputValueDto1);

        //占比 = 今年产值/全年目标
        List<Double> value2 = completionRate(value1, outputValue);
        OutputValueDto outputValueDto2 = new OutputValueDto("占比",null,value2);
        outputValueDtoList.add(outputValueDto2);

        //管理费用
        List<Double> value3 = getFRDtos("costproportion_manage",param.getEndTime(),nameList);
        OutputValueDto outputValueDto3 = new OutputValueDto("管理费用",thisYear,value3);
        outputValueDtoList.add(outputValueDto3);

        //占比
        List<Double> value4 = completionRate(value3, outputValue);
        OutputValueDto outputValueDto4 = new OutputValueDto("占比",null,value4);
        outputValueDtoList.add(outputValueDto4);

        //房租折旧、设备租赁
        List<Double> value5 = getFRDtos("costproportion_housing",param.getEndTime(),nameList);
        OutputValueDto outputValueDto5 = new OutputValueDto("房租折旧、设备租赁",thisYear,value5);
        outputValueDtoList.add(outputValueDto5);

        //占比
        List<Double> value6 = completionRate(value5, outputValue);
        OutputValueDto outputValueDto6 = new OutputValueDto("占比",null,value6);
        outputValueDtoList.add(outputValueDto6);

        //人工成本
        List<Double> value7 = getFRDtos("costproportion_people",param.getEndTime(),nameList);
        OutputValueDto outputValueDto7 = new OutputValueDto("人工成本",thisYear,value7);
        outputValueDtoList.add(outputValueDto7);

        //占比
        List<Double> value8 = completionRate(value7, outputValue);
        OutputValueDto outputValueDto8 = new OutputValueDto("占比",null,value8);
        outputValueDtoList.add(outputValueDto8);

        //提成
        List<Double> value9 = getFRDtos("costproportion_commission",param.getEndTime(),nameList);
        OutputValueDto outputValueDto9 = new OutputValueDto("提成",thisYear,value9);
        outputValueDtoList.add(outputValueDto9);

        //占比
        List<Double> value10 = completionRate(value9, outputValue);
        OutputValueDto outputValueDto10 = new OutputValueDto("占比",null,value10);
        outputValueDtoList.add(outputValueDto10);

        //试剂耗材
        List<Double> value11 = getFRDtos("costproportion_consumables",param.getEndTime(),nameList);
        OutputValueDto outputValueDto11 = new OutputValueDto("试剂耗材",thisYear,value11);
        outputValueDtoList.add(outputValueDto11);

        //占比
        List<Double> value12 = completionRate(value11, outputValue);
        OutputValueDto outputValueDto12 = new OutputValueDto("占比",null,value12);
        outputValueDtoList.add(outputValueDto12);

        //试剂耗材(不含疫苗)
        List<Double> value13 = getFRDtos("costproportion_consumables_no",param.getEndTime(),nameList);
        OutputValueDto outputValueDto13 = new OutputValueDto("试剂耗材(不含疫苗)",thisYear,value13);
        outputValueDtoList.add(outputValueDto13);

        //占比
        List<Double> value14 = completionRate(value13, outputValue2);
        OutputValueDto outputValueDto14 = new OutputValueDto("占比",null,value14);
        outputValueDtoList.add(outputValueDto14);

        vo.setList(outputValueDtoList);
        return vo;
    }

    /**
     * 销售数据情况
     * @param param
     * @return
     */
    private IndexSituationVo getSalesData(ISDataParam param) {
        IndexSituationVo vo = new IndexSituationVo();
        List<String> nameList = param.getBranchNameList();
        vo.setName(nameList);
        vo.setTitle("销售数据情况");
        //获取年份
        int lastYear = DateUtil.year(param.getStartTime());
        int thisYear = DateUtil.year(param.getEndTime());
        List<OutputValueDto> outputValueDtoList = new ArrayList<>();

        //当期应收账款回收率 今年
        List<Double> value1 = getFRDtos("saleoverdue_rate_of_recovery",param.getEndTime(),nameList);
        OutputValueDto outputValueDto1 = new OutputValueDto("当期应收账款回收率",thisYear,value1);
        outputValueDtoList.add(outputValueDto1);

        //当期应收账款回收率 去年
        List<Double> value2 = getFRDtos("saleoverdue_rate_of_recovery",param.getStartTime(),nameList);
        OutputValueDto outputValueDto2 = new OutputValueDto("当期应收账款回收率",lastYear,value2);
        outputValueDtoList.add(outputValueDto2);

        //折扣率今年
        List<Double> value3 = getFRDtos("saleoverdue_discount_rate",param.getEndTime(),nameList);
        OutputValueDto outputValueDto3 = new OutputValueDto("折扣率",thisYear,value3);
        outputValueDtoList.add(outputValueDto3);

        //折扣率去年
        List<Double> value4 = getFRDtos("saleoverdue_discount_rate",param.getStartTime(),nameList);
        OutputValueDto outputValueDto4 = new OutputValueDto("折扣率",lastYear,value4);
        outputValueDtoList.add(outputValueDto4);

        //平均客单价(元) 今年
        List<Double> value5 = unitPrice(nameList,param.getEndTime());
        OutputValueDto outputValueDto5 = new OutputValueDto("平均客单价(元)",thisYear,value5);
        outputValueDtoList.add(outputValueDto5);

        //平均客单价(元) 去年
        List<Double> value6 = unitPrice(nameList,param.getStartTime());
        OutputValueDto outputValueDto6 = new OutputValueDto("平均客单价(元)",lastYear,value6);
        outputValueDtoList.add(outputValueDto6);

        //超期应收账款(万元) 今年
        List<Double> value7 = getFRDtos("saleoverdue_accounts",param.getStartTime(),nameList);
        OutputValueDto outputValueDto7 = new OutputValueDto("超期应收账款(万元)",thisYear,value7);
        outputValueDtoList.add(outputValueDto7);

        //超期应收账款(万元) 去年
        List<Double> value8 = getFRDtos("saleoverdue_accounts",param.getStartTime(),nameList);
        OutputValueDto outputValueDto8 = new OutputValueDto("超期应收账款(万元)",lastYear,value8);
        outputValueDtoList.add(outputValueDto8);


        vo.setList(outputValueDtoList);
        return vo;
    }


    /**
     * 获取平均客单价
     * @param nameList
     * @param time
     * @return
     */
    private List<Double> unitPrice(List<String> nameList, Date time) {
        List<Double> data = new ArrayList<>();
        //总费用
        List<ISDataDto> dto1 = indexSituationMapper.getIncome(time, null);
        List<Double> value1 = dataSort(dto1, nameList);
        //总人数
        List<ISDataDto> dto2 = indexSituationMapper.getPersonCount(time);
        List<Double> value2 = dataSort(dto2, nameList);
        //计算客单价 = 总费用/总人数
        for (int i = 0; i < value1.size(); i++) {
            Double amount = value1.get(i);
            Double number = value2.get(i);
            //总金额为空 或 总人数为空
            if (amount == 0.0 ||number == 0.0){
                data.add(0.0);
            }else {
                double divide = Arith.div(amount, number);
                //保留两位小数
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String formattedNumber = decimalFormat.format(divide);
                double convertedNumber = Double.parseDouble(formattedNumber);
                data.add(convertedNumber);
            }

        }
        return data;
    }

    /**
     * 来检人数情况
     * @param param
     * @return
     */
    private IndexSituationVo getComingInspection(ISDataParam param) {
        IndexSituationVo vo = new IndexSituationVo();
        List<String> nameList = param.getBranchNameList();
        vo.setName(nameList);
        vo.setTitle("来检人数情况");
        //获取年份
        int lastYear = DateUtil.year(param.getStartTime());
        int thisYear = DateUtil.year(param.getEndTime());
        List<OutputValueDto> outputValueDtoList = new ArrayList<>();

        //业务类型
        List<BusinessCat> list = businessCatMapper.selectList(new LambdaQueryWrapper<BusinessCat>()
                .eq(BusinessCat::getStatus, 1).orderByAsc(BusinessCat::getSeq));
        //查询来检人数
        for (BusinessCat businessCat : list) {
            //去年数据
            List<DSVOptionDto> list1 = daySalesVolumeMapper.findOptionCount(param.getEndTime(),String.valueOf(businessCat.getTypeId()));
            List<Double> completionValue1 = completion(list1,nameList);
            OutputValueDto completionDto1 = new OutputValueDto(businessCat.getTypeName(),thisYear,completionValue1);
            outputValueDtoList.add(completionDto1);

            //今年数据
            List<DSVOptionDto> list2 = daySalesVolumeMapper.findOptionCount(param.getStartTime(),String.valueOf(businessCat.getTypeId()));
            List<Double> completionValue2 = completion(list2,nameList);
            OutputValueDto completionDto2 = new OutputValueDto(businessCat.getTypeName(),lastYear,completionValue2);
            outputValueDtoList.add(completionDto2);

        }
        vo.setList(outputValueDtoList);
        return vo;
    }
//
    /**
     * 获取客户情况
     * @param param
     * @return
     */
    private IndexSituationVo getCustomer(ISDataParam param) {
        IndexSituationVo vo = new IndexSituationVo();
        List<String> nameList = param.getBranchNameList();
        vo.setName(nameList);
        vo.setTitle("客户情况");
        List<OutputValueDto> outputValueDtoList = new ArrayList<>();

        //新单金额
        List<Double> value1 = getFRDtos("customer_new",param.getEndTime(),nameList);
        OutputValueDto outputValueDto1 = new OutputValueDto("新单金额",null,value1);
        outputValueDtoList.add(outputValueDto1);

        //丢单金额
        List<Double> value2 = getFRDtos("customer_lose",param.getEndTime(),nameList);
        OutputValueDto outputValueDto2 = new OutputValueDto("丢单金额",null,value2);
        outputValueDtoList.add(outputValueDto2);

        //丢单金额(个检记账)
        List<Double> value3 = getFRDtos("customer_lose_accounting",param.getEndTime(),nameList);
        OutputValueDto outputValueDto3 = new OutputValueDto("丢单金额(个检记账)",null,value3);
        outputValueDtoList.add(outputValueDto3);

        //大单金额(50万以上)
        List<Double> value4 = getFRDtos("customer_big50",param.getEndTime(),nameList);
        OutputValueDto outputValueDto4 = new OutputValueDto("大单金额(50万以上)",null,value4);
        outputValueDtoList.add(outputValueDto4);

        //50万以上大单个数
        List<Double> value5 = getFRDtos("customer_big50_count",param.getEndTime(),nameList);
        OutputValueDto outputValueDto5 = new OutputValueDto("50万以上大单个数",null,value5);
        outputValueDtoList.add(outputValueDto5);

        //大单金额(20万-50万大单个数)
        List<Double> value6 = getFRDtos("customer_big20",param.getEndTime(),nameList);
        OutputValueDto outputValueDto6 = new OutputValueDto("大单金额(20万-50万大单个数)",null,value6);
        outputValueDtoList.add(outputValueDto6);

        //20万-50万大单个数
        List<Double> value7 = getFRDtos("customer_big20_count",param.getEndTime(),nameList);
        OutputValueDto outputValueDto7 = new OutputValueDto("20万-50万大单个数",null,value7);
        outputValueDtoList.add(outputValueDto7);

        //个检记账
        List<Double> value8 = getFRDtos("customer_accounting",param.getEndTime(),nameList);
        OutputValueDto outputValueDto8 = new OutputValueDto("个检记账",null,value8);
        outputValueDtoList.add(outputValueDto8);

        vo.setList(outputValueDtoList);
        return vo;

    }

    /**
     * 产值分布
     * @param param
     * @return
     */
    private IndexSituationVo getDistribution(ISDataParam param) {
        IndexSituationVo vo = new IndexSituationVo();
        List<String> nameList = param.getBranchNameList();
        vo.setName(nameList);
        vo.setTitle("产值分布情况");
        //获取年份
        int lastYear = DateUtil.year(param.getStartTime());
        int thisYear = DateUtil.year(param.getEndTime());

        List<OutputValueDto> outputValueDtoList = new ArrayList<>();

        //团检收入今年
        List<ISDataDto> dto1 = indexSituationMapper.getIncome(param.getEndTime(), DatascreenConstant.GROUP);
        List<Double> value1 = dataSort(dto1, nameList);
        OutputValueDto outputValueDto1 = new OutputValueDto("团检收入",thisYear,value1);
        outputValueDtoList.add(outputValueDto1);

        //团检收入去年
        List<ISDataDto> dto2 = indexSituationMapper.getIncome(param.getStartTime(),DatascreenConstant.GROUP);
        List<Double> value2 = dataSort(dto2, nameList);
        OutputValueDto outputValueDto2 = new OutputValueDto("团检收入",lastYear,value2);
        outputValueDtoList.add(outputValueDto2);

        //个检收入今年
        List<ISDataDto> dto3 = indexSituationMapper.getIncome(param.getEndTime(),DatascreenConstant.PERSON);
        List<Double> value3 = dataSort(dto3, nameList);
        OutputValueDto outputValueDto3 = new OutputValueDto("个检收入",thisYear,value3);
        outputValueDtoList.add(outputValueDto3);

        //个检收入去年
        List<ISDataDto> dto4 = indexSituationMapper.getIncome(param.getStartTime(),DatascreenConstant.PERSON);
        List<Double> value4 = dataSort(dto4, nameList);
        OutputValueDto outputValueDto4 = new OutputValueDto("个检收入",lastYear,value4);
        outputValueDtoList.add(outputValueDto4);

        //对应业务类别的收费金额
        List<BusinessCat> list = businessCatMapper.selectList(new LambdaQueryWrapper<BusinessCat>()
                .eq(BusinessCat::getStatus, 1).orderByAsc(BusinessCat::getSeq));
        for (BusinessCat businessCat : list) {
            //去年数据
            List<DSVOptionDto> list1 = daySalesVolumeMapper.findOption(param.getEndTime(),String.valueOf(businessCat.getTypeId()));
            List<Double> completionValue1 = completion(list1,nameList);
            OutputValueDto completionDto1 = new OutputValueDto(businessCat.getTypeName(),thisYear,completionValue1);
            outputValueDtoList.add(completionDto1);

            //今年数据
            List<DSVOptionDto> list2 = daySalesVolumeMapper.findOption(param.getStartTime(),String.valueOf(businessCat.getTypeId()));
            List<Double> completionValue2 = completion(list2,nameList);
            OutputValueDto completionDto2 = new OutputValueDto(businessCat.getTypeName(),lastYear,completionValue2);
            outputValueDtoList.add(completionDto2);
        }

        vo.setList(outputValueDtoList);
        return vo;
    }

    /**
     * 根据分中心补全对应业务类别
     * @param list
     * @param nameList
     * @return
     */
    private List<Double> completion(List<DSVOptionDto> list, List<String> nameList) {
        //找到了就添加值，每找到就添加0
        List<Double> data = new ArrayList<>();
        for (String name : nameList) {
            boolean found = false;
            for (DSVOptionDto dto : list) {
                if (dto.getFzx().equals(name)) {
                    data.add(dto.getAmount());
                    found = true;
                    break;
                }
            }
            if (!found) {
                data.add(0d);
            }
        }
        return data;
    }

    /**
     * 获取现金流情况
     * @param param
     * @return
     */
    private IndexSituationVo getCashFlow(ISDataParam param) {
        IndexSituationVo vo = new IndexSituationVo();
        List<String> nameList = param.getBranchNameList();
        vo.setName(nameList);
        vo.setTitle("现金流情况");
        List<OutputValueDto> outputValueDtoList = new ArrayList<>();
        DateTime now = DateUtil.beginOfMonth(new Date());

        //现金净流量
        List<Double> value1 = getFRDtos("cashflow_flow",now,nameList);
        OutputValueDto outputValueDto1 = new OutputValueDto("现金净流量",null,value1);
        outputValueDtoList.add(outputValueDto1);

        //资金安全警戒线
        List<Double> value2 = getFRDtos("cashflow_cordon",now,nameList);
        OutputValueDto outputValueDto2 = new OutputValueDto("资金安全警戒线",null,value2);
        outputValueDtoList.add(outputValueDto2);

        //产值(不含疫苗)
        List<Double> value3 = getFRDtos("cashflow_outputvalue",now,nameList);
        OutputValueDto outputValueDto3 = new OutputValueDto("产值(不含疫苗)",null,value3);
        outputValueDtoList.add(outputValueDto3);

        //放入返回数据
        vo.setList(outputValueDtoList);
        return vo;
    }

    /**
     * 获取查询财务提报字段
     * @param field 要查询的字段
     * @param dateTime 时间筛选
     * @param nameList 分中心名字集合
     * @return
     */
    private List<Double> getFRDtos(String field, Date dateTime, List<String> nameList) {
        List<ISDataDto> dtos = financialReportingMapper.findList(field,dateTime);
        List<Double> value = dataSort(dtos, nameList);
        return value;
    }
//
    /**
     * 获取利润情况
     * @param param
     * @return
     */
    private IndexSituationVo getProfit(ISDataParam param) {
        IndexSituationVo vo = new IndexSituationVo();
        List<String> nameList = param.getBranchNameList();
        vo.setName(nameList);
        vo.setTitle("利润情况");
        //获取年份
        int lastYear = DateUtil.year(param.getStartTime());
        int thisYear = DateUtil.year(param.getEndTime());
        DateTime now = DateUtil.beginOfMonth(new Date());
        List<OutputValueDto> outputValueDtoList = new ArrayList<>();

        //利润 结束时间
        List<Double> value1 = getFRDtos("profitsituation_profit",param.getEndTime(),nameList);
        OutputValueDto outputValueDto1 = new OutputValueDto("利润",thisYear,value1);
        outputValueDtoList.add(outputValueDto1);

        //利润 开始时间
        List<Double> value2 = getFRDtos("profitsituation_profit",param.getStartTime(),nameList);
        OutputValueDto outputValueDto2 = new OutputValueDto("利润",lastYear,value2);
        outputValueDtoList.add(outputValueDto2);

        //增长率 增长率=增量/原总量*100%
        List<Double> value3 = growthRate(value1,value2);
        OutputValueDto outputValueDto3 = new OutputValueDto("增长率",null,value3);
        outputValueDtoList.add(outputValueDto3);

        //全年目标(不含疫苗)
        List<Double> value4 = getFRDtos("profitsituation_goals",now,nameList);
        OutputValueDto outputValueDto4 = new OutputValueDto("全年目标（不含疫苗）",null,value4);
        outputValueDtoList.add(outputValueDto4);

        //完成率= 实际/目标*100
        List<Double> value5 = completionRate(value1,value4);
        OutputValueDto outputValueDto5 = new OutputValueDto("完成率",null,value5);
        outputValueDtoList.add(outputValueDto5);

        vo.setList(outputValueDtoList);
        return vo;
    }

    /**
     * 获取产值数据
     * @param param
     * @return
     */
    private IndexSituationVo getOutputValue(ISDataParam param) {
        IndexSituationVo vo = new IndexSituationVo();
        List<String> nameList = param.getBranchNameList();
        //分中心及名称
        vo.setName(nameList);
        vo.setTitle("产值情况");

        //获取年份
        int lastYear = DateUtil.year(param.getStartTime());
        int thisYear = DateUtil.year(param.getEndTime());

        //数据
        List<OutputValueDto> outputValueDtoList = new ArrayList<>();
        //产值今年
        List<ISDataDto> doubleList1 = indexSituationMapper.getOutputValue(param.getEndTime(), 1);
        List<Double> value1 = dataSort(doubleList1, nameList);
        OutputValueDto outputValueDto1 = new OutputValueDto("产值",thisYear,value1);
        outputValueDtoList.add(outputValueDto1);

        //产值去年
        List<ISDataDto> doubleList2 = indexSituationMapper.getOutputValue(param.getStartTime(), 1);
        List<Double> value2 = dataSort(doubleList2, nameList);
        OutputValueDto outputValueDto2 = new OutputValueDto("产值",lastYear,value2);
        outputValueDtoList.add(outputValueDto2);

        //产值不含疫苗 今年
        List<ISDataDto> doubleList3 = indexSituationMapper.getOutputValue(param.getEndTime(), 0);
        List<Double> value3 = dataSort(doubleList3, nameList);
        OutputValueDto outputValueDto3 = new OutputValueDto("产值(不含疫苗)",thisYear,value3);
        outputValueDtoList.add(outputValueDto3);

        //产值不含疫苗 去年
        List<ISDataDto> doubleList4 = indexSituationMapper.getOutputValue(param.getStartTime(), 0);
        List<Double> value4 = dataSort(doubleList4, nameList);
        OutputValueDto outputValueDto4 = new OutputValueDto("产值(不含疫苗)",lastYear,value4);
        outputValueDtoList.add(outputValueDto4);

        //同比增长率=(本期统计周期数据-去年同期统计周期数据)÷去年同期统计周期数据×100%;
        List<Double> doubleList5 = growthRate(value3,value4);
        OutputValueDto outputValueDto5 = new OutputValueDto("增长率",lastYear,doubleList5);
        outputValueDtoList.add(outputValueDto5);

        //全年目标(不含疫苗)
        Date now = DateUtil.beginOfMonth(new Date());
        List<Double> value6 = getFRDtos("outputvalue_goals",now,nameList);
        OutputValueDto outputValueDto6 = new OutputValueDto("全年目标（不含疫苗）",null,value6);
        outputValueDtoList.add(outputValueDto6);

        //完成率 = 实际/目标*100
        List<Double> doubleList7 = completionRate(value3,value6);
        OutputValueDto outputValueDto7 = new OutputValueDto("完成率",null,doubleList7);
        outputValueDtoList.add(outputValueDto7);
        vo.setList(outputValueDtoList);
        return vo;
    }

    /**
     * 计算完成率
     * @param realitys 实际
     * @param targets 目标
     * @return
     */
    private List<Double> completionRate(List<Double> realitys, List<Double> targets) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<Double> list = new ArrayList<>();
        //完成率= 实际/目标*100
        for (int i = 0; i < realitys.size(); i++) {
            Double reality = realitys.get(i);
            Double target = targets.get(i);
            //目标为0返回
            if (target == 0.0 || reality == 0.0){
                list.add(0.0);
            }else {
                double divide = Arith.div(reality, target);
                double multiply = Arith.mul(divide, 100d);
                //保留两位小数
                String formattedNumber = decimalFormat.format(multiply);
                double convertedNumber = Double.parseDouble(formattedNumber);
                list.add(convertedNumber);
            }

        }
        return list;
    }

    /**
     * 计算增长率
     * @param nowData 今年数据
     * @param oldData 去年数据
     * @return
     */
    private List<Double> growthRate(List<Double> nowData, List<Double> oldData) {
        List<Double> list = new ArrayList<>();
        //同比增长率=(本期统计周期数据-去年同期统计周期数据)÷去年同期统计周期数据×100%;
        for (int i = 0; i < nowData.size(); i++) {
            Double now = nowData.get(i);
            Double old = oldData.get(i);
            //去年数据等于0直接返回100%
            if (now != 0.0 && old == 0.0){
                list.add(100d);
            }else {
                //本期统计周期数据-去年同期统计周期数据
                double sub = Arith.sub(now, old);
                //÷去年同期统计周期数据
                double divide = Arith.div(sub, old);
                //×100
                double multiply = Arith.mul(divide, 100d);
                list.add(multiply);
            }

        }
        return list;
    }

    /**
     * 数据排序
     * @param list 需要排序的数据
     * @param nameList 排序标准
     * @return
     */
    private List<Double> dataSort(List<ISDataDto> list,List<String> nameList) {
        //找到了就添加值，每找到就添加0
        List<Double> data = new ArrayList<>();
        for (String name : nameList) {
            boolean found = false;
            for (ISDataDto dto : list) {
                if (dto.getFzx().equals(name)) {
                    data.add(dto.getValue());
                    found = true;
                    break;
                }
            }
            if (!found) {
                data.add(0d);
            }
        }
        return data;
    }
}

