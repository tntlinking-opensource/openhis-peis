package com.center.medical.center.qingdao.profession.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Result {
    private String code;
    private String msg;
    private List<InfoDTO> data;
    private String user;
    private String token;
    @JsonProperty("info")
    private List<Info> info;
    private String qrcode;
    private String status;
    private String message;
}
