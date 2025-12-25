package com.center.medical.sellcrm.bean.dto;

import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.center.medical.bean.model.Peispatientcharge;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.sellcrm.bean.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 路飞
 * @date: 2022-12-05 17:42
 * @description: 同步线上订单数据
 */
@Data
public class SyncOrderDto implements Serializable {

    @ApiModelProperty(value = "备单数据")
    private List<Createorder> orders; //db

    @ApiModelProperty(value = "团体数据")
    private List<Sellcustomer> customers; //customer

    @ApiModelProperty(value = "订单与分中心关联数据")
    private List<Orderandfzx> orderAndFzxs; //orderAndFzxs

    @ApiModelProperty(value = "订单与套餐关联数据")
    private List<Orderandcombo> orderAndCombos; //orderAndCombo

    @ApiModelProperty(value = "修改发放方式的订单")
    private List<Createorder> editOrders;

    @ApiModelProperty(value = "变更前台须知的订单")
    private List<Createorder> xzOrders;

    @ApiModelProperty(value = "变更隐藏状态的订单")
    private List<Orderandcombo> ycOrders;

    @ApiModelProperty(value = "变更开单助理状态的订单")
    private List<Createorder> kdzlOrders;

    @ApiModelProperty(value = "普通套餐相关")
    private List<Createmeal> createMeals; //createMeal

    @ApiModelProperty(value = "普通套餐与分中心关联数据")
    private List<Mealandfzx> mealAndFzxs;

    @ApiModelProperty(value = "普通套餐与收费项目关联数据")
    private List<Mealanditem> mealAndItemss;

    @ApiModelProperty(value = "最小套餐")
    private List<Createcombo> createCombos; //createCombo

    @ApiModelProperty(value = "最小套餐与收费项目关联数据")
    private List<Comboanditem> comboAndItemss;

    @ApiModelProperty(value = "最小套餐与分中心关联数据")
    private List<Comboandfzx> comboAndFzxs;

    @ApiModelProperty(value = "判断职业小结(Comboexamitem)表同步数据")
    private List<Comboexamitem> comboExamItem;

    @ApiModelProperty(value = "体检者团体任务数据")
    private List<Peisorgreservation> vations;

    @ApiModelProperty(value = "体检者任务分组数据")
    private List<Peisorgreservationgroup> groups;

    @ApiModelProperty(value = "体检者数据")
    private List<Peispatient> patients;

    @ApiModelProperty(value = "体检者收费项目数据")
    private List<Peispatientfeeitem> feeitems;

    @ApiModelProperty(value = "体检者缴费数据")
    private List<Peispatientcharge> charges;

    @ApiModelProperty(value = "体检者费用主表数据")
    private List<PeispatientChargeMain> pcms;

    @ApiModelProperty(value = "客户转移数据")
    private List<CustomerTransfer> customerTransfer;
}
