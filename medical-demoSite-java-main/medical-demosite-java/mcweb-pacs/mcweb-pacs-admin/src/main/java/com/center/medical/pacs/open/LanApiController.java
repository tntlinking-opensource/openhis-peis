package com.center.medical.pacs.open;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.annotation.IpAuth;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.data.bean.model.KsIp;
import com.center.medical.data.service.KsIpService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 体检者业务
 */
@RestController
@RequestMapping("/lan")
@Api(tags = "内网开放接口业务")
@RequiredArgsConstructor
@Validated
public class LanApiController {

    private final ISysBranchService iSysBranchService;
    private final PeispatientService peispatientService;
    private final KsIpService ksIpService;

    /**
     * 根据体检号判断体检者是否存在
     *
     * @param patientCode 体检号
     * @param flag        是否补全体检号：0.不需要 1.需要
     * @return
     */
    @IpAuth
    @GetMapping("/patient/isExist/{patientCode}/{flag}")
    @ApiOperation(value = "判断体检者是否存在", notes = "根据体检号判断体检者是否存在")
    @ApiImplicitParams({@ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "flag", value = "是否补全体检号：0.不需要 1.需要")})
    public Boolean isExist(@PathVariable String patientCode, @PathVariable Integer flag) {
        if (flag == 1) {
            //补全体检号
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        }
        // 数据库中没有的体检号
        long count = peispatientService.count(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, patientCode));
        return count > 0;
    }

    /**
     * 根据科室配置Id获取ksip配置信息
     *
     * @param ksIpId 科室配置信息ID
     * @return
     */
    @IpAuth
    @GetMapping("/getKsIp/{ksIpId}")
    @ApiOperation(value = "根据科室配置Id获取ksip配置信息", notes = "根据科室配置Id获取ksip配置信息")
    @ApiImplicitParam(name = "ksIpId", value = "科室配置信息ID")
    public R<KsIp> getKsIp(@PathVariable String ksIpId) {
        // 获取ksip配置信息
        KsIp ksIp = ksIpService.getById(ksIpId);
        return R.ok(ksIp);
    }

    /**
     * 根据科室号获取ksip配置信息
     *
     * @param ksId 科室号
     * @return
     */
    @IpAuth
    @GetMapping("/getKsIpByKsId/{ksId}")
    @ApiOperation(value = "根据科室号获取ksip配置信息", notes = "根据科室号获取ksip配置信息")
    @ApiImplicitParam(name = "ksId", value = "科室号")
    public R<List<KsIp>> getKsIpByKsId(@PathVariable String ksId) {
        // 获取ksip配置信息
        List<KsIp> list = ksIpService.list(new LambdaQueryWrapper<KsIp>().eq(KsIp::getKsId, ksId).eq(KsIp::getStatus, 1));
        return R.ok(list);
    }
}
