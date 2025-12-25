package com.center.medical.sellcrm.bean.vo;

import com.center.medical.sellcrm.bean.model.OrderConflict;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2023-05-17 8:34
 * @description: 撞单记录信息
 */
@Data
public class OrderConflictVo extends OrderConflict implements Serializable {
    private static final long serialVersionUID = -8717323247924994282L;

    private List<ConflictOrderVo> ConflictOrderList;
}
