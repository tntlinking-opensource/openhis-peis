package com.center.medical.common.utils.file;

import com.center.medical.common.exception.UtilException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * @author xhp
 * @since 2023-05-05 9:09
 */
public class MultipartFileUtil {

    /**
     * File转MultipartFile
     * @param file
     * @return
     */
    public static MultipartFile fileConvertMultipartFile(File file){
        try{
            DiskFileItem fileItem = new DiskFileItem(
                    "file", // 表单字段名
                    Files.probeContentType(file.toPath()), // 文件类型
                    false, // 是否在内存中存储文件
                    file.getName(), // 文件名
                    (int) file.length(), // 文件大小
                    file.getParentFile() // 临时文件目录
            );
            try(
                    OutputStream outputStream=fileItem.getOutputStream();
            ){
                outputStream.write(Files.readAllBytes(file.toPath()));
                return new CommonsMultipartFile(fileItem);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new UtilException(e);
        }
    }

    /**
     * base64转multipartfile
     * @param base64 data:image/png;base64,……
     * @return
     */
    public static Base64ToMultipartFile base64ConvertMultipartFile(String base64) {
        return new Base64ToMultipartFile(base64);
    }

}
