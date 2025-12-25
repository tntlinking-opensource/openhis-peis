package com.center.medical.common.utils;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.Optional;


@Slf4j
public class Base64Util implements MultipartFile {

    private final byte[] imgContent;
    private final String header;

    public Base64Util(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        // TODO - implementation depends on your requirements
        return System.currentTimeMillis() + (int) Math.random() * 10000 + ".png" ;
    }

    @Override
    public String getContentType() {
        // TODO - implementation depends on your requirements
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }

    /**
     * base64转MultipartFile文件
     *
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStr = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStr[0]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            return new Base64Util(b, baseStr[0]);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * base64转InputStream
     *
     * @param fileName  上传到ftp的文件名
     * @param base64str 需上传文件的base64流
     * @return
     */
    public static InputStream base64ToInputStream(String fileName, String base64str) {
        try {
            //根据文件名后缀类型，匹配base64的头部信息
            Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(fileName);
            String type = mediaType.orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
            String head = "data:" + type + ";base64,";
            base64str = head + base64str;
            //base64转文件流
            MultipartFile multipartFile = Base64Util.base64ToMultipart(base64str);
            String originalFilename = multipartFile.getOriginalFilename();
            // 文件扩展名
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).trim();
            String uuid = DigestUtils.md5Hex(System.currentTimeMillis() + "");
            //将文件写入服务器
            File localFile = File.createTempFile(uuid, suffix);
            multipartFile.transferTo(localFile);
            if (!FileUtil.exist(localFile)) {
                log.info("文件不存在");
            }
            InputStream os = new FileInputStream(localFile);
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
