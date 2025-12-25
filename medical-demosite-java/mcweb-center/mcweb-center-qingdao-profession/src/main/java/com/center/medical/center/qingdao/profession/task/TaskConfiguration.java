package com.center.medical.center.qingdao.profession.task;

import com.center.medical.center.qingdao.profession.entity.properties.ConfigProperties;
import lombok.AllArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * quartz自动上传定时任务
 * 在配置文件中配置上auto-startup: true才会执行
 */
@Configuration
@AllArgsConstructor
public class TaskConfiguration {
    private  final ConfigProperties properties;

    @Bean
    public JobDetail uploadJob() {
        return JobBuilder.newJob(UploadTask.class).storeDurably().build();
    }

    @Bean
    public Trigger trigger1() {
        Integer repeatCount=properties.getRepeatCount();
        SimpleScheduleBuilder scheduleBuilder =null;
        if(repeatCount==null||repeatCount<1){
            //永远重复
            scheduleBuilder=SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(2) //不能不设置，否则报错：Repeat Interval cannot be zero.
                    .repeatForever()//永久重复，一直执行下去，只要有这句，即使上面设置了重复多少次，也会永久重复
            ;
        }else{
            //重复指定次数
            scheduleBuilder=SimpleScheduleBuilder.simpleSchedule()
                    .withRepeatCount(repeatCount-1)//设置重复次数为n，创建时立即执行一次，共执行n+1次
                    .withIntervalInSeconds(2) //不能不设置，否则报错：Repeat Interval cannot be zero.
            ;
        }
        return TriggerBuilder.newTrigger()
                .forJob(uploadJob())
                .withSchedule(scheduleBuilder)
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
                .build();
    }

//    @Bean
//    public JobDetail getTokenTask() {
//        return JobBuilder.newJob(GetTokenTask.class).storeDurably().build();
//    }

//    @Bean
//    public Trigger trigger2() {
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(23)
//                .repeatForever();
//        return TriggerBuilder.newTrigger()
//                .forJob(uploadJob())
//                .withSchedule(scheduleBuilder)
//                .build();
//    }
}
