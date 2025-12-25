package com.center.medical.app.controller;

/**
 * @author: 路飞船长
 * @date: 2024/3/18 12:46
 * @description: 获取推荐商品
 */

import cn.hutool.http.HttpUtil;
import com.center.medical.app.bean.dto.ProductDto;
import com.center.medical.app.bean.param.ProdParam;
import com.center.medical.app.common.bean.HelpCenter;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "推荐商品")
@RequestMapping("/prod")
public class ProductController {

    private final SysConfigService sysConfigService;

    /**
     * 获取推荐商品
     *
     * @param page 分页参数
     * @return
     */
    @GetMapping("/getRecommend")
    @ApiOperation(value = "获取推荐商品列表", notes = "获取推荐商品列表")
    public String getRecommend(PageParam<ProductDto> page, ProdParam param) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("current", page.getCurrent());
        params.put("size", page.getSize());
        params.put("hospitalId", StringUtils.isBlank(param.getHospitalId()) ? "" : param.getHospitalId());
        params.put("priceSort", Objects.isNull(param.getPriceSort()) ? 0 : param.getPriceSort());

        return HttpUtil.get(Constant.KANGTAO_HOST + Constant.KANGTAO_GETRECOMMEND_PATH, params);
    }


    /**
     * 获取推荐商品的店铺列表
     *
     * @return
     */
    @GetMapping("/getRecommendShop")
    @ApiOperation(value = "获取推荐商品的店铺列表", notes = "获取推荐商品的店铺列表")
    public String getRecommendShop() {
        return HttpUtil.get(Constant.KANGTAO_HOST + Constant.KANGTAO_GETRECOMMENDSHOP_PATH);
    }



    /**
     * 获取帮助中心数据
     * @param
     * @return
     */
    @GetMapping("/getHelpCenter")
    @ApiOperation(value = "获取帮助中心数据", notes = "获取帮助中心数据")
    public ResponseEntity<HelpCenter> getHelpCenter() {
        HelpCenter helpCenter = sysConfigService.getSysConfigObject(Constant.HELP_CENTER, HelpCenter.class);
        return ResponseEntity.ok(helpCenter);
    }



    /**
     * 获取配置数据
     * @param value
     * @return
     */
    @GetMapping("/getConfigValue")
    @ApiOperation(value = "获取配置数据", notes = "获取配置数据")
    public ResponseEntity<String> getConfigValue(String value) {
        String result = sysConfigService.getValue(value);
        return ResponseEntity.ok(result);
    }


    public static void main(String[] args) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("current", 1);
        params.put("size", 2);
        params.put("hospitalId", "32");
        params.put("priceSort", 2);

        System.out.println(HttpUtil.get("http://localhost:8086" + Constant.KANGTAO_GETRECOMMEND_PATH, params));

        System.out.println(HttpUtil.get("http://localhost:8086" + Constant.KANGTAO_GETRECOMMENDSHOP_PATH));
    }
}
