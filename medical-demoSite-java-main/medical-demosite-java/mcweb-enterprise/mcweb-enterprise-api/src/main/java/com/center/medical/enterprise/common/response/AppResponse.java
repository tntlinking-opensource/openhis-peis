package com.center.medical.enterprise.common.response;

import com.center.medical.enterprise.common.enums.AppHttpStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 体检系统响应信息主体
 *
 * @author 路飞
 */
@Data
public class AppResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = AppHttpStatus.SUCCESS.value();

    /**
     * 资源已存在或者冲突或者被锁定
     */
    public static final int CONFLICT = AppHttpStatus.CONFLICT.value();

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    public static final int BAD_REQUEST = AppHttpStatus.BAD_REQUEST.value();

    @ApiModelProperty(value = "状态码:[操作成功=200, 对象创建成功=201," +
            "请求已经被接受=202, 操作已经执行成功，但是没有返回数据=204, 资源已被移除=301, 重定向=303, 资源没有被修改=304," +
            "参数列表错误（缺少，格式不匹配）=400, 未授权=401, 访问受限，授权过期=403, 资源，服务未找到=404, 不允许的http方法=405," +
            "资源冲突，或者资源被锁=409, 不支持的数据，媒体类型=415, 系统内部错误=500, 接口未实现=501]")
    private int code;

    @ApiModelProperty(value = "响应消息")
    private String msg;

    @ApiModelProperty(value = "响应数据")
    private T data;

    public static Boolean isSuccess(AppResponse result) {
        if (result.getCode() == AppResponse.SUCCESS) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
