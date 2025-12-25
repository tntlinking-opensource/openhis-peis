package com.center.medical.messagepf.config;

import cn.hutool.json.JSONUtil;
import com.center.medical.bean.param.RequestParam;
import com.center.medical.message.constant.MessageConstant;
import com.center.medical.system.service.ISysBranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 线上消息队列-消费者
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:58
 */
@Slf4j
@Component
public class PlatformConsumer {
    @Autowired
    private ISysBranchService iSysBranchService;


    /**
     * 消费线下分中心的请求
     *
     * @param message
     */
    @RabbitListener(queues = MessageConstant.LC_REQUEST_QUEUE)
    public void receiveSyncData(String message) {
        log.info("--消费线下系统的请求message：{}", message);
        RequestParam params = null;
        try {
            params = JSONUtil.toBean(message, RequestParam.class);
        } catch (Exception e) {
            log.error("消费线下系统的请求失败，数据不合法:{}、{}", message, e.getMessage());
        }
        if (Objects.nonNull(params)) {
            if (iSysBranchService.isOpened(params.getBranchId())) {
                log.error("消费线下系统的请求失败，分中心不存在或者已经禁用！分中心：{}", params.getBranchId());
            } else {
                try {
                    //执行业务

                } catch (Exception e) {
                    log.error("接消费线下系统的请求失败:{}、{}", message, e);
                }
            }
        }
    }

}
