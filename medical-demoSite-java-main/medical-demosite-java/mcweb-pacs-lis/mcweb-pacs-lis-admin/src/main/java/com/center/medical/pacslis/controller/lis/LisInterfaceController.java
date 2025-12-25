package com.center.medical.pacslis.controller.lis;

import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.pacslis.bean.param.LisReceiveParam;
import com.center.medical.pacslis.service.LisInterfaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * lis接口对接
 *
 * @author xhp
 * @since 2023-02-20 8:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "lis接口对接")
@RequestMapping("/lis/lisInterface")
@Validated
public class LisInterfaceController extends BaseController {
    private final LisInterfaceService lisInterfaceService;

    @PostMapping("/receive")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    @ApiOperation(value = "获取lis结果", notes = "获取lis结果")
    public R receive(@RequestBody LisReceiveParam lisReceiveParam) {

        if (StringUtils.isBlank(lisReceiveParam.getPatientcode())) {
            return R.fail("体检号不能为空！");
        }
        return lisInterfaceService.receive(lisReceiveParam.getPatientcode());

    }




    @PostMapping("/getAdiconList")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    @ApiOperation(value = "获取艾迪康结果", notes = "获取艾迪康结果")
    public R getAdiconList(@RequestBody LisReceiveParam lisReceiveParam) {

        if (StringUtils.isBlank(lisReceiveParam.getPatientcode())) {
            return R.fail("体检号不能为空！");
        }
        return lisInterfaceService.getAdiconList(lisReceiveParam.getPatientcode());

    }



    @PostMapping("/getTap")
    @RepeatSubmit(interval = 3000, message = "正在获取中，请稍等...")
    @ApiOperation(value = "获取Tap结果", notes = "获取Tap结果")
    public R getTap(@RequestBody LisReceiveParam lisReceiveParam) {

        if (StringUtils.isBlank(lisReceiveParam.getPatientcode())) {
            return R.fail("体检号不能为空！");
        }
        return lisInterfaceService.getTap(lisReceiveParam.getPatientcode());

    }
}
