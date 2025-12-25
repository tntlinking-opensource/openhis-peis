package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.report.bean.param.IRParam;
import com.center.medical.report.bean.param.inspectReportsParam;
import com.center.medical.report.bean.vo.IRPageVo;
import com.center.medical.report.bean.vo.InfoListDataVo;
import com.center.medical.report.service.InspectReportsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 检验报告生成记录(InspectReport)表控制层
 *
 * @author ay
 * @since 2023-07-11 09:27:31
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "检验报告")
@RequestMapping("/report/inspectReports")
public class InspectReportsController extends BaseController {
    /**
     * 服务对象
     */
    private final InspectReportsService inspectReportsService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param   查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询检验报告生成记录")
    public R<IPage<IRPageVo>> getPage(PageParamSimple pageParamSimple, IRParam param) {
        PageParam<IRPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.inspectReportsService.getList(page, param));
    }

    /**
     * 获取右侧详情
     * @param pageParamSimple
     * @param patientcode
     * @return
     */
    @GetMapping("/getInfoListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取右侧详情", notes = "获取右侧详情")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<IPage<InfoListDataVo>> getInfoListData(PageParamSimple pageParamSimple, String patientcode) {
        PageParam<InfoListDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.inspectReportsService.getInfoListData(page, patientcode));
    }


    /**
     * 生成检验报告
     * @return
     */
    @GetMapping("/create")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "生成检验报告", notes = "生成检验报告")
    public R create(inspectReportsParam param) {
        Boolean b = inspectReportsService.create(param);
        return R.ok(b);
    }

}

