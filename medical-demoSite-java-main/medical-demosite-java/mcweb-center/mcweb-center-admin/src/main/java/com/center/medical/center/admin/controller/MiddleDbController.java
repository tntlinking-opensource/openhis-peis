package com.center.medical.center.admin.controller;

import com.center.medical.center.common.service.LisPacsService;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.pacslis.bean.dto.MiddleDbDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 中间库接口
 *
 * @author xhp
 * @since 2023-03-02 9:43
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "中间库接口")
@RequestMapping("/open/api/middleDb")
public class MiddleDbController extends BaseController {
    @Qualifier("lisPacsService")
    private final LisPacsService lisPacsService;


    /**
     * 使用HttpUtils.sendPost调用接口时不能加@RequestBody
     * 但是删除@RequestBody就接收不到
     * 改用Hutool发送请求，不再使用HttpUtils
     *
     * @param middleDbDto
     * @return
     */
    @PostMapping("/insert")
    @ApiOperation(value = "插入中间库", notes = "插入中间库")
    public R insert(@RequestBody MiddleDbDto middleDbDto) {
        lisPacsService.save(middleDbDto);
        return R.ok();
    }
}
