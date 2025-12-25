package com.center.medical.center.qingdao.profession.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class HnResult {

    private Boolean success;//是否成功
    private String data;//token
}
