package com.center.medical.pacs.bean.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 多文件接收对象
 *
 * @author xhp
 * @since 2023-03-23 13:57
 */
@Data
public class MultipartFileParam {
    private List<MultipartFile> files;
}
