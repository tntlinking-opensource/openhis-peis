package com.center.medical.center.deploy.bean.param;

import lombok.Data;
/**
 * 自动部署-保存更新记录
 * @author xhp
 * @since 2023-11-15 14:31
 */
@Data
public class AutoDeploySaveRecordParam {
    //"是否更新成功 0失败 1成功",required = true)
    private Integer isSuccess;

    //"sys_deploy_version.id",required = true)
    private Integer versionId;

    //"md_ks_ip.id",required = true)
    private String ksIpId;

    //"错误信息")
    private String message;

    //"ip")
    private String address;

    //"主机名")
    private String hostName;
}
