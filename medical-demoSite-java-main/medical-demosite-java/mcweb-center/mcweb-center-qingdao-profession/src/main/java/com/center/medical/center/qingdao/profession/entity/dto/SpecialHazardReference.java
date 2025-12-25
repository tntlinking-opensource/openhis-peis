package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;

@Data
public class SpecialHazardReference {
    /**
     * 危害因素ID
     */
    private String harmId;
    /**
     * 单位
     */
    private String unit;
    /**
     * 男性上限
     */
    private Double scopeUpper;
    /**
     * 男性下限
     */
    private Double scoperFloor;
    /**
     * 女性上限
     */
    private Double gscopeupper;
    /**
     * 女性下限
     */
    private Double gscoperfloor;
    private String harmName;
    private String harmCode;
    private String harmName2;
    private String jcCdoe;
    private String jcName;

}
