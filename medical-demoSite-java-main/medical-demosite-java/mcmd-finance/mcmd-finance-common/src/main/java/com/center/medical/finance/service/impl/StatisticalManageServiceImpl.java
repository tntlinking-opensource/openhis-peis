package com.center.medical.finance.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.*;
import com.center.medical.finance.bean.dto.*;
import com.center.medical.finance.bean.vo.StatisticalManageVo;
import com.center.medical.finance.dao.DaySalesVolumeMapper;
import com.center.medical.finance.dao.FinancialReportingMapper;
import com.center.medical.finance.dao.IndexSituationMapper;
import com.center.medical.finance.dao.StatisticalManageMapper;
import com.center.medical.finance.service.StatisticalManageService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.system.dao.SysBranchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务报表-统计报表(Createorder)表服务实现类
 *
 * @author ay
 * @since 2023-05-15 09:37:35
 */
@Slf4j
@Service("statisticalManageService")
@RequiredArgsConstructor
public class StatisticalManageServiceImpl extends ServiceImpl<StatisticalManageMapper, Createorder> implements StatisticalManageService {
    private final StatisticalManageMapper statisticalManageMapper;
    private final SysBranchMapper sysBranchMapper;
    private final FinancialReportingMapper financialReportingMapper;
    private final DaySalesVolumeMapper daySalesVolumeMapper;
    private final IndexSituationMapper indexSituationMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Createorder> getList(PageParam<Createorder> page, Createorder param) {
        return statisticalManageMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createorder getInfoById(String id) {
        return statisticalManageMapper.getInfoById(id);
    }


    /**
     * 查询统计数据
     * @param date
     * @return
     */
    @Override
    public StatisticalManageVo getData(Date date) {
        //分中心名字集合
        List<SysBranch> sysBranches = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>()
                .eq(SysBranch::getIsShow, 1)
                .orderByAsc(SysBranch::getBranchSort));
        List<String> nameList = sysBranches.stream().map(b -> b.getFzx()).collect(Collectors.toList());

        StatisticalManageVo vo = new StatisticalManageVo();

        //新增客户及数量
        List<NewCustomersDto> newCustomersDtoList = getNewCustomersDto(nameList,date);
        //体检机构大客户
        List<KeyAccountDto> keyAccountDtoList = getKeyAccountDto(nameList,date);
        //来检人数及体检收入
        List<IncomeDto> incomeDtoList = getIncomeDto(nameList,date);
        //客单价及折扣
        List<CustomerPriceDto> customerPriceDtoList = getCustomerPriceDto(nameList,date);



        vo.setNewCustomers(newCustomersDtoList);
        vo.setIncome(incomeDtoList);
        vo.setKeyAccount(keyAccountDtoList);
        vo.setCustomerPrice(customerPriceDtoList);
        return vo;
    }

    /**
     * 客单价及折扣
     * @param nameList
     * @param end
     * @return
     */
    private List<CustomerPriceDto> getCustomerPriceDto(List<String> nameList, Date end) {
        List<CustomerPriceDto> list = new ArrayList<>();
        //获取年份
        int thisYear = DateUtil.year(end);
        int lastYear = thisYear -1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.YEAR, -1);
        Date start = calendar.getTime();

        //折扣率今年
        List<Double> value1 = getFRDtos("saleoverdue_discount_rate",end,nameList);
        //折扣率去年
        List<Double> value2 = getFRDtos("saleoverdue_discount_rate",start,nameList);
        //平均客单价(元) 今年
        List<Double> value3 = unitPrice(nameList,end);
        //平均客单价(元) 去年
        List<Double> value4 = unitPrice(nameList,start);
        for (int i = 0; i < nameList.size(); i++) {
            String name = nameList.get(i);
            CustomerPriceDto dto = new CustomerPriceDto();
            dto.setName(name);
            dto.setThisYear(thisYear);
            dto.setLastYear(lastYear);
            dto.setThisDiscountRate(value1.get(i));
            dto.setLastDiscountRate(value2.get(i));
            dto.setThisCustomerPrice(value3.get(i));
            dto.setLastCustomerPrice(value4.get(i));
            list.add(dto);
        }
        return list;
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
     * 来检人数及体检收入
     * @param nameList
     * @param end
     * @return
     */
    private List<IncomeDto> getIncomeDto(List<String> nameList, Date end) {
        List<IncomeDto> list = new ArrayList<>();
        //获取年份
        int thisYear = DateUtil.year(end);
        int lastYear = thisYear -1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.add(Calendar.YEAR, -1);
        Date start = calendar.getTime();

        //去年数据
        List<ISDataDto> list1 = daySalesVolumeMapper.findInCome(start);
        List<Double> value1 = dataSort(list1,nameList);
        //今年数据
        List<ISDataDto> list2 = daySalesVolumeMapper.findInCome(end);
        List<Double> value2 = dataSort(list2,nameList);
        //去年收入
        List<ISDataDto> list3 = daySalesVolumeMapper.findInComeMoney(start);
        List<Double> value3 = dataSort(list3,nameList);
        //今年收入
        List<ISDataDto> list4 = daySalesVolumeMapper.findInComeMoney(end);
        List<Double> value4 = dataSort(list4,nameList);

        for (int i = 0; i < nameList.size(); i++) {
            String name = nameList.get(i);
            IncomeDto dto = new IncomeDto();
            dto.setName(name);
            dto.setLastCount(value1.get(i));
            dto.setThisCount(value2.get(i));
            dto.setLastIncome(value3.get(i));
            dto.setThisIncome(value4.get(i));
            dto.setLastYear(lastYear);
            dto.setThisYear(thisYear);
            list.add(dto);
        }
        return list;
    }


    /**
     * 体检机构大客户
     * @param nameList
     * @param date
     * @return
     */
    private List<KeyAccountDto> getKeyAccountDto(List<String> nameList, Date date) {
        List<KeyAccountDto> list = new ArrayList<>();
        //50万以上大单个数
        List<Double> value1 = getFRDtos("customer_big50_count",date,nameList);
        //大单金额(20万-50万大单个数)
        List<Double> value2 = getFRDtos("customer_big20",date,nameList);
        for (int i = 0; i < nameList.size(); i++) {
            String name = nameList.get(i);
            KeyAccountDto dto = new KeyAccountDto();
            dto.setName(name);
            dto.setFiveHundred(value1.get(i));
            dto.setTwoHundred(value2.get(i));
            list.add(dto);
        }
        return list;


    }

    /**
     * 新增客户及数量
     * @param nameList
     * @param date
     * @return
     */
    private List<NewCustomersDto> getNewCustomersDto(List<String> nameList, Date date) {
        List<NewCustomersDto> list = new ArrayList<>();
        //新单金额
        List<Double> value1 = getFRDtos("customer_new",date,nameList);
        //新单个数
        List<Double> value2 = getFRDtos("customer_newCount",date,nameList);
        for (int i = 0; i < nameList.size(); i++) {
            String name = nameList.get(i);
            NewCustomersDto dto = new NewCustomersDto();
            dto.setName(name);
            dto.setMoney(value1.get(i));
            dto.setCount(value2.get(i));
            list.add(dto);
        }
        return list;
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

