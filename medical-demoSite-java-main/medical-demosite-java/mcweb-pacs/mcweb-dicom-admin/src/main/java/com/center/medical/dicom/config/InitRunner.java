package com.center.medical.dicom.config;

import com.center.medical.dicom.service.DicomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
public class InitRunner implements CommandLineRunner {
    @Autowired
    private DicomService dicomService;

    @Override
    public void run(String... args) {
        //log.info("开始传图");
        //启动上传图片功能
//        try {
//            dicomService.uploadDicomFileTask();
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
