package com.center.medical.app.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.center.medical.app.bean.model.AppArticle;
import com.center.medical.app.bean.model.CreatemealAppType;
import com.center.medical.app.bean.param.GetArticleListParam;
import com.center.medical.app.bean.param.GetMealDetailsParam;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.config.OsZhongKangConfig;
import com.center.medical.app.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 小程序文章表(AppArticle)接口
 *
 * @author ay
 * @since 2024-06-15 09:08:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "小程序文章")
@RequestMapping("/appArticle")
public class AppArticleController {

    private final MapperFacade mapperFacade;
    private final OsZhongKangConfig osZhongKangConfig;

    private final PayService payService;
    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/getArticleType")
    @ApiOperation(value = "获取文章分类", notes = "获取文章分类")
    public ResponseEntity<List<CreatemealAppType>> getArticleType() {
        GetMealDetailsParam param = new GetMealDetailsParam();
//        param.setId(SecurityUtils.getUser().getUserId());
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_APPARTICLE_GETARTICLETYPE_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    List<CreatemealAppType> body = JSONUtil.toList((String) response.getData(), CreatemealAppType.class);
                    return ResponseEntity.ok(body);
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }
    }



    /**
     * 获取套餐列表
     *
     * @return 所有数据
     */
    @PostMapping("/getArticleList")
    @ApiOperation(value = "获取文章列表", notes = "获取文章列表")
    public ResponseEntity getArticleList(GetArticleListParam param) {
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_APPARTICLE_GETARTICLELIST_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return ResponseEntity.ok(JSON.parseObject((String) response.getData(), Object.class));
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }
    }




    /**
     * 获取套餐列表
     *
     * @return 所有数据
     */
    @PostMapping("/getArticleDetails")
    @ApiOperation(value = "获取文章详情", notes = "获取文章详情")
    public ResponseEntity getArticleDetails(String id) {
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(id);
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_APPARTICLE_GETARTICLEDETAILS_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData()) && !"null".equals(String.valueOf(response.getData()))) {
                    AppArticle appArticle = JSON.parseObject((String) response.getData(), AppArticle.class);
                    return ResponseEntity.ok(appArticle);
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }
    }




    /**
     * 获取套餐列表
     *
     * @return 所有数据
     */
    @PostMapping("/getJumpUrl")
    @ApiOperation(value = "获取跳转路径", notes = "获取跳转路径")
    public String getJumpUrl() {
        log.info("开始获取跳转路径");
        return payService.getJumpUrl();
    }
}
