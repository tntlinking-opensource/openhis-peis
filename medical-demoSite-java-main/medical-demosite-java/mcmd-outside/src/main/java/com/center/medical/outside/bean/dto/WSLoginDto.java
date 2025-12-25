package com.center.medical.outside.bean.dto;

import com.center.medical.outside.bean.model.WSLoginHead;
import com.center.medical.outside.bean.model.WSLoginMessage;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * WebService 登录返回参数
 */
@Data
@XmlRootElement(name = "ZK")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OutsideLoginZK", propOrder = {
        "head","message"
})
public class WSLoginDto {
    @XmlElement(name="HEAD")
    private WSLoginHead head;

    @XmlElement(name="MESSAGE")
    private WSLoginMessage message;

}
