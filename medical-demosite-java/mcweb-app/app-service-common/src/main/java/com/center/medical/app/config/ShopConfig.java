package com.center.medical.app.config;


import cn.hutool.core.util.StrUtil;
import com.center.medical.app.common.bean.*;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商城配置文件
 * 支付配置、文件上传配置、短信配置、快递配置、小程序配置、微信网页开发配置、公众号配置
 *
 * @author yami
 */
@Component
public class ShopConfig {

    @Autowired
    private SysConfigService sysConfigService;

    public Qiniu getQiniu() {
        // 从数据库 / 缓存中获取到配置文件信息
        return sysConfigService.getSysConfigObject(Constant.QINIU_CONFIG, Qiniu.class);
    }

    public AliOss getAliOss() {
        return sysConfigService.getSysConfigObject(Constant.ALI_OSS_CONFIG, AliOss.class);
    }

    public QCloud getQCloud() {
        return sysConfigService.getSysConfigObject(Constant.Q_CLOUD_CONFIG, QCloud.class);
    }

    public HuaWeiOss getHuaWeiObs() {
        return sysConfigService.getSysConfigObject(Constant.HUAWEI_OBS_CONFIG, HuaWeiOss.class);
    }

    public Minio getMinio() {
        return sysConfigService.getSysConfigObject(Constant.MINIO_OSS_CONFIG, Minio.class);
    }

    public ALiDaYu getAliDaYu() {
        ALiDaYu aLiDaYu = sysConfigService.getSysConfigObject(Constant.ALIDAYU_CONFIG, ALiDaYu.class);
        if (aLiDaYu == null || StrUtil.isBlank(aLiDaYu.getAccessKeyId())) {
            // 无法获取短信配置，无法发送短信
            throw new AppBindException("无法获取短信配置，无法发送短信");
        }
        return aLiDaYu;
    }


    public QuickBird getQuickBird() {
        return sysConfigService.getSysConfigObject(Constant.QUICKBIRD_CONFIG, QuickBird.class);
    }

    public Quick100 getQuick100() {
        return sysConfigService.getSysConfigObject(Constant.QUICK100_CONFIG, Quick100.class);
    }

    public QuickAli getAliQuick() {
        return sysConfigService.getSysConfigObject(Constant.ALI_QUICK_CONFIG, QuickAli.class);
    }


    public WxMiniApp getWxMiniApp() {
        WxMiniApp wxMiniApp = sysConfigService.getSysConfigObject(Constant.MA_CONFIG, WxMiniApp.class);
        if (wxMiniApp == null || StrUtil.isBlank(wxMiniApp.getAppId())) {
            // 请在后台页面设置微信小程序信息后，再进行操作
            throw new AppBindException("请在后台页面设置微信小程序信息后，再进行操作");
        }
        return wxMiniApp;
    }

    public WxMp getWxMp() {
        WxMp wxMp = sysConfigService.getSysConfigObject(Constant.MP_CONFIG, WxMp.class);
        if (wxMp == null || StrUtil.isBlank(wxMp.getAppId())) {
            // 请在后台页面设置微信公众号信息后，再进行操作
            throw new AppBindException("请在后台页面设置微信公众号信息后，再进行操作");
        }
        return wxMp;
    }

    public WxApp getWxApp() {
        WxApp wxApp = sysConfigService.getSysConfigObject(Constant.MX_APP_CONFIG, WxApp.class);
        if (wxApp == null || StrUtil.isBlank(wxApp.getAppId())) {
            // 请在后台页面设置微信开放平申请应用相关信息后，再进行操作
            throw new AppBindException("请在后台页面设置微信开放平申请应用相关信息后，再进行操作");
        }
        return wxApp;
    }

    public WxPay getWxPay() {
        WxPay wxPay = sysConfigService.getSysConfigObject(Constant.WXPAY_CONFIG, WxPay.class);
        if (wxPay == null || StrUtil.isBlank(wxPay.getMchId())) {
            // 请在后台页面设置微信支付信息后，再进行操作
            throw new AppBindException("请在后台页面设置微信支付信息后，再进行操作");
        }
        return wxPay;
    }

    public Alipay getAlipay() {

        return sysConfigService.getSysConfigObject(Constant.ALIPAY_CONFIG, Alipay.class);
    }

    public Domain getDomain() {
        Domain domain = sysConfigService.getSysConfigObject(Constant.DOMAIN_CONFIG, Domain.class);
        if (domain == null || StrUtil.isBlank(domain.getMdPlatformOutreach())) {
            // 请在后台页面设置回调域名后，再进行操作
            throw new AppBindException("请在后台页面设置回调域名后，再进行操作");
        }
        return domain;
    }

    /**
     * 获取报告加密使用的授权码，公钥私钥
     *
     * @return
     */
    public ReportConfig getReportConfig() {
        ReportConfig reportConfig = sysConfigService.getSysConfigObject(Constant.REPORT_CONFIG, ReportConfig.class);
        if (reportConfig == null || StrUtil.isBlank(reportConfig.getAuthCode())) {
            // 请在后台页面设置回调域名后，再进行操作
            throw new AppBindException("请在后台页面设置报告后，再进行操作");
        }
        return reportConfig;
    }

    public String getZkReservationAesKey() {
        String result = sysConfigService.getValue(Constant.ZK_RESERVATION_AES_KEY);
        if (StrUtil.isBlank(result)) {
            throw new AppBindException("获取沃德体检系统AES加密数据失败！");
        }
        return result;
    }

    public String getZkReservationRsaKey() {
        String result = sysConfigService.getValue(Constant.ZK_RESERVATION_RSA_KEY);
        if (StrUtil.isBlank(result)) {
            throw new AppBindException("获取沃德体检系统RSA加密数据失败！");
        }
        return result;
    }

    public String getZkReservationAuthcode() {
        String result = sysConfigService.getValue(Constant.ZK_RESERVATION_AUTHCODE);
        if (StrUtil.isBlank(result)) {
            throw new AppBindException("获取沃德体检系统授权码失败！");
        }
        return result;
    }


    public String getZkSystemFlag() {
        String result = sysConfigService.getValue(Constant.ZK_SYSTEM_FLAG);
        if (StrUtil.isBlank(result)) {
            throw new AppBindException("获取沃德体检系统标识码失败！");
        }
        return result;
    }

}
