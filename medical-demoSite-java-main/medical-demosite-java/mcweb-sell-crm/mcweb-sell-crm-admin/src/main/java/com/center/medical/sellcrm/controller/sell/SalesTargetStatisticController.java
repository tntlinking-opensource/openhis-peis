package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.SalesTargetStatistic;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.sellcrm.service.LeadertargetService;
import com.center.medical.sellcrm.service.SelltargetService;
import com.center.medical.service.SalesTargetStatisticService;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;

/**
 * 销售季度目标(Selltarget)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售目标自动统计定时任务")
@RequestMapping("sell/salesTargetStatistic")
public class SalesTargetStatisticController extends BaseController {
    /**
     * 服务对象
     */
    private final SelltargetService selltargetService;
    private final MapperFacade mapperFacade;
    private final LeadertargetService leadertargetService;
    private final ISysUserService sysUserService;
    private final SalesTargetStatisticService salesTargetStatisticService;


    /**
     * 获取领导制定的年度目标
     *
     * @param yearStr 分页参数
     * @return 所有数据
     */
    @GetMapping("/SalesTargetStatisticTask")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取年度目标", notes = "获取年度目标，返回空就是没有")
    @ApiImplicitParam(name = "yearStr", value = "年份")
    public R SalesTargetStatisticTask(String yearStr) {
        Calendar c=Calendar.getInstance();
        int yearEnd=c.get(Calendar.YEAR);
        int yearStart= StringUtils.isNotEmpty(yearStr)?Integer.parseInt(yearStr):yearEnd;
        while(yearStart<=yearEnd){
            String year=yearStart+"";
            //清除老数据
            salesTargetStatisticService.remove(new LambdaQueryWrapper<SalesTargetStatistic>().eq(SalesTargetStatistic::getYear,year));

            //查询并添加
            List<SalesTargetStatistic> leaderList = salesTargetStatisticService.selectLeaderList(year);
            log.info("年度计划共"+leaderList.size()+"条");
            for(SalesTargetStatistic salesTargetStatistic:leaderList){
                salesTargetStatistic.setYear(year);
                salesTargetStatisticService.save(salesTargetStatistic);
            }
            List<SalesTargetStatistic> sellList = salesTargetStatisticService.selectSellList(year);
            log.info("季度计划共"+sellList.size()+"条");
            for(SalesTargetStatistic salesTargetStatistic:sellList){
                salesTargetStatistic.setYear(year);
                salesTargetStatisticService.save(salesTargetStatistic);
            }
            List<SalesTargetStatistic> monthList = salesTargetStatisticService.selectMonthList(year);
            log.info("月度计划共"+monthList.size()+"条");
            for(SalesTargetStatistic salesTargetStatistic:monthList){
                salesTargetStatistic.setYear(year);
                salesTargetStatisticService.save(salesTargetStatistic);
            }
            yearStart++;
        }
        return R.ok(Boolean.TRUE);
    }

}

