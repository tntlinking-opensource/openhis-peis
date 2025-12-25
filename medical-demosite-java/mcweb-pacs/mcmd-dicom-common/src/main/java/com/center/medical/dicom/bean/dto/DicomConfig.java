package com.center.medical.dicom.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Dicom配置信息
 *
 * @author 路飞船长
 * @since 2023-03-27 18:52:45
 */
@Data
public class DicomConfig implements Serializable {

    private static final long serialVersionUID = -7012251113138650959L;

    /**
     * 端口号
     */
    private Integer port;
    /**
     * 设备的应用实体名称
     */
    private String callingAET;
    /**
     * 是否开启接收
     */
    private Integer isEnabled;
    /**
     * 接口类型转换
     */
    private Map<String, String> jklxs;

}
