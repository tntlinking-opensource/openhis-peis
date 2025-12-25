package com.center.medical.finance.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 上传金蝶数据
 */
@Data
public class KdReceivePaymentDto implements Serializable {
    private static final long serialVersionUID = 5060259347817216466L;

    private KingdeeUploadModel Model;

    public KdReceivePaymentDto(KingdeeUploadModel model) {
        Model = model;
    }

    public KdReceivePaymentDto() {
    }

    @Data
    public static class KingdeeUploadModel implements Serializable {
        private static final long serialVersionUID = 68533565324371431L;

        private int FID;
        private String FBillNo;
        private List<kdReceivePaymentEntity> FEntity;

        public KingdeeUploadModel(String FBillNo, List<kdReceivePaymentEntity> FEntity) {
            this.FBillNo = FBillNo;
            this.FEntity = FEntity;
        }

        public KingdeeUploadModel() {
        }
    }



}
