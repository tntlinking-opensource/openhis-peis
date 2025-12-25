package com.center.medical.admin.controller.test;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.finance.bean.model.KdRemittance;
import com.center.medical.finance.service.KdRemittanceService;
import com.center.medical.reception.service.RegisterService;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.system.config.SqlScriptExecutor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 测试
 *
 * @author 路飞
 */
@Slf4j
@RestController
@Api(tags = "测试接口")
@RequiredArgsConstructor
public class TestAllController {

    private final SqlScriptExecutor sqlScriptExecutor;

    private final RegisterService registerService;

    private final Snowflake snowflake;

    private final KdRemittanceService kdRemittanceService;

    /**
     *
     */
    @GetMapping("/open/api/test")
    @ApiOperation(value = "test", notes = "test")
    public R test(String parses1, String parses2) {
        //获取上次执行后更新的银行流水数据
        DateTime parse1 = DateUtil.parse(parses1);
        DateTime parse2 = DateUtil.parse(parses2);
        List<KdRemittance> kdRemittances = kdRemittanceService.list(new LambdaQueryWrapper<KdRemittance>().ge(KdRemittance::getModifydate, parse1)
                .le(KdRemittance::getModifydate,parse2).eq(KdRemittance::getOnline, 0));

        //1.同步最新的银行流水
        //手动执行同步
        for (KdRemittance kr : kdRemittances) {
            //执行同步
            SyncData syncData = new SyncData();
            syncData.setOptType(2);
            syncData.setBizDb("medical_prod");
            syncData.setBizTable("kd_remittance");
            syncData.setBizModifydate(new Date());
            syncData.setIsAll(0);
            syncData.setStatus(0);
            syncData.setBizId(kr.getFid());
            RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
            log.info("执行KdRemittance同步成功: {}", syncData);
        }
        log.info("set测试数据数量：{}", kdRemittances.size());
        return R.ok(kdRemittances);
    }


    /**
     *
     */
    @GetMapping("/open/api/test1")
    @ApiOperation(value = "test1", notes = "test")
    public R test1(List<Integer> numList) {
        Set<Object> set = RedisSetUtil.queryByRange("wTestSetKey", 0L, null);
        log.info("set测试数据删除前的数量：{}", set.size());
        int i = 0;
        for (Object item1 : set) {
            if (numList.contains(i)) {
                RedisSetUtil.remove("wTestSetKey", item1);
            }
        }
        Set<Object> set1 = RedisSetUtil.queryByRange("wTestSetKey", 0L, null);
        log.info("set测试数据删除后的数量：{}", set1.size());
        return R.ok(set1);
    }

}
