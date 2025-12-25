package com.center.medical.common.utils.file;

import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.utils.DateUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.uuid.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件处理工具类
 *
 * @author 路飞
 */
@Slf4j
public class FileUtils {
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    public static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.close(os);
            IOUtils.close(fis);
        }
    }

    /**
     * 写数据到文件中
     *
     * @param data 数据
     * @return 目标文件
     * @throws IOException IO异常
     */
    public static String writeImportBytes(byte[] data) throws IOException {
        return writeBytes(data, ZhongkangConfig.getImportPath());
    }

    /**
     * 写数据到文件中
     *
     * @param data      数据
     * @param uploadDir 目标文件
     * @return 目标文件
     * @throws IOException IO异常
     */
    public static String writeBytes(byte[] data, String uploadDir) throws IOException {
        FileOutputStream fos = null;
        String pathName = "";
        try {
            String extension = getFileExtendName(data);
            pathName = DateUtils.datePath() + "/" + IdUtils.fastUUID() + "." + extension;
            File file = FileUploadUtils.getAbsoluteFile(uploadDir, pathName);
            fos = new FileOutputStream(file);
            fos.write(data);
        } finally {
            IOUtils.close(fos);
        }
        return FileUploadUtils.getPathFileName(uploadDir, pathName);
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 检查文件是否可下载
     *
     * @param resource 需要下载的文件
     * @return true 正常 false 非法
     */
    public static boolean checkAllowDownload(String resource) {
        // 禁止目录上跳级别
        if (StringUtils.contains(resource, "..")) {
            return false;
        }

        // 检查允许下载的文件规则
        if (ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, FileTypeUtils.getFileType(resource))) {
            return true;
        }

        // 不在允许下载的文件规则
        return false;
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 下载文件名重新编码
     *
     * @param response     响应对象
     * @param realFileName 真实文件名
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
        response.setHeader("Content-disposition", contentDispositionValue.toString());
        response.setHeader("download-filename", percentEncodedFileName);
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

    /**
     * 获取图像后缀
     *
     * @param photoByte 图像数据
     * @return 后缀名
     */
    public static String getFileExtendName(byte[] photoByte) {
        String strFileExtendName = "jpg";
        if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70) && (photoByte[3] == 56)
                && ((photoByte[4] == 55) || (photoByte[4] == 57)) && (photoByte[5] == 97)) {
            strFileExtendName = "gif";
        } else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73) && (photoByte[9] == 70)) {
            strFileExtendName = "jpg";
        } else if ((photoByte[0] == 66) && (photoByte[1] == 77)) {
            strFileExtendName = "bmp";
        } else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71)) {
            strFileExtendName = "png";
        }
        return strFileExtendName;
    }

    /**
     * 获取文件名称 /profile/upload/2022/04/16/zhongkang.png -- zhongkang.png
     *
     * @param fileName 路径名称
     * @return 没有文件路径的名称
     */
    public static String getName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int lastUnixPos = fileName.lastIndexOf('/');
        int lastWindowsPos = fileName.lastIndexOf('\\');
        int index = Math.max(lastUnixPos, lastWindowsPos);
        return fileName.substring(index + 1);
    }

    /**
     * 获取不带后缀文件名称 /profile/upload/2022/04/16/zhongkang.png -- zhongkang
     *
     * @param fileName 路径名称
     * @return 没有文件路径和后缀的名称
     */
    public static String getNameNotSuffix(String fileName) {
        if (fileName == null) {
            return null;
        }
        String baseName = FilenameUtils.getBaseName(fileName);
        return baseName;
    }

    public static String downLoadFile(HttpServletResponse response, HttpServletRequest request, String path) {
        return downLoadFile(response, request, path, null);
    }

    /**
     * 下载单个文件
     */
    public static String downLoadFile(HttpServletResponse response, HttpServletRequest request, String path, String fileName) {
        FileInputStream fis = null;
        ServletOutputStream out = null;
        BufferedInputStream bis = null;
        String result = null;
        try {
            File file = new File(path);
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            response.setContentType("application/x-msdownload");
            String filename = fileName != null ? fileName : file.getName();
            // 解决中文文件名乱码问题
            if (request.getHeader("User-Agent").toLowerCase()
                    .indexOf("firefox") > 0) {
                path = new String(path.getBytes("UTF-8"), "ISO8859-1"); // firefox浏览器
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            } else if (request.getHeader("User-Agent").toUpperCase()
                    .indexOf("MSIE") > 0) {
                path = URLEncoder.encode(path, "UTF-8");// IE浏览器
                filename = URLEncoder.encode(filename, "UTF-8");
            } else if (request.getHeader("User-Agent").toUpperCase()
                    .indexOf("CHROME") > 0) {
                path = new String(path.getBytes("UTF-8"), "ISO8859-1");// 谷歌
                filename = URLEncoder.encode(filename, "UTF-8");//正常显示中文文件名
            } else {
                path = URLEncoder.encode(path, "UTF-8");
                filename = URLEncoder.encode(filename, "UTF-8");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            out = response.getOutputStream();
            int len = -1;
            while ((len = bis.read()) != -1) {
                out.write(len);
            }
        } catch (FileNotFoundException e) {
            log.error("下载失败，文件不存在：{}", e);
            result = "下载失败，文件不存在!";
        } catch (UnsupportedEncodingException e) {
            log.error("下载失败，编码不支持：{}", e);
            result = "下载失败，编码不支持！";
        } catch (IOException e) {
            result = e.getMessage();
            log.error("下载失败，IOException：{}", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("out.close失败，IOException：{}", e);
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error("bis.close失败，IOException：{}", e);
                }
            }
        }
        return result;
    }

    public static boolean areFilesSameSize(String url1, String url2) {
        try {
            URLConnection connection1 = new URL(url1).openConnection();
            long size1 = connection1.getContentLengthLong();
            URLConnection connection2 = new URL(url2).openConnection();
            long size2 = connection2.getContentLengthLong();
            return size1 == size2;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static void main(String[] args) throws IOException {
//        System.out.println(areFilesSameSize("https://XXX.XXX.XXX.XXXm/newimage/images/common/02/20240910/e7dc74cf49d8409da8613f0712f83c7d.jpg",
//                "http://XXX.XXX.XXX.XXX/resours_host/newimage/images/common/02/20240910/e7dc74cf49d8409da8613f0712f83c7d.jpg"));
//
//        String filePath = "newimage/images/common/02/20240910/e7dc74cf49d8409da8613f0712f83c7d.jpg";
//        System.out.println(StrUtil.subBefore(filePath, "/", true) +"----------"+ StrUtil.subAfter(filePath, "/", true));
//
//        String filePath1 = "/www/nas" + (filePath.startsWith(Constants.RESOURCE_PREFIX) ? StrUtil.removePrefix(filePath, Constants.RESOURCE_PREFIX):(filePath.startsWith(Constants.RESOURCE_PREFIX1)?StrUtil.removePrefix(filePath, Constants.RESOURCE_PREFIX1):filePath));
//        System.out.println(filePath1);
//
//        // 获取远程图片的输入流
//        URL url = new URL("https://XXX.XXX.XXX.XXXm/newimage/images/common/02/20240910/e7dc74cf49d8409da8613f0712f83c7d.jpg");
//        URLConnection connection = url.openConnection();
//        InputStream inputStream = connection.getInputStream();
////        if (isOnline == 1) {
////            //同步至线上
////            byte[] bytes = FileUtils.convertInputStreamToByteArray(inputStream);
////            upload(bytes, filePath, extName);
////        } else {
//            //线下系统将文件储存至指定的硬盘中位置中
//            FtpUtil.upload(StrUtil.subBefore(filePath, "/", true), StrUtil.subAfter(filePath, "/", true), inputStream, false);
////        }
////        FtpUtil.upload(StrUtil.subBefore(filePath, "/", true), StrUtil.subAfter(filePath, "/", true), inputStream);
//        inputStream.close();
////            log.info("上传成功!");
////        } catch (FileNotFoundException e) {
////            log.error("上传失败,文件不存在：", e);
////            throw new FileNotFoundException(e.getMessage());
////        }
//    }
}
