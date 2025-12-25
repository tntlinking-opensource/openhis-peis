package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.sellcrm.bean.param.DayTargetParam;
import com.center.medical.sellcrm.bean.vo.DayTargetVo;
import com.center.medical.sellcrm.service.DayTargetService;
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
 * 销售日目标(CrmSelltarget)接口
 *
 * @author ay
 * @since 2024-01-22 11:13:07
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售日目标")
@RequestMapping("sell/dayTarget")
public class DayTargetController extends BaseController {
    /**
     * 服务对象
     */
    private final DayTargetService dayTargetService;
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
    @ApiOperation(value = "分页查询", notes = "分页查询销售日目标")
    public R<IPage<DayTargetVo>> getPage(PageParamSimple pageParamSimple, DayTargetParam param) {
        PageParam<DayTargetVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.dayTargetService.getPage(page, param));
    }




    /**
     * 获取总结数据
     * @param param
     * @return
     */
    @GetMapping("/getSummaryData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取总结数据", notes = "获取总结数据")
    public R<Double> getSummaryData(DayTargetParam param) {
        Double aDouble = dayTargetService.getSummaryData(param);
        return R.ok(aDouble);
    }





    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "销售日目标", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出销售日目标", notes = "导出销售日目标")
    @PostMapping("/export")
    public void export(HttpServletResponse response, DayTargetParam param) {
        List<DayTargetVo> list = dayTargetService.exportData(param);
        //加序号
        for (int i = 0; i < list.size(); i++) {
            DayTargetVo dayTargetVo = list.get(i);
            dayTargetVo.setRownum(i+1);
        }
        ExcelUtil<DayTargetVo> util = new ExcelUtil<DayTargetVo>(DayTargetVo.class);
        util.exportExcel(response, list, "销售日目标");
    }

}
