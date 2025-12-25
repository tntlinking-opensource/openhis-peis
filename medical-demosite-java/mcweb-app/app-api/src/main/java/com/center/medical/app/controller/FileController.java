/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.controller;

import com.center.medical.app.bean.app.dto.ResourcesInfoDto;
import com.center.medical.app.bean.model.AttachFile;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.service.AttachFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * 文件上传
 *
 * @author 路飞船长
 * @since 2023-03-27 18:53:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "文件上传")
@RequestMapping("/api/v1/file")
public class FileController {

    private final AttachFileService attachFileService;
    private final ShopConfig shopConfig;

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传接口", notes = "上传文件，返回文件路径与域名")
    public ResponseEntity<ResourcesInfoDto> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        String fileName = attachFileService.uploadFile(file.getBytes(), file.getOriginalFilename());
        String resourcesUrl = shopConfig.getDomain().getMdResources() + "/";
        ResourcesInfoDto resourcesInfoDto = new ResourcesInfoDto();
        resourcesInfoDto.setResourcesUrl(resourcesUrl);
        resourcesInfoDto.setFilePath(fileName);
        return ResponseEntity.ok(resourcesInfoDto);
    }

    @GetMapping("/getFileById")
    @ApiOperation(value = "根据文件id获取文件信息")
    public ResponseEntity<AttachFile> getFileById(@RequestParam("fileId") Long fileId) {
        AttachFile attachFile = attachFileService.getById(fileId);
        return ResponseEntity.ok(attachFile);
    }
}
