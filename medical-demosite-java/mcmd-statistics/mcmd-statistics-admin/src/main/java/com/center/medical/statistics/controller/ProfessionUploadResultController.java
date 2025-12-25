package com.center.medical.statistics.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.statistics.bean.param.ProfessionUploadResultParam;
import com.center.medical.statistics.bean.vo.ProfessionUploadResultVo;
import com.center.medical.statistics.service.ProfessionUploadResultService;
import com.center.medical.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 云平台上传结果
 * @author xhp
 * @since 2023-11-28 10:18
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "职业检查统计-云平台上传结果")
@Slf4j
@RequestMapping("statistics/professionUploadResult")
public class ProfessionUploadResultController extends BaseController{
    private final ProfessionUploadResultService professionUploadResultService;
    private final MapperFacade mapperFacade;
    private final ISysConfigService iSysConfigService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    @Autowired
    private LoadProperties loadProperties;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<ProfessionUploadResultVo>> getPatientList(PageParamSimple pageParamSimple, ProfessionUploadResultParam param) {
        PageParam page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(professionUploadResultService.getPage(page, param));
    }

    @PutMapping("/updateStatus")
    @ApiOperation(value = "重新上传", notes = "重新上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcodes", value = "体检号数组", required = true),
    })
    public R updateStatus(@RequestParam List<String> patientcodes) {
        //修改状态
        professionUploadResultService.updateStatus(patientcodes);

        //调用接口，立刻重传
        if(patientcodes!=null&&patientcodes.size()>0){
            log.info("重新上传开始发送请求！");
            String professionDomain=iSysConfigService.getDomain().getProfessionDomain();
            String url = StringUtils.equals(loadProperties.name, "weifang")? professionDomain+"/wfjk/multi" :professionDomain+"/send/multi";
            if(StrUtil.isNotEmpty(professionDomain)){
                executorService.submit(() -> {
                    try {
                        HttpRequest.post(url)
                                .timeout(10*1000)
                                .contentType(ContentType.JSON.getValue())
                                .body(JSONUtil.toJsonStr(patientcodes))
                                .execute();
                    } catch (Exception e) {
                        log.error("重新上传请求失败:{}", JSONUtil.toJsonStr(e));
                    }

                });
            }
        }

        return R.ok();
    }

    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "云平台上传结果", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "导出", notes = "导出云平台上传结果")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProfessionUploadResultParam param) {
        List<ProfessionUploadResultVo> list=professionUploadResultService.getList(param);
        ExcelUtil<ProfessionUploadResultVo> util = new ExcelUtil<>(ProfessionUploadResultVo.class);
        util.exportExcel(response, list, "云平台上传结果");
    }
}
