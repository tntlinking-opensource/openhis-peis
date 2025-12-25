package com.center.medical.statistics.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.ChargeDistributeParam;
import com.center.medical.statistics.bean.vo.CDGetTotalVo;
import com.center.medical.statistics.bean.vo.ChargeDistributeVo;
import com.center.medical.statistics.service.ChargeDistributeService;
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
 * 收费项目分布情况(Peispatientfeeitem)表控制层
 *
 * @author ay
 * @since 2023-04-18 19:47:26
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "收费项目分布情况")
@RequestMapping("statistics/chargeDistribute")
public class ChargeDistributeController extends BaseController {
    /**
     * 服务对象
     */
    private final ChargeDistributeService chargeDistributeService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple    分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询体检者收费项目表")
    public R<IPage<ChargeDistributeVo>> getPage(PageParamSimple pageParamSimple, ChargeDistributeParam param) {
        PageParam<ChargeDistributeVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.chargeDistributeService.getList(page, param));
    }




    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "体检收费项目分布", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出体检收费项目分布")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChargeDistributeParam param) {
        List<ChargeDistributeVo> list = chargeDistributeService.exportData(param);
        ExcelUtil<ChargeDistributeVo> util = new ExcelUtil<ChargeDistributeVo>(ChargeDistributeVo.class);
        util.exportExcel(response, list, "体检收费项目分布");
    }


    /**
     * 获取总数
     * @param param
     * @return
     */
    @GetMapping("/getTotal")
    @ApiOperation(value = "获取总数", notes = "获取总数")
    public R<CDGetTotalVo> getTotal(ChargeDistributeParam param) {
        return R.ok(this.chargeDistributeService.getTotal(param));
    }

}

