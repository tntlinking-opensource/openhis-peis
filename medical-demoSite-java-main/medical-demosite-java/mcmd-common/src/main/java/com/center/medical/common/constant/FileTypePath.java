package com.center.medical.common.constant;

import java.io.File;

/**
 * 文件路径配置类
 *
 * @author 路飞
 */
public class FileTypePath {
    /**
     * 客户相关的文件路径
     */
    public static final String FILE_BASE_PATH = "D:\\local-git-repository\\customer\\zhongkang\\zhongkang-medical\\docs";

    /**
     * 客户相关的文件路径
     */
    public static final String SELL_CRM_PATH = "\\file" + File.separator + "sell" + File.separator + "customer";
    /**
     * #财务对接金蝶 获取等级在本中心组织下的客户单位
     */
    public static final String KING_DEE_URL = "XXX.XXX.XXX.XXX";

    /**
     * 总检结论词维护上传
     */
    public static final String NET_URL = "XXX.XXX.XXX.XXX";

    /**
     * 下载地址
     */
    public static final String REAL_PATH = "https://XXX.XXX.XXX.XXXm";

    /**
     * 创建ip
     */
    public static final String CREATE_IP = "//localhost\\:8083/medoc/";


    /**
     * 虚拟地址
     */
    public static final String FICTITIOUS_PATH = "http\\://localhost\\:8088/medical_war_exploded/mec";


    /**
     * 新pacs
     */
    public static final String NEWPACS = "\\XXX.XXX.XXX.XXX\\\\UserUpload\\=X\\";

    /**
     * 旧pacs
     */
    public static final String OLDPACS = "\\XXX.XXX.XXX.XXX\\image$\\=S\\";




    /**
     * 本地测试地址
     */
//    public static final String local = "\\www\\wwwroot\\java_8080\\temp\\";
    public static final String local = "\\www\\wwwroot\\java_8090\\temp\\";


}
