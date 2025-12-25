package com.center.medical.system.controller.version;

import com.center.medical.common.bean.dto.DeployVersionDto;
import com.center.medical.common.bean.param.AutoDeploySaveRecordParam;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.file.FileUtils;
import com.center.medical.system.service.DeployVersionBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xhp
 * @since 2023-11-15 9:01
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "版本控制-更新部署")
@RequestMapping("/open/api/system/deploy")
@Validated
public class AutoDeployController {
    private final DeployVersionBranchService deployVersionBranchService;

    @GetMapping("/version/get")
    @ApiOperation(value = "获取更新版本信息", notes = "获取更新版本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ksIp", value = "科室Ip", required = true),
            @ApiImplicitParam(name = "updateType", value = "更新类型 详见com.center.medical.bean.enums.DeployType", required = true),
    })
    public R<DeployVersionDto> getVersion(@RequestParam String ksIp, @RequestParam int updateType) {
        return R.ok(deployVersionBranchService.getVersion(ksIp, updateType));
    }

    /**
     * 下载jar包
     *
     * @param fileName 文件名称
     */
    @GetMapping("/jar/download")
    @ApiOperation(value = "下载jar包", notes = "下载jar包")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", value = "jar包路径", required = false),
    })
    public void fileDownload(@RequestParam String fileName, HttpServletResponse response) {
        try {
            if (StringUtils.contains(fileName, "..") || !fileName.endsWith(".jar")) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = ZhongkangConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 保存更新记录
     *
     * @param
     * @return
     */
    @PostMapping("/record/save")
    @ApiOperation(value = "保存更新记录", notes = "审核、保存")
    public R saveRecord(@Validated @RequestBody AutoDeploySaveRecordParam autoDeploySaveRecordParam) {
        deployVersionBranchService.saveRecord(autoDeploySaveRecordParam);
        return R.ok();
    }
}
