package com.center.medical.report.controller;

import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.report.bean.param.CReprotNewDParam;
import com.center.medical.report.bean.param.UploadWordParam;
import com.center.medical.report.service.GroupReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 团检报告审批-生成报告(Report)表控制层
 *
 * @author ay
 * @since 2023-04-21 16:55:19
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "团检报告审批-生成报告")
@RequestMapping("report/groupReport")
public class GroupReportController extends BaseController {
    /**
     * 服务对象
     */
    private final GroupReportService groupReportService;
    private final MapperFacade mapperFacade;

    /**
     * 综合分析生成报告
     *
     * @return 所有数据
     */
    @GetMapping("/createReprotNewD")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "综合分析-生成报告", notes = "综合分析生成报告")
    public R createReprotNewD(CReprotNewDParam param) {
        param.setGenerateName(SecurityUtils.getUsername());
        Boolean b = groupReportService.createReprotNewD(param);
        return R.ok(b);
    }

    /**
     * 上传word
     */
    @PostMapping("/uploadWord")
    @ApiOperation(value = "上传word", notes = "上传word")
    public R uploadWord(UploadWordParam param) throws Exception {
        if (Objects.isNull(param.getFile())) {
            throw new GlobalException("请选择上传文件！");
        }
        String fileName = param.getFile().getOriginalFilename();
        //判断文件后缀
        String endSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (!endSuffix.toLowerCase().endsWith("docx") ) {
            throw new GlobalException("请选择正确的文件类型，必须是以docx结尾！");
        }
        if (StringUtils.isBlank(param.getId())) {
            throw new GlobalException("团检报告id不能为空！");
        }
        Boolean b = groupReportService.uploadWord(param);
        return R.ok(b);
    }


}

