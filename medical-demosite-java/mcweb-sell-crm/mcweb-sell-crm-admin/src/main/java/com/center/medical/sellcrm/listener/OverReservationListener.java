package com.center.medical.sellcrm.listener;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.event.OverReservationEvent;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.service.PeispatientService;
import com.center.medical.workflow.bean.model.WorkflowReservationType;
import com.center.medical.workflow.service.WorkflowReservationTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: ay
 * @date: 2024-3-8 12:47
 * @description: 跨级预约审批
 */
@Slf4j
@Component("overReservationListener")
@AllArgsConstructor
public class OverReservationListener {

    private final WorkflowReservationTypeService workflowReservationTypeService;
    private final PeispatientService peispatientService;

    @EventListener(OverReservationEvent.class)
    public void start(OverReservationEvent event) {
        log.info("跨级预约审批：{}", JSONUtil.toJsonStr(event));
        String id = event.getParam().getId();
        Integer status = event.getParam().getStatus();
        if (status == 2){
            //查询关联的预约体检者
            List<WorkflowReservationType> list = workflowReservationTypeService.list(new LambdaQueryWrapper<WorkflowReservationType>()
                    .eq(WorkflowReservationType::getCaseId, id)
            );
            for (WorkflowReservationType workflowReservationType : list) {

                //修改体检者会员等级
                Peispatient peispatient = peispatientService.getByPatientCode(workflowReservationType.getPatientcode());
                peispatient.setIdPatientclass(workflowReservationType.getIdPatientclass());
                peispatientService.updateById(peispatient);

            }
        }
        log.info("跨级预约审批结束");
    }
}
