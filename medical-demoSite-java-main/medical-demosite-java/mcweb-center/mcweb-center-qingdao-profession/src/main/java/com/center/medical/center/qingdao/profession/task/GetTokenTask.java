package com.center.medical.center.qingdao.profession.task;

import com.center.medical.center.qingdao.profession.entity.properties.ConfigProperties;
import com.center.medical.center.qingdao.profession.service.LoginService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class GetTokenTask extends QuartzJobBean {
    @Autowired
    private LoginService loginService;
    @Autowired
    private ConfigProperties properties;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        loginService.getToken(properties.getUserCode(), properties.getPassword());
    }
}
