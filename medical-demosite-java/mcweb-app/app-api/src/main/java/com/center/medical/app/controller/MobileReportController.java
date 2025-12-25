package com.center.medical.app.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.model.UserIdcard;
import com.center.medical.app.bean.param.*;
import com.center.medical.app.common.bean.Domain;
import com.center.medical.app.common.bean.PdfToBase64Dto;
import com.center.medical.app.common.bean.ReportConfig;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.common.util.HttpUtils;
import com.center.medical.app.common.util.PdfUtil;
import com.center.medical.app.common.util.RSAUtil;
import com.center.medical.app.config.OsZhongKangConfig;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.security.util.SecurityUtils;
import com.center.medical.app.service.UserIdcardService;
import com.center.medical.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 手机版报告(ReportContent)接口
 *
 * @author ay
 * @since 2023-08-14 14:54:49
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "手机版报告")
@RequestMapping("/api/v1/mobileReport")
public class MobileReportController {
    /**
     * 服务对象
     */
    private final ShopConfig shopConfig;
    private final OsZhongKangConfig osZhongKangConfig;
    private final UserIdcardService userIdcardService;
    private final UserService userService;
    private final String oldReportList = "http://XXX.XXX.XXX.XXX:8086/mec/inter/report_ex!getReportList.action";
    private final String oldDetails = "http://XXX.XXX.XXX.XXX:8086/mec/inter/report_ex!getReportUrl.action";


    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "查询列表", notes = "查询列表")
    @ApiImplicitParam(name = "phone", value = "手机号")
    public Object getPage(MobileReportParam param) {
        //获取手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        String phone = user.getUserMobile();
        param.setPhone(phone);
//        log.info("查询手机报告列表:{}", param);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
//        log.info("加密后的数据:" + mapParam);
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_PHONE_REPORT_LIST_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return JSONUtil.toList((String) response.getData(), Object.class);
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
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("/details")
    @ApiOperation(value = "详情", notes = "根据id查生成报告内容详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public Object selectOne(String id) {
        //用id查询，手机号作为校验，如果该手机号与报告不相符，就查不出来
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        String phone = user.getUserMobile();
        MobileDetailsParam param = new MobileDetailsParam(phone,id);

        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
//        log.info("加密后的数据:" + mapParam);
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
                throw new AppBindException(response.getMsg());
            }
        } else {
            throw new AppBindException("获取失败，请稍后再重试！");
        }
    }

    /**
     * 将pdf转jep图片的Base64
     *
     * @param remotePath pdf文件地址
     * @return Base64图片列表
     */
    @PostMapping("/pdfToBase64")
    @ApiOperation(value = "将pdf转jep图片的Base64", notes = "将pdf转jep图片的Base64")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "remotePath", value = "pdf文件地址"),
            @ApiImplicitParam(name = "curPage", value = "请求的页码")
    })
    public PdfToBase64Dto pdfToBase64(@RequestParam(name = "remotePath") String remotePath, @RequestParam(name = "curPage") Integer curPage) {
//        log.info("将pdf转jep图片的Base64.remotePath:{}、{}", remotePath, curPage);
        return PdfUtil.pdfToBase64(remotePath, curPage);
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
//        log.info("查询获取分中心报告配置:{}", branchId);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(branchId);
//        log.info("加密后的数据:" + mapParam);
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


    /**
     * 获取分中心报告配置
     *
     * @return
     */
    @GetMapping("/getDomain")
    @ApiOperation(value = "获取资源路径", notes = "获取资源路径")
    public ResponseEntity<Domain> getDomain() {
        //从体检系统中获取可预约时间段列表
        Domain domain = shopConfig.getDomain();
        return ResponseEntity.ok(domain);
    }


    /**
     * 查询老系统报告
     *
     * @param param
     * @return
     */
    @PostMapping("/listOld")
    @ApiOperation(value = "查询老系统报告", notes = "查询老系统报告")
    public Object listOld(ReportListOldParam param) {
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        String phone = user.getUserMobile();
        param.setPhone(phone);
        //获取报告加密设置
        ReportConfig reportConfig = shopConfig.getReportConfig();
        param.setAuthCode(reportConfig.getAuthCode());
        //获取绑定的身份证号
        List<UserIdcard> list = userIdcardService.list(new LambdaQueryWrapper<UserIdcard>().eq(UserIdcard::getUserId, userId));
        if (ObjectUtils.isNotEmpty(list)) {
            List<String> idcardNumbers = list.stream()
                    .map(UserIdcard::getIdcard)
                    .collect(Collectors.toList());
            param.setIdcardno(idcardNumbers);
        } else {
            throw new AppBindException("请先填写身份证号！");
        }
//        log.info("打印一下userId:{}", userId);
        //公钥加密
        String data = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(param), reportConfig.getPublicKey());
        //请求参数
        ListOldReportParam reportParam = new ListOldReportParam(data, reportConfig.getAuthCode());
        //发送post请求
        String s = HttpUtils.sendPost(oldReportList, JSONUtil.toJsonStr(reportParam));
        return ResponseEntity.ok(JSON.parse(s));
    }


    /**
     * 获取老系统详情
     *
     * @param param
     * @return
     */
    @PostMapping("/oldDetails")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取老系统详情", notes = "获取老系统详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public Object oldDetails(OldDetailsParam param) {
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        String phone = user.getUserMobile();
        param.setPhone(phone);
        //获取报告加密设置
        ReportConfig reportConfig = shopConfig.getReportConfig();
        param.setAuthCode(reportConfig.getAuthCode());
        //公钥加密
        String data = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(param), reportConfig.getPublicKey());
        //请求参数
        ListOldReportParam reportParam = new ListOldReportParam(data, reportConfig.getAuthCode());
        //发送post请求
        String s = HttpUtils.sendPost(oldDetails, JSONUtil.toJsonStr(reportParam));
        return ResponseEntity.ok(JSON.parse(s));
    }


}

