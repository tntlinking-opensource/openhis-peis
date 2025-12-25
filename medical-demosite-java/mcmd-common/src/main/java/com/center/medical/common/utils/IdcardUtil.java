package com.center.medical.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证工具类
 */
public class IdcardUtil {
    private static final int[] POWER = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final Map<String, String> CITY_CODES = new HashMap();
    private static final Map<Character, Integer> TW_FIRST_CODE = new HashMap();

    /**
     * 严格校验身份证号
     *
     * @param idCard
     * @return
     */
    public static boolean isValidCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return false;
        } else {
            idCard = idCard.trim();
            int length = idCard.length();
            switch (length) {
                case 10:
                    String[] cardVal = isValidCard10(idCard);
                    return null != cardVal && "true".equals(cardVal[2]);
                case 15:
                    return isValidCard15(idCard);
                case 18:
                    return isValidCard18(idCard);
                default:
                    return false;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(isValidCard("44162219930119131X"));
    }

    public static boolean isValidCard18(String idcard) {
        return isValidCard18(idcard, true);
    }

    public static final Pattern NUMBERS = Pattern.compile("\\d+");

    public static boolean isValidCard18(String idcard, boolean ignoreCase) {
        if (18 != idcard.length()) {
            return false;
        } else {
            String proCode = idcard.substring(0, 2);
            if (null == CITY_CODES.get(proCode)) {
                return false;
            } else if (!isBirthday(idcard.substring(6, 14))) {
                return false;
            } else {
                String code17 = idcard.substring(0, 17);
                if (isMatch(NUMBERS, code17)) {
                    char val = getCheckCode18(code17);
                    return equals(val, idcard.charAt(17), ignoreCase);
                } else {
                    return false;
                }
            }
        }
    }

    public static boolean isValidCard15(String idcard) {
        if (15 != idcard.length()) {
            return false;
        } else if (isMatch(NUMBERS, idcard)) {
            String proCode = idcard.substring(0, 2);
            if (null == CITY_CODES.get(proCode)) {
                return false;
            } else {
                return isBirthday("19" + idcard.substring(6, 12));
            }
        } else {
            return false;
        }
    }

    public static String[] isValidCard10(String idcard) {
        if (StringUtils.isEmpty(idcard)) {
            return null;
        } else {
            String[] info = new String[3];
            String card = idcard.replaceAll("[()]", "");
            if (card.length() != 8 && card.length() != 9 && idcard.length() != 10) {
                return null;
            } else {
                if (idcard.matches("^[a-zA-Z][0-9]{9}$")) {
                    info[0] = "台湾";
                    char char2 = idcard.charAt(1);
                    if ('1' == char2) {
                        info[1] = "M";
                    } else {
                        if ('2' != char2) {
                            info[1] = "N";
                            info[2] = "false";
                            return info;
                        }

                        info[1] = "F";
                    }

                    info[2] = isValidTWCard(idcard) ? "true" : "false";
                } else if (idcard.matches("^[157][0-9]{6}\\(?[0-9A-Z]\\)?$")) {
                    info[0] = "澳门";
                    info[1] = "N";
                } else {
                    if (!idcard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$")) {
                        return null;
                    }

                    info[0] = "香港";
                    info[1] = "N";
                    info[2] = isValidHKCard(idcard) ? "true" : "false";
                }

                return info;
            }
        }
    }

    public static boolean isValidTWCard(String idcard) {
        if (null != idcard && idcard.length() == 10) {
            Integer iStart = (Integer) TW_FIRST_CODE.get(idcard.charAt(0));
            if (null == iStart) {
                return false;
            } else {
                int sum = iStart / 10 + iStart % 10 * 9;
                String mid = idcard.substring(1, 9);
                char[] chars = mid.toCharArray();
                int iflag = 8;
                char[] var6 = chars;
                int var7 = chars.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    char c = var6[var8];
                    sum += Integer.parseInt(String.valueOf(c)) * iflag;
                    --iflag;
                }

                String end = idcard.substring(9, 10);
                return (sum % 10 == 0 ? 0 : 10 - sum % 10) == Integer.parseInt(end);
            }
        } else {
            return false;
        }
    }

    public static boolean isValidHKCard(String idcard) {
        String card = idcard.replaceAll("[()]", "");
        int sum;
        if (card.length() == 9) {
            sum = (Character.toUpperCase(card.charAt(0)) - 55) * 9 + (Character.toUpperCase(card.charAt(1)) - 55) * 8;
            card = card.substring(1, 9);
        } else {
            sum = 522 + (Character.toUpperCase(card.charAt(0)) - 55) * 8;
        }

        String mid = card.substring(1, 7);
        String end = card.substring(7, 8);
        char[] chars = mid.toCharArray();
        int iflag = 7;
        char[] var7 = chars;
        int var8 = chars.length;

        for (int var9 = 0; var9 < var8; ++var9) {
            char c = var7[var9];
            sum += Integer.parseInt(String.valueOf(c)) * iflag;
            --iflag;
        }

        if ("A".equalsIgnoreCase(end)) {
            sum += 10;
        } else {
            sum += Integer.parseInt(end);
        }

        return sum % 11 == 0;
    }

    private static char getCheckCode18(String code17) {
        int sum = getPowerSum(code17.toCharArray());
        return getCheckCode18(sum);
    }

    private static char getCheckCode18(int iSum) {
        switch (iSum % 11) {
            case 0:
                return '1';
            case 1:
                return '0';
            case 2:
                return 'X';
            case 3:
                return '9';
            case 4:
                return '8';
            case 5:
                return '7';
            case 6:
                return '6';
            case 7:
                return '5';
            case 8:
                return '4';
            case 9:
                return '3';
            case 10:
                return '2';
            default:
                return ' ';
        }
    }

    private static int getPowerSum(char[] iArr) {
        int iSum = 0;
        if (POWER.length == iArr.length) {
            for (int i = 0; i < iArr.length; ++i) {
                iSum += Integer.parseInt(String.valueOf(iArr[i])) * POWER[i];
            }
        }

        return iSum;
    }

    static {
        CITY_CODES.put("11", "北京");
        CITY_CODES.put("12", "天津");
        CITY_CODES.put("13", "河北");
        CITY_CODES.put("14", "山西");
        CITY_CODES.put("15", "内蒙古");
        CITY_CODES.put("21", "辽宁");
        CITY_CODES.put("22", "吉林");
        CITY_CODES.put("23", "黑龙江");
        CITY_CODES.put("31", "上海");
        CITY_CODES.put("32", "江苏");
        CITY_CODES.put("33", "浙江");
        CITY_CODES.put("34", "安徽");
        CITY_CODES.put("35", "福建");
        CITY_CODES.put("36", "江西");
        CITY_CODES.put("37", "山东");
        CITY_CODES.put("41", "河南");
        CITY_CODES.put("42", "湖北");
        CITY_CODES.put("43", "湖南");
        CITY_CODES.put("44", "广东");
        CITY_CODES.put("45", "广西");
        CITY_CODES.put("46", "海南");
        CITY_CODES.put("50", "重庆");
        CITY_CODES.put("51", "四川");
        CITY_CODES.put("52", "贵州");
        CITY_CODES.put("53", "云南");
        CITY_CODES.put("54", "西藏");
        CITY_CODES.put("61", "陕西");
        CITY_CODES.put("62", "甘肃");
        CITY_CODES.put("63", "青海");
        CITY_CODES.put("64", "宁夏");
        CITY_CODES.put("65", "新疆");
        CITY_CODES.put("71", "台湾");
        CITY_CODES.put("81", "香港");
        CITY_CODES.put("82", "澳门");
        CITY_CODES.put("83", "台湾");
        CITY_CODES.put("91", "国外");
        TW_FIRST_CODE.put('A', 10);
        TW_FIRST_CODE.put('B', 11);
        TW_FIRST_CODE.put('C', 12);
        TW_FIRST_CODE.put('D', 13);
        TW_FIRST_CODE.put('E', 14);
        TW_FIRST_CODE.put('F', 15);
        TW_FIRST_CODE.put('G', 16);
        TW_FIRST_CODE.put('H', 17);
        TW_FIRST_CODE.put('J', 18);
        TW_FIRST_CODE.put('K', 19);
        TW_FIRST_CODE.put('L', 20);
        TW_FIRST_CODE.put('M', 21);
        TW_FIRST_CODE.put('N', 22);
        TW_FIRST_CODE.put('P', 23);
        TW_FIRST_CODE.put('Q', 24);
        TW_FIRST_CODE.put('R', 25);
        TW_FIRST_CODE.put('S', 26);
        TW_FIRST_CODE.put('T', 27);
        TW_FIRST_CODE.put('U', 28);
        TW_FIRST_CODE.put('V', 29);
        TW_FIRST_CODE.put('X', 30);
        TW_FIRST_CODE.put('Y', 31);
        TW_FIRST_CODE.put('W', 32);
        TW_FIRST_CODE.put('Z', 33);
        TW_FIRST_CODE.put('I', 34);
        TW_FIRST_CODE.put('O', 35);
    }

    public static final Pattern BIRTHDAY = Pattern.compile("^(\\d{2,4})([/\\-.年]?)(\\d{1,2})([/\\-.月]?)(\\d{1,2})日?$");

    public static boolean isBirthday(int year, int month, int day) {
        int thisYear = thisYear();
        if (year >= 1900 && year <= thisYear) {
            if (month >= 1 && month <= 12) {
                if (day >= 1 && day <= 31) {
                    if (day == 31 && (month == 4 || month == 6 || month == 9 || month == 11)) {
                        return false;
                    } else if (month != 2) {
                        return true;
                    } else {
                        return day < 29 || day == 29 && isLeapYear(year);
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static int thisYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static boolean isBirthday(CharSequence value) {
        Matcher matcher = BIRTHDAY.matcher(value);
        if (matcher.find()) {
            int year = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(3));
            int day = Integer.parseInt(matcher.group(5));
            return isBirthday(year, month, day);
        } else {
            return false;
        }
    }

    public static boolean isLeapYear(int year) {
        return (new GregorianCalendar()).isLeapYear(year);
    }

    public static boolean isMatch(Pattern pattern, CharSequence content) {
        return content != null && pattern != null ? pattern.matcher(content).matches() : false;
    }

    public static boolean equals(char c1, char c2, boolean ignoreCase) {
        if (ignoreCase) {
            return Character.toLowerCase(c1) == Character.toLowerCase(c2);
        } else {
            return c1 == c2;
        }
    }
}
