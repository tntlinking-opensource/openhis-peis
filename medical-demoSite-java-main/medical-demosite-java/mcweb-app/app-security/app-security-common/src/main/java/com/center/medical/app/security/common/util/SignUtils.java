package com.center.medical.app.security.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.center.medical.app.common.enums.AppHttpStatus;
import com.center.medical.app.common.exception.AppBindException;
import com.center.medical.app.common.util.Json;
import com.center.medical.app.security.common.bo.UserInfoInTokenBO;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 签名工具类
 *
 * @author yami
 */
public class SignUtils {

    public static final String ACCESS_SHOP = "Access-Shop";
    public static final String ACCESS_TIME = "Access-Time";
    public static final String ACCESS_USER = "Access-User";
    public static final String ACCESS_SIGN = "Access-Sign";
    public static final String ACCESS_ID = "Access-Id";
    public static final String ACCESS_DATA = "Access-Data";
    public static final String ACCESS_BODY = "Access-Body";
    private static final long TEN_MINUTES = 1000 * 60 * 10;
    /**
     * 指定key的大小
     */
    private static int KEY_SIZE = 512;
    private static String SHA_256 = "SHA-256";
    private static String CHARSET_NAME = "UTF-8";


    // 测试
    public static void main(String[] args) {
        try {
//            String key = generateKeyPair("1453645829717102592");
            String key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAITi4LR3RTpXedGguZNE4Mnyo0XP4cntVd9ZJKGnj3OfCqoqi31RVjyqtdFvrYxR77hvWAWZI2TJmjFipNU2AlsCAwEAAQ==";
            //原始报文
            String plain = "原始报文";
            Map<String, String> map = new TreeMap<>();
//            map.put(ACCESS_SHOP, "0");
            long currentTimeMillis = System.currentTimeMillis();
            System.out.println("currentTimeMillis:  " + currentTimeMillis);
            map.put(ACCESS_TIME, String.valueOf(currentTimeMillis));
//            map.put(ACCESS_USER, null);
            map.put(ACCESS_ID, "1453645829717102592");
            // 请求数据
            Map<String, String> dataMap = new TreeMap<>();
//            dataMap.put("t", "1637287792312");
//            dataMap.put("current", "1");
//            dataMap.put("size", "10");
            dataMap.put("t", "1637309255594");
            dataMap.put("formId", "152");
            map.put(ACCESS_DATA, Json.toJsonString(dataMap));
            //加签
            String sign = sign(Json.toJsonString(map), key);
            System.out.println("原始报文是:" + plain);
            System.out.println("加签结果:");
            System.out.println(sign);
            //验签
            boolean verifyResult = verify(Json.toJsonString(map), sign, key);
            System.out.println("验签结果:" + verifyResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成密钥
     */
    public static String generateKeyPair(String accessId) {
        String key = accessId + IdUtil.simpleUUID();
        String sha256 = getSHA256(key);
        return sha256;
    }

    /**
     * 加签方法
     *
     * @param plain
     * @param key   密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     * @throws SignatureException
     */
    public static String sign(String plain, String key) {
        //加签
        return encryption(plain, key);
    }

    public static String loadRequestData(HttpServletRequest request, Map<String, String> dataMap) {
        TreeMap<String, String> params = new TreeMap<>();
        //获取请求参数（对应@RequestParam）
        if (!CollUtil.isEmpty(request.getParameterMap())) {
            getParam(request, params);
        }
//        //获取path variable（对应@PathVariable）
//        ServletWebRequest webRequest = new ServletWebRequest(request, null);
//        Map<String, String> uriTemplateVars = (Map<String, String>) webRequest.getAttribute(
//                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
//        if (CollUtil.isNotEmpty(uriTemplateVars)) {
//            params.putAll(uriTemplateVars);
//        }

        //获取body（对应@RequestBody）
        String body = getBody(request, dataMap);
        // 整合数据
        if (params.size() > 0 && StrUtil.isNotBlank(body)) {
            params.put(ACCESS_BODY, body);
        }

        String data;
        if (StrUtil.isBlank(body)) {
            data = Json.toJsonString(params);
        } else {
            data = body;
        }
        return data;
    }

    private static void getParam(HttpServletRequest request, TreeMap<String, String> params) {
        Map<String, String[]> map = request.getParameterMap();
        if (MapUtil.isNotEmpty(map)) {
            for (String key : map.keySet()) {
                String[] strings = map.get(key);
                // 数组中只有一个数据，则把参数单独作为值
                if (strings.length == 1) {
                    params.put(key, strings[0]);
                }
                // 数组中有多个数据，则把数组作为值
                else {
                    Object value = map.get(key);
                    params.put(key, Objects.isNull(value) ? null : value.toString());
                }
            }
        }
    }

    private static String getBody(HttpServletRequest request, Map<String, String> dataMap) {
        String body = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            is = request.getInputStream();
            baos = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int read;
            while ((read = is.read(buff)) > 0) {
                baos.write(buff, 0, read);
            }
            byte[] bytes = baos.toByteArray();
            body = new String(bytes, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(baos)) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(is)) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return body;
    }

    /**
     * 加密
     *
     * @param plain
     * @param key
     * @return
     */
    private static String encryption(String plain, String key) {
        String sha256 = getSHA256(plain);
        return getSHA256(key + sha256);
    }

    /**
     * 验签方法
     *
     * @param plain     数据
     * @param signature 签名
     * @param key       密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws SignatureException
     * @throws InvalidKeySpecException
     */
    public static boolean verify(String plain, String signature, String key) {
        // 生成签名
        String sign = encryption(plain, key);
        // 进行验签
        return Objects.equals(sign, signature);
    }

    private static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance(SHA_256);
            messageDigest.update(str.getBytes(CHARSET_NAME));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * 从请求头中获取签名相关的信息
     *
     * @param req
     * @return
     */
    public static Map<String, String> getSignInfo(HttpServletRequest req, UserInfoInTokenBO userInfoInToken) {
        Map<String, String> map = new HashMap<>(2);
        // 校验必传数据
        String accessShop = req.getHeader(ACCESS_SHOP);
        String accessTime = req.getHeader(ACCESS_TIME);
        if (StrUtil.isBlank(accessTime)) {
            throw new AppBindException(AppHttpStatus.UNAUTHORIZED, ACCESS_TIME + " is blank");
        }
        String accessSign = req.getHeader(ACCESS_SIGN);
        if (StrUtil.isBlank(accessSign)) {
            throw new AppBindException(AppHttpStatus.UNAUTHORIZED, ACCESS_SIGN + " is blank");
        }
        String accessUser = req.getHeader(ACCESS_USER);

        if (Objects.nonNull(accessShop)) {
            // 店铺id
            Long shopId = getLong(accessShop, ACCESS_SHOP);
            // 平台端不需要传店铺id，商家的店铺id都大于0
            if (shopId < 1) {
                throw new AppBindException(AppHttpStatus.UNAUTHORIZED, ACCESS_SHOP + " is error");
            }
            userInfoInToken.setShopId(shopId);
        }

        // 签名时间
        Long signTime = getLong(accessTime, ACCESS_TIME);
        long currentTimeMillis = System.currentTimeMillis();
        // 签名时间大于十分钟，提示签名超时
        if (signTime + TEN_MINUTES < currentTimeMillis) {
            throw new AppBindException(AppHttpStatus.UNAUTHORIZED, "The request time has exceeded ten minutes");
        }
        map.put(ACCESS_TIME, signTime.toString());

        // 签名
        map.put(ACCESS_SIGN, accessSign);

        if (StrUtil.isNotBlank(accessUser)) {
            // 用户在自己系统的用户id
            getLong(accessUser, ACCESS_USER);
            userInfoInToken.setBizUserId(accessUser);
        }
        return map;
    }


    private static long getLong(String data, String param) {
        long value;
        try {
            value = Long.valueOf(data);
        } catch (Exception e) {
            throw new AppBindException(AppHttpStatus.UNAUTHORIZED, param + " is error");
        }
        return value;
    }
}
