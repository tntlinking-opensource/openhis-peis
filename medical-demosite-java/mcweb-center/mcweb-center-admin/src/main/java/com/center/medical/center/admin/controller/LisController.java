package com.center.medical.center.admin.controller;

import com.center.medical.center.common.bean.dto.AdiconItemDto;
import com.center.medical.center.common.service.AdiconService;
import com.center.medical.center.common.service.LisPacsService;
import com.center.medical.common.core.domain.R;
import com.center.medical.pacslis.bean.dto.LisDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * lis
 */
@RestController
@RequestMapping("/open/api/lis")
@Api(tags = "lis")
@RequiredArgsConstructor
@Validated
public class LisController {
    @Qualifier("lisPacsService")
    private final LisPacsService lisPacsService;

    private final AdiconService adiconService;

    @GetMapping("/list")
    @ApiOperation(value = "获取lis结果", notes = "获取lis结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", dataType = "string", required = true),
            @ApiImplicitParam(name = "loginid", value = "艾迪康账号", dataType = "string", required = false),
            @ApiImplicitParam(name = "password", value = "艾迪康密码", dataType = "string", required = false),
            @ApiImplicitParam(name = "lisConfig", value = "lis配置", dataType = "string", required = true),
    })
    public R<List<LisDto>> getResultList(
            @NotEmpty @RequestParam String patientcode
            ,@RequestParam(required = false) String loginid
            ,@RequestParam(required = false) String password
            ,@NotEmpty @RequestParam String lisConfig
    ) {
        return R.ok(lisPacsService.selectList(patientcode,loginid,password,lisConfig));
    }

    @GetMapping("/getAdiconList")
    @ApiOperation(value = "获取艾迪康结果", notes = "获取艾迪康结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", dataType = "string", required = true),
    })
    public R<List<AdiconItemDto>> adiconList(@NotEmpty @RequestParam String patientcode,@RequestParam String loginid,@RequestParam String password) {
        List<AdiconItemDto> adicon = adiconService.getAdiconList(patientcode,loginid,password);
        return R.ok(adicon);
    }

    @GetMapping("/getTap")
    @ApiOperation(value = "获取tap结果", notes = "获取tap结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", dataType = "string", required = true)
    })
    public R<List<LisDto>> getTap(@NotEmpty @RequestParam String patientcode) {
        List<LisDto> tap = adiconService.getTap(patientcode);
        return R.ok(tap);
    }
}
