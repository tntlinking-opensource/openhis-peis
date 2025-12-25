package com.center.medical.app.common.wx.bean.resp;

import lombok.Data;

/**
 * 注册返回值
 *
 * @author LGH
 */
@Data
public class RoomResponse {

    private Long roomId;

    private String qrcodeUrl;

    private String createdAt;

}
