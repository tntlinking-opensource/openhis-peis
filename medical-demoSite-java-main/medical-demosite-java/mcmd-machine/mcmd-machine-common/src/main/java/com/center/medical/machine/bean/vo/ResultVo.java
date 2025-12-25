package com.center.medical.machine.bean.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: mecreg
 * @description:
 * @author: yuane
 * @create: 2020-12-02 10:42
 */
public class ResultVo {
    @ApiModelProperty(value = "状态码")
    private String code;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "数据")
    private Object data;

    @ApiModelProperty(value = "总数")
    private Integer total;

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    public ResultVo() {
    }

    public ResultVo(String code, String message, Object data, boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public ResultVo(String code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}