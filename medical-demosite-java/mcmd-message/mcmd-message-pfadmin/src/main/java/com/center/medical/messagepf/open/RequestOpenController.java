package com.center.medical.messagepf.open;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.Branch;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.bean.param.RequestParam;
import com.center.medical.message.service.PlatformProducer;
import com.center.medical.system.service.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 线上与线下通信开放接口
 *
 * @author 路飞船长
 * @since 2024-12-07 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "线上与线下通信开放接口")
@RequestMapping("/open/api/request")
public class RequestOpenController extends BaseController {
    /**
     * 服务对象
     */
    private final PlatformProducer platformProducer;
    private final BranchService branchService;
    /**
     * 线上向线下分中心发送请求的消息队列
     */
    @Value("${rabbitmq.offline.routingKey.request}")
    private String PTL_REQUEST_ROUTING_KEY;
    /**
     * 线上向线下发送请求的消息交换机
     */
    @Value("${rabbitmq.offline.topicExchange.request}")
    private String PTL_REQUEST_TOPIC_EXCHANGE;


    /**
     * 下载数据同步结果
     */
    @PostMapping("/sent")
    @ApiOperation(value = "发送请求到线下", notes = "发送请求到线下")
    public R<String> result(RequestParam param) {
        log.error("发送请求到线下.接收的参数：{}", param);
        String branchId = param.getBranchId();
        if (StringUtils.isBlank(branchId)){
            R.fail("分中心不能为空！");
        }
        List<String> branchIds = Arrays.stream(branchId.split(",")).collect(Collectors.toList());
        //查询分中心列表数据
        List<Branch> list = branchService.list(new LambdaQueryWrapper<Branch>().in(Branch::getBranchId, branchIds).eq(Branch::getIsDelete, 0).eq(Branch::getIsShow, 1));
        for (Branch branch: list) {
            log.error("发送请求到线下.消息队列参数：{}，{}", PTL_REQUEST_TOPIC_EXCHANGE + "." + branch.getJm(), PTL_REQUEST_ROUTING_KEY + branch.getJm());
            try {
                platformProducer.sendMessage(PTL_REQUEST_TOPIC_EXCHANGE + "." + branch.getJm(), PTL_REQUEST_ROUTING_KEY + branch.getJm(), JSONUtil.toJsonStr(param));
            } catch (Exception e) {
                log.error("发送请求到线下：消息队列异常，生产数据失败：jm={}、param={}", branch.getJm(), param);
                R.fail("发送请求到线下：消息队列异常，生产数据失败！");
            }
        }
        return R.ok("发送成功！");
    }

}

