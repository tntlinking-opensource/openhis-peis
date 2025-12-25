package com.center.medical.common.constant;

/**
 * 自助登记机常量
 */
public class MachineConstants {


    /**
     * 身份证读卡器类型：华旭-HUAXU，神思-SHENSI，华视-HUASHI，神思SS728M05-SS728M05
     * 默认华视 -->
     * <card-reader-type>HUASHI</card-reader-type>
     * <card-reader-type>SS728M05</card-reader-type>
     */
    public final static String CARD_READER_TYPE = "SS728M05";


    /**
     * 身份证地址
     */
    public final static String IdCardPath = "C\\:/Tomcat 7.0/bin";


    /**
     * 卡片标记
     */
    public final static String cardmark = "1001";


    /**
     * 可以打印报告的科室id，逗号拼接
     */
    public final static String DEP_IDS = "19,143,173,171,24,402848e3625a920201625ff99a3404a5";


    /**
     * 排队管理配置文件,排队系统接口地址
     */
    public final static String interface_url = "http://XXX.XXX.XXX.XXX:8081/";


    /**
     * 本地导引单打印服务（可为原中心8089服务）
     */
    public final static String doc_url = "http\\://localhost\\:8080/component/";


    /**
     * 导引单样式
     */
    public final static String dydStyle = "1";


    /**
     * 打印通用条码数量1 是3个 2是6个 3是9个（3排）
     */
    public final static String barcodeCount = "3";

}
