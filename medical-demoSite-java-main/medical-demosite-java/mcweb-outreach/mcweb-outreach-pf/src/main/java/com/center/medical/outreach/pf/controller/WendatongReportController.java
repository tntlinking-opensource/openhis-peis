package com.center.medical.outreach.pf.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.HttpStatus;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.outreach.pf.config.WendatongReportConfig;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 文达通查询报告数据接口
 *
 * @author xhp
 * @since 2023-10-23 8:23
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "文达通查询报告数据接口")
@RequestMapping("/open/api/v1/wendatongReport")
public class WendatongReportController extends BaseController {
    private final SystemConfig systemConfig;
    private final ISysConfigService iSysConfigService;

    /**
     * 文达通获取体检号列表
     *
     * @param request
     * @return
     */
    @PostMapping("/patientcode/list")
    @ApiOperation(value = "获取体检号列表", notes = "根据时间段获取体检号列表")
    public R selectPatientcodeList(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        //log.info("文达通获取体检号列表,data：{}", dataStr);

        //判断接口是否开放
        WendatongReportConfig wendatongReportConfig = iSysConfigService.getSysConfigObject(Constants.WENDATONG_REPORT_CONFIG, WendatongReportConfig.class);
        if (wendatongReportConfig == null || wendatongReportConfig.getIsOpen() == null || wendatongReportConfig.getIsOpen().intValue() != 1) {
            return R.fail(HttpStatus.NOT_FOUND, "该功能尚未开放！");
        }

        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_WENDATONG_REPORT_PATIENTCODE_LIST_PATH;
//        String url = "http://localhost:8090" + Constants.RT_WENDATONG_REPORT_PATIENTCODE_LIST_PATH;
        Map map = JSONUtil.toBean((String) dataStr, HashMap.class);

        //只查询开放时间以前的数据
        String endDate = wendatongReportConfig.getEndDate();
        String endDateParam = map.get("endDate") == null ? null : map.get("endDate").toString();
        if (StrUtil.isNotEmpty(endDate)) {
            if (StrUtil.isEmpty(endDateParam) || DateUtil.parse(endDate, "yyyy-MM-dd").before(DateUtil.parse(endDateParam, "yyyy-MM-dd"))) {
                map.put("endDate", endDate);
            }
        }

        String post = HttpRequest.post(url).body(JSONUtil.toJsonStr(map)).contentType(ContentType.JSON.getValue()).timeout(1000 * 20)
                .execute().body();
        //log.info("请求地址及参数：{}、{}、{}", url, map, post);
        R result = JSONUtil.toBean(post, R.class);
        log.info("文达通-从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        if (R.isError(result)) {
            return R.fail(result.getCode(), result.getMsg());
        }
        return R.ok(JSONUtil.toJsonStr(result.getData()));
    }

    /**
     * 获取体检号列表,只查瑞源的,非文达通使用
     *
     * @param request
     * @return
     */
    @PostMapping("/patientcode/ry/list")
    @ApiOperation(value = "获取瑞源体检号列表", notes = "根据时间段获取瑞源体检号列表")
    public R selectPatientcodeRyList(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        //log.info("文达通获取体检号列表,data：{}", dataStr);

        //判断接口是否开放
        WendatongReportConfig wendatongReportConfig = iSysConfigService.getSysConfigObject(Constants.WENDATONG_REPORT_CONFIG, WendatongReportConfig.class);
        if (wendatongReportConfig == null || wendatongReportConfig.getIsRyOpen() == null || wendatongReportConfig.getIsRyOpen().intValue() != 1) {
            return R.fail(HttpStatus.NOT_FOUND, "该功能尚未开放！");
        }

        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_WENDATONG_REPORT_PATIENTCODE_LIST_PATH;
        Map map = JSONUtil.toBean((String) dataStr, HashMap.class);
        map.put("orgName","瑞源");

        //只查询开放时间以前的数据
        String endDate = wendatongReportConfig.getEndDate();
        String endDateParam = map.get("endDate") == null ? null : map.get("endDate").toString();
        if (StrUtil.isNotEmpty(endDate)) {
            if (StrUtil.isEmpty(endDateParam) || DateUtil.parse(endDate, "yyyy-MM-dd").before(DateUtil.parse(endDateParam, "yyyy-MM-dd"))) {
                map.put("endDate", endDate);
            }
        }

        String post = HttpRequest.post(url).body(JSONUtil.toJsonStr(map)).contentType(ContentType.JSON.getValue()).timeout(1000 * 20)
                .execute().body();
        //log.info("请求地址及参数：{}、{}、{}", url, map, post);
        R result = JSONUtil.toBean(post, R.class);
        log.info("文达通-从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        if (R.isError(result)) {
            return R.fail(result.getCode(), result.getMsg());
        }
        return R.ok(JSONUtil.toJsonStr(result.getData()));
    }

    /**
     * 文达通根据体检号获取报告数据
     *
     * @param request
     * @return
     */
    @PostMapping("/report/getByPatientcode")
    @ApiOperation(value = "根据体检号获取报告数据", notes = "根据体检号获取报告数据")
    public R getReportByPatientcode(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        //log.info("文达通根据体检号获取报告数据,data：{}", dataStr);
        String paramStr = (String) dataStr;

        //判断接口是否开放
        WendatongReportConfig wendatongReportConfig = iSysConfigService.getSysConfigObject(Constants.WENDATONG_REPORT_CONFIG, WendatongReportConfig.class);
        if (wendatongReportConfig == null || wendatongReportConfig.getIsOpen() == null || wendatongReportConfig.getIsOpen().intValue() != 1) {
            return R.fail(HttpStatus.NOT_FOUND, "该功能尚未开放！");
        }

        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_WENDATONG_REPORT_PATIENTCODE_GET_BY_PATIENTCODE_PATH;
//        String url = "http://localhost:8090" + Constants.RT_WENDATONG_REPORT_PATIENTCODE_GET_BY_PATIENTCODE_PATH;
        Map<String, Object> paramMap = new HashMap<String, Object>() {{
            put("patientcode", paramStr);
        }};
        String post = HttpRequest.post(url).body(JSONUtil.toJsonStr(paramMap)).contentType(ContentType.JSON.getValue()).timeout(1000 * 20)
                .execute().body();
        //log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
        R result = JSONUtil.toBean(post, R.class);
        log.info("文达通-从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        if (R.isError(result)) {
            return R.fail(result.getCode(), result.getMsg());
        }
        return R.ok(JSONUtil.toJsonStr(result.getData()));
    }

    /**
     * 根据体检号获取报告数据,非文达通使用，只查瑞源的
     *
     * @param request
     * @return
     */
    @PostMapping("/report/ry/getByPatientcode")
    @ApiOperation(value = "根据瑞源体检号获取报告数据", notes = "根据瑞源体检号获取报告数据")
    public R getReportRyByPatientcode(HttpServletRequest request) {
        Object dataStr = request.getAttribute("dataStr");
        //log.info("文达通根据体检号获取报告数据,data：{}", dataStr);
        String paramStr = (String) dataStr;

        //判断接口是否开放
        WendatongReportConfig wendatongReportConfig = iSysConfigService.getSysConfigObject(Constants.WENDATONG_REPORT_CONFIG, WendatongReportConfig.class);
        if (wendatongReportConfig == null || wendatongReportConfig.getIsRyOpen() == null || wendatongReportConfig.getIsRyOpen().intValue() != 1) {
            return R.fail(HttpStatus.NOT_FOUND, "该功能尚未开放！");
        }

        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_WENDATONG_REPORT_PATIENTCODE_GET_BY_PATIENTCODE_PATH;
//        String url = "http://localhost:8090" + Constants.RT_WENDATONG_REPORT_PATIENTCODE_GET_BY_PATIENTCODE_PATH;
        Map<String, Object> paramMap = new HashMap<String, Object>() {{
            put("patientcode", paramStr);
            put("orgName","瑞源");
        }};
        String post = HttpRequest.post(url).body(JSONUtil.toJsonStr(paramMap)).contentType(ContentType.JSON.getValue()).timeout(1000 * 20)
                .execute().body();
        //log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
        R result = JSONUtil.toBean(post, R.class);
        log.info("文达通-从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
        if (R.isError(result)) {
            return R.fail(result.getCode(), result.getMsg());
        }
        return R.ok(JSONUtil.toJsonStr(result.getData()));
    }
}
