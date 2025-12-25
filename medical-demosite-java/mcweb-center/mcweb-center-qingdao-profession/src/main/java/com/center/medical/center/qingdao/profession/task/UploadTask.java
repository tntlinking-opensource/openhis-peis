package com.center.medical.center.qingdao.profession.task;

import cn.hutool.core.date.DateUtil;
import com.center.medical.center.qingdao.profession.entity.properties.ConfigProperties;
import com.center.medical.center.qingdao.profession.service.OccupationalHealthArchivesService;
import com.center.medical.center.qingdao.profession.service.QjkService;
import com.center.medical.center.qingdao.profession.service.WfjkService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@DisallowConcurrentExecution
public class UploadTask extends QuartzJobBean {
    @Autowired
    private OccupationalHealthArchivesService occupationalHealthArchivesService;
    @Autowired
    private QjkService qjkService;
    @Autowired
    private ConfigProperties properties;
    @Autowired
    private WfjkService wfjkService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    log.info("职业数据自动上传线程启动");
//                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//                    try {
//                        occupationalHealthArchivesService.uploadCondition(sdf.parse("20230729000000"), sdf.parse("20230809235959"));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("职业数据自动上传线程结束");
//                }
//            }).start();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    log.info("职业数据自动上传线程启动");
//                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//                    try {
//                        occupationalHealthArchivesService.uploadCondition(sdf.parse("20230810000000"), sdf.parse("20230819235959"));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("职业数据自动上传线程结束");
//                }
//            }).start();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    log.info("职业数据自动上传线程启动");
//                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//                    try {
//                        occupationalHealthArchivesService.uploadCondition(sdf.parse("20230820000000"), sdf.parse("20230831235959"));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("职业数据自动上传线程结束");
//                }
//            }).start();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    log.info("职业数据自动上传线程启动");
//                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//                    try {
//                        occupationalHealthArchivesService.uploadCondition(sdf.parse("20230901000000"), sdf.parse("20230907235959"));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("职业数据自动上传线程结束");
//                }
//            }).start();


//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    log.info("职业数据自动上传线程启动");
//                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//                    try {
//                        occupationalHealthArchivesService.uploadCondition(sdf.parse("20231010000000"), sdf.parse("20231019235959"));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("职业数据自动上传线程结束");
//                }
//            }).start();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    log.info("职业数据自动上传线程启动");
//                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//                    try {
//                        occupationalHealthArchivesService.uploadCondition(sdf.parse("20231020000000"), sdf.parse("20231031235959"));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("职业数据自动上传线程结束");
//                }
//            }).start();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    log.info("职业数据自动上传线程启动");
//                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
//                    try {
//                        occupationalHealthArchivesService.uploadCondition(sdf.parse("20231101000000"), sdf.parse("20231111235959"));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("职业数据自动上传线程结束");
//                }
//            }).start();



            log.info("职业数据自动上传线程启动");
            occupationalHealthArchivesService.uploadCondition(DateUtil.parseDate(properties.getStartDate()), DateUtil.parseDate(properties.getEndDate()));
            log.info("职业数据自动上传线程结束");


            log.info("----区疾控传线程启动----");
            qjkService.upload(DateUtil.parseDate(properties.getStartDate()), DateUtil.parseDate(properties.getEndDate()));
            log.info("----区疾控传线程结束----");


            log.info("----潍坊疾控传线程启动----");
            wfjkService.upload(DateUtil.parseDate(properties.getStartDate()), DateUtil.parseDate(properties.getEndDate()));
            log.info("----潍坊疾控传线程结束----");
        } catch (Exception e) {
            log.info("其他错误",e);
            throw new JobExecutionException(e);
        }
    }
}
