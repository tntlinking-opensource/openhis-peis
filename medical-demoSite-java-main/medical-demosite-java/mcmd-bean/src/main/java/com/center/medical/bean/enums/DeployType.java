package com.center.medical.bean.enums;

/**
 * 自动部署类型
 * @author xhp
 * @since 2023-11-15 9:42
 */
public enum DeployType {
    MCWEB_ADMIN(0,"线下主系统"),
    MCWEB_CENTER_COMPONENT(1,"分中心组件系统"),
    MCWEB_CENTER_ADMIN(2,"mcweb_center_admin")
    ;

    private Integer id;

    private String name;

    DeployType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public static DeployType instance(Integer id) {
        DeployType[] enums = values();
        for (DeployType statusEnum : enums) {
            if (statusEnum.getId()==id) {
                return statusEnum;
            }
        }
        return null;
    }
}
