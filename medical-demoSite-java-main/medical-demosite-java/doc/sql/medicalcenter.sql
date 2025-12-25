/*
 Navicat Premium Data Transfer

 Source Server         : 沃德体检系统-测试
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : XXX.XXX.XXX.XXX:3306
 Source Schema         : medicalcenter

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 15/11/2022 15:05:15
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`
(
    `table_id`          bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `table_name`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表名称',
    `table_comment`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表描述',
    `sub_table_name`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联子表的表名',
    `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子表关联的外键名',
    `class_name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '实体类名称',
    `tpl_category`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
    `package_name`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成包路径',
    `module_name`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成模块名',
    `business_name`     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成业务名',
    `function_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成功能名',
    `function_author`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成功能作者',
    `gen_type`          char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
    `gen_path`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
    `options`           varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其它生成选项',
    `create_by`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`       datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remark`            varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`
(
    `column_id`      bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `table_id`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '归属表编号',
    `column_name`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列名称',
    `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列描述',
    `column_type`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '列类型',
    `java_type`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'JAVA类型',
    `java_field`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'JAVA字段名',
    `is_pk`          char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否主键（1是）',
    `is_increment`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否自增（1是）',
    `is_required`    char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否必填（1是）',
    `is_insert`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
    `is_edit`        char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
    `is_list`        char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
    `is_query`       char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
    `query_type`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
    `html_type`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    `dict_type`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `sort`           int(0) NULL DEFAULT NULL COMMENT '排序',
    `create_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 557 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_advance_visit
-- ----------------------------
DROP TABLE IF EXISTS `md_advance_visit`;
CREATE TABLE `md_advance_visit`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `is_inspect`   tinyint(1) NULL DEFAULT NULL COMMENT '是否来检：0是 1.否  2再通知 ',
    `inspect_time` datetime(0) NULL DEFAULT NULL COMMENT '来检时间',
    `visit_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访人ID',
    `visit_time`   datetime(0) NULL DEFAULT NULL COMMENT '回访时间',
    `memo`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访备注',
    `vm_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预检跟踪回访主表ID',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '更改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KF预检跟踪回访记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_advance_visit_write
-- ----------------------------
DROP TABLE IF EXISTS `md_advance_visit_write`;
CREATE TABLE `md_advance_visit_write`
(
    `id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `is_inspect`          tinyint(1) NULL DEFAULT NULL COMMENT '是否来检：0是 1.否  2再通知 ',
    `inspect_time`        datetime(0) NULL DEFAULT NULL COMMENT '来检时间',
    `visit_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访人ID',
    `visit_time`          datetime(0) NULL DEFAULT NULL COMMENT '回访时间',
    `memo`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访备注',
    `patientarchiveno_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联档案表档案号',
    `is_inspected`        tinyint(1) NULL DEFAULT NULL COMMENT '是否已检（在预约时间内是否已检）：0是 1.否',
    `createdate`          datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`          datetime(0) NULL DEFAULT NULL COMMENT '更改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_attachment
-- ----------------------------
DROP TABLE IF EXISTS `md_attachment`;
CREATE TABLE `md_attachment`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `create_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传人',
    `patientcode`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `dep_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室id',
    `file_sort`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件序列',
    `file_path`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件路径',
    `file_type`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '类型说明',
    `fee_item_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者收费项目ID（PACS）',
    `short_code`       int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    `pacs_fee_item_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PACS系统的体检者收费项目ID',
    `config_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '如果为空，取标识最小的attachmentConfig.保存文件时，用标识最大的CONFIG',
    `in_report`        tinyint(1) NULL DEFAULT NULL COMMENT '是否进报告：0.否 1.是(PACS  彩超控制个检报告和科室报告)',
    `bw`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'bw',
    `dcm_path`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'dicom文件路径，高清屏使用',
    `memo`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX              `ATT_PATIENTCODE`(`patientcode`) USING BTREE,
    INDEX              `I_ATT_DEPID`(`dep_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC附件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_attachment_config
-- ----------------------------
DROP TABLE IF EXISTS `md_attachment_config`;
CREATE TABLE `md_attachment_config`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `aetitle`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'aetitle',
    `ip`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
    `port`         int(0) NULL DEFAULT NULL COMMENT '端口',
    `real_path`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实际存储路径',
    `visit_path`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问路径',
    `memo`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `memo_a`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注a',
    `memo_b`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注b',
    `flag`         int(0) NULL DEFAULT NULL COMMENT '标示//如果是pacs，如果记录configid为null，获取flag最大的配置，保存新记录时也保存flag最大的id；如果不是pacs，没有configid时，取配置文件',
    `mapping_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '映射路径',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_ball_check_report
-- ----------------------------
DROP TABLE IF EXISTS `md_ball_check_report`;
CREATE TABLE `md_ball_check_report`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `disease_health`    tinyint(1) NULL DEFAULT NULL COMMENT '类型：0.职业 1.健康',
    `sample_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本名称',
    `combo_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐ID',
    `sample_type`       varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本类型',
    `group_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `order_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
    `begin_time`        datetime(0) NULL DEFAULT NULL COMMENT '开始登记时间',
    `end_time`          datetime(0) NULL DEFAULT NULL COMMENT '结束登记时间',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `status`            tinyint(0) NULL DEFAULT NULL COMMENT '报告状态，详见：enums.com.center.medical.bean.CheckReportStatus',
    `is_print`          tinyint(1) NULL DEFAULT NULL COMMENT '是否已打印：0或null.否 1.是',
    `ddh`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单id',
    `org_name`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体名称',
    `id_orgreservation` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体任务表ID',
    `create_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人id',
    `word_url`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'word存放地址',
    `pdf_url`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'pdf存放地址',
    `pic_inspect_m`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参检人员构成情况图（男）',
    `pic_inspect_w`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参检人员构成情况图(女）',
    `pic_inspect_t`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参检人员构成情况图（综合）',
    `pic_age_columnar`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '男女合计各年龄段构成（柱型）',
    `pic_age_pie`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '男女合计各年龄段构成（饼图）',
    `pic_age_w`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '女合计各年龄段构成（饼图）',
    `pic_age_m`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '男合计各年龄段构成（饼图）',
    `item_total`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '横坐标为在上表中各体检项目的行号',
    `exception_m`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '男性前十大异常结果(单位:人)',
    `exception_w`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '女性前十大异常结果(单位:人)',
    `exception_t`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '男女综合前十大异常结果(单位:人)',
    `sglx`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上岗类型(多个，逗号,现改为单选)',
    `scsflj`            tinyint(1) NULL DEFAULT NULL COMMENT '上次是否来检',
    `bgfx`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '上次职业健康检查情况',
    `generate_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告生成人',
    `generate_date`     datetime(0) NULL DEFAULT NULL COMMENT '报告生成时间',
    `generate_hint`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '报告生成错误信息',
    `has_unchecked`     tinyint(1) NULL DEFAULT NULL COMMENT '是否含未检：0.不含 1.含',
    `bcbgfx`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '本次职业健康检查情况',
    `scbs`              tinyint(1) NULL DEFAULT NULL COMMENT '上传标志：0.未上传 1.已上传',
    `sex`               tinyint(1) NULL DEFAULT NULL COMMENT '性别：0.男 1.女  null.通用',
    `create_status`     tinyint(1) NULL DEFAULT NULL COMMENT '报告生成状态：0或Null未生成 1.生成中 2.已生成',
    `config_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'configId',
    `progress`          decimal(3, 0) NULL DEFAULT NULL COMMENT '生成进度',
    `org_depart`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '部门',
    `ball_check_report` tinyint(1) NULL DEFAULT NULL COMMENT 'ball_check_report',
    `reason`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '未通过原因',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'TJ团检报告主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_bas_merge
-- ----------------------------
DROP TABLE IF EXISTS `md_bas_merge`;
CREATE TABLE `md_bas_merge`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `merge_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论名称',
    `suggestion` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总检建议',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `input_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拼音输入码',
    `tjjy`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '团检建议',
    `creater`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '合并结伦词' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_bas_merge_conclusion
-- ----------------------------
DROP TABLE IF EXISTS `md_bas_merge_conclusion`;
CREATE TABLE `md_bas_merge_conclusion`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `conclusion_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结伦词ID',
    `merge_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合并结伦词ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '合并结伦词中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_basconclusion
-- ----------------------------
DROP TABLE IF EXISTS `md_basconclusion`;
CREATE TABLE `md_basconclusion`
(
    `id`                            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `sysmanmark`                    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护标记',
    `keyconclusion`                 varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '健值',
    `name`                          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '结论名称',
    `conclusion_nameeng`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `conclusion_code`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `conclusion_code2`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `conclusion_code3`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `conclusion_codehm`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `conclusioncodex`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_conclusiontype`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_conclusiontype2`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_conclusiontype3`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_conclusiongroup`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_conclusionlink`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_conclusionmedinsurance`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_conclusioncadre`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_conclusionoccup`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_section`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `depiction`                     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '疾病解释',
    `suggestion`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总检建议',
    `advice`                        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康建议',
    `dietguide`                     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '饮食指导',
    `sportguide`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '运动指导',
    `knowledge`                     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康知识',
    `suggestiongroup`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '团体建议',
    `id_icd10`                      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `input_code`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '拼音输入码',
    `inputcodeb`                    varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `inputcodec`                    varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `inputcoded`                    varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `inputcodee`                    varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `disporder`                     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_specialconclusionlist`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `f_realdisease`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `f_maledisease`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '男性病',
    `f_femaledisease`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '女性病',
    `f_hideongroupreport`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `disporder_ongroupreport`       varchar(26) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `conclusionfindermatch`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `conclusion`                    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'CONCLUSIONMATCHEXCLUDESELF',
    `f_normalconclusion`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `severedegree`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `f_occudiseaseobject`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `f_occudiseasecontraindication` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `suggestionoccudisease`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `createdate`                    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `note`                          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `status`                        tinyint(1) NULL DEFAULT NULL COMMENT '状态',
    `division_id`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `is_long`                       tinyint(1) NULL DEFAULT NULL COMMENT '是否进总检',
    `dep_name`                      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室名称',
    `is_delete`                     tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `is_public`                     tinyint(1) NULL DEFAULT NULL COMMENT '是否是公共的：1.是 0或NULL. 不是',
    `fzx_ids`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心IDs，逗号拼接',
    `creater`                       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护（创建）人',
    `auditer`                       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人',
    `audit_time`                    datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
    `audit_status`                  tinyint(1) NULL DEFAULT NULL COMMENT '审核状态（null不需要审核,不是总检插入）： 0.未审 1.已审',
    `scbz`                          tinyint(1) NULL DEFAULT NULL COMMENT '上传状态（null不需要上传,不是总检插入）：0.未上传  1.已上传（总检插入的上传到线上）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '总检结论词' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_basconclusiontype
-- ----------------------------
DROP TABLE IF EXISTS `md_basconclusiontype`;
CREATE TABLE `md_basconclusiontype`
(
    `id`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `keyconclusiontype`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词分类',
    `conclusiontype_name`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
    `conclusiontype_nameeng` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类英文名称',
    `conclusiontype_code`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类码',
    `input_code`             varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类输入码',
    `disporder`              varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类展现顺序（用户总检汇总时顺序控制）',
    `createdate`             datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`             datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `dep_id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '总检结论词类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_base_adicon_exam_item_code
-- ----------------------------
DROP TABLE IF EXISTS `md_base_adicon_exam_item_code`;
CREATE TABLE `md_base_adicon_exam_item_code`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `item_code`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目代码',
    `item_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
    `input_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '艾迪康项目代码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_base_attachment_config
-- ----------------------------
DROP TABLE IF EXISTS `md_base_attachment_config`;
CREATE TABLE `md_base_attachment_config`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `aetitle`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AETITLE',
    `ip`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
    `port`         int(0) NULL DEFAULT NULL COMMENT '端口',
    `real_path`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实际存储路径',
    `visit_path`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问路径',
    `memo`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `memo_a`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注a',
    `memo_b`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注b',
    `flag`         int(0) NULL DEFAULT NULL COMMENT '标示//如果是pacs，如果记录configid为null，获取flag最大的配置，保存新记录时也保存flag最大的id；如果不是pacs，没有configid时，取配置文件',
    `mapping_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '映射路径',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础附件配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_base_guide_meal
-- ----------------------------
DROP TABLE IF EXISTS `md_base_guide_meal`;
CREATE TABLE `md_base_guide_meal`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `type`       varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
    `name`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
    `value`      varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值',
    `content`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建世家',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `fzx`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心名称',
    `remark`     varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础推荐套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_base_guide_mealitem
-- ----------------------------
DROP TABLE IF EXISTS `md_base_guide_mealitem`;
CREATE TABLE `md_base_guide_mealitem`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `meal_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推荐套餐id',
    `item_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `ys_price`   decimal(10, 2) NULL DEFAULT NULL COMMENT '原始价格',
    `price`      decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠价格',
    `remark`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `tj`         varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否推荐：1推荐',
    `orderindex` int(0) NULL DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础收费项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_base_industry
-- ----------------------------
DROP TABLE IF EXISTS `md_base_industry`;
CREATE TABLE `md_base_industry`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `category`       varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门类代码',
    `major`          varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '大类代码',
    `middle`         varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中类代码',
    `sub`            varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小类代码',
    `arrangement`    tinyint(1) NULL DEFAULT NULL COMMENT '1门类 2大类 3中类 4小类',
    `interface_code` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '济南市职业卫生综合管理平台代码',
    `is_delete`      tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `industry_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类别名称',
    `illustration`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '说明',
    `industry_code`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '完整编码',
    `idx`            int(0) NULL DEFAULT NULL COMMENT '序号（有但没用）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '国民经济行业分类GB/T 4754—2017' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_base_unit
-- ----------------------------
DROP TABLE IF EXISTS `md_base_unit`;
CREATE TABLE `md_base_unit`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `exam_code`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目代码（济南）',
    `code_no`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计量单位编码',
    `code_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计量单位名称',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `FK_UNIT_EXAM`(`exam_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '济南-计量单位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_base_worktype
-- ----------------------------
DROP TABLE IF EXISTS `md_base_worktype`;
CREATE TABLE `md_base_worktype`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `type_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工种名称',
    `qingdao_code`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '青岛代码',
    `jinan_code`    varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '济南代码',
    `changsha_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '长沙代码',
    `is_delete`     tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工种' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_base_zone
-- ----------------------------
DROP TABLE IF EXISTS `md_base_zone`;
CREATE TABLE `md_base_zone`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `zone_code`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域代码',
    `zone_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域名称',
    `zone_level`    tinyint(1) NULL DEFAULT NULL COMMENT '区域级别：1.省 2.市 3.区 4.街道',
    `pcode`         varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级区域代码 省没有上级区域',
    `is_delete`     tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `qingdao_code`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '青岛行政区代码',
    `qingdao_pcode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '青岛行政编码父级CODE',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '所属地区' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_base_zone_qd
-- ----------------------------
DROP TABLE IF EXISTS `md_base_zone_qd`;
CREATE TABLE `md_base_zone_qd`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `zone_code`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域代码',
    `zone_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '所属地区' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_basexamltem
-- ----------------------------
DROP TABLE IF EXISTS `md_basexamltem`;
CREATE TABLE `md_basexamltem`
(
    `id`                      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `sysmanmark`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统维护标志',
    `keyexamitem`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYEXAMITEM',
    `examitem_name`           varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目名称',
    `examitem_nameabr`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目名称缩写',
    `examitem_nameprn`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目打印名称',
    `examitem_nameeng`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目英文名称',
    `examitem_nameengabr`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目英文名称缩写',
    `examitem_code`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目代码',
    `examitem_code2`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMITEM_CODE2',
    `examitem_code3`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMITEM_CODE3',
    `examitem_codehm`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMITEM_CODEHM',
    `examitemcodex`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导出代码',
    `lbdm_examitemtype`       varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '_LBDM_EXAMITEMTYPE',
    `id_examitemtype`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目类型ID',
    `id_treegroup`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_TREEGROUP',
    `id_treegroupsub`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_TREEGROUPSUB',
    `valuetype`               varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值类型',
    `valueunit`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值单位',
    `valuememolines`          int(0) NULL DEFAULT NULL COMMENT '多行文本行数',
    `f_autocalc`              tinyint(1) NULL DEFAULT NULL COMMENT '自动计算',
    `f_print`                 tinyint(1) NULL DEFAULT NULL COMMENT '是否打印',
    `f_multival`              tinyint(1) NULL DEFAULT NULL COMMENT '是否多值',
    `commongroup`             int(0) NULL DEFAULT NULL COMMENT '通用组(不再使用)',
    `valuecheckcols`          int(0) NULL DEFAULT NULL COMMENT '多值列数',
    `f_entryonly`             tinyint(0) NULL DEFAULT NULL COMMENT '仅自由输入',
    `f_noentry`               tinyint(1) NULL DEFAULT NULL COMMENT '无自由输入',
    `f_entrytoconclusion`     tinyint(1) NULL DEFAULT NULL COMMENT '输入至小结',
    `f_usecheck`              tinyint(1) NULL DEFAULT NULL COMMENT '使用勾选',
    `f_sameline`              tinyint(1) NULL DEFAULT NULL COMMENT '同行显示',
    `f_can_dup`               tinyint(1) NULL DEFAULT NULL COMMENT '可重复(不再使用)',
    `f_lab_byhand`            tinyint(1) NULL DEFAULT NULL COMMENT '检验手工项目',
    `f_lab_nowait`            tinyint(1) NULL DEFAULT NULL COMMENT '检验不必等待',
    `conclusionlevel`         int(0) NULL DEFAULT NULL COMMENT '小结级别',
    `f_summarytitle`          int(0) NULL DEFAULT NULL COMMENT '小结标题',
    `phraseforhilo`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高低用词(不再使用)',
    `increments`              bigint(0) NULL DEFAULT NULL COMMENT '数值型增量',
    `numprecision`            bigint(0) NULL DEFAULT NULL COMMENT '精度',
    `valuevalidmin`           bigint(0) NULL DEFAULT NULL COMMENT 'VALUEVALIDMIN',
    `valuevalidmax`           bigint(0) NULL DEFAULT NULL COMMENT 'VALUEVALIDMAX',
    `itemflagcaselessthan`    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ITEMFLAGCASELESSTHAN',
    `itemflagcasegreaterthan` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ITEMFLAGCASEGREATERTHAN',
    `f_bothnumtext`           tinyint(1) NULL DEFAULT NULL COMMENT '数字文字同时显示',
    `f_numonly`               tinyint(1) NULL DEFAULT NULL COMMENT 'F_NUMONLY',
    `f_useappmaxmin`          tinyint(1) NULL DEFAULT NULL COMMENT '应用范围',
    `f_includemin`            tinyint(1) NULL DEFAULT NULL COMMENT '含低值',
    `f_includemax`            tinyint(1) NULL DEFAULT NULL COMMENT '含高值',
    `f_male`                  tinyint(1) NULL DEFAULT NULL COMMENT '用于男性',
    `valuemaledef`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '男性缺省值',
    `valuemalemax`            double(20, 8
) NULL DEFAULT NULL COMMENT '男性上限',
  `valuemalenormmid` bigint(0) NULL DEFAULT NULL COMMENT 'VALUEMALENORMMID',
  `valuemalemin` double(20, 8) NULL DEFAULT NULL COMMENT '男性下限',
  `valuemaleweakup` bigint(0) NULL DEFAULT NULL COMMENT 'VALUEMALEWEAKUP',
  `valuemaleweakdown` bigint(0) NULL DEFAULT NULL COMMENT 'VALUEMALEWEAKDOWN',
  `valuemalesevereup` bigint(0) NULL DEFAULT NULL COMMENT '男性生命值上限',
  `valuemaleseveredown` bigint(0) NULL DEFAULT NULL COMMENT '男性生命值下限',
  `f_female` tinyint(1) NULL DEFAULT NULL COMMENT '用于女性',
  `valuefemaledef` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '女性缺省值',
  `valuefemalemax` double(20, 8) NULL DEFAULT NULL COMMENT '女性上限',
  `valuefemalenormmid` bigint(0) NULL DEFAULT NULL COMMENT 'VALUEFEMALENORMMID',
  `valuefemalemin` double(20, 8) NULL DEFAULT NULL COMMENT '女性下限',
  `valuefemaleweakup` bigint(0) NULL DEFAULT NULL COMMENT 'VALUEFEMALEWEAKUP',
  `valuefemaleweakdown` bigint(0) NULL DEFAULT NULL COMMENT 'VALUEFEMALEWEAKDOWN',
  `valuefemalesevereup` bigint(0) NULL DEFAULT NULL COMMENT '女性生命值上限',
  `valuefemaleseveredown` bigint(0) NULL DEFAULT NULL COMMENT '女性生命值下限',
  `gxdm` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '_GXDM',
  `expression` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表达式',
  `id_conclusionhi` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词(高)',
  `id_conclusionlo` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词(低)',
  `id_conclusionpo` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词(阳)',
  `id_conclusionne` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词(阴)',
  `id_conclusionweaknormhi` int(0) NULL DEFAULT NULL COMMENT 'ID_CONCLUSIONWEAKNORMHI',
  `id_conclusionweaknormlo` int(0) NULL DEFAULT NULL COMMENT 'ID_CONCLUSIONWEAKNORMLO',
  `id_conclusionweakposihi` int(0) NULL DEFAULT NULL COMMENT 'ID_CONCLUSIONWEAKPOSIHI',
  `id_conclusionweakposilo` bigint(0) NULL DEFAULT NULL COMMENT 'ID_CONCLUSIONWEAKPOSILO',
  `input_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
  `inputcodeb` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INPUTCODEB',
  `inputcodec` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INPUTCODEC',
  `inputcoded` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INPUTCODED',
  `inputcodee` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INPUTCODEE',
  `f_patientbaseinfo` tinyint(1) NULL DEFAULT NULL COMMENT '(不使用)',
  `patientbaseinfodisporder` int(0) NULL DEFAULT NULL COMMENT '(不使用 )',
  `note` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `disporder` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行序',
  `f_examitemexportflag01` tinyint(1) NULL DEFAULT NULL COMMENT 'F_EXAMITEMEXPORTFLAG01',
  `f_examitemexportflag02` tinyint(1) NULL DEFAULT NULL COMMENT 'F_EXAMITEMEXPORTFLAG02',
  `f_nodepartsummary` tinyint(1) NULL DEFAULT NULL COMMENT 'F_NODEPARTSUMMARY',
  `f_comparereport` tinyint(1) NULL DEFAULT NULL COMMENT 'F_COMPAREREPORT',
  `f_noreportoutput` tinyint(1) NULL DEFAULT NULL COMMENT 'F_NOREPORTOUTPUT',
  `f_alwayspositive` tinyint(1) NULL DEFAULT NULL COMMENT 'F_ALWAYSPOSITIVE',
  `f_titleonreport` tinyint(1) NULL DEFAULT NULL COMMENT 'F_TITLEONREPORT',
  `itemrefrange` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ITEMREFRANGE',
  `keysummarygroup` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYSUMMARYGROUP',
  `f_useapprangeoccudisease` tinyint(1) NULL DEFAULT NULL COMMENT 'F_USEAPPRANGEOCCUDISEASE',
  `expressionoccudisease` tinyint(1) NULL DEFAULT NULL COMMENT '是否外送: null或0.不是1.是（各中心自己设置，同步时排除此字段）',
  `division_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '0:健康检查类型1:职业检查类型2:健康+职业(职业)',
  `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
  `interface_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口代码',
  `valuedangerousmax` double(20, 8) NULL DEFAULT NULL COMMENT '危急值上限',
  `valuedangerousmin` double(20, 8) NULL DEFAULT NULL COMMENT '男性生命值下限',
  `is_desc` tinyint(1) NULL DEFAULT NULL COMMENT '描述进小结',
  `is_name` tinyint(1) NULL DEFAULT NULL COMMENT '名称进小结',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '0：解锁 1：锁定',
  `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '0:未删除 1：删除',
  `is_public` tinyint(1) NULL DEFAULT NULL COMMENT '是否是公共的',
  `fzx_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心IDs，逗号拼接',
  `devicetype_position_checkitem` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `add_unit` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `BASEXAMLTEM_INPUTCODE`(`input_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC检查项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_basexamltem_sign
-- ----------------------------
DROP TABLE IF EXISTS `md_basexamltem_sign`;
CREATE TABLE `md_basexamltem_sign`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '.330',
    `inspect_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目ID',
    `name`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '体证词名称',
    `result_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词ID',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_in_summary`   tinyint(1) NULL DEFAULT NULL COMMENT '是否进小结',
    `body_input_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体证词输入码',
    `body_detail`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '体证词详细描述',
    `body_detail_zy`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '体证词详细描述(职业病）',
    `intensive_level` tinyint(0) NULL DEFAULT NULL COMMENT '重症级别',
    `other_mutex`     varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '互斥分组（异组互斥）',
    `num_mutex`       tinyint(0) NULL DEFAULT NULL COMMENT '互斥分组（同组正整数编号互斥）',
    `is_positive`     tinyint(1) NULL DEFAULT NULL COMMENT '是否阳性',
    `input_code`      varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `is_default`      tinyint(1) NULL DEFAULT NULL COMMENT '是否选中：0.未选中 1.选中',
    `orderindex`      smallint(0) NULL DEFAULT NULL COMMENT '体征词显示顺序，1、2、3、4...',
    `is_input`        varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC检查项目体证词关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_basexamltemtype
-- ----------------------------
DROP TABLE IF EXISTS `md_basexamltemtype`;
CREATE TABLE `md_basexamltemtype`
(
    `id`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `examitemtypekey`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMITEMTYPEKEY',
    `examitemtype_name`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目类型名称',
    `examitemtype_nameeng` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目类型英文名称',
    `examitemtype_code`    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目类型代码',
    `examitemtypecodex`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导出代码',
    `id_department`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门ID',
    `depart_r`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称(R)',
    `input_code`           varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `note`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`           datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`           datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `is_delete`            tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC检查项目类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_batch_sms_record
-- ----------------------------
DROP TABLE IF EXISTS `md_batch_sms_record`;
CREATE TABLE `md_batch_sms_record`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `phone`      varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `status`     tinyint(1) NULL DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `IDX_BATCHSMS_PHONE`(`phone`) USING BTREE,
    INDEX        `IDX_BATCHSMS_STATUS`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '批量发送短信记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_bk_patient
-- ----------------------------
DROP TABLE IF EXISTS `md_bk_patient`;
CREATE TABLE `md_bk_patient`
(
    `id`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`               datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`               datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientname`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `input_code`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `patientcode`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `idcardno`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `org_name`                 varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体名称',
    `id_sex`                   varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别ID',
    `birthdate`                datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
    `age`                      int(0) NULL DEFAULT NULL COMMENT '年龄',
    `marriage`                 varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻',
    `phone`                    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `dateregister`             datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `id_examtype`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型',
    `examsuite_name`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐名称',
    `org_depart`               varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体部门',
    `note`                     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `countreportoccupationxml` tinyint(0) NULL DEFAULT NULL COMMENT '客户证件类型：1.身份证 2.护照 6.军人证  7.港澳通行证/回乡证或台胞证',
    `doctorapply`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开单医生',
    `f_registered`             tinyint(1) NULL DEFAULT NULL COMMENT '是否已登记：0或null.否 1.是',
    `f_readytofinal`           tinyint(1) NULL DEFAULT NULL COMMENT '是否分检完成：0或null.否 1.是',
    `suggestion`               longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总检结论',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                      `BK_PATIENT_CODE`(`patientcode`) USING BTREE,
    INDEX                      `BK_PATIENT_IDCARDNO`(`idcardno`) USING BTREE,
    INDEX                      `BK_PATIENT_NAME`(`patientname`) USING BTREE,
    INDEX                      `BK_PATIENT_NOTE`(`note`) USING BTREE,
    INDEX                      `BK_PATIENT_PHONE`(`phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_bk_patientfeeitem
-- ----------------------------
DROP TABLE IF EXISTS `md_bk_patientfeeitem`;
CREATE TABLE `md_bk_patientfeeitem`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `id_patient`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者id',
    `note`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `examfeeitem_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费检查项目名称',
    `factprice`        decimal(12, 2) NULL DEFAULT NULL COMMENT '实付价格',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX              `BK_PI_CODE`(`id_patient`) USING BTREE,
    INDEX              `BK_PI_NOTE`(`note`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者收费项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_card
-- ----------------------------
DROP TABLE IF EXISTS `md_card`;
CREATE TABLE `md_card`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `card_no`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
    `balance_limit`   decimal(16, 2) NULL DEFAULT NULL COMMENT '卡内剩余的金额或者积分',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `modify_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人ID',
    `type_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡类型ID',
    `sell_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '售卡人ID',
    `sell_time`       datetime(0) NULL DEFAULT NULL COMMENT '出售时间',
    `validity`        datetime(0) NULL DEFAULT NULL COMMENT '有效期',
    `card_state`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '卡说明',
    `memo`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '卡备注',
    `getter_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领取人ID',
    `get_time`        datetime(0) NULL DEFAULT NULL COMMENT '领取时间',
    `grant_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发卡人ID',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `card_prefix`     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡前缀',
    `card_logo`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡标识',
    `balance_jf`      decimal(16, 0) NULL DEFAULT NULL COMMENT '会员卡剩余积分（会员卡和家庭卡使用）',
    `pay_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式ID',
    `sk_money`        decimal(16, 0) NULL DEFAULT NULL COMMENT '收款金额',
    `is_deletea`      decimal(1, 0) NULL DEFAULT NULL COMMENT '体检卡管理假删标识',
    `password`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检卡密码',
    `tel`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检卡绑定的app userid',
    `order_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单ID（团检专属卡）',
    `tc_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐id（团检专属卡)||十周年  最小套餐id',
    `balance_money`   decimal(11, 2) NULL DEFAULT NULL COMMENT '剩余金额(只有家庭卡使用这个字段)',
    `tc_dateregister` datetime(0) NULL DEFAULT NULL COMMENT '使用套餐完成登记的时间',
    `tc_patientcode`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用赠送套餐完成登记的体检号',
    `tc_checked`      tinyint(1) NULL DEFAULT NULL COMMENT '赠送套餐是否已用：0或null.否 1.是',
    `recheck_money`   decimal(8, 2) NULL DEFAULT NULL COMMENT '复查额度（体检时间一年后到期（判断套餐登记日期，必须要有赠送套餐）  按钮手动扣除）',
    `sell_status`     tinyint(1) NULL DEFAULT NULL COMMENT '销售状态：0或null未售 1.已售',
    `tc_price`        decimal(16, 4) NULL DEFAULT NULL COMMENT '面值（活动专属卡和团检专属卡即套餐优惠价，其他卡就是初始金额）',
    `sell_price`      decimal(16, 4) NULL DEFAULT NULL COMMENT '售价',
    `purchaser`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购卡人名字（不一定在系统中）',
    `phone`           varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购卡人手机号',
    `zk`              decimal(4, 1) NULL DEFAULT NULL COMMENT '折扣',
    `process_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡办理id 对应card_payway processId',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检卡' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_card_member_medical
-- ----------------------------
DROP TABLE IF EXISTS `md_card_member_medical`;
CREATE TABLE `md_card_member_medical`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '关联时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `member_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡id',
    `medical_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检卡id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `CARD_MM_MEDICALID`(`medical_id`) USING BTREE,
    INDEX        `CARD_MM_MEMBERID`(`member_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员卡体检卡关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_card_payway
-- ----------------------------
DROP TABLE IF EXISTS `md_card_payway`;
CREATE TABLE `md_card_payway`
(
    `id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `id_charge`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡ID',
    `moneyamountpaid`     decimal(8, 2) NULL DEFAULT NULL COMMENT '收费金额',
    `moneyamountpaiddate` datetime(0) NULL DEFAULT NULL COMMENT '收费日期',
    `id_feecharger`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员ID',
    `id_payway`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款方式ID',
    `createdate`          datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`          datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `note`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `cardno`              varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检卡卡号',
    `is_charged`          tinyint(1) NULL DEFAULT NULL COMMENT '已收费：1已收',
    `process_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡办理id（对应card processId）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卡办理收款方式表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_card_recheck_record
-- ----------------------------
DROP TABLE IF EXISTS `md_card_recheck_record`;
CREATE TABLE `md_card_recheck_record`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建世家',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `start_money` decimal(8, 2) NULL DEFAULT NULL COMMENT '操作起始金额',
    `end_money`   decimal(8, 2) NULL DEFAULT NULL COMMENT '操作结束金额',
    `money`       decimal(8, 2) NULL DEFAULT NULL COMMENT '金额(消费负数，充值正数)',
    `note`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `card_no`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
    `patientcode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号（消费）',
    `charge_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
    `charge_type` decimal(1, 0) NULL DEFAULT NULL COMMENT '操作类型：0.消费 1.充值',
    `username`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人用户名',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `CARD_RR_CARDNO`(`card_no`) USING BTREE,
    INDEX         `CARD_RR_CODE`(`patientcode`) USING BTREE,
    INDEX         `CARD_RR_TIME`(`charge_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '十周年卡复查金额记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_card_type
-- ----------------------------
DROP TABLE IF EXISTS `md_card_type`;
CREATE TABLE `md_card_type`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `type_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型名称',
    `card_money`  decimal(12, 2) NULL DEFAULT NULL COMMENT '卡金额',
    `is_recharge` tinyint(1) NULL DEFAULT NULL COMMENT '是否可充值：0或null.否 1.是',
    `memo`        varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `card_logo`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡标识',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `card_prefix` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡前缀',
    `jf`          int(0) NULL DEFAULT NULL COMMENT '积分（仅限会员卡）',
    `type`        tinyint(1) NULL DEFAULT NULL COMMENT 'type',
    `flag`        tinyint(1) NULL DEFAULT NULL COMMENT 'flag',
    `cid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '卡类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_carmanage
-- ----------------------------
DROP TABLE IF EXISTS `md_carmanage`;
CREATE TABLE `md_carmanage`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `car_no`         varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '车牌号',
    `id_driver`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '司机',
    `id_order`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单ID',
    `id_org`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `org_name`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体名称',
    `address`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检地址',
    `out_time_start` datetime(0) NULL DEFAULT NULL COMMENT '外出开始时间',
    `out_time_end`   datetime(0) NULL DEFAULT NULL COMMENT '外出结束时间',
    `is_delete`      tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `createer`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检车管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_carmanagefr
-- ----------------------------
DROP TABLE IF EXISTS `md_carmanagefr`;
CREATE TABLE `md_carmanagefr`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `id_user`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外出体检人员',
    `id_carmanage` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外出体检车上的人员关联ID',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检车与外出体检车上的人员关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_chest
-- ----------------------------
DROP TABLE IF EXISTS `md_chest`;
CREATE TABLE `md_chest`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `tjlx`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型',
    `ddh`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
    `gzh`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '柜子号',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更改日期',
    `note`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `dwmc`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位名称',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `chest_num`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '此表为通用表，团检' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_clientcommon
-- ----------------------------
DROP TABLE IF EXISTS `md_clientcommon`;
CREATE TABLE `md_clientcommon`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `khdwmc`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称',
    `khdwsrm`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位输入码',
    `khdwlxr`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位联系人',
    `khdh`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户电话',
    `sshy`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属行业',
    `xsjl`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理',
    `dwjgdm`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位机构代码',
    `khdwdz`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位地址',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `lqcstj`     int(0) NULL DEFAULT NULL COMMENT '领取次数统计',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `fpzt`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分配状态：0.未分配 1.已分配',
    `frdwmc`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '法人单位名称',
    `fddbr`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法定代表人',
    `yzbm`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮政编码',
    `qygm`       varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业规模',
    `qyjjlx`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业经济类型',
    `zywsfzr`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业卫生负责人',
    `khdwzcdz`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位注册地址',
    `zywsgljg`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业卫生管理机构',
    `zclx`       varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注册类型',
    `lsgx`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '隶属关系',
    `sjzgdw`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级主管单位',
    `sjcyrs`     varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实际从业人数',
    `ldrs`       varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流动人数',
    `scgrs`      varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产工人数',
    `zybwhysrs`  varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病危害因素人数',
    `zybwhzycss` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病危害作业场所数',
    `zybwhyslb`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病危害因素类别',
    `zybwhys`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业病危害因素',
    `gylc`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '工艺流程',
    `zyyfl`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主要原辅料',
    `tjttlx`     int(0) NULL DEFAULT NULL COMMENT '体检团体类型',
    `zycp`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主要产品',
    `khsctjdwdz` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '客户上次体检单位地址',
    `bz`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户公共池表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_comboandfzx
-- ----------------------------
DROP TABLE IF EXISTS `md_comboandfzx`;
CREATE TABLE `md_comboandfzx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `tcid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐ID',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `tbzt`       tinyint(1) NULL DEFAULT NULL COMMENT '同步状态：0.未同步 1.同步 2.更新',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '最小套餐与分中心关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_comboanditem
-- ----------------------------
DROP TABLE IF EXISTS `md_comboanditem`;
CREATE TABLE `md_comboanditem`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `tcid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐ID',
    `sfxmid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    `sfbx`       varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '是否备选',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否可以删除：0或null.不可以 1.可以',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `sfbj`       tinyint(1) NULL DEFAULT NULL COMMENT '是否必检：0.选检 1.必检 ',
    `sort`       int(0) NULL DEFAULT NULL COMMENT '排序',
    `item_sort`  int(0) NULL DEFAULT NULL COMMENT 'item_sort',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '维护最小套餐与收费项目关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_comboexamitem
-- ----------------------------
DROP TABLE IF EXISTS `md_comboexamitem`;
CREATE TABLE `md_comboexamitem`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `harm_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素Id',
    `exam_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目Id',
    `item_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目Id',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `scope_upper`  decimal(12, 3) NULL DEFAULT NULL COMMENT '男性上限',
    `scoper_floor` decimal(12, 3) NULL DEFAULT NULL COMMENT '男性下限',
    `gscopeupper`  decimal(12, 3) NULL DEFAULT NULL COMMENT '女性上限',
    `gscoperfloor` decimal(12, 3) NULL DEFAULT NULL COMMENT '女性下限',
    `combo_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最小套餐ID',
    `medical_type` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业体检类别，详见：enums.com.center.medical.bean.MedicalType',
    `ks_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ks_id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用于判断职业小结' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_comments_progessional
-- ----------------------------
DROP TABLE IF EXISTS `md_comments_progessional`;
CREATE TABLE `md_comments_progessional`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `progessional_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业处理意见ID（基础数据中的）',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职业处理意见表：这个表根据体检号进行筛选出这个体检号下所做的所有职业处理意见，这里会有多条，主要用于存储多条处理意见。这里的多条处理意见从基础数据的维护中取得' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_compare_report
-- ----------------------------
DROP TABLE IF EXISTS `md_compare_report`;
CREATE TABLE `md_compare_report`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientarchiveno`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案号',
    `patientcode_this`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '本届体检号',
    `patientcode_before` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上届体检号',
    `patientcode_second` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上二届体检号',
    `register_this`      datetime(0) NULL DEFAULT NULL COMMENT '本届登记时间',
    `register_before`    datetime(0) NULL DEFAULT NULL COMMENT '上届登记时间',
    `register_second`    datetime(0) NULL DEFAULT NULL COMMENT '上二届登记时间',
    `path_word`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对比报告存放位置（WORD）',
    `path_pdf`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对比报告存放位置（PDF）',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`          tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `config_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'config_id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对比报告表，用于存储对报告都有哪些生成过' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_conclusion_and_fzx
-- ----------------------------
DROP TABLE IF EXISTS `md_conclusion_and_fzx`;
CREATE TABLE `md_conclusion_and_fzx`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `conclusion_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结伦词ID',
    `fzx_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `tbzt`          tinyint(1) NULL DEFAULT NULL COMMENT '同步状态：0.未同步 1.已同步 ',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC结伦词和分中心关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_consulation
-- ----------------------------
DROP TABLE IF EXISTS `md_consulation`;
CREATE TABLE `md_consulation`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新世家',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `consult_type`    tinyint(1) NULL DEFAULT NULL COMMENT '咨询类型，详见：enums.com.center.medical.bean.ConsultType',
    `consult_name`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '咨询人姓名',
    `consult_time`    datetime(0) NULL DEFAULT NULL COMMENT '咨询时间',
    `dep_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室id',
    `sign_path`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签名图路径',
    `consult_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '咨询内容',
    `patientcode`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `consult_phone`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '咨询电话',
    `doctor_username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医生用户名',
    `config_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'configid（attachmentconfig中的id）',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '科室/总检咨询' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_country
-- ----------------------------
DROP TABLE IF EXISTS `md_country`;
CREATE TABLE `md_country`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `keycountry`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYCOUNTRY',
    `country_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家名称',
    `country_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家编码',
    `country_code2`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'COUNTRY_CODE2',
    `country_code3`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'COUNTRY_CODE3',
    `country_codehm` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'COUNTRY_CODEHM',
    `country_codexh` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家导出代码',
    `input_code`     varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `note`           varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC国家' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_create_order_qtxz
-- ----------------------------
DROP TABLE IF EXISTS `md_create_order_qtxz`;
CREATE TABLE `md_create_order_qtxz`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '变更时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `order_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单ID',
    `username`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更人USERNAME',
    `qtxz`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '修改后的前台须知',
    `old_qtxz`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '修改前的前台须知',
    `idx`        int(0) NULL DEFAULT NULL COMMENT '第几次修改',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `IDX_QTXZ_DDID`(`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '线上变更订单前台须知记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_createcombo
-- ----------------------------
DROP TABLE IF EXISTS `md_createcombo`;
CREATE TABLE `md_createcombo`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `tjtcmc`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检套餐名称',
    `tjlx`           tinyint(1) NULL DEFAULT NULL COMMENT '体检类型',
    `tjtcjc`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检套餐简称',
    `tjtcsrm`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检套餐输入码',
    `jhys`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接害因素',
    `zytjlb`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业体检类别，详见：enums.com.center.medical.bean.MedicalType',
    `syxb`           tinyint(1) NULL DEFAULT NULL COMMENT '适用性别',
    `tcysjg`         decimal(8, 3) NULL DEFAULT NULL COMMENT '套餐原始价格',
    `tczk`           decimal(8, 3) NULL DEFAULT NULL COMMENT '套餐折扣',
    `zhjg`           decimal(8, 3) NULL DEFAULT NULL COMMENT '折后价格',
    `khdwmc`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称',
    `khdwmcid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称ID',
    `sfybd`          char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否已备单',
    `sfyhtc`         char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否已婚套餐',
    `nlz`            varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄自',
    `nld`            varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄至',
    `fkfs`           varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款方式',
    `sl`             int(0) NULL DEFAULT NULL COMMENT '数量',
    `jxj`            varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '几选几',
    `combostate`     char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最小套餐标识：1.健康体检最小套餐 2.职业体检最小套餐',
    `xsjlid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `fzxid`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心ID',
    `is_delete`      tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `bz`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `bjzt`           tinyint(1) NULL DEFAULT NULL COMMENT '编辑状态：0.可编辑 1.不可编辑',
    `is_plus`        tinyint(1) NULL DEFAULT NULL COMMENT '团检加项/弃项用：0.不是加项/弃项 1.是加项/弃项',
    `tbzt`           tinyint(1) NULL DEFAULT NULL COMMENT '同步状态：0.未同步 1.已同步',
    `is_active`      tinyint(1) NULL DEFAULT NULL COMMENT '是否活动：0或null.否 1.是',
    `is_recommended` tinyint(1) NULL DEFAULT NULL COMMENT '是否推荐：0或null.否 1.是',
    `combo_sort`     int(0) NULL DEFAULT NULL COMMENT 'app排序',
    `is_ban`         tinyint(1) NULL DEFAULT NULL COMMENT '是否禁用：0或null.否 1.是',
    `pingan_id`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平安套餐ID',
    `app_type_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小程序分类id',
    `modifier`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人用户名',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX            `SELLCUSTOMER_TJTCSRM`(`tjtcsrm`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '最小套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_createcombo_type
-- ----------------------------
DROP TABLE IF EXISTS `md_createcombo_type`;
CREATE TABLE `md_createcombo_type`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建世家',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `name`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
    `type_index` int(0) NULL DEFAULT NULL COMMENT '序号',
    `type_state` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '最小套餐分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_createmeal
-- ----------------------------
DROP TABLE IF EXISTS `md_createmeal`;
CREATE TABLE `md_createmeal`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `tjtcmc`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检套餐名称',
    `tjtcsrm`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检套餐输入码',
    `tjtcjc`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检套餐简称',
    `tjlx`            tinyint(1) NULL DEFAULT NULL COMMENT '体检类型',
    `jhys`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接害因素',
    `syxb`            tinyint(1) NULL DEFAULT NULL COMMENT '适用性别',
    `tcysjg`          decimal(16, 4) NULL DEFAULT NULL COMMENT '套餐原始价格',
    `tczk`            decimal(16, 4) NULL DEFAULT NULL COMMENT '套餐折扣',
    `zhjg`            decimal(16, 4) NULL DEFAULT NULL COMMENT '折后价格',
    `khdwmcid`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称ID',
    `sfybd`           char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否已备单',
    `sfyhtc`          char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否已婚套餐',
    `nlz`             varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄自',
    `nld`             varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄至',
    `fkfs`            varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款方式',
    `zytjlb`          tinyint(1) NULL DEFAULT NULL COMMENT '职业体检类别，详见：enums.com.center.medical.bean.MedicalType',
    `combostate`      varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '普通套餐标示：0.普通套餐',
    `kxsl`            int(0) NULL DEFAULT NULL COMMENT '可选数量',
    `xsjlid`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `fzxid`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心ID--存在多个分中心',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `bjzt`            tinyint(1) NULL DEFAULT NULL COMMENT '编辑状态：0.可编辑 1.不可编辑',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `is_org_plus`     tinyint(1) NULL DEFAULT NULL COMMENT '团检加项/弃项用：0.未修改 1.已用于加项/弃项',
    `zkztw`           tinyint(1) NULL DEFAULT NULL COMMENT '折扣状态位：0.可用折扣打折 1.可用领导折扣打折',
    `tjm`             int(0) NULL DEFAULT NULL COMMENT '体检码',
    `sfzx`            tinyint(1) NULL DEFAULT NULL COMMENT '是否自选套餐：0.固定套餐 1.完全自选 2.部分自选',
    `sfwc`            tinyint(1) NULL DEFAULT NULL COMMENT '是否外出：0 .内检 1.外检',
    `zxzk`            decimal(16, 4) NULL DEFAULT NULL COMMENT '自选折扣（用于APP计算自选项目价格）',
    `forbidden`       tinyint(1) NULL DEFAULT NULL COMMENT '是否禁用：0或null.否 1.是',
    `pingan_id`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平安套餐ID',
    `is_big`          tinyint(1) NULL DEFAULT NULL COMMENT '是否大套餐：0或null.否 1.是',
    `tong_limit`      decimal(12, 4) NULL DEFAULT NULL COMMENT '统收限额',
    `zhjgapp`         decimal(16, 4)                                               NOT NULL COMMENT '团惠价，优惠价不包括备选项目（app使用）',
    `group_price`     decimal(16, 4) NULL DEFAULT NULL COMMENT '分组金额',
    `total_costprice` decimal(16, 4) NULL DEFAULT NULL COMMENT '项目成本价合计',
    `bz`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX             `CM_ISBIG_INDEX`(`is_big`) USING BTREE,
    INDEX             `CREATEMEAL_TJTCSRM`(`tjtcsrm`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '普通套餐表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_createorder
-- ----------------------------
DROP TABLE IF EXISTS `md_createorder`;
CREATE TABLE `md_createorder`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `ddh`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
    `ddmc`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单名称',
    `dddm`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单代码',
    `khdwmcid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称ID',
    `txfs`          char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提醒方式：0.首页提醒 1.短信提醒',
    `fsdxsjh`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送短信手机号',
    `cjddrq`        datetime(0) NULL DEFAULT NULL COMMENT '创建订单日期',
    `khdwdh`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位电话',
    `tjzx`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检中心',
    `jhjqc`         datetime(0) NULL DEFAULT NULL COMMENT '计划检期从',
    `jhjqd`         datetime(0) NULL DEFAULT NULL COMMENT '计划检期到',
    `sfyqdht`       char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否已签订合同',
    `htbh`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合同编号',
    `nxtjrs`        int(0) NULL DEFAULT NULL COMMENT '男性体检人数',
    `vxtjrs`        int(0) NULL DEFAULT NULL COMMENT '女性体检人数',
    `ddjg`          decimal(12, 2) NULL DEFAULT NULL COMMENT '订单价格',
    `ddzk`          decimal(8, 3) NULL DEFAULT NULL COMMENT '订单折扣',
    `ddyhj`         decimal(12, 3) NULL DEFAULT NULL COMMENT '订单优惠价',
    `qtxz`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '前台须知',
    `spzt`          varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批状态，详见：enums.com.center.medical.bean.OrderCheckStatus',
    `spyj`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批意见',
    `xsjlid`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `xsjl`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理',
    `fzxid`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心ID',
    `is_delete`     tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `urls`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件路径(名单)',
    `bgzt`          tinyint(1) NULL DEFAULT NULL COMMENT '变更状态，详见：enums.com.center.medical.bean.OrderChangeStatus',
    `xsdh`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径(名单)',
    `tjxs`          tinyint(1) NULL DEFAULT NULL COMMENT '体检形式：0.内检 1.外检 2.内检加外检',
    `clurls`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '材料文件路径s（|分隔）',
    `clspzt`        tinyint(1) NULL DEFAULT NULL COMMENT '材料审批状态：null未审核 1.通过 2.驳回',
    `clspyj`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '材料审批意见',
    `id_inforway`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知方式ID',
    `tjlx`          tinyint(1) NULL DEFAULT NULL COMMENT '体检类型',
    `spr`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人',
    `clspr`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '材料审批人',
    `is_online`     tinyint(1) NULL DEFAULT NULL COMMENT '是否线上备单：null或其他.未线上备单 1.已线上备单',
    `submit_time`   datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
    `cant_replace`  tinyint(1) NULL DEFAULT NULL COMMENT '是否不可替检：0或null可替检 1.不可替检',
    `chest_num`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '柜子号，同步订单时，直接往线下柜子号中同步生成相应的柜子号记录（chest）',
    `bgmemo`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更备注',
    `review_payway` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查收费方式',
    `review_zk`     decimal(8, 2) NULL DEFAULT NULL COMMENT '复查优惠折扣',
    `template_jm`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板类型，分中心简码',
    `template_text` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板类型，分中心名称（用于显示）',
    `kdzl_name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开单助理用户名',
    `is_market`     tinyint(1) NULL DEFAULT NULL COMMENT '是否执行市价（如果执行市价，登记时添加的项目，价格都使用收费项目市场价）',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX           `CREATEORDER_JHJQC`(`jhjqc`) USING BTREE,
    INDEX           `CREATEORDER_JHJQD`(`jhjqd`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_current_queue_info
-- ----------------------------
DROP TABLE IF EXISTS `md_current_queue_info`;
CREATE TABLE `md_current_queue_info`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `create_date`  datetime(0) NULL DEFAULT NULL COMMENT '绑定时间',
    `user_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
    `patient_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '当前排队绑定信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_cust_feedback
-- ----------------------------
DROP TABLE IF EXISTS `md_cust_feedback`;
CREATE TABLE `md_cust_feedback`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '故障名称',
    `type`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '故障类型',
    `contact`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
    `phone`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
    `detail`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '故障详细',
    `faultdate`   datetime(0) NULL DEFAULT NULL COMMENT '故障时间',
    `submited`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理备注',
    `state`       char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态：0或null.未处理 1.已处理',
    `submit_date` datetime(0) NULL DEFAULT NULL COMMENT '处理日期',
    `submit_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人Id',
    `submit_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人姓名',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '故障反馈' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_customer_operate_history
-- ----------------------------
DROP TABLE IF EXISTS `crm_customer_operate_history`;
CREATE TABLE `crm_customer_operate_history`
(
    `operate_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人ID',
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `khdwid`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户ID',
    `khdwmc`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
    `operate_type`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型(名称)',
    `xsjlid`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `xsjlmc`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理名称',
    `operate_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人名称',
    `fprid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分配人ID',
    `fprmc`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分配人名称',
    `from_xsjlid`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '转移（原销售经理id）',
    `from_xsjlname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '转移（原销售经理用户名）',
    `trans_type`    tinyint(1) NULL DEFAULT NULL COMMENT '转移类型：0/未离职 1.已离职',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_customer_transfer
-- ----------------------------
DROP TABLE IF EXISTS `crm_customer_transfer`;
CREATE TABLE `crm_customer_transfer`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '转移时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `from_xsjlid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原销售经理id',
    `to_xsjlid`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更至销售经理id',
    `to_xsjl`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更至销售经理名称',
    `fzx_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    `xzzt`        tinyint(1) NULL DEFAULT NULL COMMENT '下载状态：0.未下载 1.已下载',
    `xzdate`      datetime(0) NULL DEFAULT NULL COMMENT '下载时间',
    `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户转移：转移时如果销售经理已离职，需要修改所有未登记的体检者的开单医师为新的销售经理。' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_customercommunicate
-- ----------------------------
DROP TABLE IF EXISTS `crm_customercommunicate`;
CREATE TABLE `crm_customercommunicate`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `khdwmc`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户预检跟踪ID',
    `khlxdh`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户联系电话',
    `sctjksrq`   datetime(0) NULL DEFAULT NULL COMMENT '上次体检开始日期',
    `gtjg`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '沟通结果',
    `gtrq`       datetime(0) NULL DEFAULT NULL COMMENT '沟通日期',
    `xcgtrq`     datetime(0) NULL DEFAULT NULL COMMENT '下次沟通日期',
    `bcgtfs`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '本次沟通方式',
    `xsjl`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理',
    `bz`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理id',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户沟通表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_customerfollow
-- ----------------------------
DROP TABLE IF EXISTS `crm_customerfollow`;
CREATE TABLE `crm_customerfollow`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `khdwmcid`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称ID',
    `khdwmc`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称',
    `zt`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题',
    `khfzr`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户负责人',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '便于维护是哪个销售经理填写的记录',
    `xsjl`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理',
    `gjjd`       varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '跟进阶段',
    `gjrq`       datetime(0) NULL DEFAULT NULL COMMENT '跟进日期',
    `gjnr`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '跟进内容',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `jsrq`       datetime(0) NULL DEFAULT NULL COMMENT '结束日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户跟踪表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_danager_object
-- ----------------------------
DROP TABLE IF EXISTS `md_danager_object`;
CREATE TABLE `md_danager_object`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `harm_type_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素分类名称',
    `harm_type_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素分类ID',
    `harm_name`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素名称',
    `harm_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素名称ID',
    `items`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称',
    `sellcustomer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `order_no`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
    `people_num`      int(0) NULL DEFAULT NULL COMMENT '应查人数',
    `inspect_num`     int(0) NULL DEFAULT NULL COMMENT '实查人数',
    `unexplored_num`  int(0) NULL DEFAULT NULL COMMENT '未查人数',
    `man_num`         int(0) NULL DEFAULT NULL COMMENT '男性人数',
    `women_num`       int(0) NULL DEFAULT NULL COMMENT '女性人数',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `onjob_type`      tinyint(1) NULL DEFAULT NULL COMMENT '上岗类型，详见：enums.com.center.medical.bean.OnjobType',
    `sample_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'TJ危害因素收费项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_describe
-- ----------------------------
DROP TABLE IF EXISTS `md_describe`;
CREATE TABLE `md_describe`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `item_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目ID',
    `item_name`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目打印名称',
    `inspect_describe` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查描述',
    `sign_list`        longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '用于存放该检查项目下的所有体证词所拼接的字符串。',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `dep_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `tjlx`             tinyint(1) NULL DEFAULT NULL COMMENT '体检类型：0.非职业 1.职业',
    `short_code`       int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    `dep_description`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室描述',
    `fee_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目Id',
    `fee_name`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目打印名称',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `DISCRIBE_PK_INDEX`(`id`) USING BTREE,
    INDEX              `DESCRIBE_CODE_DEP_INDEX`(`patientcode`, `dep_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS科室描述、检查结果表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_devicetype_position_checkitem
-- ----------------------------
DROP TABLE IF EXISTS `md_devicetype_position_checkitem`;
CREATE TABLE `md_devicetype_position_checkitem`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `devicetypename`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'devicetypename',
    `studypositionname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'studypositionname',
    `checkitemnname`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'checkitemnname',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '东软pacs部位方法基础表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_dictmarriage
-- ----------------------------
DROP TABLE IF EXISTS `md_dictmarriage`;
CREATE TABLE `md_dictmarriage`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `keymarriage`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYMARRIAGE',
    `marriage_name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻名称',
    `marriage_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻代码',
    `marriage_code2`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MARRIAGE_CODE2',
    `marriage_code3`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MARRIAGE_CODE3',
    `marriage_codehm` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MARRIAGE_CODEHM',
    `marriage_codex`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导出代码',
    `f_hasmarried`    tinyint(1) NULL DEFAULT NULL COMMENT '视同已婚：0或null.否 1.是',
    `input_code`      varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `note`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `status`          tinyint(1) NULL DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '婚姻表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_dictoccupation
-- ----------------------------
DROP TABLE IF EXISTS `md_dictoccupation`;
CREATE TABLE `md_dictoccupation`
(
    `key_occupation`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '健管代码',
    `occupation_name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业名称',
    `occupation_code`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业代码',
    `occupation_code2`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业代码2',
    `occupation_code3`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业代码3',
    `occupation_codehm` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'OCCUPATION_CODEHM',
    `occupationcodex`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导出代码',
    `input_code`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `note`              varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `status`            tinyint(1) NULL DEFAULT NULL COMMENT '状态',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `is_delete`         tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职业类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_dictpayway
-- ----------------------------
DROP TABLE IF EXISTS `md_dictpayway`;
CREATE TABLE `md_dictpayway`
(
    `id_payway`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '序号',
    `keypayway`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYPAYWAY',
    `payway_name`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式名称',
    `payway_code`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式代码: HIS接口标识',
    `payway_code2`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PAYWAY_CODE2',
    `payway_code3`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PAYWAY_CODE3',
    `payway_codehm`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PAYWAY_CODEHM',
    `paywaycodex`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导出代码',
    `keypayway2`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYPAYWAY2',
    `keypayway3`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYPAYWAY3',
    `id_payway2`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_PAYWAY2',
    `id_payway3`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_PAYWAY3',
    `paywayhis_code`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PAYWAYHIS_CODE',
    `paywayhis_name`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PAYWAYHIS_NAME',
    `f_hisinttransdisabled`    tinyint(1) NULL DEFAULT NULL COMMENT 'F_HISINTTRANSDISABLED',
    `id_medinsurance`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_MEDINSURANCE',
    `id_membercard`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_MEMBERCARD',
    `f_iscash`                 tinyint(1) NULL DEFAULT NULL COMMENT '现金',
    `f_isoffline`              tinyint(1) NULL DEFAULT NULL COMMENT '离线',
    `f_discountorfree`         tinyint(1) NULL DEFAULT NULL COMMENT 'F_DISCOUNTORFREE',
    `f_delayed`                tinyint(1) NULL DEFAULT NULL COMMENT 'F_DELAYED',
    `f_applytoaccountonly`     tinyint(1) NULL DEFAULT NULL COMMENT '财务专用',
    `f_applyto_outp`           tinyint(1) NULL DEFAULT NULL COMMENT '用于门诊',
    `f_applyto_inp`            tinyint(1) NULL DEFAULT NULL COMMENT '用于住院',
    `f_applyto_org`            tinyint(1) NULL DEFAULT NULL COMMENT '用于团检',
    `f_applyto_person`         tinyint(1) NULL DEFAULT NULL COMMENT '用于个人',
    `f_returntocash`           tinyint(1) NULL DEFAULT NULL COMMENT '可退现金',
    `f_builtin`                tinyint(1) NULL DEFAULT NULL COMMENT '内置',
    `f_disabled`               tinyint(1) NULL DEFAULT NULL COMMENT '禁用',
    `id_receipttype_org`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体发票ID',
    `id_receipttype_person`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人发票ID',
    `input_code`               varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `note`                     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `f_hideondailyreport`      tinyint(1) NULL DEFAULT NULL COMMENT '是否记录提成：0.否 1.是 (客服个检统计中只记录提成的)',
    `f_autoinputbox`           tinyint(1) NULL DEFAULT NULL COMMENT 'F_AUTOINPUTBOX',
    `plsx`                     int(0) NULL DEFAULT NULL COMMENT '排列顺序',
    `hisbfs`                   tinyint(1) NULL DEFAULT NULL COMMENT 'HIS不发送',
    `id`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `createdate`               datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`               datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `is_delete`                tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `is_change`                tinyint(1) NULL DEFAULT NULL COMMENT '是否可以编辑卡号：0.可以 1.不可以',
    `thing_kingdee_paywayname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个检金蝶支付名',
    `thing_kingdee_use_status` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个检金蝶状态',
    `group_kingdee_number`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团检金蝶编号',
    `group_kingdee_paywayname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团检金蝶支付名',
    `group_kingdee_use_status` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团检金蝶状态',
    `pos_kingdee_number`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记账结算金蝶编码',
    `pos_kingdee_paywayname`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记账结算金蝶支付名',
    `pos_kingdee_use_status`   varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记账结算金蝶状态',
    `kingdee_company`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手动维护上传时候的客户名',
    `kingdee_saleer`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金蝶业务员',
    `thing_kingdee_number`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个检金蝶编号',
    `vaccine`                  varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否上传疫苗',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC支付方式表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_dining_record
-- ----------------------------
DROP TABLE IF EXISTS `md_dining_record`;
CREATE TABLE `md_dining_record`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `dining_time` datetime(0) NULL DEFAULT NULL COMMENT '就餐时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '就餐记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_dlqk
-- ----------------------------
DROP TABLE IF EXISTS `md_dlqk`;
CREATE TABLE `md_dlqk`
(
    `bm`          char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
    `mc`          char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
    `create_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS锻炼情况' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_dmyh_result
-- ----------------------------
DROP TABLE IF EXISTS `md_dmyh_result`;
CREATE TABLE `md_dmyh_result`
(
    `ruxy`        char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'RUXY',
    `luxy`        char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LUXY',
    `rdxy`        char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'RDXY',
    `ldxy`        char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LDXY',
    `xl`          char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'XL',
    `rpwv`        char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'RPWV',
    `lpwv`        char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LPWV',
    `rabi`        char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'RABI',
    `labi`        char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LABI',
    `rtbi`        char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'RTBI',
    `ltbi`        char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LTBI',
    `height`      char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HEIGHT',
    `weight`      char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'WEIGHT',
    `sex`         char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SEX',
    `tai`         char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'TAI',
    `tinfo`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'TINFO',
    `tjrepno`     char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告单号',
    `age`         char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AGE',
    `c_date`      char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'C_DATE',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS动脉硬化结果' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_drinking_type
-- ----------------------------
DROP TABLE IF EXISTS `md_drinking_type`;
CREATE TABLE `md_drinking_type`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `type_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '种类名称',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '饮酒种类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_drug_and_fzx
-- ----------------------------
DROP TABLE IF EXISTS `md_drug_and_fzx`;
CREATE TABLE `md_drug_and_fzx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `drug_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药物id',
    `fzx_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    `tbzt`       tinyint(1) NULL DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '药品分中心映射' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_drug_disease
-- ----------------------------
DROP TABLE IF EXISTS `md_drug_disease`;
CREATE TABLE `md_drug_disease`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
    `health_evaluation` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '禁忌疾病名称',
    `input_code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `type_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '禁忌疾病类型ID',
    `is_delete`         tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '禁忌疾病' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_drug_disease_type
-- ----------------------------
DROP TABLE IF EXISTS `md_drug_disease_type`;
CREATE TABLE `md_drug_disease_type`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `health_evaluation` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '禁忌疾病分类名称',
    `input_code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '禁忌疾病分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_drugstore_class
-- ----------------------------
DROP TABLE IF EXISTS `md_drugstore_class`;
CREATE TABLE `md_drugstore_class`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `class_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类别编号',
    `class_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类别名称',
    `short_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拼音码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '药品分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_drugstore_drug
-- ----------------------------
DROP TABLE IF EXISTS `md_drugstore_drug`;
CREATE TABLE `md_drugstore_drug`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `drug_class`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药瓶分类（代号）',
    `drug_name`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品名称',
    `drug_standard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格',
    `drug_price`    decimal(8, 2) NULL DEFAULT NULL COMMENT '药品单价',
    `drug_place`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '产地',
    `drug_type`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '剂型',
    `drug_unit`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
    `retail_price`  decimal(8, 2) NULL DEFAULT NULL COMMENT '零售价',
    `cost_price`    decimal(8, 2) NULL DEFAULT NULL COMMENT '成本价',
    `stock`         int(0) NULL DEFAULT NULL COMMENT '库存',
    `is_forbidden`  tinyint(1) NULL DEFAULT NULL COMMENT '是否禁用：0或null.否 1.是',
    `input_code`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拼音码',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX           `DRUG_DCLASS`(`drug_class`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '药品基础表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_drugstore_prescribe
-- ----------------------------
DROP TABLE IF EXISTS `md_drugstore_prescribe`;
CREATE TABLE `md_drugstore_prescribe`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `drug_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品ID',
    `username`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医生用户名',
    `patientcode`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `reason`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '开药原因',
    `prescribe_time`    datetime(0) NULL DEFAULT NULL COMMENT '开药时间',
    `prescribe_num`     int(0) NULL DEFAULT NULL COMMENT '开药数量',
    `transaction_price` int(0) NULL DEFAULT NULL COMMENT '成交价',
    `is_finished`       tinyint(1) NULL DEFAULT NULL COMMENT '是否已开药：0.否 1.已成交 2.未成交 3.退药',
    `prescribe_note`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '开药记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_dw_harm
-- ----------------------------
DROP TABLE IF EXISTS `md_dw_harm`;
CREATE TABLE `md_dw_harm`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID号',
    `harm_code`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素代码',
    `harm_name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素名字',
    `harm_class`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '种类名字',
    `input_code`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `keyword`            varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYWORD',
    `diagnosis_from`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '诊断结论(依据)',
    `mbjb_zyb`           varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MBJB_ZYB',
    `mbjb_jjz`           varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MBJB_JJZ',
    `influence`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '影响',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `diagnosis`          varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '诊断依据',
    `industrial_disease` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病',
    `contraindication`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '禁忌症',
    `affect`             varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对健康影响',
    `company_name`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户公司名称',
    `company_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户公司ID',
    `company_department` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司部门',
    `department_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司部门ID',
    `class_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素种类ID',
    `khdwsrm`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位代码',
    `note`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `is_delete`          tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '单位危害因素' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_education
-- ----------------------------
DROP TABLE IF EXISTS `md_education`;
CREATE TABLE `md_education`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `keyeducation`     varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYEDUCATION',
    `education_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教育名称',
    `education_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教育编码',
    `education_code2`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EDUCATION_CODE2',
    `education_code3`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EDUCATION_CODE3',
    `education_codehm` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EDUCATION_CODEHM',
    `education_codex`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教育导出代码',
    `input_code`       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `note`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '教育程度' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_electro_audiometer
-- ----------------------------
DROP TABLE IF EXISTS `md_electro_audiometer`;
CREATE TABLE `md_electro_audiometer`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `dep_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `id_examtype`    varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型ID：0.健康体检 1.职业体检 2.综合',
    `patientcode`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `bone_left250`   decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导左耳250Hz',
    `air_left125`    decimal(3, 0) NULL DEFAULT NULL COMMENT '气导左耳125Hz',
    `air_right125`   decimal(3, 0) NULL DEFAULT NULL COMMENT '气导右耳125Hz',
    `bone_right250`  decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导右耳250Hz',
    `bone_left4000`  decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导左耳4000Hz',
    `bone_right4000` decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导右耳4000Hz',
    `bone_left2000`  decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导左耳2000Hz',
    `bone_right2000` decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导右耳2000Hz',
    `bone_left1000`  decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导左耳1000Hz',
    `bone_right1000` decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导右耳1000Hz',
    `bone_left500`   decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导左耳500Hz',
    `bone_right500`  decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导右耳500Hz',
    `air_left250`    decimal(3, 0) NULL DEFAULT NULL COMMENT '气导左耳250Hz',
    `air_right250`   decimal(3, 0) NULL DEFAULT NULL COMMENT '气导右耳250Hz',
    `air_left500`    decimal(3, 0) NULL DEFAULT NULL COMMENT '气导左耳500Hz',
    `air_right500`   decimal(3, 0) NULL DEFAULT NULL COMMENT '气导右耳500Hz',
    `air_left1000`   decimal(3, 0) NULL DEFAULT NULL COMMENT '气导左耳1000Hz',
    `air_right1000`  decimal(3, 0) NULL DEFAULT NULL COMMENT '气导右耳1000Hz',
    `air_left2000`   decimal(3, 0) NULL DEFAULT NULL COMMENT '气导左耳2000Hz',
    `air_right2000`  decimal(3, 0) NULL DEFAULT NULL COMMENT '气导右耳2000Hz',
    `air_left4000`   decimal(3, 0) NULL DEFAULT NULL COMMENT '气导左耳4000Hz',
    `air_right4000`  decimal(3, 0) NULL DEFAULT NULL COMMENT '气导右耳4000Hz',
    `air_left8000`   decimal(3, 0) NULL DEFAULT NULL COMMENT '气导左耳8000Hz',
    `air_right8000`  decimal(3, 0) NULL DEFAULT NULL COMMENT '气导右耳8000Hz',
    `air_left3000`   decimal(3, 0) NULL DEFAULT NULL COMMENT '气导左耳3000Hz',
    `air_right3000`  decimal(3, 0) NULL DEFAULT NULL COMMENT '气导右耳3000Hz',
    `air_left6000`   decimal(3, 0) NULL DEFAULT NULL COMMENT '气导左耳6000Hz',
    `describe`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注说明',
    `test_result`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果评定',
    `bone_left3000`  decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导左耳3000Hz',
    `air_right6000`  decimal(3, 0) NULL DEFAULT NULL COMMENT '气导右耳6000Hz',
    `bone_right3000` decimal(3, 0) NULL DEFAULT NULL COMMENT '骨导右耳3000Hz2',
    `air_img_path`   varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '气导折线图路径',
    `bone_img_path`  varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '骨导折线图路径',
    `short_code`     int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX            `IDX_AUDIOMETER_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS电测听' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_emphasis_ask_symptom
-- ----------------------------
DROP TABLE IF EXISTS `md_emphasis_ask_symptom`;
CREATE TABLE `md_emphasis_ask_symptom`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `danager_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素种类ID',
    `disiase_type` decimal(1, 0) NULL DEFAULT NULL COMMENT '职业体检类别，详见：enums.com.center.medical.bean.MedicalType',
    `symptom_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '症状ID',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `harmname`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素名称',
    `is_delete`    tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `symptomcode`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '症状代码',
    `symptom`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '症状名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC重点询问症状表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_exam_and_fzx
-- ----------------------------
DROP TABLE IF EXISTS `md_exam_and_fzx`;
CREATE TABLE `md_exam_and_fzx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `exam_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目ID',
    `fzx_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `tbzt`       tinyint(1) NULL DEFAULT NULL COMMENT '同步状态：0或NULL 未同步 1.已同步 ',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '检查项目和分中心关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_expresscompany
-- ----------------------------
DROP TABLE IF EXISTS `md_expresscompany`;
CREATE TABLE `md_expresscompany`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `express_name`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知方式',
    `express_phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
    `input_code`    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '快递公司表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fail_total_visit
-- ----------------------------
DROP TABLE IF EXISTS `md_fail_total_visit`;
CREATE TABLE `md_fail_total_visit`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `visit_type`    tinyint(1) NULL DEFAULT NULL COMMENT '回访类型',
    `personcode`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `visit_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访人ID',
    `memo`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访备注',
    `visit_time`    datetime(0) NULL DEFAULT NULL COMMENT '回访时间',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更改时间',
    `visit_main_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '迟捡回访人员表ID|阳性结果回访riskclientId(弃用)',
    `sflj`          decimal(65, 30) NULL DEFAULT NULL COMMENT '处理结果',
    `ljsj`          datetime(0) NULL DEFAULT NULL COMMENT '来检时间',
    `visit_text`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '跟进内容',
    `visit_result`  tinyint(1) NULL DEFAULT NULL COMMENT '跟进结果',
    `notice_again`  tinyint(1) NULL DEFAULT NULL COMMENT '再通知',
    `pre_time`      datetime(0) NULL DEFAULT NULL COMMENT '预处理时间',
    `pre_result`    tinyint(1) NULL DEFAULT NULL COMMENT '预处理结果',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX           `IDX_FAILTOTALVISIT_CODE`(`personcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KF迟捡、阳性、不合格样本回访' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_family_card_charge
-- ----------------------------
DROP TABLE IF EXISTS `md_family_card_charge`;
CREATE TABLE `md_family_card_charge`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `cardno`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
    `charger_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人用户名',
    `charge_time`      datetime(0) NULL DEFAULT NULL COMMENT '充值/消费时间',
    `note`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `id_payway`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款方式id',
    `money`            decimal(11, 2) NULL DEFAULT NULL COMMENT '金额',
    `type`             tinyint(1) NULL DEFAULT NULL COMMENT '操作类型：0.充值 1.消费 2.退费',
    `jf`               decimal(11, 2) NULL DEFAULT NULL COMMENT '积分(充值正、 消费负)',
    `start_jf`         decimal(11, 2) NULL DEFAULT NULL COMMENT '充值前积分',
    `end_jf`           decimal(11, 2) NULL DEFAULT NULL COMMENT '充值后积分',
    `start_money`      decimal(11, 2) NULL DEFAULT NULL COMMENT '充值前金额',
    `end_money`        decimal(11, 2) NULL DEFAULT NULL COMMENT '充值后金额',
    `patientcode`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `consumetype`      char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消费类型，详见：enums.com.center.medical.bean.CardConsumeType',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '家庭卡充值记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_file_change_record
-- ----------------------------
DROP TABLE IF EXISTS `md_file_change_record`;
CREATE TABLE `md_file_change_record`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `status`     tinyint(1) NULL DEFAULT NULL COMMENT '同步状态：0.未同步 1.已同步',
    `dir`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '目录',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '记录有文件改变的文件目录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_financeinput
-- ----------------------------
DROP TABLE IF EXISTS `crm_financeinput`;
CREATE TABLE `crm_financeinput`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `year`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年份',
    `yy`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '1月',
    `ey`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '2月',
    `sy`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '3月',
    `iy`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '4月',
    `wy`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '5月',
    `ly`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '6月',
    `qy`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '7月',
    `ay`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '8月',
    `jy`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '9月',
    `oy`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '10月',
    `ny`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '11月',
    `dy`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '12月',
    `bz`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '销售财务录入表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_follow_up
-- ----------------------------
DROP TABLE IF EXISTS `md_follow_up`;
CREATE TABLE `md_follow_up`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
    `follow_up_code` int(0) NULL DEFAULT NULL COMMENT '随访代码',
    `follow_up_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '随访目的名称',
    `input_code`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC随访目的维护' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_completion
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_completion`;
CREATE TABLE `md_fx_completion`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检码',
    `sample_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    `patientname`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `sex`                varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `age`                int(0) NULL DEFAULT NULL COMMENT '年龄',
    `jhgl`               int(0) NULL DEFAULT NULL COMMENT '接害工龄',
    `jhys`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接害因素',
    `jhys_ids`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接害因素',
    `id_org`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `org_name`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体名称',
    `org_depart`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体部门',
    `marriage`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻',
    `id_marriage`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻ID',
    `unchecked_items`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '未检项目（名称）',
    `unchecked_itemids`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '未检项目（ID）',
    `medicaldate`        datetime(0) NULL DEFAULT NULL COMMENT '体检时间',
    `f_examstarted`      tinyint(1) NULL DEFAULT NULL COMMENT '已开始体检：0或null.否 1.是',
    `f_readytofinal`     tinyint(1) NULL DEFAULT NULL COMMENT '分检完成：0或null.否 1.是',
    `zytjzt`             tinyint(0) NULL DEFAULT NULL COMMENT '职业体检状态，详见：enums.com.center.medical.bean.ExamStatus',
    `trades`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工种',
    `dateregister`       datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `is_print`           tinyint(1) NULL DEFAULT NULL COMMENT '报告是否已打印：0.未打印 1.已打印',
    `jktjzt`             tinyint(0) NULL DEFAULT NULL COMMENT '健康体检状态',
    `registered`         tinyint(1) NULL DEFAULT NULL COMMENT '已登记：0.未登记 1.已登记',
    `f_registered`       tinyint(1) NULL DEFAULT NULL COMMENT '已登记：0.未登记 1.已登记',
    `dateregisternotime` datetime(0) NULL DEFAULT NULL COMMENT '职业总检时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '本次职业健康检查漏检人员及漏检项目人员一览表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_detection
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_detection`;
CREATE TABLE `md_fx_detection`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `sample_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    `dep_name`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室名称',
    `dep_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `conclusion`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论',
    `check_male`       decimal(6, 4) NULL DEFAULT NULL COMMENT '总体百分比-男',
    `check_female`     decimal(6, 4) NULL DEFAULT NULL COMMENT '总体百分比-女',
    `check_total`      decimal(6, 6) NULL DEFAULT NULL COMMENT '总体百分比-合计',
    `all_male`         int(0) NULL DEFAULT NULL COMMENT '总体人数-男',
    `all_female`       int(0) NULL DEFAULT NULL COMMENT '总体人数-女',
    `all_total`        int(0) NULL DEFAULT NULL COMMENT '总体人数-合计',
    `detection_male`   int(0) NULL DEFAULT NULL COMMENT '检出人数-男',
    `detection_female` int(0) NULL DEFAULT NULL COMMENT '检出人数-女',
    `detection_total`  int(0) NULL DEFAULT NULL COMMENT '检出人数-合计',
    `patientcode`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `patientname`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `sex`              tinyint(1) NULL DEFAULT NULL COMMENT '性别',
    `age`              int(0) NULL DEFAULT NULL COMMENT '年龄',
    `org_depart`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门',
    `basconclusion_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论ID',
    `report_sort`      int(0) NULL DEFAULT NULL COMMENT '部门排序',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '综合分析-检出统计、团体小结（健康）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_detectionzy
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_detectionzy`;
CREATE TABLE `md_fx_detectionzy`
(
    `id`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `createdate`           datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`           datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `sample_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    `disease`              varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疾病名称',
    `check_num`            int(0) NULL DEFAULT NULL COMMENT '检出人数',
    `occupation_diseast`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '建议',
    `occupation_diagnosis` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素ID',
    `harm_name`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素名称',
    `harm_num`             int(0) NULL DEFAULT NULL COMMENT '危害因素接触人数',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'TJ综合分析-检出人数（职业）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_harm
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_harm`;
CREATE TABLE `md_fx_harm`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `harm_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素id',
    `sample_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本id',
    `person_num` int(0) NULL DEFAULT NULL COMMENT '人数',
    `harm_name`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素名称',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `IDX_FX_HARM_SAMPLEID`(`sample_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职业团检分析-危害因素' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_itemscheck
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_itemscheck`;
CREATE TABLE `md_fx_itemscheck`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `sample_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    `dep_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室名称',
    `dep_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `item_name`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称',
    `item_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    `check_male`   int(0) NULL DEFAULT NULL COMMENT '该项检出人数-男',
    `check_female` int(0) NULL DEFAULT NULL COMMENT '该项检出人数-女',
    `check_total`  int(0) NULL DEFAULT NULL COMMENT '该项检出人数-合计',
    `all_male`     int(0) NULL DEFAULT NULL COMMENT '总体參检人数-男',
    `all_female`   int(0) NULL DEFAULT NULL COMMENT '总体參检人数-女',
    `all_total`    int(0) NULL DEFAULT NULL COMMENT '总体參检人数-合计',
    `per_male`     decimal(6, 3) NULL DEFAULT NULL COMMENT '该项參检比例-男',
    `per_female`   decimal(6, 3) NULL DEFAULT NULL COMMENT '该项參检比例-女',
    `per_total`    decimal(6, 3) NULL DEFAULT NULL COMMENT '该项參检比例-合计',
    `report_sort`  int(0) NULL DEFAULT NULL COMMENT '科室报告排序',
    `rowno`        int(0) NULL DEFAULT NULL COMMENT '行号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '综合分析-项目參检（健康）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_negative
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_negative`;
CREATE TABLE `md_fx_negative`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `sample_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `dateregister`    datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `id_org`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `org_name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体名称',
    `org_depart`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体部门',
    `patientcode`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `patientname`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `sex`             varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `age`             int(0) NULL DEFAULT NULL COMMENT '年龄',
    `positive_result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '阴性结果',
    `verdict`         longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '结论',
    `offer`           longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总检建议',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '综合分析-阴性小结' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_personnelview
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_personnelview`;
CREATE TABLE `md_fx_personnelview`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `sample_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    `positives`          longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查结果',
    `summary_text`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '处理意见',
    `summary_serialno`   int(0) NULL DEFAULT NULL COMMENT '结论（序列号）',
    `occupation_summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论(名称)',
    `patientname`        varchar(52) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `sex`                varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `age`                int(0) NULL DEFAULT NULL COMMENT '年龄',
    `jhgl`               int(0) NULL DEFAULT NULL COMMENT '接害工龄',
    `jhys`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接害因素',
    `jhys_ids`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接害因素',
    `trades`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工种',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '综合分析-人员一览表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_positive
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_positive`;
CREATE TABLE `md_fx_positive`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `sample_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `dateregister`    datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `id_org`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `org_name`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体名称',
    `org_depart`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体部门',
    `patientcode`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `patientname`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `sex`             varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `age`             int(0) NULL DEFAULT NULL COMMENT '年龄',
    `positive_result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '阳性结果',
    `verdict`         longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '结论',
    `offer`           longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总检建议',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '综合分析-阳性小结' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_review_info
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_review_info`;
CREATE TABLE `md_fx_review_info`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `harm_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素ID',
    `harm_name`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素名称',
    `diagnosis`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业禁忌症名称',
    `num`          int(0) NULL DEFAULT NULL COMMENT '人数',
    `summary_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '建议',
    `sample_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职业健康检查职业病危害效应相关指标异常需要复查人员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_reviewsituation
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_reviewsituation`;
CREATE TABLE `md_fx_reviewsituation`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `patientcode`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `sample_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    `positives`          longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查结果',
    `summary_text`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '处理意见',
    `occupation_summary` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论(名称)',
    `patientname`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `sex`                varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `age`                int(0) NULL DEFAULT NULL COMMENT '年龄',
    `jhys`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接害因素',
    `jhys_ids`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接害因素',
    `review_status`      varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查情况(未复查，复查未完成，复查已完成)',
    `summary_serialno`   int(0) NULL DEFAULT NULL COMMENT '结论（序列号）',
    `trades`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工种',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '本次职业健康检查复查人员复查情况一览表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fx_summary
-- ----------------------------
DROP TABLE IF EXISTS `md_fx_summary`;
CREATE TABLE `md_fx_summary`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `sample_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本ID',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `regimentation_note` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业体检类别(工作状态名称)',
    `harm_name`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素名称',
    `harm_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素ID',
    `org_depart`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体部门',
    `yszyb`              int(0) NULL DEFAULT NULL COMMENT '疑似职业病人数',
    `zyjjz`              int(0) NULL DEFAULT NULL COMMENT '职业禁忌症人数',
    `fc`                 int(0) NULL DEFAULT NULL COMMENT '复查人数',
    `qtjb`               int(0) NULL DEFAULT NULL COMMENT '其他疾病或异常人数',
    `wjyc`               int(0) NULL DEFAULT NULL COMMENT '目前未见异常人数',
    `class_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素种类ID',
    `class_name`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素种类名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '本次职业健康检查危害因素人员检查情况汇总一览表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_fylx
-- ----------------------------
DROP TABLE IF EXISTS `md_fylx`;
CREATE TABLE `md_fylx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `name`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
    `srm`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC费用类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_group_and_fzx
-- ----------------------------
DROP TABLE IF EXISTS `md_group_and_fzx`;
CREATE TABLE `md_group_and_fzx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `group_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组ID',
    `fzx_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `xzzt`       tinyint(1) NULL DEFAULT NULL COMMENT '下载状态：0.未下载 1.已下载',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分组分中心' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_group_balance
-- ----------------------------
DROP TABLE IF EXISTS `md_group_balance`;
CREATE TABLE `md_group_balance`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `order_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单id',
    `type_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费方式ID(弃用)',
    `reality_money`  decimal(16, 2) NULL DEFAULT NULL COMMENT '实收金额(结算金额)',
    `prepaid_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '是预缴金(待用)',
    `getter_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员ID(弃用)',
    `getter_time`    datetime(0) NULL DEFAULT NULL COMMENT '收费时间(弃用)',
    `memo`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注(弃用)',
    `is_square`      tinyint(1) NULL DEFAULT NULL COMMENT '是否已结清：0或null.否 1.是',
    `return_money`   decimal(16, 2) NULL DEFAULT NULL COMMENT '退费金额(弃用)',
    `group_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `should_money`   decimal(16, 2) NULL DEFAULT NULL COMMENT '应收金额 (弃用：应该是动态计算所有统收已收的金额)',
    `card_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡ID(弃用)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检团体结算表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_group_review_notice
-- ----------------------------
DROP TABLE IF EXISTS `md_group_review_notice`;
CREATE TABLE `md_group_review_notice`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `start_date`  datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
    `end_date`    datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
    `customer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `status`      tinyint(1) NULL DEFAULT NULL COMMENT '状态',
    `error_msg`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误信息',
    `config_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置id',
    `url`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'url',
    `ddh`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ddh',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `IDX_GROUPREVIEW_CREATEDATE`(`createdate`) USING BTREE,
    INDEX         `IDX_GROUPREVIEW_CUSTOMERID`(`customer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '批量职业健康检查复查通知书' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_group_review_notice_patient
-- ----------------------------
DROP TABLE IF EXISTS `md_group_review_notice_patient`;
CREATE TABLE `md_group_review_notice_patient`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `main_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人员id',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `IDX_GROUPREVIEWPATIENT_MAIN`(`main_id`) USING BTREE,
    INDEX         `IDX_GROUPREVIEWPATIENT_PATIENT`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '批量复查通知人员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_handle_new_projects
-- ----------------------------
DROP TABLE IF EXISTS `md_handle_new_projects`;
CREATE TABLE `md_handle_new_projects`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `create_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `projects_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目ID',
    `add_doctor_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '加项医生',
    `is_handle`      tinyint(1) NULL DEFAULT NULL COMMENT '是否已处理：0.未处理 1.已处理',
    `handle_time`    datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
    `handle_name_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人',
    `status`         tinyint(1) NULL DEFAULT NULL COMMENT '处理状态',
    `idea`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理意见',
    `is_delete`      tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `handle_type`    tinyint(1) NULL DEFAULT NULL COMMENT '加项处理类型，详见：enums.com.center.medical.bean.NewProjectHandleType',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS检验科加项处理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_harm
-- ----------------------------
DROP TABLE IF EXISTS `md_harm`;
CREATE TABLE `md_harm`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID号',
    `harm_code`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素代码',
    `harm_name`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素名字',
    `harm_class`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '种类名字',
    `input_code`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `keyword`            varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYWORD',
    `diagnosis_from`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'DIAGNOSIS_FROM',
    `mbjb_zyb`           varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MBJB_ZYB',
    `mbjb_jjz`           varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MBJB_JJZ',
    `influence`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '影响',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `diagnosis`          varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '诊断依据',
    `industrial_disease` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病',
    `contraindication`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '禁忌症',
    `affect`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '对健康影响',
    `class_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素种类ID',
    `is_delete`          tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `note`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `is_public`          tinyint(1) NULL DEFAULT NULL COMMENT '是否是公共的：0或null.否 1.是',
    `fzx_ids`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心IDs，逗号拼接',
    `creater`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人用户名',
    `create_fzx`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '初创中心',
    `pid`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级id',
    `lv`                 tinyint(1) NULL DEFAULT NULL COMMENT '级别：1.级父级  2.级子级',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC危害因素' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_harm_and_fzx
-- ----------------------------
DROP TABLE IF EXISTS `md_harm_and_fzx`;
CREATE TABLE `md_harm_and_fzx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `harm_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    `fzx_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `tbzt`       tinyint(1) NULL DEFAULT NULL COMMENT '同步状态：0.未同步 1.已同步',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '危害因素和分中心' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_harm_package_match
-- ----------------------------
DROP TABLE IF EXISTS `md_harm_package_match`;
CREATE TABLE `md_harm_package_match`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `tcid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐id',
    `harm_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接害因素id',
    `pharm_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级接害因素id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `IDX_CMM_PHARMID`(`pharm_id`) USING BTREE,
    INDEX        `IDX_CMM_TCID`(`tcid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '危害因素-套餐匹配表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_inspect_charge
-- ----------------------------
DROP TABLE IF EXISTS `md_inspect_charge`;
CREATE TABLE `md_inspect_charge`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `charge_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    `inspect_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目ID',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`   tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `order_index` int(0) NULL DEFAULT NULL COMMENT '顺序',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `INSCHARGE_CHARGE`(`charge_id`) USING BTREE,
    INDEX         `INSCHARGE_INSPPECT`(`inspect_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC检查项目收费项目关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_inspect_report
-- ----------------------------
DROP TABLE IF EXISTS `md_inspect_report`;
CREATE TABLE `md_inspect_report`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `print_time`  datetime(0) NULL DEFAULT NULL COMMENT '最近打印时间',
    `print_count` int(0) NULL DEFAULT NULL COMMENT '打印次数',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `IDX_INSPECT_REPORT_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '检验报告生成记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_interface_account
-- ----------------------------
DROP TABLE IF EXISTS `md_interface_account`;
CREATE TABLE `md_interface_account`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `username`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `password`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码（固定32位）',
    `key`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密钥',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '各种接口加密信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_items
-- ----------------------------
DROP TABLE IF EXISTS `md_items`;
CREATE TABLE `md_items`
(
    `id`                           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `basexamltem_id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目ID',
    `sysmanmark`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统维护标记',
    `keyexamfeeitem`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYEXAMFEEITEM',
    `examfeeitem_name`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称',
    `examfeeitem_nameabr`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称缩写',
    `examfeeitem_nameprn`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目打印名称',
    `examfeeitem_nameeng`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目英语名称',
    `examfeeitem_nameengabr`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目英语名称缩写',
    `examfeeitem_code`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目代码',
    `examfeeitem_code2`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目代码二',
    `examfeeitem_code3`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMFEEITEM_CODE3',
    `examfeeitem_codehm`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部位ID',
    `examfeeitem_codex`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否在APP出现：1.是  0或null.否',
    `examfeeitem_feecode`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目收费代码',
    `examfeeitem_feecodesub`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMFEEITEM_FEECODESUB',
    `examfeeitem_pricecode`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMFEEITEM_PRICECODE',
    `examfeeitem_pricecodesub`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMFEEITEM_PRICECODESUB',
    `examfeeitem_hisname`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目HIS名称',
    `examfeeitem_class`            varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条码打印分类id',
    `id_reportitem`                decimal(22, 16) NULL DEFAULT NULL COMMENT '报告项目ID',
    `x_xxdm`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '创建人',
    `f_discountdisabled`           tinyint(1) NULL DEFAULT NULL COMMENT '禁止打折字段，1禁止打折',
    `f_maxoffpercentlimited`       tinyint(1) NULL DEFAULT NULL COMMENT 'F_MAXOFFPERCENTLIMITED',
    `maxoffpercent`                decimal(22, 16) NULL DEFAULT NULL COMMENT 'MAXOFFPERCENT',
    `unitprice`                    decimal(16, 4) NULL DEFAULT NULL COMMENT '价格',
    `suiteprice`                   decimal(16, 4) NULL DEFAULT NULL COMMENT '套餐价格',
    `specialprice`                 decimal(8, 2) NULL DEFAULT NULL COMMENT 'SPECIALPRICE',
    `foreignprice`                 decimal(8, 2) NULL DEFAULT NULL COMMENT 'FOREIGNPRICE',
    `preferprice`                  decimal(8, 2) NULL DEFAULT NULL COMMENT 'PREFERPRICE',
    `innerprice`                   decimal(8, 2) NULL DEFAULT NULL COMMENT 'INNERPRICE',
    `materialprice`                decimal(16, 4) NULL DEFAULT NULL COMMENT '耗材价格',
    `costprice`                    decimal(16, 4) NULL DEFAULT NULL COMMENT '成本价',
    `coopprice`                    decimal(16, 4) NULL DEFAULT NULL COMMENT '外部价',
    `id_cooporg`                   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '外送机构ID',
    `exampoint`                    decimal(8, 2) NULL DEFAULT NULL COMMENT 'EXAMPOINT',
    `x_departcode`                 varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'X_DEPARTCODE',
    `id_depart`                    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属科室ID',
    `depart_name`                  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '所属科室名称',
    `x_ybdm`                       varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'X_YBDM',
    `id_guidesheetgroup`           int(0) NULL DEFAULT NULL COMMENT 'ID_GUIDESHEETGROUP',
    `id_guidesheetgroupsub`        int(0) NULL DEFAULT NULL COMMENT 'ID_GUIDESHEETGROUPSUB',
    `guidesheetgroupset`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GUIDESHEETGROUPSET',
    `guidesheetgroupsetdisporder`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GUIDESHEETGROUPSETDISPORDER',
    `id_labtype`                   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标本类型ID',
    `labtype_r`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标本类型名称',
    `labtype_sub`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'app分类ID',
    `guidesheelabtypeset`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GUIDESHEELABTYPESET',
    `guidesheelabtypesetdisporder` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GUIDESHEELABTYPESETDISPORDER',
    `hisopendepartname`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HISOPENDEPARTNAME',
    `hisopendepartcode`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HISOPENDEPARTCODE',
    `hisexecdepartname`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行科室名称',
    `hisexecdepartcode`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行科室代码',
    `hisexecworkgroupname`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作组名称',
    `hisexecworkgroupcode`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作组代码',
    `hisexecworkstationname`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作站名称',
    `hisexecworkstationcode`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作站代码',
    `hisexecworkbenchname`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作台名称',
    `hisexecworkbenchcode`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作台代码',
    `input_code`                   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `inputcodeb`                   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INPUTCODEB',
    `inputcodec`                   varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INPUTCODEC',
    `inputcoded`                   varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INPUTCODED',
    `inputcodee`                   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
    `f_reportalone`                tinyint(1) NULL DEFAULT NULL COMMENT '独立报告',
    `f_male`                       tinyint(1) NULL DEFAULT NULL COMMENT '男性项目',
    `f_female`                     tinyint(1) NULL DEFAULT NULL COMMENT '女性项目',
    `f_alert`                      tinyint(1) NULL DEFAULT NULL COMMENT '警示项目',
    `warningmsg`                   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '警示信息',
    `f_autovip`                    tinyint(1) NULL DEFAULT NULL COMMENT '自动VIP项目',
    `f_nonexam`                    tinyint(1) NULL DEFAULT NULL COMMENT '非检查项目',
    `f_isdrug`                     tinyint(1) NULL DEFAULT NULL COMMENT '是否药品',
    `f_isforask`                   tinyint(1) NULL DEFAULT NULL COMMENT '是否问卷',
    `f_hideinguidesheet`           tinyint(1) NULL DEFAULT NULL COMMENT '指引单不打印',
    `breakfastorder`               char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '早餐顺序',
    `f_disabled`                   tinyint(1) NULL DEFAULT NULL COMMENT '禁用',
    `f_invisible`                  tinyint(1) NULL DEFAULT NULL COMMENT 'F_INVISIBLE',
    `disporder`                    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行序',
    `note`                         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
    `id_classoffeeitem`            int(0) NULL DEFAULT NULL COMMENT '收费项目分类ID',
    `id_classofreceipt`            int(0) NULL DEFAULT NULL COMMENT '发票项目分类ID',
    `id_classofaccount`            int(0) NULL DEFAULT NULL COMMENT 'ID_CLASSOFACCOUNT',
    `id_feeitemclass2`             int(0) NULL DEFAULT NULL COMMENT 'ID_FEEITEMCLASS2',
    `id_feeitemclass3`             int(0) NULL DEFAULT NULL COMMENT 'ID_FEEITEMCLASS3',
    `lookupwarning`                varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LOOKUPWARNING',
    `guidesheetcode`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '必选项目ids',
    `limitedinexamplaceidlist`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LIMITEDINEXAMPLACEIDLIST',
    `f_qtysettable`                tinyint(1) NULL DEFAULT NULL COMMENT 'F_QTYSETTABLE',
    `f_doctorbyfeeitem`            tinyint(1) NULL DEFAULT NULL COMMENT 'F_DOCTORBYFEEITEM',
    `f_dosplitting`                tinyint(1) NULL DEFAULT NULL COMMENT 'F_DOSPLITTING',
    `specification`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SPECIFICATION',
    `measureunit`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MEASUREUNIT',
    `numpricelistneeded`           bigint(0) NULL DEFAULT NULL COMMENT 'NUMPRICELISTNEEDED',
    `numpricelistincomplete`       bigint(0) NULL DEFAULT NULL COMMENT 'NUMPRICELISTINCOMPLETE',
    `numusedfordeveloper`          bigint(0) NULL DEFAULT NULL COMMENT 'NUMUSEDFORDEVELOPER',
    `f_dontupdate`                 tinyint(1) NULL DEFAULT NULL COMMENT 'F_DONTUPDATE',
    `f_dontupdatepricedetail`      tinyint(1) NULL DEFAULT NULL COMMENT 'F_DONTUPDATEPRICEDETAIL',
    `f_comparereport`              tinyint(1) NULL DEFAULT NULL COMMENT 'F_COMPAREREPORT',
    `f_guidesheetbackupsingleitem` tinyint(1) NULL DEFAULT NULL COMMENT 'F_GUIDESHEETBACKUPSINGLEITEM',
    `dt_lastautoinsert`            datetime(0) NULL DEFAULT NULL COMMENT '上次统计检查次数时间（体检者收费项目创建时间）',
    `dt_lastautoupdate`            datetime(0) NULL DEFAULT NULL COMMENT 'DT_LASTAUTOUPDATE',
    `dt_lastautoupdatedetail`      datetime(0) NULL DEFAULT NULL COMMENT 'DT_LASTAUTOUPDATEDETAIL',
    `jz`                           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健值',
    `sfxmsrm`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '收费项目输入码',
    `xmdymc`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '项目打印名称',
    `dydfz`                        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '导引单分组',
    `xh`                           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '序号',
    `dyddybs`                      varchar(155) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导引单打印标示',
    `zk`                           decimal(16, 4) NULL DEFAULT NULL COMMENT '折扣',
    `txjg`                         decimal(16, 4) NULL DEFAULT NULL COMMENT '特需价格',
    `wbjg`                         decimal(16, 4) NULL DEFAULT NULL COMMENT '外宾价格',
    `ydjg`                         decimal(16, 4) NULL DEFAULT NULL COMMENT '优待价格',
    `nbj`                          decimal(16, 4) NULL DEFAULT NULL COMMENT '内部价',
    `zybm`                         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业编码',
    `yblx`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '样本类型',
    `dlbs`                         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '独立标示',
    `yblxmc`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '样本类型名称',
    `fylx`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '费用类型',
    `jcyy`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查意义',
    `cx`                           varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '餐序',
    `yblxid`                       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本类型ID',
    `tjlx`                         varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型',
    `xb`                           varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `bs`                           varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标示',
    `bz`                           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `bgdybs`                       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告打印标示',
    `xsdyfl`                       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售打印分类',
    `ldrq`                         datetime(0) NULL DEFAULT NULL COMMENT '录单日期',
    `createdate`                   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                   datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `review_matters`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '复查注意事项',
    `is_delete`                    tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `jccs`                         int(0) NULL DEFAULT NULL COMMENT '检查次数',
    `examfeeitemid`                int(0) NULL DEFAULT NULL COMMENT '收费项目intID',
    `is_public`                    tinyint(1) NULL DEFAULT NULL COMMENT '是否是公共的',
    `fzx_ids`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心IDs，逗号拼接',
    `pre_upper_limit`              int(0) NULL DEFAULT NULL COMMENT '排检人数上限(废弃)',
    `is_ban`                       tinyint(1) NULL DEFAULT NULL COMMENT '是否禁用：1.是  0或null.否',
    `barcode_count`                int(0) NULL DEFAULT NULL COMMENT '条码打印个数',
    `barcode_name`                 varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '条码打印名称',
    `group_order`                  int(0) NULL DEFAULT NULL COMMENT '序号，职业团检报告 五、检查项目 排序',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                          `ITEMS_EXAMFEEITEMNAME`(`examfeeitem_name`) USING BTREE,
    INDEX                          `ITEMS_INPUTCODE`(`input_code`) USING BTREE,
    INDEX                          `ITEMS_TJLX`(`tjlx`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC收费项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_items_and_fzx
-- ----------------------------
DROP TABLE IF EXISTS `md_items_and_fzx`;
CREATE TABLE `md_items_and_fzx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `items_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    `fzx_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `tbzt`       decimal(1, 0) NULL DEFAULT NULL COMMENT '同步状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC收费项目和分中心关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_items_barcode_class
-- ----------------------------
DROP TABLE IF EXISTS `md_items_barcode_class`;
CREATE TABLE `md_items_barcode_class`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `createdate` datetime(0) NULL DEFAULT NULL,
    `modifydate` datetime(0) NULL DEFAULT NULL,
    `class_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收费项目条码打印分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_kingdeecustomer
-- ----------------------------
DROP TABLE IF EXISTS `md_kingdeecustomer`;
CREATE TABLE `md_kingdeecustomer`
(
    `account_no`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户号',
    `account_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户名称',
    `use_status_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '使用状态id',
    `ct_date`       datetime(0) NULL DEFAULT NULL COMMENT 'ct_date',
    `lt_date`       datetime(0) NULL DEFAULT NULL COMMENT 'lt_date',
    `centerorgname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'centerorgname',
    PRIMARY KEY (`account_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '金碟账户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_kingdeedepartment
-- ----------------------------
DROP TABLE IF EXISTS `md_kingdeedepartment`;
CREATE TABLE `md_kingdeedepartment`
(
    `account_no`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'account_no',
    `account_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'account_name',
    `use_status_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'use_status_id',
    `ct_date`       datetime(0) NULL DEFAULT NULL COMMENT 'ct_date',
    `lt_date`       datetime(0) NULL DEFAULT NULL COMMENT 'lt_date',
    PRIMARY KEY (`account_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'kingdeedepartment' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_kingdeeorganization
-- ----------------------------
DROP TABLE IF EXISTS `md_kingdeeorganization`;
CREATE TABLE `md_kingdeeorganization`
(
    `org_id`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'org_id',
    `org_number` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'org_number',
    `org_name`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'org_name',
    `parent_id`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'parent_id',
    PRIMARY KEY (`org_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'kingdeeorganization' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_kingdeepayway
-- ----------------------------
DROP TABLE IF EXISTS `md_kingdeepayway`;
CREATE TABLE `md_kingdeepayway`
(
    `account_no`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'account_no',
    `account_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'account_name',
    `use_status_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'use_status_id',
    `ct_date`       datetime(0) NULL DEFAULT NULL COMMENT 'ct_date',
    `lt_date`       datetime(0) NULL DEFAULT NULL COMMENT 'lt_date',
    PRIMARY KEY (`account_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'kingdeepayway' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_kingdeeremittance
-- ----------------------------
DROP TABLE IF EXISTS `md_kingdeeremittance`;
CREATE TABLE `md_kingdeeremittance`
(
    `fid`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金蝶主键值',
    `transactionnumber` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对方账户编号',
    `remitter`          varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对方户名',
    `income`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收入金额',
    `transdate`         datetime(0) NULL DEFAULT NULL COMMENT '交易日期'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'kingdeeremittance' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_kingdeereser
-- ----------------------------
DROP TABLE IF EXISTS `md_kingdeereser`;
CREATE TABLE `md_kingdeereser`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id',
    `id_remitter`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行汇款流水号',
    `id_remittanceway` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结算方式的ID',
    `customername`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
    `id_customer`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id：团体号/体检号/卡号一类的',
    `id_feecharger`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `id_creator`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人ID',
    `moneyamountpaid`  decimal(8, 2) NULL DEFAULT NULL COMMENT '结算金额',
    `note`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `id_audit`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
    `is_update`        char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否同步：0.未同步 1.已同步',
    `is_audit`         char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否审核：0.未审核 1.已审核',
    `auditdate`        datetime(0) NULL DEFAULT NULL COMMENT '审核日期',
    `id_change`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '每笔银行汇款结算详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_kingdeereserway
-- ----------------------------
DROP TABLE IF EXISTS `md_kingdeereserway`;
CREATE TABLE `md_kingdeereserway`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id',
    `account_no`    varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'account_no',
    `account_name`  varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'account_name',
    `use_status_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'use_status_id',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'kingdeereserway' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_laboratory_result
-- ----------------------------
DROP TABLE IF EXISTS `md_laboratory_result`;
CREATE TABLE `md_laboratory_result`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `create_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `projects_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目ID',
    `lis_code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LIS代码',
    `projects`        tinyint(1) NULL DEFAULT NULL COMMENT '检验项目',
    `intensive_level` tinyint(1) NULL DEFAULT NULL COMMENT '重症级',
    `projects_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '报告结果',
    `is_normal`       tinyint(1) NULL DEFAULT NULL COMMENT '是否正常',
    `unit`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
    `result_upper`    int(0) NULL DEFAULT NULL COMMENT '报告结果上限',
    `result_floor`    int(0) NULL DEFAULT NULL COMMENT '报告结果下限',
    `doctor_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检验医师',
    `inspect_code`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查医生代码',
    `inspect_time`    datetime(0) NULL DEFAULT NULL COMMENT '检验时间',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS检验科接收数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_large_amount_group_statistics
-- ----------------------------
DROP TABLE IF EXISTS `md_large_amount_group_statistics`;
CREATE TABLE `md_large_amount_group_statistics`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `year`              varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年份',
    `money`             decimal(16, 4) NULL DEFAULT NULL COMMENT '金额',
    `org_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位id',
    `salesman_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售员USERNAME',
    `order_count`       decimal(5, 0) NULL DEFAULT NULL COMMENT '总订单数量',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX               `IDX_LAGS_MONEY`(`money`) USING BTREE,
    INDEX               `IDX_LAGS_ORG_ID`(`org_id`) USING BTREE,
    INDEX               `IDX_LAGS_YEAR`(`year`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '年超20万额度单位统计' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_leadertarget
-- ----------------------------
DROP TABLE IF EXISTS `md_leadertarget`;
CREATE TABLE `md_leadertarget`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `year`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年份',
    `dyjdmb`     decimal(16, 4) NULL DEFAULT NULL COMMENT '第一季度目标',
    `dejdmb`     decimal(16, 4) NULL DEFAULT NULL COMMENT '第二季度目标',
    `dsjdmb`     decimal(16, 4) NULL DEFAULT NULL COMMENT '第三季度目标',
    `dijdmb`     decimal(16, 4) NULL DEFAULT NULL COMMENT '第四季度目标',
    `ndmb`       decimal(16, 4) NULL DEFAULT NULL COMMENT '全年目标',
    `bz`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '领导目标表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_limit_operation
-- ----------------------------
DROP TABLE IF EXISTS `md_limit_operation`;
CREATE TABLE `md_limit_operation`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `card_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检卡ID',
    `card_type`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡类型',
    `limit`          decimal(16, 2) NULL DEFAULT NULL COMMENT '记录增加或减少的金额或者积分',
    `memotext`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `operation_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人ID',
    `is_add`         tinyint(1) NULL DEFAULT NULL COMMENT '是否为增加：0.为充值 1.为消费',
    `operation_time` datetime(0) NULL DEFAULT NULL COMMENT '操作日期',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `pay_mode`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款方式ID',
    `handle_money`   decimal(16, 4) NULL DEFAULT NULL COMMENT '操作后金额',
    `branch_center`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `charge_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `is_delete`      tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `patientname`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `member_cardno`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
    `consumetype`    varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消费类型，详见：enums.com.center.medical.bean.CardConsumeType',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'CW卡额度操作表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_lung
-- ----------------------------
DROP TABLE IF EXISTS `md_lung`;
CREATE TABLE `md_lung`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `dep_id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `tjrq`               datetime(0) NULL DEFAULT NULL COMMENT '体检日期',
    `xj`                 text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '小结',
    `create_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `fvc`                decimal(8, 2) NULL DEFAULT NULL COMMENT '用力肺活量(测定值)',
    `fev`                decimal(8, 2) NULL DEFAULT NULL COMMENT '1秒钟用力肺活量（FEV1)）',
    `fev_fvc`            decimal(8, 2) NULL DEFAULT NULL COMMENT '1秒用力呼气容积/用力肺活量(FEV1%G)',
    `percentage_mmef`    decimal(5, 2) NULL DEFAULT NULL COMMENT '最大呼气中期流速（MMEF %预测值）',
    `predict_mmef`       decimal(5, 2) NULL DEFAULT NULL COMMENT '最大呼气中期流速（MMEF 预测值）',
    `mmef`               decimal(5, 2) NULL DEFAULT NULL COMMENT '最大呼气中期流速（MMEF）',
    `percentage_feffa`   decimal(5, 2) NULL DEFAULT NULL COMMENT '75%呼气中期流速（FEF75%）%预测值',
    `predict_feffa`      decimal(5, 2) NULL DEFAULT NULL COMMENT '75%呼气中期流速（FEF75%）预测值',
    `feffa`              decimal(5, 2) NULL DEFAULT NULL COMMENT '75%呼气中期流速（FEF75%）',
    `percentage_feffb`   decimal(5, 2) NULL DEFAULT NULL COMMENT '50%呼气中期流速（FEF50%）%预测值',
    `predict_feffb`      decimal(5, 2) NULL DEFAULT NULL COMMENT '50%呼气中期流速（FEF50%）预测值',
    `feffb`              decimal(5, 2) NULL DEFAULT NULL COMMENT '50%呼气中期流速（FEF50%）',
    `percentage_feffc`   decimal(5, 2) NULL DEFAULT NULL COMMENT '25%呼气中期流速（FEF25%）%预测值',
    `predict_feffc`      decimal(5, 2) NULL DEFAULT NULL COMMENT '25%呼气中期流速（FEF25%）预测值',
    `feffc`              decimal(5, 2) NULL DEFAULT NULL COMMENT '25%呼气中期流速（FEF25%）',
    `patientcode`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `predict_fvc`        decimal(5, 2) NULL DEFAULT NULL COMMENT '用力肺活量（预测值）',
    `percentage_fvc`     decimal(5, 2) NULL DEFAULT NULL COMMENT '用力肺活量（%预测值）',
    `predict_fev`        decimal(5, 2) NULL DEFAULT NULL COMMENT '1秒钟用力肺活量（FEV1 预测值)',
    `percentage_fev`     decimal(5, 2) NULL DEFAULT NULL COMMENT '1秒钟用力肺活量（FEV1% 预测值)',
    `predict_fev_fvc`    decimal(5, 2) NULL DEFAULT NULL COMMENT '1秒用力呼气容积/用力肺活量(FEV1%G)预测值',
    `percentage_fev_fvc` decimal(5, 2) NULL DEFAULT NULL COMMENT '1秒用力呼气容积/用力肺活量(FEV1%G)%预测值',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                `IDX_LUNG_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS肺功能' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_mealandfzx
-- ----------------------------
DROP TABLE IF EXISTS `md_mealandfzx`;
CREATE TABLE `md_mealandfzx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `tcid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐ID',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `tbzt`       tinyint(1) NULL DEFAULT NULL COMMENT '同步状态：0.未同步 1.已同步 2.更新',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `I_MAF_FZXID`(`fzxid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '普通套餐与分中心关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_mealanditem
-- ----------------------------
DROP TABLE IF EXISTS `md_mealanditem`;
CREATE TABLE `md_mealanditem`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `tcid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐ID',
    `sfxmid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    `sfbx`       char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否备选：0或null.否 1.是',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `is_system`  tinyint(1) NULL DEFAULT NULL COMMENT '是否复制套餐：0或null.否 1.是',
    `price`      decimal(16, 4) NULL DEFAULT NULL COMMENT '优惠价',
    `item_sort`  int(0) NULL DEFAULT NULL COMMENT '排序',
    `item_group` int(0) NULL DEFAULT NULL COMMENT '分组',
    `group_type` tinyint(1) NULL DEFAULT NULL COMMENT '分组类型：0.组内选 1.组间选 2.组任选',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '普通套餐与收费项目关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_memberbirthdat
-- ----------------------------
DROP TABLE IF EXISTS `md_memberbirthdat`;
CREATE TABLE `md_memberbirthdat`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `hy_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员档案ID',
    `visit_man`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访人ID',
    `visit_time`   datetime(0) NULL DEFAULT NULL COMMENT '回访时间',
    `visit_type`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访方式',
    `visit_note`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '回访备注',
    `note`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `visit_text`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '跟进内容',
    `visit_status` tinyint(1) NULL DEFAULT NULL COMMENT '回访状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员生日提醒回访表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_memberintegral
-- ----------------------------
DROP TABLE IF EXISTS `md_memberintegral`;
CREATE TABLE `md_memberintegral`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `card_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡号',
    `card_type`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡类型',
    `limit`           int(0) NULL DEFAULT NULL COMMENT '记录增加或减少的积分',
    `memotext`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `operation_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人ID',
    `is_add`          tinyint(1) NULL DEFAULT NULL COMMENT '是否为增加：0或null.否 1.是',
    `operation_time`  datetime(0) NULL DEFAULT NULL COMMENT '操作日期',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `pay_mode`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款方式ID',
    `handle_integral` int(0) NULL DEFAULT NULL COMMENT '操作后积分',
    `branch_center`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `member_name`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员姓名',
    `patientcode`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员卡积分明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_monthtarget
-- ----------------------------
DROP TABLE IF EXISTS `md_monthtarget`;
CREATE TABLE `md_monthtarget`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理id',
    `year`       varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年份',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    `bz`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `target1`    decimal(16, 4) NULL DEFAULT NULL COMMENT 'target1',
    `target2`    decimal(16, 4) NULL DEFAULT NULL COMMENT 'target2',
    `target3`    decimal(16, 4) NULL DEFAULT NULL COMMENT 'target3',
    `target4`    decimal(16, 4) NULL DEFAULT NULL COMMENT 'target4',
    `target5`    decimal(16, 4) NULL DEFAULT NULL COMMENT 'target5',
    `target6`    decimal(16, 4) NULL DEFAULT NULL COMMENT 'target6',
    `target7`    decimal(16, 4) NULL DEFAULT NULL COMMENT 'target7',
    `target8`    decimal(16, 4) NULL DEFAULT NULL COMMENT 'target8',
    `target9`    decimal(16, 4) NULL DEFAULT NULL COMMENT 'target9',
    `target10`   decimal(16, 4) NULL DEFAULT NULL COMMENT 'target10',
    `target11`   decimal(16, 4) NULL DEFAULT NULL COMMENT 'target11',
    `target12`   decimal(16, 4) NULL DEFAULT NULL COMMENT 'target12',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `IDX_MONTHT_FZXID`(`fzxid`) USING BTREE,
    INDEX        `IDX_MONTHT_XSJLID`(`xsjlid`) USING BTREE,
    INDEX        `IDX_MONTHT_YEAR`(`year`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '销售月度计划' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_nation
-- ----------------------------
DROP TABLE IF EXISTS `md_nation`;
CREATE TABLE `md_nation`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名族名称',
    `input_code`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `rome_code`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '罗马代码',
    `word_code`   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字母代码',
    `number_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数字代码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC民族' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_notificationmethod
-- ----------------------------
DROP TABLE IF EXISTS `md_notificationmethod`;
CREATE TABLE `md_notificationmethod`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `method_name`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知方式',
    `input_code`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `creater`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `is_delete`      tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `is_send_notice` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否发送通知：0.否 1.是',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知方式（领取方式）表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_notifier
-- ----------------------------
DROP TABLE IF EXISTS `md_notifier`;
CREATE TABLE `md_notifier`
(
    `id`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `report_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告主表ID',
    `notifier_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知人ID',
    `notification_mode`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知方式ID',
    `notification_details` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知内容',
    `notification_time`    datetime(0) NULL DEFAULT NULL COMMENT '通知时间',
    `notification_result`  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知结果',
    `createdate`           datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`           datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `memo`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'BG报告领取通知' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_notify_sms_exam
-- ----------------------------
DROP TABLE IF EXISTS `md_notify_sms_exam`;
CREATE TABLE `md_notify_sms_exam`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `id_order`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单ID',
    `ddh`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
    `id_contact`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
    `phone`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createer`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
    `fzxid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '来检短信提醒表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_notify_sms_visit
-- ----------------------------
DROP TABLE IF EXISTS `md_notify_sms_visit`;
CREATE TABLE `md_notify_sms_visit`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `id_visit`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访关联表ID',
    `id_template`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板ID',
    `notify_result` tinyint(1) NULL DEFAULT NULL COMMENT '通知状态：0.未通知 1.已通知 2.等待通知 3.通知失败',
    `notify_time`   datetime(0) NULL DEFAULT NULL COMMENT '通知时间：0.未通知 1.已通知 2.通知失败',
    `createer`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
    `fzxid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信回访表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_nuclein
-- ----------------------------
DROP TABLE IF EXISTS `md_nuclein`;
CREATE TABLE `md_nuclein`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `creater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传人',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `IDX_NUCLEIN_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '核酸检测报告上传记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_occupation_diseast
-- ----------------------------
DROP TABLE IF EXISTS `md_occupation_diseast`;
CREATE TABLE `md_occupation_diseast`
(
    `id`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
    `occupation_diseast_code`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病编码',
    `occupation_diseast`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病名称',
    `input_code`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`                  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `occupation_diseast_class` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病分类ID',
    `createdate`               datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`               datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`                tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC职业病名称' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_occupation_diseast_class
-- ----------------------------
DROP TABLE IF EXISTS `md_occupation_diseast_class`;
CREATE TABLE `md_occupation_diseast_class`
(
    `id`                            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `occupation_diseast_class_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类码',
    `occupation_diseast_class_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病分类名称',
    `input_code`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`                       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `createdate`                    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                    datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `is_delete`                     tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC职业病种分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_occupation_drug
-- ----------------------------
DROP TABLE IF EXISTS `md_occupation_drug`;
CREATE TABLE `md_occupation_drug`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `harm_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素ID',
    `poststage`          tinyint(1) NULL DEFAULT NULL COMMENT '在岗阶段',
    `industrial_disease` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '目标疾病(职业病)',
    `contraindication`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '目标疾病(禁忌症)',
    `keyword`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '关键字',
    `diagnosis`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '诊断依据',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更改日期',
    `is_public`          decimal(1, 0) NULL DEFAULT NULL COMMENT '是否是公共的：0或null.否 1.是',
    `fzx_ids`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心IDs，逗号拼接',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC职业病/禁忌症' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_occupation_symptom
-- ----------------------------
DROP TABLE IF EXISTS `md_occupation_symptom`;
CREATE TABLE `md_occupation_symptom`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `symptom_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '症状编码',
    `symptom`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '症状',
    `input_code`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`      varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `is_delete`    tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC职业症状' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_operate_log
-- ----------------------------
DROP TABLE IF EXISTS `md_operate_log`;
CREATE TABLE `md_operate_log`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `operator`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
    `method`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作方法',
    `note`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `op_type`    varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `IDX_OP_TYPE`(`op_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_orderandcombo
-- ----------------------------
DROP TABLE IF EXISTS `md_orderandcombo`;
CREATE TABLE `md_orderandcombo`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `ddid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单ID',
    `tcid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐ID',
    `xsjlid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `combostate`   varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐状态--用于区分从哪张表中取套餐(0:普通套餐、1/2:最小套餐)',
    `isjj`         tinyint(1) NULL DEFAULT NULL COMMENT '0.否 1.是--前台需要',
    `sfybd`        varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '0.否 1.是--前台需要',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `spzt`         tinyint(1) NULL DEFAULT NULL COMMENT '审批状态：0.未审批 1.已审批',
    `isbg`         tinyint(1) NULL DEFAULT NULL COMMENT '是否变更：1是最近一次变更',
    `show`         tinyint(1) NULL DEFAULT NULL COMMENT '是否显示：0或者Null展示 1.隐藏',
    `id_examclass` tinyint(0) NULL DEFAULT NULL COMMENT '检查类型，关联md_register_type表id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX          `I_OAC_TCID`(`tcid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单与套餐关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_orderandfzx
-- ----------------------------
DROP TABLE IF EXISTS `md_orderandfzx`;
CREATE TABLE `md_orderandfzx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `ddid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单ID',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `tbzt`       tinyint(1) NULL DEFAULT NULL COMMENT '同步状态：0.未同步 1.同步 2.更新',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `bdrq`       datetime(0) NULL DEFAULT NULL COMMENT '备单日期',
    `xzzt`       tinyint(1) NULL DEFAULT NULL COMMENT '下载状态：Null或其他.未下载 1.已下载',
    `xzsj`       datetime(0) NULL DEFAULT NULL COMMENT '下载时间',
    `ddxzzt`     tinyint(1) NULL DEFAULT NULL COMMENT '下载状态；0未下载、1已下载、null不需要下载',
    `xzxzzt`     tinyint(1) NULL DEFAULT NULL COMMENT '线上变更前台须知后的下载状态。0未下载、1已下载、null不需要下载',
    `ycxzzt`     tinyint(1) NULL DEFAULT NULL COMMENT '隐藏展示下载状态  0未下载 1已下载 null不需要下载',
    `kdzlzt`     tinyint(1) NULL DEFAULT NULL COMMENT '开单助理下载状态  0未下载 1已下载 null不需要下载',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单与分中心关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_ordersummary
-- ----------------------------
DROP TABLE IF EXISTS `md_ordersummary`;
CREATE TABLE `md_ordersummary`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `ddid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单ID',
    `ddmc`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单名称',
    `khdwmc`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '客户单位名称',
    `xsjl`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理',
    `zj`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总结',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单总结表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_other_report
-- ----------------------------
DROP TABLE IF EXISTS `md_other_report`;
CREATE TABLE `md_other_report`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `pdf_url`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'pdf地址',
    `config_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'config_id',
    `create_status` tinyint(1) NULL DEFAULT NULL COMMENT '生成状态：null未生成 0.生成中 1.已生成',
    `error_msg`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误信息',
    `creater`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生成时间',
    `create_time`   datetime(0) NULL DEFAULT NULL COMMENT '生成时间',
    `report_type`   tinyint(0) NULL DEFAULT NULL COMMENT '报告类型，详见：enums.com.center.medical.bean.ReportType',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX           `IDX_OTHER_REPORT_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '其他报告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_outside_charge_item
-- ----------------------------
DROP TABLE IF EXISTS `md_outside_charge_item`;
CREATE TABLE `md_outside_charge_item`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `id_charge`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目id',
    `id_depart`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门id',
    `is_delete`   tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `wsjg_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外送机构id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `IDX_OCI_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS外送项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_outside_checkin
-- ----------------------------
DROP TABLE IF EXISTS `md_outside_checkin`;
CREATE TABLE `md_outside_checkin`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `id_charge`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目id',
    `status`      varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态：1.已处理',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS外送项目图片关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_outside_main
-- ----------------------------
DROP TABLE IF EXISTS `md_outside_main`;
CREATE TABLE `md_outside_main`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `send_people` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外送人',
    `send_date`   datetime(0) NULL DEFAULT NULL COMMENT '外送时间',
    `back_people` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '返回人',
    `back_date`   datetime(0) NULL DEFAULT NULL COMMENT '返回时间',
    `note`        varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `wsjg`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外送机构',
    `patientname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者姓名',
    `status`      tinyint(1) NULL DEFAULT NULL COMMENT '处理状态',
    `is_delete`   tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS外送登记结果主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_outside_pictrue
-- ----------------------------
DROP TABLE IF EXISTS `md_outside_pictrue`;
CREATE TABLE `md_outside_pictrue`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `pictrue_position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片存放位置',
    `order_index`      int(0) NULL DEFAULT NULL COMMENT '序号',
    `charge_id`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '收费项目ID(多个 逗号分隔)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS外送项目图片结果' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pa_peissortexam
-- ----------------------------
DROP TABLE IF EXISTS `md_pa_peissortexam`;
CREATE TABLE `md_pa_peissortexam`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `branch_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    `area_id`    decimal(5, 0) NULL DEFAULT NULL COMMENT '普检区可预约人数',
    `sort_num`   decimal(5, 0) NULL DEFAULT NULL COMMENT 'VIP可预约人数',
    `sort_date`  datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `is_open`    decimal(1, 0) NULL DEFAULT NULL COMMENT '是否开检：0或null.否 1.是',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `PASORTEXAM_AREA`(`area_id`) USING BTREE,
    INDEX        `PASORTEXAM_BRANCH`(`branch_id`) USING BTREE,
    INDEX        `PASORTEXAM_SORT_DATE`(`sort_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '平安软件-排检' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_basconclusion
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_basconclusion`;
CREATE TABLE `md_pacs_basconclusion`
(
    `id`                            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `sysmanmark`                    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护标记',
    `keyconclusion`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '健值',
    `name`                          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论名称',
    `conclusion_nameeng`            varchar(140) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conclusion_nameeng',
    `conclusion_code`               varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conclusion_code',
    `conclusion_code2`              varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conclusion_code2',
    `conclusion_code3`              varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conclusion_code3',
    `conclusion_codehm`             varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conclusion_codehm',
    `conclusioncodex`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conclusioncodex',
    `id_conclusiontype`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_conclusiontype',
    `id_conclusiontype2`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_conclusiontype2',
    `id_conclusiontype3`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_conclusiontype3',
    `id_conclusiongroup`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_conclusiongroup',
    `id_conclusionlink`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_conclusionlink',
    `id_conclusionmedinsurance`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_conclusionmedinsurance',
    `id_conclusioncadre`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_conclusioncadre',
    `id_conclusionoccup`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_conclusionoccup',
    `id_section`                    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_section',
    `depiction`                     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '疾病解释',
    `suggestion`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总检建议',
    `advice`                        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康建议',
    `dietguide`                     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '饮食指导',
    `sportguide`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '运动指导',
    `knowledge`                     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康知识',
    `suggestiongroup`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康知识',
    `id_icd10`                      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_icd10',
    `input_code`                    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拼音输入码',
    `inputcodeb`                    varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcodeb',
    `inputcodec`                    varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcodec',
    `inputcoded`                    varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcoded',
    `inputcodee`                    varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcodee',
    `disporder`                     varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'disporder',
    `id_specialconclusionlist`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'id_specialconclusionlist',
    `f_realdisease`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'f_realdisease',
    `f_maledisease`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'f_maledisease',
    `f_femaledisease`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'f_femaledisease',
    `f_hideongroupreport`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'f_hideongroupreport',
    `disporder_ongroupreport`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'disporder_ongroupreport',
    `conclusionfindermatch`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'conclusionfindermatch',
    `conclusion`                    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'conclusion',
    `f_normalconclusion`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'f_normalconclusion',
    `severedegree`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'severedegree',
    `f_occudiseaseobject`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'f_occudiseaseobject',
    `f_occudiseasecontraindication` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'f_occudiseasecontraindication',
    `suggestionoccudisease`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'suggestionoccudisease',
    `createdate`                    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `note`                          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `status`                        tinyint(0) NULL DEFAULT NULL COMMENT '状态',
    `division_id`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `is_long`                       tinyint(1) NULL DEFAULT NULL COMMENT '是否进总检',
    `dep_name`                      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室名称',
    `is_delete`                     tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `is_public`                     tinyint(1) NULL DEFAULT NULL COMMENT '是否是公共的：0或null.否 1.是',
    `fzx_ids`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心IDs，逗号拼接',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'pacs总检结论词' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_base_part
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_base_part`;
CREATE TABLE `md_pacs_base_part`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `part_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部位名称',
    `input_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `part_sort`  int(0) NULL DEFAULT NULL COMMENT '排序',
    `pid`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'pid',
    `en_name`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '英文名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS-部位' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_basexamltem
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_basexamltem`;
CREATE TABLE `md_pacs_basexamltem`
(
    `id`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `sysmanmark`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统维护标志',
    `keyexamitem`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '键值',
    `examitem_name`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查项目名称',
    `examitem_nameabr`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目名称缩写',
    `examitem_nameprn`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打印名称',
    `examitem_nameeng`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目英文名称',
    `examitem_nameengabr`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目名称英文缩写',
    `examitem_code`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目代码',
    `examitem_code2`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'examitemCode2',
    `examitem_code3`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'examitemCode3',
    `examitem_codehm`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'examitemCodehm',
    `examitemcodex`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'examitemcodex',
    `lbdm_examitemtype`        varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'lbdmExamitemtype',
    `id_examitemtype`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目类型ID',
    `id_treegroup`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'idTreegroup',
    `id_treegroupsub`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'idTreegroupsub',
    `valuetype`                varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值类型',
    `valueunit`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值单位',
    `valuememolines`           tinyint(1) NULL DEFAULT NULL COMMENT '多行文本行数',
    `f_autocalc`               tinyint(1) NULL DEFAULT NULL COMMENT '自动计算：0或null.否 1.是',
    `f_print`                  tinyint(1) NULL DEFAULT NULL COMMENT '是否打印：0或null.否 1.是',
    `f_multival`               tinyint(1) NULL DEFAULT NULL COMMENT '是否多值：0或null.否 1.是',
    `commongroup`              tinyint(1) NULL DEFAULT NULL COMMENT '通用组(不再使用)',
    `valuecheckcols`           tinyint(1) NULL DEFAULT NULL COMMENT '多值列数',
    `f_entryonly`              tinyint(1) NULL DEFAULT NULL COMMENT '仅自由输入',
    `f_noentry`                tinyint(1) NULL DEFAULT NULL COMMENT '无自由输入',
    `f_entrytoconclusion`      tinyint(1) NULL DEFAULT NULL COMMENT '输入至小结== 描述进小结',
    `f_usecheck`               tinyint(1) NULL DEFAULT NULL COMMENT '使用勾选',
    `f_sameline`               tinyint(1) NULL DEFAULT NULL COMMENT '同行显示',
    `f_can_dup`                tinyint(1) NULL DEFAULT NULL COMMENT '收费项目中可重复',
    `f_lab_byhand`             tinyint(1) NULL DEFAULT NULL COMMENT '检验手工项目',
    `f_lab_nowait`             tinyint(1) NULL DEFAULT NULL COMMENT '检验不必等待',
    `conclusionlevel`          tinyint(1) NULL DEFAULT NULL COMMENT '小结级别',
    `f_summarytitle`           tinyint(1) NULL DEFAULT NULL COMMENT '小结标题 ==  名称进小结',
    `phraseforhilo`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高低用词(不再使用)',
    `increments`               int(0) NULL DEFAULT NULL COMMENT '数值型增量',
    `numprecision`             int(0) NULL DEFAULT NULL COMMENT '精度',
    `valuevalidmin`            int(0) NULL DEFAULT NULL COMMENT '验证最小值',
    `valuevalidmax`            int(0) NULL DEFAULT NULL COMMENT '验证最大值',
    `itemflagcaselessthan`     varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'itemflagcaselessthan',
    `itemflagcasegreaterthan`  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'itemflagcasegreaterthan',
    `f_bothnumtext`            tinyint(1) NULL DEFAULT NULL COMMENT '数字文字同时显示',
    `f_numonly`                tinyint(1) NULL DEFAULT NULL COMMENT 'FNumonly',
    `f_useappmaxmin`           tinyint(1) NULL DEFAULT NULL COMMENT '应用范围',
    `f_includemin`             tinyint(1) NULL DEFAULT NULL COMMENT '含低值',
    `f_includemax`             tinyint(1) NULL DEFAULT NULL COMMENT '含高值',
    `f_male`                   tinyint(1) NULL DEFAULT NULL COMMENT '用于男性：0.代表男 1.代表女 2.通用',
    `valuemaledef`             varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '男性缺省值',
    `valuemalemax`             int(0) NULL DEFAULT NULL COMMENT '男性上限',
    `valuemalenormmid`         int(0) NULL DEFAULT NULL COMMENT 'valuemalenormmid',
    `valuemalemin`             int(0) NULL DEFAULT NULL COMMENT '男性下限',
    `valuemaleweakup`          int(0) NULL DEFAULT NULL COMMENT 'valuemaleweakup',
    `valuemaleweakdown`        int(0) NULL DEFAULT NULL COMMENT 'valuemaleweakdown',
    `valuemalesevereup`        int(0) NULL DEFAULT NULL COMMENT '男性生命值上限',
    `valuemaleseveredown`      int(0) NULL DEFAULT NULL COMMENT '男性生命值下限',
    `f_female`                 tinyint(1) NULL DEFAULT NULL COMMENT '用于女性：0.代表男 1.代表女 2.通用',
    `valuefemaledef`           varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '女性缺省值',
    `valuefemalemax`           int(0) NULL DEFAULT NULL COMMENT '女性上限',
    `valuefemalenormmid`       int(0) NULL DEFAULT NULL COMMENT 'valuefemalenormmid',
    `valuefemalemin`           int(0) NULL DEFAULT NULL COMMENT '女性下限',
    `valuefemaleweakup`        int(0) NULL DEFAULT NULL COMMENT 'valuefemaleweakup',
    `valuefemaleweakdown`      int(0) NULL DEFAULT NULL COMMENT 'valuefemaleweakdown',
    `valuefemalesevereup`      int(0) NULL DEFAULT NULL COMMENT '女性生命值上限',
    `valuefemaleseveredown`    int(0) NULL DEFAULT NULL COMMENT '女性生命值下限',
    `gxdm`                     varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'gxdm',
    `expression`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表达式',
    `id_conclusionhi`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词(高)',
    `id_conclusionlo`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词(低)',
    `id_conclusionpo`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词(阳)',
    `id_conclusionne`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词(阴)',
    `id_conclusionweaknormhi`  int(0) NULL DEFAULT NULL COMMENT 'idConclusionweaknormhi',
    `id_conclusionweaknormlo`  int(0) NULL DEFAULT NULL COMMENT 'idConclusionweaknormlo',
    `id_conclusionweakposihi`  int(0) NULL DEFAULT NULL COMMENT 'idConclusionweakposihi',
    `id_conclusionweakposilo`  int(0) NULL DEFAULT NULL COMMENT 'idConclusionweakposilo',
    `input_code`               varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `inputcodeb`               varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcodeb',
    `inputcodec`               varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcodec',
    `inputcoded`               varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcoded',
    `inputcodee`               varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcodee',
    `f_patientbaseinfo`        tinyint(1) NULL DEFAULT NULL COMMENT 'FPatientbaseinfo',
    `patientbaseinfodisporder` int(0) NULL DEFAULT NULL COMMENT 'patientbaseinfodisporder',
    `note`                     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `disporder`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行序',
    `f_examitemexportflag01`   tinyint(1) NULL DEFAULT NULL COMMENT 'FExamitemexportflag01',
    `f_examitemexportflag02`   tinyint(1) NULL DEFAULT NULL COMMENT 'FExamitemexportflag02',
    `f_nodepartsummary`        tinyint(1) NULL DEFAULT NULL COMMENT 'FNodepartsummary',
    `f_comparereport`          tinyint(1) NULL DEFAULT NULL COMMENT 'FComparereport',
    `f_noreportoutput`         tinyint(1) NULL DEFAULT NULL COMMENT 'FNoreportoutput',
    `f_alwayspositive`         tinyint(1) NULL DEFAULT NULL COMMENT 'FAlwayspositive',
    `f_titleonreport`          tinyint(1) NULL DEFAULT NULL COMMENT 'FTitleonreport',
    `itemrefrange`             varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'itemrefrange',
    `keysummarygroup`          varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'keysummarygroup',
    `f_useapprangeoccudisease` tinyint(1) NULL DEFAULT NULL COMMENT 'FUseapprangeoccudisease',
    `expressionoccudisease`    tinyint(1) NULL DEFAULT NULL COMMENT 'expressionoccudisease',
    `division_id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `type`                     tinyint(1) NULL DEFAULT NULL COMMENT '类型：0.健康检查类型 1.职业检查类型 2.健康+职业',
    `createdate`               datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`               datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `interface_code`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口代码',
    `valuedangerousmax`        decimal(12, 3) NULL DEFAULT NULL COMMENT '危急值上限',
    `valuedangerousmin`        decimal(12, 3) NULL DEFAULT NULL COMMENT '危急值下限',
    `is_desc`                  tinyint(1) NULL DEFAULT NULL COMMENT '描述进小结：0或null.否 1.是',
    `is_name`                  tinyint(1) NULL DEFAULT NULL COMMENT '名称进小结：0或null.否 1.是',
    `status`                   tinyint(1) NULL DEFAULT NULL COMMENT '状态',
    `is_delete`                tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `is_public`                tinyint(1) NULL DEFAULT NULL COMMENT '是否是公共的：0或null.否 1.是',
    `fzx_ids`                  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心IDs，逗号拼接',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                      `PACS_BASEXAMLTEM_INPUTCODE`(`input_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS-基础检查项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_basexamltem_sign
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_basexamltem_sign`;
CREATE TABLE `md_pacs_basexamltem_sign`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `inspect_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目ID',
    `name`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '体征词名称',
    `result_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_in_summary`   tinyint(1) NULL DEFAULT NULL COMMENT '不进小结：0代表进，1代表不进',
    `body_input_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体证词输入码',
    `body_detail`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '体证词详细描述',
    `body_detail_zy`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '体证词详细描述(职业病）',
    `intensive_level` tinyint(1) NULL DEFAULT NULL COMMENT '重症级别',
    `other_mutex`     varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '互斥分组（异组互斥）',
    `num_mutex`       int(0) NULL DEFAULT NULL COMMENT '互斥分组（同组正整数编号互斥）',
    `is_positive`     tinyint(1) NULL DEFAULT NULL COMMENT '是否阳性结果：0或null.否 1.是',
    `input_code`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '输入码',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `is_default`      tinyint(1) NULL DEFAULT NULL COMMENT '是否选中：0.不选中 1.选中',
    `orderindex`      int(0) NULL DEFAULT NULL COMMENT '体征词显示顺序',
    `is_input`        varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'is_input',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS-体征词' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_inspect_charge
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_inspect_charge`;
CREATE TABLE `md_pacs_inspect_charge`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `charge_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '费用id',
    `inspect_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检验项目id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`   tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `order_index` int(0) NULL DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `PACS_INSCHARGE_CHARGE`(`charge_id`) USING BTREE,
    INDEX         `PACS_INSCHARGE_INSPPECT`(`inspect_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS-项目检查费用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_item_part
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_item_part`;
CREATE TABLE `md_pacs_item_part`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `item_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'pacs收费项目id',
    `part_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'pacs部位id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目部位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_items
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_items`;
CREATE TABLE `md_pacs_items`
(
    `id`                           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `basexamltem_id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目ID',
    `sysmanmark`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统维护标记',
    `keyexamfeeitem`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '键值',
    `examfeeitem_name`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称',
    `examfeeitem_nameabr`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称缩写',
    `examfeeitem_nameprn`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目打印名称',
    `examfeeitem_nameeng`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目英语名称',
    `examfeeitem_nameengabr`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目英语名称缩写',
    `examfeeitem_code`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目接口代码',
    `examfeeitem_code2`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目接口代码2',
    `examfeeitem_code3`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目接口代码3',
    `examfeeitem_codehm`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '部位IDs',
    `examfeeitem_codex`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否在APP出现：0或null.否 1.是',
    `examfeeitem_feecode`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目收费代码',
    `examfeeitem_feecodesub`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目收费子代码',
    `examfeeitem_pricecode`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'examfeeitemPricecode',
    `examfeeitem_pricecodesub`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'examfeeitemPricecodesub',
    `examfeeitem_hisname`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目HIS名称',
    `examfeeitem_class`            varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'examfeeitemClass',
    `id_reportitem`                int(0) NULL DEFAULT NULL COMMENT '报告项目ID',
    `x_xxdm`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'XXxdm',
    `f_discountdisabled`           tinyint(1) NULL DEFAULT NULL COMMENT '禁止打折字段，1禁止打折',
    `f_maxoffpercentlimited`       tinyint(1) NULL DEFAULT NULL COMMENT 'FMaxoffpercentlimited',
    `maxoffpercent`                decimal(8, 4) NULL DEFAULT NULL COMMENT 'maxoffpercent',
    `unitprice`                    decimal(8, 4) NULL DEFAULT NULL COMMENT '价格',
    `suiteprice`                   decimal(8, 4) NULL DEFAULT NULL COMMENT '套餐价格',
    `specialprice`                 decimal(8, 2) NULL DEFAULT NULL COMMENT 'specialprice',
    `foreignprice`                 decimal(8, 2) NULL DEFAULT NULL COMMENT 'foreignprice',
    `preferprice`                  decimal(8, 2) NULL DEFAULT NULL COMMENT 'preferprice',
    `innerprice`                   decimal(8, 2) NULL DEFAULT NULL COMMENT 'innerprice',
    `materialprice`                decimal(8, 4) NULL DEFAULT NULL COMMENT '耗材价格',
    `costprice`                    decimal(8, 4) NULL DEFAULT NULL COMMENT '成本价',
    `coopprice`                    decimal(8, 4) NULL DEFAULT NULL COMMENT '外部价',
    `id_cooporg`                   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '外送机构ID',
    `exampoint`                    int(0) NULL DEFAULT NULL COMMENT 'exampoint',
    `x_departcode`                 varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'XDepartcode',
    `id_depart`                    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属科室ID',
    `depart_name`                  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '所属科室名称',
    `x_ybdm`                       varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'XYbdm',
    `id_guidesheetgroup`           int(0) NULL DEFAULT NULL COMMENT 'idGuidesheetgroup',
    `id_guidesheetgroupsub`        int(0) NULL DEFAULT NULL COMMENT 'idGuidesheetgroupsub',
    `guidesheetgroupset`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'guidesheetgroupset',
    `guidesheetgroupsetdisporder`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'guidesheetgroupsetdisporder',
    `id_labtype`                   int(0) NULL DEFAULT NULL COMMENT '标本类型ID',
    `labtype_r`                    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标本类型名称',
    `labtype_sub`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'labtypeSub',
    `guidesheelabtypeset`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'guidesheelabtypeset',
    `guidesheelabtypesetdisporder` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'guidesheelabtypesetdisporder',
    `hisopendepartname`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'hisopendepartname',
    `hisopendepartcode`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'hisopendepartcode',
    `hisexecdepartname`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行科室名称',
    `hisexecdepartcode`            varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行科室代码',
    `hisexecworkgroupname`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作组名称',
    `hisexecworkgroupcode`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作组代码',
    `hisexecworkstationname`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作站名称',
    `hisexecworkstationcode`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作站代码',
    `hisexecworkbenchname`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作台名称',
    `hisexecworkbenchcode`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'HIS执行工作台代码',
    `input_code`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `inputcodeb`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcodeb',
    `inputcodec`                   varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcodec',
    `inputcoded`                   varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'inputcoded',
    `inputcodee`                   varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
    `f_reportalone`                tinyint(1) NULL DEFAULT NULL COMMENT '独立报告',
    `f_male`                       tinyint(1) NULL DEFAULT NULL COMMENT '男性项目',
    `f_female`                     tinyint(1) NULL DEFAULT NULL COMMENT '女性项目',
    `f_alert`                      tinyint(1) NULL DEFAULT NULL COMMENT '警示项目',
    `warningmsg`                   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '警示信息',
    `f_autovip`                    tinyint(1) NULL DEFAULT NULL COMMENT '自动VIP项目',
    `f_nonexam`                    tinyint(1) NULL DEFAULT NULL COMMENT '非检查项目',
    `f_isdrug`                     tinyint(1) NULL DEFAULT NULL COMMENT '是否药品',
    `f_isforask`                   tinyint(1) NULL DEFAULT NULL COMMENT '是否问卷',
    `f_hideinguidesheet`           tinyint(1) NULL DEFAULT NULL COMMENT '指引单不打印',
    `breakfastorder`               char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '早餐顺序',
    `f_disabled`                   tinyint(1) NULL DEFAULT NULL COMMENT '禁用',
    `f_invisible`                  tinyint(1) NULL DEFAULT NULL COMMENT 'FInvisible',
    `disporder`                    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行序',
    `note`                         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
    `id_classoffeeitem`            int(0) NULL DEFAULT NULL COMMENT '收费项目分类ID',
    `id_classofreceipt`            int(0) NULL DEFAULT NULL COMMENT '发票项目分类ID',
    `id_classofaccount`            int(0) NULL DEFAULT NULL COMMENT 'idClassofaccount',
    `id_feeitemclass2`             int(0) NULL DEFAULT NULL COMMENT 'idFeeitemclass2',
    `id_feeitemclass3`             int(0) NULL DEFAULT NULL COMMENT 'idFeeitemclass3',
    `lookupwarning`                varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'lookupwarning',
    `guidesheetcode`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'guidesheetcode',
    `limitedinexamplaceidlist`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'limitedinexamplaceidlist',
    `f_qtysettable`                tinyint(1) NULL DEFAULT NULL COMMENT 'FQtysettable',
    `f_doctorbyfeeitem`            tinyint(1) NULL DEFAULT NULL COMMENT 'FDoctorbyfeeitem',
    `f_dosplitting`                tinyint(1) NULL DEFAULT NULL COMMENT 'FDosplitting',
    `specification`                varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'specification',
    `measureunit`                  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'measureunit',
    `numpricelistneeded`           int(0) NULL DEFAULT NULL COMMENT 'numpricelistneeded',
    `numpricelistincomplete`       int(0) NULL DEFAULT NULL COMMENT 'numpricelistincomplete',
    `numusedfordeveloper`          int(0) NULL DEFAULT NULL COMMENT 'numusedfordeveloper',
    `f_dontupdate`                 tinyint(1) NULL DEFAULT NULL COMMENT 'FDontupdate',
    `f_dontupdatepricedetail`      tinyint(1) NULL DEFAULT NULL COMMENT 'FDontupdatepricedetail',
    `f_comparereport`              tinyint(1) NULL DEFAULT NULL COMMENT 'FComparereport',
    `f_guidesheetbackupsingleitem` tinyint(1) NULL DEFAULT NULL COMMENT 'FGuidesheetbackupsingleitem',
    `dt_lastautoinsert`            datetime(0) NULL DEFAULT NULL COMMENT '上次统计检查次数时间',
    `dt_lastautoupdate`            datetime(0) NULL DEFAULT NULL COMMENT 'dtLastautoupdate',
    `dt_lastautoupdatedetail`      datetime(0) NULL DEFAULT NULL COMMENT 'dtLastautoupdatedetail',
    `jz`                           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健值',
    `sfxmsrm`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '收费项目输入码',
    `xmdymc`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '项目打印名称',
    `dydfz`                        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '导引单分组',
    `xh`                           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '打印排列序号',
    `dyddybs`                      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导引单打印标示',
    `zk`                           decimal(8, 4) NULL DEFAULT NULL COMMENT '折扣',
    `txjg`                         decimal(8, 4) NULL DEFAULT NULL COMMENT '特需价格',
    `wbjg`                         decimal(8, 4) NULL DEFAULT NULL COMMENT '外宾价格',
    `ydjg`                         decimal(8, 4) NULL DEFAULT NULL COMMENT '优待价格',
    `nbj`                          decimal(8, 4) NULL DEFAULT NULL COMMENT '内部价',
    `zybm`                         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业编码',
    `yblx`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'yblx',
    `dlbs`                         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'dlbs',
    `yblxmc`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'yblxmc',
    `fylx`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '费用类型',
    `jcyy`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查意义',
    `cx`                           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '餐序',
    `yblxid`                       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'yblxid',
    `tjlx`                         varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型',
    `xb`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `bs`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标示',
    `bz`                           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `bgdybs`                       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '隐私项目：0.不是 1.是',
    `xsdyfl`                       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售打印分类',
    `ldrq`                         datetime(0) NULL DEFAULT NULL COMMENT '录单日期',
    `createdate`                   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `review_matters`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '复查注意事项',
    `is_delete`                    tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `jccs`                         int(0) NULL DEFAULT NULL COMMENT '检查次数',
    `examfeeitemid`                int(0) NULL DEFAULT NULL COMMENT '收费项目intID',
    `is_public`                    tinyint(1) NULL DEFAULT NULL COMMENT '是否是公共的',
    `fzx_ids`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '分中心IDs，逗号拼接',
    `per_upper_limit`              int(0) NULL DEFAULT NULL COMMENT 'per_upper_limit',
    `pre_upper_limit`              int(0) NULL DEFAULT NULL COMMENT '排检人数上限',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                          `PACS_ITEMS_EXAMFEEITEMNAME`(`examfeeitem_name`) USING BTREE,
    INDEX                          `PACS_ITEMS_INPUTCODE`(`input_code`) USING BTREE,
    INDEX                          `PACS_ITEMS_TJLX`(`tjlx`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS-收费项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_pdf
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_pdf`;
CREATE TABLE `md_pacs_pdf`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `feeitem_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目id',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `pdf_url`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'pdf地址',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `IDX_PACS_PDF_FEEID`(`feeitem_id`) USING BTREE,
    INDEX         `IDX_PACS_PDF_PCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS PDF  海康医院使用' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_peispatient
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_peispatient`;
CREATE TABLE `md_pacs_peispatient`
(
    `id`                           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `id_patient`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检id',
    `id_orgpatient`                int(0) NULL DEFAULT NULL COMMENT '预定体检编码(无)',
    `id_cis`                       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '线上档案id',
    `id_patientarchive`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案ID',
    `patientcode`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '体检号',
    `patientcodeprn`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检代码打印格式',
    `patientarchiveno`             varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案号',
    `patientcardno`                varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一卡通号',
    `patientbizno`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务编号',
    `idcardno`                     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `dailynumberdate`              datetime(0) NULL DEFAULT NULL COMMENT '每日编码日期',
    `dailynumber`                  int(0) NULL DEFAULT NULL COMMENT '每日编码',
    `numtotal`                     tinyint(0) NULL DEFAULT NULL COMMENT '(弃用)下载同步标志（总编号） 0未同步，1已同步,2更新',
    `numyear`                      int(0) NULL DEFAULT NULL COMMENT '年编号',
    `nummonth`                     int(0) NULL DEFAULT NULL COMMENT '月编号',
    `numday`                       int(0) NULL DEFAULT NULL COMMENT '日编号',
    `numorg`                       int(0) NULL DEFAULT NULL COMMENT '团体编号',
    `numorgresv`                   int(0) NULL DEFAULT NULL COMMENT '订单号',
    `id_patientlinked`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'idPatientlinked',
    `id_nonorg`                    int(0) NULL DEFAULT NULL COMMENT '非团体虚拟团体ID',
    `patientname`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `input_code`                   varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `id_orgreservationgroup`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务分组ID',
    `id_orgreservation`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预定任务ID',
    `id_org`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `org_name`                     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体名称',
    `org_depart`                   varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体部门',
    `org_departsuba`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'orgDepartsuba',
    `org_departsubb`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'orgDepartsubb',
    `org_departsubc`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'orgDepartsubc',
    `org_departsubd`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'orgDepartsubd',
    `org_departsube`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否已在APP保存',
    `have_private`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'have_private',
    `id_payway`                    varchar(62) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预支付方式ID',
    `payway`                       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预支付方式',
    `offpercent`                   decimal(8, 4) NULL DEFAULT NULL COMMENT '扣率',
    `maxoffpercent`                decimal(8, 4) NULL DEFAULT NULL COMMENT '最大扣率',
    `personpricelimit`             decimal(8, 2) NULL DEFAULT NULL COMMENT '统收限额-->原价(现用)',
    `id_sex`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别ID',
    `sex`                          varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `birthdate`                    datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
    `age`                          int(0) NULL DEFAULT NULL COMMENT '年龄',
    `id_ageunit`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄单位ID',
    `ageunit`                      varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄单位',
    `ageofreal`                    int(0) NULL DEFAULT NULL COMMENT '实数年龄',
    `id_marriage`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻ID',
    `marriage`                     varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻',
    `id_nation`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族ID',
    `nation`                       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族',
    `address`                      varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
    `id_informway`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知方式ID',
    `id_opendoctor`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开单医生ID',
    `email`                        varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
    `phone`                        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `id_patientclass`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者类型ID',
    `id_education`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教育ID',
    `education`                    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教育程度',
    `id_occupation`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业ID',
    `occupation`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业',
    `id_resarea`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区ID',
    `resarea`                      varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区',
    `dateinorganization`           datetime(0) NULL DEFAULT NULL COMMENT '入职日期',
    `f_isforprepare`               tinyint(1) NULL DEFAULT NULL COMMENT '预定：0.不是备单人员 1.备单人员',
    `f_isforreserve`               tinyint(1) NULL DEFAULT NULL COMMENT '预约：1.预约',
    `datecreated`                  datetime(0) NULL DEFAULT NULL COMMENT '职业性问诊签字日期',
    `f_registered`                 tinyint(1) NULL DEFAULT NULL COMMENT '已登记',
    `dateregister`                 datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `position_code`                varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置',
    `jobtype_code`                 varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作类型编码',
    `moneyamount`                  decimal(8, 2) NULL DEFAULT NULL COMMENT '应付金额',
    `moneyamountpaid`              decimal(8, 2) NULL DEFAULT NULL COMMENT '实付金额',
    `guidancenote`                 varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指引单说明',
    `workno`                       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工号',
    `id_doctorreg`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记员ID',
    `doctorreg`                    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记员',
    `id_examtype`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型ID，详见：enums.com.center.medical.bean.ExamType',
    `id_examsuite`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型',
    `examsuite_name`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐名称',
    `examsuite_alias`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家人手机号',
    `id_doctorconclusion`          int(0) NULL DEFAULT NULL COMMENT '预约编号',
    `doctorconclusion`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医生结论',
    `id_doctorapply`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '健康锁定人ID',
    `doctorapply`                  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '健康锁定人',
    `f_guidanceprinted`            int(0) NULL DEFAULT NULL COMMENT '指引单已打次数',
    `f_feecharged`                 tinyint(1) NULL DEFAULT NULL COMMENT '已缴费',
    `f_examstarted`                tinyint(1) NULL DEFAULT NULL COMMENT '已开始体检',
    `f_readytofinal`               tinyint(1) NULL DEFAULT NULL COMMENT '分检完成',
    `id_doctorfee`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员ID',
    `doctorfee`                    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员',
    `f_paused`                     tinyint(1) NULL DEFAULT NULL COMMENT '禁检',
    `f_finallocked`                tinyint(1) NULL DEFAULT NULL COMMENT '健康锁定',
    `f_finalexamed`                tinyint(1) NULL DEFAULT NULL COMMENT '总检完成',
    `f_finalapproved`              tinyint(1) NULL DEFAULT NULL COMMENT '总审完成',
    `id_doctorfinal`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总检医生ID',
    `doctorfinal_name_r`           varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总检医生',
    `datefinalexamed`              datetime(0) NULL DEFAULT NULL COMMENT '总检时间',
    `id_doctorfinalapproved`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总审医生',
    `datefinalapproved`            datetime(0) NULL DEFAULT NULL COMMENT '总审时间',
    `f_cardissued`                 tinyint(1) NULL DEFAULT NULL COMMENT '磁卡已发',
    `f_cardreturned`               tinyint(1) NULL DEFAULT NULL COMMENT '磁卡已交还',
    `f_coverprinted`               tinyint(1) NULL DEFAULT NULL COMMENT '报告封面已打印',
    `f_reportprinted`              tinyint(1) NULL DEFAULT NULL COMMENT '报告已打印：0.未打印 1.已打印',
    `id_reportprintedby`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告打印者ID',
    `datereportprinted`            datetime(0) NULL DEFAULT NULL COMMENT '报告打印日期',
    `f_reportinformed`             tinyint(1) NULL DEFAULT NULL COMMENT '报告领取是否通知',
    `datereportinformed`           datetime(0) NULL DEFAULT NULL COMMENT '报告领取通知时间',
    `f_reportfetched`              tinyint(1) NULL DEFAULT NULL COMMENT '报告是否已取',
    `datereportfetched`            datetime(0) NULL DEFAULT NULL COMMENT '报告领取日期',
    `f_issevere`                   tinyint(1) NULL DEFAULT NULL COMMENT '是否重大疾病',
    `f_closed`                     tinyint(1) NULL DEFAULT NULL COMMENT '是否结案',
    `dateclosed`                   datetime(0) NULL DEFAULT NULL COMMENT '结案日期',
    `f_needtraced`                 tinyint(1) NULL DEFAULT NULL COMMENT '是否需要跟踪',
    `f_diffperson`                 tinyint(1) NULL DEFAULT NULL COMMENT 'FDiffperson',
    `confidentiallevel`            int(0) NULL DEFAULT NULL COMMENT '密级',
    `f_settleall`                  tinyint(1) NULL DEFAULT NULL COMMENT '是否预结',
    `signature`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签名',
    `note`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `dt_lastmodifiedthisat`        datetime(0) NULL DEFAULT NULL COMMENT '最近修改时间',
    `f_inneroper`                  tinyint(1) NULL DEFAULT NULL COMMENT '内部操作标志',
    `severedegree`                 int(0) NULL DEFAULT NULL COMMENT '重症级别',
    `severedegreenote`             varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重症说明',
    `f_severeinformed`             tinyint(1) NULL DEFAULT NULL COMMENT '重症已通知',
    `severeinformtime`             datetime(0) NULL DEFAULT NULL COMMENT '重症通知时间',
    `id_severeinformby`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重症通知人ID',
    `conclusion`                   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '综述',
    `conclusionsummary`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '结论',
    `suggestion`                   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业建议',
    `conclusionrich`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '建议',
    `dietguide`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮食建议',
    `sportguide`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运行建议',
    `knowledge`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康知识',
    `message`                      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '温馨提示',
    `positivesummary`              varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阳性综述',
    `resultcompare`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果比较',
    `interfacemarks`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口标志',
    `patientflag`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者标志',
    `timingstartedat`              datetime(0) NULL DEFAULT NULL COMMENT '体检者记时器开始',
    `timeresultlastchange`         datetime(0) NULL DEFAULT NULL COMMENT '体检者记时器改变时间',
    `timeresultlastarchive`        datetime(0) NULL DEFAULT NULL COMMENT '体检者结果上次档案',
    `timeresultlastolap`           datetime(0) NULL DEFAULT NULL COMMENT '体检者结果上一次OLAP时间',
    `hospitalcode`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id（医院代码）',
    `hospitalname`                 varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医院名称',
    `id_examplace`                 int(0) NULL DEFAULT NULL COMMENT '在pacs登记页面登记的',
    `parsedassignedsuiteandfi`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业锁定人',
    `parsedassignedgroupandfi`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '复查通知单word路径',
    `parsedsuiteandfi`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '禁忌症告知书word路径',
    `parsedsuiteandfilab`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业病告知书word路径',
    `id_guidenurse`                int(0) NULL DEFAULT NULL COMMENT '职业锁定完成',
    `patientnameencoded`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业总检医生',
    `patientcodehiden`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业总检医生ID',
    `f_pdfcreated`                 tinyint(1) NULL DEFAULT NULL COMMENT 'FPdfcreated',
    `f_wordcreated`                tinyint(1) NULL DEFAULT NULL COMMENT 'FWordcreated',
    `f_wordprinted`                tinyint(1) NULL DEFAULT NULL COMMENT 'FWordprinted',
    `guidancenote2`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'guidancenote2',
    `f_usecodehiden`               tinyint(1) NULL DEFAULT NULL COMMENT '团体与个人体检标志：0.个人 1.团体',
    `id_patientclass2`             int(0) NULL DEFAULT NULL COMMENT '收费信息上传标志',
    `id_patientclass3`             tinyint(1) NULL DEFAULT NULL COMMENT '确认来检：0或null.否 1.是',
    `dateregisternotime`           datetime(0) NULL DEFAULT NULL COMMENT '职业总检时间',
    `counterreportprinted`         int(0) NULL DEFAULT NULL COMMENT 'counterreportprinted',
    `f_printcomparingreport`       tinyint(1) NULL DEFAULT NULL COMMENT 'FPrintcomparingreport',
    `f_isrecheck`                  tinyint(1) NULL DEFAULT NULL COMMENT '是否需要复查 0: 未复查 1: 复查',
    `f_settlenone`                 tinyint(1) NULL DEFAULT NULL COMMENT '中间库同步标识 0：登记、重新登记未同步 1：增加项目未同步 2：已同步',
    `f_guidancereturned`           tinyint(1) NULL DEFAULT NULL COMMENT '指引单回收',
    `dateguidancereturned`         datetime(0) NULL DEFAULT NULL COMMENT '预约时间（只保存了日期，没有时分秒）',
    `id_guidancereturnedby`        tinyint(1) NULL DEFAULT NULL COMMENT '收费项目标识 null未上传   1已上传',
    `f_outpatient`                 decimal(1, 0) NULL DEFAULT NULL COMMENT 'FOutpatient',
    `patientnamereceipt`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'patientnamereceipt',
    `patientnamepinyin`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'patientnamepinyin',
    `f_forpreparefinancialconfirm` tinyint(1) NULL DEFAULT NULL COMMENT 'FForpreparefinancialconfirm',
    `statusofhm`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'statusofhm',
    `instancetag`                  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'instancetag',
    `keybirthplace`                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'keybirthplace',
    `keybloodtype`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'keybloodtype',
    `exammethod`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'exammethod',
    `inpatientno`                  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查体检号',
    `insuranceno`                  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补检上一次（上一次体检号）',
    `keypayway`                    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'keypayway',
    `healthcard`                   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'healthcard',
    `exampoint`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'exampoint',
    `fingerprint`                  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'fingerprint',
    `countreportcoverprinted`      int(0) NULL DEFAULT NULL COMMENT 'countreportcoverprinted',
    `countreportprinted`           int(0) NULL DEFAULT NULL COMMENT 'countreportprinted',
    `countreportpdf`               int(0) NULL DEFAULT NULL COMMENT 'countreportpdf',
    `countreportword`              int(0) NULL DEFAULT NULL COMMENT 'countreportword',
    `countreportxml`               tinyint(0) NULL DEFAULT NULL COMMENT '是否替检',
    `countreportcompare`           int(0) NULL DEFAULT NULL COMMENT 'countreportcompare',
    `countreportcomparepdf`        int(0) NULL DEFAULT NULL COMMENT 'countreportcomparepdf',
    `countreportcompareword`       int(0) NULL DEFAULT NULL COMMENT 'countreportcompareword',
    `countreportcomparexml`        int(0) NULL DEFAULT NULL COMMENT 'countreportcomparexml',
    `countreportoccupation`        int(0) NULL DEFAULT NULL COMMENT 'countreportoccupation',
    `countreportoccupationpdf`     int(0) NULL DEFAULT NULL COMMENT '复查通知单生成状态：0.未生成 1.生成中 2.已生成',
    `countreportoccupationword`    int(0) NULL DEFAULT NULL COMMENT 'countreportoccupationword',
    `countreportoccupationxml`     int(0) NULL DEFAULT NULL COMMENT 'countreportoccupationxml',
    `scbs`                         int(0) NULL DEFAULT NULL COMMENT '上传标示',
    `id_tjtc`                      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检套餐ID',
    `jzdw`                         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记账单位',
    `jzdwr`                        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记账人',
    `spr`                          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人',
    `tjr`                          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原体检者姓名',
    `lqfs`                         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领取方式',
    `yzbm`                         int(0) NULL DEFAULT NULL COMMENT '邮政编码',
    `yjaddress`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮寄地址',
    `qtxz`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '前台须知',
    `is_hmdb`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '黑名单备注',
    `is_hmd`                       tinyint(1) NULL DEFAULT NULL COMMENT '黑名单',
    `isjj`                         tinyint(1) NULL DEFAULT NULL COMMENT '是否加急',
    `zgl`                          int(0) NULL DEFAULT NULL COMMENT '总工龄',
    `jhgl`                         int(0) NULL DEFAULT NULL COMMENT '接害工龄',
    `jhys`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接害因素',
    `jktjzt`                       tinyint(0) NULL DEFAULT NULL COMMENT '健康体检状态，详见：com.center.medical.bean.enums.jktjzt',
    `zytjzt`                       tinyint(0) NULL DEFAULT NULL COMMENT '职业体检状态，详见：com.center.medical.bean.enums.jktjzt',
    `tmyd`                         int(0) NULL DEFAULT NULL COMMENT '条码已打',
    `medicaldate`                  datetime(0) NULL DEFAULT NULL COMMENT '预计体检时间',
    `trades`                       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工种',
    `cjjgsfyhf`                    tinyint(1) NULL DEFAULT NULL COMMENT '迟检是否已回访 0：未回访 1：已回访',
    `bhgybsfyhf`                   tinyint(1) NULL DEFAULT NULL COMMENT '不合格样本是否已回访 0：未回访 1：已回访',
    `yxjgsfyhf`                    tinyint(1) NULL DEFAULT NULL COMMENT '阳性结果是否已回访 0：未回访 1：已回访',
    `medicaltype`                  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类别，详见：enums.com.center.medical.bean.MedicalType',
    `prepayment`                   decimal(8, 2) NULL DEFAULT NULL COMMENT '预付金额',
    `tcprice`                      decimal(8, 2) NULL DEFAULT NULL COMMENT '套餐价格',
    `createdate`                   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `cultural`                     tinyint(1) NULL DEFAULT NULL COMMENT '文化程度，详见：enums.com.center.medical.bean.CulturalLevel',
    `ever_of_disease`              varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '既往病名(多个ID逗号连接)',
    `ccnl`                         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-初潮',
    `jq`                           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-经期',
    `zq`                           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-周期',
    `tjnl`                         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-停经年龄',
    `family_number`                varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-现有子女',
    `zc`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_ci NULL DEFAULT NULL COMMENT '月经及生育史-早产',
    `sc`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-死产',
    `lc`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-流产',
    `jt`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-先天畸形',
    `ywrc`                         varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-异常胎',
    `abstain_smoke_note`           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '吸烟史-吸烟情况0：从不吸烟 ；1：偶尔吸烟；2：以往曾经吸烟，现已戒除；3：经常吸烟',
    `everyday_smoke_n`             varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '吸烟史-每天吸烟包数',
    `smoke_year`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '吸烟史-吸烟年数',
    `no_kiss_the_cup`              varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-是否不饮酒',
    `between_kiss_the_cup`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-是否偶饮酒',
    `evermore_kiss`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-是否经常饮酒',
    `abstain_lost_kiss`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-是否戒酒',
    `kiss_year_n`                  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-饮酒年数',
    `kiss_amount`                  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-饮酒量',
    `kiss_type`                    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-饮酒种类',
    `family_of_disease`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '家族病史',
    `symptom`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '症状',
    `is_audit`                     tinyint(1) NULL DEFAULT NULL COMMENT '职业性问诊-是否已审核 0未审核，1已审核，2反审核',
    `ever_of_disease_remark`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '既往病备注',
    `create_report_num`            int(0) NULL DEFAULT NULL COMMENT '报告生成次数',
    `work_date`                    datetime(0) NULL DEFAULT NULL COMMENT '参加工作时间',
    `harm_date`                    datetime(0) NULL DEFAULT NULL COMMENT '接害时间',
    `picture`                      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '照片',
    `advice`                       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康建议 ',
    `disease_print_num`            int(0) NULL DEFAULT NULL COMMENT '职业报告打印次数',
    `health_print_num`             int(0) NULL DEFAULT NULL COMMENT '健康报告打印次数',
    `readytofinal_date`            datetime(0) NULL DEFAULT NULL COMMENT '分拣完成时间',
    `guide_signle_count`           int(0) NULL DEFAULT NULL COMMENT '导引单打印次数',
    `short_code`                   int(0) NULL DEFAULT NULL COMMENT '短体检号',
    `is_noticed`                   tinyint(1) NULL DEFAULT NULL COMMENT '复查通知单PDF',
    `review_pdf`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查通知单PDF',
    `contraindicated_pdf`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否预约通知：1预约已通知 0或NULL预约未通知',
    `disease_pdf`                  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疑似职业病PDF路径',
    `sign_picture`                 longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业问诊签字图片',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                          `IDX_PP_PATIENTNAME`(`patientname`) USING BTREE,
    INDEX                          `PACSPATIENT_ARCHIVE`(`id_patientarchive`) USING BTREE,
    INDEX                          `PACSPATIENT_FISFORPREPARE`(`id`, `f_isforprepare`) USING BTREE,
    INDEX                          `PACSPATIENT_FREGISTERED`(`f_registered`) USING BTREE,
    INDEX                          `PACSPATIENT_F_ISFORRESERVE`(`f_isforreserve`, `id`) USING BTREE,
    INDEX                          `PACSPATIENT_IDCARDNO`(`idcardno`) USING BTREE,
    INDEX                          `PACSPATIENT_IDEXAMTYPE`(`id_examtype`) USING BTREE,
    INDEX                          `PACSPATIENT_IDPATIENTCLASS`(`id_patientclass`) USING BTREE,
    INDEX                          `PACSPATIENT_ID_TJTC`(`id_tjtc`) USING BTREE,
    INDEX                          `PACSPATIENT_INPUTCODE`(`input_code`) USING BTREE,
    INDEX                          `PACSPATIENT_JKTJZT`(`jktjzt`) USING BTREE,
    INDEX                          `PACSPATIENT_VATION`(`id`, `id_orgreservation`) USING BTREE,
    INDEX                          `PACSPATIENT_ZYTJZT`(`zytjzt`) USING BTREE,
    INDEX                          `PACS_PATIENT_CODE_IND`(`patientcode`) USING BTREE,
    INDEX                          `PACS_PEISPATIENT_DATEREG_IDX`(`dateregister`) USING BTREE,
    INDEX                          `PACS_PEISPATIENT_IDSEX`(`id_sex`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS-体检者表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_peispatientfeeitem
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_peispatientfeeitem`;
CREATE TABLE `md_pacs_peispatientfeeitem`
(
    `id`                            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `id_patientfeeitem`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序号',
    `count`                         int(0) NULL DEFAULT NULL COMMENT '数量',
    `id_patient`                    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `id_examfeeitem`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '基础数据的收费项目ID',
    `examfeeitem_name`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称,必填/如果基础里面名称没填，此字段填“未命名”，查id未找到此字段填为“未知项”',
    `price`                         decimal(8, 2) NULL DEFAULT NULL COMMENT '原始价格',
    `factprice`                     decimal(8, 2) NULL DEFAULT NULL COMMENT '优惠价格',
    `settleprice`                   decimal(8, 2) NULL DEFAULT NULL COMMENT '结算价格',
    `f_addeditem`                   tinyint(1) NULL DEFAULT NULL COMMENT '新增项目',
    `id_payway`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式ID',
    `f_registered`                  tinyint(1) NULL DEFAULT NULL COMMENT '已登记',
    `id_doctorreg`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记员ID',
    `doctorreg_r`                   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记员',
    `registertime`                  datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `id_patientregistersheet`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记单ID',
    `f_regreturned`                 tinyint(1) NULL DEFAULT NULL COMMENT '已退登记',
    `f_feecharged`                  tinyint(1) NULL DEFAULT NULL COMMENT '已收费',
    `id_feecharger`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员ID',
    `feecharger_name_r`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员姓名',
    `feechargetime`                 datetime(0) NULL DEFAULT NULL COMMENT '收费时间',
    `id_feediscounter`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打折者ID',
    `batchnumber`                   int(0) NULL DEFAULT NULL COMMENT '检验: 批次',
    `tubeposition`                  int(0) NULL DEFAULT NULL COMMENT '检验: 管位',
    `samplenumber`                  int(0) NULL DEFAULT NULL COMMENT '检验: 样本号',
    `f_labsampled`                  tinyint(1) NULL DEFAULT NULL COMMENT '检验: 已采样',
    `id_labsampler`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '采样者ID',
    `labsampler_name_r`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检验: 采样者',
    `labsampletime`                 datetime(0) NULL DEFAULT NULL COMMENT '检验: 采样时间',
    `f_labsendtolis`                tinyint(1) NULL DEFAULT NULL COMMENT '检验: 已发至LIS',
    `labsendtolistime`              datetime(0) NULL DEFAULT NULL COMMENT '检验: 发至LIS时间',
    `f_labrcvdfromlis`              tinyint(1) NULL DEFAULT NULL COMMENT '检验: 已自LIS收到结果',
    `labrcvdfromlistime`            datetime(0) NULL DEFAULT NULL COMMENT '检验: 收到LIS结果时间',
    `f_giveup`                      tinyint(1) NULL DEFAULT NULL COMMENT '弃检 0 或者null：未弃检 1：弃检',
    `f_examinated`                  tinyint(1) NULL DEFAULT NULL COMMENT '是否检查：0：未检；1：已检；',
    `id_patientexamdepart`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者检查科室',
    `id_examdoctor`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分科检查医师ID',
    `examdoctor_name_r`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分科检查医师',
    `examinatetime`                 datetime(0) NULL DEFAULT NULL COMMENT '检查时间',
    `f_mark_feereturn`              tinyint(1) NULL DEFAULT NULL COMMENT '退费拟退标志',
    `f_work_inner_modify`           tinyint(1) NULL DEFAULT NULL COMMENT '修改标志(内部使用)',
    `severedegree`                  tinyint(1) NULL DEFAULT NULL COMMENT '重症级',
    `positivesummary`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '阳性小结',
    `interfacemarks`                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口标志',
    `urlresult`                     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '结果URL',
    `f_delayed`                     decimal(1, 0) NULL DEFAULT NULL COMMENT '迟检  1为迟捡',
    `dt_delayedtill`                datetime(0) NULL DEFAULT NULL COMMENT 'dtDelayedtill',
    `notewhydelayed`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'notewhydelayed',
    `id_examplace`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'idExamplace',
    `f_transferedhl7`               decimal(1, 0) NULL DEFAULT NULL COMMENT '补检状态 0: 未补检 1：已补检',
    `qty`                           decimal(10, 2) NULL DEFAULT NULL COMMENT 'qty',
    `feeitemdesc`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'feeitemdesc',
    `feeitemsummary`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'feeitemsummary',
    `id_typist`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'idTypist',
    `id_examapprovedby`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'idExamapprovedby',
    `examapprovedby_name_r`         varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'examapprovedbyNameR',
    `samplenumberfromlis`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'word报告路径',
    `samplemsgfromlis`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'pdf报告路径',
    `receiptsheetnofromhis`         varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'receiptsheetnofromhis',
    `feeitemrequestno`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'feeitemrequestno',
    `samplestatus`                  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下载标志   0未同步，1已同步,2更新',
    `backupsingleitemcopiesprinted` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'backupsingleitemcopiesprinted',
    `f_feechargedinttrans`          tinyint(1) NULL DEFAULT NULL COMMENT '性别',
    `giveupnotelet`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'giveupnotelet',
    `create_date`                   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modify_date`                   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `sfjx`                          tinyint(1) NULL DEFAULT NULL COMMENT '是否加项',
    `jxys`                          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '加项医师',
    `id_ks`                         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `createdate`                    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `change_item`                   tinyint(1) NULL DEFAULT NULL COMMENT '换项（相当于删除本项的标记）退项',
    `is_mintc`                      tinyint(1) NULL DEFAULT NULL COMMENT '是否是最小套餐 0：不是 1：是',
    `isbx`                          tinyint(1) NULL DEFAULT NULL COMMENT '是否备选 0： 不是 1： 是',
    `bxcount`                       int(0) NULL DEFAULT NULL COMMENT '备选数量',
    `endtuiprice`                   decimal(8, 2) NULL DEFAULT NULL COMMENT '退费价格',
    `actualprice`                   decimal(8, 2) NULL DEFAULT NULL COMMENT '实际金额',
    `short_code`                    int(0) NULL DEFAULT NULL COMMENT ' 短体检号',
    `sfjj`                          tinyint(1) NULL DEFAULT NULL COMMENT '据检状态  1据检   null未据检',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                           `I_PPF_IDKS`(`id_ks`) USING BTREE,
    INDEX                           `I_PPF_IDPATIENT`(`id_patient`) USING BTREE,
    INDEX                           `PACSITEM_CHARGED`(`f_feecharged`) USING BTREE,
    INDEX                           `PACSITEM_CODE_CHANGEITEM`(`change_item`) USING BTREE,
    INDEX                           `PACSITEM_CODE_GIVEUP`(`f_giveup`) USING BTREE,
    INDEX                           `PACSITEM_REGISTERED`(`f_registered`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS-体检者收费项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_result
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_result`;
CREATE TABLE `md_pacs_result`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `patientname`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者姓名',
    `examfeeitem_code`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目代码',
    `examdatetime`       datetime(0) NULL DEFAULT NULL COMMENT '检查时间',
    `examdoctor`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查师',
    `examresultdesc`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查结果描述',
    `examresultsummary`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查结果总结',
    `examresultisnormal` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查结果是否正常',
    `transftertarget`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'TransfterTarget',
    `f_resulttransfered` tinyint(1) NULL DEFAULT NULL COMMENT 'F_ResultTransfered',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `imagefullpath`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件路径',
    `username`           varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查人用户名',
    `dep_id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `item_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    `item_name`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称',
    `short_code`         int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    `write_date`         datetime(0) NULL DEFAULT NULL COMMENT 'PACS系统：录入时间',
    `pacs_item_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PCAS系统中的基础收费项目ID',
    `is_new_pacs`        tinyint(1) NULL DEFAULT NULL COMMENT '是否是新PACS',
    `update_num`         int(0) NULL DEFAULT NULL COMMENT '版本号',
    `audit_doctor`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核者用户名',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                `IDX_PACSRE_TIME`(`examdatetime`) USING BTREE,
    INDEX                `PR_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS-数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_section_result_main
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_section_result_main`;
CREATE TABLE `md_pacs_section_result_main`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `dep_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `rummager_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查人ID',
    `write_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '录入人ID',
    `rummager_time`     datetime(0) NULL DEFAULT NULL COMMENT '检查时间',
    `write_time`        datetime(0) NULL DEFAULT NULL COMMENT '录入时间',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_audit`          tinyint(1) NULL DEFAULT NULL COMMENT '分检是否已审核',
    `audit_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
    `audit_time`        datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
    `is_danager`        tinyint(1) NULL DEFAULT NULL COMMENT '是否危急值',
    `danager_level`     tinyint(1) NULL DEFAULT NULL COMMENT '危急值级别',
    `conclusions`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康小结',
    `is_delete`         tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `audit_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
    `rummager_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查人姓名',
    `patientcode`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `is_finish`         tinyint(1) NULL DEFAULT NULL COMMENT '上传标识   null未上传  1已上传  分检是否已完成(不用)',
    `zy_conclusions`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业小结',
    `associative_table` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关表名',
    `short_code`        int(0) NULL DEFAULT NULL COMMENT '短体检号',
    `file_type`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
    `file_path`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
    `description`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX               `I_PSM_PATIENTCODE`(`patientcode`) USING BTREE,
    INDEX               `PACS_MAIN_DEPID`(`id`, `dep_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS-检查结果主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pacs_section_result_two
-- ----------------------------
DROP TABLE IF EXISTS `md_pacs_section_result_two`;
CREATE TABLE `md_pacs_section_result_two`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `main_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主表ID',
    `verdict_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PACS检查项目ID',
    `nodule`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PACS检查项目体证词关联表ID',
    `posistive`        tinyint(1) NULL DEFAULT NULL COMMENT '否阳性结果(1阳性  0或NULL非阳性)',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `intensive_level`  tinyint(1) NULL DEFAULT NULL COMMENT '重症级别(弃用)',
    `is_unchecked`     tinyint(1) NULL DEFAULT NULL COMMENT '是否弃检(弃用)',
    `disease_health`   tinyint(1) NULL DEFAULT NULL COMMENT 'disease_health',
    `basconclusion_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PACS结论词ID',
    `division_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `is_delete`        tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `charges_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PACS收费项目ID',
    `ms`               longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'ms',
    `is_danger`        tinyint(1) NULL DEFAULT NULL COMMENT '是否危急值',
    `input_result`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入的结果(弃用)',
    `tjlx`             tinyint(1) NULL DEFAULT NULL COMMENT '体检类型 1.职业 0.非职业      1.职业+健康 0、纯健康 2、纯职业(只有检验科有)(弃用)',
    `short_code`       int(0) NULL DEFAULT NULL COMMENT '短体检号',
    `feeitem_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PACS体检者收费项目ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX              `IDX_PSRT_PATIENTCODE`(`patientcode`) USING BTREE,
    INDEX              `PACS_SECRESTWO_IDMAIN`(`main_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PACS-科室结果表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_patient_users
-- ----------------------------
DROP TABLE IF EXISTS `md_patient_users`;
CREATE TABLE `md_patient_users`
(
    `id`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `phone`      varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `password`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
    `name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `id_card`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `archive_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案ID',
    `status`     varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '激活状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检用户账号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_patienttype
-- ----------------------------
DROP TABLE IF EXISTS `md_patienttype`;
CREATE TABLE `md_patienttype`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patient_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
    `input_code`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `flag`         tinyint(1) NULL DEFAULT NULL COMMENT '如果为1在科室显示红色名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peis_ol
-- ----------------------------
DROP TABLE IF EXISTS `md_peis_ol`;
CREATE TABLE `md_peis_ol`
(
    `id`                         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`                 datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                 datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientbizno`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'patientbizno',
    `org_departsubc`             varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'app订单类型 0活动套餐，1团检套餐 2个性套餐         3自我检测4自主定制',
    `org_departsubd`             varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信商城核销码',
    `org_departsube`             varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否已在APP保存',
    `ageofreal`                  decimal(8, 2) NULL DEFAULT NULL COMMENT '微信商城套餐价格',
    `id_doctorconclusion`        int(0) NULL DEFAULT NULL COMMENT 'app预约号',
    `wechat_code`                varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信沃德小程序体检码',
    `is_wechat_noticed`          tinyint(1) NULL DEFAULT NULL COMMENT '微信沃德小程序是否已通知',
    `wechat_notice_type`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信沃德小程序通知类型  1短信 2微信',
    `wechat_notice_time`         datetime(0) NULL DEFAULT NULL COMMENT '微信沃德小程序通知时间',
    `wechat_notice_failed_count` int(0) NULL DEFAULT NULL COMMENT '通知失败次数',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者线上信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peis_questionnaire
-- ----------------------------
DROP TABLE IF EXISTS `md_peis_questionnaire`;
CREATE TABLE `md_peis_questionnaire`
(
    `id`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`            datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`            datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `patientname`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `id_sex`                varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别id',
    `id_nation`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族id',
    `id_marriage`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻id',
    `address`               varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '长期居住地',
    `phone`                 varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `has_allergy_history`   tinyint(1) NULL DEFAULT NULL COMMENT '有无过敏史 0无1有',
    `report_type`           tinyint(1) NULL DEFAULT NULL COMMENT '体检报告（0纸质版1电子版）',
    `breakfast`             tinyint(1) NULL DEFAULT NULL COMMENT '早餐（0中餐1西餐）',
    `disease_history`       varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人疾病史ids',
    `ph_and_diseases`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '既往史及现在正在治疗的疾病',
    `family_history`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家族史ids',
    `is_smoking`            tinyint(1) NULL DEFAULT NULL COMMENT '是否吸烟（0否1是）',
    `smoke_num`             int(0) NULL DEFAULT NULL COMMENT '吸烟x支/天',
    `is_drinking`           tinyint(1) NULL DEFAULT NULL COMMENT '是否饮酒（0否1是）',
    `drink_type`            varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒种类ids',
    `drink_num`             int(0) NULL DEFAULT NULL COMMENT '饮酒 x ml/周',
    `eating_habits`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮食习惯ids',
    `sports`                int(0) NULL DEFAULT NULL COMMENT '运动情况id',
    `sleep`                 varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '睡眠质量ids',
    `psychological`         int(0) NULL DEFAULT NULL COMMENT '心理情况id',
    `history_abnormal`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '既往体检异常',
    `abnormal`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '本次体检异常',
    `is_preparing_pregnant` tinyint(1) NULL DEFAULT NULL COMMENT '是否有备孕计划（0否1是）',
    `is_having_period`      tinyint(1) NULL DEFAULT NULL COMMENT '是否在生理期（0否1是）',
    `is_pregnant`           tinyint(1) NULL DEFAULT NULL COMMENT '是否在孕期（0否1是）',
    `input_code`            varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `doctorname`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '录入人用户名',
    `write_time`            datetime(0) NULL DEFAULT NULL COMMENT '录入时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者沃德国际健康问卷' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peis_questionnaire_second
-- ----------------------------
DROP TABLE IF EXISTS `md_peis_questionnaire_second`;
CREATE TABLE `md_peis_questionnaire_second`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `patientname`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `id_sex`            varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别 0男1女',
    `birthdate`         datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
    `idcardno`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `is_han`            varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族  1汉族0少数民族',
    `id_province`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省id',
    `id_city`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市id',
    `id_area`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区id',
    `id_marriage`       tinyint(1) NULL DEFAULT NULL COMMENT '婚姻',
    `phone`             varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `family1`           int(0) NULL DEFAULT NULL COMMENT 'family1',
    `family2`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'family2',
    `family3`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'family3',
    `family4`           int(0) NULL DEFAULT NULL COMMENT 'family4',
    `present1`          int(0) NULL DEFAULT NULL COMMENT 'present1',
    `present2`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'present2',
    `present3`          int(0) NULL DEFAULT NULL COMMENT 'present3',
    `allergy1`          int(0) NULL DEFAULT NULL COMMENT 'allergy1',
    `medication1`       int(0) NULL DEFAULT NULL COMMENT 'medication1',
    `medication2`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'medication2',
    `operation1`        int(0) NULL DEFAULT NULL COMMENT 'operation1',
    `body1`             int(0) NULL DEFAULT NULL COMMENT 'body1',
    `body2`             int(0) NULL DEFAULT NULL COMMENT 'body2',
    `body3`             int(0) NULL DEFAULT NULL COMMENT 'body3',
    `body4`             int(0) NULL DEFAULT NULL COMMENT 'body4',
    `body5`             int(0) NULL DEFAULT NULL COMMENT 'body5',
    `body6`             int(0) NULL DEFAULT NULL COMMENT 'body6',
    `body7`             int(0) NULL DEFAULT NULL COMMENT 'body7',
    `body8`             int(0) NULL DEFAULT NULL COMMENT 'body8',
    `body9`             int(0) NULL DEFAULT NULL COMMENT 'body9',
    `smoke1`            int(0) NULL DEFAULT NULL COMMENT 'smoke1',
    `smoke2`            int(0) NULL DEFAULT NULL COMMENT 'smoke2',
    `smoke3`            int(0) NULL DEFAULT NULL COMMENT 'smoke3',
    `smoke4`            int(0) NULL DEFAULT NULL COMMENT 'smoke4',
    `drink1`            int(0) NULL DEFAULT NULL COMMENT 'drink1',
    `sport1`            int(0) NULL DEFAULT NULL COMMENT 'sport1',
    `sport2`            int(0) NULL DEFAULT NULL COMMENT 'sport2',
    `sport3`            int(0) NULL DEFAULT NULL COMMENT 'sport3',
    `environment1`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'environment1',
    `sleep1`            int(0) NULL DEFAULT NULL COMMENT 'sleep1',
    `sleep2int`         int(0) NULL DEFAULT NULL COMMENT 'sleep2',
    `examination1`      int(0) NULL DEFAULT NULL COMMENT 'examination1',
    `creator`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
    `sleep2_other`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'sleep2Other',
    `medication2_other` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'medication2Other',
    `present2_other`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'present2Other',
    `family3_other`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'family3Other',
    `family2_other`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'family2Other',
    `operation1_other`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'operation1Other',
    `app_user_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信小程序用户id',
    `sleep2`            varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sleep2',
    `body10`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'body10',
    `patientbizno`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'patientbizno',
    `bmi`               decimal(10, 2) NULL DEFAULT NULL COMMENT 'bmi',
    `height`            decimal(5, 2) NULL DEFAULT NULL COMMENT '身高m',
    `weight`            decimal(5, 2) NULL DEFAULT NULL COMMENT '体重kg',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX               `IDX_QUES2_CODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '健康体检问卷' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peis_reser_payway
-- ----------------------------
DROP TABLE IF EXISTS `md_peis_reser_payway`;
CREATE TABLE `md_peis_reser_payway`
(
    `id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `id_charge`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者结算表ID',
    `moneyamountpaid`     decimal(8, 2) NULL DEFAULT NULL COMMENT '结算金额',
    `moneyamountpaiddate` datetime(0) NULL DEFAULT NULL COMMENT '结算日期',
    `id_feecharger`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员ID',
    `id_payway`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款方式ID',
    `createdate`          datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`          datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `note`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `cardno`              varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检卡卡号',
    `is_charged`          tinyint(1) NULL DEFAULT NULL COMMENT '已收费',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者结算方式表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peis_state
-- ----------------------------
DROP TABLE IF EXISTS `md_peis_state`;
CREATE TABLE `md_peis_state`
(
    `id`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`            datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`            datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `f_diffperson`          tinyint(1) NULL DEFAULT NULL COMMENT '是否从标软提取',
    `f_inneroper`           tinyint(1) NULL DEFAULT NULL COMMENT 'DESCRIBE上传标志',
    `patientflag`           varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区疾控上传标志',
    `id_patientclass2`      tinyint(1) NULL DEFAULT NULL COMMENT '收费信息上传标志',
    `id_guidancereturnedby` tinyint(1) NULL DEFAULT NULL COMMENT '收费项目上传标志',
    `scbs`                  tinyint(1) NULL DEFAULT NULL COMMENT '体检者上传标志',
    `countreportpdf`        tinyint(1) NULL DEFAULT NULL COMMENT '核酸报告上传标志',
    `countreportcomparexml` tinyint(1) NULL DEFAULT NULL COMMENT '是否上传至大数据(线上)',
    `is_batch_registered`   tinyint(1) NULL DEFAULT NULL COMMENT '是否是批量登记的，批量登记的不进排队。数据库中增加此列前的数据，isBatchRegistered都是0。',
    `jinan_msg`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '济南上传错误原因',
    `jinan_status`          tinyint(1) NULL DEFAULT NULL COMMENT '济南上传状态',
    `upload_date`           datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
    `health_care_id`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病平台ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者上传状态' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatient
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatient`;
CREATE TABLE `md_peispatient`
(
    `id`                           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'XID',
    `id_patient`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序号',
    `id_orgpatient`                int(0) NULL DEFAULT NULL COMMENT '预定体检编码',
    `id_cis`                       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_CIS',
    `id_patientarchive`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案ID',
    `patientcode`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `patientcodeprn`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检代码打印格式',
    `patientarchiveno`             varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案代码',
    `patientcardno`                varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一卡通号',
    `patientbizno`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务编号',
    `idcardno`                     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `dailynumberdate`              int(0) NULL DEFAULT NULL COMMENT '每日编码日期',
    `dailynumber`                  int(0) NULL DEFAULT NULL COMMENT '每日编码',
    `numtotal`                     int(0) NULL DEFAULT NULL COMMENT '总编号',
    `numyear`                      int(0) NULL DEFAULT NULL COMMENT '年编号',
    `nummonth`                     int(0) NULL DEFAULT NULL COMMENT '月编号',
    `numday`                       int(0) NULL DEFAULT NULL COMMENT '日编号',
    `numorg`                       int(0) NULL DEFAULT NULL COMMENT '团体编号',
    `numorgresv`                   int(0) NULL DEFAULT NULL COMMENT '订单号',
    `id_patientlinked`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_PATIENTLINKED',
    `id_nonorg`                    int(0) NULL DEFAULT NULL COMMENT '非团体虚拟团体ID',
    `patientname`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `input_code`                   varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `id_orgreservationgroup`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务分组ID',
    `id_orgreservation`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预定任务ID',
    `id_org`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `org_name`                     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体名称',
    `org_depart`                   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体部门',
    `org_departsuba`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ORG_DEPARTSUBA',
    `org_departsubb`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ORG_DEPARTSUBB',
    `org_departsubc`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ORG_DEPARTSUBC',
    `org_departsubd`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ORG_DEPARTSUBD',
    `org_departsube`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ORG_DEPARTSUBE',
    `have_private`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否有隐私项目：0.无 1.有',
    `id_payway`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式ID',
    `payway`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式',
    `offpercent`                   decimal(8, 4) NULL DEFAULT NULL COMMENT '扣率',
    `maxoffpercent`                decimal(8, 4) NULL DEFAULT NULL COMMENT '最大扣率',
    `personpricelimit`             decimal(8, 2) NULL DEFAULT NULL COMMENT '统收限额',
    `id_sex`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别ID',
    `sex`                          varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `birthdate`                    datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
    `age`                          int(0) NULL DEFAULT NULL COMMENT '年龄',
    `id_ageunit`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄单位ID',
    `ageunit`                      varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄单位',
    `ageofreal`                    int(0) NULL DEFAULT NULL COMMENT '实数年龄',
    `id_marriage`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻ID',
    `marriage`                     varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻',
    `id_nation`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族ID',
    `nation`                       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族',
    `address`                      varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
    `id_informway`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知方式ID',
    `id_opendoctor`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开单医生ID',
    `email`                        varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
    `phone`                        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `id_patientclass`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者类型ID',
    `id_education`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教育ID',
    `education`                    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教育程度',
    `id_occupation`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业ID',
    `occupation`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业',
    `id_resarea`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区ID',
    `resarea`                      varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区',
    `dateinorganization`           datetime(0) NULL DEFAULT NULL COMMENT '入职日期',
    `f_isforprepare`               tinyint(1) NULL DEFAULT NULL COMMENT '预定（已备单）',
    `f_isforreserve`               tinyint(1) NULL DEFAULT NULL COMMENT '预约',
    `datecreated`                  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `f_registered`                 tinyint(1) NULL DEFAULT NULL COMMENT '已登记',
    `dateregister`                 datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `position_code`                varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置',
    `jobtype_code`                 varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作类型编码',
    `moneyamount`                  decimal(8, 2) NULL DEFAULT NULL COMMENT '应付金额',
    `moneyamountpaid`              decimal(8, 2) NULL DEFAULT NULL COMMENT '实付金额',
    `guidancenote`                 varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指引单说明（导引单备注）',
    `workno`                       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工号',
    `id_doctorreg`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记员ID',
    `doctorreg`                    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记员',
    `id_examtype`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型ID',
    `id_examsuite`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型',
    `examsuite_name`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐名称',
    `examsuite_alias`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMSUITE_ALIAS',
    `id_doctorconclusion`          int(0) NULL DEFAULT NULL COMMENT '(不使用)',
    `doctorconclusion`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '(不使用)2',
    `id_doctorapply`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开单医生ID2总检锁定人',
    `doctorapply`                  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开单医生',
    `f_guidanceprinted`            tinyint(1) NULL DEFAULT NULL COMMENT '指引单已打',
    `f_feecharged`                 tinyint(1) NULL DEFAULT NULL COMMENT '已缴费',
    `f_examstarted`                tinyint(1) NULL DEFAULT NULL COMMENT '已开始体检',
    `f_readytofinal`               tinyint(1) NULL DEFAULT NULL COMMENT '分检完成',
    `id_doctorfee`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员ID',
    `doctorfee`                    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员',
    `f_paused`                     tinyint(1) NULL DEFAULT NULL COMMENT '禁检',
    `f_finallocked`                tinyint(1) NULL DEFAULT NULL COMMENT '总检锁定',
    `f_finalexamed`                decimal(1, 0) NULL DEFAULT NULL COMMENT '总检完成',
    `f_finalapproved`              tinyint(1) NULL DEFAULT NULL COMMENT '总审完成',
    `id_doctorfinal`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总检医生ID',
    `doctorfinal_name_r`           varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总检医生',
    `datefinalexamed`              datetime(0) NULL DEFAULT NULL COMMENT '总检时间',
    `id_doctorfinalapproved`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总审医生',
    `datefinalapproved`            datetime(0) NULL DEFAULT NULL COMMENT '总审时间',
    `f_cardissued`                 tinyint(1) NULL DEFAULT NULL COMMENT '磁卡已发',
    `f_cardreturned`               tinyint(1) NULL DEFAULT NULL COMMENT '磁卡已交还',
    `f_coverprinted`               tinyint(1) NULL DEFAULT NULL COMMENT '报告封面已打印',
    `f_reportprinted`              tinyint(1) NULL DEFAULT NULL COMMENT '报告已打印',
    `id_reportprintedby`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告打印者ID',
    `datereportprinted`            datetime(0) NULL DEFAULT NULL COMMENT '报告打印日期',
    `f_reportinformed`             tinyint(1) NULL DEFAULT NULL COMMENT '报告领取通知',
    `datereportinformed`           datetime(0) NULL DEFAULT NULL COMMENT '报告领取通知时间',
    `f_reportfetched`              tinyint(1) NULL DEFAULT NULL COMMENT '报告已取',
    `datereportfetched`            datetime(0) NULL DEFAULT NULL COMMENT '报告领取日期',
    `f_issevere`                   tinyint(1) NULL DEFAULT NULL COMMENT '重大疾病',
    `f_closed`                     tinyint(1) NULL DEFAULT NULL COMMENT '结案',
    `dateclosed`                   datetime(0) NULL DEFAULT NULL COMMENT '结案日期',
    `f_needtraced`                 tinyint(1) NULL DEFAULT NULL COMMENT '需要跟踪',
    `f_diffperson`                 tinyint(1) NULL DEFAULT NULL COMMENT '非本人体检(是否替检)',
    `confidentiallevel`            int(0) NULL DEFAULT NULL COMMENT '密级',
    `f_settleall`                  decimal(1, 0) NULL DEFAULT NULL COMMENT '预结',
    `signature`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '签名',
    `note`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `dt_lastmodifiedthisat`        datetime(0) NULL DEFAULT NULL COMMENT '最近修改时间',
    `f_inneroper`                  tinyint(1) NULL DEFAULT NULL COMMENT '内部操作标志',
    `severedegree`                 int(0) NULL DEFAULT NULL COMMENT '重症级别',
    `severedegreenote`             varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重症说明',
    `f_severeinformed`             tinyint(1) NULL DEFAULT NULL COMMENT '重症已通知',
    `severeinformtime`             datetime(0) NULL DEFAULT NULL COMMENT '重症通知时间',
    `id_severeinformby`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重症通知人ID',
    `conclusion`                   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '综述',
    `conclusionsummary`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '结论',
    `suggestion`                   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '建议',
    `conclusionrich`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '建议(暂不使用)',
    `dietguide`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮食建议',
    `sportguide`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运行建议',
    `knowledge`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康知识',
    `message`                      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '温馨提示',
    `positivesummary`              varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阳性综述',
    `resultcompare`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果比较',
    `interfacemarks`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口标志',
    `patientflag`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者标志',
    `timingstartedat`              datetime(0) NULL DEFAULT NULL COMMENT '体检者记时器开始',
    `timeresultlastchange`         datetime(0) NULL DEFAULT NULL COMMENT '体检者记时器改变时间',
    `timeresultlastarchive`        datetime(0) NULL DEFAULT NULL COMMENT '体检者结果上次档案',
    `timeresultlastolap`           datetime(0) NULL DEFAULT NULL COMMENT '体检者结果上一次OLAP时间',
    `hospitalcode`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医院代码',
    `hospitalname`                 varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医院名称',
    `id_examplace`                 decimal(65, 30) NULL DEFAULT NULL COMMENT 'ID_EXAMPLACE',
    `parsedassignedsuiteandfi`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业锁定人',
    `parsedassignedgroupandfi`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '复查通知单word路径',
    `parsedsuiteandfi`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '禁忌症告知书word路径',
    `parsedsuiteandfilab`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业病告知书word路径',
    `id_guidenurse`                decimal(65, 30) NULL DEFAULT NULL COMMENT '职业锁定完成',
    `patientnameencoded`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业总检医生',
    `patientcodehiden`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业总检医生ID',
    `f_pdfcreated`                 tinyint(1) NULL DEFAULT NULL COMMENT 'F_PDFCREATED',
    `f_wordcreated`                tinyint(1) NULL DEFAULT NULL COMMENT 'F_WORDCREATED',
    `f_wordprinted`                tinyint(1) NULL DEFAULT NULL COMMENT 'F_WORDPRINTED',
    `guidancenote2`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GUIDANCENOTE2',
    `f_usecodehiden`               tinyint(1) NULL DEFAULT NULL COMMENT 'F_USECODEHIDEN',
    `id_patientclass2`             int(0) NULL DEFAULT NULL COMMENT 'ID_PATIENTCLASS2',
    `id_patientclass3`             int(0) NULL DEFAULT NULL COMMENT 'ID_PATIENTCLASS3',
    `dateregisternotime`           datetime(0) NULL DEFAULT NULL COMMENT 'DATEREGISTERNOTIME',
    `counterreportprinted`         int(0) NULL DEFAULT NULL COMMENT 'COUNTERREPORTPRINTED',
    `f_printcomparingreport`       tinyint(1) NULL DEFAULT NULL COMMENT 'F_PRINTCOMPARINGREPORT',
    `f_isrecheck`                  tinyint(1) NULL DEFAULT NULL COMMENT '0: 未复查 1: 复查',
    `f_settlenone`                 tinyint(1) NULL DEFAULT NULL COMMENT 'F_SETTLENONE',
    `f_guidancereturned`           tinyint(1) NULL DEFAULT NULL COMMENT 'F_GUIDANCERETURNED',
    `dateguidancereturned`         datetime(0) NULL DEFAULT NULL COMMENT 'DATEGUIDANCERETURNED',
    `id_guidancereturnedby`        int(0) NULL DEFAULT NULL COMMENT 'ID_GUIDANCERETURNEDBY',
    `f_outpatient`                 tinyint(1) NULL DEFAULT NULL COMMENT 'F_OUTPATIENT',
    `patientnamereceipt`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PATIENTNAMERECEIPT',
    `patientnamepinyin`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PATIENTNAMEPINYIN',
    `f_forpreparefinancialconfirm` tinyint(1) NULL DEFAULT NULL COMMENT 'F_FORPREPAREFINANCIALCONFIRM',
    `statusofhm`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'STATUSOFHM',
    `instancetag`                  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INSTANCETAG',
    `keybirthplace`                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYBIRTHPLACE',
    `keybloodtype`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYBLOODTYPE',
    `exammethod`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMMETHOD',
    `inpatientno`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INPATIENTNO',
    `insuranceno`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INSURANCENO',
    `keypayway`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'KEYPAYWAY',
    `healthcard`                   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'HEALTHCARD',
    `exampoint`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMPOINT',
    `fingerprint`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'FINGERPRINT',
    `countreportcoverprinted`      int(0) NULL DEFAULT NULL COMMENT 'COUNTREPORTCOVERPRINTED',
    `countreportprinted`           int(0) NULL DEFAULT NULL COMMENT 'COUNTREPORTPRINTED',
    `countreportpdf`               int(0) NULL DEFAULT NULL COMMENT 'COUNTREPORTPDF',
    `countreportword`              int(0) NULL DEFAULT NULL COMMENT 'COUNTREPORTWORD',
    `countreportxml`               int(0) NULL DEFAULT NULL COMMENT 'COUNTREPORTXML',
    `countreportcompare`           int(0) NULL DEFAULT NULL COMMENT 'COUNTREPORTCOMPARE',
    `countreportcomparepdf`        int(0) NULL DEFAULT NULL COMMENT 'COUNTREPORTCOMPAREPDF',
    `countreportcompareword`       int(0) NULL DEFAULT NULL COMMENT 'COUNTREPORTCOMPAREWORD',
    `countreportcomparexml`        int(0) NULL DEFAULT NULL COMMENT 'COUNTREPORTCOMPAREXML',
    `countreportoccupation`        int(0) NULL DEFAULT NULL COMMENT '体检者来源：关联sys_business_source表source_id',
    `countreportoccupationpdf`     int(0) NULL DEFAULT NULL COMMENT '复查通知单生成状态：0未生成 1生成中 2已生成',
    `countreportoccupationword`    int(0) NULL DEFAULT NULL COMMENT '复查通知单生成状态：0未生成 1生成中 2已生成',
    `countreportoccupationxml`     int(0) NULL DEFAULT NULL COMMENT '客户证件类型，详见：enums.com.center.medical.bean.CusCardType',
    `scbs`                         tinyint(1) NULL DEFAULT NULL COMMENT '上传标示',
    `id_tjtc`                      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检套餐ID',
    `jzdw`                         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记账单位',
    `jzdwr`                        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记账人',
    `spr`                          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人',
    `tjr`                          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '替检人',
    `lqfs`                         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领取方式',
    `yzbm`                         int(0) NULL DEFAULT NULL COMMENT '邮政编码',
    `yjaddress`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮寄地址',
    `qtxz`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '前台须知',
    `is_hmdb`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '黑名单备注',
    `is_hmd`                       tinyint(1) NULL DEFAULT NULL COMMENT '黑名单',
    `isjj`                         tinyint(1) NULL DEFAULT NULL COMMENT '是否加急',
    `zgl`                          int(0) NULL DEFAULT NULL COMMENT '总工龄',
    `jhgl`                         int(0) NULL DEFAULT NULL COMMENT '接害工龄',
    `jhys`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '接害因素',
    `jktjzt`                       tinyint(0) NULL DEFAULT NULL COMMENT '健康体检状态，详见：com.center.medical.bean.enums.jktjzt',
    `zytjzt`                       tinyint(0) NULL DEFAULT NULL COMMENT '职业体检状态，详见：com.center.medical.bean.enums.jktjzt',
    `tmyd`                         int(0) NULL DEFAULT NULL COMMENT '条码打印次数',
    `medicaldate`                  datetime(0) NULL DEFAULT NULL COMMENT '体检时间',
    `trades`                       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工种',
    `cjjgsfyhf`                    tinyint(1) NULL DEFAULT NULL COMMENT '迟检是否已回访：0:未回访 1：已回访',
    `bhgybsfyhf`                   tinyint(1) NULL DEFAULT NULL COMMENT '不合格样本是否已回访：0:未回访 1：已回访',
    `yxjgsfyhf`                    tinyint(1) NULL DEFAULT NULL COMMENT '阳性结果是否已回访：0:未回访 1：已回访',
    `medicaltype`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上岗前 在岗期间 离岗时',
    `prepayment`                   decimal(8, 2) NULL DEFAULT NULL COMMENT '预付金额',
    `tcprice`                      decimal(8, 2) NULL DEFAULT NULL COMMENT 'X套餐价格',
    `createdate`                   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                   datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `cultural`                     tinyint(1) NULL DEFAULT NULL COMMENT '文化水平，详见：enums.com.center.medical.bean.CulturalLevel',
    `ever_of_disease`              varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '既往病ids',
    `ccnl`                         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '初潮年龄',
    `jq`                           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经期',
    `zq`                           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '周期',
    `tjnl`                         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '停经年龄',
    `family_number`                varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '现有子女人数',
    `zc`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '早产',
    `sc`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '死产',
    `lc`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流产',
    `jt`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '畸胎',
    `ywrc`                         varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异位妊娠',
    `abstain_smoke_note`           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '吸烟情况',
    `everyday_smoke_n`             varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '每天吸烟支数',
    `smoke_year`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '吸烟年数',
    `no_kiss_the_cup`              varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否不饮酒',
    `between_kiss_the_cup`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否偶饮酒',
    `evermore_kiss`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否经常饮酒',
    `abstain_lost_kiss`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否戒酒',
    `kiss_year_n`                  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经常饮酒年数',
    `kiss_amount`                  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒量',
    `kiss_type`                    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒种类',
    `family_of_disease`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '家族病史',
    `symptom`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '症状',
    `is_audit`                     tinyint(1) NULL DEFAULT NULL COMMENT '是否已审核（职业性问诊）',
    `ever_of_disease_remark`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '既往病备注',
    `create_report_num`            int(0) NULL DEFAULT NULL COMMENT '生成次数',
    `work_date`                    datetime(0) NULL DEFAULT NULL COMMENT '参加工作时间',
    `harm_date`                    datetime(0) NULL DEFAULT NULL COMMENT '从事该工种时间',
    `picture`                      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '照片',
    `advice`                       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康建议',
    `disease_print_num`            int(0) NULL DEFAULT NULL COMMENT '职业报告打印次数',
    `health_print_num`             int(0) NULL DEFAULT NULL COMMENT '健康报告打印次数',
    `readytofinal_date`            datetime(0) NULL DEFAULT NULL COMMENT '分检完成时间',
    `guide_signle_count`           int(0) NULL DEFAULT NULL COMMENT '导引单打印次数',
    `short_code`                   int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    `is_noticed`                   tinyint(1) NULL DEFAULT NULL COMMENT '是否预约通知：0或null.否 1.是',
    `review_pdf`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查PDF路径',
    `contraindicated_pdf`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业禁忌证PDF路径',
    `disease_pdf`                  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疑似职业病PDF路径',
    `sign_picture`                 longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '签名图片',
    `is_new_pacs`                  tinyint(1) NULL DEFAULT NULL COMMENT 'is_new_pacs',
    `ts_limit`                     decimal(8, 2) NULL DEFAULT NULL COMMENT '统收限额（未用）',
    `committee`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'committee',
    `street`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'street',
    `checkout_date`                datetime(0) NULL DEFAULT NULL COMMENT '点击已结账的结账日期',
    `checkout_status`              tinyint(1) NULL DEFAULT NULL COMMENT '结账状态 0或者Null：未结账 1：已结账',
    `physique`                     varchar(105) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'physique',
    `doc_name`                     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'doc_name',
    `worktype_id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工种id  base_worktype',
    `id_examclass`                 tinyint(1) NULL DEFAULT NULL COMMENT '检查类型，关联md_register_type表id',
    `original_trade`               varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原始工种（名称,不需要是工种表里存在的）',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                          `DOCTORAPPLY`(`doctorapply`) USING BTREE,
    INDEX                          `IDX_PATIENT_IDOPEN`(`id_opendoctor`) USING BTREE,
    INDEX                          `IDX_PATIENT_INPATIENTNO`(`inpatientno`) USING BTREE,
    INDEX                          `I_PEISPATIENT_PINGANID`(`patientnamereceipt`) USING BTREE,
    INDEX                          `PATIENT_CIS_INDEX`(`id_cis`) USING BTREE,
    INDEX                          `PATIENT_CODE_INDEX`(`patientcode`) USING BTREE,
    INDEX                          `PATIENT_FZX_INDEX`(`hospitalcode`) USING BTREE,
    INDEX                          `PATIENT_MEDICALDATE`(`medicaldate`) USING BTREE,
    INDEX                          `PATIENT_NAME`(`patientname`) USING BTREE,
    INDEX                          `PEISPATIENT_COUNTREPORTOCCU`(`countreportoccupation`) USING BTREE,
    INDEX                          `PEISPATIENT_FHSY`(`id_examtype`, `f_examstarted`, `f_readytofinal`) USING BTREE,
    INDEX                          `PEISPATIENT_FISFORPREPARE`(`f_isforprepare`, `id`) USING BTREE,
    INDEX                          `PEISPATIENT_FREGISTERED`(`f_registered`) USING BTREE,
    INDEX                          `PEISPATIENT_F_ISFORRESERVE`(`f_isforreserve`, `id`) USING BTREE,
    INDEX                          `PEISPATIENT_IDCARDNO`(`idcardno`) USING BTREE,
    INDEX                          `PEISPATIENT_IDEXAMTYPE`(`id_examtype`) USING BTREE,
    INDEX                          `PEISPATIENT_IDPATIENTCLASS`(`id_patientclass`) USING BTREE,
    INDEX                          `PEISPATIENT_IDSEX`(`id_sex`) USING BTREE,
    INDEX                          `PEISPATIENT_ID_TJTC`(`id_tjtc`) USING BTREE,
    INDEX                          `PEISPATIENT_INPUTCODE`(`input_code`) USING BTREE,
    INDEX                          `PEISPATIENT_JKTJZT`(`jktjzt`) USING BTREE,
    INDEX                          `PEISPATIENT_NUMORGRESV_INDEX`(`numorgresv`) USING BTREE,
    INDEX                          `PEISPATIENT_PHONE_INDEX`(`phone`) USING BTREE,
    INDEX                          `PEISPATIENT_VATION`(`id`, `id_orgreservation`) USING BTREE,
    INDEX                          `PEISPATIENT_ZYTJZT`(`zytjzt`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'QT体检者表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatient_and_fzx
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatient_and_fzx`;
CREATE TABLE `md_peispatient_and_fzx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patient_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id',
    `fzx_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    `xzzt`       tinyint(1) NULL DEFAULT NULL COMMENT '下载状态：0未下载 1已下载',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分组分中心' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatient_charge_main
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatient_charge_main`;
CREATE TABLE `md_peispatient_charge_main`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `note`            varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'note',
    `moneyamount`     decimal(8, 2) NULL DEFAULT NULL COMMENT '应付',
    `moneyamountpaid` decimal(8, 2) NULL DEFAULT NULL COMMENT '实付',
    `patientcode`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `version`         bigint(0) NULL DEFAULT NULL COMMENT '版本号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX             `PEISCHARGEMAIN_CODE_INDEX`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者费用主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatient_charge_other
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatient_charge_other`;
CREATE TABLE `md_peispatient_charge_other`
(
    `id`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `order_no`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
    `transaction_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易号',
    `patientcode`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `fee_name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '费用名称',
    `price`          decimal(8, 2) NULL DEFAULT NULL COMMENT '待支付金额',
    `paid_price`     decimal(8, 2) NULL DEFAULT NULL COMMENT '支付金额',
    `pay_status`     tinyint(1) NULL DEFAULT NULL COMMENT '支付状态：0 未支付 1 已支付',
    `order_type`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单类型',
    `chage_time`     datetime(0) NULL DEFAULT NULL COMMENT '收费时间',
    `charge_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者其他缴费' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatient_charge_record
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatient_charge_record`;
CREATE TABLE `md_peispatient_charge_record`
(
    `id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`          datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`          datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`         varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `method`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '方法',
    `version`             bigint(0) NULL DEFAULT NULL COMMENT '版本号',
    `moneyamount`         decimal(8, 2) NULL DEFAULT NULL COMMENT '应付',
    `moneyamountpaid`     decimal(8, 2) NULL DEFAULT NULL COMMENT '实付',
    `moneyamount_old`     decimal(8, 2) NULL DEFAULT NULL COMMENT '原应付',
    `moneyamountpaid_old` decimal(8, 2) NULL DEFAULT NULL COMMENT '原实付',
    `username`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人用户名',
    `note`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                 `PEISPATIENTCHARGERECORD_CODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '收费记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatient_consultation
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatient_consultation`;
CREATE TABLE `md_peispatient_consultation`
(
    `id`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`             datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`             datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `ever_of_disease`        varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '既往病名(多个ID逗号连接)',
    `ccnl`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-初潮',
    `jq`                     varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-经期',
    `zq`                     varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-周期',
    `tjnl`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-停经年龄',
    `family_number`          varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-现有子女',
    `zc`                     varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-早产',
    `sc`                     varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-死产',
    `lc`                     varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-流产',
    `jt`                     varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-先天畸形',
    `ywrc`                   varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '月经及生育史-异常胎',
    `abstain_smoke_note`     varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '吸烟史-吸烟情况 0：从不吸烟 ；1：偶尔吸烟；2：以往曾经吸烟，现已戒除；3：经常吸烟',
    `everyday_smoke_n`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '吸烟史-每天吸烟包数',
    `smoke_year`             varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '吸烟史-吸烟年数',
    `no_kiss_the_cup`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-是否不饮酒',
    `between_kiss_the_cup`   varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-是否偶饮酒',
    `evermore_kiss`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-是否经常饮酒',
    `abstain_lost_kiss`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-是否戒酒',
    `kiss_year_n`            varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-饮酒年数',
    `kiss_amount`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-饮酒量',
    `kiss_type`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮酒史-饮酒种类',
    `family_of_disease`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '家族病史',
    `symptom`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '症状',
    `is_audit`               tinyint(1) NULL DEFAULT NULL COMMENT '职业性问诊-是否已审核 0未审核，1已审核，2反审核',
    `ever_of_disease_remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '既往病备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职业问诊' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatient_consultation_pic
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatient_consultation_pic`;
CREATE TABLE `md_peispatient_consultation_pic`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `sign_picture` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '签名base64',
    `datecreated`  datetime(0) NULL DEFAULT NULL COMMENT '签名日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职业问诊签名图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatient_photo
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatient_photo`;
CREATE TABLE `md_peispatient_photo`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `createdate`  datetime(0) NULL DEFAULT NULL,
    `modifydate`  datetime(0) NULL DEFAULT NULL,
    `patientcode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `picture`     longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '照片base64',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者头像' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatient_reservation_charge
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatient_reservation_charge`;
CREATE TABLE `md_peispatient_reservation_charge`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `id_orgreservation` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
    `patientcode`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `moneyamountpaid`   decimal(8, 2) NULL DEFAULT NULL COMMENT '结算金额',
    `jzje`              decimal(8, 2) NULL DEFAULT NULL COMMENT '记账金额',
    `note`              varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `id_payway`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '付款方式ID',
    `id_feecharger`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员ID',
    `id_charge`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费信息ID',
    `jsdate`            datetime(0) NULL DEFAULT NULL COMMENT '结算日期(弃用)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者结算表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatientarchive
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatientarchive`;
CREATE TABLE `md_peispatientarchive`
(
    `id`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `id_patientarchive`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案ID',
    `patientarchiveno`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案号',
    `patientcardno`            varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一卡通号',
    `idcardno`                 varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `patientname`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `input_code`               varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `id_sex`                   int(0) NULL DEFAULT NULL COMMENT '性别ID',
    `sex`                      varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `age`                      int(0) NULL DEFAULT NULL COMMENT '年龄',
    `agebasedate`              datetime(0) NULL DEFAULT NULL COMMENT '年龄的时间基准',
    `birthdate`                datetime(0) NULL DEFAULT NULL COMMENT '生日',
    `id_marriage`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻ID',
    `marriage`                 varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻',
    `id_nation`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族ID',
    `nation`                   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族',
    `address`                  varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
    `phone`                    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `id_education`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教育ID',
    `education`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教育程度',
    `id_occupation`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业ID',
    `occupation`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业',
    `id_resarea`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区ID',
    `resarea`                  varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地区',
    `dateregister`             datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `note`                     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `create_date`              datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modify_date`              datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `ishmd`                    tinyint(1) NULL DEFAULT NULL COMMENT '是否黑名单',
    `hmdbz`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '黑名单备注',
    `restatus`                 tinyint(1) NULL DEFAULT NULL COMMENT '回访状态',
    `yjgzbj`                   tinyint(1) NULL DEFAULT NULL COMMENT '0   ; 1',
    `memberlevel`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'vip、vvip等等',
    `createdate`               datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`               datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `cultural`                 tinyint(1) NULL DEFAULT NULL COMMENT '文化程度，详见：enums.com.center.medical.bean.CulturalLevel',
    `is_org`                   tinyint(1) NULL DEFAULT NULL COMMENT '是否团检 0：个人 1：团检',
    `membercreate`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `khjl`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户经理',
    `jf`                       decimal(16, 0) NULL DEFAULT NULL COMMENT '积分',
    `fzx`                      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心',
    `dw`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
    `is_delete`                tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `countreportoccupationxml` tinyint(0) NULL DEFAULT NULL COMMENT '客户证件类型，详见：enums.com.center.medical.bean.CusCardType',
    `is_main`                  tinyint(1) NULL DEFAULT NULL COMMENT '是否主持卡人  1是  0或null不是',
    `source`                   tinyint(1) NULL DEFAULT NULL COMMENT '档案来源  1家庭卡、超级会员管理新增的档案',
    `old_card`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定前用过的旧卡号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者档案表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatientcharge
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatientcharge`;
CREATE TABLE `md_peispatientcharge`
(
    `id_patientcharge`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '充值id',
    `id_patientchargesheet`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_PATIENTCHARGESHEET',
    `patientcode`                 varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号(ID)',
    `id_payway`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式ID',
    `payway`                      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式',
    `moneyamount`                 decimal(8, 2) NULL DEFAULT NULL COMMENT '金额应付',
    `moneyamountpaid`             decimal(8, 2) NULL DEFAULT NULL COMMENT '金额实付',
    `f_work_byorgdelta`           tinyint(1) NULL DEFAULT NULL COMMENT '(内部使用)',
    `f_feetransfered`             tinyint(1) NULL DEFAULT NULL COMMENT '金额已接口',
    `f_feeconfirmed`              tinyint(1) NULL DEFAULT NULL COMMENT '收费确认',
    `f_feecharged`                tinyint(1) NULL DEFAULT NULL COMMENT '已收费',
    `f_feechargedinttrans`        tinyint(1) NULL DEFAULT NULL COMMENT '下载标志  0或NULL未下载   1已下载 2更新',
    `f_feechargedoncredit`        tinyint(1) NULL DEFAULT NULL COMMENT '已收费(记帐)',
    `f_isreturn`                  tinyint(1) NULL DEFAULT NULL COMMENT '为退费',
    `f_receiptprinted`            tinyint(1) NULL DEFAULT NULL COMMENT '发票已打印',
    `id_feecharger`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员ID',
    `feechargetime`               datetime(0) NULL DEFAULT NULL COMMENT '缴费时间',
    `note`                        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `ver_thisrecord`              int(0) NULL DEFAULT NULL COMMENT '行版本',
    `f_work_inner_modify`         tinyint(1) NULL DEFAULT NULL COMMENT '内部使用',
    `leagmembercardno`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LEAGMEMBERCARDNO',
    `id_examplace`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_EXAMPLACE',
    `receiptsheetnofrominterface` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否复查额度消费  1是null不是',
    `f_feechargedbyinterface`     tinyint(1) NULL DEFAULT NULL COMMENT '上传标志  1已上传    其他 未上传',
    `whofeechargedbyinterface`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否已上传bigdata线上大数据报表  1是  0/null否',
    `intpatientcharge_code`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'INTPATIENTCHARGE_CODE',
    `intpatientcharge_date`       datetime(0) NULL DEFAULT NULL COMMENT 'INTPATIENTCHARGE_DATE',
    `is_jq`                       tinyint(1) NULL DEFAULT NULL COMMENT '是否结清：0:未结清 1：结清',
    `is_totalcharge`              tinyint(1) NULL DEFAULT NULL COMMENT '是否统收：0:不是统收1：统收',
    `cardno`                      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检卡卡号',
    `is_delete`                   tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `id`                          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `createdate`                  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                  datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `num_index`                   int(0) NULL DEFAULT NULL COMMENT '顺序',
    `short_code`                  int(0) NULL DEFAULT NULL COMMENT '体检短号',
    `is_add`                      tinyint(1) NULL DEFAULT NULL COMMENT '是否是加项收费   1 是  null不是',
    `trade_no`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
    `charge_main_version`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主单版本（用于统一最新版本）',
    `charge_main_id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主表ID',
    `tx_trade_no`                 varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付平台交易流水号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                         `CHARGE_FEECHARGETIME`(`feechargetime`) USING BTREE,
    INDEX                         `G_INX_PCHARGE_PATIENTCODE`(`short_code`) USING BTREE,
    INDEX                         `PEISPATIENTCHARGE_DELETE`(`is_delete`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者缴费表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatientexamitem
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatientexamitem`;
CREATE TABLE `md_peispatientexamitem`
(
    `id`                      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `associative_table`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联表名',
    `id_patientexamitem`      decimal(65, 30) NULL DEFAULT NULL COMMENT 'ID_PATIENTEXAMITEM',
    `id_patientexamdepart`    decimal(65, 30) NULL DEFAULT NULL COMMENT '体检者检查分科ID(没用)',
    `id_patientfeeitem`       decimal(65, 30) NULL DEFAULT NULL COMMENT '体检者收费项目ID(没用)',
    `patientcode`             varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `id_examfeeitem`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    `examfeeitem`             varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称',
    `id_examitem`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目ID',
    `examitem_name_r`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目名称(冗余)（名称）',
    `examitem_code_r`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目编码(冗余)(LIS接口代码)',
    `id_examitemvalue`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查体检描述ID',
    `severedegree`            int(0) NULL DEFAULT NULL COMMENT '重症级别',
    `refrange`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '参考范围（LIS范围）',
    `examitemvalues`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查项目体征词',
    `examitemvaluestext`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查项目体征: 描述',
    `examitemvaluesshort`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查项目体征: 简单描述(结果)',
    `examitemvaluesnumber`    double NULL DEFAULT NULL COMMENT '检查项目体征: 数字',
    `labitemflag`             varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目标志（标志）',
    `f_labitemnormal`         tinyint(1) NULL DEFAULT NULL COMMENT '是否 正常',
    `examitemvaluesnumber2`   double NULL DEFAULT NULL COMMENT '检查项目体征: 数字2',
    `examitemvaluesnumber3`   double NULL DEFAULT NULL COMMENT '检查项目体征: 数字3',
    `befid_disporder_r`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目中检查项目行序',
    `rowcreatetime`           datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `f_labrcvdfromlis`        decimal(1, 0) NULL DEFAULT NULL COMMENT 'LIS结果已收(不使用)',
    `valueoper`               varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'VALUEOPER',
    `valueoper2`              varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'VALUEOPER2',
    `createdate`              datetime(0) NULL DEFAULT NULL COMMENT '创建时间1',
    `modifydate`              datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `status`                  varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
    `lis_code`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LIS代码',
    `units`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
    `dep_id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `report_range`            varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告结果',
    `examitemvaluesreport`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告范围',
    `patient_name`            varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者姓名',
    `exam_doctor`             varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查医师',
    `exam_date_time`          datetime(0) NULL DEFAULT NULL COMMENT '检查时间',
    `image_full_path`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图片路径',
    `audit_name`              varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人',
    `inspect_name`            varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查人',
    `audit_date`              datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
    `reflow`                  decimal(16, 8) NULL DEFAULT NULL COMMENT 'LIS范围/报告范围 低',
    `refhigh`                 decimal(16, 8) NULL DEFAULT NULL COMMENT 'LIS范围/报告范围 高',
    `lisybbh`                 decimal(16, 8) NULL DEFAULT NULL COMMENT 'LIS样本编号',
    `table_value`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '关联表数据',
    `zy_conclusions`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业小结',
    `conclusions`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康小结',
    `section_result_two_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查结果子表数据',
    `ms`                      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '描述',
    `input_result`            varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入结果',
    `is_unchecked`            tinyint(1) NULL DEFAULT NULL COMMENT '弃检',
    `basconclusion_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结伦词ID',
    `positive`                tinyint(1) NULL DEFAULT NULL COMMENT '是否阳性结果',
    `examitem_nameprn`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目打印名称',
    `examfeeitem_nameprn`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目',
    `type`                    tinyint(1) NULL DEFAULT NULL COMMENT '检查类型：0:健康检查类型 1:职业检查类型 2:健康+职业(职业)',
    `posistive`               tinyint(1) NULL DEFAULT NULL COMMENT '是否阳性结果',
    `zy_status`               varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业状态',
    `inspect_code`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查医师代码',
    `short_code`              int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    `receive_date`            datetime(0) NULL DEFAULT NULL COMMENT '收样时间（虹桥lis）',
    `adicon_code`             varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '艾迪康代码',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                     `PEISPATIENTEXAMITEM_EXAMID`(`id_examitem`) USING BTREE,
    INDEX                     `PEXAMITEM_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'LIS结果(LisPacs数据)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatientfeeitem
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatientfeeitem`;
CREATE TABLE `md_peispatientfeeitem`
(
    `id_patientfeeitem`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序号',
    `id_patient`                    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `id_examfeeitem`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者收费项目ID',
    `examfeeitem_name`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称:必填/如果基础里面名称没填，此字段填“未命名”，查id未找到此字段填为“未知项”',
    `price`                         decimal(8, 2) NULL DEFAULT NULL COMMENT '原始价格',
    `factprice`                     decimal(8, 2) NULL DEFAULT NULL COMMENT '优惠价格',
    `settleprice`                   decimal(8, 2) NULL DEFAULT NULL COMMENT '结算价格(目前仅用于团检)',
    `f_addeditem`                   tinyint(1) NULL DEFAULT NULL COMMENT '新增项目(不再使用)',
    `id_payway`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式ID',
    `f_registered`                  tinyint(1) NULL DEFAULT NULL COMMENT '已登记',
    `id_doctorreg`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记员ID',
    `doctorreg_r`                   varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记员(冗余)',
    `registertime`                  datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `id_patientregistersheet`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记单ID',
    `f_regreturned`                 tinyint(1) NULL DEFAULT NULL COMMENT '已退登记',
    `f_feecharged`                  tinyint(1) NULL DEFAULT NULL COMMENT '已收费',
    `id_feecharger`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员ID',
    `feecharger_name_r`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费员姓名(冗余)',
    `feechargetime`                 datetime(0) NULL DEFAULT NULL COMMENT '收费时间',
    `id_feediscounter`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打折者ID',
    `batchnumber`                   int(0) NULL DEFAULT NULL COMMENT '检验: 批次',
    `tubeposition`                  int(0) NULL DEFAULT NULL COMMENT '检验: 管位',
    `samplenumber`                  int(0) NULL DEFAULT NULL COMMENT '检验: 样本号',
    `f_labsampled`                  tinyint(1) NULL DEFAULT NULL COMMENT '检验: 已采样',
    `id_labsampler`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '采样者ID',
    `labsampler_name_r`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检验: 采样者',
    `labsampletime`                 datetime(0) NULL DEFAULT NULL COMMENT '检验: 采样时间',
    `f_labsendtolis`                tinyint(1) NULL DEFAULT NULL COMMENT '检验: 已发至LIS',
    `labsendtolistime`              datetime(0) NULL DEFAULT NULL COMMENT '检验: 发至LIS时间',
    `f_labrcvdfromlis`              tinyint(1) NULL DEFAULT NULL COMMENT '检验: 已自LIS收到结果',
    `labrcvdfromlistime`            datetime(0) NULL DEFAULT NULL COMMENT '检验: 收到LIS结果时间',
    `f_giveup`                      tinyint(1) NULL DEFAULT NULL COMMENT '弃检',
    `f_examinated`                  tinyint(1) NULL DEFAULT NULL COMMENT '0：未检；1：已检；',
    `id_patientexamdepart`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者检查科室',
    `id_examdoctor`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分科检查医师ID',
    `examdoctor_name_r`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分科检查医师',
    `examinatetime`                 datetime(0) NULL DEFAULT NULL COMMENT '分科检查时间',
    `f_mark_feereturn`              tinyint(1) NULL DEFAULT NULL COMMENT '退费拟退标志',
    `f_work_inner_modify`           tinyint(1) NULL DEFAULT NULL COMMENT '修改标志(内部使用)',
    `severedegree`                  tinyint(1) NULL DEFAULT NULL COMMENT '重症级',
    `positivesummary`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '阳性小结',
    `interfacemarks`                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口标志',
    `urlresult`                     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '结果URL',
    `f_delayed`                     tinyint(1) NULL DEFAULT NULL COMMENT '迟检  1为迟捡',
    `dt_delayedtill`                datetime(0) NULL DEFAULT NULL COMMENT '退费时间',
    `notewhydelayed`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'NOTEWHYDELAYED',
    `id_examplace`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_EXAMPLACE',
    `f_transferedhl7`               tinyint(1) NULL DEFAULT NULL COMMENT '补检状态 0: 未补检 1：已补检',
    `qty`                           int(0) NULL DEFAULT NULL COMMENT '序号',
    `feeitemdesc`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者收费项目备注',
    `feeitemsummary`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'feeitemsummary',
    `id_typist`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '寿光jypacs插入中间库标志   1已插入  null未插入',
    `id_examapprovedby`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID_EXAMAPPROVEDBY',
    `examapprovedby_name_r`         varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'EXAMAPPROVEDBY_NAME_R',
    `samplenumberfromlis`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SAMPLENUMBERFROMLIS',
    `samplemsgfromlis`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否是十周年卡套餐里的项目   1是',
    `receiptsheetnofromhis`         varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'RECEIPTSHEETNOFROMHIS',
    `feeitemrequestno`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'FEEITEMREQUESTNO',
    `samplestatus`                  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下载标志：0未同步，1已同步,2更新',
    `backupsingleitemcopiesprinted` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'BACKUPSINGLEITEMCOPIESPRINTED',
    `f_feechargedinttrans`          tinyint(1) NULL DEFAULT NULL COMMENT '性别',
    `giveupnotelet`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否已做  1已做  null或其他未做   在样本录入中录入后会变成已做',
    `create_date`                   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modify_date`                   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `sfjx`                          tinyint(1) NULL DEFAULT NULL COMMENT '0:未加项 1：加项',
    `jxys`                          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '加项医师',
    `id_ks`                         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `count`                         tinyint(1) NULL DEFAULT NULL COMMENT '数量',
    `id`                            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `createdate`                    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                    datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `change_item`                   tinyint(1) NULL DEFAULT NULL COMMENT '换项',
    `is_mintc`                      tinyint(1) NULL DEFAULT NULL COMMENT '是否是最小套餐：0不是 1是',
    `isbx`                          tinyint(1) NULL DEFAULT NULL COMMENT '是否备选：0不是 1是',
    `bxcount`                       tinyint(0) NULL DEFAULT NULL COMMENT '备选数量',
    `endtuiprice`                   decimal(8, 2) NULL DEFAULT NULL COMMENT '退费价格',
    `actualprice`                   decimal(8, 2) NULL DEFAULT NULL COMMENT '实际金额',
    `short_code`                    int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    `qjr`                           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '弃检人用户名',
    `bjr`                           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补检人用户名',
    `cjr`                           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '迟检人用户名',
    `qjsj`                          datetime(0) NULL DEFAULT NULL COMMENT '弃检操作时间',
    `bjsj`                          datetime(0) NULL DEFAULT NULL COMMENT '补检操作时间',
    `cjsj`                          datetime(0) NULL DEFAULT NULL COMMENT '迟检操作时间',
    `sfjj`                          tinyint(1) NULL DEFAULT NULL COMMENT '据检状态  1据检   null未据检',
    `jjr`                           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '据检人用户名',
    `jjsj`                          datetime(0) NULL DEFAULT NULL COMMENT '据检时间',
    `jjrqm`                         varchar(155) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '据检人签名路径',
    `item_group`                    int(0) NULL DEFAULT NULL COMMENT 'item_group',
    `adicon_code`                   varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '艾迪康代码',
    `charge_id`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费表ID',
    `trade_no`                      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
    `isconfirm`                     tinyint(0) NULL DEFAULT NULL COMMENT 'isconfirm',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                           `IDX_PFEEITEM_TIME`(`feechargetime`) USING BTREE,
    INDEX                           `PEISPATIENTFEEITEME_KS_ID`(`id_patient`, `id_ks`) USING BTREE,
    INDEX                           `PEXAMITEM_CHARGED`(`f_feecharged`) USING BTREE,
    INDEX                           `PEXAMITEM_CODE_CHANGEITEM`(`change_item`) USING BTREE,
    INDEX                           `PEXAMITEM_CODE_GIVEUP`(`f_giveup`) USING BTREE,
    INDEX                           `PEXAMITEM_REGISTERED`(`f_registered`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者收费项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatienthistory
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatienthistory`;
CREATE TABLE `md_peispatienthistory`
(
    `id_patient`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_orgpatient`                int(0) NULL DEFAULT NULL,
    `id_cis`                       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_patientarchive`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `patientcode`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `patientcodeprn`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `patientarchiveno`             varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `patientcardno`                varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `patientbizno`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `idcardno`                     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `dailynumberdate`              datetime(0) NULL DEFAULT NULL,
    `dailynumber`                  int(0) NULL DEFAULT NULL,
    `numtotal`                     int(0) NULL DEFAULT NULL,
    `numyear`                      int(0) NULL DEFAULT NULL,
    `nummonth`                     int(0) NULL DEFAULT NULL,
    `numday`                       int(0) NULL DEFAULT NULL,
    `numorg`                       int(0) NULL DEFAULT NULL,
    `numorgresv`                   int(0) NULL DEFAULT NULL,
    `id_patientlinked`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_nonorg`                    int(0) NULL DEFAULT NULL,
    `patientname`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `input_code`                   varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_orgreservationgroup`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_orgreservation`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_org`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `org_name`                     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `org_depart`                   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `org_departsuba`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `org_departsubb`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `org_departsubc`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `org_departsubd`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `org_departsube`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `have_private`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_payway`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `payway`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `offpercent`                   decimal(8, 4) NULL DEFAULT NULL,
    `maxoffpercent`                decimal(8, 4) NULL DEFAULT NULL,
    `personpricelimit`             int(0) NULL DEFAULT NULL,
    `id_sex`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `sex`                          varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `birthdate`                    datetime(0) NULL DEFAULT NULL,
    `age`                          int(0) NULL DEFAULT NULL,
    `id_ageunit`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `ageunit`                      varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `ageofreal`                    int(0) NULL DEFAULT NULL,
    `id_marriage`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `marriage`                     varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_nation`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `nation`                       varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `address`                      varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_informway`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_opendoctor`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `email`                        varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `phone`                        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_patientclass`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_education`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `education`                    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_occupation`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `occupation`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_resarea`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `resarea`                      varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `dateinorganization`           datetime(0) NULL DEFAULT NULL,
    `f_isforprepare`               tinyint(1) NULL DEFAULT NULL,
    `f_isforreserve`               tinyint(1) NULL DEFAULT NULL,
    `datecreated`                  datetime(0) NULL DEFAULT NULL,
    `f_registered`                 tinyint(1) NULL DEFAULT NULL,
    `dateregister`                 datetime(0) NULL DEFAULT NULL,
    `position_code`                varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `jobtype_code`                 varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `moneyamount`                  decimal(8, 2) NULL DEFAULT NULL,
    `moneyamountpaid`              decimal(8, 2) NULL DEFAULT NULL,
    `guidancenote`                 varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `workno`                       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_doctorreg`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `doctorreg`                    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_examtype`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_examsuite`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `examsuite_name`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `examsuite_alias`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_doctorconclusion`          int(0) NULL DEFAULT NULL,
    `doctorconclusion`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_doctorapply`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `doctorapply`                  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `f_guidanceprinted`            tinyint(1) NULL DEFAULT NULL,
    `f_feecharged`                 tinyint(1) NULL DEFAULT NULL,
    `f_examstarted`                tinyint(1) NULL DEFAULT NULL,
    `f_readytofinal`               tinyint(1) NULL DEFAULT NULL,
    `id_doctorfee`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `doctorfee`                    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `f_paused`                     tinyint(1) NULL DEFAULT NULL,
    `f_finallocked`                tinyint(1) NULL DEFAULT NULL,
    `f_finalexamed`                tinyint(1) NULL DEFAULT NULL,
    `f_finalapproved`              tinyint(1) NULL DEFAULT NULL,
    `id_doctorfinal`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `doctorfinal_name_r`           varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `datefinalexamed`              datetime(0) NULL DEFAULT NULL,
    `id_doctorfinalapproved`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `datefinalapproved`            datetime(0) NULL DEFAULT NULL,
    `f_cardissued`                 tinyint(1) NULL DEFAULT NULL,
    `f_cardreturned`               tinyint(1) NULL DEFAULT NULL,
    `f_coverprinted`               tinyint(1) NULL DEFAULT NULL,
    `f_reportprinted`              tinyint(1) NULL DEFAULT NULL,
    `id_reportprintedby`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `datereportprinted`            datetime(0) NULL DEFAULT NULL,
    `f_reportinformed`             tinyint(1) NULL DEFAULT NULL,
    `datereportinformed`           datetime(0) NULL DEFAULT NULL,
    `f_reportfetched`              tinyint(1) NULL DEFAULT NULL,
    `datereportfetched`            datetime(0) NULL DEFAULT NULL,
    `f_issevere`                   tinyint(1) NULL DEFAULT NULL,
    `f_closed`                     tinyint(1) NULL DEFAULT NULL,
    `dateclosed`                   datetime(0) NULL DEFAULT NULL,
    `f_needtraced`                 tinyint(1) NULL DEFAULT NULL,
    `f_diffperson`                 tinyint(1) NULL DEFAULT NULL,
    `confidentiallevel`            int(0) NULL DEFAULT NULL,
    `f_settleall`                  tinyint(1) NULL DEFAULT NULL,
    `signature`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `note`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `dt_lastmodifiedthisat`        datetime(0) NULL DEFAULT NULL,
    `f_inneroper`                  tinyint(1) NULL DEFAULT NULL,
    `severedegree`                 int(0) NULL DEFAULT NULL,
    `severedegreenote`             varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `f_severeinformed`             tinyint(1) NULL DEFAULT NULL,
    `severeinformtime`             datetime(0) NULL DEFAULT NULL,
    `id_severeinformby`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `conclusion`                   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `conclusionsummary`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `suggestion`                   longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `conclusionrich`               varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `dietguide`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `sportguide`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `knowledge`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `message`                      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `positivesummary`              varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `resultcompare`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `interfacemarks`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `patientflag`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `timingstartedat`              datetime(0) NULL DEFAULT NULL,
    `timeresultlastchange`         datetime(0) NULL DEFAULT NULL,
    `timeresultlastarchive`        datetime(0) NULL DEFAULT NULL,
    `timeresultlastolap`           datetime(0) NULL DEFAULT NULL,
    `hospitalcode`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `hospitalname`                 varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_examplace`                 decimal(65, 30) NULL DEFAULT NULL,
    `parsedassignedsuiteandfi`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `parsedassignedgroupandfi`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `parsedsuiteandfi`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `parsedsuiteandfilab`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `id_guidenurse`                decimal(65, 30) NULL DEFAULT NULL,
    `patientnameencoded`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `patientcodehiden`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `f_pdfcreated`                 tinyint(1) NULL DEFAULT NULL,
    `f_wordcreated`                tinyint(1) NULL DEFAULT NULL,
    `f_wordprinted`                tinyint(1) NULL DEFAULT NULL,
    `guidancenote2`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `f_usecodehiden`               tinyint(1) NULL DEFAULT NULL,
    `id_patientclass2`             int(0) NULL DEFAULT NULL,
    `id_patientclass3`             int(0) NULL DEFAULT NULL,
    `dateregisternotime`           datetime(0) NULL DEFAULT NULL,
    `counterreportprinted`         int(0) NULL DEFAULT NULL,
    `f_printcomparingreport`       tinyint(1) NULL DEFAULT NULL,
    `f_isrecheck`                  tinyint(1) NULL DEFAULT NULL,
    `f_settlenone`                 tinyint(1) NULL DEFAULT NULL,
    `f_guidancereturned`           tinyint(1) NULL DEFAULT NULL,
    `dateguidancereturned`         datetime(0) NULL DEFAULT NULL,
    `id_guidancereturnedby`        decimal(65, 30) NULL DEFAULT NULL,
    `f_outpatient`                 tinyint(1) NULL DEFAULT NULL,
    `patientnamereceipt`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `patientnamepinyin`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `f_forpreparefinancialconfirm` tinyint(1) NULL DEFAULT NULL,
    `statusofhm`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `instancetag`                  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `keybirthplace`                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `keybloodtype`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `exammethod`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `inpatientno`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `insuranceno`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `keypayway`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `healthcard`                   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `exampoint`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `fingerprint`                  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `countreportcoverprinted`      int(0) NULL DEFAULT NULL,
    `countreportprinted`           int(0) NULL DEFAULT NULL,
    `countreportpdf`               int(0) NULL DEFAULT NULL,
    `countreportword`              int(0) NULL DEFAULT NULL,
    `countreportxml`               int(0) NULL DEFAULT NULL,
    `countreportcompare`           int(0) NULL DEFAULT NULL,
    `countreportcomparepdf`        int(0) NULL DEFAULT NULL,
    `countreportcompareword`       int(0) NULL DEFAULT NULL,
    `countreportcomparexml`        int(0) NULL DEFAULT NULL,
    `countreportoccupation`        int(0) NULL DEFAULT NULL,
    `countreportoccupationpdf`     int(0) NULL DEFAULT NULL,
    `countreportoccupationword`    int(0) NULL DEFAULT NULL,
    `countreportoccupationxml`     int(0) NULL DEFAULT NULL,
    `scbs`                         tinyint(1) NULL DEFAULT NULL,
    `id_tjtc`                      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `jzdw`                         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `jzdwr`                        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `spr`                          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tjr`                          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `lqfs`                         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `yzbm`                         decimal(6, 0) NULL DEFAULT NULL,
    `yjaddress`                    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `qtxz`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `is_hmdb`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `is_hmd`                       tinyint(1) NULL DEFAULT NULL,
    `isjj`                         tinyint(1) NULL DEFAULT NULL,
    `zgl`                          int(0) NULL DEFAULT NULL,
    `jhgl`                         int(0) NULL DEFAULT NULL,
    `jhys`                         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `jktjzt`                       tinyint(0) NULL DEFAULT NULL,
    `zytjzt`                       tinyint(0) NULL DEFAULT NULL,
    `tmyd`                         int(0) NULL DEFAULT NULL,
    `id`                           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `medicaldate`                  datetime(0) NULL DEFAULT NULL,
    `trades`                       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `cjjgsfyhf`                    tinyint(1) NULL DEFAULT NULL,
    `bhgybsfyhf`                   tinyint(1) NULL DEFAULT NULL,
    `yxjgsfyhf`                    tinyint(1) NULL DEFAULT NULL,
    `medicaltype`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `prepayment`                   decimal(8, 2) NULL DEFAULT NULL,
    `tcprice`                      decimal(8, 2) NULL DEFAULT NULL,
    `createdate`                   datetime(0) NULL DEFAULT NULL,
    `modifydate`                   datetime(0) NULL DEFAULT NULL,
    `cultural`                     tinyint(1) NULL DEFAULT NULL,
    `ever_of_disease`              varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `ccnl`                         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `jq`                           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `zq`                           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `tjnl`                         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `family_number`                varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `zc`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `sc`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `lc`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `jt`                           varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `ywrc`                         varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `abstain_smoke_note`           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `everyday_smoke_n`             varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `smoke_year`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `no_kiss_the_cup`              varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `between_kiss_the_cup`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `evermore_kiss`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `abstain_lost_kiss`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `kiss_year_n`                  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `kiss_amount`                  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `kiss_type`                    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `family_of_disease`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `symptom`                      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `is_audit`                     tinyint(1) NULL DEFAULT NULL,
    `ever_of_disease_remark`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `create_report_num`            int(0) NULL DEFAULT NULL,
    `work_date`                    datetime(0) NULL DEFAULT NULL,
    `harm_date`                    datetime(0) NULL DEFAULT NULL,
    `picture`                      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `advice`                       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `disease_print_num`            int(0) NULL DEFAULT NULL,
    `health_print_num`             int(0) NULL DEFAULT NULL,
    `readytofinal_date`            datetime(0) NULL DEFAULT NULL,
    `guide_signle_count`           int(0) NULL DEFAULT NULL,
    `short_code`                   int(0) NULL DEFAULT NULL,
    `is_noticed`                   tinyint(1) NULL DEFAULT NULL,
    `review_pdf`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `contraindicated_pdf`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `disease_pdf`                  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `sign_picture`                 longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `is_new_pacs`                  tinyint(0) NULL DEFAULT NULL,
    `ts_limit`                     int(0) NULL DEFAULT NULL,
    `committee`                    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `street`                       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
    `checkout_date`                datetime(0) NULL DEFAULT NULL,
    `checkout_status`              tinyint(0) NULL DEFAULT NULL,
    `physique`                     varchar(105) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `doc_name`                     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `worktype_id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `id_examclass`                 tinyint(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检者（history）表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peispatientsccl
-- ----------------------------
DROP TABLE IF EXISTS `md_peispatientsccl`;
CREATE TABLE `md_peispatientsccl`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `clname`     varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '策略名称',
    `id_fzx`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `clcontent`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '策略内容',
    `jm`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简码',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '体检号生成策略' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_peissortexam
-- ----------------------------
DROP TABLE IF EXISTS `md_peissortexam`;
CREATE TABLE `md_peissortexam`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `group_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体分组id',
    `sort_date`  datetime(0) NULL DEFAULT NULL COMMENT '天',
    `order_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单id',
    `sort_num`   int(0) NULL DEFAULT NULL COMMENT '序号',
    `tcid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '套餐id',
    `am`         int(0) NULL DEFAULT NULL COMMENT '上午人数',
    `pm`         int(0) NULL DEFAULT NULL COMMENT '下午人数',
    `creator`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预约人用户名',
    `bz`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '排检' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pricture
-- ----------------------------
DROP TABLE IF EXISTS `md_pricture`;
CREATE TABLE `md_pricture`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `url`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
    `section_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `patientcode`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `pricture_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片名称',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS图片存储表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_printtype
-- ----------------------------
DROP TABLE IF EXISTS `md_printtype`;
CREATE TABLE `md_printtype`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `print_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打印项目分类名称',
    `input_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更改日期',
    `note`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `seq`        int(0) NULL DEFAULT NULL COMMENT '序号',
    `shunxu`     int(0) NULL DEFAULT NULL COMMENT '顺序 实际使用此字段排序',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '销售打印分类设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_pufftube
-- ----------------------------
DROP TABLE IF EXISTS `md_pufftube`;
CREATE TABLE `md_pufftube`
(
    `patientcode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `trontimage`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'trontimage',
    `sideimage`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sideimage',
    `result`      varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果',
    `createtime`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `fzx`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心',
    `is_update`   varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否更新',
    `md5`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'md5'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '噗噗管' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_qyjjlx
-- ----------------------------
DROP TABLE IF EXISTS `md_qyjjlx`;
CREATE TABLE `md_qyjjlx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `qyjjlx`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业经济类型名称',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '企业经济类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_receipt_type
-- ----------------------------
DROP TABLE IF EXISTS `md_receipt_type`;
CREATE TABLE `md_receipt_type`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `receipt_type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票团体名称',
    `receipt_type_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票类型代码',
    `input_code`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `note`              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '发票类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_receiveandsell
-- ----------------------------
DROP TABLE IF EXISTS `md_receiveandsell`;
CREATE TABLE `md_receiveandsell`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `clientid`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户公共池ID',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `lqrq`       datetime(0) NULL DEFAULT NULL COMMENT '操作日期',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '假删状态',
    `czzt`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户公共池领取与领取人员关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_report
-- ----------------------------
DROP TABLE IF EXISTS `md_report`;
CREATE TABLE `md_report`
(
    `id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`         varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`          datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`          datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `disease_health`      tinyint(1) NULL DEFAULT NULL COMMENT '职业/健康',
    `is_print_message`    tinyint(1) NULL DEFAULT NULL COMMENT '是否打印复查通知单',
    `url_word`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '报告存放位置（word）',
    `url_pdf`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '报告存放位置（pdf）',
    `status`              tinyint(0) NULL DEFAULT NULL COMMENT '体检状态，详见：com.center.medical.bean.enums.jktjzt',
    `is_total`            tinyint(1) NULL DEFAULT NULL COMMENT '是否已总检',
    `print_man`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打印人名称',
    `print_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打印人ID',
    `print_time`          datetime(0) NULL DEFAULT NULL COMMENT '打印时间',
    `create_num`          int(0) NULL DEFAULT NULL COMMENT '生成次数',
    `print_num`           int(0) NULL DEFAULT NULL COMMENT '打印次数',
    `first_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一审人ID',
    `first_man`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一审人名称',
    `first_time`          datetime(0) NULL DEFAULT NULL COMMENT '一审时间',
    `first_reason`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '一审未通过原因',
    `second_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二审人ID',
    `second_man`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二审人名称',
    `second_time`         datetime(0) NULL DEFAULT NULL COMMENT '二审时间',
    `second_reason`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '二审未通过原因',
    `last_id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终审人ID',
    `last_man`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '终审人名称',
    `last_time`           datetime(0) NULL DEFAULT NULL COMMENT '终审时间',
    `last_reason`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '终审未通过原因',
    `join_person_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交出人ID',
    `join_person_man`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交出人名称',
    `join_time`           datetime(0) NULL DEFAULT NULL COMMENT '交出时间',
    `rev_person_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接受人ID',
    `rev_person_man`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接受人名称',
    `rev_time`            datetime(0) NULL DEFAULT NULL COMMENT '接受时间',
    `grant_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发放方式ID',
    `chest_num`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '柜子号',
    `notification_result` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知结果',
    `getter_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告人领取ID（内部销售领取用）',
    `getter_phone`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告领取人电话',
    `getter_name`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告领取人姓名（代领用，内部人领带出来）',
    `issue_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告发放人ID',
    `return_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '反领取ID',
    `get_time`            datetime(0) NULL DEFAULT NULL COMMENT '领取时间',
    `return_time`         datetime(0) NULL DEFAULT NULL COMMENT '反领取时间',
    `express_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '快递公司ID',
    `express_num`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '快递号',
    `patientname`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `sex`                 varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `age`                 int(0) NULL DEFAULT NULL COMMENT '年龄',
    `id_org`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `org_name`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '团体名称',
    `dateregister`        datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `phone`               varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `id_opendoctor`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开单医生ID',
    `doctorapply`         varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开单医生',
    `id_doctorfinal`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总检医生ID',
    `doctorfinal_name_r`  varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总检医生',
    `datefinalexamed`     datetime(0) NULL DEFAULT NULL COMMENT '总检时间',
    `numorgresv`          decimal(65, 30) NULL DEFAULT NULL COMMENT '任务编号',
    `tbbz`                tinyint(1) NULL DEFAULT NULL COMMENT '同步标志',
    `notify_date`         datetime(0) NULL DEFAULT NULL COMMENT '通知时间',
    `notify_memo`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '通知备注',
    `generate_name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报告生成人',
    `generate_hint`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '报告错误提示',
    `generate_date`       datetime(0) NULL DEFAULT NULL COMMENT '报告生成时间',
    `short_code`          int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    `config_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置文件id',
    `is_nuclein`          tinyint(1) NULL DEFAULT NULL COMMENT '是否是核酸报告：1是 null不是',
    `noticer`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知操作人',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                 `REPORT_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'BG报告主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_report_url
-- ----------------------------
DROP TABLE IF EXISTS `md_report_url`;
CREATE TABLE `md_report_url`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `dep_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `patientcode`    varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `word_url`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'WORD存放位置',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_head`        tinyint(1) NULL DEFAULT NULL COMMENT '是否为头文件',
    `pdf_url`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PDF存放位置',
    `dep_name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室名称',
    `disease_health` tinyint(1) NULL DEFAULT NULL COMMENT '职业/健康  0：健康；1 职业',
    `pdf_url_head`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'pdf存放位置（用于单科室出报告存放pdf地址）',
    `word_url_head`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'word存放位置（用于单科室出报告存放word地址）',
    `short_code`     int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    `config_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置文件id',
    `creator`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'BG科室报告目录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_reservation
-- ----------------------------
DROP TABLE IF EXISTS `md_reservation`;
CREATE TABLE `md_reservation`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `id_reservation`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预约员ID',
    `id_check`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批人ID',
    `patientname`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `phone`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `idcardno`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `type`            tinyint(1) NULL DEFAULT NULL COMMENT '0:预留申请 1：超额申请',
    `id_patientclass` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者类型ID',
    `approved_status` tinyint(1) NULL DEFAULT NULL COMMENT '0：提交申请 1：审核通过 2：审核不通过',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '0 未删除 1 删除',
    `medicaldate`     datetime(0) NULL DEFAULT NULL COMMENT '体检时间',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `successcode`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '信息保存成功生成的体检号',
    `id_sex`          varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别ID',
    `id_examtype`     varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型，详见：enums.com.center.medical.bean.ExamType',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预约管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_reservation_return_visit
-- ----------------------------
DROP TABLE IF EXISTS `md_reservation_return_visit`;
CREATE TABLE `md_reservation_return_visit`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patient_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者ID',
    `visit_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访人ID',
    `visit_time` datetime(0) NULL DEFAULT NULL COMMENT '回访时间',
    `visit_note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '回访备注',
    `sflj`       tinyint(1) NULL DEFAULT NULL COMMENT '是否来检(1来检0不来)',
    `ljsj`       datetime(0) NULL DEFAULT NULL COMMENT '来检时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预约回访记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_review
-- ----------------------------
DROP TABLE IF EXISTS `md_review`;
CREATE TABLE `md_review`
(
    `id`                        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `date_from`                 datetime(0) NULL DEFAULT NULL COMMENT '复查时间起',
    `date_to`                   datetime(0) NULL DEFAULT NULL COMMENT '复查时间止',
    `callback_station`          tinyint(1) NULL DEFAULT NULL COMMENT '复查状态',
    `user_id`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员ID',
    `notice_of_proceeding_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '注意事项',
    `createdate`                datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`                 tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `id_org`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `review_pdf`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查PDF地址',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'ZJ复查表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_review_notification_format
-- ----------------------------
DROP TABLE IF EXISTS `md_review_notification_format`;
CREATE TABLE `md_review_notification_format`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `format_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知格式名称',
    `format_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拼音码',
    `format_file` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存放位置',
    `memo`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`   tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC复查通知格式' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_review_project
-- ----------------------------
DROP TABLE IF EXISTS `md_review_project`;
CREATE TABLE `md_review_project`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `items_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    `items_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目名称',
    `review_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查表ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'ZJ复查项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_riskclient
-- ----------------------------
DROP TABLE IF EXISTS `md_riskclient`;
CREATE TABLE `md_riskclient`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `tjid`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `gwrymc`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高危人员名称',
    `nl`              varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄',
    `xb`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `lxdh`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
    `gwxm`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '高危项目',
    `tjlx`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检类型',
    `tirq`            datetime(0) NULL DEFAULT NULL COMMENT '体检日期',
    `tjzt`            tinyint(1) NULL DEFAULT NULL COMMENT '状态：0：未处理1：处理',
    `bz`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `cid`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `id_org`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `id_opendoctor`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理（开单医生ID）',
    `report_man`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提报者',
    `report_division` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提报科室',
    `deal_man`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人',
    `reportstatus`    tinyint(1) NULL DEFAULT NULL COMMENT '提报状态',
    `report_time`     datetime(0) NULL DEFAULT NULL COMMENT '提报时间',
    `deal_time`       datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
    `tbbz`            tinyint(1) NULL DEFAULT NULL COMMENT '同步标志  1： 已同步  ',
    `clbz`            tinyint(1) NULL DEFAULT NULL COMMENT '处理标志 科室-危急值提报-处理  1已处理  null未处理（弃用）',
    `ywclr`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务处理人用户名',
    `ywclsj`          datetime(0) NULL DEFAULT NULL COMMENT '业务处理时间',
    `ywfffs`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务处理选择的报告发放方式id',
    `ywclzt`          tinyint(1) NULL DEFAULT NULL COMMENT '业务处理状态',
    `ywbz`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '业务处理备注',
    `hfclr`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访处理人用户名',
    `hfclsj`          datetime(0) NULL DEFAULT NULL COMMENT '回访处理时间',
    `hfclzt`          tinyint(1) NULL DEFAULT NULL COMMENT '回访处理状态，详见：enums.com.center.medical.bean.Jktjzt',
    `hffffs`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访处理方式',
    `hfbz`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '回访备注',
    `hfclfs`          tinyint(1) NULL DEFAULT NULL COMMENT '回访发放方式',
    `zjclr`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专家处理人用户名',
    `zjclsj`          datetime(0) NULL DEFAULT NULL COMMENT '专家处理时间',
    `zjclzt`          tinyint(1) NULL DEFAULT NULL COMMENT '专家处理状态 0未处理  1 已处理',
    `zjbz`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '专家处理备注',
    `zjclfs`          tinyint(1) NULL DEFAULT NULL COMMENT '专家处理方式',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '高危人员管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_riskclientcon
-- ----------------------------
DROP TABLE IF EXISTS `md_riskclientcon`;
CREATE TABLE `md_riskclientcon`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `riskid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高危人员表ID',
    `gwxm`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '高危项目',
    `wjzxj`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '危机值小结',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `division_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室id',
    `wjzjb`       tinyint(1) NULL DEFAULT NULL COMMENT '危急值级别',
    `doctor_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医生名称',
    `check_time`  datetime(0) NULL DEFAULT NULL COMMENT '检查时间',
    `patientcode` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `sfxm`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '收费项目名称',
    `tbbz`        tinyint(1) NULL DEFAULT NULL COMMENT '同步标志：1已同步 ',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '高危人员关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for kd_saleer
-- ----------------------------
DROP TABLE IF EXISTS `kd_saleer`;
CREATE TABLE `kd_saleer`
(
    `account_name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户名称',
    `account_no`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账户号',
    `use_status_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
    `ct_date`       datetime(0) NULL DEFAULT NULL COMMENT 'ct_date',
    `lt_date`       datetime(0) NULL DEFAULT NULL COMMENT 'lt_date',
    `centerorgname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'centerorgname',
    `md5`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'md5'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '金蝶销售员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_sales_target_statistic
-- ----------------------------
DROP TABLE IF EXISTS `md_sales_target_statistic`;
CREATE TABLE `md_sales_target_statistic`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `userid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
    `username`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `complete`   decimal(8, 2) NULL DEFAULT NULL COMMENT '实际完成额',
    `complete1`  decimal(8, 2) NULL DEFAULT NULL COMMENT '1月实际完成额',
    `complete2`  decimal(8, 2) NULL DEFAULT NULL COMMENT '2月实际完成额',
    `complete3`  decimal(8, 2) NULL DEFAULT NULL COMMENT '3月实际完成额',
    `complete4`  decimal(8, 2) NULL DEFAULT NULL COMMENT '4月实际完成额',
    `complete5`  decimal(8, 2) NULL DEFAULT NULL COMMENT '5月实际完成额',
    `complete6`  decimal(8, 2) NULL DEFAULT NULL COMMENT '6月实际完成额',
    `complete7`  decimal(8, 2) NULL DEFAULT NULL COMMENT '7月实际完成额',
    `complete8`  decimal(8, 2) NULL DEFAULT NULL COMMENT '8月实际完成额',
    `complete9`  decimal(8, 2) NULL DEFAULT NULL COMMENT '9月实际完成额',
    `complete10` decimal(8, 2) NULL DEFAULT NULL COMMENT '10实际完成额',
    `complete11` decimal(8, 2) NULL DEFAULT NULL COMMENT '11月实际完成额',
    `complete12` decimal(8, 2) NULL DEFAULT NULL COMMENT '12实际完成额',
    `bz`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `cid`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中心id',
    `year`       varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年',
    `type`       tinyint(1) NULL DEFAULT NULL COMMENT '类型',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `IDX_SALES_TARGET_STA_FZX`(`cid`) USING BTREE,
    INDEX        `IDX_SALES_TARGET_STA_YEAR`(`year`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '销售目标自动统计' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_sample_connect
-- ----------------------------
DROP TABLE IF EXISTS `md_sample_connect`;
CREATE TABLE `md_sample_connect`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `delivery_time`   datetime(0) NULL DEFAULT NULL COMMENT '交接时间',
    `delivery_name`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交接人',
    `is_qualified`    tinyint(1) NULL DEFAULT NULL COMMENT '是否合格',
    `recipient`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '承接人',
    `department_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '承接科室ID',
    `un_qualified_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '不合格原因',
    `create_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `is_visit`        tinyint(1) NULL DEFAULT NULL COMMENT '是否已回访',
    `patientcode`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `id_examfeeitem`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联收费项目表id',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS样本交接' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_sample_delivery
-- ----------------------------
DROP TABLE IF EXISTS `md_sample_delivery`;
CREATE TABLE `md_sample_delivery`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `sample_no`       int(0) NULL DEFAULT NULL COMMENT '样本编号',
    `delivery_time`   datetime(0) NULL DEFAULT NULL COMMENT '交接时间',
    `delivery_name`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交接人',
    `is_qualified`    tinyint(1) NULL DEFAULT NULL COMMENT '是否合格',
    `recipient`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '承接人',
    `department_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '承接科室ID',
    `un_qualified_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '不合格原因',
    `create_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `is_visit`        tinyint(1) NULL DEFAULT NULL COMMENT '是否已回访',
    `patientcode`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `id_examfeeitem`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联收费项目表id',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS样本录入' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_sample_person
-- ----------------------------
DROP TABLE IF EXISTS `md_sample_person`;
CREATE TABLE `md_sample_person`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `ball_check_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团检报告ID',
    `patientcode`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `group_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体ID',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `patient_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者表id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'TJ团检报告人员样本表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_satisfaction
-- ----------------------------
DROP TABLE IF EXISTS `md_satisfaction`;
CREATE TABLE `md_satisfaction`
(
    `id`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `division_receptionist` tinyint(1) NULL DEFAULT NULL COMMENT '0科室/1整个分中心/2前台评价/3咨询',
    `division_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `doctor_id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开单医生ID',
    `appraise_result`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '，详见：enums.com.center.medical.bean.AppraiseResult',
    `visit_result`          tinyint(1) NULL DEFAULT NULL COMMENT '回访结果',
    `appraise_time`         datetime(0) NULL DEFAULT NULL COMMENT '评价时间',
    `personcode`            varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`            datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`            datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_very_satisfy`       tinyint(1) NULL DEFAULT NULL COMMENT '是否已非常满意',
    `note`                  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评价备注',
    `visit_time`            datetime(0) NULL DEFAULT NULL COMMENT '回访时间',
    `visit_person`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访人',
    `visit_note`            varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访备注',
    `second_result`         tinyint(1) NULL DEFAULT NULL COMMENT '回访结果2',
    `second_note`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访备注2',
    `second_person`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访人2',
    `second_time`           datetime(0) NULL DEFAULT NULL COMMENT '回访时间2',
    `is_delete`             tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `patientname`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者姓名',
    `visit_text`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '跟进内容',
    `ks_doctor_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室医生ID',
    `satisfaction_level`    tinyint(1) NULL DEFAULT NULL COMMENT '星级',
    `source_type`           tinyint(0) NULL DEFAULT NULL COMMENT '评论来源  1微信小程序',
    `user_id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信小程序id',
    `consult_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'consult_id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KF满意度' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_savefilepath
-- ----------------------------
DROP TABLE IF EXISTS `md_savefilepath`;
CREATE TABLE `md_savefilepath`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `ggid`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公共ID',
    `filepath`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件路径+文件名',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存放文件路径表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_section_and_remind
-- ----------------------------
DROP TABLE IF EXISTS `md_section_and_remind`;
CREATE TABLE `md_section_and_remind`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remind_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室提醒主表ID',
    `dep_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '被提醒科室ID',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '科室提醒和科室关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_section_remind
-- ----------------------------
DROP TABLE IF EXISTS `md_section_remind`;
CREATE TABLE `md_section_remind`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `remind_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '提醒内容',
    `dep_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提醒科室ID',
    `dep_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提醒科室名称',
    `user_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提醒人ID',
    `user_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提醒人姓名',
    `remind_time`    datetime(0) NULL DEFAULT NULL COMMENT '提醒时间',
    `dep_ids`        text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '被提醒的科室ids',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '科室提醒主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_section_result_main
-- ----------------------------
DROP TABLE IF EXISTS `md_section_result_main`;
CREATE TABLE `md_section_result_main`
(
    `id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `dep_id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `rummager_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查人ID',
    `write_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '录入人ID',
    `rummager_time`       datetime(0) NULL DEFAULT NULL COMMENT '检查时间',
    `write_time`          datetime(0) NULL DEFAULT NULL COMMENT '录入时间',
    `createdate`          datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`          datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_audit`            tinyint(1) NULL DEFAULT NULL COMMENT '分检是否已审核',
    `audit_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人ID',
    `audit_time`          datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
    `is_danager`          tinyint(1) NULL DEFAULT NULL COMMENT '标注体检者在本科室体检是否有危急值',
    `danager_level`       tinyint(1) NULL DEFAULT NULL COMMENT '危急值级别',
    `conclusions`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康小结',
    `is_delete`           tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
    `audit_name`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
    `rummager_name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查人姓名',
    `patientcode`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `is_finish`           tinyint(1) NULL DEFAULT NULL COMMENT '分检完成',
    `zy_conclusions`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业小结',
    `associative_table`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关联表名',
    `short_code`          int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    `file_type`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片类型',
    `file_path`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
    `pacs_conclusions`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'pacs_conclusions',
    `zy_pacs_conclusions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'zy_pacs_conclusions',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS科室检查结果主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_section_result_plan
-- ----------------------------
DROP TABLE IF EXISTS `md_section_result_plan`;
CREATE TABLE `md_section_result_plan`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `dep_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `creater`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人username',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `status`      tinyint(1) NULL DEFAULT NULL COMMENT '状态：0未审核  1已审核(只用于记录是否已被线程审核，实际是否审核判断sectionResultMain)',
    `error_msg`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误信息',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '科室批量录入结果' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_section_result_two
-- ----------------------------
DROP TABLE IF EXISTS `md_section_result_two`;
CREATE TABLE `md_section_result_two`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `main_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室检查结果主表ID',
    `verdict_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '在搜索结论词与小结时必须先以本ID查询到所有的相关结论词，然后再按照检查项目体证词关联表ID找到所对应的体证词（小结）与结论词ID',
    `nodule`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目体证词关联表ID',
    `posistive`        tinyint(1) NULL DEFAULT NULL COMMENT '是否阳性结果',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `intensive_level`  tinyint(1) NULL DEFAULT NULL COMMENT '重症级别',
    `is_unchecked`     tinyint(1) NULL DEFAULT NULL COMMENT '是否弃检',
    `disease_health`   tinyint(1) NULL DEFAULT NULL COMMENT '职业/健康',
    `basconclusion_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词ID',
    `division_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `is_delete`        tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
    `charges_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    `ms`               longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检查描述',
    `is_danger`        tinyint(1) NULL DEFAULT NULL COMMENT '是否危急值',
    `input_result`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自由输入结果',
    `tjlx`             tinyint(1) NULL DEFAULT NULL COMMENT '体检类型 1.职业 0.非职业',
    `short_code`       int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX              `I_SRT_PATIENTCODE`(`patientcode`) USING BTREE,
    INDEX              `SECRESTWO_IDMAIN`(`main_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS科室检查结果表-结论词、小结' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_section_total
-- ----------------------------
DROP TABLE IF EXISTS `md_section_total`;
CREATE TABLE `md_section_total`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `doctor_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总检医生ID',
    `total_time`         datetime(0) NULL DEFAULT NULL COMMENT '总检时间',
    `write_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '录入人ID',
    `write_time`         datetime(0) NULL DEFAULT NULL COMMENT '录入时间',
    `summarize`          longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '综述',
    `disease_health`     tinyint(1) NULL DEFAULT NULL COMMENT '职业/健康',
    `is_delete`          tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `offer`              longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '健康总检：健康建议   职业总检：职业结论及建议 OFFER',
    `verdict`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '结论',
    `posistive`          longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '阳性结果',
    `jkoffer`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业总检：健康建议',
    `scbs`               tinyint(1) NULL DEFAULT NULL COMMENT '上传标志  0未上传1已上传 无null值',
    `report_conclusions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业报告结论词',
    `summary_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'zySummary（职业总检）',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                `ST_PATIENTCODE_INDEX`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'ZJ总检主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_sell_outside
-- ----------------------------
DROP TABLE IF EXISTS `crm_sell_outside`;
CREATE TABLE `crm_sell_outside`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `fzx`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    `kh`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户',
    `allprice`   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总价',
    `zk`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '折扣',
    `sj`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实收',
    `items`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '所有收费项目',
    `sellman`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '外出沟通' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_sell_remind
-- ----------------------------
DROP TABLE IF EXISTS `crm_sell_remind`;
CREATE TABLE `crm_sell_remind`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `khdwid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户id',
    `khdwmc`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
    `remind_time` datetime(0) NULL DEFAULT NULL COMMENT '提醒时间',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '提醒内容',
    `create_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人用户名',
    `status`      tinyint(1) NULL DEFAULT NULL COMMENT '状态 0未结束 1 已结束',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '销售提醒' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_sellcustomer
-- ----------------------------
DROP TABLE IF EXISTS `crm_sellcustomer`;
CREATE TABLE `crm_sellcustomer`
(
    `id`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `khdwmc`                 varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称',
    `khdwsrm`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位输入码',
    `khdwlxr`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位联系人',
    `khdh`                   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户电话',
    `frdwmc`                 varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法人单位名称',
    `fddbr`                  varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法定代表人',
    `yzbm`                   varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮政编码',
    `qygm`                   varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业规模',
    `qyjjlx`                 varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业经济类型',
    `zywsfzr`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业卫生负责人',
    `khdwzcdz`               varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位注册地址',
    `zywsgljg`               varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业卫生管理机构',
    `dwjgdm`                 varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位机构代码',
    `xsjl`                   varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理',
    `zclx`                   varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注册类型',
    `sshy`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属行业',
    `lsgx`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '隶属关系',
    `sjzgdw`                 varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级主管单位',
    `sjcyrs`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实际从业人数',
    `ldrs`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流动人数',
    `scgrs`                  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生产工人数',
    `zybwhysrs`              varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病危害因素人数',
    `zybwhzycss`             varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病危害作业场所数',
    `zybwhyslb`              varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业病危害因素类别',
    `zybwhys`                text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职业病危害因素',
    `gylc`                   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '工艺流程',
    `zyyfl`                  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主要原辅料',
    `tjttlx`                 tinyint(1) NULL DEFAULT NULL COMMENT '体检团体类型',
    `zycp`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主要产品',
    `khsctjdwdz`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户上次体检单位地址',
    `bz`                     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `sjrq`                   datetime(0) NULL DEFAULT NULL COMMENT '升级日期',
    `khzt`                   varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户状态：0潜在 1正式 2释放',
    `is_delete`              tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `clientid`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户公共池领取ID',
    `ldfpid`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领导分配ID',
    `xsjlid`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `fzxid`                  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `createdate`             datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`             datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `lost`                   tinyint(1) NULL DEFAULT NULL COMMENT '流失标志（1、已流失）',
    `int_id`                 int(0) NULL DEFAULT NULL COMMENT '数字形式团体ID，方便财务导出后处理数据使用',
    `brief_text`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '简述',
    `social_credit_code`     varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '社会信用代码',
    `province`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省级代码',
    `city`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市级代码',
    `district`               varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区级代码',
    `street`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '街道代码',
    `indus_type_code`        varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业类别小类代码',
    `economy_code`           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经济类型编码',
    `crpt_size_code`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业规模编码',
    `work_force`             int(0) NULL DEFAULT NULL COMMENT '职工人数',
    `hold_card_man`          int(0) NULL DEFAULT NULL COMMENT '接触危害因素人数',
    `workman_num`            int(0) NULL DEFAULT NULL COMMENT '生产工人数',
    `workmistress_num`       int(0) NULL DEFAULT NULL COMMENT '接触职业病危害因素女工人数',
    `work_area`              varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经营面积',
    `register_fund`          varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注册资金',
    `safety_principal`       varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业卫生安全负责人',
    `build_date`             datetime(0) NULL DEFAULT NULL COMMENT '建厂日期',
    `linkman1`               varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检测联系人',
    `position1`              varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检测联系人职务',
    `linkphone1`             varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检测联系人电话',
    `linkman2`               varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检联系人',
    `position2`              varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检联系人职务',
    `linkphone2`             varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检联系人电话',
    `safeposition`           varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业卫生安全联系人职务',
    `safephone`              varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业卫生安全联系人电话',
    `subje_conn`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '隶属关系',
    `enrol_address`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作业场所地址',
    `enrol_postalcode`       varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作业场所邮政编码',
    `occ_mana_office`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业卫生管理机构',
    `jinan_status`           tinyint(1) NULL DEFAULT NULL COMMENT '济南市职业卫生综合管理平台上传状态 -1未录入必填字段不能上传 0待上传 1已上传  2上传失败',
    `jinan_msg`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '济南市职业卫生综合管理平台上传失败信息',
    `indus_type_code1`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业类别门类代码',
    `indus_type_code2`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业类别大类代码',
    `indus_type_code3`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业类别种类代码',
    `phone`                  varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法人联系电话',
    `rau_social_credit_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位统一社会信用代码',
    `rau_economy_code`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位经济类型编码',
    `rau_indus_type_code1`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位行业类别门类代码',
    `rau_indus_type_code2`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位行业类别大类代码',
    `rau_indus_type_code3`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位行业类别种类代码',
    `rau_indus_type_code`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位行业类别小类代码',
    `rau_qygm`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位企业规模编码',
    `rau_province`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位省级代码',
    `rau_city`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位市级代码',
    `rau_district`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位区级代码',
    `rau_street`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位街道代码',
    `unitarea`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用人单位所属区名称（固定10个）',
    `rau_khdwmc`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工客户单位名称',
    `rau_unitarea`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用工单位所属区名称（固定10个）',
    `license_name`           varchar(75) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业执照上的企业名称用工客户单位名称',
    `jindie_id`              varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金蝶ID',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                    `SELLCUSTOMER_KHDH`(`khdh`) USING BTREE,
    INDEX                    `SELLCUSTOMER_KHDWLXR`(`khdwlxr`) USING BTREE,
    INDEX                    `SELLCUSTOMER_KHDWSRM`(`khdwsrm`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '我的客户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_sellpact
-- ----------------------------
DROP TABLE IF EXISTS `crm_sellpact`;
CREATE TABLE `crm_sellpact`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `htmc`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '合同名称',
    `htbh`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '合同编号',
    `xsjl`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理',
    `htqdrq`     datetime(0) NULL DEFAULT NULL COMMENT '合同签订日期',
    `tjksrq`     datetime(0) NULL DEFAULT NULL COMMENT '体检开始日期',
    `tjjsrq`     datetime(0) NULL DEFAULT NULL COMMENT '体检结束日期',
    `khdwmcid`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称id',
    `khdwmc`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称',
    `lxdh`       varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
    `tjrs`       int(0) NULL DEFAULT NULL COMMENT '体检人数',
    `ys`         decimal(12, 2) NULL DEFAULT NULL COMMENT '预算',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '销售合同维护表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_sellprintteams
-- ----------------------------
DROP TABLE IF EXISTS `crm_sellprintteams`;
CREATE TABLE `crm_sellprintteams`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `dyxmflmc`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打印项目分类名称',
    `srm`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `bz`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '销售打印项目分类设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_selltarget
-- ----------------------------
DROP TABLE IF EXISTS `crm_selltarget`;
CREATE TABLE `crm_selltarget`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `year`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年份',
    `dyjdmb`     decimal(12, 2) NULL DEFAULT NULL COMMENT '第一季度目标',
    `dejdmb`     decimal(12, 2) NULL DEFAULT NULL COMMENT '第二季度目标',
    `dsjdmb`     decimal(12, 2) NULL DEFAULT NULL COMMENT '第三季度目标',
    `dijdmb`     decimal(12, 2) NULL DEFAULT NULL COMMENT '第四季度目标',
    `qnmb`       decimal(12, 2) NULL DEFAULT NULL COMMENT '全年目标',
    `qnhke`      decimal(12, 2) NULL DEFAULT NULL COMMENT '全年回款额',
    `hkbl`       decimal(12, 2) NULL DEFAULT NULL COMMENT '回款比率',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `bz`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'XS销售目标' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_sh_report
-- ----------------------------
DROP TABLE IF EXISTS `md_sh_report`;
CREATE TABLE `md_sh_report`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `idcardno`    varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `report_type` tinyint(1) NULL DEFAULT NULL COMMENT '0单科室报告 1既往报告',
    `dep_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID（单科室 报告）',
    `is_charged`  tinyint(1) NULL DEFAULT NULL COMMENT '0未收费 1已收费',
    `is_printed`  tinyint(1) NULL DEFAULT NULL COMMENT '0未打印 1已打印',
    `error_msg`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '打印失败时的错误信息',
    `print_time`  datetime(0) NULL DEFAULT NULL COMMENT '打印时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `IDX_SH_REPORT_DEP_ID`(`dep_id`) USING BTREE,
    INDEX         `IDX_SH_REPORT_IDCARDNO`(`idcardno`) USING BTREE,
    INDEX         `IDX_SH_REPORT_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自助机-报告打印 操作记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_short_message_type
-- ----------------------------
DROP TABLE IF EXISTS `md_short_message_type`;
CREATE TABLE `md_short_message_type`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `type_name`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短消息类型名称',
    `inputcode`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `note`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `params`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可使用的参数，逗号分隔',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC短信信息分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_shortmessage
-- ----------------------------
DROP TABLE IF EXISTS `md_shortmessage`;
CREATE TABLE `md_shortmessage`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `message_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短消息类型ID， 详见数据表：md_short_message_type',
    `message_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短消息名称',
    `message_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '短消息正文',
    `inputcode`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `note`         varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `is_delete`    tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `template_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网上模板ID',
    `params`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
    `appid`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'APPID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC短信信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_sms_record
-- ----------------------------
DROP TABLE IF EXISTS `md_sms_record`;
CREATE TABLE `md_sms_record`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `id_template`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统模板ID',
    `notify_type`    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '通知类型， 详见数据表：md_short_message_type',
    `notify_result`  tinyint(1) NULL DEFAULT NULL COMMENT '通知结果状态， 详见：enums.com.center.medical.bean.SmsNotifyResultType',
    `notify_time`    datetime(0) NULL DEFAULT NULL COMMENT '通知时间',
    `creater`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
    `is_immediately` tinyint(1) NULL DEFAULT NULL COMMENT '是否立即发送：0.否 1.是',
    `patientcode`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `notify_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '通知内容',
    `archive_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信发送记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_sortexam_limit
-- ----------------------------
DROP TABLE IF EXISTS `md_sortexam_limit`;
CREATE TABLE `md_sortexam_limit`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `cid`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    `sort_date`  datetime(0) NULL DEFAULT NULL COMMENT '排检日期',
    `vip`        int(0) NULL DEFAULT NULL COMMENT 'VIP人数上限',
    `vvip`       int(0) NULL DEFAULT NULL COMMENT 'VVIP人数上限',
    `am`         int(0) NULL DEFAULT NULL COMMENT '普通上午人数上限',
    `pm`         int(0) NULL DEFAULT NULL COMMENT '普通下午人数上限',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX        `IDX_EXAMSORT_LIMIT_CID`(`cid`) USING BTREE,
    INDEX        `IDX_EXAMSORT_LIMIT_SORTDATE`(`sort_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '每日排检上限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_sshy
-- ----------------------------
DROP TABLE IF EXISTS `md_sshy`;
CREATE TABLE `md_sshy`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `hymc`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业名称',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '创建团体客户要选择的所属行业在这里维护' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_stencil_maintain
-- ----------------------------
DROP TABLE IF EXISTS `md_stencil_maintain`;
CREATE TABLE `md_stencil_maintain`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `model_name`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板名称',
    `model_inputcode` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板输入码',
    `model_type`      tinyint(1) NULL DEFAULT NULL COMMENT '模板类型，详见：enums.com.center.medical.bean.ModelType',
    `center_name`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心',
    `center_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `is_default`      tinyint(1) NULL DEFAULT NULL COMMENT '是否为默认：0是 1否；',
    `dep_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室名称',
    `dep_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `model_urls`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模板存放地址（可存放多个，用“；”分割）',
    `suitable_type`   tinyint(1) NULL DEFAULT NULL COMMENT '适用于：0健康 1职业',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.已删除 1.未删除',
    `is_head`         tinyint(1) NULL DEFAULT NULL COMMENT '是否为头模板：0或null.否 1.是',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_tb_patient
-- ----------------------------
DROP TABLE IF EXISTS `md_tb_patient`;
CREATE TABLE `md_tb_patient`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `patientcode`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `idcardno`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `patientname`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `input_code`      varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `id_sex`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别ID',
    `birthdate`       datetime(0) NULL DEFAULT NULL COMMENT '生日',
    `age`             int(0) NULL DEFAULT NULL COMMENT '年龄',
    `id_marriage`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻ID',
    `marriage`        varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻',
    `phone`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `id_patientclass` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者类型ID PatienType表',
    `dateregister`    datetime(0) NULL DEFAULT NULL COMMENT '登记时间',
    `short_code`      int(0) NULL DEFAULT NULL COMMENT '短体检号',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `pictures`        longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'DICOM图片路径（多个路径用 分号+空格  拼接）',
    `up_fzx_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传分中心ID',
    `down_fzx_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目标分中心ID',
    `up_name`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传人用户名',
    `down_time`       datetime(0) NULL DEFAULT NULL COMMENT '目标分中心下载时间',
    `back_time`       datetime(0) NULL DEFAULT NULL COMMENT '目标分中心上传时间',
    `finish_time`     datetime(0) NULL DEFAULT NULL COMMENT '下载时间',
    `status`          tinyint(1) NULL DEFAULT NULL COMMENT '状态  1已上传 2目标分中心已下载 3目标分中心已上传  4已下载',
    `down_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目标分中心下载人用户名',
    `back_name`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '目标分中心上传人用户名',
    `finish_name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下载人用户名',
    `id_examtype`     tinyint(1) NULL DEFAULT NULL COMMENT '体检类型ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '同步主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for crm_teamremind
-- ----------------------------
DROP TABLE IF EXISTS `crm_teamremind`;
CREATE TABLE `crm_teamremind`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `xshtwhid`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售合同维护id',
    `khdwmc`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位名称',
    `khlxdh`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户联系电话',
    `clr`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人',
    `clsj`       datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
    `sctjksrq`   datetime(0) NULL DEFAULT NULL COMMENT '上次体检开始日期',
    `xcgtrq`     datetime(0) NULL DEFAULT NULL COMMENT '下次沟通时间',
    `clzt`       tinyint(1) NULL DEFAULT NULL COMMENT '处理状态(0：未处理  1：已处理)',
    `xsjlid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售经理ID',
    `fzxid`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `khdwid`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位ID',
    `khdwlxr`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户单位联系人',
    `is_examed`  tinyint(1) NULL DEFAULT NULL COMMENT '是否已检：0或null.否 1.是',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户预检跟踪表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_temp_feeitem
-- ----------------------------
DROP TABLE IF EXISTS `md_temp_feeitem`;
CREATE TABLE `md_temp_feeitem`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `item_id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目基础表id',
    `doctor_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '加项医生',
    `price`           decimal(8, 2) NULL DEFAULT NULL COMMENT '优惠价',
    `remarks`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `patientcode`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `feeitem_id`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检者收费项目id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX             `TEMPFEEITEM_CREATEDATE`(`createdate`) USING BTREE,
    INDEX             `TEMPFEEITEM_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '科室临时加项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_temporaryqueue
-- ----------------------------
DROP TABLE IF EXISTS `md_temporaryqueue`;
CREATE TABLE `md_temporaryqueue`
(
    `id`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `patientcode`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `transmitting` tinyint(1) NULL DEFAULT NULL COMMENT '是否发送',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'temporaryqueue' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_temporaryqueuetest
-- ----------------------------
DROP TABLE IF EXISTS `md_temporaryqueuetest`;
CREATE TABLE `md_temporaryqueuetest`
(
    `id`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `patientcode`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `transmitting` decimal(1, 0) NULL DEFAULT NULL COMMENT '是否发送',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'temporaryqueuetest' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_tjbb_bmi
-- ----------------------------
DROP TABLE IF EXISTS `md_tjbb_bmi`;
CREATE TABLE `md_tjbb_bmi`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `xh`          int(0) NULL DEFAULT NULL COMMENT '序号',
    `bmi`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'BMI描述',
    `ckfw`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参考范围描述',
    `value_min`   decimal(10, 2) NULL DEFAULT NULL COMMENT '低值',
    `value_max`   decimal(10, 2) NULL DEFAULT NULL COMMENT '高值',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS体重指数体检报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_tjbb_gmd
-- ----------------------------
DROP TABLE IF EXISTS `md_tjbb_gmd`;
CREATE TABLE `md_tjbb_gmd`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `xh`          int(0) NULL DEFAULT NULL COMMENT '序号',
    `gmd`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '骨密度描述',
    `ckfw`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参考范围描述',
    `value_min`   decimal(10, 2) NULL DEFAULT NULL COMMENT '低值',
    `value_max`   decimal(10, 2) NULL DEFAULT NULL COMMENT '高值',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS骨密度体检报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_tjbb_xtjc
-- ----------------------------
DROP TABLE IF EXISTS `md_tjbb_xtjc`;
CREATE TABLE `md_tjbb_xtjc`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `xh`          int(0) NULL DEFAULT NULL COMMENT '序号',
    `xtjc`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '血糖检测',
    `ckfw`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参考范围描述',
    `value_min`   decimal(10, 2) NULL DEFAULT NULL COMMENT '低值',
    `value_max`   decimal(10, 2) NULL DEFAULT NULL COMMENT '高值',
    `create_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS血糖检测体检报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_tjbb_xyjc
-- ----------------------------
DROP TABLE IF EXISTS `md_tjbb_xyjc`;
CREATE TABLE `md_tjbb_xyjc`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `xh`            int(0) NULL DEFAULT NULL COMMENT '序号',
    `xyjc`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '血压检测',
    `ckfw_ssy`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收缩压参考范围',
    `ckfw_szy`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '舒张压参考范围',
    `value_min_ssy` decimal(10, 2) NULL DEFAULT NULL COMMENT '收缩压低值',
    `value_max_ssy` decimal(10, 2) NULL DEFAULT NULL COMMENT '收缩压高值',
    `value_min_szy` decimal(10, 2) NULL DEFAULT NULL COMMENT '舒张压低值',
    `value_max_szy` decimal(10, 2) NULL DEFAULT NULL COMMENT '舒张压高值',
    `create_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `patientcode`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS血压检测' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_tjbb_xzjc
-- ----------------------------
DROP TABLE IF EXISTS `md_tjbb_xzjc`;
CREATE TABLE `md_tjbb_xzjc`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `xh`          int(0) NULL DEFAULT NULL COMMENT '序号',
    `fl`          char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类',
    `xzjcmc`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '血脂检测项目',
    `ckfw`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参考范围',
    `value_min`   decimal(10, 2) NULL DEFAULT NULL COMMENT '低值',
    `value_max`   decimal(10, 2) NULL DEFAULT NULL COMMENT '高值',
    `create_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS血脂检测体检报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_tjdw_branch
-- ----------------------------
DROP TABLE IF EXISTS `md_tjdw_branch`;
CREATE TABLE `md_tjdw_branch`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `serial_code` int(0) NULL DEFAULT NULL COMMENT '序号',
    `branch_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门编码',
    `branch_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
    `corp_code`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司编码',
    `input_code`  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'XS体检单位：部门信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_tjreg
-- ----------------------------
DROP TABLE IF EXISTS `md_tjreg`;
CREATE TABLE `md_tjreg`
(
    `id`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `tjbh`             char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检编号',
    `tjrq`             datetime(0) NULL DEFAULT NULL COMMENT '体检日期',
    `sg`               char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身高',
    `tz`               char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体重',
    `xl`               char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '心率',
    `xy`               char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '血压',
    `xj`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '小结',
    `tjzt`             char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检状态',
    `djid`             int(0) NULL DEFAULT NULL COMMENT '登记ID',
    `bmi`              decimal(10, 2) NULL DEFAULT NULL COMMENT '体重指数',
    `ssy`              decimal(5, 0) NULL DEFAULT NULL COMMENT '收缩压',
    `szy`              decimal(5, 0) NULL DEFAULT NULL COMMENT '舒张压',
    `yw`               decimal(10, 1) NULL DEFAULT NULL COMMENT '腰围',
    `djls`             varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记流水',
    `mb`               decimal(5, 0) NULL DEFAULT NULL COMMENT '脉搏',
    `create_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `common_state`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '一般状况',
    `respiratory_rate` decimal(6, 2) NULL DEFAULT NULL COMMENT '呼吸频率',
    `createdate`       datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`       datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `dep_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `short_code`       int(0) NULL DEFAULT NULL COMMENT '短号体检号',
    `xyms`             varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '血压文字描述',
    `bmims`            varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体重文字描述',
    `scbz`             tinyint(1) NULL DEFAULT NULL COMMENT '上传标志  0未上传,1已上传',
    `temperature`      decimal(5, 2) NULL DEFAULT NULL COMMENT '体温测量',
    `waist`            decimal(4, 1) NULL DEFAULT NULL COMMENT 'waist',
    `bust`             decimal(4, 0) NULL DEFAULT NULL COMMENT 'bust',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX              `IDX_TJREG_PATIENTCODE`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS一般检查' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_total_doctor
-- ----------------------------
DROP TABLE IF EXISTS `md_total_doctor`;
CREATE TABLE `md_total_doctor`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `total_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总检ID',
    `user_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ID',
    `type`       tinyint(1) NULL DEFAULT NULL COMMENT '类型：0当前登录医生 1额外选择的医生',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '总检-医生 关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_total_verdict
-- ----------------------------
DROP TABLE IF EXISTS `md_total_verdict`;
CREATE TABLE `md_total_verdict`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `basconclusion_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论词ID',
    `total_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总检主表ID',
    `division_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`          tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `disease_health`     tinyint(1) NULL DEFAULT NULL COMMENT '检查类型：0:健康 1:职业',
    `flag`               tinyint(1) NULL DEFAULT NULL COMMENT '标志：0不出现,1出现',
    `total_advice`       longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总检建议',
    `basconclusion_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '总检结论词名称',
    `merge_id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '合并结伦词ID',
    `merge_name`         varchar(70) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总检结伦词合并名称',
    `verdict_sort`       int(0) NULL DEFAULT NULL COMMENT '顺序',
    `suggestiongroup`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '团检建议',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX                `I_TV_TOTALID`(`total_id`) USING BTREE,
    INDEX                `TV_CON_INDEX`(`basconclusion_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'ZJ总检结论词表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_trade_record
-- ----------------------------
DROP TABLE IF EXISTS `md_trade_record`;
CREATE TABLE `md_trade_record`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `patientcode`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `out_trade_no`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方交易流水号',
    `money`         decimal(8, 2) NULL DEFAULT NULL COMMENT '交易额',
    `note`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `trade_type`    tinyint(1) NULL DEFAULT NULL COMMENT '交易类型',
    `temp_item_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'temp_item_ids',
    `status`        tinyint(1) NULL DEFAULT NULL COMMENT '状态',
    `register_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记员ID',
    `order_type`    tinyint(1) NULL DEFAULT NULL COMMENT '订单类型 0 等级 1 加项',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX           `TRADE_CREATEDATE_INDEX`(`createdate`) USING BTREE,
    INDEX           `TRADE_PATIENTCODE_INDEX`(`patientcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '交易记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_upperower
-- ----------------------------
DROP TABLE IF EXISTS `md_upperower`;
CREATE TABLE `md_upperower`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `rymc`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人员名称',
    `zw`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位',
    `xb`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `nl`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄',
    `dh`         varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `sfysj`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否有上级',
    `sj`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '上下级关系管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_user_harm_class
-- ----------------------------
DROP TABLE IF EXISTS `md_user_harm_class`;
CREATE TABLE `md_user_harm_class`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `user_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ID',
    `class_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '总检医生-危害因素分类关联表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_user_saleer
-- ----------------------------
DROP TABLE IF EXISTS `md_user_saleer`;
CREATE TABLE `md_user_saleer`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `user_id`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
    `saleer_md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '销售（md5）',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-销售关联表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_userauthcode
-- ----------------------------
DROP TABLE IF EXISTS `md_userauthcode`;
CREATE TABLE `md_userauthcode`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `user_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户id',
    `auth_code`  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户授权码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_utside_hand
-- ----------------------------
DROP TABLE IF EXISTS `md_utside_hand`;
CREATE TABLE `md_utside_hand`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `result`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果值',
    `id_charge`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目id',
    `id_check`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目ID',
    `result_hand` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结果值',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS外送手动结果表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_vation_and_fzx
-- ----------------------------
DROP TABLE IF EXISTS `md_vation_and_fzx`;
CREATE TABLE `md_vation_and_fzx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `vation_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '团体任务ID',
    `fzx_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `xzzt`       tinyint(1) NULL DEFAULT NULL COMMENT '下载状态：0未下载 1已下载',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '团体任务分中心（不会被同步）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_visit_main
-- ----------------------------
DROP TABLE IF EXISTS `md_visit_main`;
CREATE TABLE `md_visit_main`
(
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `is_inspect`     tinyint(1) NULL DEFAULT NULL COMMENT '是否已来检：1已来检，0未来检',
    `inspect_time`   datetime(0) NULL DEFAULT NULL COMMENT '来检时间',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '更改时间',
    `is_inspected`   tinyint(1) NULL DEFAULT NULL COMMENT '是否已检：0已检 1未已检',
    `type`           tinyint(1) NULL DEFAULT NULL COMMENT '回访类型：0：迟检；1：阳性结果；2：不合格样本 //3.补检(护理登记用到 ) 之前写的3，实际上护理登记补检处理成4',
    `note`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    `belowquestion`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本不合格原因ID',
    `is_delete`      tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `id_examfeeitem` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '与迟检、阳性、不合格样本回访表一对多关联，' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_visit_write
-- ----------------------------
DROP TABLE IF EXISTS `md_visit_write`;
CREATE TABLE `md_visit_write`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `satisfaction_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '满意度ID',
    `visit_result`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访结果',
    `visit_level`     tinyint(1) NULL DEFAULT NULL COMMENT '0:不满意回访；1：再次不满意回访',
    `visitter_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回访人ID',
    `visit_time`      datetime(0) NULL DEFAULT NULL COMMENT '回访时间',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `memo`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KF回访记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_whysqzfw
-- ----------------------------
DROP TABLE IF EXISTS `md_whysqzfw`;
CREATE TABLE `md_whysqzfw`
(
    `id`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `harm_name`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素名字',
    `unit`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '取值范围单位',
    `jc_name`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目名称',
    `jc_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查项目ID',
    `wh_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素ID',
    `scope_upper`  decimal(12, 3) NULL DEFAULT NULL COMMENT '男取值范围上限',
    `scoper_floor` decimal(12, 3) NULL DEFAULT NULL COMMENT '男取值范围下限',
    `scoper_name`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '范围名称',
    `scoper_code`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '范围代码',
    `lis_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LIS编号',
    `createdate`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`    tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `zyorjk`       tinyint(1) NULL DEFAULT NULL COMMENT '职业/综合',
    `input_code`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素输入码',
    `sex`          tinyint(1) NULL DEFAULT NULL COMMENT '男女标识',
    `gscopeupper`  decimal(12, 3) NULL DEFAULT NULL COMMENT '女取值范围上限',
    `gscoperfloor` decimal(12, 3) NULL DEFAULT NULL COMMENT '女取值范围下限',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC危害因素取值范围' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wsjg
-- ----------------------------
DROP TABLE IF EXISTS `md_wsjg`;
CREATE TABLE `md_wsjg`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `name`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `srm`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `addr`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
    `phone`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `lxr`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '外送机构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_callback
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_callback`;
CREATE TABLE `md_wz_callback`
(
    `id`                        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `djls`                      varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记流水',
    `patientcode`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `amount`                    int(0) NULL DEFAULT NULL COMMENT '体检次数',
    `create_date`               datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `date_from`                 datetime(0) NULL DEFAULT NULL COMMENT '复查时间起',
    `date_to`                   datetime(0) NULL DEFAULT NULL COMMENT '复查时间止',
    `item_code`                 varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目编码',
    `item_name`                 varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查项目',
    `result_text`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '复查结果',
    `summary_code`              varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总结代码',
    `summary_text`              varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理意见',
    `callback_order_text`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查内容',
    `callback_station`          varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查状态',
    `db_user`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `callback_class`            varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复查类别',
    `notice_of_proceeding_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '注意事项',
    `createdate`                datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`                datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `create_id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——复查随访' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_grxx
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_grxx`;
CREATE TABLE `md_wz_grxx`
(
    `id`                     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `djls`                   varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记流水',
    `patientcode`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `amount`                 int(0) NULL DEFAULT NULL COMMENT '体检次数',
    `create_date`            datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `name`                   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `name_phonetic`          varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拼音码',
    `age`                    int(0) NULL DEFAULT NULL COMMENT '年龄',
    `sex_code`               varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `id_card`                varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
    `nation`                 varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族',
    `birth_place`            varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'BIRTH_PLACE',
    `kultur`                 varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文化程度',
    `marital_status`         varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '婚姻状况',
    `total_length_of_server` int(0) NULL DEFAULT NULL COMMENT '总工龄',
    `now_length_of_server`   int(0) NULL DEFAULT NULL COMMENT '接害工龄',
    `occupation_harm`        varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '毒害种类和名称',
    `working_poct`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '劳动合同',
    `db_user`                varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `remark`                 varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `qrbz`                   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QRBZ',
    `create_id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `createdate`             datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`             datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——个人基本信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_jwb
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_jwb`;
CREATE TABLE `md_wz_jwb`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '序号',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `input_code`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `occupation_diseast` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '病名',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——既往病' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_jzb
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_jzb`;
CREATE TABLE `md_wz_jzb`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '序号',
    `createdate`        datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`        datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `family_of_disease` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '病名',
    `input_code`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——家族病' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_latest_rummager
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_latest_rummager`;
CREATE TABLE `md_wz_latest_rummager`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '序号',
    `cid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心ID',
    `ks_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室ID',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `rummager_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查人ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——最近检查人' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_life
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_life`;
CREATE TABLE `md_wz_life`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `djls`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记流水',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `amount`      int(0) NULL DEFAULT NULL COMMENT '体检次数',
    `id1`         int(0) NULL DEFAULT NULL COMMENT '序号(不用，是原带的）',
    `create_date` datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `cqshdq`      varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '长期生活地区',
    `ysxg`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '饮食习惯',
    `qjxg`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '起居习惯',
    `xgtz`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性格特征',
    `jszk`        varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '精神状况',
    `dfb`         varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地方病',
    `yqshs`       varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疫区生活史',
    `ywlyqk`      varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药物滥用情况',
    `db_user`     varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `remark`      varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `create_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——个人生活史' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_symptom
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_symptom`;
CREATE TABLE `md_wz_symptom`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `symptom_id`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '症状ID',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——症状' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_tjdwxx
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_tjdwxx`;
CREATE TABLE `md_wz_tjdwxx`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `patientcode`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `create_date`   datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `corp_code`     varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位编码',
    `corp_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位名称',
    `corp_address`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
    `post`          varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮编',
    `economy_type`  varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ECONOMY_TYPE',
    `calling`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'CALLING',
    `calling_space` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'CALLING_SPACE',
    `phone`         varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `fax`           varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '传真',
    `db_user`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
    `remark`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `lxr`           varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'LXR',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——体检单位信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_tjdwxx_mx
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_tjdwxx_mx`;
CREATE TABLE `md_wz_tjdwxx_mx`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '序号',
    `djls`            varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记流水',
    `patientcode`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `amount`          int(0) NULL DEFAULT NULL COMMENT '体检次数',
    `create_date`     datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `basic_course`    varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '基本工艺过程',
    `defend_manage`   varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '防护设施和防护用品的管理',
    `work_condition`  varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '劳动条件',
    `diathesis_check` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素检测',
    `db_user`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `remark`          varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `corp_code`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位编码',
    `corp_name`       varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位名称',
    `corp_address`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位地址',
    `post`            char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮编',
    `lxr`             varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
    `phone`           varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——单位明细信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_tjrecord
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_tjrecord`;
CREATE TABLE `md_wz_tjrecord`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '序号',
    `djls`               varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记流水',
    `patientcode`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `amount`             int(0) NULL DEFAULT NULL COMMENT '体检次数',
    `create_date`        datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `regimentation_note` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类别',
    `db_user`            varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `remark`             varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——体检记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_zybs
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_zybs`;
CREATE TABLE `md_wz_zybs`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '序号',
    `djls`               varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记流水',
    `id_patientarchive`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案号',
    `amount`             int(0) NULL DEFAULT NULL COMMENT '体检次数',
    `create_date`        datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `occupation_diseast` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '疾病名称',
    `diagnosis_date`     datetime(0) NULL DEFAULT NULL COMMENT '诊断日期',
    `diagnosis_dept`     varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '诊断单位',
    `diagnosis_desc`     varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '转归',
    `db_user`            varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `remark`             varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '治疗经过',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `status`             tinyint(1) NULL DEFAULT NULL COMMENT '是否痊愈',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——职业病史' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_zys
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_zys`;
CREATE TABLE `md_wz_zys`
(
    `id`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '序号',
    `djls`               varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记流水',
    `id_patientarchive`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '档案ID',
    `amount`             int(0) NULL DEFAULT NULL COMMENT '体检次数',
    `create_date`        datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `start_date`         datetime(0) NULL DEFAULT NULL COMMENT '开始年月',
    `stop_date`          datetime(0) NULL DEFAULT NULL COMMENT '截止年月',
    `dept`               varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作单位',
    `branch`             varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门',
    `type_of_work`       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工种',
    `occupation_harm`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '有无有害因素',
    `locale_test_amount` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '有无检测',
    `defend_manage`      varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'DEFEND_MANAGE',
    `occupation_bargain` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '有无告知',
    `occupation_defend`  varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '有无防护',
    `db_user`            varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `remark`             varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '治疗经过',
    `createdate`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `dept_id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作单位ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `pk_record_key`(`djls`, `id`) USING BTREE,
    INDEX                `i_occupation_record_date`(`create_date`) USING BTREE,
    INDEX                `i_occupation_record_dept`(`dept`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊——职业史' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_wz_zys_whys
-- ----------------------------
DROP TABLE IF EXISTS `md_wz_zys_whys`;
CREATE TABLE `md_wz_zys_whys`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '序号',
    `djls`          varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记流水',
    `patientcode`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '体检号',
    `amount`        int(0) NULL DEFAULT NULL COMMENT '体检次数',
    `occupation_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流水号',
    `create_date`   datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `diathesis`     varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素',
    `db_user`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `remark`        varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `createdate`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `create_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `modify_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'KS问诊—职业史—危害因素' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_yblx
-- ----------------------------
DROP TABLE IF EXISTS `md_yblx`;
CREATE TABLE `md_yblx`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `name`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样本名称',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `srm`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `xh`         int(0) NULL DEFAULT NULL COMMENT '序号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '样本类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_year
-- ----------------------------
DROP TABLE IF EXISTS `md_year`;
CREATE TABLE `md_year`
(
    `id`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `year`       varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年份',
    `parentname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父节点',
    `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '年份表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_zy_fhcl_gr
-- ----------------------------
DROP TABLE IF EXISTS `md_zy_fhcl_gr`;
CREATE TABLE `md_zy_fhcl_gr`
(
    `id`                      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID号',
    `defend_individual_code`  varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '防护用品代码',
    `defend_individual`       varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '防护用品',
    `input_code`              varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`                 varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `defend_individual_class` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '防护用品种类代码',
    `createdate`              datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`              datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `gr_id`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人防护用品种类ID',
    `is_delete`               tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC个人防护用品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_zy_fhcs_gc
-- ----------------------------
DROP TABLE IF EXISTS `md_zy_fhcs_gc`;
CREATE TABLE `md_zy_fhcs_gc`
(
    `id`                       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID号',
    `defend_engieering_code`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码',
    `defend_engineering`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
    `input_code`               varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `defend_engineering_class` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工程防护种类代码',
    `gc_id`                    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工程防护种类ID',
    `createdate`               datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`               datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`                tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC工程防护' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_zy_fhcs_gc_class
-- ----------------------------
DROP TABLE IF EXISTS `md_zy_fhcs_gc_class`;
CREATE TABLE `md_zy_fhcs_gc_class`
(
    `id`                            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID号',
    `defend_engineering_class_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代码',
    `defend_engineering_class`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
    `input_code`                    varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`                       varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `createdate`                    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `is_delete`                     tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC工程防护种类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_zy_fhcs_gr_class
-- ----------------------------
DROP TABLE IF EXISTS `md_zy_fhcs_gr_class`;
CREATE TABLE `md_zy_fhcs_gr_class`
(
    `id`                           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID号',
    `defend_individual_class_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '防护用品种类代码',
    `defend_individual_class`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '防护用品种类',
    `input_code`                   varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`                      varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `createdate`                   datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `modifydate`                   datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `is_delete`                    tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC个人防护用品种类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_zy_harm_class
-- ----------------------------
DROP TABLE IF EXISTS `md_zy_harm_class`;
CREATE TABLE `md_zy_harm_class`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `har_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因_id号',
    `harm_class_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害种类代码',
    `harm_class`      varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素种类',
    `input_code`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `name`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `note`            varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `is_delete`       tinyint(1) NULL DEFAULT NULL COMMENT '是否删除 0：不删除，1：删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职业危害因素分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_zy_occupation_org
-- ----------------------------
DROP TABLE IF EXISTS `md_zy_occupation_org`;
CREATE TABLE `md_zy_occupation_org`
(
    `org_code`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构代码',
    `org_name`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构名称',
    `org_other_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构别名',
    `org_info`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '详细信息',
    `id`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID',
    `createdate`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`     datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `is_delete`      tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `input_code`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    PRIMARY KEY (`org_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC职业病体检机构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_zy_summary
-- ----------------------------
DROP TABLE IF EXISTS `md_zy_summary`;
CREATE TABLE `md_zy_summary`
(
    `id`                         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `serial_no`                  int(0) NULL DEFAULT NULL COMMENT '序列号',
    `occupation_summary_code`    varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论代码',
    `occupation_summary`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论',
    `print_for_short`            varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结论简称',
    `occupation_summary_explain` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `input_code`                 varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `createdate`                 datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                 datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
    `is_delete`                  tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `sort`                       int(0) NULL DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC职业病检查结论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for md_zy_vs_summary
-- ----------------------------
DROP TABLE IF EXISTS `md_zy_vs_summary`;
CREATE TABLE `md_zy_vs_summary`
(
    `id`                        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id号',
    `regimentation_note`        varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业体检类别',
    `occupation_summary_class`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总结分类',
    `occupation_summary_code`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总结代码',
    `diagnosis_from`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参考依据',
    `occupation_diagnosis_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素代码',
    `occupation_diagnosis`      varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素',
    `diagnosis`                 text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '疾病',
    `summary_text`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '处理意见',
    `for_person_influence`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '对人体的影响',
    `input_code`                varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `db_user`                   varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `occupation_diseast`        varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可疑职业病',
    `occupation_summary`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查结论',
    `createdate`                datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`                datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
    `health_evaluation_class`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '危害因素分类分类',
    `zzjjzdm`                   varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业禁忌症代码',
    `is_delete`                 tinyint(1) NULL DEFAULT NULL COMMENT '是否删除：0.未删除 1.已删除',
    `serial_no`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总结分类ID',
    `item_id`                   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收费项目id',
    `always_in_report`          tinyint(1) NULL DEFAULT NULL COMMENT '建议进报告：1进 0不进',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'JC职业病处理意见' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`
(
    `sched_name`    varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_name`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
    `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    `blob_data`     blob NULL COMMENT '存放持久化Trigger对象',
    PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
    CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Blob类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`
(
    `sched_name`    varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
    `calendar`      blob                                                          NOT NULL COMMENT '存放持久化calendar对象',
    PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日历信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`
(
    `sched_name`      varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_name`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
    `trigger_group`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
    `time_zone_id`    varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '时区',
    PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
    CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Cron类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`
(
    `sched_name`        varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `entry_id`          varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '调度器实例id',
    `trigger_name`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
    `trigger_group`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    `instance_name`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例名',
    `fired_time`        bigint(0) NOT NULL COMMENT '触发的时间',
    `sched_time`        bigint(0) NOT NULL COMMENT '定时器制定的时间',
    `priority`          int(0) NOT NULL COMMENT '优先级',
    `state`             varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '状态',
    `job_name`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
    `job_group`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务组名',
    `is_nonconcurrent`  varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否并发',
    `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否接受恢复执行',
    PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '已触发的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`
(
    `sched_name`        varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `job_name`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
    `job_group`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
    `description`       varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
    `job_class_name`    varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行任务类名称',
    `is_durable`        varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '是否持久化',
    `is_nonconcurrent`  varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '是否并发',
    `is_update_data`    varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '是否更新数据',
    `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '是否接受恢复执行',
    `job_data`          blob NULL COMMENT '存放持久化job对象',
    PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`
(
    `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `lock_name`  varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '悲观锁名称',
    PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储的悲观锁信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`
(
    `sched_name`    varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '暂停的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`
(
    `sched_name`        varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `instance_name`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
    `last_checkin_time` bigint(0) NOT NULL COMMENT '上次检查时间',
    `checkin_interval`  bigint(0) NOT NULL COMMENT '检查间隔时间',
    PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度器状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`
(
    `sched_name`      varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_name`    varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
    `trigger_group`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    `repeat_count`    bigint(0) NOT NULL COMMENT '重复的次数统计',
    `repeat_interval` bigint(0) NOT NULL COMMENT '重复的间隔时间',
    `times_triggered` bigint(0) NOT NULL COMMENT '已经触发的次数',
    PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
    CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '简单触发器的信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`
(
    `sched_name`    varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_name`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
    `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
    `str_prop_1`    varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
    `str_prop_2`    varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
    `str_prop_3`    varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
    `int_prop_1`    int(0) NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
    `int_prop_2`    int(0) NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
    `long_prop_1`   bigint(0) NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
    `long_prop_2`   bigint(0) NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
    `dec_prop_1`    decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
    `dec_prop_2`    decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
    `bool_prop_1`   varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
    `bool_prop_2`   varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
    PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
    CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '同步机制的行锁表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`
(
    `sched_name`     varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
    `trigger_name`   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的名字',
    `trigger_group`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组的名字',
    `job_name`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
    `job_group`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
    `description`    varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
    `next_fire_time` bigint(0) NULL DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
    `prev_fire_time` bigint(0) NULL DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
    `priority`       int(0) NULL DEFAULT NULL COMMENT '优先级',
    `trigger_state`  varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '触发器状态',
    `trigger_type`   varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '触发器的类型',
    `start_time`     bigint(0) NOT NULL COMMENT '开始时间',
    `end_time`       bigint(0) NULL DEFAULT NULL COMMENT '结束时间',
    `calendar_name`  varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日程表名称',
    `misfire_instr`  smallint(0) NULL DEFAULT NULL COMMENT '补偿执行的策略',
    `job_data`       blob NULL COMMENT '存放持久化job对象',
    PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
    INDEX            `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
    CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_branch
-- ----------------------------
DROP TABLE IF EXISTS `sys_branch`;
CREATE TABLE `sys_branch`
(
    `id`                 int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `branch_id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分中心id',
    `fzx`                varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心名字',
    `jm`                 varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简码，不能重复',
    `srm`                varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `pid`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级分中心id',
    `is_delete`          tinyint(1) NULL DEFAULT 0 COMMENT '是否删除：0.未删除 1.已删除',
    `address`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
    `is_default`         tinyint(1) NULL DEFAULT NULL COMMENT '只能有一个为1的分中心,当前数据库的分中心',
    `branch_type`        tinyint(1) NULL DEFAULT NULL COMMENT '机构类型，详见：com.center.medical.bean.enums',
    `f_pay_online`       tinyint(1) NULL DEFAULT NULL COMMENT '是否支持在线支付：0.否 1.是',
    `lng`                varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经度',
    `lat`                varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '纬度',
    `tel`                varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
    `start_time`         varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抽号开始时间1 HH:mm',
    `end_time`           varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抽号结束时间1 HH:mm',
    `province`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
    `city`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
    `area`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
    `inspect_info`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '检前须知',
    `no_info`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '抽号须知',
    `pics`               varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构照片',
    `introduce`          text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '机构介绍',
    `qt_info`            varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前台须知',
    `picture`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图',
    `hospital_sub_id`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构门店id',
    `pyjm`               varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拼音简码（用于会员卡卡号）',
    `is_show`            tinyint(1) NULL DEFAULT NULL COMMENT '是否展示：0.否 1.是',
    `start_time2`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抽号开始时间2 HH:mm',
    `end_time2`          varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抽号结束时间2 HH:mm',
    `reservation_period` smallint(0) NULL DEFAULT NULL COMMENT '预约周期(天)',
    `branch_sort`        smallint(0) NULL DEFAULT NULL COMMENT '排序',
    `centerorgname`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `brief_fzx`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `is_wechat_app`      tinyint(1) NULL DEFAULT NULL,
    `queue_url`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `lat_gcj`            varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `lng_gcj`            varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `create_time`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`        datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uni_brand_id`(`is_delete`, `branch_id`) USING BTREE,
    UNIQUE INDEX `uni_fzx`(`is_delete`, `fzx`) USING BTREE,
    UNIQUE INDEX `uni_jm`(`is_delete`, `jm`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分中心维护表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`
(
    `config_id`    int(0) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    `config_name`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
    `config_key`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键名',
    `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键值',
    `config_type`  char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
    `create_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `dept_id`             bigint(0) NOT NULL AUTO_INCREMENT COMMENT '部门id',
    `parent_id`           bigint(0) NULL DEFAULT 0 COMMENT '父部门id',
    `ancestors`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '祖级列表',
    `dept_name`           varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
    `description`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `is_function`         char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否为功能部门0为非功能科室1为功能科室',
    `input_code`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '拼音输入码',
    `sjbggs`              tinyint(0) NULL DEFAULT NULL COMMENT '数据报告格式，详见：enums.com.center.medical.bean.SjbggsType',
    `jcdd`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检查地点',
    `jklx`                varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口类型，详见：enums.com.center.medical.bean.LpsJklxType',
    `ksh`                 varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室号',
    `imgpath`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
    `report_path_health`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '健康报告默认模板存放路径',
    `report_path_disease` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职业报告默认模板存放路径',
    `add_pic_before`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否最后追加图片：0否 1.是',
    `report_sort`         smallint(0) NULL DEFAULT NULL COMMENT '科室报告排序',
    `dyd_xh`              smallint(0) NULL DEFAULT NULL COMMENT '导引单排序',
    `dyd_memo`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '导引单备注',
    `kingdee_account_no`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金蝶号码',
    `grey_imgpath`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '灰色图路径',
    `is_showapp`          char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否在APP显示：0否 1.是',
    `order_num`           int(0) NULL DEFAULT 0 COMMENT '显示顺序',
    `leader`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
    `phone`               varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
    `email`               varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `status`              char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '部门状态：0.正常 1.停用',
    `del_flag`            char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志：0.代表存在 2.代表删除',
    `create_by`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`dept_id`) USING BTREE,
    INDEX                 `idx_parent_id`(`parent_id`) USING BTREE,
    INDEX                 `idx_dept_name`(`dept_name`) USING BTREE,
    INDEX                 `idx_input_code`(`input_code`) USING BTREE,
    INDEX                 `idx_order_num_status`(`order_num`, `status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`
(
    `dict_code`   bigint(0) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `dict_sort`   int(0) NULL DEFAULT 0 COMMENT '字典排序',
    `dict_label`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
    `dict_value`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
    `dict_type`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `css_class`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    `list_class`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
    `is_default`  char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`
(
    `dict_id`     bigint(0) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `dict_name`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
    `dict_type`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_id`) USING BTREE,
    UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`
(
    `job_id`          bigint(0) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `job_name`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '任务名称',
    `job_group`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
    `invoke_target`   varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
    `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
    `misfire_policy`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
    `concurrent`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
    `status`          char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
    `create_by`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`     datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`     datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remark`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
    PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`
(
    `job_log_id`     bigint(0) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
    `job_name`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '任务名称',
    `job_group`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '任务组名',
    `invoke_target`  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
    `job_message`    varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
    `status`         char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
    `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '异常信息',
    `create_time`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`
(
    `info_id`        bigint(0) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
    `user_name`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户账号',
    `ipaddr`         varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
    `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录地点',
    `browser`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
    `os`             varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作系统',
    `status`         char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
    `msg`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '提示消息',
    `login_time`     datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
    PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 342 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `menu_id`     bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
    `parent_id`   bigint(0) NULL DEFAULT 0 COMMENT '父菜单ID',
    `order_num`   int(0) NULL DEFAULT 0 COMMENT '显示顺序',
    `path`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
    `component`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
    `query`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由参数',
    `is_frame`    int(0) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
    `is_cache`    int(0) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
    `menu_type`   char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `visible`     char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
    `perms`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
    `icon`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2378 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`
(
    `notice_id`      int(0) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    `notice_title`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
    `notice_type`    char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '公告类型（1通知 2公告）',
    `notice_content` longblob NULL COMMENT '公告内容',
    `status`         char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
    `create_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`    datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`    datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remark`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`
(
    `oper_id`        bigint(0) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `title`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模块标题',
    `business_type`  int(0) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    `method`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '方法名称',
    `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方式',
    `operator_type`  int(0) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    `oper_name`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人员',
    `dept_name`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
    `oper_url`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求URL',
    `oper_ip`        varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主机地址',
    `oper_location`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作地点',
    `oper_param`     varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求参数',
    `json_result`    varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '返回参数',
    `status`         int(0) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
    `error_msg`      varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误消息',
    `oper_time`      datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
    PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1025 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`
(
    `post_id`     bigint(0) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `post_code`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
    `post_name`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
    `post_sort`   int(0) NOT NULL COMMENT '显示顺序',
    `status`      char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '状态（0正常 1停用）',
    `create_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `role_id`             bigint(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`           varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '角色名称',
    `role_key`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
    `role_sort`           int(0) NOT NULL COMMENT '显示顺序',
    `data_scope`          char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
    `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
    `status`              char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NOT NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    `del_flag`            char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`         datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `remark`              varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`
(
    `role_id` bigint(0) NOT NULL COMMENT '角色ID',
    `dept_id` bigint(0) NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` bigint(0) NOT NULL COMMENT '角色ID',
    `menu_id` bigint(0) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_upload_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_upload_log`;
CREATE TABLE `sys_upload_log`
(
    `id`              varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`      datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `cid`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    `upload_type`     int(0) NULL DEFAULT NULL COMMENT '上传数据类型(SYS_UPLOAD_TYPE)代码',
    `upload_num`      int(0) NULL DEFAULT NULL COMMENT '上传数据条数',
    `upload_date`     datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
    `upload_status`   tinyint(1) NULL DEFAULT NULL COMMENT '上传结果',
    `msg`             text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误信息',
    `ip`              varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
    `upload_date_str` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上传时间YYYY-MM-DD',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX             `IDX_SYS_UP_LOG_CID`(`cid`) USING BTREE,
    INDEX             `IDX_SYS_UP_LOG_DATE`(`upload_date`) USING BTREE,
    INDEX             `IDX_SYS_UP_LOG_STATUS`(`upload_status`) USING BTREE,
    INDEX             `IDX_SYS_UP_LOG_STR`(`upload_date_str`) USING BTREE,
    INDEX             `IDX_SYS_UP_LOG_TYPE`(`upload_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据上传接收日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_upload_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_upload_type`;
CREATE TABLE `sys_upload_type`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `createdate`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `modifydate`  datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `upload_type` int(0) NULL DEFAULT NULL COMMENT '类型代码',
    `upload_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型名称',
    `table_name`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据上传接收日志数据类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`            bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `dept_id`            bigint(0) NULL DEFAULT NULL COMMENT '部门ID',
    `user_name`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
    `nick_name`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
    `user_type`          varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
    `email`              varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
    `phonenumber`        varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
    `sex`                char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    `avatar`             varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '头像地址',
    `password`           varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
    `sdiscount`          decimal(10, 4) NULL DEFAULT NULL COMMENT '销售折扣',
    `ldiscount`          decimal(10, 4) NULL DEFAULT NULL COMMENT '领导折扣',
    `isleader`           char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否领导：0.否 1.是)',
    `cid`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分中心id',
    `status`             char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
    `input_code`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入码',
    `recive_code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交接密码',
    `is_doc`             char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否是医师：0.否 1.是',
    `user_code`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接口库用户代码（lis代码）',
    `picture`            longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '医生照片',
    `sign_pic`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '医生签字',
    `superior_id`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上级id（销售，只有一层）',
    `isbj`               decimal(1, 0) NULL DEFAULT NULL COMMENT '是否补检账号：0.不是 1.是）',
    `secret_password`    varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '涉密密码（家庭卡）',
    `del_flag`           char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否删除：0.存在 1.删除）',
    `login_ip`           varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
    `login_date`         datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
    `create_by`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time`        datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time`        datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `exp_date`           datetime(0) NULL DEFAULT NULL COMMENT '用户密码修改日期',
    `lock_date`          datetime(0) NULL DEFAULT NULL COMMENT '禁用时间',
    `unlock_date`        datetime(0) NULL DEFAULT NULL COMMENT '解禁时间',
    `remark`             varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `kingdee_use_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金蝶账户ID',
    `kingdee_account_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金蝶账户状态',
    `entry_date`         datetime(0) NULL DEFAULT NULL,
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`
(
    `user_id` bigint(0) NOT NULL COMMENT '用户ID',
    `post_id` bigint(0) NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` bigint(0) NOT NULL COMMENT '用户ID',
    `role_id` bigint(0) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
