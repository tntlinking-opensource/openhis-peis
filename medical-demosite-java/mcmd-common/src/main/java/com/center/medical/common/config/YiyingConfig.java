package com.center.medical.common.config;

import lombok.Data;

import java.io.Serializable;

/**
 * 易影云影像平台对接配置
 * @author xhp
 * @since 2025-08-14 11:15
 */
@Data
public class YiyingConfig implements Serializable {
    //是否在报告中展示云胶片二维码
    private Boolean isShowQrcode;
    //云胶片二维码宽度
    private Integer qrcodeWidth;
    //云胶片二维码高度
    private Integer qrcodeHeight;
    //云胶片二维码边距
    private Integer qrcodeMargin;
    //云胶片二维码字体
    private String qrcodeFont;
    //云胶片二维码说明
    private String qrcodeRemark;
    //云胶片二维码说明字体大小
    private Integer qrcodeFontSize;

}
