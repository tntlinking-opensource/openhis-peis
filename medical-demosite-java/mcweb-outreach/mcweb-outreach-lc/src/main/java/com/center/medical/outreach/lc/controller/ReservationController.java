package com.center.medical.outreach.lc.controller;

import com.center.medical.common.annotation.IpAuth;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.outreach.bean.dto.ReservationDto;
import com.center.medical.outreach.bean.param.DateParam;
import com.center.medical.outreach.bean.dto.ReportDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预约标准数据接口控制层：1.提供可预约时间段和人数 2.接收预约信息 3.返回预约结果 4.预约状态改变通知（到检核销、预约取消） 5.推送体检报告
 * 该拦截层之作权限数据认证和数据消毒处理，不对任何业务处理
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "预约标准数据接口")
@RequestMapping("/open/api/reservation")
public class ReservationController extends BaseController {

    /**
     * 服务对象
     */
    private final MapperFacade mapperFacade;

    /**
     * 查询可预约信息：提供可预约时间段和人数
     *
     * @param param 查询参数
     */
    @IpAuth
    @PostMapping("/getDate")
    @ApiOperation(value = "查询可预约信息", notes = "查询可预约信息：可根据时间段查询，或者查询全部，提供可预约时间段和人数")
    public R<String> getDate(@RequestBody DateParam param) {
        log.info("查询可预约信息：{}");
        return R.ok(null);
    }

    /**
     * 接收预约信息
     *
     * @param data 接收预约信息
     */
    @IpAuth
    @PostMapping("/add")
    @ApiOperation(value = "接收预约信息", notes = "接收预约信息")
    public R<String> reservation(ReservationDto data) {
        log.info("接收预约信息：{}", data);
        return R.ok(null);
    }

    /**
     * 预约状态改变通知（到检核销、预约取消）（到检核销、预约取消）
     *
     * @param data 接收预约信息
     */
    @IpAuth
    @PostMapping("/update")
    @ApiOperation(value = "预约状态改变通知", notes = "预约状态改变通知（到检核销、预约取消）")
    public R<String> getSyncData(ReservationDto data) {
        log.info("接收预约信息：{}", data);
        return R.ok(null);
    }

    /**
     * 推送体检报告
     *
     * @param data 接收预约信息
     */
    @IpAuth
    @PostMapping("/report")
    @ApiOperation(value = "推送体检报告", notes = "推送体检报告")
    public R<String> report(ReportDto data) {
        log.info("推送体检报告：{}", data);
        return R.ok(null);
    }


}

