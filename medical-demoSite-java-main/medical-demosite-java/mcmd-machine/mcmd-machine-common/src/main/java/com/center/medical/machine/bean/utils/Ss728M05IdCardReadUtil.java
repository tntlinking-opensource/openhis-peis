package com.center.medical.machine.bean.utils;

import com.alibaba.fastjson.JSON;
import com.sun.jna.Library;
import com.sun.jna.Native;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 神思身份证读卡器  SS728M05
 * 推荐读卡流程：打开设备卡片复位读卡获得数据关闭设备
 * license.dat似乎要求放到c盘根目录下
 * 必须使用32位jdk,myeclilpse-installed jre -java 1.8.0_211 win32环境下测试。
 * 出现乱码原因：参数类型不正确，应该用int，实际使用了long，应该用byte[]，实际使用了byte[]
 * 具体参考c#demo
 */
public class Ss728M05IdCardReadUtil {
    public interface RdcardDll extends Library {
        //目录下单独一个RdCard.dll不能用，验证返回-9
        RdcardDll bell = (RdcardDll) Native.loadLibrary(
                IdCardReaderUtil.getPath() + "dll/shensi/SS728M05_SDK.dll"
                , RdcardDll.class);

        /**
         * 功能：打开设备。
         * 参数说明：无
         * 返回值：大于零的设备句柄。(注意小写)
         */
        int ss_reader_open();

        /**
         * 该功能：关闭设备。
         * 参数说明：icdev：设备句柄；
         * 返回值：参照通用返回值。(注意小写)
         */
        int ss_reader_close(int ReaderHandle);

        /**
         * 复位
         * 参数说明：
         * (1) icdev：设备句柄。
         * 返回值：如果函数执行成功，则返回值为0；否则，表示函数执行失败，返回错误代码。
         */
        int ss_id_ResetID2Card(int icdev);

        /**
         * 用于读取身份证卡信息，调用成功后，读出的身份证信息将保存在库内部存储中，可以使用以 ss_id_query_开头的函数去读取这些内部存储中的信息。
         * 参数说明：
         * (1) icdev：设备句柄。
         * (2) Flag：如果是0，读取基本信息数据，如果是其他值则读取基本信息加指纹信息数据。
         * 返回值：如果函数执行成功，则返回值为0；否则，表示函数执行失败，返回错误代码。
         */
        int ss_id_read_card(int icdev, int Flag);

        /**
         * 从库内部存储中取身份证卡的姓名信息。
         * 参数说明：
         * (1) icdev：设备句柄。
         * (2) _Name：姓名，不超过15个汉字，以'\0'结束。
         * 返回值：如果函数执行成功，则返回值为0；否则，表示函数执行失败，返回错误代码。
         */
        int ss_id_query_name(int icdev, byte[] _Name);

        /**
         * 性别名称，1个汉字，以'\0'结束。
         */
        int ss_id_query_sexL(int icdev, byte[] _Sex);

        /**
         * 从库内部存储中取身份证卡的性别编码信息。
         */
        int ss_id_query_sex(int icdev, byte[] _Sex);

        /**
         * 民族名称，不超过5个汉字，譬如'汉'、'蒙古'等，以'\0'结束
         */
        int ss_id_query_folkL(int icdev, byte[] _Folk);

        /**
         * 国家标准的民族编码，2个数字字符，譬如‘01’表示汉族等，以'\0'结束。
         */
        int ss_id_query_folk(int icdev, byte[] _Folk);

        /**
         * _Date：出生日期，8个数字字符，格式为YYYYMMDD，以'\0'结束。
         */
        int ss_id_query_birth(int icdev, byte[] _Date);

        /**
         * 住址信息，不超过35个汉字，以'\0'结束。
         */
        int ss_id_query_address(int icdev, byte[] _Addr);

        /**
         * 身份号码，18个数字字符，以'\0'结束。
         */
        int ss_id_query_number(int icdev, byte[] _Number);

        /**
         * 从库内部存储中取身份证卡的签发机关信息。
         */
        int ss_id_query_organ(int icdev, byte[] _Organ);

        /**
         * _Date：有效期限起始日期，8个数字字符，格式为YYYYMMDD，以'\0'结束。
         */
        int ss_id_query_termbegin(int icdev, byte[] _Date);

        /**
         * _Dat：有效期限截止日期，8个数字字符，格式为YYYYMMDD，以'\0'结束。
         */
        int ss_id_query_termend(int icdev, byte[] _Date);

        /**
         * 从库内部存储中取身份证卡的照片数据信息，存储为指定格式的数据流。
         * 参数说明：
         * (1) icdev：设备句柄。
         * (2) _Format：照片格式，取值0或1(0表示WLT格式；1表示BMP格式)。
         * (3) ImageData：照片的数据。
         * (4) ImageLen：照片数据的长度。
         * 返回值：如果函数执行成功，则返回值为0；否则，表示函数执行失败，返回错误代码。
         */
        int ss_id_query_photo_data(int icdev, int _Format, byte[] ImageData, int[] ImageLen);

        /**
         * 功能：从库内部存储中取身份证卡的照片数据信息，根据指定的文件绝对路径存储为指定格式的文件。
         * 参数说明：
         * (1) icdev：设备句柄。
         * (2) _Format：照片格式，取值0或1(0表示WLT格式；1表示BMP格式)。
         * (3) ImagePath：照片路径，必须是全路径，譬如‘d:\temp\zhangsan’,。如果照片格式为0，则生成‘d:\temp\zhangsan-id2.wlt’，未解码图片。如果照片格式为1，则生成‘d:\temp\zhangsan-id2.bmp’，已解码图片。
         * 返回值：如果函数执行成功，则返回值为0；否则，表示函数执行失败，返回错误代码。
         */
        int ss_id_query_photo_file(int icdev, int _Format, String ImagePath);

        int ss_id_query_IDBaseInfo_text(int icdev, byte[] _Text);

    }

    public static String readIdCard() throws Exception {
        //将License.dat放到c盘根目录
        IdCardReaderUtil.putLisense(IdCardReaderUtil.SHENSIX);

        //开启设备
        int csh = RdcardDll.bell.ss_reader_open();//设备句柄
        IdCardReaderUtil.log("开启设备:" + csh);
        if (csh <= 0) {
            IdCardReaderUtil.error("开启设备失败，错误代码：" + csh);
        }

        //卡片复位
        int fwres = RdcardDll.bell.ss_id_ResetID2Card(csh);
        IdCardReaderUtil.log("卡片复位:" + fwres);
        if (fwres != 0) {
            IdCardReaderUtil.error("卡片复位失败，错误代码：" + fwres);
        }

        //读取卡片数据至内存
        int readres = RdcardDll.bell.ss_id_read_card(csh, 0);
        IdCardReaderUtil.log("读取卡信息:" + readres);
        if (readres != 0) {
            IdCardReaderUtil.error("读取卡信息失败，错误代码：" + readres);
        }

        //处理卡信息
        List<String> result = new ArrayList<String>();
        byte[] name1 = new byte[36];
        int nl = RdcardDll.bell.ss_id_query_name(csh, name1);
        if (nl != 0) {
            IdCardReaderUtil.error("读取姓名失败，错误代码：" + nl);
        }
        String name = getString(name1);
        System.out.println(name);
        result.add(name);

        byte[] sexp = new byte[2];
        nl = RdcardDll.bell.ss_id_query_sexL(csh, sexp);
        if (nl != 0) {
            IdCardReaderUtil.error("读取性别失败，错误代码：" + nl);
        }
        String sex = getString(sexp);
        System.out.println(sex);
        result.add(sex);

        byte[] min = new byte[12];
        nl = RdcardDll.bell.ss_id_query_folkL(csh, min);
        if (nl != 0) {
            IdCardReaderUtil.error("读取民族失败，错误代码：" + nl);
        }
        String folk = getString(min);
        System.out.println(folk);
        result.add(folk);

        byte[] birp = new byte[12];
        nl = RdcardDll.bell.ss_id_query_birth(csh, birp);
        if (nl != 0) {
            IdCardReaderUtil.error("读取生日失败，错误代码：" + nl);
        }
        String bir = IdCardReaderUtil.getDateStr(getString(birp), "-");
        System.out.println(bir);
        result.add(bir);

        byte[] addrp = new byte[128];
        nl = RdcardDll.bell.ss_id_query_address(csh, addrp);
        if (nl != 0) {
            IdCardReaderUtil.error("读取地址失败，错误代码：" + nl);
        }
        String add = getString(addrp);
        System.out.println(add);
        result.add(add);

        byte[] idp = new byte[24];
        nl = RdcardDll.bell.ss_id_query_number(csh, idp);
        if (nl != 0) {
            IdCardReaderUtil.error("读取身份证号码失败，错误代码：" + nl);
        }
        String idn = getString(idp);
        System.out.println(idn);
        result.add(idn);

        byte[] depp = new byte[32];
        nl = RdcardDll.bell.ss_id_query_organ(csh, depp);
        if (nl != 0) {
            IdCardReaderUtil.error("读取发卡机构失败，错误代码：" + nl);
        }
        String dep = getString(depp);
        System.out.println(dep);
        result.add(dep);

        byte[] beginp = new byte[10];
        nl = RdcardDll.bell.ss_id_query_termbegin(csh, beginp);
        String be = IdCardReaderUtil.getDateStr(getString(beginp), ".");
        System.out.println(be);
        if (nl != 0) {
            IdCardReaderUtil.error("读取有效期失败，错误代码：" + nl);
        }

        byte[] endp = new byte[10];
        nl = RdcardDll.bell.ss_id_query_termend(csh, endp);
        String end = IdCardReaderUtil.getDateStr(getString(endp), ".");
        System.out.println(end);
        if (nl != 0) {
            IdCardReaderUtil.error("读取有效期失败，错误代码：" + nl);
        }

        result.add(be
                + "-"
                + end);
        String folderPath = "D:\\photo\\"
                + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "\\";
        ;
        File foleder = new File(folderPath);
        if (!foleder.exists()) {
            foleder.mkdirs();
        }
        String photopath = folderPath + idn;
        nl = RdcardDll.bell.ss_id_query_photo_file(csh, 1, photopath);
        photopath = photopath + "-id2.bmp";
        if (nl != 0) {
            IdCardReaderUtil.error("读取照片失败，错误代码：" + nl);
        }
        System.out.println(photopath);
        // 读取图片转base64
        File file2 = new File(photopath);
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

        //关闭设备
        int closeres = RdcardDll.bell.ss_reader_close(csh);
        IdCardReaderUtil.log("关闭设备:" + closeres);
        if (closeres != 0) {
            IdCardReaderUtil.error("关闭失败，错误代码：" + closeres);
        }

//		for(String str:result){
//			IdCardReaderUtil.log(str);
//		}
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
        Ss728M05IdCardReadUtil.readIdCard();
    }

    public static String getString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "gb2312").trim();//解决乱码
    }
}
