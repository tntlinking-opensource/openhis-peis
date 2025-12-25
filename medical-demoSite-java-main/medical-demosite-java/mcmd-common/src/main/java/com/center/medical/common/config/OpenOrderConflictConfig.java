package com.center.medical.common.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 路飞
 * @date: 2023-02-07 16:13
 * @description: 撞单配置信息:open=0不开启撞单排查，open=1开启，rate：撞单成立相似度，如90表示相似度达到90%以上则成立
 */
@Data
public class OpenOrderConflictConfig implements Serializable {

    private static final long serialVersionUID = -8296856310883454132L;
    /**
     * open=0不开启撞单排查，open=1开启
     */
    private Integer open;

    /**
     * 撞单成立相似度，如90表示相似度达到90%以上则成立
     */
    private Double rate;
}
