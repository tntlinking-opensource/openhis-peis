/**
 * 项目名：
 * 文件名：ToolUtil.java
 * 创建日期：2015-12-23 上午10:25:19
 * 创建人：Administrator
 * cvs版本：$Reversion$ $Date: 2016/11/14 03:39:34 $
 * 修改记录：无
 * [参考格式：修改人：Administrator 修改时间：2015-12-23 上午10:25:19 修改内容：X]
 */
package com.center.medical.common.utils;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.constant.ReportConstants;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名称：[中文类名] 作者： Administrator
 */
@Service("toolUtil")
public class ToolUtil {

    public final static String CC = "143";//彩超


    /**
     * 获取汉字的拼音
     *
     * @param src
     * @return String
     * @Title: getGuihua
     * @author yinzl
     * @since 2016-4-13 V 1.0
     */
    public static String getHanziPinyin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    /**
     * 获取汉字的拼音首字母
     *
     * @param str
     * @return String
     * @Title: getPinYinHeadChar
     * @author yinzl
     * @since 2016-4-13 V 1.0
     */
    public static String getHanziPinyinHeadChar(String str) {
        String convert = "";
        str = str.trim();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = null;
            try {
                pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word,
                        new HanyuPinyinOutputFormat());
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert.trim().toUpperCase();
    }

    /**
     * 获取中文编码集
     *
     * @param s
     * @return String
     * @Title: getChinese
     * @author yinzl
     * @since 2016-4-14 V 1.0
     */
    public static String getChinese(String s) {

        try {

            return new String(s.getBytes("gb2312"), "iso-8859-1");

        } catch (UnsupportedEncodingException e) {

            return s;

        }

    }

    public static void main(String[] args) {
        String yearMonth = getYearMonth(36);
        System.out.println(yearMonth);
    }

    /**
     * 体检号补0
     *
     * @param code 体检号
     * @param jm   分中心简码
     */
    @SuppressWarnings("rawtypes")
    public static String patientCode(String code, String jm) {
        if (StringUtils.isBlank(code)) {
            return code;
        }
        int length = code.trim().length();
        // 体检号长度为0或者体检号长度确定为12位，直接返回
        if (length == 0 || length >= 12) {
            return code;
        }
        //霸州有职业的另一个系统的体检号，不需要补全直接返回
        if (length == 9 && code.startsWith("25")){
            return code;
        }
        if (length > 8) {
            code = code.substring(length - 8);
        }
        StringBuilder builder = new StringBuilder(jm);

        // 自动补0
        code = String.format("%8s", code).replace(" ", "0");
        builder.append(code);
        // 第5位的0替换成3
        builder.setCharAt(4, '3');
        return builder.toString();
    }

    public static String patientCodeApp(String code) {
        if (StringUtils.isBlank(code)) {
            return code;
        }
        int length = code.trim().length();
        // 体检号长度为0或者体检号长度确定为12位，直接返回
        if (length == 0 || length == 13) {
            return code;
        }
        if (length > 8) {
            code = code.substring(length - 8);
        }
        StringBuilder builder = new StringBuilder(Constants.ONLINE_PREFIX + "02");
        // 自动补0
        code = String.format("%8s", code).replace(" ", "0");
        builder.append(code);

        return builder.toString();
    }

    /**
     * 根据体检号获取体检号所在分区及该长体检号, 例如：{1553,HD0000001553}
     *
     * @param code 体检号
     * @param jm   分中心简码
     */
    public Object[] getPartitionCode(String code, String jm) {
        if (StringUtils.isBlank(code.trim())) {
            return null;
        }
        int length = code.trim().length();
        // 体检号长度为0或者体检号长度确定为12位，直接返回
        int p = 0;
        if (length == 12) {
            p = Integer.valueOf(code.substring(2));
        }
        // 计算分区
        int area = 1;
        if (p % 10000 == 0) {
            area = p / 10000;
        } else {
            area = p / 10000 + 1;
        }
        // 胡群殴长体检号
        String longCode = patientCode(code, jm);

        return new Object[]{area, longCode};
    }

    /**
     * 根据体检号获取短号
     *
     * @param patientcode 体检号长号
     * @return String
     * @Title: getShortCodeByLong
     * @author xuhp
     * @since 2017-3-7 V 1.0
     */
    public static Integer getShortCodeByLong(String patientcode) {
        Integer shortCode = null;
        if (StringUtils.isNotEmpty(patientcode)) {
            if (patientcode.length() >= 8) {
                shortCode = Integer.parseInt(patientcode.substring(patientcode.length() - 8));
            }else {
                shortCode = Integer.parseInt(patientcode);
            }
        }
        return shortCode;
    }


    /**
     * 替换短信参数
     *
     * @param messageText
     * @param params
     * @return String
     * @Title: getSmsContent
     * @author xuhp
     * @since 2017-3-17 V 1.0
     */
    public static final String getSmsContent(String messageText, String[] params) {
        if (params == null || messageText == null) {
            return messageText;
        }
        for (int i = 0, l = params.length; i < l; i++) {
            messageText = messageText.replaceAll("\\{" + (i + 1) + "\\}", params[i]);
        }
        return messageText;
    }

    public final static String ClobToString(Clob clob) {
        if (clob == null) {
            return null;
        }
        Reader is = null;
        String returnString = null;
        try {
            is = clob.getCharacterStream();
            BufferedReader br = new BufferedReader(is);

            String str = null;
            str = br.readLine(); // 读取第一行
            StringBuffer sb = new StringBuffer();
            while (str != null) { // 如果没有到达流的末尾，则继续读取下一行
                sb.append(str);
                str = br.readLine();
            }
            returnString = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnString;
    }


    public static boolean setEquals(Set<String> set1, Set<String> set2) {
        if (set1.size() != set2.size()) return false;
        return set1.containsAll(set2);
    }


    /**
     * 修改PACS图片路径为映射路径
     *
     * @param path
     * @return String
     * @Title: getPacsPath
     * @author xuhp
     * @since 2016-11-7 V 1.0
     */
    public static String getPacsPath(String path) {
        String newpac = FileTypePath.NEWPACS;
        String[] newpacs = newpac.split("=");
        if (newpacs.length > 0) {
            if (path.indexOf(newpacs[0]) != -1) {
                path = path.replace(newpacs[0], newpacs[1]);
            }
            String oldpac = FileTypePath.OLDPACS;
            String[] oldpacs = oldpac.split("=");
            if (oldpacs.length > 0) {
                if (path.indexOf(oldpacs[0]) == -1) {

                } else {

                    path = path.replace(oldpacs[0], oldpacs[1]);
                }
                path = path.trim();
            }
        } else {
            String oldpac = FileTypePath.OLDPACS;
            String[] oldpacs = oldpac.split("=");
            if (oldpacs.length > 0) {
                if (path.indexOf(oldpacs[0]) == -1) {

                } else {

                    path = path.replace(oldpacs[0], oldpacs[1]);
                }
                path = path.trim();
            }
        }
        return path;
    }


    /**
     * 根据总工龄计算参加工作时间
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) yearInterval--;
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) monthInterval--;
        monthInterval %= 12;

        return yearInterval * 12 + monthInterval;
    }

    /**
     * 截取时间
     *
     * @param indate
     * @return
     */
    public static Date subTime(Date indate) {
        Date dat = null;
        if (indate != null) {
            Long time = indate.getTime() - 28800000;
            dat = new Date(time);
        }
        return dat;
    }

    public static Date getDateForMonth(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -months);
        return calendar.getTime();
    }


    /**
     * 获取配置文件里的配置 String property 配置文件名称 String name 需要获取的配置参数名称 return String
     * 配置参数
     **/
    public static String getPropert(String property, String name) {
        String url = "";
        try {
            ClassLoader cls = Thread.currentThread().getContextClassLoader();
            InputStream in = cls.getResourceAsStream(property);
            Properties p = new Properties();

            p.load(in);
            if (p.containsKey(name)) {
                url = p.getProperty(name);
            }
        } catch (Exception e) {
        }

        return url;
    }


    /**
     * 生成卡发放（体检卡）的密码
     *
     * @return
     */
    public static String generatePassword() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }


    /**
     * 获取接害工龄年份
     *
     * @param jhglMonth
     * @return
     */
    public static String getJhglYear(Integer jhglMonth) {
        if (jhglMonth == null) {
            return "";
        }
        return new BigDecimal(jhglMonth).divide(new BigDecimal(12), 1,
                BigDecimal.ROUND_HALF_UP).toString();
    }



    /**
     * 通过月份获取X年X月
     *
     * @param jhglMonth
     * @return
     */
    public static String getYearMonth(Integer jhglMonth) {
        if (jhglMonth == null || jhglMonth == 0) {
            return "0月";
        }
        // 计算年份和剩余的月份
        int years = jhglMonth / 12; // 整数除法得到年份
        int months = jhglMonth % 12; // 取余得到剩余的月份
        // 格式化输出
        String result = null;
        if (years == 0){
            result = months + "月";
        } else if (months == 0) {
            result = years + "年";
        } else {
            result = years + "年" + months + "月";
        }

        return result;
    }




    /**
     * 获取性别
     *
     * @param idSex
     * @return
     */
    public static String getSex(Object idSex) {
        if (idSex == null) return "";
        return "0".equals(idSex.toString()) ? "男" : "女";
    }


    public static String getUnit(String units) {
        return units == null ? "" : ToolUtil.isStartWithNum(units) ? ("*" + units) : (" " + units);
    }

    public static boolean isStartWithNum(String str) {
        return str.matches("[0-9]+[\\s\\S]*");
    }


    public static final String ClobToString(Object obj) {
        if (obj == null) {
            return "";
        }

        if (!(obj instanceof Clob)) {
            return obj.toString();
        }
        Clob clob = (Clob) obj;
        Reader is = null;
        try {
            is = clob.getCharacterStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(is);

        String str = null;
        try {
            str = br.readLine(); // 读取第一行
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer();
        while (str != null) { // 如果没有到达流的末尾，则继续读取下一行
            sb.append(str);
            try {
                str = br.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String returnString = sb.toString();

        return returnString;
    }


    public static final boolean isInteger(String input) {
        if (input == null) {
            return false;
        }
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }


    public static String getBarcodeCountSelectData() {
        String barcodeCountSelectConf = ReportConstants.barcodeCount;
        if (barcodeCountSelectConf == null || barcodeCountSelectConf.length() == 0) {
            barcodeCountSelectConf = "3,6,9";
        }
        String[] confs = barcodeCountSelectConf.split(",");
        List<Map<String, Object>> selectData = new ArrayList<Map<String, Object>>();
        try {
            for (int i = 0; i < confs.length; i++) {
                Map<String, Object> select = new HashMap<String, Object>();
                selectData.add(select);
                select.put("id", i);
                select.put("text", Integer.parseInt(confs[i].trim()) + "次");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            barcodeCountSelectConf = "3,6,9";
            confs = barcodeCountSelectConf.split(",");
            selectData = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < confs.length; i++) {
                Map<String, Object> select = new HashMap<String, Object>();
                selectData.add(select);
                select.put("id", i);
                select.put("text", Integer.parseInt(confs[i].trim()) + "次");
            }
        }
        return JSON.toJSONString(selectData);
    }


    /**
     * 使用了正则表达式来判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(str).matches();
    }


    /**
     * 使用了正则表达式来判断字符串是否为中文
     *
     * @param str
     * @return
     */
    public static boolean isChineseCharacters(String str) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        return pattern.matcher(str).matches();
    }


    /**
     * 手动分页
     *
     * @param currentPage
     * @param pageSize
     * @param list
     * @return
     */
    public static Page getPages(Integer currentPage, Integer pageSize, List list) {
        Page page = new Page();
        if (list == null) {
            return null;
        }
        int size = list.size();

        if (pageSize > size) {
            pageSize = size;
        }
        if (pageSize != 0) {
            // 求出最大页数，防止currentPage越界
            int maxPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;

            if (currentPage > maxPage) {
                currentPage = maxPage;
            }
        }
        // 当前页第一条数据的下标
        int curIdx = currentPage > 1 ? (currentPage - 1) * pageSize : 0;

        List pageList = new ArrayList();

        // 将当前页的数据放进pageList
        for (int i = 0; i < pageSize && curIdx + i < size; i++) {
            pageList.add(list.get(curIdx + i));
        }

        page.setCurrent(currentPage).setSize(pageSize).setTotal(list.size()).setRecords(pageList);
        return page;
    }


    /**
     * 根据身份证号判断当前年龄
     *
     * @param cardNo
     * @return
     */
    public static int getAge(String cardNo) {
        if (StringUtils.isNotBlank(cardNo) && (cardNo.length() == 15 || cardNo.length() == 18)) {
            String birthday = cardNo.substring(6, 14);
            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            GregorianCalendar currentDay = new GregorianCalendar();
            currentDay.setTime(birthdate);
            int birYear = currentDay.get(Calendar.YEAR);

            // 获取年龄
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            String thisYear = simpleDateFormat.format(new Date());
            int age = Integer.parseInt(thisYear) - birYear;
            return age;
        } else {
            return 0;
        }
    }

    public static String randomFilePath(String fileType, String filename) {
        fileType = StringUtils.isNotBlank(fileType) ? fileType : "jpg";
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/" + ((StringUtils.isBlank(filename) ? (IdUtil.simpleUUID() + "." + fileType) : filename));
    }




    /**
     * 校验SABC字段值
     * @param str
     * @return
     */
    public static String validateSABC(String str) {
        String processedSabc = str.trim().toUpperCase();
        // 校验是否是SABC中的大写字母
        if (processedSabc.length() == 1 && "SABC".contains(processedSabc)) {
            return processedSabc;
        }else {
            return null;
        }
    }

}
