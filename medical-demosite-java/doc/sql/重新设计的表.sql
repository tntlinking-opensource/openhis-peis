CREATE TABLE `md_base_conclusion_type`
(
    `id`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `keyconclusiontype`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结论词分类',
    `conclusiontype_name`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类名称',
    `conclusiontype_nameeng` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类英文名称',
    `conclusiontype_code`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类码',
    `input_code`             varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '分类输入码',
    `disporder`              varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分类展现顺序（用户总检汇总时顺序控制）',
    `createdate`             datetime                                                     DEFAULT NULL COMMENT '创建时间',
    `modifydate`             datetime                                                     DEFAULT NULL COMMENT '更新时间',
    `dep_id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '科室ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='总检结论词类型';

CREATE TABLE `md_base_conclusion`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `sysmanmark`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '维护标记',
    `keyconclusion`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '健值',
    `name`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '结论名称',
    `name_en`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结论英文名称',
    `depiction`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '疾病解释',
    `suggestion`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '总检建议',
    `advice`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '健康建议',
    `dietguide`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '饮食指导',
    `sportguide`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '运动指导',
    `knowledge`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '健康知识',
    `suggestiongroup` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '团体建议',
    `input_code`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '拼音输入码',
    `f_maledisease`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '男性病',
    `f_femaledisease` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '女性病',
    `note`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备注',
    `division_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '科室ID',
    `dep_name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '科室名称',
    `is_long`         tinyint(1) DEFAULT NULL COMMENT '是否进总检',
    `is_delete`       tinyint(1) DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `is_public`       tinyint(1) DEFAULT NULL COMMENT '是否是公共的： 0.不是 1.是',
    `fzx_ids`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '分中心IDs，逗号拼接',
    `status`          tinyint(1) DEFAULT NULL COMMENT '状态',
    `audit_status`    tinyint(1) DEFAULT NULL COMMENT '审核状态（null不需要审核,不是总检插入）： 0.未审 1.已审',
    `scbz`            tinyint(1) DEFAULT NULL COMMENT '上传状态（null不需要上传,不是总检插入）：0.未上传  1.已上传（总检插入的上传到线上）',
    `auditer`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '审核人',
    `audit_time`      datetime                                                      DEFAULT NULL COMMENT '审核时间',
    `creater`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '维护（创建）人',
    `createdate`      datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime                                                      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='总检结论词';