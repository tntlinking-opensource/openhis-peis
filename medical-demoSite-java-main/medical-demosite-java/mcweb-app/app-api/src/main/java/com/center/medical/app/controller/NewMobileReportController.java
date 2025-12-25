package com.center.medical.app.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.model.FamilyList;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.*;
import com.center.medical.app.common.bean.ReportConfig;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.common.util.HttpUtils;
import com.center.medical.app.common.util.RSAUtil;
import com.center.medical.app.config.OsZhongKangConfig;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.security.util.SecurityUtils;
import com.center.medical.app.service.FamilyListService;
import com.center.medical.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "新手机版报告")
@RequestMapping("/api/v1/newMobileReport")
public class NewMobileReportController {
    /**
     * 服务对象
     */
    private final ShopConfig shopConfig;
    private final OsZhongKangConfig osZhongKangConfig;
    private final UserService userService;

    private final FamilyListService familyListService;
    private final String oldReportList = "http://XXX.XXX.XXX.XXX:8086/mec/inter/report_ex!getReportList.action";
    private final String oldDetails = "http://XXX.XXX.XXX.XXX:8086/mec/inter/report_ex!getReportUrl.action";


    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询列表", notes = "查询列表")
    public ResponseEntity getPage(NewMobileReportParam param) {
        //获取手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        param.setPhone(user.getUserMobile());
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_PHONE_REPORT_NEWLIST_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return ResponseEntity.ok(JSONUtil.toList((String) response.getData(), Object.class));
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
     * @return 单条数据
     */
    @PostMapping("/details")
    @ApiOperation(value = "详情", notes = "根据id查生成报告内容详情")
    public ResponseEntity details(NewMoReportDetailsParam newMoReportDetailsParam) {
        String id = newMoReportDetailsParam.getId();
        //加密
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
                    return ResponseEntity.ok(JSON.parse((String) data));
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
     * 查询老系统报告
     *
     * @param param
     * @return
     */
    @PostMapping("/listOld")
    @ApiOperation(value = "查询老系统报告", notes = "查询老系统报告")
    public Object listOld(ReportListOldParam param) {
        //获取报告加密设置
        ReportConfig reportConfig = shopConfig.getReportConfig();
        param.setAuthCode(reportConfig.getAuthCode());
        //设置手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        param.setPhone(user.getUserMobile());
        //获取绑定的身份证号
        List<FamilyList> list = familyListService.list(new LambdaQueryWrapper<FamilyList>().eq(FamilyList::getUserId, userId));
        if (ObjectUtils.isNotEmpty(list)) {
            List<String> idcardNumbers = list.stream()
                    .map(FamilyList::getIdcardno)
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





//    /**
//     * 查询老系统报告
//     *
//     * @param param
//     * @return
//     */
//    @PostMapping("/listOldCs")
//    @ApiOperation(value = "查询老系统报告（测试）", notes = "查询老系统报告（测试）")
//    public Object listOldCs(ReportListOldParam param) {
//        //获取报告加密设置
//        ReportConfig reportConfig = shopConfig.getReportConfig();
//        param.setAuthCode(reportConfig.getAuthCode());
//        //公钥加密
//        String data = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(param), reportConfig.getPublicKey());
//        //请求参数
//        ListOldReportParam reportParam = new ListOldReportParam(data, reportConfig.getAuthCode());
//        //发送post请求
//        String s = HttpUtils.sendPost(oldReportList, JSONUtil.toJsonStr(reportParam));
//        return ResponseEntity.ok(JSON.parse(s));
//    }
}

