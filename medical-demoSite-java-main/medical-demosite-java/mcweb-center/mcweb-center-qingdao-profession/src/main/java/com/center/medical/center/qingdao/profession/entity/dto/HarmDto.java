package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 危害因素 只用到了id和name
 */
@Data
public class HarmDto implements Serializable {
    private static final long serialVersionUID = -4539806346658700970L;

    private String id;

    private String harmName;
}
