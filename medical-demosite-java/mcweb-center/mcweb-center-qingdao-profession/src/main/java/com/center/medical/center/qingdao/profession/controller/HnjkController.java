package com.center.medical.center.qingdao.profession.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.center.medical.center.qingdao.profession.entity.dto.QjkUploadDto;
import com.center.medical.center.qingdao.profession.service.HnService;
import com.center.medical.center.qingdao.profession.service.LoginService;
import com.center.medical.center.qingdao.profession.service.OccupationalHealthArchivesService;
import com.center.medical.center.qingdao.profession.service.QjkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 上传区疾控
 *
 * @author ay
 * @since 2024-05-07 15:54:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "上传淮南疾控")
@RequestMapping("hnjk")
public class HnjkController {
    /**
     * 服务对象
     */
    private final HnService hnService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传", notes = "上传")
    public void upload(@RequestBody QjkUploadDto param) {
        DateTime start = DateUtil.parse(param.getStartDate(), "yyyy-MM-dd HH:mm:ss");
        DateTime end = DateUtil.parse(param.getEndDate(), "yyyy-MM-dd HH:mm:ss");
        hnService.upload(start,end);
    }




}
