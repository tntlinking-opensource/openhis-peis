package com.center.medical.center.qingdao.profession.service;

import java.util.Date;
import java.util.List;

/**
 * QT体检者表(Peispatient)服务接口
 *
 * @author ay
 * @since 2024-05-07 15:54:16
 */
public interface WfjkService {


    /**
     * 上传
     * @param startDate
     * @param endDate
     */
    void upload(Date startDate, Date endDate) throws Exception;

    /**
     * 重新上传
     * @param patientcodes
     */
    void sendMulti(List<String> patientcodes);
}

