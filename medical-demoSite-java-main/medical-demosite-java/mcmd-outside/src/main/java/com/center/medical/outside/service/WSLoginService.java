package com.center.medical.outside.service;


import com.center.medical.outside.bean.dto.WSLoginDto;
import com.center.medical.outside.bean.param.WSLoginParam;

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
 * @author xhp
 * @since 2025-05-23 9:56
 */
@WebService(name="wsLoginService",targetNamespace = "http://service.outside.medical.world.com")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface WSLoginService {

    /**
     * WebService登录
     *
     * @param param
     * @return
     */
    @WebMethod(operationName = "OutsideLogin")
    @WebResult(name = "ZK")
    WSLoginDto outsideLogin(
            @WebParam(name = "Request") WSLoginParam param
    );
}
