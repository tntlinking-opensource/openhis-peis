package com.center.medical.common.constant;

import java.util.HashMap;
import java.util.Map;


/**
 * 报告常量
 */
public class ReportConstants {

    /**
     * 尾页总结报告标题 默认：青岛沃德国际体检中心职业体检总结报告
     */
    public final static String END_TITLE = "";

    /**
     * 尾页总结报告正文体检中心名称 默认：沃德国际体检中心
     */
    public final static String END_ORG = "";

    /**
     * 职业尾logo
     */
    public final static String diseaseEndLogo = "file\\wordmodel\\ks-i\\diseaseEnd3_logo.png";

    /**
     * 职业尾二维码
     */
    public final static String diseaseEndQrcode = "file\\wordmodel\\ks-i\\diseaseEnd3_qrcode.png";

    /**
     * 职业报告配置
     * 是否启用这个配置，1启用，如果启用优先读取这个配置文件，如果不启用
     * ，就用doc_config.properties和分中心维护的数据
     */
    public final static String is_open = "0";
    //名称
    public final static String org_orgName = "";
    //地址
    public final static String org_address = "";
    //电话
    public final static String org_phone = "";
    //电子邮箱
    public final static String org_email = "";
    //邮编
    public final static String org_zipcode = "";
    //传真
    public final static String org_fax = "";
    //资质证书号
    public final static String org_zzzs = "";


    /**
     * 是否展示尾页沃德logo 0：不展示 其他任何值：展示
     */
    public final static String end_logo = "0";


    /**
     * 是否展示尾页中的二维码  0：不展示 其他任何值：展示
     */
    public final static String end_qrcode = "0";


    /**
     * 隐藏非vip健康报告尾页 1隐藏  默认不隐藏
     */
    public final static String hide_health_common_end_page = "0";


    /**
     * 健康尾logo及二维码
     */
    public final static String healthEndLogo = "file\\\\wordmodel\\\\ks-i\\\\healthEnd_logo.png";
    public final static String healthEndQrcode = "file\\\\wordmodel\\\\ks-i\\\\healthEnd_qrcode.png";


    /**
     * ZK 检字（xxxx）第 xxxxx 号
     */
    public final static String zk_inspection = "2021";


    /**
     * 导引单样式
     */
    public final static String DYDSTYLE = "1";


    /**
     * 排队激活后，打印单查询二维码，需要根据各中心自身进行配置。以下的URL为开发区中心排队查询url
     */
    public final static String QUEUE_URL = "http://XXX.XXX.XXX.XXX:8081/pdcx/pd_queue_detail!list.action";


    /**
     * 打印通用条码数量1 是3个 2是6个 3是9个（3排）
     */
    public final static String BARCODECOUNT = "1";


    /**
     * 登记员+收费员 每台自助机可以配置不同的账号
     */
    public final static String CHARGE_ID = "syy2";


    /**
     * 机器id
     */
    public final static String MACHINE_ID = "syy2";


    /**
     * 关闭重发
     */
    public final static String CLOSE_CHONGFA = "";


    /**
     * lis系统ip
     */
    public final static String LIS_IP = "http\\://XXX.XXX.XXX.XXX\\:8098/lisPac/";


    /**
     * QUEUE
     */
    public final static String QUEUE = "http\\://XXX.XXX.XXX.XXX\\:8084/pdcx/pd_queue_detail\\!list.action?patientId\\=";


    /**
     * 条码：
     * vertical 一行一个  按收费项目的条码打印分类打印;
     * verticalStandard 一行一个 标准条码；
     * 其他 一行三个，标准条码；
     * verticalYanTai 烟台地区使用的调码
     */
    public final static String ORIENTATION = "";


    /**
     * 条码
     */
    public final static String TM = "\\XXX.XXX.XXX.XXX\\Bar Code Printer T-4503E,pdfFactory Pro";


    /**
     * 导引单
     */
    public final static String DYD = "\\XXX.XXX.XXX.XXX\\HP LaserJet 1020,Microsoft XPS Document Writer,pdfFactory Pro";


    /**
     * 条码
     */
    public final static String JFD = "Microsoft XPS Document Writer,pdfFactory Pro";

    /**
     * 0-3\u4E2A  1-6\u4E2A  2-9\u4E2A
     */
    public final static String BARCOUNT = "1";


    /**
     * 是否隐藏体检流程，1隐藏，默认显示 。简单导引单不显示流程。
     */
    public final static String hideProcess = "0";

    /**
     * 底部流程前，下载app的二维码图片路径
     */
    public final static String qrCodePath = "http://localhost:8080/template/image/appQrCode.png";


    /**
     * 烟台医星商城二维码图片生成路径
     */
    public final static String yxCodePath = "http://localhost:8080/template/image/menu_yxcode.png";


    /**
     * 烟台底部修饰图片
     */
    public final static String dbCodePath = "http://localhost:8080/template/image/menu_dbcode.png";


    /**
     * 烟台体检预约电话
     */
    public final static String YTYYPHONE = "0535-6676888";

    /**
     * 烟台报告解读电话
     */
    public final static String YTBGPHONE = "0535-6016120";

    /**
     * 烟台投诉电话
     */
    public final static String YTTSPHONE = "0535-6018120";


    /**
     * 条码可选数量 默认 3,6,9  表示可以选择3个或6个或9个
     */
    public final static String barcodeCount = "3,6,9,12,15";

    /**
     * 条码
     */
    public final static Map<String, String> barcode = new HashMap() {
        {
            put("patientname_top1", "2mm");
            put("patientname_left1", "2mm");
            put("patientname_width1", "");
            put("patientname_height1", "4mm");
            put("sex_top1", "2mm");
            put("sex_left1", "18mm");
            put("sex_width1", "6mm");
            put("sex_height1", "4mm");
            put("age_top1", "2mm");
            put("age_left1", "25mm");
            put("age_width1", "8mm");
            put("age_height1", "4mm");
            put("barcode_top1", "6mm");
            put("barcode_left1", "2mm");
            put("barcode_width1", "27mm");
            put("barcode_height1", "7mm");
            put("barcode_font1", "128C");
            put("date_top1", "16.5mm");
            put("date_left1", "2mm");
            put("date_width1", "30mm");
            put("date_height1", "4mm");
            put("intId_top1", "13.5mm");
            put("intId_left1", "0mm");
            put("intId_width1", "30mm");
            put("intId_height1", "4mm");
            put("intId_fontSize1", "10");
            put("patientname_top2", "2mm");
            put("patientname_left2", "36mm");
            put("patientname_width2", "");
            put("patientname_height2", "4mm");
            put("sex_top2", "2mm");
            put("sex_left2", "52mm");
            put("sex_width2", "6mm");
            put("sex_height2", "4mm");
            put("age_top2", "2mm");
            put("age_left2", "59mm");
            put("age_width2", "8mm");
            put("age_height2", "4mm");
            put("barcode_top2", "6mm");
            put("barcode_left2", "36mm");
            put("barcode_width2", "27mm");
            put("barcode_height2", "7mm");
            put("barcode_font2", "128C");
            put("date_top2", "16.5mm");
            put("date_left2", "36mm");
            put("date_width2", "30mm");
            put("date_height2", "4mm");
            put("intId_top2", "13.5mm");
            put("intId_left2", "34mm");
            put("intId_width2", "30mm");
            put("intId_height2", "4mm");
            put("intId_fontSize2", "10");
            put("patientname_top3", "2mm");
            put("patientname_left3", "70mm");
            put("patientname_width3", "");
            put("patientname_height3", "4mm");
            put("sex_top3", "2mm");
            put("sex_left3", "86mm");
            put("sex_width3", "6mm");
            put("sex_height3", "4mm");
            put("age_top3", "2mm");
            put("age_left3", "93mm");
            put("age_width3", "8mm");
            put("age_height3", "4mm");
            put("barcode_top3", "6mm");
            put("barcode_left3", "70mm");
            put("barcode_width3", "27mm");
            put("barcode_height3", "7mm");
            put("barcode_font3", "128C");
            put("date_top3", "16.5mm");
            put("date_left3", "70mm");
            put("date_width3", "30mm");
            put("date_height3", "4mm");
            put("intId_top3", "13.5mm");
            put("intId_left3", "68mm");
            put("intId_width3", "30mm");
            put("intId_height3", "4mm");
            put("intId_fontSize3", "10");
            put("patientname_topv", "0mm");
            put("patientname_leftv", "30mm");
            put("patientname_widthv", "");
            put("patientname_heightv", "4mm");
            put("sex_topv", "0mm");
            put("sex_leftv", "42mm");
            put("sex_widthv", "6mm");
            put("sex_heightv", "4mm");
            put("age_topv", "0mm");
            put("age_leftv", "46.5mm");
            put("age_widthv", "8mm");
            put("age_heightv", "4mm");
            put("item_topv", "0mm");
            put("item_leftv", "51mm");
            put("item_widthv", "15mm");
            put("item_heightv", "4mm");
            put("barcode_topv", "4mm");
            put("barcode_leftv", "30mm");
            put("barcode_widthv", "27mm");
            put("barcode_heightv", "7mm");
            put("barcode_fontv", "128C");
            put("barcodename_topv", "14mm");
            put("barcodename_leftv", "30mm");
            put("barcodename_widthv", "30mm");
            put("barcodename_heightv", "4mm");
            put("patientcode_topv", "11mm");
            put("patientcode_leftv", "30mm");
            put("patientcode_widthv", "30mm");
            put("patientcode_heightv", "4mm");
            put("class_topv", "0mm");
            put("class_leftv", "60mm");
            put("class_widthv", "4mm");
            put("class_heightv", "20mm");
        }
    };


    /**
     * 当存在复查项目时，职业总检-最终结论建议 的最后一句，如果不配置，默认是：请您于收到此报告后一个月内进行复查。
     */
    public final static String REVIEWTIPS = "请您于收到此报告后15天内进行复查。";



    /**
     * 分享报告地址
     */
    public final static String REPORTSHAREPATH = "http://report.world.com/preview/report_share?";




    /**
     * 淮南条码
     */
    public final static Map<String, String> hnBarcode = new HashMap() {
        {
            put("patientname_top1", "0.5mm");
            put("patientname_left1", "6mm");
            put("patientname_width1", "");
            put("patientname_height1", "4mm");
            put("sex_top1", "0.5mm");
            put("sex_left1", "16mm");
            put("sex_width1", "4mm");
            put("sex_height1", "4mm");
            put("age_top1", "0.5mm");
            put("age_left1", "20mm");
            put("age_width1", "7mm");
            put("age_height1", "4mm");
            put("note_top1", "0.5mm");
            put("note_width1", "10mm");
            put("note_left1", "26mm");
            put("note_height1", "4mm");
            put("barcode_top1", "4.5mm");
            put("barcode_left1", "8mm");
            put("barcode_width1", "27mm");
            put("barcode_height1", "7mm");
            put("barcode_font1", "128C");
            put("date_top1", "15.5mm");
            put("date_left1", "3mm");
            put("date_width1", "30mm");
            put("date_height1", "4mm");
            put("intId_top1", "11.5mm");
            put("intId_left1", "3mm");
            put("intId_width1", "30mm");
            put("intId_height1", "4mm");
            put("intId_fontSize1", "10");
            put("patientname_top2", "0.5mm");
            put("patientname_left2", "38mm");
            put("patientname_width2", "");
            put("patientname_height2", "4mm");
            put("sex_top2", "0.5mm");
            put("sex_left2", "48mm");
            put("sex_width2", "4mm");
            put("sex_height2", "4mm");
            put("age_top2", "0.5mm");
            put("age_left2", "52mm");
            put("age_width2", "7mm");
            put("age_height2", "4mm");
            put("note_top2", "0.5mm");
            put("note_left2", "58mm");
            put("note_width2", "10mm");
            put("note_height2", "4mm");
            put("barcode_top2", "4.5mm");
            put("barcode_left2", "40mm");
            put("barcode_width2", "27mm");
            put("barcode_height2", "7mm");
            put("barcode_font2", "128C");
            put("date_top2", "15.5mm");
            put("date_left2", "34mm");
            put("date_width2", "30mm");
            put("date_height2", "4mm");
            put("intId_top2", "11.5mm");
            put("intId_left2", "34mm");
            put("intId_width2", "30mm");
            put("intId_height2", "4mm");
            put("intId_fontSize2", "10");
            put("patientname_top3", "0.5mm");
            put("patientname_left3", "68mm");
            put("patientname_width3", "");
            put("patientname_height3", "4mm");
            put("sex_top3", "0.5mm");
            put("sex_left3", "79mm");
            put("sex_width3", "4mm");
            put("sex_height3", "4mm");
            put("age_top3", "0.5mm");
            put("age_left3", "83mm");
            put("age_width3", "7mm");
            put("age_height3", "4mm");
            put("note_top3", "0.5mm");
            put("note_left3", "89mm");
            put("note_width3", "10mm");
            put("note_height3", "4mm");
            put("barcode_top3", "4.5mm");
            put("barcode_left3", "72mm");
            put("barcode_width3", "27mm");
            put("barcode_height3", "7mm");
            put("barcode_font3", "128C");
            put("date_top3", "15.5mm");
            put("date_left3", "68mm");
            put("date_width3", "30mm");
            put("date_height3", "4mm");
            put("intId_top3", "11.5mm");
            put("intId_left3", "68mm");
            put("intId_width3", "30mm");
            put("intId_height3", "4mm");
            put("intId_fontSize3", "10");
            put("patientname_topv", "0mm");
            put("patientname_leftv", "30mm");
            put("patientname_widthv", "");
            put("patientname_heightv", "4mm");
            put("sex_topv", "0mm");
            put("sex_leftv", "42mm");
            put("sex_widthv", "6mm");
            put("sex_heightv", "4mm");
            put("age_topv", "0mm");
            put("age_leftv", "46.5mm");
            put("age_widthv", "8mm");
            put("age_heightv", "4mm");
            put("item_topv", "0mm");
            put("item_leftv", "51mm");
            put("item_widthv", "15mm");
            put("item_heightv", "4mm");
            put("barcode_topv", "4mm");
            put("barcode_leftv", "30mm");
            put("barcode_widthv", "27mm");
            put("barcode_heightv", "7mm");
            put("barcode_fontv", "128C");
            put("barcodename_topv", "14mm");
            put("barcodename_leftv", "30mm");
            put("barcodename_widthv", "30mm");
            put("barcodename_heightv", "4mm");
            put("patientcode_topv", "11mm");
            put("patientcode_leftv", "30mm");
            put("patientcode_widthv", "30mm");
            put("patientcode_heightv", "4mm");
            put("class_topv", "0mm");
            put("class_leftv", "60mm");
            put("class_widthv", "4mm");
            put("class_heightv", "20mm");
        }
    };


    public final static Map<String, String> wfBarcode = new HashMap() {
        {
            put("patientname_top1", "1mm");
            put("patientname_left1", "2mm");
            put("patientname_width1", "");
            put("patientname_height1", "4mm");
            put("sex_top1", "1mm");
            put("sex_left1", "18mm");
            put("sex_width1", "6mm");
            put("sex_height1", "4mm");
            put("age_top1", "1mm");
            put("age_left1", "25mm");
            put("age_width1", "8mm");
            put("age_height1", "4mm");
            put("barcode_top1", "5mm");
            put("barcode_left1", "2mm");
            put("barcode_width1", "32mm");
            put("barcode_height1", "5mm");
            put("barcode_font1", "128C");
            put("date_top1", "13mm");
            put("date_left1", "2mm");
            put("date_width1", "30mm");
            put("date_height1", "4mm");
            put("intId_top1", "10mm");
            put("intId_left1", "0mm");
            put("intId_width1", "30mm");
            put("intId_height1", "4mm");
            put("intId_fontSize1", "10");
            put("patientname_top2", "1mm");
            put("patientname_left2", "36mm");
            put("patientname_width2", "");
            put("patientname_height2", "4mm");
            put("sex_top2", "1mm");
            put("sex_left2", "52mm");
            put("sex_width2", "6mm");
            put("sex_height2", "4mm");
            put("age_top2", "1mm");
            put("age_left2", "59mm");
            put("age_width2", "8mm");
            put("age_height2", "4mm");
            put("barcode_top2", "5mm");
            put("barcode_left2", "36mm");
            put("barcode_width2", "32mm");
            put("barcode_height2", "5mm");
            put("barcode_font2", "128C");
            put("date_top2", "13mm");
            put("date_left2", "36mm");
            put("date_width2", "30mm");
            put("date_height2", "4mm");
            put("intId_top2", "10mm");
            put("intId_left2", "34mm");
            put("intId_width2", "30mm");
            put("intId_height2", "4mm");
            put("intId_fontSize2", "10");
            put("patientname_top3", "1mm");
            put("patientname_left3", "70mm");
            put("patientname_width3", "");
            put("patientname_height3", "4mm");
            put("sex_top3", "1mm");
            put("sex_left3", "86mm");
            put("sex_width3", "6mm");
            put("sex_height3", "4mm");
            put("age_top3", "1mm");
            put("age_left3", "93mm");
            put("age_width3", "8mm");
            put("age_height3", "4mm");
            put("barcode_top3", "5mm");
            put("barcode_left3", "70mm");
            put("barcode_width3", "32mm");
            put("barcode_height3", "5mm");
            put("barcode_font3", "128C");
            put("date_top3", "13mm");
            put("date_left3", "70mm");
            put("date_width3", "30mm");
            put("date_height3", "4mm");
            put("intId_top3", "10mm");
            put("intId_left3", "68mm");
            put("intId_width3", "30mm");
            put("intId_height3", "4mm");
            put("intId_fontSize3", "10");
            put("patientname_topv", "0mm");
            put("patientname_leftv", "30mm");
            put("patientname_widthv", "");
            put("patientname_heightv", "4mm");
            put("sex_topv", "0mm");
            put("sex_leftv", "42mm");
            put("sex_widthv", "6mm");
            put("sex_heightv", "4mm");
            put("age_topv", "0mm");
            put("age_leftv", "46.5mm");
            put("age_widthv", "8mm");
            put("age_heightv", "4mm");
            put("item_topv", "0mm");
            put("item_leftv", "51mm");
            put("item_widthv", "15mm");
            put("item_heightv", "4mm");
            put("barcode_topv", "4mm");
            put("barcode_leftv", "30mm");
            put("barcode_widthv", "27mm");
            put("barcode_heightv", "7mm");
            put("barcode_fontv", "128C");
            put("barcodename_topv", "14mm");
            put("barcodename_leftv", "30mm");
            put("barcodename_widthv", "30mm");
            put("barcodename_heightv", "4mm");
            put("patientcode_topv", "11mm");
            put("patientcode_leftv", "30mm");
            put("patientcode_widthv", "30mm");
            put("patientcode_heightv", "4mm");
            put("class_topv", "0mm");
            put("class_leftv", "60mm");
            put("class_widthv", "4mm");
            put("class_heightv", "20mm");
        }
    };
}



