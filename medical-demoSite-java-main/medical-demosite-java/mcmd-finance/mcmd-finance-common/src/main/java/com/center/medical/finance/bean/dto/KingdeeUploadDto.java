package com.center.medical.finance.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 上传金蝶数据
 */
@Data
public class KingdeeUploadDto implements Serializable {
    private static final long serialVersionUID = 5060259347817216466L;

    private KingdeeUploadModel Model;

    public KingdeeUploadDto(KingdeeUploadModel model) {
        Model = model;
    }

    public KingdeeUploadDto() {
    }

    @Data
    public static class KingdeeUploadModel implements Serializable {
        private static final long serialVersionUID = 68533565324371431L;

        private int FID;
        private String FBillNo;
        private List<KingdeeUploadFEntity> FEntity;

        public KingdeeUploadModel(String FBillNo, List<KingdeeUploadFEntity> FEntity) {
            this.FBillNo = FBillNo;
            this.FEntity = FEntity;
        }

        public KingdeeUploadModel() {
        }
    }



}
