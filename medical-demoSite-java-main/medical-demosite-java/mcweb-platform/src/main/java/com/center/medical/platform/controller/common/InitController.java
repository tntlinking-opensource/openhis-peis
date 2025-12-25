package com.center.medical.platform.controller.common;

import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.job.TaskException;
import com.center.medical.quartz.service.ISysJobService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysDictTypeService;
import com.center.medical.system.service.SysTableConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 初始化配置操作
 *
 * @author 路飞
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/init")
@Api(tags = "初始化配置操作")
public class InitController {

    private final SysTableConfigService sysTableConfigService;
    private final ISysConfigService iSysConfigService;
    private final ISysDictTypeService iSysDictTypeService;
    private final ISysJobService iSysJobService;
    private final ISysBranchService iSysBranchService;

    /**
     * 初始化字典到redis缓存中
     */
    @GetMapping("/sysDictType")
    @ApiOperation(value = "缓存字典", notes = "初始化字典到redis缓存中")
    public R sysDictType() {
        //初始化字典到redis缓存中
        iSysDictTypeService.init();
        return R.ok();
    }

    /**
     * 初始化数据同步表配置到redis缓存中
     */
    @GetMapping("/sysTableConfig")
    @ApiOperation(value = "缓存数据表配置", notes = "初始化数据表配置到redis缓存中")
    public R sysTableConfig() {
        //初始化数据同步表配置到redis缓存中
        sysTableConfigService.init();
        return R.ok();
    }

    /**
     * 初始化系统配置到redis缓存中
     */
    @GetMapping("/sysConfig")
    @ApiOperation(value = "缓存系统配置", notes = "初始化系统配置到redis缓存中")
    public R sysConfig() {
        //初始化系统配置到redis缓存中
        iSysConfigService.init();
        return R.ok();
    }

    /**
     * 初始化定时任务
     */
    @GetMapping("/sysJob")
    @ApiOperation(value = "缓存定时任务", notes = "初始化定时任务")
    public R sysJob() throws SchedulerException, TaskException {
        //初始化定时任务
        iSysJobService.init();
        return R.ok();
    }


    /**
     * 删除分中心缓存
     */
    @GetMapping("/delAllBranchCaching")
    @ApiOperation(value = "删除分中心缓存", notes = "删除分中心缓存")
    public R delAllBranchCaching() {
        //删除分中心缓存
        iSysBranchService.removeBranchCaching("");
        return R.ok();
    }


    /**
     * 缓存分中心
     */
    @GetMapping("/branchCaching/{branchId}")
    @ApiOperation(value = "缓存分中心", notes = "缓存分中心")
    @ApiImplicitParam(name = "branchId", value = "分中心编号")
    public R branchCaching(@PathVariable String branchId) {
        //删除分中心缓存
        iSysBranchService.getByBranchId(branchId);
        return R.ok();
    }


    /**
     * 初始化分中心信息到缓存中，如分中心简码（数据同步时用到）
     */
    @GetMapping("/branchInit")
    @ApiOperation(value = "初始化分中心到缓存", notes = "初始化分中心信息到缓存中，如分中心简码（数据同步时用到）")
    public R branchInit() {
        //初始化分中心到缓存
        iSysBranchService.init();
        return R.ok();
    }


    /**
     * 删除分中心开放标识缓存
     */
    @GetMapping("/delBranchOpenedFlag")
    @ApiOperation(value = "删除分中心开放标识缓存", notes = "删除分中心开放标识缓存")
    public R delBranchOpenedFlag() {
        //删除分中心开放标识缓存
        iSysBranchService.delBranchOpenedFlag();
        return R.ok();
    }


    /**
     * 删除分中心开放同步标识缓存
     */
    @GetMapping("/delBranchOpenedSyncFlag")
    @ApiOperation(value = "删除分中心开放同步标识缓存", notes = "删除分中心开放同步标识缓存")
    public R delBranchOpenedSyncFlag() {
        //删除分中心开放同步标识缓存
        iSysBranchService.delBranchOpenedSyncFlag();
        return R.ok();
    }
}
