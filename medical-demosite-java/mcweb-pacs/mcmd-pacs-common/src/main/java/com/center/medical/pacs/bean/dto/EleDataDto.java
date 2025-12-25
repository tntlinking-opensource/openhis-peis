package com.center.medical.pacs.bean.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EleDataDto implements Serializable {
    private static final long serialVersionUID = 117235837612267933L;

    private String hz;//赫兹
    private String side;//左右耳，L左R右
    private String type;//Air气导，Bone骨导
    private Double value;//结果


    public EleDataDto(String hz, String side, String type, Double value) {
        this.hz = hz;
        this.side = side;
        this.type = type;
        this.value = value;
    }


    public EleDataDto() {
    }
}
