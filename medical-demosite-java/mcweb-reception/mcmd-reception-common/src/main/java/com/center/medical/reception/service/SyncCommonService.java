package com.center.medical.reception.service;

import java.util.List;
import java.util.Map;

/**
 * @author: 路飞
 * @date: 2022-12-05 19:56
 * @description: 前台-备单服务接口
 */
public interface SyncCommonService {

    /**
     * 同步订单信息
     * @param orderIds
     * @return
     */
    String syncOrderData(List<String> orderIds);

    /**
     * 同步体检者信息
     * @param patientCodes
     * @return
     */
    List<Map<String, String>> syncPatientData(List<String> patientCodes);

    /**
     * 删除已登记体检者在其他分中心数据（每天晚上10点半以后），只在线上系统执行
     * @param patientCodes
     * @return
     */
    List<Map<String, Object>> delOtherPatient(List<String> patientCodes);
}
