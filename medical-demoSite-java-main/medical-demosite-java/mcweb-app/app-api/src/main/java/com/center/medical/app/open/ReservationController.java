package com.center.medical.app.open;

import cn.hutool.json.JSONUtil;
import com.center.medical.app.bean.model.Appointment;
import com.center.medical.app.common.response.AppResponse;
import com.center.medical.app.common.response.AppResponseEntity;
import com.center.medical.app.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 体检系统预约对接接口
 *
 * @author 路飞船长
 * @since 2023-03-27 18:53:56
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "体检系统预约对接接口")
@RequestMapping("/open/api/v1/reservation")
public class ReservationController {
    /**
     * 服务对象
     */
    private final AppointmentService appointmentService;

    /**
     * 更新预约状态
     *
     * @param request
     * @return
     */
    @PostMapping("/updateStatus")
    @ApiOperation(value = "更新预约状态", notes = "更新预约状态")
    public AppResponse<String> statusUpdate(HttpServletRequest request) {
        log.info("1、用户更新预约状态，data：{}", request.getAttribute("dataStr"));
        Object dataStr = request.getAttribute("dataStr");
        Map<String, Object> params = JSONUtil.toBean(JSONUtil.toJsonStr(dataStr), Map.class);
        log.info("1、用户更新预约状态，params：{}", params);
        String id = (String) params.get("id");
        Integer status = (Integer) params.get("status");
        String reason = (String) params.get("reason");
        if (StringUtils.isBlank(id) || Objects.isNull(status)) {
            return AppResponseEntity.fail("参数不能为空！");
        }

        //查看是否存在
        Appointment appointment = appointmentService.getInfoById(id);
        if (Objects.isNull(appointment)) {
            return AppResponseEntity.fail("预约记录不存在！");
        }
        appointment.setStatus(status);
        appointment.setModifydate(new Date());
        appointment.setFailReasion(reason);
        if (appointmentService.updateById(appointment)) {
            return AppResponseEntity.success("更新成功！");
        }
        return AppResponseEntity.fail("更新失败！");
    }
}

