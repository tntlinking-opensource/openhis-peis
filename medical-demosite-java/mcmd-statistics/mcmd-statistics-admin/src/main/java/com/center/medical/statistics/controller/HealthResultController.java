package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.HealthResultParam;
import com.center.medical.statistics.bean.vo.HealthResultVo;
import com.center.medical.statistics.service.HealthResultService;
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
import java.util.List;

/**
 * QT体检者表(Peispatient)接口
 *
 * @author ay
 * @since 2023-10-25 09:06:02
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "健康检查结果附表")
@RequestMapping("statistics/healthResult")
public class HealthResultController extends BaseController {
    /**
     * 服务对象
     */
    private final HealthResultService healthResultService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<HealthResultVo>> getPage(PageParamSimple pageParamSimple, HealthResultParam param) {
        PageParam<HealthResultVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.healthResultService.getPage(page, param));
    }




    /**
     * 导出
     * @param response
     * @param param
     */
    @Log(title = "健康检查结果附表", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出", notes = "导出健康检查结果附表")
    @PostMapping("/export")
    public void export(HttpServletResponse response, HealthResultParam param) {
        List<HealthResultVo> list = healthResultService.getExportData(param);
        ExcelUtil<HealthResultVo> util = new ExcelUtil<HealthResultVo>(HealthResultVo.class);
        util.exportExcel(response, list, "健康检查结果附表","健康检查结果附表");
    }


}
