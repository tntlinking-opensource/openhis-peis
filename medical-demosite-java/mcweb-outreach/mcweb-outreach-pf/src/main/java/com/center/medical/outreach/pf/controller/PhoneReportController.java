package com.center.medical.outreach.pf.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.dto.ReportListDto;
import com.center.medical.bean.model.MobileReportParam;
import com.center.medical.bean.model.NewMoReportDetailsParam;
import com.center.medical.bean.model.NewMobileReportParam;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.bean.param.LastAccessParam;
import com.center.medical.bean.param.MobileDetailsParam;
import com.center.medical.bean.param.ValidationCodeParam;
import com.center.medical.bean.vo.ValidationCodeVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.report.bean.vo.ReportConfigVo;
import com.center.medical.report.service.MobileReportService;
import com.center.medical.report.service.ReportConfigService;
import com.center.medical.service.ReportContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 查询手机报告列表
 * 该拦截层之作权限数据认证和数据消毒处理，不对任何业务处理
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "手机报告标准数据接口")
@RequestMapping("/open/api/v2/phoneReport")
public class PhoneReportController extends BaseController {

    /**
     * 服务对象
     */
    private final MobileReportService mobileReportService;
    private final ReportConfigService reportConfigService;
    private final ReportContentService reportContentService;

    /**
     * 查询手机报告列表
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "查询手机报告列表", notes = "查询手机报告列表")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getGroupOrderList(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        //log.info("查询手机报告列表,data：{}", dataStr);
        MobileReportParam mobileReportParam = JSON.parseObject((String) dataStr, MobileReportParam.class);
        log.info("请求参数：{}", mobileReportParam);
        List<ReportListDto> reportList = mobileReportService.getReportList(mobileReportParam.getPhone(), mobileReportParam.getYear());
        return R.ok(JSONUtil.toJsonStr(reportList));
//        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_REPORT_MOBILEREPORT_LIST;
////        String url = "http://localhost:8080" + Constants.RT_REPORT_MOBILEREPORT_LIST;
//        Map<String, Object> paramMap = new HashMap<String, Object>() {{
//            put("phone", mobileReportParam.getPhone());
//            put("year", mobileReportParam.getYear());
//        }};
//        String post = HttpUtil.get(url, paramMap);
//        //log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
//        R result = JSONUtil.toBean(post, R.class);
//        //log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
//        if (R.isError(result)) {
//            return R.fail(result.getCode(), result.getMsg());
//        }
//        return R.ok(JSONUtil.toJsonStr(result.getData()));
    }

    /**
     * 查询手机报告详情
     *
     * @return
     */
    @PostMapping("/details")
    @ApiOperation(value = "查询手机报告详情", notes = "查询手机报告详情")
    public R details(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        //log.info("查询手机报告详情,data：{}", dataStr);
        MobileDetailsParam param = JSON.parseObject((String) dataStr, MobileDetailsParam.class);
        log.info("请求参数：{}", param);
        if (ObjectUtils.isEmpty(param) || StringUtils.isEmpty(param.getId()) || StringUtils.isEmpty(param.getPhone())){
            throw new ServiceException("请输入正确的参数！");
        }
        ReportContent reportContent = mobileReportService.getInfoById(param.getId());
        if (ObjectUtils.isEmpty(reportContent)){
            throw new ServiceException("报告不存在！");
        }
        //查询手机号是否对应
        Boolean b = mobileReportService.checkDetails(reportContent.getPatientcode(),param.getPhone());
        if (!b){
            throw new ServiceException("报告不存在！");
        }
        String content = reportContent.getContent();
        return R.ok(JSONUtil.toJsonStr(content));
//        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_REPORT_MOBILEREPORT_DETAILS;
////        String url = "http://localhost:8080" + Constants.RT_REPORT_MOBILEREPORT_DETAILS;
//        Map<String, Object> paramMap = new HashMap<String, Object>() {{
//            put("id", paramStr);
//        }};
//        String post = HttpUtil.post(url, paramMap);
//        //log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
//        R result = JSONUtil.toBean(post, R.class);
//        //log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
//        if (R.isError(result)) {
//            return R.fail(result.getCode(), result.getMsg());
//        }
//        return R.ok(JSONUtil.toJsonStr(result.getData()));
    }


    /**
     * 查询手机报告详情
     *
     * @return
     */
    @PostMapping("/getBranchConfig")
    @ApiOperation(value = "查询手机报告分中心配置", notes = "查询手机报告分中心配置")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getBranchConfig(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        //log.info("查询手机报告分中心配置,data：{}", dataStr);
        String branchId = (String) dataStr;
        log.info("请求参数：{}", branchId);
        String content = reportConfigService.getBranchConfig(branchId);
        ReportConfigVo vo = StringUtils.isNotEmpty(content) ? JSONObject.parseObject(content, ReportConfigVo.class) : null;
        return R.ok(JSONUtil.toJsonStr(vo));
        //log.info("请求参数：{}", paramStr);
//        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_REPORT_MOBILEREPORT_CONFIG;
////        String url = "http://localhost:8080"  + Constants.RT_REPORT_MOBILEREPORT_CONFIG;
//        Map<String, Object> paramMap = new HashMap<String, Object>() {{
//            put("branchId", paramStr);
//        }};
//        String post = HttpUtil.post(url, paramMap);
//        //log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
//        R result = JSONUtil.toBean(post, R.class);
//        //log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
//        if (R.isError(result)) {
//            return R.fail(result.getCode(), result.getMsg());
//        }
//        return R.ok(JSONUtil.toJsonStr(result.getData()));
    }


    /**
     * 查询手机报告列表
     *
     * @return
     */
    @PostMapping("/validationCode")
    @ApiOperation(value = "校验验证码并返回列表数据", notes = "校验验证码并返回列表数据")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R validationCode(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        log.info("校验验证码并返回列表数据,data：{}", dataStr);
        ValidationCodeParam param = JSON.parseObject((String) dataStr, ValidationCodeParam.class);
        log.info("请求参数：{}", param);
        ValidationCodeVo vo = mobileReportService.validationCode(param);
        return R.ok(JSONUtil.toJsonStr(vo));
//        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_REPORT_MOBILEREPORT_VALIDATIONCODE;
////        String url = "http://localhost:8080" + Constants.RT_REPORT_MOBILEREPORT_VALIDATIONCODE;
//        String s = HttpUtils.sendPost(url, JSONUtil.toJsonStr(validationCodeParam));
//        //log.info("请求地址及参数：{}、{}、{}", url, validationCodeParam, s);
//        R result = JSONUtil.toBean(s, R.class);
//        //log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
//        if (R.isError(result)) {
//            return R.fail(result.getCode(), result.getMsg());
//        }
//        return R.ok(result.getData());
    }


    /**
     * 查询手机报告列表
     *
     * @return
     */
    @PostMapping("/lastAccess")
    @ApiOperation(value = "分享报告-访问次数和ip", notes = "分享报告-访问次数和ip")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R lastAccess(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        String id = (String) dataStr;
        LastAccessParam param = new LastAccessParam();
        param.setId(id);
        log.info("请求参数：{}", param);
        Boolean b = mobileReportService.lastAccess(param);
        return R.ok(b);
//        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_REPORT_MOBILEREPORT_LASTACCESS;
////        String url = "http://localhost:8080" + Constants.RT_REPORT_MOBILEREPORT_LASTACCESS;
//        String s = HttpUtils.sendPost(url, JSONUtil.toJsonStr(validationCodeParam));
//        //log.info("请求地址及参数：{}、{}、{}", url, validationCodeParam, s);
//        R result = JSONUtil.toBean(s, R.class);
//        //log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
//        if (R.isError(result)) {
//            return R.fail(result.getCode(), result.getMsg());
//        }



    }






    /**
     * 查询手机报告列表
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/newList")
    @ApiOperation(value = "新版小程序查询手机报告列表", notes = "新版小程序查询手机报告列表")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getNewReportList(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        NewMobileReportParam mobileReportParam = JSON.parseObject((String) dataStr, NewMobileReportParam.class);
        log.info("请求参数：{}", mobileReportParam);
        List<ReportListDto> reportList = mobileReportService.getNewReportList(mobileReportParam);
        return R.ok(JSONUtil.toJsonStr(reportList));
    }





    /**
     * 查询手机报告详情
     *
     * @return
     */
    @PostMapping("/newDetails")
    @ApiOperation(value = "查询手机报告详情", notes = "查询手机报告详情")
    public R newDetails(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        NewMoReportDetailsParam param = JSON.parseObject((String) dataStr, NewMoReportDetailsParam.class);
        log.info("请求参数：{}", param);
        ReportContent reportContent = reportContentService.getOne(new LambdaQueryWrapper<ReportContent>()
                .eq(ReportContent::getId, param.getId())
                .eq(ReportContent::getPatientcode, param.getPatientcode())
        );
        if (ObjectUtils.isEmpty(reportContent)){
            throw new ServiceException("参数错误");
        }
        String content = reportContent.getContent();
        return R.ok(JSONUtil.toJsonStr(content));
    }
}

