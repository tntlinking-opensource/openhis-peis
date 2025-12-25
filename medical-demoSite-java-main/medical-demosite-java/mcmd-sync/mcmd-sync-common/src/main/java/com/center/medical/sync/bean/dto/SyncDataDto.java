package com.center.medical.sync.bean.dto;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据表配置(SysTableConfig)表实体类
 *
 * @author makejava
 * @since 2023-07-12 11:41:24
 */
@Data
@ApiModel(value = "SyncDataDto", description = "同步数据")
public class SyncDataDto extends Model<SyncDataDto> implements Serializable {
    //如果缓存中存在数据，这个serialVersionUID的值不能变化，这个值最好不要改变，否则取值时会发生序列化错误
    private static final long serialVersionUID = -14427158743376801L;

    @ApiModelProperty(value = "操作ID(md_sync_data表中的ID)")
    private Long syncDataId;

    @ApiModelProperty(value = "关联的数据ID集合")
    private String bizId;

    @ApiModelProperty(value = "操作类型：2.新增 3.更新 4.删除")
    private Integer optType;

    @ApiModelProperty(value = "同步的数据表")
    private String bizTable;

    @ApiModelProperty(value = "同步更新数据执行sql")
    private String syncRunSql;

    @ApiModelProperty(value = "同步分中心：1.同步至所有中心 2.指定中心")
    private Integer syncTo;

    @ApiModelProperty(value = "分中心ID：线上表示同步到的分中心，线下表示该条记录来自的分中心")
    private String fxzId;

    public SyncDataDto() {
    }

    public SyncDataDto(Long syncDataId, String bizId, Integer optType, String bizTable, String syncRunSql, Integer syncTo, String fxzId) {
        this.syncDataId = syncDataId;
        this.optType = optType;
        this.bizId = bizId;
        this.bizTable = bizTable;
        this.syncRunSql = syncRunSql;
        this.syncTo = syncTo;
        this.fxzId = fxzId;
    }
}
