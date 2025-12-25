package com.center.medical.common.constant;

/**
 * 金蝶相关常量
 *
 * @author xhp
 * @since 2023-05-06 10:10
 */
public class KingdeeConstants {

    /**
     * 销售部部门名称（上传银行流水默认部门销售部）
     */
    public static final String SALES_DEPARTMENT_NAME = "销售部";

    /**
     * ioc命名空间的uri，<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:loc="http://localhost/">
     * 接口中所有方法都使用ioc命名空间，URI都一样
     */
    public static final String NAMESPACE_URI = "http://localhost/";

    /**
     * SOAP Body的XPATH
     * 强制使用的 SOAP 的 Envelope 元素是 SOAP 消息的根元素。
     * 强制使用的 SOAP Body 元素包含实际的 SOAP 消息。
     */
    public static final String XPATH_BODY = "//soap:Envelope/soap:Body";

    /**
     * SOAP Fault的XPATH
     * 可选的 SOAP Fault 元素用于指示错误消息。
     * 如果已提供了 Fault 元素，则它必须是 Body 元素的子元素。在一条 SOAP 消息中，Fault 元素只能出现一次。
     */
    public static final String XPATH_FAULT = "//soap:Envelope/soap:Body/soap:Fault";

    /**
     * 没有返回SOAP Fault，但是发送失败时，会返回ResponseStatus，成功时不会返回ResponseStatus，例：{"ResponseStatus":"Error","Reason":"组织不存在！"}
     */
    public static final String KEY_RESPONSE_STATUS = "ResponseStatus";

    /**
     * 部分接口成功时返回
     */
    public static final String SUCCESS_MSG = "\"ResponseStatus\":\"Success\"";

    /**
     * 获取金蝶组织的方法名，<loc:GetOrganization/>
     */
    public static final String METHOD_GET_ORGANIZATION = "loc:GetOrganization";

    /**
     * 获取金蝶组织结果的XPATH
     */
    public static final String XPATH_GET_ORGANIZATION = "/*[local-name()='GetOrganizationResponse']/*[local-name()='GetOrganizationResult']";

    /**
     * 获取金蝶部门的方法名
     */
    public static final String METHOD_GET_DEPARTMENT = "loc:GetDepartment";

    /**
     * 获取金蝶部门结果的XPATH
     */
    public static final String XPATH_GET_DEPARTMENT = "/*[local-name()='GetDepartmentResponse']/*[local-name()='GetDepartmentResult']";

    /**
     * 上传银行流水方法名
     */
    public static final String METHOD_SAVE_OTHER_PAYABLE = "loc:SaveOtherPayable";

    /**
     * 上传银行流水方法名的XPATH
     */
    public static final String XPATH_SAVE_OTHER_PAYABLE = "/*[local-name()='SaveOtherPayableResponse']/*[local-name()='SaveOtherPayableResult']";

    /**
     * 银行交易流水更新方法名
     */
    public static final String METHOD_GET_RECEIVE_BILL = "loc:GetReceiveBill";

    /**
     * 银行交易流水更新的XPATH
     */
    public static final String XPATH_GET_RECEIVE_BILL = "/*[local-name()='GetReceiveBillResponse']/*[local-name()='GetReceiveBillResult']";

    /**
     * 银行交易流水更新方法名
     */
    public static final String METHOD_UPDATE_FLAG = "loc:UpdateFlag";

    /**
     * 银行交易流水更新的XPATH
     */
    public static final String XPATH_UPDATE_FLAG = "/*[local-name()='UpdateFlagResponse']/*[local-name()='UpdateFlagResult']";

    /**
     *
     */
    public static final String METHOD_GET_CUSTOMER = "loc:GetCustomer";

    /**
     *
     */
    public static final String XPATH_GET_CUSTOMER = "/*[local-name()='GetCustomerResponse']/*[local-name()='GetCustomerResult']";

    /**
     * 收费方式-金蝶数据更新方法名
     */
    public static final String METHOD_GET_FYXM = "loc:GetFYXM";

    /**
     * 收费方式-金蝶数据更新XPATH
     */
    public static final String XPATH_GET_FYXM = "/*[local-name()='GetFYXMResponse']/*[local-name()='GetFYXMResult']";

    /**
     * 用户-金蝶数据更新方法名
     */
    public static final String METHOD_EMPINFO = "loc:Empinfo";

    /**
     * 用户-金蝶数据更新XPATH
     */
    public static final String XPATH_EMPINFO = "/*[local-name()='EmpinfoResponse']/*[local-name()='EmpinfoResult']";

    /**
     * 上传个检方法名
     */
    public static final String METHOD_RECEIVE = "loc:Receive";

    /**
     * 上传个检XPATH
     */
    public static final String XPATH_RECEIVE = "/*[local-name()='ReceiveResponse']/*[local-name()='ReceiveResult']";
}
