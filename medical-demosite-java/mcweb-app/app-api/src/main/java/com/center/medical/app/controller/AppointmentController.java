package com.center.medical.app.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.app.bean.app.dto.AppointmentDto;
import com.center.medical.app.bean.dto.AppointmentAvailableList;
import com.center.medical.app.bean.dto.CancelAppointmentDto;
import com.center.medical.app.bean.dto.GroupOrderDto;
import com.center.medical.app.bean.model.Appointment;
import com.center.medical.app.bean.model.Reservation;
import com.center.medical.app.bean.param.AppointmentAvailableParam;
import com.center.medical.app.bean.param.AppointmentParam;
import com.center.medical.app.bean.param.CheckAppointmentParam;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.ServerResponse;
import com.center.medical.app.common.response.ServerResponseEntity;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.security.util.SecurityUtils;
import com.center.medical.app.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 体检预约
 *
 * @author 路飞船长
 * @since 2023-03-27 18:53:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检预约")
@RequestMapping("/api/v1/appointment")
public class AppointmentController {
    /**
     * 服务对象
     */
    private final AppointmentService appointmentService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParam 分页参数
     * @param param     查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询体检预约")
    public ServerResponse<IPage<Appointment>> getPage(PageParam pageParam, AppointmentParam param) {
        param.setUserId(SecurityUtils.getUser().getUserId());
        return ServerResponseEntity.success(this.appointmentService.getPage(pageParam, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation(value = "预约详情", notes = "根据id查体检预约详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public ServerResponse<Appointment> selectOne(@PathVariable Long id) {
        String userId = SecurityUtils.getUser().getUserId();
        Appointment appointment = appointmentService.getOne(new LambdaQueryWrapper<Appointment>().
                eq(Appointment::getId, id).
                eq(Appointment::getUserId, userId));
        return ServerResponseEntity.success(appointment);
    }

    /**
     * 根据手机号获取我的待预约团体订单列表
     *
     * @param phone 客户的备单中的手机号
     * @return
     */
    @PostMapping("/getGroupOrderList/{phone}")
    @ApiOperation(value = "获取我的待预约团体订单", notes = "根据手机号获取我的待预约团体订单列表")
    @ApiImplicitParam(name = "phone", value = "客户的备单中的手机号")
    public ServerResponse<List<GroupOrderDto>> getGroupOrderList(@PathVariable String phone) {

        return ServerResponseEntity.success(appointmentService.getGroupOrderList(phone));
    }


    /**
     * 获取个检订单
     *
     * @param phone 客户的备单中的手机号
     * @return
     */
    @PostMapping("/getPersonList/{phone}")
    @ApiOperation(value = "获取个检订单", notes = "获取个检订单")
    @ApiImplicitParam(name = "phone", value = "客户的备单中的手机号")
    public ServerResponse<List<GroupOrderDto>> getPersonList(@PathVariable String phone) {
        return ServerResponseEntity.success(appointmentService.getPersonList(phone));
    }


    /**
     * 获取我的个检订单列表（来自康淘）
     *
     * @return
     */
    @PostMapping("/getPersonalOrderList/{phone}")
    @ApiOperation(value = "获取我的个检订单列表", notes = "获取我的个检订单列表")
    public ServerResponse<?> getPersonalOrderList() {

        return ServerResponseEntity.success(null);
    }

    /**
     * 获取可预约时间段列表
     *
     * @param param
     * @return
     */
    @PostMapping("/getAvailableNums")
    @ApiOperation(value = "获取可预约时间段列表")
    public ServerResponse<List<AppointmentAvailableList>> getAvailableNums(@RequestBody AppointmentAvailableParam param) {

        log.info("获取可预约时间段列表:{}", param);
        if (StringUtils.isBlank(param.getBranchId()) || Objects.isNull(param.getReservationDate())) {
            throw new AppBindException("参数有误！");
        }

        return ServerResponseEntity.success(appointmentService.getAvailableNums(param));
    }

    /**
     * 提交预约申请
     *
     * @param data
     * @return
     */
    @PostMapping("/apply")
    @ApiOperation(value = "提交预约申请", notes = "提交预约申请信息")
    public ResponseEntity<String> apply(@RequestBody AppointmentDto data) {
        System.out.println("用户提交预约申请.appointment=" + JSONUtil.toJsonStr(data));
        String userId = SecurityUtils.getUser().getUserId();
        if (appointmentService.check(new CheckAppointmentParam(data.getNumorgresv(),
                data.getPatientcode(), data.getIdcard(), data.getLevelId(), data.getFUsecodehiden()))) {
            //可预约
            data.setUserId(userId);
            return ResponseEntity.ok(appointmentService.apply(data));

        } else {
            //已预约
            throw new AppBindException("你已预约，不能再次预约！");
        }
    }

    /**
     * 取消预约
     *
     * @param param
     * @return
     */
    @PutMapping("/cancel")
    @ApiOperation(value = "取消预约", notes = "取消预约")
    public ResponseEntity<String> cancel(@Valid CancelAppointmentDto param) {
        log.info("用户取消预约{}、{}", param);
        String userId = SecurityUtils.getUser().getUserId(); //SecurityUtils.getUser().getUserId() "0e8afc06b65c4bf5826e5a2c8047f9d5";
        String result = appointmentService.cancel(param, userId) ? "取消成功" : "取消失败";

        return ResponseEntity.ok(result);
    }



    /**
     * 根据体检号获取预约详情
     *
     * @param patientcode 体检号
     * @return
     */
    @PostMapping("/getReservationByCode")
    @ApiOperation(value = "根据体检号获取预约详情", notes = "根据体检号获取预约详情")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public ResponseEntity<Reservation> getReservationByCode(String patientcode) {
        Reservation reservation = appointmentService.getReservationByCode(patientcode);
        return ResponseEntity.ok(reservation);
    }




    /**
     * 根据体检号获取预约详情
     *
     * @param param 体检号
     * @return
     */
    @PostMapping("/personApply")
    @ApiOperation(value = "个检提交预约申请", notes = "个检提交预约申请")
    public ResponseEntity<String> personApply(AppointmentDto param) {
        System.out.println("个检提交预约申请:" + JSONUtil.toJsonStr(param));
        String userId = SecurityUtils.getUser().getUserId();
        if (appointmentService.check(new CheckAppointmentParam(param.getNumorgresv(),
                param.getPatientcode(), param.getIdcard(), param.getLevelId(), param.getFUsecodehiden()))) {
            //可预约
            param.setUserId(userId);
            return ResponseEntity.ok(appointmentService.personApply(param));
        } else {
            //已预约
            throw new AppBindException("你已预约，不能再次预约！");
        }
    }

}

