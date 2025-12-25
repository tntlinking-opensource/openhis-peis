package com.center.medical.center.qingdao.profession.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Data
public class InfoDTO {

    @JsonProperty("code")
    private String code;
    @JsonProperty("DocumentId")
    private String documentId;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("table")
    private String table;
    @JsonProperty("field")
    private String field;
    @JsonProperty("field_value")
    private String fieldValue;
    @JsonProperty("rec_info")
    private Map<String, Object> recInfo;
}