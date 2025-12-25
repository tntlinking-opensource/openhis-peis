package com.center.medical.bean.enums;

import cn.hutool.json.JSONUtil;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 文件类型：1.图片 2.视频 3.文件
 */
public enum AttachmentType {

    /**
     * 图片
     */
    IMAGE(1, "图片"),

    /**
     * 视频
     */
    VIDEO(2, "视频"),

    /**
     * 文件
     */
    FILE(3, "文件");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    public String gName() {
        return name;
    }

    public static String getName(Integer value) {
        AttachmentType[] enums = values();
        for (AttachmentType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }


    public static Integer getValueByName(String name) {
        AttachmentType[] enums = values();
        System.out.println("类型列表：" + JSONUtil.toJsonStr(enums));
        for (AttachmentType enmu : enums) {
            if (enmu.gName().equals(name)) {
                return enmu.value();
            }
        }
        return null;
    }

    AttachmentType(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static AttachmentType instance(Integer value) {
        AttachmentType[] enums = values();
        for (AttachmentType statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

}
