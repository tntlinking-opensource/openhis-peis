package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.param.HSPageParam;
import com.center.medical.finance.bean.param.TotalListParam;
import com.center.medical.finance.bean.vo.HSPageVo;
import com.center.medical.finance.bean.vo.TotalListVo;
import com.center.medical.finance.service.HealthyStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 销售提成核算-销售团检统计(Createorder)表控制层
 *
 * @author ay
 * @since 2023-04-04 16:53:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售提成核算-销售团检统计")
@RequestMapping("finance/healthyStatistics")
public class HealthyStatisticsController extends BaseController {
    /**
     * 服务对象
     */
    private final HealthyStatisticsService healthyStatisticsService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询订单表")
    public R<IPage<HSPageVo>> getPage(PageParamSimple pageParamSimple, HSPageParam param) {
        PageParam<HSPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.healthyStatisticsService.getList(page, param));
    }


    /**
     * 查询右边列表
     *
     * @param param
     * @return
     */
    @GetMapping("/getTotalList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询右边列表", notes = "查询右边列表")
    public R<List<TotalListVo>> getTotalList(TotalListParam param) {
        List<TotalListVo> list = healthyStatisticsService.getTotalList(param);
        return R.ok(list);
    }


    /**
     * 导出销售团检统计
     *
     * @param response
     * @param param
     */
    @Log(title = "销售团检统计", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出销售团检统计")
    @PostMapping("/export")
    public void export(HttpServletResponse response, HSPageParam param) {
        List<HSPageVo> list = healthyStatisticsService.getExportData(param);
        ExcelUtil<HSPageVo> util = new ExcelUtil<HSPageVo>(HSPageVo.class);
        util.exportExcel(response, list, "销售团检统计");
    }


    /**
     * 判断权限
     *
     * @param param
     * @return
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断权限", notes = "判断是否有决策管理的权限及分中心")
    public R list(TotalListParam param) {
        //决策管理
        String isGreatLeader = "";
        if (SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            isGreatLeader = "1";
        } else {
            isGreatLeader = "0";
        }
        String cId = SecurityUtils.getCId();
        Map<String, String> map = new HashMap<>();
        map.put("isGreatLeader", isGreatLeader);
        map.put("id", cId);
        return R.ok(map);
    }
}

