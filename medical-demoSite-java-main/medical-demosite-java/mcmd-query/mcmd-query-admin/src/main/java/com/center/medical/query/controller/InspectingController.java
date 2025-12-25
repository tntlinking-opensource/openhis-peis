package com.center.medical.query.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.query.bean.param.InspectingParam;
import com.center.medical.query.bean.vo.InspectingVo;
import com.center.medical.query.bean.vo.LoadFormVo;
import com.center.medical.query.service.InspectingService;
import com.center.medical.sellcrm.bean.vo.GetOrgsVo;
import com.center.medical.sellcrm.service.SellcustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 在检人员信息(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-04-08 09:31:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "在检人员信息")
@RequestMapping("query/inspecting")
public class InspectingController extends BaseController {

    /**
     * 服务对象
     */
    private final InspectingService inspectingService;
    private final MapperFacade mapperFacade;
    private final SellcustomerService sellcustomerService;


    /**
     * 【在检人员信息】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【在检人员信息】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("婚姻下拉", "GET", "/total/RecordManage/getMarriageData", "05.查询模块->查询登记-在检人员信息->婚姻下拉", null)
        );
        return R.ok(new FunctionVo("05.查询模块", "在检人员信息", interfaceVos, "05.查询模块"));
    }

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/loadExamData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "有几个字段改名了,trades改为tradesName,idTjtc改为tjtcmc,idTjtc是tjtcmc,idDoctorreg是doctorregName")
    public R<IPage<InspectingVo>> getPage(PageParamSimple pageParamSimple, InspectingParam param) {
        PageParam<InspectingVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.inspectingService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/loadForm/{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查QT体检者表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<LoadFormVo> loadForm(@PathVariable String id) {
        return R.ok(this.inspectingService.getInfoById(id));
    }


    /**
     * 团体名称下拉
     *
     * @param key
     * @return
     */
    @GetMapping("/getOrgs")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "团体名称下拉", notes = "团体名称下拉")
    @ApiImplicitParam(name = "key", value = "团体名称或输入码")
    public R getOrgs(String key) {
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        List<GetOrgsVo> list = sellcustomerService.getOrgs(key);
        return R.ok(list);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出在检人员名单")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "在检人员名单", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, InspectingParam param) {
        List<InspectingVo> list = inspectingService.getExportData(param);
        ExcelUtil<InspectingVo> util = new ExcelUtil<InspectingVo>(InspectingVo.class);
        util.exportExcel(response, list, "在检人员名单");
    }
}

