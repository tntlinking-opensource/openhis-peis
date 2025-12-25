package com.center.medical.center.qingdao.profession.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author xhp
 * @since 2023-05-29 14:24
 */
public class Base64ToMultipartFile implements MultipartFile {
    private final byte[] fileContent;

    private final String extension;
    private final String contentType;

    /**
     * @param fullBase64 data:image/png;base64,……
     */
    public Base64ToMultipartFile(String fullBase64) {
        String[] arr = fullBase64.split(";");
        String base64=arr[1].split(",")[1 ];
        String dataUri=arr[0];
        this.fileContent = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
        this.extension = dataUri.split(";")[0].split("/")[1];
        this.contentType = dataUri.split(";")[0].split(":")[1];
    }

    /**
     * data:image/png;base64,……
     * @param base64
     * @param dataUri     格式类似于: data:image/png;base64
     */
    public Base64ToMultipartFile(String base64, String dataUri) {
        this.fileContent = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
        this.extension = dataUri.split(";")[0].split("/")[1];
        this.contentType = dataUri.split(";")[0].split(":")[1];
    }

    public Base64ToMultipartFile(String base64, String extension, String contentType) {
        this.fileContent = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
        this.extension = extension;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return "param_" + System.currentTimeMillis();
    }

    @Override
    public String getOriginalFilename() {
        return "file_" + System.currentTimeMillis() + "." + extension;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return fileContent == null || fileContent.length == 0;
    }

    @Override
    public long getSize() {
        return fileContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return fileContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(fileContent);
    }

    @Override
    public void transferTo(File file) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(fileContent);
        }
    }

    public String getExtension() {
        return extension;
    }
}