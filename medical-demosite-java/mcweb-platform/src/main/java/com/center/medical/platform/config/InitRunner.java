package com.center.medical.platform.config;

import com.center.medical.quartz.service.ISysJobService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysDictTypeService;
import com.center.medical.system.service.SysTableConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class InitRunner implements CommandLineRunner {
    @Autowired
    private SysTableConfigService sysTableConfigService;
    @Autowired
    private ISysConfigService iSysConfigService;
    @Autowired
    private ISysDictTypeService iSysDictTypeService;
    @Autowired
    private ISysJobService iSysJobService;
    @Autowired
    private ISysBranchService iSysBranchService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("初始化项目配置");
        //初始化字典到redis缓存中
        iSysDictTypeService.init();
        //初始化系统配置到redis缓存中
        iSysConfigService.init();
        //初始化数据同步表配置到redis缓存中
        sysTableConfigService.init();
        //初始化定时任务
        iSysJobService.init();
        //删除分中心缓存
        iSysBranchService.removeBranchCaching(null);
        //初始化分中心信息到缓存中，如分中心简码（数据同步时用到）
        iSysBranchService.init();
    }
}
