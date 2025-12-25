package com.center.medical.center.admin.controller;

import com.center.medical.center.common.bean.dto.PickupBrDto;
import com.center.medical.center.common.bean.model.PacsResultMiddel;
import com.center.medical.center.common.service.LisPacsService;
import com.center.medical.common.core.domain.R;
import com.center.medical.pacslis.bean.dto.PacsDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * pacs
 * @author xhp
 * @since 2023-03-07 10:55
 */
@RestController
@RequestMapping("/open/api/pacs")
@Api(tags = "pacs")
@RequiredArgsConstructor
@Validated
public class PacsController {
    @Qualifier("lisPacsService")
    private final LisPacsService lisPacsService;

    @GetMapping("/list")
    @ApiOperation(value = "获取pacs结果", notes = "获取pacs结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号", dataType = "string", required = true),
            @ApiImplicitParam(name = "pacsConfigStr", value = "pacs配置", dataType = "string", required = true),
    })
    public R<List<PacsDto>> getResultList(
            @NotEmpty @RequestParam String patientcode
            ,@NotEmpty @RequestParam String pacsConfigStr
    ) {
        return R.ok(lisPacsService.selectPacsList(patientcode,pacsConfigStr));
    }


    /**
     * 抓取中间库职业的体检号
     * @return
     */
    @GetMapping("/pickupBr")
    @ApiOperation(value = "抓取中间库职业的体检号", notes = "抓取中间库职业的体检号")
    public R<List<PickupBrDto>> pickupBr() {
        return R.ok(lisPacsService.pickupBr());
    }



    /**
     * 设置是否核收标志
     * @return
     */
    @GetMapping("/setFTransfered")
    @ApiOperation(value = "设置是否核收标志", notes = "设置是否核收标志")
    public R setFTransfered(@RequestParam List<String> patientcodes) {
        return R.ok(lisPacsService.setFTransfered(patientcodes));
    }


    /**
     * 保存pacs结果
     * @return
     */
    @PostMapping("/saveResult")
    @ApiOperation(value = "保存pacs结果", notes = "保存pacs结果")
    public R saveResult(@RequestBody List<PacsResultMiddel> pacsResult) {
        return R.ok(lisPacsService.saveResult(pacsResult));
    }
}
