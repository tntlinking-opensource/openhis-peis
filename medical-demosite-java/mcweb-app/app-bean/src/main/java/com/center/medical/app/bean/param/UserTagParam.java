package com.center.medical.app.bean.param;

import lombok.Data;

/**
 * @author Yami
 */
@Data
public class UserTagParam {


    /**
     * id
     */
    private Integer tagId;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签类型0手动1条件2系统
     */
    private Integer tagType;

}
