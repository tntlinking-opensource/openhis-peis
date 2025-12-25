package com.center.medical.app.common.wx.bean;

import lombok.Data;

/**
 * 图片请求参数
 *
 * @author yami
 */
@Data
public class ImageUploadReqParam {

    private String name;

    private String type = "image/jpeg";

    private Integer size;

    private byte[] file;

}
