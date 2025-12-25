package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.query.bean.dto.EETimeListDto;
import com.center.medical.query.bean.param.ECGroupDataParam;
import com.center.medical.query.bean.param.EEChargeDataParam;
import com.center.medical.query.bean.param.EveryExaminerParam;
import com.center.medical.query.bean.vo.EEChargeDataVo;
import com.center.medical.query.bean.vo.EveryExaminerVo;
import com.center.medical.query.service.EveryExaminerService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 每日体检明细(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-08 09:31:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "每日体检明细")
@RequestMapping("query/everyExaminer")
public class EveryExaminerController extends BaseController {
    /**
     * 服务对象
     */
    private final EveryExaminerService everyExaminerService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/getListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<EveryExaminerVo>> getPage(PageParamSimple pageParamSimple, EveryExaminerParam param) {
        PageParam<EveryExaminerVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.everyExaminerService.getList(page, param));
    }


    /**
     * 获取分组数据
     *
     * @param param
     * @return
     */
    @GetMapping("/getGroupData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取分组数据", notes = "获取分组数据")
    public R<List<EETimeListDto>> getGroupData(ECGroupDataParam param) {
        return R.ok(this.everyExaminerService.getGroupData(param));
    }


    /**
     * 点击获取收费项目信息
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getChargeData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "点击获取收费项目信息", notes = "点击获取收费项目信息")
    public R<IPage<EEChargeDataVo>> getChargeData(PageParamSimple pageParamSimple, EEChargeDataParam param) {
        PageParam<EEChargeDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.everyExaminerService.getChargeData(page, param));
    }


    /**
     * 导出销售团检统计
     *
     * @param response
     * @param param
     */
    @Log(title = "销售团检统计", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出每日体检者构成人员")
    @PostMapping("/exportDetailTjz")
    public void export(HttpServletResponse response, EveryExaminerParam param) {
        List<EveryExaminerVo> list = everyExaminerService.getExportData(param);
        ExcelUtil<EveryExaminerVo> util = new ExcelUtil<EveryExaminerVo>(EveryExaminerVo.class);
        util.exportExcel(response, list, "每日体检者构成-人员");
    }


    /**
     * 导出每日体检者构成团体
     *
     * @param response
     * @param param
     */
    @Log(title = "每日体检者构成团体", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出分组数据", notes = "导出每日体检者构成团体")
    @PostMapping("/exportdataTt")
    public void exportdataTt(HttpServletResponse response, ECGroupDataParam param) {
        List<EETimeListDto> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            param.setIndex(i + 1);
            list.addAll(everyExaminerService.getGroupData(param));
        }
        //序号
        for (int i = 0; i < list.size(); i++) {
            EETimeListDto dto = list.get(i);
            dto.setRownum(i+1);
        }
        ExcelUtil<EETimeListDto> util = new ExcelUtil<EETimeListDto>(EETimeListDto.class);
        util.exportExcel(response, list, "每日体检者构成-团体");
    }
}

