package com.center.medical.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 读取项目相关配置
 *
 * @author 路飞
 */
@Component
@ConfigurationProperties(prefix = "zhongkang")
public class ZhongkangConfig {
    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private static String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 实例演示开关
     */
    private boolean demoEnabled;

    /**
     * 分中心简码
     */
    private static String fzxjm;

    /**
     * 分中心ID
     */
    private static String fzxId;

    /**
     * 是否线上系统
     */
    private static boolean online;

    /**
     * 上传路径
     */
    private static String profile;

    /**
     * 上传路径
     */
    private static String profileLinux;

    /**
     * 获取地址开关
     */
    private static boolean addressEnabled;

    /**
     * 验证码类型
     */
    private static String captchaType;

    public static String getFzxjm() {
        return fzxjm;
    }

    public void setFzxjm(String fzxjm) {
        this.fzxjm = fzxjm;
    }

    public void setFzxId(String fzxId) {
        this.fzxId = fzxId;
    }

    public static String getFzxId() {
        return fzxId;
    }

    public static boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled() {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled) {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        ZhongkangConfig.profile = profile;
    }

    public static String getProfileLinux() {
        return profileLinux;
    }

    public void setProfileLinux(String profileLinux) {
        ZhongkangConfig.profileLinux = profileLinux;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled) {
        ZhongkangConfig.addressEnabled = addressEnabled;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        ZhongkangConfig.captchaType = captchaType;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 基础数据图片上传路径
     */
    public static String getDataPath() {
        return getProfile() + "/data";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/";
    }

    /**
     * 获取上传路径
     */
    public static String getSellCrmFilePath() {
        return getProfile() + File.separator + "file" + File.separator + "sell" + File.separator + "customer";
    }


    /**
     * 外送系统图片上传路径
     */
    public static String getSendDataPath() {
        return getProfile() + "/image/wsxm/";
    }
}
