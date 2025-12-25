package com.center.medical.machine.bean.utils;

import com.alibaba.fastjson.JSON;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 华旭身份证读卡器
 * 官网：http://www.hxgc.com.cn/class/view?id=19
 * 二代证机具开发包（含港澳台）只支持32位系统,需要用32位jre,测试使用1.8.0_211（32位）
 * 参考代码form1.cs
 * <p>
 * 宋豪
 * 男
 * 汉
 * 2002-02-20
 * 山东省青岛市黄岛区宝山镇前沟98号
 * 370284200202205310
 * 青岛市公安局黄岛分局
 * 2017.07.21-2022.07.21
 * 图片
 */
public class HxIdCardReadUtil {
    private static final int IPORT = 1001;
    private static final int IIFOPEN = 1;
    private static final int iIsSaveToBmp = 1;//1会在license.dat所在目录生成照片

    public interface HxInterface extends Library {
        HxInterface INSTANCE = (HxInterface)
                Native.loadLibrary(IdCardReaderUtil.getPath()
                                + "dll/huaxu/sdtapi.dll",
                        HxInterface.class);

        int SDT_StartFindIDCard(int iPort, byte[] byManaID, int iIfOpen);

        int SDT_SelectIDCard(int iPort, byte[] byManaID, int iIfOpen);

        int SDT_ReadBaseFPMsg(int iPort, byte[] byCHMsg, IntByReference uiCHMsgSize
                , byte[] byPHMsg, IntByReference uiPHMsgSize, byte[] byFPMsg
                , IntByReference uiFPMsgSize, int iIfOpen);

        int SDT_ReadBaseMsg(int IPORT
                , byte[] byCHMsg
                , IntByReference UICHMSGSIZE
                , byte[] byPHMsg
                , IntByReference UIPHMSGSIZE
                , int IIFOPEN);
    }

    public interface DllFileInterface extends Library {
        DllFileInterface INSTANCE = (DllFileInterface)
                Native.loadLibrary(IdCardReaderUtil.getPath()
                                + "dll/huaxu/DLL_File.dll",
                        DllFileInterface.class);

        int unpack(byte[] byPHMsg, byte[] byBgrBuffer, int iIsSaveToBmp);
    }

    public static String readIdCard()
            throws Exception {
        List<String> result = new ArrayList<String>();
        IdCardReaderUtil.log("reading...");
        //找卡
        byte[] byManaID = new byte[8];
        int findIdCardResult = HxInterface.INSTANCE.SDT_StartFindIDCard(IPORT
                , byManaID
                , IIFOPEN);
        IdCardReaderUtil.log("findIdCardResult:" + findIdCardResult);
        if (0x9F != findIdCardResult) {
            String msg = "寻卡失败，错误代码：{0:" + findIdCardResult + "}";
            IdCardReaderUtil.error(msg);
        }
        //选卡
        int selectIdCardResult = HxInterface.INSTANCE.SDT_SelectIDCard(IPORT
                , byManaID
                , IIFOPEN);
        IdCardReaderUtil.log("selectIdCardResult:" + selectIdCardResult);
        if (0x90 != selectIdCardResult) {
            String msg = "选卡失败，错误代码：{0:" + selectIdCardResult + "}";
            IdCardReaderUtil.error(msg);
        }
        //读取身份证个人基本信息、照片信息和指纹信息；
        byte[] byCHMsg = new byte[256 + 1];        //个人基本信息
        byte[] byPHMsg = new byte[1024 + 1];       //照片信息
        byte[] byFPMsg = new byte[1024 + 1];
        IntByReference UICHMSGSIZE = new IntByReference(0);
        IntByReference UIPHMSGSIZE = new IntByReference(0);
        IntByReference UIFPMSGSIZE = new IntByReference(0);
        //c#中ref uint在java中应该是IntByReference
        int readResult = HxInterface.INSTANCE.SDT_ReadBaseFPMsg(IPORT
                , byCHMsg, UICHMSGSIZE, byPHMsg
                , UIPHMSGSIZE, byFPMsg
                , UIFPMSGSIZE
                , IIFOPEN);
//		IdCardReaderUtil.log("readResult:"+readResult);
        /*华旭 FDX3S无法读取身份证，报错33*/
        if (0x21 == readResult)//0501模块(一种老模块)无法读取指纹信息,会返回0x21错误，这里进行兼容处理；这种模块早就不用了，实际可以不做处理。
        {
            //System.out.println("0501模块(一种老模块)无法读取指纹信息,会返回0x21错误，这里进行兼容处理；这种模块早就不用了，实际可以不做处理。");
            readResult = HxInterface.INSTANCE.SDT_ReadBaseMsg(IPORT
                    , byCHMsg
                    , UICHMSGSIZE
                    , byPHMsg
                    , UIPHMSGSIZE
                    , IIFOPEN);//采用只读卡信息和照片，不读指纹信息的接口
        } else if (0x90 != readResult) {
            String msg = "读取身份信息失败，错误代码：{0:" + readResult + "}";
            IdCardReaderUtil.error(msg);
        }
        //解码照片数据，获得BGR格式数据
        //解码库需要依赖授权文件（license.dat）；要确保“当前工作目录下”license.dat文件存在且正确，否则会返回-22和-12的错误
        IdCardReaderUtil.putLisense(IdCardReaderUtil.HUAXU);
        byte[] byBgrBuffer = new byte[38556];    //解码后图片BGR编码值
        int unpackResult = DllFileInterface.INSTANCE.unpack(byPHMsg
                , byBgrBuffer
                , iIsSaveToBmp);
        IdCardReaderUtil.log("unpackResult:" + unpackResult);
        if (1 != unpackResult) {
            String msg = "照片解码失败，错误代码：{0:" + unpackResult + "}";
            IdCardReaderUtil.error(msg);
        }

        //截断数据获得证件类型标识
        byte[] byFgnCardSign = new byte[2];       //证件类型标识
        copyArray(byCHMsg, 248, byFgnCardSign, 0, 2);
        //判断类型标识，更新UI显示信息
        //大写字母'I'表示为外国人居住证，卡片返回身份信息数据默认为宽字符，这里采用直接判断字节的方法（宽字符大写字母'I'由2字节组成，分别为0x49 0x00）
        if ((byFgnCardSign[0] == 0x49) && (byFgnCardSign[1] == 0x00)) {
            byte[] byFgnNameEN = new byte[120];     //Name EN
            byte[] byFgnSex = new byte[2];          //Sex
            byte[] byFgnCardNo = new byte[30];      //Card No
            byte[] byFgnNation = new byte[6];       //Nationality
            byte[] byFgnNameCN = new byte[30];      //Name CN
            byte[] byFgnValidityBeg = new byte[16]; //Period of Validity Begin
            byte[] byFgnValidityEnd = new byte[16]; //Period of Validity End
            byte[] byFgnBirth = new byte[16];        //Date of Birth
            byte[] byFgnCardVer = new byte[4];      //Card Version
            byte[] byFgnIssAuth = new byte[8];      //Issuing Authority

            copyArray(byCHMsg, 0, byFgnNameEN, 0, 120);
            copyArray(byCHMsg, 120, byFgnSex, 0, 2);
            copyArray(byCHMsg, 122, byFgnCardNo, 0, 30);
            copyArray(byCHMsg, 152, byFgnNation, 0, 6);
            copyArray(byCHMsg, 158, byFgnNameCN, 0, 30);
            copyArray(byCHMsg, 188, byFgnValidityBeg, 0, 16);
            copyArray(byCHMsg, 204, byFgnValidityEnd, 0, 16);
            copyArray(byCHMsg, 220, byFgnBirth, 0, 16);
            copyArray(byCHMsg, 236, byFgnCardVer, 0, 4);
            copyArray(byCHMsg, 240, byFgnIssAuth, 0, 8);

            //显示结果
            result.add(getString(byFgnNameCN));
            result.add(getSex(getString(byFgnSex)));
            result.add("");
            result.add(getString(byFgnBirth));
            result.add("");
            result.add(getString(byFgnCardNo));
            result.add(getString(byFgnIssAuth));
            result.add(getString(byFgnValidityBeg) + "-" + getString(byFgnValidityEnd));
//            strIDBase += "英文姓名： " + Encoding.Unicode.GetString(byFgnNameEN).Trim() + "\r\n\r\n";
//            strIDBase += "性别： " + Encoding.Unicode.GetString(byFgnSex).Trim() + "\r\n\r\n";
//            strIDBase += "永久居留证号： " + Encoding.Unicode.GetString(byFgnCardNo).Trim() + "\r\n\r\n";
//            strIDBase += "国籍或所在地区代码： " + Encoding.Unicode.GetString(byFgnNation).Trim() + "\r\n\r\n";
//            strIDBase += "中文姓名： " + Encoding.Unicode.GetString(byFgnNameCN).Trim() + "\r\n\r\n";
//            strIDBase += "证件签发日期： " + Encoding.Unicode.GetString(byFgnValidityBeg).Trim() + "\r\n\r\n";
//            strIDBase += "证件终止日期： " + Encoding.Unicode.GetString(byFgnValidityEnd).Trim() + "\r\n\r\n";
//            strIDBase += "出生日期： " + Encoding.Unicode.GetString(byFgnBirth).Trim() + "\r\n\r\n";
//            strIDBase += "证件版本号： " + Encoding.Unicode.GetString(byFgnCardVer).Trim() + "\r\n\r\n";
//            strIDBase += "当此申请受理机关代码： " + Encoding.Unicode.GetString(byFgnIssAuth).Trim() + "\r\n\r\n";
//            strIDBase += "证件类型标识： " + Encoding.Unicode.GetString(byFgnCardSign).Trim() + "\r\n\r\n";
        } else if ((byFgnCardSign[0] == 0x4A) && (byFgnCardSign[1] == 0x00))//大写字母'I'（0x4A 0x00）表示为港澳台居住证
        {
            //截取个人信息数据。信息采用UNICODE存储，具体格式参可见《港澳台居民居住证机读信息规范（试行版）》
            byte[] byName = new byte[30];
            byte[] bySex = new byte[2];
            byte[] byBirth = new byte[16];
            byte[] byAddress = new byte[70];
            byte[] byID = new byte[36];
            byte[] byCompany = new byte[30];
            byte[] byBeginDate = new byte[16];
            byte[] byEndDate = new byte[16];
            byte[] byPassID = new byte[18];
            byte[] byIssNum = new byte[4];

            copyArray(byCHMsg, 0, byName, 0, 30);
            copyArray(byCHMsg, 30, bySex, 0, 2);
            copyArray(byCHMsg, 36, byBirth, 0, 16);
            copyArray(byCHMsg, 52, byAddress, 0, 70);
            copyArray(byCHMsg, 122, byID, 0, 36);
            copyArray(byCHMsg, 158, byCompany, 0, 30);
            copyArray(byCHMsg, 188, byBeginDate, 0, 16);
            copyArray(byCHMsg, 204, byEndDate, 0, 16);
            copyArray(byCHMsg, 220, byPassID, 0, 18);
            copyArray(byCHMsg, 238, byIssNum, 0, 4);

            //显示结果
            result.add(getString(byName));
            result.add(getSex(getString(bySex)));
            result.add("");
            result.add(getString(byBirth));
            result.add(getString(byAddress));
            result.add(getString(byID));
            result.add(getString(byCompany));
            result.add(getString(byBeginDate) + "-" + getString(byEndDate));
//            strIDBase = "读卡成功.\r\n\r\n";
//            strIDBase += "姓名： " + Encoding.Unicode.GetString(byName).Trim() + "\r\n\r\n";
//            strIDBase += "性别： " + Encoding.Unicode.GetString(bySex).Trim() + "\r\n\r\n";
//            strIDBase += "出生日期： " + Encoding.Unicode.GetString(byBirth).Trim() + "\r\n\r\n";
//            strIDBase += "居住地址： " + Encoding.Unicode.GetString(byAddress).Trim() + "\r\n\r\n";
//            strIDBase += "公民身份证号： " + Encoding.Unicode.GetString(byID).Trim() + "\r\n\r\n";
//            strIDBase += "签发机关： " + Encoding.Unicode.GetString(byCompany).Trim() + "\r\n\r\n";
//            strIDBase += "有效起始日期： " + Encoding.Unicode.GetString(byBeginDate).Trim() + "\r\n\r\n";
//            strIDBase += "有效截止日期： " + Encoding.Unicode.GetString(byEndDate).Trim() + "\r\n\r\n";
//            strIDBase += "通行证号码： " + Encoding.Unicode.GetString(byPassID).Trim() + "\r\n\r\n";
//            strIDBase += "签发次数： " + Encoding.Unicode.GetString(byIssNum).Trim() + "\r\n\r\n";
//            strIDBase += "证件类型标识： " + Encoding.Unicode.GetString(byFgnCardSign).Trim() + "\r\n\r\n";
        } else {
            //截取个人信息数据。信息采用UNICODE存储，具体格式参可见《二代证机读信息说明.doc》
            byte[] byName = new byte[30];
            byte[] bySex = new byte[2];
            byte[] byRace = new byte[4];
            byte[] byBirth = new byte[16];
            byte[] byAddress = new byte[70];
            byte[] byID = new byte[36];
            byte[] byCompany = new byte[30];
            byte[] byBeginDate = new byte[16];
            byte[] byEndDate = new byte[16];

            copyArray(byCHMsg, 0, byName, 0, 30);
            copyArray(byCHMsg, 30, bySex, 0, 2);
            copyArray(byCHMsg, 32, byRace, 0, 4);
            copyArray(byCHMsg, 36, byBirth, 0, 16);
            copyArray(byCHMsg, 52, byAddress, 0, 70);
            copyArray(byCHMsg, 122, byID, 0, 36);
            copyArray(byCHMsg, 158, byCompany, 0, 30);
            copyArray(byCHMsg, 188, byBeginDate, 0, 16);
            copyArray(byCHMsg, 204, byEndDate, 0, 16);

            //显示结果
            result.add(getString(byName));
            result.add(getSex(getString(bySex)));
            result.add(getNation(getString(byRace)));
            result.add(getString(byBirth));
            result.add(getString(byAddress));
            result.add(getString(byID));
            result.add(getString(byCompany));
            result.add(getString(byBeginDate) + "-" + getString(byEndDate));
//            strIDBase = "读卡成功.\r\n\r\n";
//            strIDBase += "姓名： " + Encoding.Unicode.GetString(byName).Trim() + "\r\n\r\n";
//            strIDBase += "性别： " + Encoding.Unicode.GetString(bySex).Trim() + "\r\n\r\n";
//            strIDBase += "民族： " + Encoding.Unicode.GetString(byRace).Trim() + "\r\n\r\n";
//            strIDBase += "出生日期： " + Encoding.Unicode.GetString(byBirth).Trim() + "\r\n\r\n";
//            strIDBase += "居住地址： " + Encoding.Unicode.GetString(byAddress).Trim() + "\r\n\r\n";
//            strIDBase += "公民身份证号： " + Encoding.Unicode.GetString(byID).Trim() + "\r\n\r\n";
//            strIDBase += "签发机关： " + Encoding.Unicode.GetString(byCompany).Trim() + "\r\n\r\n";
//            strIDBase += "有效起始日期： " + Encoding.Unicode.GetString(byBeginDate).Trim() + "\r\n\r\n";
//            strIDBase += "有效截止日期： " + Encoding.Unicode.GetString(byEndDate).Trim() + "\r\n\r\n";
        }
        // 读取图片转base64
        File file2 = new File(IdCardReaderUtil.USER_DIR + "/zp.bmp");
        if (!file2.exists()) {
            IdCardReaderUtil.error("{'status':0, 'text': '头像文件不存在'}");
        }
        try (FileInputStream in = new FileInputStream(file2);
             ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            out.write(buffer);
            BASE64Encoder decoder = new BASE64Encoder();
            String enPicture = decoder.encode(buffer);
            result.add("data:image/bmp;base64," + enPicture);
        }
        for (String str : result) {
            IdCardReaderUtil.log(str);
        }
        return JSON.toJSONString(result);
    }

    public static void copyArray(byte[] source, int sourceIndex
            , byte[] target, int targetIndex, int length) {
        for (int i = 0; i < length; i++) {
            target[targetIndex + i] = source[sourceIndex + i];
        }
    }

    /**
     * c#中unicode就是utf-16 ，对应java是utf-16le
     */
    public static String getString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "utf-16le").trim();
    }

    /**
     * 所有类型身份证读卡器匹配  性别返回男或女
     */
    public static String getSex(String idSex) {
        return "1".equals(idSex) ? "男" : "女";
    }

    /**
     * 所有类型身份证读卡器匹配  民族不包含族字
     * 01	汉		02	蒙古		03	回 	    04	藏  	05	维吾尔
     * 06	苗  	07	彝  		08	壮  	09	布依	10	朝鲜
     * 11	满  	12	侗  		13	瑶  	14	白  	15	土家
     * 16	哈尼	17	哈萨克	    18	傣  	19	黎  	20	傈僳
     * 21	佤  	22	畲  		23	高山	24	拉祜	25	水
     * 26	东乡	27	纳西		28	景颇	29	柯尔克孜30	土
     * 31	达斡尔	32	仫佬	    33	羌  	34	布朗	35	撒拉
     * 36	毛南	37	仡佬	    38	锡伯	39	阿昌	40	普米
     * 41	塔吉克	42	怒  	    43	乌孜别克44	俄罗斯
     * 45	鄂温克	46	德昂	    47	保安	48	裕固	49	京
     * 50	塔塔尔	51	独龙	    52	鄂伦春	53	赫哲	54	门巴
     * 55	珞巴	56	基诺
     */
    public static final Map<String, String> nations = new HashMap<String, String>() {
        private static final long serialVersionUID = 4855294381253030454L;

        {
            put("01", "汉");
            put("02", "蒙古");
            put("03", "回");
            put("04", "藏");
            put("05", "维吾尔");
            put("06", "苗");
            put("07", "彝");
            put("08", "壮");
            put("09", "布依");
            put("10", "朝鲜");
            put("11", "满");
            put("12", "侗");
            put("13", "瑶");
            put("14", "白");
            put("15", "土家");
            put("16", "哈尼");
            put("17", "哈萨克");
            put("18", "傣");
            put("19", "黎");
            put("20", "傈僳");
            put("21", "佤");
            put("22", "畲");
            put("23", "高山");
            put("24", "拉祜");
            put("25", "东乡");
            put("26", "纳西");
            put("27", "景颇");
            put("28", "柯尔克孜");
            put("29", "土");
            put("30", "达斡尔");
            put("31", "仫佬");
            put("32", "羌");
            put("33", "布朗");
            put("34", "撒拉");
            put("35", "毛南");
            put("36", "仡佬");
            put("37", "锡伯");
            put("38", "阿昌");
            put("39", "普米");
            put("40", "塔吉克");
            put("41", "怒");
            put("42", "乌孜别克");
            put("43", "俄罗斯");
            put("44", "鄂温克");
            put("45", "德昂");
            put("46", "保安");
            put("47", "裕固");
            put("48", "京");
            put("49", "塔塔尔");
            put("50", "独龙");
            put("51", "鄂伦春");
            put("52", "赫哲");
            put("53", "门巴");
            put("54", "珞巴");
            put("55", "基诺");
            put("56", "布朗");
        }
    };

    public static String getNation(String idNation) {
        return nations.get(idNation);
    }

    public static void main(String[] args) {
        try {
//			JNative n = new JNative("D:\\sdtapi.dll", "SDT_StartFindIDCard");
//			System.out.println(new File(Thread.currentThread()
//					.getContextClassLoader()
//					.getResource("")
//					.getPath()
//					).exists()
//					);
            //readIdCard();
            System.out.println(IdCardReaderUtil.getPath() + "dll/shensi/RdCard.dll"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
