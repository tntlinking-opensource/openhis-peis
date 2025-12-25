package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.SummaryDataParam;
import com.center.medical.statistics.bean.vo.SummaryDataVo;
import com.center.medical.statistics.service.SummaryQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * QT体检者表(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-14 16:40:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查统计-禁忌证人员名单及可疑职业病人名单")
@RequestMapping("statistics/contraindication")
public class ContraindicationController extends BaseController {
    /**
     * 服务对象
     */
    private final SummaryQueryService summaryQueryService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/getSummaryData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页职业健康检查职业禁忌证人员名单及可疑职业病人名单")
    public R<IPage<SummaryDataVo>> getPage(PageParamSimple pageParamSimple, SummaryDataParam param) {
        PageParam<SummaryDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.summaryQueryService.getSummaryData(page, param));
    }



    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "职业健康检查发现职业禁忌症人员名单", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出禁忌症")
    @PostMapping("/exportJjz")
    public void export(HttpServletResponse response, SummaryDataParam param) {
        List<SummaryDataVo> list = summaryQueryService.exportJjz(param);
        ExcelUtil<SummaryDataVo> util = new ExcelUtil<SummaryDataVo>(SummaryDataVo.class);
        util.exportExcel(response, list, "职业健康检查发现职业禁忌症人员名单");
    }
}

