package com.center.medical.messagelc.config;

import cn.hutool.json.JSONUtil;
import com.center.medical.abteilung.service.BoyingBusinessLocalService;
import com.center.medical.bean.param.RequestParam;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.RequestFlag;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.olddata.service.OrPeispatientService;
import com.center.medical.outside.bean.param.BoyingWriteReportParam;
import com.center.medical.service.PeispatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 线下消息队列-消费者
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:58
 */
@Slf4j
@Component
public class LocalConsumer {

    @Autowired
    private PeispatientService peispatientService;
    @Autowired
    private OrPeispatientService orPeispatientService;
    @Autowired
    private BoyingBusinessLocalService boyingBusinessLocalService;

    /**
     * 消费线上向当前分中心发送的请求
     *
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.offline.queue.request}")
    public void consumerOnlineRequest(String message) {
        log.info("消费线上向当前分中心发送的请求message: {}", message);
        RequestParam params = null;
        try {
            params = JSONUtil.toBean(message, RequestParam.class);
        } catch (Exception e) {
            log.error("消费线上向当前分中心发送的请求失败，数据不合法:{}、{}", message, e.getMessage());
        }
        if (Objects.nonNull(params)) {
            if (!ZhongkangConfig.getFzxId().equals(params.getBranchId())) {
                log.error("消费线上向当前分中心发送的请求失败，不是当前分中心{}的请求！消息的分中心{}", ZhongkangConfig.getFzxId(), params.getBranchId());
            } else {
                try {
                    //执行业务
                    deal(params);
                } catch (Exception e) {
                    log.error("消费线上向当前分中心发送的请求失败:{}、{}", message, e);
                }
            }
        }

    }

    public static void main(String[] args) {
        System.out.println(ZhongkangConfig.getFzxId());
        System.out.println(ZhongkangConfig.getFzxId());
    }

    /**
     * 处理请求
     * @param params
     */
    private void deal(RequestParam params) {

        switch (params.getBsFlagNum()){
            case RequestFlag.PUSH_DATA_TO_COO:
                //推送体检者体检数据到第三方
                peispatientService.pushDataToCoo(params);
                //查询推送给第三方的旧系统的体检者数据
//                orPeispatientService.pushOldDataToCoo(params);
                break;
            case RequestFlag.SYNC_DATA_TO_ONL:
                //从线上获取线下体检者数据
                break;
            case RequestFlag.SYNC_RESER_TO_ONL:
                //线上获取线下的银行流水数据
                break;
            case RequestFlag.SENT_BOYING_ELE_RESULT_TO_OFFL:
                //接收线上推送下来的博英心电图的报告
                log.info("接收线上推送下来的博英心电图的报告:{}", params);
                if (StringUtils.isNotBlank(params.getParams())){
                    BoyingWriteReportParam param = JSONUtil.toBean(params.getParams(), BoyingWriteReportParam.class);
                    boyingBusinessLocalService.addResult(param);
                }
                break;
            default:

        }
    }
}
