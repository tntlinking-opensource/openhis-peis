package com.center.medical.outreach.lc.controller;

import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.redis.RedisSetUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 同步数据接口控制层
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "同步数据接口")
@RequestMapping("/open/api/syncData")
public class SyncController extends BaseController {
    /**
     * 服务对象
     */
    private final MapperFacade mapperFacade;

    /**
     * 获取最新同步数据
     *
     * @param param 请求同步数据参数
     */
//    @IpAuth
//    @PostMapping("/getSyncData")
//    @ApiOperation(value = "获取最新同步数据", notes = "获取最新同步数据")
//    public R<List<SyncData>> getSyncData(SyncDataParam param) {
//        log.info("获取最新同步数据接口参数：{}", param);
//        return R.ok(null);
//    }
    @GetMapping("/resetRedisPatentCode/{jm}")
    @ApiOperation(value = "重置redis中体检号前缀", notes = "将redis缓存中的以APP开头的体检号改为当前分中心简码开头的体检号)")
    public R setRedisPatentCode(@PathVariable String jm) {
        return R.toResult(CodeUtil.resetRedisPatentCode(jm));
    }

    @GetMapping("/getRedisPatentCodes")
    @ApiOperation(value = "获取redis中体检号列表", notes = "获取redis中体检号列表")
    public R getRedisPatentCodes() {
        Set<Object> set = RedisSetUtil.queryByRange(Constants.PATIENT_CODE_SET, 0L, null);
        return R.ok(set);
    }

    public static void main(String[] args) {
        System.out.println("01" + ("APP02301110000".substring(3)));
        Set<Object> set = RedisSetUtil.queryByRange(Constants.PATIENT_CODE_SET, 0L, null);
    }

}

