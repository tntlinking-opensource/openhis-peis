package com.center.medical.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SalesTargetStatistic;
import com.center.medical.bean.param.SelectMonthListParam;
import com.center.medical.bean.param.SelectSellListParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.SalesTargetStatisticMapper;
import com.center.medical.service.SalesTargetStatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 销售目标自动统计(SalesTargetStatistic)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:03
 */
@Slf4j
@Service("salesTargetStatisticService")
@RequiredArgsConstructor
public class SalesTargetStatisticServiceImpl extends ServiceImpl<SalesTargetStatisticMapper, SalesTargetStatistic> implements SalesTargetStatisticService {

    private final SalesTargetStatisticMapper salesTargetStatisticMapper;

    /**
     * 分页查询[销售目标自动统计]列表
     *
     * @param page  分页参数
     * @param param SalesTargetStatistic查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SalesTargetStatistic> getList(PageParam<SalesTargetStatistic> page, SalesTargetStatistic param) {
        return salesTargetStatisticMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public SalesTargetStatistic getInfoById(String id) {
        return salesTargetStatisticMapper.getInfoById(id);
    }

    /**
     * 销售目标自动统计年度计划
     * @param year
     * @return
     */
    @Override
    public List<SalesTargetStatistic> selectLeaderList(String year) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = null;
        try {
            date = formatter.parse(year);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return salesTargetStatisticMapper.selectLeaderList(DateUtil.beginOfYear(date),DateUtil.endOfYear(date));
    }

    /**
     * 销售目标自动统计季度计划
     * @param year
     * @return
     */
    @Override
    public List<SalesTargetStatistic> selectSellList(String year) {
        SelectSellListParam param = new SelectSellListParam();
        param.setYearStart(year+"-01-01 00:00:00");
        param.setYearEnd(year+"-12-31 23:59:59");
        param.setSeasonStart1(year+"-01-01 00:00:00");
        param.setSeasonEnd1(year+"-03-31 23:59:59");
        param.setSeasonStart2(year+"-04-01 00:00:00");
        param.setSeasonEnd2(year+"-06-30 23:59:59");
        param.setSeasonStart3(year+"-07-01 00:00:00");
        param.setSeasonEnd3(year+"-09-30 23:59:59");
        param.setSeasonStart4(year+"-10-01 00:00:00");
        param.setSeasonEnd4(year+"-12-31 23:59:59");
        return salesTargetStatisticMapper.selectSellList(param);
    }

    /**
     * 销售目标自动统计月度计划
     * @param year
     * @return
     */
    @Override
    public List<SalesTargetStatistic> selectMonthList(String year) {
        SelectMonthListParam param = new SelectMonthListParam();
        param.setYearStart(year+"-01-01 00:00:00");
        param.setYearEnd(year+"-12-31 23:59:59");
        param.setMonthStart1(year+"-01-01 00:00:00");
        param.setMonthEnd1(year+"-01-31 23:59:59");
        param.setMonthStart2(year+"-02-01 00:00:00");
        param.setMonthEnd2(getFebruaryLast(year));
        param.setMonthStart3(year+"-03-01 00:00:00");
        param.setMonthEnd3(year+"-03-31 23:59:59");
        param.setMonthStart4(year+"-04-01 00:00:00");
        param.setMonthEnd4(year+"-04-30 23:59:59");
        param.setMonthStart5(year+"-05-01 00:00:00");
        param.setMonthEnd5(year+"-05-31 23:59:59");
        param.setMonthStart6(year+"-06-01 00:00:00");
        param.setMonthEnd6(year+"-06-30 23:59:59");
        param.setMonthStart7(year+"-07-01 00:00:00");
        param.setMonthEnd7(year+"-07-31 23:59:59");
        param.setMonthStart8(year+"-08-01 00:00:00");
        param.setMonthEnd8(year+"-08-31 23:59:59");
        param.setMonthStart9(year+"-09-01 00:00:00");
        param.setMonthEnd9(year+"-09-30 23:59:59");
        param.setMonthStart9(year+"-10-01 00:00:00");
        param.setMonthEnd9(year+"-10-31 23:59:59");
        param.setMonthStart9(year+"-11-01 00:00:00");
        param.setMonthEnd9(year+"-11-30 23:59:59");
        param.setMonthStart9(year+"-12-01 00:00:00");
        param.setMonthEnd9(year+"-12-31 23:59:59");
        return salesTargetStatisticMapper.selectMonthList(param);
    }



    /**
     * 获取二月最后一天
     */
    public String getFebruaryLast(String yearstr){
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,Integer.parseInt(yearstr));
        c.set(Calendar.MONTH,2);
        c.set(Calendar.DAY_OF_MONTH,c.getMinimum(Calendar.DATE)-1);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        return sdf.format(c.getTime())+"235959";
    }
}

