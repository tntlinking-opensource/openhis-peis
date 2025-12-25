package com.center.medical.common.constant;

/**
 * @author xhp
 * @since 2023-11-23 9:30
 */
public class AutoDeployConstant {
    //获取最新版本接口地址
    public final static String GET_VERSION_PATH="/open/api/autoDeploy/version/get";
    //下载jar包接口地址
    public final static String DOWNLOAD_JAR_PATH="/open/api/autoDeploy/jar/download";
    //开始更新接口地址
    public final static String START_DEPLOY_PATH="/open/api/deploy/start";
    //保存更新结果接口地址
    public final static String SAVE_RECORD_PATH="/open/api/autoDeploy/record/save";
    //http请求超时时间
    public final static int HTTP_TIMEOUT=1000*10;
    //下载文件超时时间
    public final static int DOWNLOAD_TIMEOUT=1000*60*10;
}
