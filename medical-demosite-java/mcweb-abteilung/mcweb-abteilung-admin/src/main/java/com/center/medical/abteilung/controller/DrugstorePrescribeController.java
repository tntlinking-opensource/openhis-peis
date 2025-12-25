package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.DrugstorePreParam;
import com.center.medical.abteilung.bean.param.PrescribeParam;
import com.center.medical.abteilung.bean.param.TakeDrugParam;
import com.center.medical.abteilung.bean.vo.DrugstorePreVo;
import com.center.medical.abteilung.service.DrugstorePrescribeService;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.vo.GetDoctorVo;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 药房管理-审核发药(DrugstorePrescribe)表控制层
 *
 * @author ay
 * @since 2023-01-13 13:42:26
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "药房管理-审核发药")
@RequestMapping("/drugstore/prescribe")
public class DrugstorePrescribeController extends BaseController {
    /**
     * 服务对象
     */
    private final DrugstorePrescribeService drugstorePrescribeService;
    private final MapperFacade mapperFacade;
    private final ISysUserService sysUserService;

    /**
     * 【药房管理-审核发药】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【药房管理-审核发药】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/drugstore/prescribe/page", "09.科室系统->科室管理-药房管理-审核发药->分页查询", null),
                new InterfaceVo("获取医师", "GET", "/drugstore/prescribe/getDoctor", "09.科室系统->科室管理-药房管理-审核发药->获取医师", null),
                new InterfaceVo("通过药品名称查询", "GET", "/drugstore/drug/getSelectData", "09.科室系统->科室管理-药房管理-药品管理->通过药品名称查询", null),
                new InterfaceVo("快捷开药-右侧-获得已开药记录", "GET", "/drugstore/drug/getAddedData", "09.科室系统->科室管理-药房管理-药品管理->快捷开药-右侧-获得已开药记录", null),
                new InterfaceVo("快捷开药-保存", "POST", "/drugstore/drug/saveOrUpdate", "09.科室系统->科室管理-药房管理-药品管理->快捷开药-保存", null),
                new InterfaceVo("药房取药", "PUT", "/drugstore/drug/saveOrUpdate", "09.科室系统->科室管理-药房管理-药品管理->药房取药", null),
                new InterfaceVo("获取上次体检开的什么药", "GET", "/drugstore/drug/getLastDrugs", "09.科室系统->科室管理-药房管理-药品管理->获取上次体检开的什么药", null),
                new InterfaceVo("退药", "PUT", "/drugstore/drug/drugRepercussion", "09.科室系统->科室管理-药房管理-药品管理->退药", null)
        );
        return R.ok(new FunctionVo("09.科室系统", "药房管理-审核发药", interfaceVos, "09.科室系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询开药记录")
    public R<IPage<DrugstorePreVo>> getPage(PageParamSimple pageParamSimple, DrugstorePreParam param) {
        PageParam<DrugstorePreVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.drugstorePrescribeService.getList(page, param));
    }


    /**
     * 获取医师
     *
     * @param key
     * @return
     */
    @GetMapping("/getDoctor")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取医师", notes = "获取医师")
    @ApiImplicitParam(name = "key", value = "输入码")
    public R<List<GetDoctorVo>> getDoctor(String key) {
        String cId = SecurityUtils.getCId();
        //去空格
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        List<GetDoctorVo> list = sysUserService.getDoctor(cId, key);
        return R.ok(list);
    }


    /**
     * 快捷开药-右侧-获得已开药记录
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getAddedData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "快捷开药-右侧-获得已开药记录", notes = "快捷开药-右侧-获得已开药记录")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<List<DrugstorePreVo>> getAddedData(String patientcode) {
        List<DrugstorePreVo> list = drugstorePrescribeService.getAddedData(patientcode);
        return R.ok(list);
    }


    /**
     * 快捷开药-保存
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "快捷开药-保存", notes = "快捷开药-保存,因为老系统没数据，所以可能缺数据")
    public R saOrUp(@RequestBody PrescribeParam param) {
        return R.toResult(drugstorePrescribeService.saOrUp(param));
    }


    /**
     * 药房取药
     *
     * @param param
     * @return
     */
    @PutMapping("/takeDrug")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "药房取药", notes = "药房取药")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R takeDrug(TakeDrugParam param) {
        Boolean b = drugstorePrescribeService.takeDrug(param);
        return R.toResult(b);
    }


    /**
     * 获取上次体检开的什么药
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getLastDrugs")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取上次体检开的什么药", notes = "获取上次体检开的什么药")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<List<DrugstorePreVo>> getLastDrugs(String patientcode) {
        List<DrugstorePreVo> list = drugstorePrescribeService.getLastDrugs(patientcode);
        return R.ok(list);
    }


    /**
     * 退药
     *
     * @param id
     * @return
     */
    @PutMapping("/drugRepercussion")
    @ApiOperation(value = "退药", notes = "退药")
    @ApiImplicitParam(name = "id", value = "id")
    public R drugRepercussion(String id) {
        Boolean b = drugstorePrescribeService.drugRepercussion(id);
        return R.toResult(b);
    }


}

