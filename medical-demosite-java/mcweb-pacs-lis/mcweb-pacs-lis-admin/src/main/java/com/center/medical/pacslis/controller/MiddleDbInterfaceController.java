package com.center.medical.pacslis.controller;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.pacslis.service.MiddleDbInterfaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 中间库接口对接
 *
 * @author xhp
 * @since 2023-02-28 13:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "中间库接口对接")
@RequestMapping("/pacs_list/middleDbInterface")
public class MiddleDbInterfaceController extends BaseController {
    private final MiddleDbInterfaceService middleDbInterfaceService;

    @PostMapping("/insert")
    @ApiOperation(value = "插入中间库", notes = "插入中间库(数据重发)")
    @ApiImplicitParam(name = "patientcode", value = "体检号", required = true)
    public R insert(@RequestParam String patientcode) {
        middleDbInterfaceService.insert(patientcode);
        return R.ok();
    }
}
