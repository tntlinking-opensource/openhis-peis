package com.center.medical.platform.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.sync.bean.model.SyncData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
public class TestController {


    private final Snowflake snowflake;

    /**
     *
     */
    @GetMapping("/open/api/test")
    @ApiOperation(value = "test", notes = "test")
    public R test(Long flag) {

        for (Long i = flag; i < 100; i++) {
            SyncData syncData = new SyncData();
            syncData.setIsAll(0);
            syncData.setId(i);
            syncData.setStatus(0);
            syncData.setBizDb("dd");
            syncData.setBizId(snowflake.nextIdStr());
            syncData.setBizTable("dd");
            syncData.setOptType(2);
            syncData.setRemark("测试" + i);
            RedisSetUtil.addToSortedSet("wTestSetKey", syncData, DateUtil.currentSeconds());
        }
        Set<Object> objects = RedisSetUtil.queryByRange("wTestSetKey", 0L, null);
        log.info("set测试数据数量：{}", objects.size());
        return R.ok(objects);
    }


    /**
     *
     */
    @GetMapping("/open/api/test1/{numList}")
    @ApiOperation(value = "test1", notes = "test")
    public R test1(@PathVariable List<Integer> numList) {
        Set<Object> set = RedisSetUtil.queryByRange("wTestSetKey", 0L, null);
        log.info("set测试数据删除前的数量：{}", set.size());
        int i = 0;
        for (Object item1 : set) {
            SyncData item = JSONUtil.toBean(JSONUtil.toJsonStr(item1), SyncData.class);
            item.setStatus(9);
            if (numList.contains(i)) {
                RedisSetUtil.remove("wTestSetKey", item1);
            }
            i++;
        }
        Set<Object> set1 = RedisSetUtil.queryByRange("wTestSetKey", 0L, null);
        log.info("set测试数据删除后的数量：{}", set1.size());
        return R.ok(set1);
    }
}
