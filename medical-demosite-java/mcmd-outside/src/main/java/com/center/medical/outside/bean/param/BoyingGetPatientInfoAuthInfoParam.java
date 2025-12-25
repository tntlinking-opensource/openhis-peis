package com.center.medical.outside.bean.param;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * WebService 授权信息参数
 */
@Data
public class BoyingGetPatientInfoAuthInfoParam implements Serializable {

    private static final long serialVersionUID = 6443742159231525417L;

    private String code;

    private String flag;

    private String signature;

}
