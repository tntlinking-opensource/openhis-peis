/**
 * @StringUtil.java
 * @com.lingnet.util
 * @Description：
 * @author xuhp
 * @copyright 2019
 * @version V
 * @since 2019年3月1日
 */
package com.center.medical.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.center.medical.common.bean.param.OnlineGenerateParam;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.common.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * 体检号工具
 *
 * @author 路飞
 */
@Slf4j
public class CodeUtil {


    /**
     * 生成唯一的体检号
     *
     * @param jm      分中心简码
     * @param version 版本号标识
     * @return 体检号
     */
    public static String getPatientCode(String jm, String version) {
//        Long data = RedisUtil.getSerialNumber(Constants.PATIENT_CODE_REDIS_KEY, false);
//        data = data + 30000000;
//        StringBuilder sb = new StringBuilder(jm);
//        sb.append(version);
//        sb.append(String.format("%08d", data));
//        log.info("生成唯一的体检号：{}", sb);
//        return sb.toString();
        boolean online = ZhongkangConfig.isOnline();
//        线上直接生成，线下从缓存里面拿
        if (online) {
            return generateCode(jm, version);
        } else {
            String firstElementAsString = "";
            Set<Object> set = RedisSetUtil.queryByRange(Constants.PATIENT_CODE_SET, 0L, 0L);
            if (set.isEmpty()) {
                //重新从线上生成体检号
                String url = "http://123.249.111.34:8080" + Constants.ONLINE_SYNC_DATA_GENERATECODE;
                //发送请求
                String s = HttpUtils.sendPost(url, JSONUtil.toJsonStr(new OnlineGenerateParam(jm, version, 500)));
                log.info("请求的数据是:{}", s);
                JSONArray jsonArray = JSONUtil.parseArray(s);
                //插入本地redis
                for (Object patientCode : jsonArray) {
                    RedisSetUtil.addToSortedSet(Constants.PATIENT_CODE_SET, patientCode, DateUtil.currentSeconds());
                }

            }
            //重新获取
            set = RedisSetUtil.queryByRange(Constants.PATIENT_CODE_SET, 0L, 0L);
            Object[] array = set.toArray();
            if (array.length > 0) {
                Object firstElement = array[0];
                firstElementAsString = String.valueOf(firstElement);
                log.info("缓存中的体检号是：{}", firstElementAsString);
                //删除使用过的体检号
                RedisSetUtil.removeRange(Constants.PATIENT_CODE_SET, 0L, 0L);
            } else {
                throw new ServiceException("生成体检号异常!");
            }

            if (!"APP".equals(jm)) {
                if (firstElementAsString.startsWith("APP")) {
                    if (StringUtils.isNotBlank(ZhongkangConfig.getFzxjm())) {
//                        resetRedisPatentCode(ZhongkangConfig.getFzxjm());
                        firstElementAsString = ZhongkangConfig.getFzxjm() + (firstElementAsString.substring(3));
                    } else {
//                        resetRedisPatentCode(jm.substring(0, 2));
                        firstElementAsString = jm.substring(0, 2) + (firstElementAsString.substring(3));
                    }
                }
            }

            return firstElementAsString;
        }

    }

    /**
     * 将redis缓存中的以APP开头的体检号改为当前分中心简码开头的体检号
     *
     * @param jm
     * @return
     */

    public static Boolean resetRedisPatentCode(String jm) {
        Set<Object> set = RedisSetUtil.queryByRange(Constants.PATIENT_CODE_SET, 0L, null);
        System.out.println(JSONUtil.toJsonStr(set));
        for (Object item : set) {
            String code = (String) item;
            System.out.println(code);
            if (code.startsWith("APP")) {
                String newcode = jm + (code.substring(3));
                RedisSetUtil.remove(Constants.PATIENT_CODE_SET, item);
                RedisSetUtil.addToSortedSet(Constants.PATIENT_CODE_SET, newcode, DateUtil.currentSeconds());
            }
        }
        return Boolean.TRUE;
    }

    public static void main(String[] args) {
        System.out.println(getShortCodeByLong("3750"));
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
     * @param patientcode
     * @return Integer
     * @Title: getShortCodeForApp
     * @author xuhp
     * @since 2017年9月25日 V 1.0
     */
    public static Integer getShortCodeForApp(String patientcode) {
        Integer shortCode = null;
        if (StringUtils.isNotEmpty(patientcode)) {
            if (patientcode.length() == 13) {
                shortCode = Integer.parseInt(patientcode.substring(3));
            } else {
                shortCode = Integer.parseInt(patientcode);
            }
        }
        return shortCode;
    }

    /**
     * 生成唯一的档案号
     *
     * @param jm      分中心简码
     * @param version 版本号标识
     * @return 体检号
     */
    public static String getArchiveCode(String jm, String version) {
        boolean online = ZhongkangConfig.isOnline();
        if (online) {
            Long data = RedisUtil.getSerialNumber(Constants.ARCHIVE_NO_REDIS_KEY, false);
            StringBuilder sb = new StringBuilder(jm);
            sb.append(version);
            sb.append(MathUtil.getRandom(10));
            sb.append(String.format("%08d", data));
            log.info("生成唯一的档案号：{}", sb);
            return sb.toString();
        }else {
            String firstElementAsString = "";
            Set<Object> set = RedisSetUtil.queryByRange(Constants.PATIENT_ARCHIVE_CODE_SET, 0L, 0L);
            if (set.isEmpty()) {
                //重新从线上生成体检号
                String url = "http://123.249.111.34:8080" + Constants.ONLINE_SYNC_DATA_GENERATEARCHIVECODE;
                String s = HttpUtils.sendPost(url, JSONUtil.toJsonStr(new OnlineGenerateParam(jm, version, 500)));
                JSONArray jsonArray = JSONUtil.parseArray(s);
                //插入本地redis
                for (Object patientCode : jsonArray) {
                    RedisSetUtil.addToSortedSet(Constants.PATIENT_ARCHIVE_CODE_SET, patientCode, DateUtil.currentSeconds());
                }
                //重新获取
                set = RedisSetUtil.queryByRange(Constants.PATIENT_ARCHIVE_CODE_SET, 0L, 0L);
            }
            Object[] array = set.toArray();
            if (array.length > 0) {
                Object firstElement = array[0];
                firstElementAsString = String.valueOf(firstElement);
                log.info("缓存中的档案号是：{}", firstElementAsString);
                RedisSetUtil.removeRange(Constants.PATIENT_ARCHIVE_CODE_SET, 0L, 0L);
            } else {
                throw new ServiceException("生成档案号异常!");
            }

            return firstElementAsString;
        }

    }

    /**
     * 生成今天的自增的订单号
     *
     * @param jm      分中心简码
     * @param version 版本号标识
     * @return 体检号
     */
    public static String getOrderNum(String jm, String version) {
        Long data = RedisUtil.getSerialNumber(Constants.ORDER_NUM_REDIS_KEY, true);
        data += 10000;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%06d", data));
        log.info("生成今天的自增的订单号：{}", sb);
        return sb.toString();
    }

    /**
     * 生成今天的预约号
     *
     * @param flag 预约来源标识，如：KT11(康淘)、平安（PA11）、东区线上（DQ11）、东区线上（DQ10），线上统一是:简码+系统版本号+1+当天序号，线下统一是：简码+系统版本号+0+当天序号
     * @return 体检号
     */
    public static String getReservationNo(String flag) {
        Long data = RedisUtil.getSerialNumber(Constants.RESERVATION_NO_REDIS_KEY, true);
        StringBuilder sb = new StringBuilder(flag);
        sb.append(data);
        log.info("生成今天的预约号：{}", sb);
        return sb.toString();
    }

    /**
     * 生成唯一的数字形式团体ID
     *
     * @return 体检号
     */
    public static Integer getIntId() {
        Long intId = RedisUtil.getSerialNumber(Constants.INT_ID_REDIS_KEY, false);
        log.info("生成唯一的数字形式团体ID：{}", intId);
        return Math.toIntExact(intId);
    }

//    public static void main(String[] args) {
//        System.out.println(Math.toIntExact(1000L));
//    }

    /**
     * 生成虹桥lis中间库需要的每日重置的递增号码
     *
     * @return
     */
    public static int getNumDay() {
        return RedisUtil.getSerialNumber(Constants.HQ_NUM_DAY_KEY, true).intValue();
    }

    /**
     * 使用numDay生成虹桥lis中间库每日自增流水号
     *
     * @param numDay
     * @return
     */
    public static String getPatientCodeHiden(int numDay) {
        StringBuffer sb = new StringBuffer(LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")));
        sb.append(String.format("%5d", numDay).replace(" ", "0"));
        return sb.toString();
    }

    /**
     * 生成虹桥lis自增号码
     *
     * @return
     */
    private static int getHqLisNum(String key) {
        return RedisUtil.getAtomicLong(key, null).intValue();
    }

    /**
     * 生成虹桥lis中间库需要的检查项目自增号码
     *
     * @return
     */
    public static int getHqIdPatientExamItem() {
        return getHqLisNum(Constants.HQ_ID_PATIENT_EXAM_ITEM_KEY);
    }

    /**
     * 生成虹桥lis中间库需要的收费项目自增号码
     *
     * @return
     */
    public static int getHqIdPatientFeeItem() {
        return getHqLisNum(Constants.HQ_ID_PATIENT_FEE_ITEM);
    }

    /**
     * 生成虹桥lis中间库需要的常规科室收费项目自增号码
     *
     * @return
     */
    public static int getHqIdPatientTraceExamDepart() {
        return getHqLisNum(Constants.HQ_ID_PATIENT_TRACE_EXAM_DEPART);
    }


//    public static void main(String[] args) {
//        System.out.println(DateUtil.format(new Date(), "MMdd"));
//    }


    /**
     * 生成体检号
     *
     * @param jm
     * @param version
     * @return
     */
    public static String generateCode(String jm, String version) {
        Long data = RedisUtil.getSerialNumber(Constants.PATIENT_CODE_REDIS_KEY, false);
        data = data + 30000000;
        StringBuilder sb = new StringBuilder(jm);
        sb.append(version);
        sb.append(String.format("%08d", data));
        log.info("生成唯一的体检号：{}", sb);
        return sb.toString();
    }


    /**
     * App转换为正常的体检号
     *
     * @param jm
     * @param version
     * @param patientCode
     * @return
     */
    public static String appConvert(String jm, String version, String patientCode) {
        //APP0230000207
        //010230000208
        //截取后8位，然后添加简码和版本号
        String result = patientCode.substring(patientCode.length() - 8);
        StringBuilder sb = new StringBuilder(jm);
        sb.append(version);
        sb.append(result);
        log.info(" App转换为正常的体检号：{}", sb);
        return sb.toString();
    }
}
