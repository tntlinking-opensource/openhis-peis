package com.center.medical.system.config;


import cn.hutool.core.util.StrUtil;
import com.center.medical.bean.dto.WxApp;
import com.center.medical.bean.dto.WxMiniApp;
import com.center.medical.bean.dto.WxMp;
import com.center.medical.bean.dto.WxPay;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.config.*;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: 路飞
 * @date: 2022/7/4 15:57
 * @description: 系统配置文件：支付配置、文件上传配置、短信配置、快递配置、小程序配置、微信网页开发配置、公众号配置
 */
@Slf4j
@Component
@AllArgsConstructor
public class SystemConfig {

    private final ISysConfigService iSysConfigService;
    private final ISysBranchService iSysBranchService;

    public Boolean needReservation() {
        SysBranch branch = iSysBranchService.getDefaultBranch();
        return Objects.nonNull(branch.getNeedReservation()) && branch.getNeedReservation() == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    public WxMiniApp getWxMiniApp() {
        WxMiniApp wxMiniApp = iSysConfigService.getSysConfigObject(Constants.MA_CONFIG, WxMiniApp.class);
        if (wxMiniApp == null || StrUtil.isBlank(wxMiniApp.getAppId())) {
            // 请在后台页面设置微信小程序信息后，再进行操作
            throw new ServiceException("获取微信小程序信息失败！");
        }
        return wxMiniApp;
    }

    public WxMp getWxMp() {
        WxMp wxMp = iSysConfigService.getSysConfigObject(Constants.MP_CONFIG, WxMp.class);
        if (wxMp == null || StrUtil.isBlank(wxMp.getAppId())) {
            // 请在后台页面设置微信公众号信息后，再进行操作
            throw new ServiceException("获取微信公众号信息失败！");
        }
        return wxMp;
    }

    public WxApp getWxApp() {
        WxApp wxApp = iSysConfigService.getSysConfigObject(Constants.MX_APP_CONFIG, WxApp.class);
        if (wxApp == null || StrUtil.isBlank(wxApp.getAppId())) {
            // 请在后台页面设置微信开放平申请应用相关信息后，再进行操作
            throw new ServiceException("获取微信开放平台信息失败！");
        }
        return wxApp;
    }

    public WxPay getWxPay() {
        WxPay wxPay = iSysConfigService.getSysConfigObject(Constants.WXPAY_CONFIG, WxPay.class);
        if (wxPay == null || StrUtil.isBlank(wxPay.getMchId())) {
            // 请在后台页面设置微信支付信息后，再进行操作
            throw new ServiceException("获取微信支付信息失败！");
        }
        return wxPay;
    }

//    public Alipay getAlipay() {
//
//        return iSysConfigService.getSysConfigObject(Constants.ALIPAY_CONFIG, Alipay.class);
//    }

    public Domain getDomain() {
        Domain domain = iSysConfigService.getSysConfigObject(Constants.DOMAIN_CONFIG, Domain.class);
        if (Objects.isNull(domain)) {
            // 请在后台页面设置回调域名后，再进行操作
            throw new ServiceException("获取回调域名信息失败！");
        }
        return domain;
    }

    /**
     * 获取阿里云OSS配置信息
     *
     * @return
     */
    public AliOss getAliOss() {
        return iSysConfigService.getSysConfigObject(Constants.ALI_OSS_CONFIG, AliOss.class);
    }




    /**
     * 获取华为云OSS配置信息
     *
     * @return
     */
    public HuaWeiOss getHuaWeiObs() {
        return iSysConfigService.getSysConfigObject(Constants.HUAWEI_OBS_CONFIG, HuaWeiOss.class);
    }

    /**
     * 获取文件储存配置信息
     *
     * @param type 类型：1.公共图片路径 2.影像科室图片路径 3.体检者头像路径 4.材料文件路径
     * @return
     */
    public String getFilePathConfig(Integer type) {
        FilePathConfig filePathConfig = iSysConfigService.getSysConfigObject(Constants.FILE_PATH_CONFIG, FilePathConfig.class);
        String baseDir = "";
        switch (type) {
            case 1:
                baseDir = filePathConfig.getCommonPicturePath();
                break;
            case 2:
                baseDir = filePathConfig.getImagePicturePath();
                break;
            case 3:
                baseDir = filePathConfig.getAvatorPicturePath();
                break;
            case 4:
                baseDir = filePathConfig.getMaterialFilePath();
                break;
            case 5:
                baseDir = filePathConfig.getSendPicturePath();
                break;
            case 6:
                baseDir = filePathConfig.getPactFilePath();
                break;
            case 7:
                baseDir = filePathConfig.getRiLinFilePath();
                break;
            default:
                baseDir = filePathConfig.getCommonPicturePath();
        }
        String jm = iSysBranchService.getBranchPrefix();
        return StringUtils.endsWith(baseDir, "/") ? (baseDir + jm) : (baseDir + "/" + jm);
    }


    /**
     * 获取文件的完整地址
     *
     * @param path 文件路径
     * @return
     */
    public String getDownloadFileUrl(String path) {
        if (StringUtils.startsWith(path, "http")) {
            return path;
        }
        // 拼接完整路径
        Domain domain = getDomain();
        if (ZhongkangConfig.isOnline()) {
            //线上
            String rsPfDomain = domain.getRsPfDomain();
            return StringUtils.startsWith(path, "/") ? (rsPfDomain + path) : (rsPfDomain + "/" + path);
        }
        //线下
        String rsLcDomain = domain.getRsLcDomain();
        return StringUtils.startsWith(path, "/") ? (rsLcDomain + path) : (rsLcDomain + "/" + path);
    }

//    public static void main(String[] args) {
//        System.out.println(StringUtils.endsWith("dd/ddd/", "/"));
//    }
}
