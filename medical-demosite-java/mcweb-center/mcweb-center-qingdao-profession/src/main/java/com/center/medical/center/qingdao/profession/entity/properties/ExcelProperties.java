package com.center.medical.center.qingdao.profession.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "excel")
public class ExcelProperties {
    private String path1;
    private String path2;
    private String path3;
    private int sheetIndex;
}
