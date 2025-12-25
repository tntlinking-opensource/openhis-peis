package com.center.medical.center.qingdao.profession.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface LogService {
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    void saveLogError(String patientCode, String message);

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = Exception.class)
    void saveLog(String patientCode, String code, String msg);
}
