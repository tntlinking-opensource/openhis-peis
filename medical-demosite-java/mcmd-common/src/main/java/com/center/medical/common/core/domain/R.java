package com.center.medical.common.core.domain;

import com.center.medical.common.constant.HttpStatus;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author 路飞
 */
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = HttpStatus.SUCCESS;

    /**
     * 失败
     */
    public static final int FAIL = HttpStatus.ERROR;

    @ApiModelProperty(value = "状态码:[操作成功=200, 对象创建成功=201," +
            "请求已经被接受=202, 操作已经执行成功，但是没有返回数据=204, 资源已被移除=301, 重定向=303, 资源没有被修改=304," +
            "参数列表错误（缺少，格式不匹配）=400, 未授权=401, 访问受限，授权过期=403, 资源，服务未找到=404, 不允许的http方法=405," +
            "资源冲突，或者资源被锁=409, 不支持的数据，媒体类型=415, 系统内部错误=500, 接口未实现=501]")
    private int code;

    @ApiModelProperty(value = "响应消息")
    private String msg;

    @ApiModelProperty(value = "响应数据")
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL, "操作失败");
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, "操作失败");
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> fail(int code, T data, String msg) {
        return restResult(data, code, msg);
    }

    public static <T> R<T> toResult(boolean result) {
        return result ? ok() : fail();

    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Boolean isError(R<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret) {
        return R.SUCCESS == ret.getCode();
    }
}
