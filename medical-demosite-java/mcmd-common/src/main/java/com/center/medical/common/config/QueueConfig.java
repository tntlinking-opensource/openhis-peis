package com.center.medical.common.config;

import lombok.Data;

import java.io.Serializable;

/**
 * 排队配置信息
 * @author xhp
 * @since 2024-04-21 8:26
 */
@Data
public class QueueConfig implements Serializable {
    private static final long serialVersionUID = 2589458553976797947L;
    //导引单二维码地址
    private String qrCodeUrl;
    //老系统体检号前缀（5位）
    private String oldSysPrefix;
    //是否开启,1是0否
    private String lineUpOpen;
    //导引单排队系统备注
    private String lineUpRemarks;
    //导引单图片地址(全路径)
    private String imagePath;
}
