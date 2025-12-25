package com.center.medical.dicom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author 路飞
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.center.medical"})
public class DicomApplication {

    public static void main(String[] args) {

        SpringApplication.run(DicomApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  沃德DICOMS启动成功   ლ(´ڡ`ლ)ﾞ  ");

    }
}
