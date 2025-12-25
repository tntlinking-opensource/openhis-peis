package com.center.medical.datascreen.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.NumberUtil;
import com.center.medical.common.utils.DateUtils;
import com.center.medical.datascreen.bean.param.DatascreenBaseTimeAndBranchParam;
import com.center.medical.datascreen.bean.vo.DatascreenBasePieVo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xhp
 * @since 2023-06-07 9:21
 */
public class DatascreenUtil {
    /**
     * 获取%字符串
     * @param discount
     * @return
     */
    public static String getDiscountRateStr(Double discount){
        String discountRate;
        if(discount!=null){
            discountRate= NumberUtil.toStr(NumberUtil.round(NumberUtil.mul(discount,new Double("100")),2))+"%";
        }else{
            discountRate="";
        }
        return discountRate;
    }

    /**
     * 计算客单价
     * 客单价（per customer transaction）是指商场（超市）每一个顾客平均购买商品的金额，也即是平均交易金额 客单价的计算公式是：客单价=销售额÷成交顾客数 。
     * @param amount 总金额
     * @param number 总人数
     * @return
     */
    public static String getCustomerUnitPrice(double amount,int number){
        if(number==0)return "";
        String customerUnitPrice=NumberUtil.toStr(
                NumberUtil.round(
                        NumberUtil.div(amount,number),2
                )
        );
        return customerUnitPrice;
    }

    /**
     * 将时间范围改成指定年份
     */
    public static List<DatascreenBaseTimeAndBranchParam> getGivenYearParam(DatascreenBaseTimeAndBranchParam param, int year){
        List<DatascreenBaseTimeAndBranchParam> result=new ArrayList<>();
        for(int i=0;i<7;i++){
            DatascreenBaseTimeAndBranchParam datascreenBaseTimeAndBranchParam=new DatascreenBaseTimeAndBranchParam();
            BeanUtil.copyProperties(param,datascreenBaseTimeAndBranchParam);
            datascreenBaseTimeAndBranchParam.setStartTime(
                    DateUtils.toDate(
                        LocalDateTimeUtil.beginOfDay(
                                LocalDateTime.now().withYear(year).minusDays(i)
                        )
                    )
            );
            datascreenBaseTimeAndBranchParam.setEndTime(
                    DateUtils.toDate(
                            LocalDateTimeUtil.endOfDay(
                                    LocalDateTime.now().withYear(year).minusDays(i)
                            )
                    )
            );
            result.add(datascreenBaseTimeAndBranchParam);
        }
        return result.stream().sorted(
                (e1,e2) -> e1.getStartTime().after(e2.getStartTime())?1:-1
        ).collect(Collectors.toList());
    }

    /**
     * 将时间范围改成上一年
     */
    public static DatascreenBaseTimeAndBranchParam getLastYearParam(DatascreenBaseTimeAndBranchParam param){
        DatascreenBaseTimeAndBranchParam result=new DatascreenBaseTimeAndBranchParam();
        BeanUtil.copyProperties(param,result);
        result.setStartTime(getLastYear(result.getStartTime()));
        result.setEndTime(getLastYear(result.getEndTime()));
        return result;
    }

    /**
     * 获取上一年
     * @param date
     * @return
     */
    public static Date getLastYear(Date date){
        if(date==null)return null;
        return DateUtils.toDate(LocalDateTimeUtil.of(date).minusYears(1));
    }

    /**
     * 计算增长率
     * @param last
     * @param current
     * @return
     */
    public static String getIncreaseRate(double last,double current){
        if(last==0.0)return "";
        double rate=NumberUtil.div(
                NumberUtil.sub(current,last),
                last
        );
        return getDiscountRateStr(rate);
    }

    /**
     * 计算增长率 不带百分号
     * @param last
     * @param current
     * @return
     */
    public static String getIncreaseRateNumber(double last,double current){
        if(last==0.0)return "";
        double rate=NumberUtil.div(
                NumberUtil.sub(current,last),
                last
        );
        return NumberUtil.toStr(NumberUtil.round(rate,2));
    }

    public static List<DatascreenBasePieVo> getPieData(Double rate){
        List<DatascreenBasePieVo> vos=new ArrayList<>();
        String value;
        String value2;
        if(rate!=null){
            value= NumberUtil.toStr(NumberUtil.round(NumberUtil.mul(rate,new Double("100")),2));
            value2=NumberUtil.toStr(NumberUtil.round(NumberUtil.mul(NumberUtil.sub(new Double("1.0"),rate),100.0),2));
        }else{
            value="";
            value2="";
        }
        DatascreenBasePieVo vo=new DatascreenBasePieVo();
        vo.setValue(value);
        vos.add(vo);
        DatascreenBasePieVo vo2=new DatascreenBasePieVo();
        vo2.setValue(value2);
        vos.add(vo2);
        return vos;
    }
}
