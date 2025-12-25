package com.center.medical.machine.bean.utils;

import com.alibaba.fastjson.JSON;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 神思身份证读卡器
 * 必须使用64位jdk、64位tomcat，否则会报错 1%不是win32程序
 * 官网：http://www.sdses.com/index.php?c=category&id=73
 * 官网账号：zq81207  123456 zq881207@126.com 18105010339119
 * int UCommand1(BYTE *pCmd, int * parg0, int * parg1,  int * parg2)
 * pCmd:  0x41: 初始化端口  0x42: 关闭端口  0x43: 验证卡  0x44: 读基本信息  0x45: 读最新住址信息  0x46: 仅读文字信息  0x47: 读基本信息但不进行图像解码  0x48: 读外国人永久居留证基本信息  0x49: 读二代身份证基本信息或外国人永久居留证基本信息
 * 1． 初始化端口   *parg0 ：串口号，取值 1~16     USB 口 取值 1001~1016      *parg0=0 时，自动查找端口范围串口 1~8，USB1001~1016 2
 */
@Slf4j
public class SsIdCardReadUtil {
    /**
     * 如果身份证，读卡成功后，可以使用一下函数直接获得文字信息
     * int GetName    (char *buf);           读取姓名
     * int GetSex      (char BYTE *buf);     读取性别编码
     * int GetFolk     (char BYTE *buf);     读取民族编码
     * int GetSexGB   (char BYTE *buf);     读取性别
     * int GetFolkGB   (char BYTE *buf);     读取民族
     * int GetBirth     (char BYTE *buf);     读取出生
     * int GetAddr     (char BYTE *buf);     读取住址
     * int GetIDNum   (char BYTE *buf);     读取公民身份号码
     * int GetDep      (char BYTE *buf);     读取签发机关
     * int GetBegin    (char BYTE *buf);     读取有效期起
     * int GetEnd      (char BYTE *buf);     读取有效期止
     * int GetNewAddr  (char BYTE *buf);    读取最新地址
     * int GetBmpPath  (char BYTE *buf);    读取头像图片路径
     * 6． 如果外国人永久居留证，读卡成功后，可以使用一下函数直接获得文字信息
     * int FID_GetEnName (char *buf);            读取英文姓名
     * int FID_ GetChName (char *buf);            读取中文姓名
     * int FID_ GetNationality (char *buf);          读取国籍
     * int FID_GetChNationality(char *pbuf)   读取国籍中文简称
     * int FID_GetSex (char BYTE *buf);       读取性别编码
     * int FID_GetSexGB (char BYTE *buf);      读取性别
     * int FID_GetBirth (char BYTE *buf);       读取出生
     * int FID_GetIDNum (char BYTE *buf);      读取公民身份号码
     * int FID_GetDep (char BYTE *buf);       读取签发机关
     * int FID_GetBegin (char BYTE *buf);       读取有效期起
     * int FID_GetEnd (char BYTE *buf);       读取有效期止
     * int FID_ GetVersion (char BYTE *buf);      读取证件版本号
     * int GetBmpPath  (char BYTE *buf);      读取头像图片路径
     * 各函数返回值为读取得信息的字节数 buf 为传入的字节数组，用于取得返回值。返回的数据文字信息为 GBK 编码，可以直接显示。 要读取这些信息，必须在读卡内信息成功后才有效！
     */
    public interface RdcardDll extends Library {
        //目录下单独一个RdCard.dll不能用，验证返回-9
        RdcardDll bell = (RdcardDll) Native.loadLibrary(
                IdCardReaderUtil.getPath() + "dll/shensi/RdCard.dll"
                , RdcardDll.class);

        int UCommand1(String pCmd, IntByReference prag0, IntByReference prag1,
                      byte[] prag2);

        int GetName(byte[] buf);

        int GetSexGB(byte[] buf);

        int GetFolkGB(byte[] buf);

        int GetAddr(byte[] buf);

        int GetIDNum(byte[] buf);

        int GetDep(byte[] buf);

        int GetBirth(byte[] buf);

        int GetBegin(byte[] buf);

        int GetEnd(byte[] buf);

        int GetBmpPath(byte[] buf);

        int FID_GetEnName(byte[] buf);

        int FID_GetChName(byte[] buf);

        int FID_GetNationality(byte[] buf);

        int FID_GetSex(byte[] buf);

        int FID_GetSexGB(byte[] buf);

        int FID_GetBirth(byte[] buf);

        int FID_GetIDNum(byte[] buf);

        int FID_GetDep(byte[] buf);

        int FID_GetBegin(byte[] buf);

        int FID_GetEnd(byte[] buf);

        int FID_GetVersion(byte[] buf);

    }

    public static String readIdCard() throws Exception {
        //要求当前工作目录下，必须有License.dat，官方文档中没有说明
        IdCardReaderUtil.putLisense(IdCardReaderUtil.SHENSI);

        String aa;
        aa = String.format("%c", 0x41);

        IntByReference bb = new IntByReference();
        bb.setValue(0);
        IntByReference cc = new IntByReference();
        cc.setValue(8811);


        byte[] dd = {0x02, 0x27, 0x00, 0x00};    //9986
        //0x41初始化端口

        RdcardDll bell = (RdcardDll) Native.loadLibrary(
                IdCardReaderUtil.getPath() + "dll/shensi/RdCard.dll"
                , RdcardDll.class);
        int csh = bell.UCommand1(aa, bb, cc, dd);
        log.info("文件地址:" + IdCardReaderUtil.getPath() + "dll/shensi/RdCard.dll");
        log.info("我是bell" + JSON.toJSONString(bell));
        IdCardReaderUtil.log("初始化端口:" + csh);
        if (62171 != csh) {
            IdCardReaderUtil.error("端口初始化失败，错误代码：" + csh);
        }

        //0x43验证卡
        aa = String.format("%c", 0x43);
        int yzk = RdcardDll.bell.UCommand1(aa, bb, cc, dd);
        IdCardReaderUtil.log("验证卡:" + yzk);
        //没有身份证，或身份证已读过，且没有拿下来重新放置，都是0
        if (0 == yzk) {
            //IdCardReaderUtil.error("验证卡失败，未放置身份证，或身份证摆放位置不正。");
        } else if (!(62171 == yzk || 62172 == yzk || 62173 == yzk)) {
            IdCardReaderUtil.error("验证卡失败，错误代码：" + yzk);
        }


        //读二代身份证基本信息或外国人永久居留证基本信息
        aa = String.format("%c", 0x49);
        //文件生成位置
//		String strPara2 ="D://SHENSI";
        String strPara2 = IdCardReaderUtil.USER_DIR + File.separator + IdCardReaderUtil.SHENSI;
        System.out.println(strPara2);
//		System.out.println(System.getProperty("file.encoding"));
        byte[] bytePara2 = strPara2.getBytes("GBK");
        int flag = RdcardDll.bell.UCommand1(aa, bb, cc, bytePara2);
        IdCardReaderUtil.log("读二代身份证基本信息或外国人永久居留证基本信息 :" + flag);
        if (flag == 65) {
            IdCardReaderUtil.error("验证卡失败，未放置身份证，或身份证摆放位置不正。");
        }
        //如果没有license，尝试重新创建license.dat
        if (flag == -3) {
            IdCardReaderUtil.rebuildLisense(IdCardReaderUtil.SHENSI);
            flag = RdcardDll.bell.UCommand1(aa, bb, cc, bytePara2);
            IdCardReaderUtil.log("重试读二代身份证基本信息或外国人永久居留证基本信息 :" + flag);
        }
        List<String> result = new ArrayList<String>();
        if ((flag == 62171) || (flag == 62172)) //身份证读取成功
        {

            byte[] name1 = new byte[20];
            RdcardDll.bell.GetName(name1);
            result.add(getString(name1));

            byte[] sex1 = new byte[20];
            RdcardDll.bell.GetSexGB(sex1);
            result.add(getString(sex1));

            byte[] min1 = new byte[20];
            RdcardDll.bell.GetFolkGB(min1);
            result.add(getString(min1));

            byte[] bir1 = new byte[20];
            RdcardDll.bell.GetBirth(bir1);
            result.add(IdCardReaderUtil.getDateStr(getString(bir1), "-"));

            byte[] addr = new byte[50];
            RdcardDll.bell.GetAddr(addr);
            result.add(getString(addr));

            byte[] idNum = new byte[50];
            RdcardDll.bell.GetIDNum(idNum);
            result.add(getString(idNum));

            byte[] dep1 = new byte[50];
            RdcardDll.bell.GetDep(dep1);
            result.add(getString(dep1));

            byte[] begin = new byte[20];
            RdcardDll.bell.GetBegin(begin);

            byte[] end1 = new byte[20];
            RdcardDll.bell.GetEnd(end1);

            result.add(IdCardReaderUtil.getDateStr(getString(begin), ".")
                    + "-"
                    + IdCardReaderUtil.getDateStr(getString(end1), "."));

            byte[] path1 = new byte[100];
            RdcardDll.bell.GetBmpPath(path1);
            String path = getString(path1);
            // 读取图片转base64
            File file2 = new File(path);
            if (!file2.exists()) {
                IdCardReaderUtil.error("{'status':0, 'text': '头像文件读取失败'}");
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

        } else if (flag == 62173) //外国人永久居留证读取成功
        {

            byte[] name1 = new byte[120];
            RdcardDll.bell.FID_GetEnName(name1);
            result.add(getString(name1));

            byte[] sex1 = new byte[20];
            RdcardDll.bell.FID_GetSexGB(sex1);
            result.add(getString(sex1));

            result.add(getString(sex1));

            byte[] bir1 = new byte[20];
            RdcardDll.bell.FID_GetBirth(bir1);
            result.add(IdCardReaderUtil.getDateStr(getString(bir1), "-"));


            byte[] addr = new byte[50];
            RdcardDll.bell.FID_GetChName(addr);
            result.add(getString(addr));

            byte[] idNum = new byte[50];
            RdcardDll.bell.FID_GetIDNum(idNum);
            result.add(getString(idNum));

            byte[] dep1 = new byte[20];
            RdcardDll.bell.FID_GetNationality(dep1);
            result.add(getString(dep1));

            byte[] begin = new byte[20];
            RdcardDll.bell.FID_GetBegin(begin);

            byte[] end1 = new byte[20];
            RdcardDll.bell.FID_GetEnd(end1);

            result.add(IdCardReaderUtil.getDateStr(getString(begin), ".")
                    + "-"
                    + IdCardReaderUtil.getDateStr(getString(end1), "."));

            byte[] path1 = new byte[50];
            RdcardDll.bell.GetBmpPath(path1);
            String path = getString(path1);
            // 读取图片转base64
            File file2 = new File(path);
            if (!file2.exists()) {
                IdCardReaderUtil.error("{'status':0, 'text': '头像文件读取失败'}");
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
        } else {
            IdCardReaderUtil.error("读取卡信息失败，错误代码：" + flag);
        }
        for (String str : result) {
            IdCardReaderUtil.log(str);
        }
        return JSON.toJSONString(result);
    }

    public static String reEncoding(String text, String newEncoding) {
        String rs = null;
        try {
            rs = new String(text.getBytes(), newEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void main(String[] args) throws Exception {
        SsIdCardReadUtil.readIdCard();
    }

    public static String getString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "GBK").trim();//解决乱码
        //return reEncoding(new String(bytes,"GBK"), "UTF-8").trim();
    }
}
