package com.center.medical.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.Arith;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.finance.bean.param.ISListDataParam;
import com.center.medical.finance.bean.param.ISPageParam;
import com.center.medical.finance.bean.vo.ExportOneVo;
import com.center.medical.finance.bean.vo.ISListDataVo;
import com.center.medical.finance.bean.vo.ISPageVo;
import com.center.medical.finance.service.IndividualStatisticsService;
import com.center.medical.system.service.ISysUserService;
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
 * 个检销售统计(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-06 10:40:40
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售提成核算-个检销售统计")
@RequestMapping("finance/individualStatistics")
public class IndividualStatisticsController extends BaseController {
    /**
     * 服务对象
     */
    private final IndividualStatisticsService individualStatisticsService;
    private final MapperFacade mapperFacade;
    private final ISysUserService sysUserService;

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
    public R<IPage<ISPageVo>> getPage(PageParamSimple pageParamSimple, ISPageParam param) {
        PageParam<ISPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.individualStatisticsService.getList(page, param));
    }


    /**
     * 导出单条
     *
     * @param response
     * @param param
     */
    @Log(title = "个检销售统计", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出个检销售统计")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ISPageParam param) {
        if (!StringUtils.isBlank(param.getId()) && param.getIsByDay()==1){
            List<ExportOneVo> list = individualStatisticsService.getExportData(param);
            int i = 1;
            for (ExportOneVo vo : list) {
                vo.setRownum(String.valueOf(i));
                i++;
            }
            ExcelUtil<ExportOneVo> util = new ExcelUtil<ExportOneVo>(ExportOneVo.class);
            util.exportExcel(response, list, "个检销售统计","个检销售统计");
        } else {
            List<ISPageVo> list = individualStatisticsService.getExportsData(param);
            //合计数据 原价合计、实收合计、人数
            Double yjhjTotal = 0.0;
            Double sshjTotal = 0.0;
            Double number = 0.0;
            int i = 1;
            for (ISPageVo vo : list) {
                yjhjTotal = Arith.add(yjhjTotal,vo.getYjhj());
                sshjTotal = Arith.add(sshjTotal,vo.getSshj());
                number = Arith.add(number,vo.getCount());
                vo.setRownum(String.valueOf(i));
                i++;
            }
            ISPageVo vo = new ISPageVo();
            vo.setRownum("合计");
            vo.setYjhj(yjhjTotal);
            vo.setSshj(sshjTotal);
            vo.setCount(number);
            list.add(vo);
            ExcelUtil<ISPageVo> util = new ExcelUtil<ISPageVo>(ISPageVo.class);
            util.exportExcel(response, list, "个检销售统计","个检销售统计");
        }
    }

    /**
     * 导出多条
     *
     * @param response
     * @param param
     */
    @Log(title = "个检销售统计", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "按天导出", notes = "导出个检销售统计")
    @PostMapping("/exportByDay")
    public void exports(HttpServletResponse response, ISPageParam param) {
        List<ISPageVo> list = individualStatisticsService.getExportsData(param);
        int i = 1;
        for (ISPageVo vo : list) {
            vo.setRownum(String.valueOf(i));
            i++;
        }
        ExcelUtil<ISPageVo> util = new ExcelUtil<ISPageVo>(ISPageVo.class);
        util.exportExcel(response, list, "个检销售统计");
    }


    /**
     * 获取关联的数据
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取关联的数据", notes = "获取关联的数据")
    public R<IPage<ISListDataVo>> getListData(PageParamSimple pageParamSimple, ISListDataParam param) {
        PageParam<ISListDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(individualStatisticsService.getListData(page, param));
    }




    /**
     * 导出多条
     *
     * @param response
     * @param param
     */
    @ApiOperation(value = "导出右侧关联数据", notes = "导出右侧关联数据")
    @PostMapping("/exportListData")
    public void exportListData(HttpServletResponse response, ISListDataParam param) {
        List<ISListDataVo> list = individualStatisticsService.exportListData(param);
        int i = 1;
        for (ISListDataVo vo : list) {
            vo.setRownum(String.valueOf(i));
            i++;
        }
        ExcelUtil<ISListDataVo> util = new ExcelUtil<ISListDataVo>(ISListDataVo.class);
        util.exportExcel(response, list, "个检销售统计体检者数据","个检销售统计体检者数据");
    }
}

