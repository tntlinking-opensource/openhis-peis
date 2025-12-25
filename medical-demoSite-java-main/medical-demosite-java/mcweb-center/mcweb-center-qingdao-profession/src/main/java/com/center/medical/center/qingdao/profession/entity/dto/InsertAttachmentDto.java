package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class InsertAttachmentDto implements Serializable {
    private static final long serialVersionUID = 5291712369161489654L;

    private String patientcode;//体检号
    private String filePath;//文件路径 赋码存的是base64


    public InsertAttachmentDto(String patientcode, String filePath) {
        this.patientcode = patientcode;
        this.filePath = filePath;
    }

    public InsertAttachmentDto() {
    }
}