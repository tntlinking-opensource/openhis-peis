package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.member.bean.model.Consulation;
import com.center.medical.member.bean.param.DCSaOrUpParam;
import com.center.medical.member.service.ConsulationService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 科室/总检咨询(Consulation)表控制层
 *
 * @author ay
 * @since 2023-02-23 09:33:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室列表-科室咨询")
@RequestMapping("abteilung/departmentCon")
public class DepartmentConController extends BaseController {
    /**
     * 服务对象
     */
    private final ConsulationService consulationService;

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("/division")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查科室/总检咨询详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depId", value = "科室id"),
            @ApiImplicitParam(name = "patientcode", value = "体检号")
    })
    public R<Consulation> division(String depId, String patientcode) {
        //科室/总检咨询
        Consulation consulation = consulationService.getOne(new QueryWrapper<Consulation>()
                .eq("dep_id", depId)
                .eq("patientcode", patientcode)
                .eq("doctor_username", SecurityUtils.getUsername())
        );
//        //签名图路径
//        if (consulation != null && consulation.getSignPath() != null) {
//
//            //设置
//            AttachmentConfig con = attachmentConfigService.getOne(new QueryWrapper<AttachmentConfig>()
//                    .eq("id", consulation.getConfigId()));
//            //签名图路径
//            if (ObjectUtils.isNotEmpty(con)) {
//                String signPath = Base64Util.imageToBase64(con.getRealPath() + "/" + consulation.getSignPath());
//                consulation.setSignPath(StringUtils.isNotEmpty(signPath) ? signPath : consulation.getSignPath());
//            }
//
//        }
        return R.ok(consulation);
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "科室咨询添加或修改", notes = "科室咨询添加或修改")
    @Log(title = "科室/总检咨询", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"consulation.id"})
    public R insert(@RequestBody DCSaOrUpParam param) {
        return R.toResult(this.consulationService.DCSaOrUp(param));
    }


}

