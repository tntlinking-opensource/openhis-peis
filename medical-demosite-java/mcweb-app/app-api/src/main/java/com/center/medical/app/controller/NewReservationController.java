package com.center.medical.app.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.app.bean.app.dto.AppointmentDto;
import com.center.medical.app.bean.dto.*;
import com.center.medical.app.bean.enums.SendType;
import com.center.medical.app.bean.model.BsSettlement;
import com.center.medical.app.bean.model.User;
import com.center.medical.app.bean.param.*;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.config.OsZhongKangConfig;
import com.center.medical.app.security.util.SecurityUtils;
import com.center.medical.app.service.AppointmentService;
import com.center.medical.app.service.BsSettlementService;
import com.center.medical.app.service.SmsLogService;
import com.center.medical.app.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@Api(tags = "新版预约接口")
@RequestMapping("/api/v1/newReservation")
public class NewReservationController {
    /**
     * 服务对象
     */
    private final OsZhongKangConfig osZhongKangConfig;
    private final UserService userService;
    private final AppointmentService appointmentService;
    private final SmsLogService smsLogService;

    private final BsSettlementService bsSettlementService;



    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/homePageList")
    @ApiOperation(value = "首页预约列表", notes = "首页预约列表,status 状态 1待预约,2已预约,3体检中,4体检结束")
    public ResponseEntity<List<HomePageListDto>> list() {
        //获取手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        String phone = user.getUserMobile();
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(phone);
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_NEWRESERVATION_HOMEPAGELIST_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    List<HomePageListDto> body = JSONUtil.toList((String) response.getData(), HomePageListDto.class);
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
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/getNewReData")
    @ApiOperation(value = "获取体检者信息", notes = "获取体检者信息")
    public ResponseEntity<GetNewReDataDto> getNewReData(String patientcode) {
        QuestionnaireParam param = new QuestionnaireParam();
        //获取手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        param.setPhone(user.getUserMobile());
        param.setPatientcode(patientcode);
        log.info("预约获取体检者信息"+param);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_NEWRESERVATION_GETNEWREDATA_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    String data = String.valueOf(response.getData());
                    GetNewReDataDto dto = JSON.parseObject(data, GetNewReDataDto.class);
                    return ResponseEntity.ok(dto);
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
     * 获取可预约时间段列表
     *
     * @param param
     * @return
     */
    @PostMapping("/getAvailableNums")
    @ApiOperation(value = "获取可预约时间段列表")
    public ResponseEntity<List<AppointmentAvailableList>> getAvailableNums(@RequestBody AppointmentAvailableParam param) {

        log.info("获取可预约时间段列表:{}", param);
        if (StringUtils.isBlank(param.getBranchId()) || Objects.isNull(param.getReservationDate())) {
            throw new AppBindException("参数有误！");
        }

        return ResponseEntity.ok(appointmentService.getAvailableNums(param));
    }



    /**
     * 提交预约申请
     *
     * @param data
     * @return
     */
    @PostMapping("/appointmentNow")
    @ApiOperation(value = "立即预约", notes = "立即预约")
    public ResponseEntity<String> appointmentNow(@RequestBody AppointmentDto data) {
        log.info("用户提交预约申请.appointment=" + JSONUtil.toJsonStr(data));
        String userId = SecurityUtils.getUser().getUserId();
        if (appointmentService.check(new CheckAppointmentParam(data.getNumorgresv(),
                data.getPatientcode(), data.getIdcard(), data.getLevelId(), data.getFUsecodehiden()))) {
            //可预约
            data.setUserId(userId);
            String str = appointmentService.appointmentNow(data);
            return ResponseEntity.ok(str);
        } else {
            //已预约
            throw new AppBindException("你已预约，不能再次预约！");
        }
    }



    /**
     * 提交预约申请
     *
     * @param param
     * @return
     */
    @PostMapping("/queryUnit")
    @ApiOperation(value = "单位预约-查询单位", notes = "单位预约-查询单位")
    public ResponseEntity<List<QueryUnitDto>> queryUnit(QueryUnitParam param) {
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_NEWRESERVATION_QUERYUNIT_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    List<QueryUnitDto> body = JSONUtil.toList((String) response.getData(), QueryUnitDto.class);
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
     * 提交预约申请
     *
     * @param param
     * @return
     */
    @PostMapping("/unitReservation")
    @ApiOperation(value = "单位预约-提交", notes = "单位预约-提交")
    public ResponseEntity<String> unitReservation(UnitReservationParam param) {
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        String phone = user.getUserMobile();
        param.setPhone(phone);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_NEWRESERVATION_UNITRESERVATION_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return ResponseEntity.ok(response.getData().toString());
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
     * 提交预约申请
     *
     * @param checkRegisterSmsParam
     * @return
     */
    @PostMapping("/helpAppoint")
    @ApiOperation(value = "帮人预约", notes = "帮人预约，通过user/sendRegisterSms 获取验证码，" +
            "然后在这个接口里进行校验，返回体检者列表")
    public ResponseEntity<List<HelpAppointDto>> helpAppoint(CheckRegisterSmsParam checkRegisterSmsParam) {
        // 每个ip每分钟只能发十个注册的验证码，免得接口被利用
        if (!smsLogService.checkValidCode(checkRegisterSmsParam.getMobile(), checkRegisterSmsParam.getValidCode(), SendType.REGISTER)) {
            // 验证码有误或已过期
            throw new AppBindException("验证码有误或已过期");
        }
        String phone = checkRegisterSmsParam.getMobile();
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(phone);
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_NEWRESERVATION_HELPAPPOINT_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    return ResponseEntity.ok(JSONUtil.toList((String) response.getData(), HelpAppointDto.class));
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
     * 提交预约申请
     *
     * @param param
     * @return
     */
    @PostMapping("/myOrder")
    @ApiOperation(value = "我的订单", notes = "我的订单")
    public ResponseEntity<Object> myOrder(MyOrderParam param) {
        //获取手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        String phone = user.getUserMobile();
        param.setPhone(phone);
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(param));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_NEWRESERVATION_MYORDER_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    // 使用 TypeReference 指定 IPage<MyOrderDto> 类型
                    JSONObject jsonObject = JSON.parseObject(String.valueOf(response.getData()));
                    Object records = jsonObject.get("records");

                    // 解析 JSON 数据
                    List<MyOrderDto> list = JSON.parseArray(String.valueOf(records), MyOrderDto.class);
                    //小程序不让购买订单了，这个暂时注释掉
//                    for (MyOrderDto dto : list) {
//                        //查询小程序订单状态
//                        if ("1".equals(dto.getPurchaseType())){
//                            BsSettlement bsSettlement = bsSettlementService.getInfoById(dto.getPatientbizno());
//                            dto.setPayStatus(bsSettlement.getPayStatus());
//                        }
//                    }
                    jsonObject.put("records",list);
                    return ResponseEntity.ok(jsonObject);
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
     * 取消预约
     *
     * @param param
     * @return
     */
    @PutMapping("/newCancel")
    @ApiOperation(value = "新版取消预约", notes = "新版取消预约")
    public ResponseEntity<String> newCancel(@Valid NewCancelParam param) {
        //获取手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        param.setPhone(user.getUserMobile());
        param.setUserId(SecurityUtils.getUser().getUserId());
        log.info("用户取消预约{}、{}", param);
        String result = appointmentService.newCancel(param) ? "取消成功" : "取消失败";
        return ResponseEntity.ok(result);
    }






    @GetMapping("/orderMarkers")
    @ApiOperation(value = "订单角标", notes = "订单角标")
    public ResponseEntity<OrderMarkersDto> orderMarkers() {
        //获取手机号
        String userId = SecurityUtils.getUser().getUserId();
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUserId, userId));
        String phone = user.getUserMobile();
        //加密
        Map<String, Object> mapParam = osZhongKangConfig.encryptV2(JSONUtil.toJsonStr(phone));
        if (Objects.nonNull(mapParam)) {
            AppResponse response = osZhongKangConfig.sendWithMap(Constant.RT_NEWRESERVATION_ORDERMARKERS_PATH, 2, mapParam);
            if (AppResponse.isSuccess(response)) {
                //获取成功
                if (Objects.nonNull(response.getData())) {
                    OrderMarkersDto dto = JSON.parseObject((String) response.getData(), OrderMarkersDto.class);
                    return ResponseEntity.ok(dto);
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







}

