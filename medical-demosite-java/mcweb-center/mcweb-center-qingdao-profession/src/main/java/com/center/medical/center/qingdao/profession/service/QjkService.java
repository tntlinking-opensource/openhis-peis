package com.center.medical.center.qingdao.profession.service;

import java.util.Date;

/**
 * QT体检者表(Peispatient)服务接口
 *
 * @author ay
 * @since 2024-05-07 15:54:16
 */
public interface QjkService {


    /**
     * 上传
     * @param startDate
     * @param endDate
     */
    void upload(Date startDate, Date endDate);
}

