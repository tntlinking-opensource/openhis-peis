package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Notificationmethod;
import com.center.medical.data.service.NotificationmethodService;
import com.center.medical.query.bean.param.ReportQueryParam;
import com.center.medical.query.bean.vo.ReportQueryVo;
import com.center.medical.query.service.ReportQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报告信息查询(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-08 09:31:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告信息查询")
@RequestMapping("query/reportQuery")
public class ReportQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportQueryService reportQueryService;
    private final MapperFacade mapperFacade;
    private final NotificationmethodService notificationmethodService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<ReportQueryVo>> getPage(PageParamSimple pageParamSimple, ReportQueryParam param) {
        PageParam<ReportQueryVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportQueryService.getList(page, param));
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出报告信息")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "报告信息", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, ReportQueryParam param) {
        List<ReportQueryVo> list = reportQueryService.getExportData(param);
        ExcelUtil<ReportQueryVo> util = new ExcelUtil<ReportQueryVo>(ReportQueryVo.class);
        util.exportExcel(response, list, "报告信息");
    }


    /**
     * 获取通知方式
     *
     * @return
     */
    @GetMapping("/getOrderNotifycationMethods")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取通知方式", notes = "获取通知方式")
    public R getOrderNotifycationMethods() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        //通知方式
        Notificationmethod m1 = notificationmethodService.getOne(new QueryWrapper<Notificationmethod>().eq("method_name", "电子版报告"));
        if (m1 != null) {
            map = new HashMap<String, String>();
            map.put("id", m1.getId());
            map.put("text", m1.getMethodName());
            result.add(map);
        }
        m1 = notificationmethodService.getOne(new QueryWrapper<Notificationmethod>().eq("method_name", "团检发"));
        if (m1 != null) {
            map = new HashMap<String, String>();
            map.put("id", m1.getId());
            map.put("text", m1.getMethodName());
            result.add(map);
        }
        m1 = notificationmethodService.getOne(new QueryWrapper<Notificationmethod>().eq("method_name", "团检按个发"));
        if (m1 != null) {
            map = new HashMap<String, String>();
            map.put("id", m1.getId());
            map.put("text", m1.getMethodName());
            result.add(map);
        }
        return R.ok(result);
    }


    /**
     * 保存发放方式
     *
     * @param ids
     * @param idInformway
     * @return
     */
    @PostMapping("/saveInfoXx")
    @ApiOperation(value = "保存发放方式", notes = "保存发放方式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "修改记录的id,多条"),
            @ApiImplicitParam(name = "idInformway", value = "发放方式id")
    })
    public R saveConsumex(@RequestParam List<String> ids, @RequestParam String idInformway) {
        Boolean b = reportQueryService.saveConsumex(ids, idInformway);
        return R.toResult(b);
    }
}

