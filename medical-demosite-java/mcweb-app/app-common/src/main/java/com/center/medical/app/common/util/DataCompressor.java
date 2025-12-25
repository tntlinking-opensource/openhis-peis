package com.center.medical.app.common.util;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author: 路飞
 * @date: 2023-02-08 17:00
 * @description: 压缩数据
 */
public class DataCompressor {
    public static byte[] compressData(String data) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        gzipOutputStream.write(data.getBytes());
        gzipOutputStream.close();
        return outputStream.toByteArray();
    }

    public static String decompressData(byte[] compressedData) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();
        return result.toString();
    }
}
