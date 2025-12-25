package com.center.medical.center.qingdao.profession.service.impl;

import com.center.medical.center.qingdao.profession.entity.persistent.PeisState;
import com.center.medical.center.qingdao.profession.repository.PeisStateRepository;
import com.center.medical.center.qingdao.profession.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@Slf4j
public class LogServiceImpl implements LogService {
    private final PeisStateRepository peisStateRepository;

    public LogServiceImpl(PeisStateRepository peisStateRepository) {
        this.peisStateRepository = peisStateRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    public void saveLogError(String patientCode, String message) {
        log.info("saveLogError");
        log.info(patientCode + ":" + message);
        PeisState peisState = peisStateRepository.findByPatientcode(patientCode);
        if (peisState != null) {
            peisState.setPatientcode(patientCode);
//            peisState.setJinanStatus(2);
            peisState.setJinanMsg("qingdao:" + message);
            peisState.setCreatedate(new Date());
            peisState.setModifydate(new Date());
            peisState.setUploadDate(new Date());
            extracted(message, peisState);
            peisStateRepository.save(peisState);
        } else {
            peisState = new PeisState();
            peisState.setPatientcode(patientCode);
//            peisState.setJinanStatus(2);
            peisState.setJinanMsg("qingdao:" + message);
            peisState.setCreatedate(new Date());
            peisState.setModifydate(new Date());
            peisState.setUploadDate(new Date());
            extracted(message, peisState);
            peisStateRepository.save(peisState);
        }
        log.info("日志保存完成");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    public void saveLog(String patientCode, String code, String msg) {
        log.info("saveLog");
        log.info(patientCode + ":" + code + ":" + msg);
        PeisState peisState = peisStateRepository.findByPatientcode(patientCode);
        if (peisState != null) {
            peisState.setPatientcode(patientCode);
            if ("200".equals(code)) {
                peisState.setJinanStatus(1);
            } else if ("201".equals(code) && msg.contains("该档案不允许更新")) {
                peisState.setJinanStatus(3);
            } else {
                extracted(msg, peisState);
            }

            peisState.setJinanMsg("qingdao:" + msg);
            peisState.setModifydate(new Date());
            peisState.setUploadDate(new Date());
            peisStateRepository.save(peisState);
        } else {
            peisState = new PeisState();
            peisState.setPatientcode(patientCode);
            if ("200".equals(code)) {
                peisState.setJinanStatus(1);
            } else if ("201".equals(code) && msg.contains("该档案不允许更新")) {
                peisState.setJinanStatus(3);
            } else {
                extracted(msg, peisState);
            }

            peisState.setJinanMsg("qingdao:" + msg);
            peisState.setModifydate(new Date());
            peisState.setUploadDate(new Date());
            peisStateRepository.save(peisState);
        }
        log.info("日志保存完成");
    }

    private void extracted(String msg, PeisState peisState) {
        if (msg.contains("不能更新") && msg.contains("不允许更新")) {
            peisState.setJinanStatus(1);
        } else {
            peisState.setJinanStatus(2);
        }
    }
}
