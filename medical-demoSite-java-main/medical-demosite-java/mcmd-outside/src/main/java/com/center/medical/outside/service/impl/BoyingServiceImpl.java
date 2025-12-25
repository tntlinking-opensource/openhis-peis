package com.center.medical.outside.service.impl;

import cn.hutool.json.JSONUtil;
import com.center.medical.outside.bean.dto.BoyingGetPatientInfoDto;
import com.center.medical.outside.bean.dto.BoyingWriteReportDto;
import com.center.medical.outside.bean.param.BoyingGetPatientInfoParam;
import com.center.medical.outside.bean.param.BoyingWriteReportParam;
import com.center.medical.outside.service.BoyingBusinessService;
import com.center.medical.outside.service.BoyingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;

/**
 * 博英心电信息管理系统对接
 * @author xhp
 * @since 2025-05-23 10:04
 */
@Service
@Slf4j
@WebService(name="BoyingService"
        ,targetNamespace = "http://service.outside.medical.world.com"
        ,endpointInterface = "com.center.medical.outside.service.BoyingService")
public class BoyingServiceImpl implements BoyingService {
    @Resource(name="boyingBusinessService")
    private BoyingBusinessService boyingBusinessService;

    /**
     * 获取患者信息
     * @param param
     * @return
     */
    @Override
    public BoyingGetPatientInfoDto getPatientInfo(BoyingGetPatientInfoParam param) {
        log.info("boying getPatientInfo:{}", JSONUtil.toJsonStr(param));
        return boyingBusinessService.getPatientInfo(param);
    }

    /**
     * 接收心电图报告
     * @param param
     * @return
     */
    @Override
    public BoyingWriteReportDto writeReport(BoyingWriteReportParam param) {
        log.info("博英心电图writeReport:{}", JSONUtil.toJsonStr(param));
        BoyingWriteReportDto dto;
        try{
//            Attachment attachment=boyingBusinessService.uploadFile(param);
//            BoyingEleResultDto eleResultDto = new BoyingEleResultDto();
//            eleResultDto.setAttachment(attachment);
//            eleResultDto.setParam( param);
//            eleResultDto.setPatientCode(attachment.getPatientcode());
            dto=boyingBusinessService.writeReport(param);
        }catch(Exception e){
            dto=boyingBusinessService.createWriteReportDto(param,e,false);
        }
        return dto;
    }
}
