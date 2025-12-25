package com.center.medical.pacslis.controller.pacs;

import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.pacslis.service.PacsInterfaceService;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * pacs接口对接
 * @author xhp
 * @since 2023-03-07 9:32
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "pacs接口对接")
@RequestMapping("/pacs/pacsInterface")
@Validated
public class PacsInterfaceController  extends BaseController {
    private final PacsInterfaceService pacsInterfaceService;
    private final ISysBranchService iSysBranchService;

//    @PostMapping("/receive")
//    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
//    @ApiOperation(value = "获取pacs结果", notes = "获取pacs结果")
//    public R receive(@RequestBody PacsReceiveParam pacsReceiveParam) {
//        if (StringUtils.isBlank(pacsReceiveParam.getPatientcode())) {
//            return R.fail("体检号不能为空！");
//        }
//        return pacsInterfaceService.receive(pacsReceiveParam.getPatientcode());
//    }

    @PostMapping("/receive")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    @ApiOperation(value = "获取pacs结果", notes = "获取pacs结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", required = true)
    })
    public R receive(@RequestParam @NotEmpty String patientcode) {
        patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
        return pacsInterfaceService.receive(patientcode);
    }
}
