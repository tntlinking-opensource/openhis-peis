package com.center.medical.app.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.dto.QuestionSaOrUpDto;
import com.center.medical.app.bean.model.Questionnaire;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.QuestionnaireParam;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.config.OsZhongKangConfig;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.security.util.SecurityUtils;
import com.center.medical.app.service.UserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * 小程序问卷(Questionnaire)接口
 *
 * @author ay
 * @since 2023-12-05 08:57:21
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "小程序问卷")
@RequestMapping("/api/v1/questionnaire")
public class QuestionnaireController{
    /**
     * 服务对象
     */
    private final MapperFacade mapperFacade;

    private final ShopConfig shopConfig;
    private final OsZhongKangConfig osZhongKangConfig;
    private final UserService userService;


    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    @ApiOperation(value = "添加或更新", notes = "添加或更新")
    @ApiOperationSupport(ignoreParameters = {"questionnaire.id"})
    public ResponseEntity saOrUp(@RequestBody Questionnaire param) {
        //获取手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        param.setPhone(user.getUserMobile());
        log.info("问卷添加或更新参数:{}", param);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        log.info("加密后的数据:" + mapParam);
        if (Objects.nonNull(mapParam)) {
            //发送请求
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_QUESTIONNAIRE_SAORUP, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    QuestionSaOrUpDto dto = new QuestionSaOrUpDto();
                    dto.setId(response.getData().toString());
                    return ResponseEntity.ok(dto);
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                System.out.println("请求失败!");
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        } else {
            System.out.println("加密失败!");
            throw new AppBindException("获取失败，请稍后再重试！");
        }
    }




    /**
     * 获取历史问卷
     *
     * @return 所有数据
     */
    @PostMapping("/getHistory")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取历史问卷", notes = "获取历史问卷")
    public ResponseEntity getPage() {
        //获取手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        String phone = user.getUserMobile();
        log.info("获取历史问卷:{}", phone);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(phone);

        if (Objects.nonNull(mapParam)) {
            //发送请求
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_QUESTIONNAIRE_GETHISTORY, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return ResponseEntity.ok(JSONUtil.toList((String) response.getData(), Questionnaire.class));
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
     * 获取历史问卷
     *
     * @return 所有数据
     */
    @PostMapping("/details")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取详情", notes = "获取详情")
    public ResponseEntity details(QuestionnaireParam param) {
        //获取手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        param.setPhone(user.getUserMobile());
        if (ObjectUtils.isEmpty(param.getType()))param.setType(0);
        log.info("获取问卷详情:{}", param);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            //发送请求
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_QUESTIONNAIRE_DETAILS, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    String data = String.valueOf(response.getData());
                    if (StringUtils.isNotEmpty(data) && !"null".equals(data)){
                        return ResponseEntity.ok(JSON.parseObject(data,Questionnaire.class));
                    }else {
                        return ResponseEntity.ok(null);
                    }
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                System.out.println("请求失败!");
                throw new AppBindException("获取失败，请稍后再重试！");
            }
        } else {
            System.out.println("加密失败!");
            throw new AppBindException("获取失败，请稍后再重试！");
        }
    }

}
