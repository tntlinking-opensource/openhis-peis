package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.query.bean.param.GeneQueryParam;
import com.center.medical.query.bean.vo.GeneQueryVo;
import com.center.medical.query.service.GeneQueryService;
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
@Api(tags = "新产品数据查询")
@RequestMapping("query/geneQuery")
public class GeneQueryController extends BaseController {
    /**
     * 服务对象
     */
    private final GeneQueryService geneQueryService;
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
    public R<IPage<GeneQueryVo>> getPage(PageParamSimple pageParamSimple, GeneQueryParam param) {
        PageParam<GeneQueryVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.geneQueryService.getPage(page, param));
    }





    /**
     * 导出每日体检者构成团体
     * @param response
     * @param param
     */
    @Log(title = "新产品数据查询", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出新产品数据查询")
    @PostMapping("/export")
    public void export(HttpServletResponse response, GeneQueryParam param) {
        List<GeneQueryVo> list = geneQueryService.getExportData(param);
        ExcelUtil<GeneQueryVo> util = new ExcelUtil<GeneQueryVo>(GeneQueryVo.class);
        util.exportExcel(response, list, "新产品数据查询","新产品数据查询");
    }

}
