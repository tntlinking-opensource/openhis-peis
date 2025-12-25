package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.SampleDelivery;
import com.center.medical.abteilung.bean.param.SDSaOrUParam;
import com.center.medical.abteilung.bean.param.SampleDeliveryParam;
import com.center.medical.abteilung.bean.vo.SampleDeVo;
import com.center.medical.abteilung.service.SampleDeliveryService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.GetItemDataVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.service.HandleNewProjectsService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientfeeitemService;
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
import java.util.HashMap;
import java.util.List;

/**
 * 检验样本-样本录入(SampleDelivery)表控制层
 *
 * @author ay
 * @since 2023-01-08 17:18:22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "检验样本-样本录入")
@RequestMapping("/handle/sampleDelivery")
public class SampleDeliveryController extends BaseController {
    /**
     * 服务对象
     */
    private final SampleDeliveryService sampleDeliveryService;
    private final HandleNewProjectsService handleNewProjectsService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;
    private final PeispatientfeeitemService peispatientfeeitemService;

    /**
     * 【检验样本-样本录入】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【检验样本-样本录入】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/handle/sampleDelivery/page", "09.科室系统->科室管理-外送管理-样本录入->分页查询", null),
                new InterfaceVo("录入保存", "POST", "/handle/sampleDelivery/saveOrUpdate", "09.科室系统->科室管理-外送管理-样本录入->录入保存", null),
                new InterfaceVo("删除", "DELETE", "/handle/sampleDelivery/{ids}", "09.科室系统->科室管理-外送管理-样本录入->删除", null),
                new InterfaceVo("新增样本-搜索体检号", "GET", "/handle/sampleDelivery/getTjzData", "09.科室系统->科室管理-外送管理-样本录入->新增样本-搜索体检号", null),
                new InterfaceVo("获取收费项目", "GET", "/handle/sampleDelivery/getItemData", "09.科室系统->科室管理-外送管理-样本录入->获取收费项目", null),
                new InterfaceVo("导出", "POST", "/handle/sampleDelivery/export", "09.科室系统->科室管理-外送管理-样本录入->导出", null)
        );
        return R.ok(new FunctionVo("09.科室系统", "检验样本-样本录入", interfaceVos, "09.科室系统"));
    }


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<SampleDeVo>> getPage(PageParamSimple pageParamSimple, SampleDeliveryParam param) {
        PageParam<SampleDelivery> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sampleDeliveryService.getList(page, param));
    }


    /**
     * 录入保存
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "录入保存", notes = "录入保存")
    public R saveOrUpdate(@RequestBody SDSaOrUParam param) {
        return R.toResult(sampleDeliveryService.saOrUp(param));
    }


    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除KS样本录入")
    @Log(title = "KS样本录入", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        //假删
        SampleDelivery sd = new SampleDelivery();
        sd.setIsDelete(1);
        boolean b = sampleDeliveryService.update(sd, new UpdateWrapper<SampleDelivery>().in("id", ids));
        return R.toResult(b);
    }


    /**
     * 新增样本搜索
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getTjzData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "新增样本-搜索体检号", notes = "新增样本-搜索体检号")
    @ApiImplicitParam(name = "patientcode", value = "体检码")
    public R getTjzData(String patientcode) {
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", patientcode));
        if (ObjectUtils.isNotEmpty(peispatient)) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("patientname", peispatient.getPatientname());
            map.put("idSex", peispatient.getIdSex());
            return R.ok(map);
        } else {
            return R.fail();
        }
    }


    /**
     * 获取收费项目
     *
     * @param pageParamSimple
     * @param patientcode
     * @return
     */
    @GetMapping("/getItemData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "新增样本-搜索体检号-項目列表", notes = "新增样本-搜索体检号-項目列表")
    @ApiImplicitParam(name = "patientcode", value = "体检码")
    public R<IPage<GetItemDataVo>> getItemData(PageParamSimple pageParamSimple, String patientcode) {
        PageParam<GetItemDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<GetItemDataVo> itemData = peispatientfeeitemService.getItemData(page, patientcode);
        return R.ok(itemData);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "样本录入", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SampleDeliveryParam param) {
        List<SampleDeVo> list = sampleDeliveryService.export(param);
        ExcelUtil<SampleDeVo> util = new ExcelUtil<SampleDeVo>(SampleDeVo.class);
        util.exportExcel(response, list, "样本录入");
    }


}

