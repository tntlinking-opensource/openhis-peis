package com.center.medical.outside.service;


import com.center.medical.outside.bean.dto.BoyingGetPatientInfoDto;
import com.center.medical.outside.bean.dto.BoyingWriteReportDto;
import com.center.medical.outside.bean.param.BoyingGetPatientInfoParam;
import com.center.medical.outside.bean.param.BoyingWriteReportParam;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * 博英心电信息管理系统对接
 *
 * @WebResult 和 @WebParam最终决定了入参和出参的根节点名称，可以存在重复
 * @WebMethod 和 @XmlType 影响的是wsdl中的名字，不可以有重复
 *
 * @author 路飞
 * @since 2025-016-16 9:56
 */
@WebService(name="BoyingService",targetNamespace = "http://service.outside.medical.world.com")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface BoyingService {

    /**
     * 获取患者信息
     *
     * @param param
     * @return
     */
    @WebMethod(operationName = "GetPatientInfo")
    @WebResult(name = "HIS")
    BoyingGetPatientInfoDto getPatientInfo(
            @WebParam(name = "Request") BoyingGetPatientInfoParam param
    );

    /**
     * 接收结果
     *
     * @param param
     * @return
     */
    @WebMethod(operationName = "WriteReport")
    @WebResult(name = "HIS")
    BoyingWriteReportDto writeReport(
            @WebParam(name = "Request") BoyingWriteReportParam param
    );
}
