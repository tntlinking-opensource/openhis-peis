package com.center.medical.center.qingdao.profession.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Info {
    @JsonProperty("code")
    private String code;
    @JsonProperty("DOCUMENTID")
    private String documentid;
    @JsonProperty("table")
    private String table;
    @JsonProperty("field")
    private String field;
    @JsonProperty("field_value")
    private Object fieldValue;
    @JsonProperty("msg")
    private String msg;
//    @JsonProperty("rec_info")  //暂时注释掉，因为有的是字符串，有的是对象，导致报未知异常
//    private String recInfo;
}
