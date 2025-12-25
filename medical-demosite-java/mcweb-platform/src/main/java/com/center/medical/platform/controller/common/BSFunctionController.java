package com.center.medical.platform.controller.common;

import com.center.medical.bean.dto.PatientDuplicate;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 特殊功能接口
 *
 * @author 路飞
 */
@Slf4j
@RestController
@Api(tags = "业务功能接口")
@RequiredArgsConstructor
public class BSFunctionController {

    private final PeispatientService peispatientService;
//    private final ExecutorService executorService = Executors.newFixedThreadPool(8);

    /**
     * 订单名单去重
     *
     * @param orderIds
     * @return
     */
    @GetMapping("/function/order/deduplication/{orderIds}")
    @Log(title = "订单名单去重", businessType = BusinessType.DELETE)
    @ApiOperation(value = "订单名单去重", notes = "订单名单去重，根据体检者名称、手机号、身份证号去重")
    @ApiImplicitParam(name = "orderIds", value = "订单号集合,多个以英文逗号隔开")
    public R deduplication(@PathVariable List<String> orderIds) {

        Map<String, List<PatientDuplicate>> result = new HashMap<>();
        for (String ddh : orderIds) {
            List<PatientDuplicate> list = peispatientService.geDuplicate(ddh);
            result.put(ddh + "==" + list.size(), list);
            // 订单去重
            log.info("需要去重的名单：{}", list.size());
            for (PatientDuplicate item : list) {
                //执行保存操作，线程池设置了一个核心线程和最大线程都为1的线程池，空闲时间为900秒。如果在900秒内没有任务提交，线程池会自动释放线程资源。当有新任务提交时，线程池会重新启动线程来处理任务。
                ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 8, 900, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
                // 提交任务到线程池
                executor.submit(() -> {
                    // 执行任务逻辑
                    peispatientService.deduplication(item);
                });
//                //进入线程池
//                executorService.submit(() -> {
//                    peispatientService.deduplication(item);
//                });
            }
        }
        return R.ok(result);
    }
}
