package com.center.medical.app.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.center.medical.app.bean.Vo.GetMealDetailsVo;
import com.center.medical.app.bean.model.CreatemealAppType;
import com.center.medical.app.bean.param.GetMealDetailsParam;
import com.center.medical.app.bean.param.GetMealListParam;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.config.OsZhongKangConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 手机版报告(ReportContent)接口
 *
 * @author ay
 * @since 2023-08-14 14:54:49
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "小程序套餐")
@RequestMapping("/createMealApp")
public class CreateMealAppController {
    /**
     * 服务对象
     */
    private final OsZhongKangConfig osZhongKangConfig;




    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/getMealType")
    @ApiOperation(value = "获取套餐分类", notes = "获取套餐分类")
    public ResponseEntity<List<CreatemealAppType>> getMealType() {
        GetMealDetailsParam param = new GetMealDetailsParam();
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_CREATEMEALAPP_GETMEALTYPE_PATH, 2, mapParam);
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
    @PostMapping("/getMealList")
    @ApiOperation(value = "获取套餐列表", notes = "获取套餐列表")
    public ResponseEntity getMealList(GetMealListParam param) {
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_CREATEMEALAPP_GETMEALLIST_PATH, 2, mapParam);
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
    @PostMapping("/getMealDetails")
    @ApiOperation(value = "获取套餐详情", notes = "获取套餐详情")
    public ResponseEntity<GetMealDetailsVo> getMealDetails(GetMealDetailsParam param) {
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_CREATEMEALAPP_GETMEALDETAILS_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData()) && !"null".equals(String.valueOf(response.getData()))) {
                    GetMealDetailsVo getMealDetailsVo = JSON.parseObject((String) response.getData(), GetMealDetailsVo.class);
                    return ResponseEntity.ok(getMealDetailsVo);
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


}

