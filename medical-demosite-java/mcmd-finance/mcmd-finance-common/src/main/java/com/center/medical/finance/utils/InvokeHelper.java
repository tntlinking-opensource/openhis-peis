package com.center.medical.finance.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class InvokeHelper
{
    public static String POST_K3CloudURL = "http://223.80.101.18:8888/k3cloud/";


    private static String CookieVal = null;

    private static Map<String, String> map = new HashMap();
    static  {
        map.put("Save", "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save.common.kdsvc");
        map.put("View", "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.View.common.kdsvc");
        map.put("Submit", "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Submit.common.kdsvc");
        map.put("Audit", "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Audit.common.kdsvc");
        map.put("UnAudit", "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.UnAudit.common.kdsvc");
        map.put("StatusConvert", "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.StatusConvert.common.kdsvc");
        map.put("ExecuteBillQuery", "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExecuteBillQuery.common.kdsvc");
    }


    private static HttpURLConnection initUrlConn(String url, JSONArray paras) throws Exception {
        URL postUrl = new URL(POST_K3CloudURL.concat(url));
        HttpURLConnection connection = (HttpURLConnection)postUrl.openConnection();
        if (CookieVal != null) {
            connection.setRequestProperty("Cookie", CookieVal);
        }
        if (!connection.getDoOutput()) {
            connection.setDoOutput(true);
        }
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/json");
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());

        UUID uuid = UUID.randomUUID();
        int hashCode = uuid.toString().hashCode();

        JSONObject jObj = new JSONObject();

        jObj.put("format", 1);
        jObj.put("useragent", "ApiClient");
        jObj.put("rid", hashCode);
        jObj.put("parameters", chinaToUnicode(paras.toString()));
        jObj.put("timestamp", (new Date()).toString());
        jObj.put("v", "1.0");

        out.writeBytes(jObj.toString());
        out.flush();
        out.close();

        return connection;
    }



    public static boolean Login(String dbId, String user, String pwd, int lang) throws Exception {
        boolean bResult = false;

        String sUrl = "Kingdee.BOS.WebApi.ServicesStub.AuthService.ValidateUser.common.kdsvc";

        JSONArray jParas = new JSONArray();
        jParas.put(dbId);
        jParas.put(user);
        jParas.put(pwd);
        jParas.put(lang);

        HttpURLConnection connection = initUrlConn(sUrl, jParas);

        String key = null;
        for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++) {
            if (key.equalsIgnoreCase("Set-Cookie")) {
                String tempCookieVal = connection.getHeaderField(i);
                if (tempCookieVal.startsWith("kdservice-sessionid")) {
                    CookieVal = tempCookieVal;

                    break;
                }
            }
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

//        System.out.println(" ============================= ");
//        System.out.println(" Contents of post request ");
//        System.out.println(" ============================= ");
        String line;
        while ((line = reader.readLine()) != null) {
            String sResult = new String(line.getBytes(), "utf-8");
//            System.out.println(sResult);
            bResult = line.contains("\"LoginResultType\":1");
        }
//        System.out.println(" ============================= ");
//        System.out.println(" Contents of post request ends ");
//        System.out.println(" ============================= ");
        reader.close();

        connection.disconnect();

        return bResult;
    }



    public static String Save(String formId, String content) throws Exception { return Invoke("Save", formId, content); }




    public static String View(String formId, String content) throws Exception { return Invoke("View", formId, content); }



    public static void ExecuteBillQuery(String content) throws Exception { Invoke("ExecuteBillQuery", content); }




    public static void Submit(String formId, String content) throws Exception { Invoke("Submit", formId, content); }




    public static void Audit(String formId, String content) throws Exception { Invoke("Audit", formId, content); }




    public static void UnAudit(String formId, String content) throws Exception { Invoke("UnAudit", formId, content); }




    public static void StatusConvert(String formId, String content) throws Exception { Invoke("StatusConvert", formId, content); }



    private static String Invoke(String deal, String formId, String content) throws Exception {
        String sUrl = ((String)map.get(deal)).toString();
        JSONArray jParas = new JSONArray();
        jParas.put(formId);
        jParas.put(content);

        HttpURLConnection connectionInvoke = initUrlConn(sUrl, jParas);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connectionInvoke.getInputStream()));



        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            String sResult = new String(line.getBytes(), "utf-8");
//            System.out.println(sResult);
            sb.append(sResult);
        }

        reader.close();

        connectionInvoke.disconnect();
        return sb.toString();
    }


    private static void Invoke(String deal, String content) throws Exception {
        String sUrl = ((String)map.get(deal)).toString();
        JSONArray jParas = new JSONArray();
        jParas.put(content);

        HttpURLConnection connectionInvoke = initUrlConn(sUrl, jParas);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connectionInvoke.getInputStream()));


//        System.out.println(" ============================= ");
//        System.out.println(" Contents of post request ");
//        System.out.println(" ============================= ");
        String line;
        while ((line = reader.readLine()) != null) {
            String sResult = new String(line.getBytes(), "utf-8");
//            System.out.println(sResult);
        }
//        System.out.println(" ============================= ");
//        System.out.println(" Contents of post request ends ");
//        System.out.println(" ============================= ");
        reader.close();

        connectionInvoke.disconnect();
    }



    public static String chinaToUnicode(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c >= ' ' && c <= '~') {

                sb.append(c);
            } else {

                sb.append(char2Unicode(c));
            }
        }
//        System.out.println("转编码字符======>" + sb);
        return sb.toString();
    }

    private static String char2Unicode(char c) {
        StringBuffer sb = new StringBuffer();
        sb.append("\\u");

        int j = c >>> '\b';
        String tmp = Integer.toHexString(j);



        if (tmp.length() == 1)
            sb.append("0");
        sb.append(tmp);
        j = c & 0xFF;
        tmp = Integer.toHexString(j);
        if (tmp.length() == 1)
            sb.append("0");
        sb.append(tmp);
        return sb.toString();
    }



    public static Map<String, String> cruxChar2General(Map<String, String> map) {
    Set<Map.Entry<String, String>> set = map.entrySet();

    for (Map.Entry<String, String> entry : set) {

        String str = (String)entry.getValue();
        if (str.contains("\"")) {
            map.put(entry.getKey(), str.replace("\"", "'")); continue;
        }  if (str.contains("\\")) {
            map.put(entry.getKey(), str.replace("\\", "/"));
        }
    }


    return map;
}


    public static String chinaToUnicode2(String str) {
        char[] chars = str.toCharArray();
        String result = "";
        for (int i = 0; i < chars.length; i++) {
            int chr1 = chars[i];
            if (isChineseByBlock(chars[i])) {
                result = result + "\\u" + Integer.toHexString(chr1);
            } else if (isChinesePunctuation(chars[i])) {
                result = result + ".";
            } else {
                result = result + chars[i];
            }
        }

        return result;
    }


    public static boolean isChinesePunctuation(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS || ub == Character.UnicodeBlock.VERTICAL_FORMS)
        {


            return true;
        }
        return false;
    }



    public static boolean isChineseByBlock(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT)
        {





            return true;
        }
        return false;
    }


    public static void main(String[] args) throws Exception {
//        InvokeHelper.Login("640ec5b9a3c20d", "kingdee3", "123qwe!@#", 2052);
//        //比如调用个物料的查询功能，formid可以在金蝶的BOS工具里面查看
//        String formid = "RYKG_TJZJB";
//        // 查询id为123456的物料
//        String content = "{\n" +
//                "    \"Model\": {\n" +
//                "        \"FID\": 0,\n" +
//                "        \"FBillNo\": \"1233\",\n" +
//                "        \"FEntity\": [\n" +
//                "            {\n" +
//                "                \"F_RYKG_TJNO\": \"1233\",\n" +
//                "                \"FDATE\": \"2025-05-08 00:00:00\",\n" +
//                "                \"F_RYKG_tjmc\": \"福利中心\",\n" +
//                "                \"F_RYKG_tjfmc\": \"客户\",\n" +
//                "                \"F_RYKG_FYXM\": \"体检\",\n" +
//                "                \"FPrice\": 11.0,\n" +
//                "                \"FPriceQty\": 1.0,\n" +
//                "                \"FALLAMOUNTFOR_D\": 11.0,\n" +
//                "                \"Frecstatus\": \"ok\"\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    }\n" +
//                "}";
//        String result = InvokeHelper.Save(formid, content);
//        System.out.println("打印一下");
//        System.out.println(result);

    }
}