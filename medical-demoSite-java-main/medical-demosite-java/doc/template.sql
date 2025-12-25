tinyint(1) DEFAULT '0' COMMENT '是否删除：0.未删除 1.已删除',

    tinyint(1) DEFAULT '0' COMMENT '：0或null.否 1.是'

 tinyint(1) DEFAULT NULL COMMENT '是否支持在线支付：0.否 1.是'

`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`update_time` datetime DEFAULT NULL COMMENT '更新时间',

，详见：ModelType

同步状态：0.未同步 1.已同步