package com.center.medical.app.common.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * minio配置信息
 *
 * @Author lth
 * @Date 2021/11/11 10:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Minio extends SwitchBaseModel {
    private String bucketName;

    private String accessKey;

    private String secretKey;

    private String endpoint;
}
