--小程序 添加业务结算表 (只更新了测试环境)
CREATE TABLE `md_bs_settlement` (
    `settlement_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '支付结算单据ID',
    `pay_no` varchar(36) DEFAULT NULL COMMENT '支付单号',
    `biz_pay_no` varchar(255) DEFAULT NULL COMMENT '外部订单流水号',
    `order_id` varchar(32) NOT NULL COMMENT '关联id',
    `biz_order_no` varchar(50) DEFAULT NULL COMMENT '第三方系统的订单号',
    `pay_type` int DEFAULT NULL COMMENT '支付方式 1:微信支付 2:支付宝',
    `pay_type_name` varchar(50) DEFAULT NULL COMMENT '支付方式名称',
    `pay_score` decimal(15,2) DEFAULT NULL COMMENT '支付积分',
    `pay_amount` decimal(15,2) DEFAULT NULL COMMENT '支付金额',
    `is_clearing` int DEFAULT NULL COMMENT '是否清算 0:否 1:是',
    `user_id` varchar(36) DEFAULT NULL COMMENT '用户ID',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `clearing_time` datetime DEFAULT NULL COMMENT '清算时间',
    `callback_time` datetime DEFAULT NULL COMMENT '回调时间',
    `version` int DEFAULT NULL COMMENT '版本号',
    `pay_status` int DEFAULT NULL COMMENT '支付状态 0:未支付 1:已支付',
    `change_amount_version` int DEFAULT NULL COMMENT '支付金额版本号',
    `order_origin` tinyint(1) DEFAULT NULL COMMENT '订单来源 1:创建套餐',
    `fzx_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '分中心id',
    PRIMARY KEY (`settlement_id`),
    KEY `idx_user_id_order_type` (`user_id`,`order_origin`),
    KEY `primary_order_no` (`order_id`) USING BTREE,
    KEY `idx_fzx_id` (`fzx_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1802877915774259203 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='业务结算表';



ALTER TABLE `medical_dev`.`md_createmeal_app`
    MODIFY COLUMN `img_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '图片' AFTER `label`;

