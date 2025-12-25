package com.center.medical.machine.bean.utils;

/**
 * 华视身份证读卡器
 */
public class HsIdCardReaderUtil {

	public static String readIdCard() throws Exception{
//		String idCardPath = ReportConstants.IdCardPath;
//        // 初始化操作
//        String cardmark = ReportConstants.cardmark;
//        int result = CVR_InitComm(Integer.valueOf(cardmark));
//        System.out.println(result);
//        if (1==result) {
//            result = CVR_Authenticate();
//            System.out.println(result);
//            if (1==result) {
//                result = CVR_Read_Content(4);
//                System.out.println(result);
//                if (1==result) {
//                    // 获取termb.dll下存放的卡片信息和头像
//                    File file = new File(idCardPath+"/wz.txt");
//                    if (!file.exists()) {
//                        String text = "{\"status\":0, \"text\": \"文件不存在\"}";
//                        throw new RuntimeException(text);
//                    }
//                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB2312"));
//                    List<String> list = new ArrayList<String>();
//                    String line = null;
//                    while ((line = br.readLine()) != null) {
//                        list.add(line);
//                    }
//                    // 读取图片转base64
//                    File file2 = new File(idCardPath+"/zp.bmp");
//                    if (!file2.exists()) {
//                        String text = "{\"status\":0, \"text\": \"头像文件不存在\"}";
//                        throw new RuntimeException(text);
//                    }
//                    FileInputStream in = new FileInputStream(file2);
//                    byte[] buffer = new byte[in.available()];
//                    in.read(buffer);
//                    ByteArrayOutputStream out = new ByteArrayOutputStream();
//                    out.write(buffer);
//                    BASE64Encoder decoder = new BASE64Encoder();
//                    String enPicture = decoder.encode(buffer);
//                    list.add("data:image/bmp;base64," + enPicture);
//                    in.close();
//                    br.close();
//                    return JsonUtil.toJson(list);
//                } else {
//                    String text = "{\"status\":0, \"text\": \"读卡失败\"}";
//                    throw new RuntimeException(text);
//                }
//            } else if (0==result) {
//                String text = "{\"status\":0, \"text\": \"初始化失败\"}";
//                throw new RuntimeException(text);
//            } else {
//                String text = "{\"status\":0, \"text\": \"读卡失败\"}";
//                throw new RuntimeException(text);
//            }
//        } else if (3==result) {
//            String text = "{\"status\":0, \"text\": \"动态库加载失败\"}";
//            throw new RuntimeException(text);
//        } else {//将dll下所有的dll文件拷贝到tomcat/bin下
//            String text = "{\"status\":0, \"text\": \"端口打开失败\"}";
//            throw new RuntimeException(text);
//        }
        return null;
	}

//	/**
//     * @Title: 初始化连接
//     * @param port 端口
//     * @return int 1:正确 2:端口打开失败 3:动态库加载失败
//     * @author zhanghj
//     * @since 2016-12-12 V 1.0
//     */
//    private static int CVR_InitComm(int port) {
//        JNative n = null;
//        try {
//            n = new JNative("Termb.dll", "CVR_InitComm");
//            n.setRetVal(Type.INT); // 指定返回参数的类型
//            n.setParameter(0, port);
//            n.invoke(); // 调用方法
//            return Integer.parseInt(n.getRetVal());
//        } catch (Throwable e) {
//            return 2;
//        }
//    }
//
//    /**
//     * @Title: 读卡器和卡片之间的合法身份确认。卡认证循环间隔大于300ms。
//     * @return
//     * int 0:初始化失败 1:卡片认证成功 2:寻卡失败 3:选卡失败
//     * @author zhanghj
//     * @since 2016-12-12 V 1.0
//     */
//    private static int CVR_Authenticate() {
//        JNative n = null;
//        try {
//            n = new JNative("Termb.dll", "CVR_Authenticate");
//            n.setRetVal(Type.INT); // 指定返回参数的类型
//            n.invoke(); // 调用方法
//            System.out.println("n.invoke():"+n.getRetVal());
//            System.out.println("n.invoke():"+n.getRetValAsInt());
//            return Integer.parseInt(n.getRetVal());
//        } catch (Throwable e) {
//        	System.out.println("CVR_Authenticate");
//        	e.printStackTrace();
//            return 2;
//        }
//    }
//
//    /**
//     * @Title: 阅读器从第二代居民身份证中读取相应信息。卡认证成功以后才可做读卡操作(wz.txt、zp.bmp)
//     * @param Active 兼容以前版本，无实际意义
//     * @return
//     * int 0:错误 1:正确 99:异常
//     * @author zhanghj
//     * @since 2016-12-12 V 1.0
//     */
//    private static int CVR_Read_Content(int Active) {
//        JNative n = null;
//        try {
//            n = new JNative("Termb.dll", "CVR_Read_Content");
//            n.setRetVal(Type.INT); // 指定返回参数的类型
//            n.setParameter(0, Active);
//            n.invoke(); // 调用方法
//            return Integer.parseInt(n.getRetVal());
//        } catch (Throwable e) {
//        	e.printStackTrace();
//            return 0;
//        }
//    }
}
