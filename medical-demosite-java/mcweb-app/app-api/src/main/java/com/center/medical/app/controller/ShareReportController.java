package com.center.medical.app.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.center.medical.app.bean.param.ValidationCodeParam;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.config.OsZhongKangConfig;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.service.UserIdcardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "分享报告")
@RequestMapping("/shareReport")
public class ShareReportController {
    /**
     * 服务对象
     */
    private final ShopConfig shopConfig;
    private final MapperFacade mapperFacade;
    private final OsZhongKangConfig osZhongKangConfig;
    private final UserIdcardService userIdcardService;
    private final String oldReportList = "http://XXX.XXX.XXX.XXX:8086/mec/inter/report_ex!getReportList.action";
    private final String oldDetails = "http://XXX.XXX.XXX.XXX:8086/mec/inter/report_ex!getReportUrl.action";




    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("/details")
    @ApiOperation(value = "详情", notes = "根据id查生成报告内容详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public Object selectOne(String id) {
        log.info("查询手机报告详情:{}", id);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(id);
        log.info("加密后的数据:" + mapParam);
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_PHONE_REPORT_DETAILS_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    Object data = response.getData();
                    return JSON.parse((String) data);
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
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/validationCode")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "校验验证码并返回列表数据", notes = "校验验证码并返回列表数据")
    public Object getPage(ValidationCodeParam param) {
        log.info("校验验证码并返回列表数据:{}", param);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        log.info("加密后的数据:" + mapParam);
        if (Objects.nonNull(mapParam)) {
            //发送请求
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_REPORT_MOBILEREPORT_VALIDATIONCODE, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return JSON.parse((String) response.getData());
                } else {
                    throw new AppBindException(AppHttpStatus.SUCCESS, "暂无数据");
                }
            } else {
                throw new AppBindException(response.getMsg());
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }
    }


    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/lastAccess")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分享报告-访问次数和ip", notes = "访问次数加一，保存最后一次的ip和时间")
    public Object lastAccess(String id) {
        log.info("分享报告-访问次数和ip:{}", id);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(id);
        log.info("加密后的数据:" + mapParam);
        if (Objects.nonNull(mapParam)) {
            //发送请求
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_REPORT_MOBILEREPORT_LASTACCESS, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return response.getData();
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
     * 通过主键查询单条数据
     *
     * @param branchId 分中心id
     * @return 单条数据
     */
    @GetMapping("/getBranchConfig")
    @ApiOperation(value = "获取分中心报告配置", notes = "获取分中心报告配置")
    @ApiImplicitParam(name = "branchId", value = "分中心id")
    public Object getBranchConfig(String branchId) {
        log.info("查询获取分中心报告配置:{}", branchId);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(branchId);
        log.info("加密后的数据:" + mapParam);
        if (Objects.nonNull(mapParam)) {
            //发送请求
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_PHONE_REPORT_BRANCHCONFIG_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    Object data = response.getData();
                    return JSON.parse((String) data);
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

