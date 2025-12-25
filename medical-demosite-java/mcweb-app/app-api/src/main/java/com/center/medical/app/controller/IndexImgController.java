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

import com.center.medical.app.bean.model.IndexImg;
import com.center.medical.app.common.bean.Domain;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.service.IndexImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LGH
 */
@Slf4j
@RestController
@Api(tags = "首页轮播图接口")
public class IndexImgController {

    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private IndexImgService indexImgService;
    @Autowired
    private ShopConfig shopConfig;

    @GetMapping("/indexImgs/{branchId}")
    @ApiOperation(value = "首页轮播图", notes = "获取首页轮播图列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "branchId", value = "分中心ID", required = true, dataType = "String"),
    })
    public ResponseEntity<List<IndexImg>> indexImgs(@PathVariable("branchId") String branchId) {
        //轮播图暂时只用东区的
        branchId = "2";
        List<IndexImg> indexImgList = indexImgService.listIndexImgsByBranchId(branchId);
        return ResponseEntity.ok(indexImgList);
    }


    @GetMapping("/getDomain")
    @ApiOperation(value = "获取Domain", notes = "获取Domain")
    public ResponseEntity<Domain> getDomain() {
        Domain domain = shopConfig.getDomain();
        return ResponseEntity.ok(domain);
    }
}
