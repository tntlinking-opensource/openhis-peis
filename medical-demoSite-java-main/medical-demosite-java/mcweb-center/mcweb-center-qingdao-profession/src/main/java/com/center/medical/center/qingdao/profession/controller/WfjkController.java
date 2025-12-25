package com.center.medical.center.qingdao.profession.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.center.medical.center.qingdao.profession.entity.dto.QjkUploadDto;
import com.center.medical.center.qingdao.profession.entity.persistent.Peispatient;
import com.center.medical.center.qingdao.profession.repository.PeispatientRepository;
import com.center.medical.center.qingdao.profession.service.LoginService;
import com.center.medical.center.qingdao.profession.service.OccupationalHealthArchivesService;
import com.center.medical.center.qingdao.profession.service.QjkService;
import com.center.medical.center.qingdao.profession.service.WfjkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 上传区疾控
 *
 * @author ay
 * @since 2024-05-07 15:54:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "上传潍坊疾控")
@RequestMapping("wfjk")
public class WfjkController {
    /**
     * 服务对象
     */
    private final WfjkService wfjkService;
    private final OccupationalHealthArchivesService occupationalHealthArchivesService;
    private final LoginService loginService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/upload")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "上传", notes = "上传")
    public void upload(@RequestBody QjkUploadDto param) throws Exception {
        DateTime start = DateUtil.parse(param.getStartDate(), "yyyy-MM-dd HH:mm:ss");
        DateTime end = DateUtil.parse(param.getEndDate(), "yyyy-MM-dd HH:mm:ss");
        wfjkService.upload(start,end);
    }


    /**
     * 重新上传
     * @param patientcodes
     */
    @PostMapping("multi")
    @ApiOperation(value = "重新上传", notes = "重新上传")
    public void sendMulti(@RequestBody List<String> patientcodes){
        wfjkService.sendMulti(patientcodes);
    }

}
