package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.abteilung.bean.model.ElectroAudiometer;
import com.center.medical.abteilung.bean.param.EAsaOrUpParam;
import com.center.medical.abteilung.service.ElectroAudiometerService;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.system.service.ISysBranchService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 科室列表-电测听(ElectroAudiometer)表控制层
 *
 * @author ay
 * @since 2023-02-17 10:24:39
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室列表-电测听")
@RequestMapping("abteilung/electroAudiometer")
public class ElectroAudiometerController extends BaseController {
    /**
     * 服务对象
     */
    private final ElectroAudiometerService electroAudiometerService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;


    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/search")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "读卡", notes = "查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "autoFill", value = "是否补全体检号，true是false否"),
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "ksId", value = "科室id")
    })
    public R search(String autoFill, String patientCode, String ksId) {
        //补全体检号
        if ("true".equals(autoFill)) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        } else {
            patientCode = patientCode.trim().toUpperCase();
        }
        HashMap<String, Object> map = electroAudiometerService.search(patientCode, ksId);
        return R.ok(map);
    }

    /**
     * 审核
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "审核", notes = "审核")
    @Log(title = "KS电测听", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"electroAudiometer.id"})
    public R insert(@RequestBody EAsaOrUpParam param) {
        return R.toResult(this.electroAudiometerService.saOrUp(param));
    }


    /**
     * 反审核
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    @PutMapping("/reverse")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "反审核", notes = "反审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "ksId", value = "科室id")
    })
    public R reverse(String patientCode, String ksId) {
        return R.toResult(this.electroAudiometerService.reverse(patientCode, ksId));
    }


    /**
     * 查看已上传的图片
     *
     * @param patientCode
     * @return
     */
    @GetMapping("/viewUploadImg")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "查看已上传的图片", notes = "查看已上传的图片")
    @ApiImplicitParam(name = "patientCode", value = "体检码")
    public R<ElectroAudiometer> viewUploadImg(String patientCode) {
        String realPath = FileTypePath.FICTITIOUS_PATH;
        ElectroAudiometer audiometer = electroAudiometerService.getOne(new QueryWrapper<ElectroAudiometer>().eq("patientCode", patientCode));
        audiometer.setRealPath(realPath);
        return R.ok(audiometer);
    }


    /**
     * 上传图片
     *
     * @param param
     * @return
     */
    @PostMapping("/UploadImg")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "上传图片-保存", notes = "上传图片-保存")
    public R UploadImg(@RequestBody ElectroAudiometer param) {
        return R.ok(electroAudiometerService.UploadImg(param));
    }

}

