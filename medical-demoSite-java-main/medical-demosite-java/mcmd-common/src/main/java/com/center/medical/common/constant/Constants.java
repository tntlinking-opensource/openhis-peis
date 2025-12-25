package com.center.medical.common.constant;

import io.jsonwebtoken.Claims;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用常量信息
 *
 * @author 路飞
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * www主域
     */
    public static final String WWW = "www.";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 英文逗号
     */
    public static final String COMMA = ",";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码有效期（秒）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2 * 60;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/newimage";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX1 = "newimage";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
     */
    public static final String[] JOB_WHITELIST_STR = {"com.center.medical"};

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = {"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.center.medical.common.utils.file"};

    /**
     * 配置名称
     */
    public static final String CONTRACT_NUM = "CONTRACT_NUM"; //合同序列号
    public static final String DOMAIN_CONFIG = "DOMAIN_CONFIG"; // 域名配置信息
    public static final String ORDER_CONFIG = "ORDER_CONFIG"; //订单相关的配置信息
    public static final String IP_CONFIG = "IP_CONFIG"; //IP配置
    public static final String HBV_CONFIG = "HBV_CONFIG"; //乙肝配置
    public static final String VERSION_NO = "VERSION_NO"; //体检号档案号等版本标识
    public static final String MA_CONFIG = "MA_CONFIG"; //微信小程序参数配置
    public static final String MP_CONFIG = "MP_CONFIG"; //微信公众号参数配置
    public static final String ALIDAYU_CONFIG = "ALIDAYU_CONFIG"; //阿里大鱼短信服务配置参数
    public static final String ALIPAY_CONFIG = "ALIPAY_CONFIG"; //支付宝支付参数配置
    public static final String MX_APP_CONFIG = "MX_APP_CONFIG"; //微信开放平申请应用相关信息参数配置
    public static final String WXPAY_CONFIG = "WXPAY_CONFIG"; //WXPAY_CONFIG
    public static final String PACS_CONFIG = "PACS_CONFIG"; //PACS参数配置
    public static final String RILIN_CONFIG = "RILIN_CONFIG";//瑞林萨尔健康管理系统对接配置
    public static final String YIYING_CONFIG = "YIYING_CONFIG";//易影云影像平台对接配置

    public static final String DICOM_CONFIG = "DICOM_CONFIG"; //DICOM参数配置
    public static final String LIS_CONFIG = "LIS_CONFIG"; //lis参数配置

    public static final String ALI_OSS_CONFIG = "ALI_OSS_CONFIG"; //阿里云OSS配置信息
    public static final String HUAWEI_OBS_CONFIG = "HUAWEI_OBS_CONFIG";
    public static final String FILE_PATH_CONFIG = "FILE_PATH_CONFIG"; //文件存储配置信息
    public static final String KINGDEE_CONFIG = "KINGDEE_CONFIG"; //金蝶参数配置
    public static final String OPEN_ORDER_CONFLICT = "OPEN_ORDER_CONFLICT"; //是否开启撞单处理
    public static final String BRANCH_CONFIG = "BRANCH_CONFIG"; //分中心独立功能系统的配置信息（只在线下系统配置）
    public static final String DATASCREEN_CONFIG = "DATASCREEN_CONFIG"; //可视化大屏配置
    public static final String FTP_CONFIG = "sys_config:FTP_CONFIG";//分中心FTP配置信息
    public static final String SYS_FTP_CONFIG = "FTP_CONFIG";//分中心FTP配置信息
    public static final String SYNC_TABLES_ONLINE = "sys_config:SYNC_TABLES_ONLINE";//线上需同步的数据表配置
    public static final String SYNC_TABLES_OFFLINE = "sys_config:SYNC_TABLES_OFFLINE";//线下需同步的数据表配置
    public static final String SYNC_REDIS_OFF_CONFIG = "sys_config:SYNC_REDIS_OFF_CONFIG"; //内网系统数据同步Redis库配置信息
    public static final String BARCODE_PRINTER = "BARCODE_PRINTER";//条码打印机型号
//    public static final String QUEUEING_SYSTEM_URL = "QUEUEING_SYSTEM_URL";//排队系统url（弃用，改用QUEUE_CONFIG）
    public static final String QUEUE_CONFIG="QUEUE_CONFIG";//排队配置信息
    public static final String SMS_CONFIG ="SMS_CONFIG";//短信配置

    public static final String DEFAULT_DOCTOR_NO = "DEFAULT_DOCTOR_NO";//默认总检医生签名id

    public static final String APPOINTMENT_SMS_CONFIG = "APPOINTMENT_SMS_CONFIG";//预约短信配置
    public static final String INSPECTION_REPORT_CONFIG = "INSPECTION_REPORT_CONFIG"; //检验报告配置
    public static final String GROUP_REPORT_CONFIG = "GROUP_REPORT_CONFIG"; //团检报告配置

    public static final String NURSING_REGISTRATION_POPID = "NURSING_REGISTRATION_POPID"; //护理登记弹框id

    public static final String WENDATONG_REPORT_CONFIG = "WENDATONG_REPORT_CONFIG"; //文达通报告查询参数配置

    public static final String OLD_SYSTEM_CONFIG = "OLD_SYSTEM_CONFIG"; //老系统参数配置

    public static final String ADICON_IMAGES_CONFIG = "ADICON_IMAGES_CONFIG"; //艾迪康图片设置，设置的获取图片
    public static final String CRISISVALUE_SUBMIT_CONFIG = "CRISISVALUE_SUBMIT_CONFIG"; //危急值自动提报项目设置，设置的项目才自动提报

    public static final String GROUP_RESERVATIONS_VIP_NUMBER = "GROUP_RESERVATIONS_VIP_NUMBER"; //团体排检，可预约vip、vvip的人数,超过该人数就要走超额审批
    public static final String REPORT_NOT_RELEASED_TIME = "REPORT_NOT_RELEASED_TIME"; //报告未出超时通知

    public static final String REPORT_GROUPNAME_CONFIG = "REPORT_GROUPNAME_CONFIG"; //报告分组名称设置,设置进去的订单号号，取的不是单位名，取的是分组名

    public static final String ADICON_CONFIG = "ADICON_CONFIG"; //艾迪康账号密码配置
    public static final String TONGLIAN_CONFIG = "TONGLIAN_CONFIG"; //通联支付配置
    public static final String TONGLIAN2_CONFIG = "TONGLIAN2_CONFIG"; //通联支付配置
    public static final String SUIXING_CONFIG = "SUIXING_CONFIG"; //随行支付配置
    public static final String EXAMTYPE_OPEN_CONFIG = "EXAMTYPE_OPEN_CONFIG"; //登记体检类型开启设置

    public static final String REPORT_SIGNATURE_CONFIG = "REPORT_SIGNATURE_CONFIG"; //报告单签名配置


    public static final String UPELEUSER_CONFIG = "UPELEUSER_CONFIG"; //电测听上传审核人配置
    public static final String BARCODE_MARGIN_CONFIG = "BARCODE_MARGIN_CONFIG"; //条码边距设置

    public static final String ADICON_SIGNATURE_CONFIG = "ADICON_SIGNATURE_CONFIG"; //艾迪康账号检验科签名配置


    public static final String PINGAN_CONFIG = "PINGAN_CONFIG"; //平安好医生配置


    public static final String MACHINE_REPORT_PRINT_CONFIG = "MACHINE_REPORT_PRINT_CONFIG"; //自助登记机报告打印配置

    public static final String REPORT_CODING_CONFIG = "REPORT_CODING_CONFIG"; //报告赋码配置

    public static final String DEPT_DATA_CONFIG = "DEPT_DATA_CONFIG"; //科室数据配置
    public static final String BACKGROUND_CONFIG= "BACKGROUND_CONFIG"; //背景设置
    /**
     * 加密解密/授权业务标识
     */
    public static final String RESERVATION_AES_FLAG = "reservation_aes";
    public static final String RESERVATION_AUTHCODE_FLAG = "reservation_authcode";
    public static final String RESERVATION_RSA_FLAG = "reservation_rsa";

    public static final String RESERVATION_CARD_FLAG = "reservation_card";

    /**
     * 工作流相关的
     */
    public static final String WORKFLOW_TYPE_FLAG_ORDER = "ORDER_FLOW";

    /**
     * redis生成档案号自增编号的key
     */
    public static final String ARCHIVE_NO_REDIS_KEY = "archiveNoRedisKey";

    /**
     * redis生成体检号自增编号的key
     */
    public static final String PATIENT_CODE_REDIS_KEY = "patientCode";

    /**
     * redis待使用的体检号集合的key
     */
    public static final String PATIENT_CODE_SET = "patientCodeSet";

    public static final String PATIENT_ARCHIVE_CODE_SET = "patientArchiveCodeSet";

    /**
     * redis生成订单号自增编号的key
     */
    public static final String ORDER_NUM_REDIS_KEY = "patientOrderNum";

    /**
     * redis生成预约号自增编号的key
     */
    public static final String RESERVATION_NO_REDIS_KEY = "reservationNoToday";

    /**
     * redis生成数字形式团体ID自增编号的key
     */
    public static final String INT_ID_REDIS_KEY = "intIdRedisKey";

    /**
     * redis生成虹桥lis中间库需要的每日重置的自增号码的key
     */
    public static final String HQ_NUM_DAY_KEY = "hqNumDay";

    /**
     * redis生成虹桥lis中间库需要的检查项目自增号码的key
     */
    public static final String HQ_ID_PATIENT_EXAM_ITEM_KEY = "hqIdPatientExamItem";

    /**
     * redis生成虹桥lis中间库需要的常规科室收费项目自增号码的key
     */
    public static final String HQ_ID_PATIENT_TRACE_EXAM_DEPART = "hqIdPatientTraceExamDepart";

    /**
     * redis生成虹桥lis中间库需要的收费项目自增号码的key
     */
    public static final String HQ_ID_PATIENT_FEE_ITEM = "hqIdPatientFeeItem";

    /**
     * redis数据库表配置的key
     */
    public static final String SYS_TABLE_CONFIG = "SysTableConfig::";

    /**
     * 线上体检号前缀
     */
    public final static String ONLINE_PREFIX = "APP";


    /**
     * 销售目标起始年份
     */
    public static final String YEAR = "2016";

    /**
     * 预检跟踪常量年和月
     */
    public static final String YEAR_TEXT = "1";
    public static final String MONTH_TEXT = "3";

    public static final String BR = "\n\r";

    /**
     * 系统授权密钥
     */
    public static final String SYS_AUTH_VALUE_KEY = "SYS_AUTH_INFO";

    /**
     * 活动赠送
     */
    public static String HDZS = "20";

    /**
     * pacs_ip
     */
    public static final String PACS_IP = "http\\://localhost\\:8098/lispacs/";

    /**
     * resourceUrl
     */
    public final static String RESOURCEURL = "/lispacs/lis_pacs!receivePacs.action";

    /**
     * 科室传图的路径
     */
    public final static String KS_UPLAOD_IMAGE_PATH = "/images/upload/";


    /**
     * 上传肺功能数据的路径
     */
    public final static String KS_UPLOAD_DATA_PATH = "/uploadLung/upload/";

    /**
     * 上传华欧医院电测听数据的路径
     */
    public final static String KS_HOUPLOADELE_DATA_PATH = "/hOUploadEle/upload/";
    /**
     * 上传胶南医院电测听数据的路径
     */
    public final static String KS_JNUPLOADELE_DATA_PATH = "/jNUploadEle/upload/";

    /**
     * 科室传图的路径，处理类似锦都彩超上传图片
     */
    public final static String KS_UPLAOD_IMAGE_PATH_2 = "/images/read/";

    /**
     * #年龄_性别（0男1女）_(消瘦xs,超重cz，肥胖fp)
     */
    public final static Map<String, String> MAP = new HashMap() {
        private static final long serialVersionUID = -8645298222947931543L;

        {
            put("6_0_xs", "13.4");
            put("6_0_cz", "16.9");
            put("6_0_fp", "18.2");
            put("7_0_xs", "13.6");
            put("7_0_cz", "17.4");
            put("7_0_fp", "19.2");
            put("8_0_xs", "13.8");
            put("8_0_cz", "18.1");
            put("8_0_fp", "20.3");
            put("9_0_xs", "14");
            put("9_0_cz", "18.9");
            put("9_0_fp", "21.4");
            put("10_0_xs", "14.3");
            put("10_0_cz", "19.6");
            put("10_0_fp", "22.5");
            put("11_0_xs", "14.7");
            put("11_0_cz", "20.3");
            put("11_0_fp", "23.6");
            put("12_0_xs", "15.1");
            put("12_0_cz", "21");
            put("12_0_fp", "24.7");
            put("13_0_xs", "15.7");
            put("13_0_cz", "21.9");
            put("13_0_fp", "25.7");
            put("14_0_xs", "16.3");
            put("14_0_cz", "22.6");
            put("14_0_fp", "26.4");
            put("15_0_xs", "16.8");
            put("15_0_cz", "23.1");
            put("15_0_fp", "26.9");
            put("16_0_xs", "17.3");
            put("16_0_cz", "23.5");
            put("16_0_fp", "27.4");
            put("17_0_xs", "17.7");
            put("17_0_cz", "23.8");
            put("17_0_fp", "27.8");
            put("18_0_xs", "18.1");
            put("18_0_cz", "24");
            put("18_0_fp", "28");
            put("6_1_xs", "13.1");
            put("6_1_cz", "16.7");
            put("6_1_fp", "18.1");
            put("7_1_xs", "13.2");
            put("7_1_cz", "17.2");
            put("7_1_fp", "18.9");
            put("8_1_xs", "13.4");
            put("8_1_cz", "18.1");
            put("8_1_fp", "19.9");
            put("9_1_xs", "13.7");
            put("9_1_cz", "19");
            put("9_1_fp", "21");
            put("10_1_xs", "14.1");
            put("10_1_cz", "20");
            put("10_1_fp", "22.1");
            put("11_1_xs", "14.6");
            put("11_1_cz", "21.1");
            put("11_1_fp", "23.3");
            put("12_1_xs", "15.2");
            put("12_1_cz", "21.9");
            put("12_1_fp", "24.5");
            put("13_1_xs", "15.8");
            put("13_1_cz", "22.6");
            put("13_1_fp", "25.6");
            put("14_1_xs", "16.3");
            put("14_1_cz", "23");
            put("14_1_fp", "26.3");
            put("15_1_xs", "16.7");
            put("15_1_cz", "23.4");
            put("15_1_fp", "26.9");
            put("16_1_xs", "16.9");
            put("16_1_cz", "23.7");
            put("16_1_fp", "27.4");
            put("17_1_xs", "17.1");
            put("17_1_cz", "23.8");
            put("17_1_fp", "27.7");
            put("18_1_xs", "17.2");
            put("18_1_cz", "24");
            put("18_1_fp", "28");
        }
    };


    /**
     * 体征词id
     */
    public static final String XS = "5251";//消瘦
    public static final String TZZC = "2156";//体重正常
    public static final String QDFP = "2159";//轻度肥胖
    public static final String ZDFP = "2158";//中度肥胖
    public static final String YZFP = "4034";//重度肥胖
    public static final String CZ = "4061";//超重
    public static final String FAT = "ff8080817920cd260179c03b0c3c2cdc";//肥胖（青少年）
    public static final String XYZG = "4915";//本次血压增高
    public static final String XYPD = "4918";//本次血压偏低
    public static final String XYZC = "4926";//本次血压正常


    /**
     * 检查项目Ids
     */
    public static final String SSYID = "434";
    public static final String SXYID = "435";
    public static final String MBID = "15";
    public static final String SGID = "12";
    public static final String TZID = "13";
    public static final String HXPLID = "10000";
    public static final String YBZKID = "1163";
    public static final String TEMPERATURE_EXAM_ID = "402848e3700dfffe01704675865e0dfe";
    public static final String XYJLID = "436";//血压结论id
    public static final String TZZSID = "14";//体重指数id


    /**
     * 体检类型
     */
    public static final int PHONEINFORM = 1;//体检类型 职业
    public static final int HEALTHPHONE = 0;//体检类型 健康


    /**
     * zySummary  id  (职业总检)
     */
    public final static String BC_SUMMARY_ID = "7";


    /**
     * 领导电话 15345427727,暂时用自己的
     */
    public final static String LEADER_PHONE = "15166033654";


    /**
     * 创建ip
     */
    public final static String CREATE_IP = "http\\://localhost\\:8083/medoc/";


    /**
     * dct.properties 电测听配置
     * #电测听生成折线图类型  默认：左耳气导、骨导在一起   1，职业综合体检，气导左右耳在一起，骨导左右耳在一起，健康，左耳气导、骨导在一起 。
     */
    public final static Map<String, String> DCT = new HashMap() {
        private static final long serialVersionUID = 3278518099024267453L;

        {
            put("F_List_500", "12-19:0,20-29:0,30-39:1,40-49:2,50-59:4,60-69:6,70-200:9");
            put("M_List_500", "12-19:0,20-29:0,30-39:1,40-49:2,50-59:4,60-69:6,70-200:9");
            put("F_List_1k", "12-19:0,20-29:0,30-39:1,40-49:2,50-59:4,60-69:7,70-200:11");
            put("M_List_1k", "12-19:0,20-29:0,30-39:1,40-49:2,50-59:4,60-69:7,70-200:11");
            put("F_List_2k", "12-19:0,20-29:0,30-39:1,40-49:3,50-59:6,60-69:11,70-200:16");
            put("M_List_2k", "12-19:0,20-29:0,30-39:1,40-49:3,50-59:7,60-69:12,70-200:19");
            put("F_List_3k", "12-19:0,20-29:0,30-39:1,40-49:4,50-59:8,60-69:13,70-200:20");
            put("M_List_3k", "12-19:0,20-29:0,30-39:2,40-49:6,50-59:12,60-69:20,70-200:31");
            put("F_List_4k", "12-19:0,20-29:0,30-39:1,40-49:4,50-59:9,60-69:16,70-200:24");
            put("M_List_4k", "12-19:0,20-29:0,30-39:2,40-49:8,50-59:16,60-69:28,70-200:43");
            put("F_List_6k", "12-19:0,20-29:0,30-39:2,40-49:6,50-59:12,60-69:21,70-200:32");
            put("M_List_6k", "12-19:0,20-29:0,30-39:3,40-49:9,50-59:18,60-69:32,70-200:49");
            put("F_List_8k", "12-19:0,20-29:0,30-39:2,40-49:7,50-59:15,60-69:27,70-200:41");
            put("M_List_8k", "12-19:0,20-29:0,30-39:3,40-49:11,50-59:23,60-69:39,70-200:60");
            put("bone_default", "10");
            put("chart_type", "0");
            put("desc", "\\u4F53\\u68C0\\u8005\\u5DF2\\u786E\\u8BA4\\u81EA\\u5DF1\\u79BB\\u5F00\\u566A\\u58F048\\u5C0F\\u65F6\\u3002");
        }
    };


    /**
     * pacs登记的体检号前缀
     */
    public final static String PACS_PREFIX = "PA";


    /**
     * 超级管理员
     */
    public final static String SUPER_MANAGER = "超级管理员";

    /**
     * 现金
     */
    public final static String XJ = "1";


    /**
     * 东华原CD210中医体质辨识  1开启 默认不开启
     */
    public final static String CD210 = "1";

    /**
     * 东华原DAS1000动脉硬化  1开启 默认不开启
     */
    public final static String Das1000 = "1";

    /**
     * FGC_A肺功能  1开启 默认不开启
     */
    public final static String FGC_A = "1";

    /**
     * #z值检查项目id
     */
    public final static String ZZ_EXAM_ID = "40286f81823ebcdc01823ec25b4b0006";


    /**
     * 骨密度页面，页面测得大于-1是正常等于-2是减少小于-2是疏松
     */
    public final static String GMD_HIGH = "-1";
    public final static String GMD_LOW = "-2";


    public final static String CHARGE_JF = "消费";//消费积分
    public final static String ADD_JF = "充值";//积分充值

    /**
     * 套餐打印设置
     */
    public final static String TCDY = "\\\\XXX.XXX.XXX.XXXCanon iR2318/2320 UFRII LT";

    /**
     * 是否是海关
     */
    public final static Integer ISHAIGUAN = 1;


    /**
     * 客户单位编辑 省市区街道选择标准 没有就是默认 1.是青岛标准
     */
    public final static Integer customerArea = 1;


    /**
     * 关闭重发
     */
    public final static Integer CLOSE_CHONGFA = 0;

    /**
     * 关闭重发
     */
    public final static String DYDSTYLE = "";

    /**
     * 平台端系统接口请求路径
     */
    public static final String RT_GROUP_ORDER_PATH = "/lan/reservation/pf/getGroupOrderList"; //根据手机号获取我的待预约团体订单列表

    public static final String RT_PERSON_LIST_PATH = "/lan/reservation/pf/getPersonList"; //根据手机号获取个检预约
    public static final String RT_GETRESERVATIONBYCODE_PATH = "/lan/reservation/pf/getReservationByCode"; //根据手机号获取个检预约
    public static final String RT_PERSONAPPLY_PATH = "/lan/reservation/pf/personApply"; //个检提交预约申请
    public static final String RT_REPORT_MOBILEREPORT_LIST = "/open/api/report/mobileReport/list"; //查看手机报告列表
    public static final String RT_REPORT_MOBILEREPORT_VALIDATIONCODE = "/open/api/report/mobileReport/validationCode"; //验证提取码并返回报告数据

    public static final String RT_REPORT_MOBILEREPORT_LASTACCESS = "/open/api/report/mobileReport/lastAccess"; //分享报告-访问次数和ip
    public static final String RT_REPORT_MOBILEREPORT_DETAILS = "/open/api/report/mobileReport/details"; //查看手机报告详情

    public static final String RT_REPORT_MOBILEREPORT_CONFIG = "/open/api/report/mobileReport/getBranchConfig"; //查看手机报告配置
    public static final String RT_AVAILABLE_NUMS_PATH = "/lan/reservation/pf/getAvailableNums"; //获取可预约时间段列表
    public static final String RT_APPLY_PATH = "/lan/reservation/pf/apply"; //接收预约申请信息
    public static final String RT_CHECK_GROUP_PATH = "/lan/reservation/pf/checkGroup"; //检查团检订单是否可以预约
    public static final String RT_CANCEL_PATH = "/lan/reservation/pf/cancel"; //预约取消

    public static final String RT_WENDATONG_REPORT_PATIENTCODE_LIST_PATH = "/lan/report/pf/patientcode/list"; //根据时间段获取报告体检号列表
    public static final String RT_WENDATONG_REPORT_PATIENTCODE_GET_BY_PATIENTCODE_PATH = "/lan/report/pf/report/getByPatientcode"; //根据体检号获取报告数据

    public static final String ONLINE_SYNC_DATA_DOWNLOAD = "/open/api/syncData/download"; //即时线上系统下载同步数据接口地址

    public static final String ONLINE_SYNC_DATA_DOWNLOAD_DELAY = "/open/api/syncData/downloadDelay"; //闲时线上系统下载同步数据接口地址

    public static final String ONLINE_SYNC_FILE_DOWNLOAD = "/open/api/syncData/downloadFile"; //线上系统下载同步数据接口地址
    public static final String ONLINE_SYNC_FILE_RESULT = "/open/api/syncData/downloadFile/result"; //线上系统接收下载同步数据结果接口地址
    public static final String ONLINE_SYNC_DATA_RESULT = "/open/api/syncData/result"; //线上系统接收下载同步数据结果接口地址
    public static final String ONLINE_SYNC_DATA_RECEIVE = "/open/api/syncData/receive"; //线上系统接收线下系统同步数据上来的数据接口地址

    public static final String ONLINE_SYNC_DATA_GENERATECODE = "/open/api/syncData/generateCode"; //线下拉取备用体检号的接口地址
    public static final String ONLINE_SYNC_DATA_GENERATEARCHIVECODE = "/open/api/syncData/generateArchiveCode"; //线下拉取备用体检号的接口地址

    public static final String ONLINE_SYNC_DATA_PULLONLINEDATA = "/open/api/syncData/pullOnlineData"; //线下拉取线上体检者数据接口地址
    public static final String ONLINE_OLDSYSTEM_SAORUP = "/open/api/oldSystem/reservation/saOrUp"; //线上保存预约信息
    public static final String ONLINE_OLDSYSTEM_LIST = "/open/api/oldSystem/reservation/list"; //线上获取预约时段列表
    public static final String ONLINE_OLDSYSTEM_GETRESERVATION = "/open/api/oldSystem/reservation/getReservation"; //线上获取预约详情
    public static final String ONLINE_OLDSYSTEM_CANCEL = "/open/api/oldSystem/reservation/cancel"; //线上取消预约

    /**
     * 分中心外联系统接口请求路径
     */
    public static final String LC_OUT_DO_PAY_PATH = "/lan/lc/out/pay/dopay"; //分中心外联系统支付转发接口地址

    public static final String ONLINE_CONFIRMORDER = "/reservation/pingAn/confirmOrder"; //线上平安好医生到检确认

    public static final String RT_QUESTIONNAIRE_SAORUP = "/open/api/questionnaire/saOrUp"; //小程序问卷保存或更新
    public static final String RT_QUESTIONNAIRE_GETHISTORY = "/open/api/questionnaire/getHistory"; //获取历史问卷
    public static final String RT_QUESTIONNAIRE_DETAILS = "/open/api/questionnaire/details"; //问卷获取详情


    /**
     * 核酸检查项目
     */
    public final static List<String> NUCLEIN_ITEMS_IDS = new ArrayList<String>() {
        private static final long serialVersionUID = -8578885644140579970L;

        {
            add("402848e3711b0680017121229ca71fc3");
            add("ff8080817206cfd401720c31218c3ae8");
            add("ff808081747c40940175115ea8550213");
        }
    };


    /**
     * 健康体检表
     * 是否公卫系统  1是 默认不是
     */
    public final static String PHYSICAL_EXAMINATION_FORM = "0";


    /**
     * 检验科检查项目id，多个id用逗号拼接，只要结果不是阴性，就标红
     * 管型、镜检白细胞、镜检红细胞、备注
     * 长沙反馈有问题，暂时先去掉
     */
//    public final static String SPECIAL_INSPECTION_EXAM_ITEMS = "1215,917,918,919";
    public final static String SPECIAL_INSPECTION_EXAM_ITEMS = null;


    /**
     * 特殊检查项目
     */
    public final static String REVIEWCOMPONY = "青岛沃德国际医疗健康产业股份有限公司综合门诊部";


    /**
     * 检验部分签名
     */
    public final static String JYSIGN = "蒋丽华";


    /**
     * 附在报告后面的pacs报告
     * 是否将彩超与放射报告附在报告最后  1是
     */
    public final static String PUT_PACS_LAST = "0";


    /**
     * 是否是海康医院项目  需要将pacs科室的pdf附在最后  1是，默认不是
     */
    public final static String IS_HAIKANG = "0";


    /**
     * 对比度pdf
     */
    public final static String CONTRASTPDF = "0";


    /**
     * 职业电话 默认的一个电话,如果有分中心设置的话,就取分中心设置的电话
     */
    public final static String ZXDH = "0532-68050577";


    /**
     * 邮编
     */
    public final static String ZIPCODE = "266555";


    /**
     * 体检报告logo
     */
    public final static String HEADER = "file/wordmodel/ks-i/tjbg_logo.jpg";


    /**
     * 体检报告logo
     */
    public final static String TOPIMG = "file/wordmodel/ks-i/topImg.jpg";


    /**
     * 是否启用电子签名 1启用  其他不启用  默认不启用
     */
    public final static String ELECTRONIC_SIGN = "1";


    /**
     * 职业报告第一页 默认 鲁卫计职检字
     * (2016)第(096)号
     */
    public final static String FIRST_ZJZ = "鲁卫计职检字 (2017)第(052)号";


    /**
     * 职业报告第一页  默认 沃德国际健管中心
     */
    public final static String FIRST_ORG = "东营市正骨医院";


    /**
     * 检查人显示格式：
     * 0，只显示检查者=审核人(检查人)   。
     * 1，检验科, 检验者：下面配置 审核者：下面配置 ；
     * 如果没有配置lab_rummager，lab_auditer，就 检验者：检验师 审核者：审核医师
     * 其他科室 ,检查者：录入人 其他科室审核者：审核人(检查人)。
     * 默认是0
     * 只有1的时候显示图片签名
     */
    public final static String DOCTOR_TYPE = "1";
    public final static String LAB_AUDITER = "admin";//姜华
    public final static String LAB_RUMMAGER = "admin";//曾雨


    /**
     * 当doctor_type为0时，仍然要使用电子签名，就配置成1，默认0
     */
    public final static String USE_PIC_SIGN = "1";


    /**
     * 各科室展示的最大图片数,如果不设置就展示全部
     */
    public final static String DEPT_MAX_IMAGE = "6";


    /**
     * 老pacs地址
     */
    public final static String OLDPACS = "\\XXX.XXX.XXX.XXX\\image$\\=S\\";

    /**
     * 配置审核者用户名
     * doctor_type=1双签名时，
     * 职业报告中，除检验科以外，审核者都使用auditor
     * 当科室检查者的用户名也是auditor时，审核者不使用auditor，而使用auditor2
     * 必须两个auditor都配置才生效
     */
    public final static String AUDITOR = "";
    public final static String AUDITOR2 = "";


    /**
     * 是否需要用doc_cofig.properties中配置的oldpacs替换PACS类型的图片地址
     */
    public final static String ISNEEDREPLACE = "1";


    /**
     * 检验报告页脚,-1删除页脚，默认：  本次检验结果获山东省通用认证(“一单通”)。
     */
    public final static String INSPECTION_FOOTER = "";


    /**
     * 是否隐藏ct科室的图片  默认不隐藏  1隐藏0不隐藏
     */
    public final static String CT_IMG = "1";


    /**
     * 是否隐藏ct科室的图片  默认不隐藏  1隐藏0不隐藏
     */
    public final static String END_TITLE = "1";


    /**
     * ZZZS
     */
    public final static String ZZZS = "鲁卫职查体证字[ 2016]第096号";


    /**
     * 公司名称
     */
    public final static String orgName = "青岛沃德国际医疗健康产业股份有限公司综合门诊部";


    /**
     * 电话
     */
    public final static String fax = "0532-86898662";

    /**
     * 邮箱
     */
    public final static String email = "zhongkangtj@sina.com";


    public static String FINANCE_COUNT_TEMPLATE = "财务收费统计.xls";
    public static String FINANCE_COUNT_TOTAL_TEMPLATE = "财务收费汇总.xls";
    public static String PERSONAL_TOTAL_TEMPLATE = "个检销售统计.xls";
    public static String PERSONAL_TOTAL_INFO_TEMPLATE = "个检销售统计明细.xls";
    public static String SELL_GROUP_TEMPLATE = "销售团检统计.xls";
    public static String ASSOCIATE_TEMPLATE = "报告交接统计.xls";
    /**
     * 应急导引单健康模板.xls
     */
    public static String GUIDANCE_LIST_HEALTH = "/images/common/302023/06/f9e56d8ec88040a0a24b7407e12a6b53.xls";
    /**
     * 应急导引单职业模板.xls
     */
    public static String GUIDANCE_LIST_DISEASE = "/images/common/302023/06/62b7a2d5635d411b8f44ae3bea06da19.xls";

    public final static String TECH = "[{id:'T1WI T2WI FLAIR',text:'T1WI T2WI FLAIR'},{id:'DWI',text:'DWI'},{id:'平扫',text:'平扫'}]";

    /**
     * 体检者导入默认图片
     */
    public final static String patientImg = "";


    /**
     * questions.properties
     */
    public final static Map<String, String> question = new HashMap() {
        {
            put("1:1.高血压", "787,449,431,615,370");
            put("1:2.糖尿病（或疑似）", "409,615,370,384,438");
        }
    };


    /**
     * 数据迁移模块url
     */
    public final static String dataMoveUrl = "http://127.0.0.1:8081/open/api/datamove/importData";
//    public final static String dataMoveUrl = "http://localhost:8090/open/api/datamove/importData";


    /**
     * 报告中不需要出现艾迪康的检查项目
     */
    public final static List<String> NOT_APPEARING_REPORT = new ArrayList<String>() {
        private static final long serialVersionUID = 1312117049265497168L;

        {
            add("61");//血常规23项
            add("Z020");//血常规-职业
            add("1093");//肝功能(健康证)
            add("Z022");//肝功能5项职业
            add("1183");//肝功14项
            add("402848e364a06e190164a10fe6c50769");//肝功3项
            add("407");//肝功7项
            add("65");//肝功13项
            add("689");//肝功2项
            add("690");//肝功4项
            add("731");//肝功10项
            add("768");//肝功6项
            add("1001");//生化34项
            add("669");//乙肝五项定性
            add("ff808081851984550186b5e5499a6bdc");//瑞源入职乙肝五项定性
            add("1006");//人类免疫缺陷病毒抗体(3工作日出结果)
            add("402848e36abe7ddd016ad96af1255895");//总前列腺特异性抗原（TPSA）（化学发光法）
            add("565");//总前列腺特异性抗原(TPSA)（电化学发光法）
            add("ff80808171a12ba50171ab4c1737467e");//总前列腺特异性抗原(TPSA)（电化学发光法)
            add("ff8080817c6cde42017c9ccf830c46dc");//总前列腺特异性抗原(TPSA)（电化学发光法)
            add("582");//游离前列腺特异抗原（电化学发光法）
            add("563");//甲胎蛋白(电发光法)
            add("964");//甲胎蛋白(化学发光法)
            add("ff808081807483e301809d76da473cf2");//甲胎蛋白(AFP)福利
            add("564");//癌胚抗原(电发光法)
            add("965");//癌胚抗原(化学发光法)
            add("ff808081807483e301809d7aa8c83dae");//癌胚抗原（CEA)福利
            add("1178");//糖化血红蛋白（液相色谱）
            add("402848e361fe195e0162002673e507bd");//糖化血清蛋白(果糖胺)(3工作日后出结果)
            add("358");//尿酸
            add("409");//血脂7项
            add("66");//血脂4项
            add("693");//血脂2项
            add("694");//血脂6项
            add("Z054");//血脂四项职业
            add("68");//肾功3项
            add("691");//肾功2项
            add("692");//肾功4项
            add("Z023");//肾功能(职业)
            add("410");//心肌酶谱
            add("ff80808173be52c60173d64ca01d0e43");//BZ-心肌酶谱
            add("464");//空腹血糖
            add("402848e36abe7ddd016ad97ef5765a99");//细胞角蛋白19片断（Cyfra21-1)(化学发光法）
            add("634");//细胞角蛋白19片断(Cyfra21-1)(电化学发光法）
            add("702");//补体C3测定(3工作日后出结果)
            add("703");//补体C4测定(3工作日后出结果)
            add("402848e36abe7ddd016ad9733dbf593e");//糖类抗原（CA19-9）（化学发光法）
            add("866");//尿微量白蛋白
            add("569");//糖类抗原(CA19-9)（电化学发光法）

        }
    };


    public final static String ADVANCE_NOTICE_VIP_TEMPLATE = "1740618145611386880";//VIP检前通知模板
    public final static String ADVANCE_NOTICE_ORDINARY_TEMPLATE = "1740618375496994816";//普通会员检前通知模板

    public final static String OLD_FAMILYCARD_URL = "http://XXX.XXX.XXX.XXX:8086/mec/inter/card_ex!"; //老系统家庭卡url
    public final static String OLD_FAMILYCARD_QUERY = "getFamilyCardData.action"; //家庭卡信息查询
    public final static String OLD_FAMILYCARD_CONSUMPTION = "familyExpense.action"; //家庭卡消费
    public final static String OTHER_IS_REGISTERED = "/open/api/syncData/otherIsRegistered"; //别的区是否登记

}
