package com.center.medical.common.utils;


import com.center.medical.common.exception.ServiceException;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @description: 导出多个Excel 压缩成ZIP
 */

public class ExportExcelZip {

    /**
     * 以压缩包的方式下载excel
     *
     * @param response    响应
     * @param workbookMap
     * @param zipName     压缩包名称
     */
    public static void downloadExcelForZip(HttpServletResponse response, Map<String, Workbook> workbookMap, String zipName) throws IOException {
        // 文件名外的双引号处理firefox的空格截断问题
        ZipOutputStream out = null;
        try {
            response.setContentType("application/*");
            response.setHeader("content-disposition", "attachment;filename=" + new String(zipName.getBytes("gb2312"), "ISO8859-1"));
            response.setCharacterEncoding("UTF-8");
            out = new ZipOutputStream(response.getOutputStream());
            for (String fileName : workbookMap.keySet()) {
                Workbook workbook = workbookMap.get(fileName);
                ZipEntry entry = new ZipEntry(fileName);
                out.putNextEntry(entry);
                // 这里讲一下，workBook.write会指定关闭数据流，如果这里直接用workbook.write(out)，
                // 下次就会抛出out已被关闭的异常，所有用ByteArrayOutputStream来拷贝一下。
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                workbook.write(bos);
                bos.writeTo(out);
                // 关闭输入流
                out.closeEntry();
            }
            out.flush();

        } catch (IOException e) {
            throw new ServiceException("下载文件异常"+e.getMessage(), 9000);
        } finally {
            if (out != null) {
                out.close();
            }

        }
    }

}