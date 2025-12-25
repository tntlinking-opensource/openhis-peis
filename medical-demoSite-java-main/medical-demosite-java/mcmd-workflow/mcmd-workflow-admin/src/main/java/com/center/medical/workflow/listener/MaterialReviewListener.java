package com.center.medical.workflow.listener;

import cn.hutool.json.JSONUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.workflow.bean.event.MaterialReviewEvent;
import com.center.medical.workflow.bean.param.MaterialReviewParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author: ay
 * @date: 2024-04-0 20:47
 * @description: 审批流材料审核事件处理
 */
@Slf4j
@Component("materialReviewListener")
@AllArgsConstructor
public class MaterialReviewListener {

    private final CreateorderService createorderService;
    private final MapperFacade mapperFacade;

    @EventListener(MaterialReviewEvent.class)
    public void orderFlowDeal(MaterialReviewEvent event) {
        log.info("开始审核材料：{}", JSONUtil.toJsonStr(event.getParam()));
        MaterialReviewParam param = event.getParam();
        Integer status = param.getStatus();
        Createorder createorder = createorderService.getInfoById(param.getId());
        //只有职业和综合的需要材料审核
        if(ObjectUtils.isNotEmpty(createorder) && createorder.getTjlx() > 0){
            log.info("准备审核材料：{}", JSONUtil.toJsonStr(event.getParam()));
            createorder.setClspzt(status == 2 ? 1 : 2);
            createorder.setClspyj("");
            //材料审批人
            createorder.setClspr(SecurityUtils.getUsername());
            createorderService.updateById(createorder);
        }

    }
}
