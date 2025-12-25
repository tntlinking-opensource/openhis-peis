package com.center.medical.pacs.bean.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**pacs体检者收费项目查询结果
 * @author xhp
 * @since 2023-03-17 15:20
 */
public class PacsAbteilungItemListVo {
    @ApiModelProperty(value = "体检者收费项目id")
    private String id;
    @ApiModelProperty(value = "pacs基础收费项目id")
    private String itemId;
    @ApiModelProperty(value = "所有部位名称，逗号拼接")
    private String partName;
    @ApiModelProperty(value = "pacs基础收费项目名称")
    private String itemName;
    @ApiModelProperty(value = "0未检 1已检")
    private Integer fExamstarted;
    @ApiModelProperty(value = "收费时间")
    private Date chargeTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getfExamstarted() {
        return fExamstarted;
    }

    public void setfExamstarted(Integer fExamstarted) {
        this.fExamstarted = fExamstarted;
    }

    public Date getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Date chargeTime) {
        this.chargeTime = chargeTime;
    }
}
