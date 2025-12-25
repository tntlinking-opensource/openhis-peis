package com.center.medical.center.qingdao.profession.utils;

import org.apache.commons.lang3.StringUtils; import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec; import java.nio.charset.StandardCharsets; import java.util.Arrays;
import java.util.Map;

import java.util.Set;
import java.util.TreeMap;

public class HmacShaUtil {
    public static String Sign256(final Map<String, Object> params, String key) throws Exception {
        Map<String, Object> data = new TreeMap();
        data.putAll(params);
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (data.get(k) instanceof Boolean) {
                sb.append("&").append(k).append("=").append(data.get(k));
            }
            if (data.get(k) instanceof String && StringUtils.isNotBlank(data.get(k).toString())) { // 参数值为空，则不参与签名
                sb.append("&").append(k).append("=").append(data.get(k));
            }
        }
        sb.append("&key=").append(key);
        return HMACSHA256(sb.substring(1), key);
    }

    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }
}

