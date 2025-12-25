/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.center.medical.app.common.constants;

import com.center.medical.app.common.i18n.LanguageEnum;

/**
 * 常量
 *
 * @author yami
 */
public class Constant {

    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN_ID = 1;

    /**
     * 自营店id
     */
    public static final long MAIN_SHOP = 1L;

    /**
     * 如果把平台的数据也保存在店铺里面，如分类，热搜之类的，保存的店铺id
     */
    public static final long PLATFORM_SHOP_ID = 0L;
    /**
     * 平台条件标签上限
     */
    public static final long TAG_LIMIT_NUM = 20;
    /**
     * 平台店名称
     */
    public static final String PLATFORM_SHOP_NAME = "官方店";

    /**
     * 积分名称
     */
    public static final String SCORE_CONFIG = "SCORE_CONFIG";
    public static final String SCORE_EXPLAIN = "SCORE_EXPLAIN";
    public static final String LEVEL_SHOW = "LEVEL_SHOW";
    public static final String SCORE_EXPIRE = "SCORE_EXPIRE";
    public static final String SCORE_QUESTION = "SCORE_QUESTION";
    /**
     * 商城缺失sku属性时的字符描述
     */
    public static final String SKU_PREFIX = "规格:";

    public static final String DEFAULT_SKU = "规格";
    /**
     * 成长值名称
     */
    public static final String GROWTH_CONFIG = "GROWTH_CONFIG";

    /**
     * 会员初始等级id
     */
    public static final int USER_LEVEL_INIT = 1;

    /**
     * 商家线下核销
     */
    public static final int SERVICE_TYPE = 1;

    /**
     * 系统菜单最大id
     */
    public static final int SYS_MENU_MAX_ID = 30;

    /**
     * 订单超时时间
     */
    public static final int ORDER_MAX_TIME = 30;

    /**
     * 订单类型：积分订单
     */
    public static final int ORDER_TYPE_SCORE = 3;

    /**
     * 最大确认收货退款时间7天
     */
    public static final int MAX_FINALLY_REFUND_TIME = 7;

    /**
     * 退款最长申请时间，当申请时间过了这个时间段之后，会取消退款申请
     */
    public static final int MAX_REFUND_APPLY_TIME = 7;
    /**
     * 离即将退款超时x小时时提醒
     */
    public static final int MAX_REFUND_HOUR = 12;
    /**
     * 直播间置顶上限个数
     */
    public static final int MAX_TOP_NUM = 10;

    /**
     * 分销佣金结算在确认收货后的时间，维权期过后（7+7+1）
     */
    public static final int DISTRIBUTION_SETTLEMENT_TIME = MAX_FINALLY_REFUND_TIME + MAX_REFUND_APPLY_TIME + 1;

    /**
     * 查询订单成功状态
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 一级分类id
     */
    public static final Long CATEGORY_ID = 0L;

    /**
     * 配置名称
     */
    public static final String ALIPAY_CONFIG = "ALIPAY_CONFIG";
    public static final String WXPAY_CONFIG = "WXPAY_CONFIG";
    public static final String SUIXINGPAY_CONFIG = "SUIXINGPAY_CONFIG";
    public static final String QINIU_CONFIG = "QINIU_CONFIG";
    public static final String ALI_OSS_CONFIG = "ALI_OSS_CONFIG";
    public static final String Q_CLOUD_CONFIG = "Q_CLOUD_CONFIG";
    public static final String MINIO_OSS_CONFIG = "MINIO_OSS_CONFIG";
    public static final String HUAWEI_OBS_CONFIG = "HUAWEI_OBS_CONFIG";
    public static final String QUICKBIRD_CONFIG = "QUICKBIRD_CONFIG";
    public static final String QUICK100_CONFIG = "QUICK100_CONFIG";
    public static final String ALI_QUICK_CONFIG = "ALI_QUICK_CONFIG";
    public static final String MA_CONFIG = "MA_CONFIG";
    public static final String MP_CONFIG = "MP_CONFIG";
    public static final String ALIDAYU_CONFIG = "ALIDAYU_CONFIG";
    public static final String DOMAIN_CONFIG = "DOMAIN_CONFIG";
    public static final String HELP_CENTER = "HELP_CENTER";
    public static final String REPORT_CONFIG = "REPORT_CONFIG";
    public static final String ZK_RESERVATION_AES_KEY = "ZK_RESERVATION_AES_KEY";
    public static final String ZK_RESERVATION_RSA_KEY = "ZK_RESERVATION_RSA_KEY";
    public static final String ZK_RESERVATION_AUTHCODE = "ZK_RESERVATION_AUTHCODE";
    public static final String ZK_SYSTEM_FLAG = "ZK_SYSTEM_FLAG";
    public static final String MX_APP_CONFIG = "MX_APP_CONFIG";
    public static final String PAYPAL_CONFIG = "PAYPAL_CONFIG";
    public static final String MERCHANT_REGISTER_PROTOCOL_CN = "MERCHANT_REGISTER_PROTOCOL_CN";
    public static final String MERCHANT_REGISTER_PROTOCOL_EN = "MERCHANT_REGISTER_PROTOCOL_EN";
    public static final String SHOP_PROTOCOL_CN = "SHOP_PROTOCOL_CN";
    public static final String SHOP_PROTOCOL_EN = "SHOP_PROTOCOL_EN";
    public static final String SENSITIVE_WORDS = "SENSITIVE_WORDS";

    /**
     * 汇率配置
     */
    public static final String EXCHANGE_RATE_CONFIG = "EXCHANGE_RATE_CONFIG";

    /**
     * 记录缓存名称
     */
    public static final String FLOW_ANALYSIS_LOG = "flowAnalysisLog";

    /**
     * 心跳字符串
     */
    public static final String HEART_BEAT = "HEART_BEAT";


    /**
     * 最大会员等级折扣
     */
    public static final double MAX_LEVEL_DISCOUNT = 10D;

    /**
     * 最小会员等级积分倍率
     */
    public static final double MIN_LEVEL_RATE_SCORE = 1D;
    /**
     * 短信套餐包最大数量
     */
    public static final int MAX_SMS_PACKAGE = 10;
    /**
     * 商品最低金额(非积分商品)
     */
    public static final Double MIN_PRODUCT_AMOUNT = 0.01D;

    /**
     * 店铺最多可以签约的分类数量
     */
    public static final int SIGNING_CATEGORY_LIMIT_NUM = 200;

    /**
     * 店铺签约的品牌数量上限
     */
    public static final int SIGNING_BRAND_LIMIT_NUM = 50;

    /**
     * 店铺可以绑定的银行卡上限
     */
    public static final int SHOP_BANK_CARD_LIMIT_NUM = 5;
    /**
     * 句号（英文符号）
     */
    public static final String PERIOD = ".";
    /**
     * 逗号
     */
    public static final String COMMA = ",";
    /**
     * 中文逗号
     */
    public static final String CN_COMMA = "，";
    /**
     * 冒号
     */
    public static final String COLON = ":";
    /**
     * 零
     */
    public static final Long ZERO_LONG = 0L;
    /**
     * 零
     */
    public static final Double ZERO_DOUBLE = 0D;
    /**
     * 参考CacheKeyPrefix
     * cacheNames 与 key 之间的默认连接字符
     */
    public static final String UNION = "::";
    /**
     * 默认语言
     */
    public static final Integer DEFAULT_LANG = LanguageEnum.LANGUAGE_ZH_CN.getLang();
    public static final Integer MAX_PAGE_SIZE = 100;

    /**
     * 数据量大时，系统单次处理数据的数量
     */
    public static final Long MAX_DATA_HANDLE_NUM = 10000L;

    /**
     * 分销相关配置
     */
    public static final String DISTRIBUTION_CONFIG = "DISTRIBUTION_CONFIG";
    public static final String DISTRIBUTION_RECRUIT_CONFIG = "DISTRIBUTION_RECRUIT_CONFIG";
    public static final Double MAX_USER_BALANCE = 999999999.99D;
    public static String DISTRIBUTION_REMARKS = "分销配置";
    public static String DISTRIBUTION_RECRUIT_REMARKS = "分销招募推广配置";
    public static final String PURCHASES_ORDER = "PO";

    /**
     * 签名相关
     */
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    /**
     * 系统配置相关
     */
    public static final String PAY_SWITCH_CONFIG = "PAY_SWITCH_CONFIG";
    public static final String SERVICE_SWITCH_CONFIG = "SERVICE_SWITCH_CONFIG";
    public static final String PROD_SWITCH_CONFIG = "PROD_SWITCH_CONFIG";

    /**
     * 平台端系统接口请求路径
     */
    public static final String RT_GROUP_ORDER_LIST_PATH = "/open/api/v2/reservation/getGroupOrderList"; //根据手机号获取我的待预约团体订单列表
    public static final String RT_PERSON_LIST_PATH = "/open/api/v2/reservation/getPersonList"; //根据手机号获取我的待预约团体订单列表
    public static final String RT_GETRESERVATIONBYCODE_PATH = "/open/api/v2/reservation/getReservationByCode"; //根据体检号获取预约详情
    public static final String RT_PERSONAPPLY_PATH = "/open/api/v2/reservation/personApply"; //根据体检号获取预约详情
    public static final String RT_AVAILABLE_NUMS_PATH = "/open/api/v2/reservation/getAvailableNums"; //获取可预约时间段列表
    public static final String RT_APPLY_PATH = "/open/api/v2/reservation/apply"; //提交预约申请
    public static final String RT_CANCEL_PATH = "/open/api/v2/reservation/cancel"; //取消预约

    public static final String ZK_AUTH_CODE = "ZKAuthCode"; //授权码请求头名称


    public static final String RT_PHONE_REPORT_LIST_PATH = "/open/api/v2/phoneReport/list"; //手机报告查询列表
    public static final String RT_PHONE_REPORT_NEWLIST_PATH = "/open/api/v2/phoneReport/newList"; //新版手机报告查询列表
    public static final String RT_PHONE_REPORT_DETAILS_PATH = "/open/api/v2/phoneReport/details"; //手机报告查询详情
    public static final String RT_PHONE_REPORT_NEWDETAILS_PATH = "/open/api/v2/phoneReport/newDetails"; //新版手机报告查询详情
    public static final String RT_PHONE_REPORT_BRANCHCONFIG_PATH = "/open/api/v2/phoneReport/getBranchConfig"; //获取分中心配置

    public static final String RT_REPORT_MOBILEREPORT_VALIDATIONCODE = "/open/api/v2/phoneReport/validationCode"; //分享报告验证提取码并返回报告数据
    public static final String RT_REPORT_MOBILEREPORT_LASTACCESS = "/open/api/v2/phoneReport/lastAccess"; //分享报告-访问次数和ip


    public static final String RT_QUESTIONNAIRE_SAORUP = "/open/api/v2/questionnaire/saOrUp"; //问卷添加或更新
    public static final String RT_QUESTIONNAIRE_GETHISTORY = "/open/api/v2/questionnaire/getHistory"; //获取历史问卷
    public static final String RT_QUESTIONNAIRE_DETAILS = "/open/api/v2/questionnaire/details"; //获取问卷详情


    public static final String RT_NEWRESERVATION_HOMEPAGELIST_PATH = "/open/api/v2/newReservation/homePageList"; //新版手机报告查询列表
    public static final String RT_NEWRESERVATION_USERIDCARD_PATH = "/open/api/v2/newReservation/userIdcard"; //获取该手机号下绑定的档案
    public static final String RT_NEWRESERVATION_GETNEWREDATA_PATH = "/open/api/v2/newReservation/getNewReData"; //获取体检者数据
    public static final String RT_NEWRESERVATION_APPOINTMENTNOW_PATH = "/open/api/v2/newReservation/appointmentNow"; //根据体检号获取预约详情
    public static final String RT_NEWRESERVATION_QUERYUNIT_PATH = "/open/api/v2/newReservation/queryUnit"; //单位预约查询单位
    public static final String RT_NEWRESERVATION_UNITRESERVATION_PATH = "/open/api/v2/newReservation/unitReservation"; //单位预约提交
    public static final String RT_NEWRESERVATION_HELPAPPOINT_PATH = "/open/api/v2/newReservation/helpAppoint"; //帮人预约
    public static final String RT_NEWRESERVATION_MYORDER_PATH = "/open/api/v2/newReservation/myOrder"; //我的订单
    public static final String RT_NEWRESERVATION_ORDERMARKERS_PATH = "/open/api/v2/newReservation/orderMarkers"; //订单角标
    public static final String RT_CREATEMEALAPP_GETMEALTYPE_PATH = "/open/api/v2/createMealApp/getMealType"; //获取套餐分类
    public static final String RT_CREATEMEALAPP_GETMEALLIST_PATH = "/open/api/v2/createMealApp/getMealList"; //获取套餐列表
    public static final String RT_CREATEMEALAPP_GETMEALDETAILS_PATH = "/open/api/v2/createMealApp/getMealDetails"; //获取套餐详情
    public static final String RT_CREATEMEALAPP_GENERATECODE_PATH = "/open/api/v2/createMealApp/generateCode"; //生成体检号
    public static final String RT_CREATEMEALAPP_DELETECODE_PATH = "/open/api/v2/createMealApp/deleteCode"; //删除体检号
    public static final String RT_CREATEMEALAPP_CHECKPEISTATUS_PATH = "/open/api/v2/createMealApp/checkPeiStatus"; //校验体检号状态
    public static final String RT_APPARTICLE_GETARTICLETYPE_PATH = "/open/api/v2/appArticle/getArticleType"; //获取文章分类
    public static final String RT_APPARTICLE_GETARTICLELIST_PATH = "/open/api/v2/appArticle/getArticleList"; //获取文章列表
    public static final String RT_APPARTICLE_GETARTICLEDETAILS_PATH = "/open/api/v2/appArticle/getArticleDetails"; //获取文章详情




    /**
     * 康淘相关的配置
     */
    public static final String KANGTAO_HOST = "http://userapi.world.com"; //康淘后端接口地址
    public static final String KANGTAO_GETRECOMMEND_PATH = "/prod/getRecommend"; //获取推荐商品路径
    public static final String KANGTAO_GETRECOMMENDSHOP_PATH = "/prod/getRecommendShop"; //获取推荐商品的店铺列表路径


}
