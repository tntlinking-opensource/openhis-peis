package com.center.medical.bean.enums;

import cn.hutool.json.JSONUtil;

/**
 * @author: 路飞船长
 * @date: 2022/8/4 19:11
 * @description: 文件存储配置信息类型：1.公共图片路径 2.影像科室图片路径 3.体检者头像路径 4.材料文件路径
 */
public enum FilePathConfigFlag {

    /**
     * 公共图片路径：管理员用户头像、部门图片、
     */
    CPP(1, "公共图片路径"),

    /**
     * 影像科室图片路径
     */
    IPP(2, "影像科室图片路径"),

    /**
     * 体检者头像路径
     */
    APP(3, "体检者头像路径"),

    /**
     * 材料文件路径
     */
    MFP(4, "材料文件路径"),

    /**
     * 外送结果图片路径
     */
    SPP(5, "外送结果图片路径"),

    /**
     * 合同文件路径
     */
    PFP(6, "合同文件路径"),

    /**
     * 合同文件路径
     */
    RiLin(7, "瑞林萨尔报告存放路径");

    private Integer num;

    private String name;

    public Integer value() {
        return num;
    }

    public String gName() {
        return name;
    }

    FilePathConfigFlag(Integer num, String name) {
        this.num = num;
        this.name = name;
    }

    public static FilePathConfigFlag instance(Integer value) {
        FilePathConfigFlag[] enums = values();
        for (FilePathConfigFlag statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static String getName(Integer value) {
        FilePathConfigFlag[] enums = values();
        for (FilePathConfigFlag statusEnum : enums) {
            if (statusEnum.value().equals(value)) {
                return statusEnum.name;
            }
        }
        return null;
    }


    public static Integer getValueByName(String name) {
        FilePathConfigFlag[] enums = values();
        System.out.println("类型列表：" + JSONUtil.toJsonStr(enums));
        for (FilePathConfigFlag enmu : enums) {
            if (enmu.gName().equals(name)) {
                return enmu.value();
            }
        }
        return null;
    }

}
