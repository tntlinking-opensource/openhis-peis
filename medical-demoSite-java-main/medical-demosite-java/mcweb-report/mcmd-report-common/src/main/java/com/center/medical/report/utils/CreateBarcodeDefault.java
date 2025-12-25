package com.center.medical.report.utils;

import org.apache.commons.codec.binary.Base64;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.BarcodeEncoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;

/**
 * 根据录入的字符串生成指定的一维条形码的byte[]
 *
 * @ClassName: createBarcodeDefault
 * @Description:
 * @author YINZL
 * @date 2016年12月8日 下午2:32:04
 *
 */
public class CreateBarcodeDefault {
	/**
	 * 产生一维码图片的byte[]
	 *
	 * @Title: createBarcode
	 * @param clazz
	 * @param value
	 * @param checkDigit
	 * @return byte[]
	 * @author YINZL
	 * @since 2016年12月8日 V 1.0
	 */
	/**
	 * 生成一维码
	 *
	 * @param value
	 *            值
	 * @return
	 */
	public static byte[] createBarcodeDefault(String value) {

		byte[] bytes = null;
		try {
			bytes = createBarcode(Code39Encoder.class, value, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public static byte[] createBarcode(Class<?> clazz, String value,
			boolean checkDigit) {
		try {
			JBarcode localJBarcode = new JBarcode(getInstance(clazz),
					WidthCodedPainter.getInstance(),
					EAN13TextPainter.getInstance());
			localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
			localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
			localJBarcode.setCheckDigit(checkDigit);
			localJBarcode.setShowCheckDigit(checkDigit);
			localJBarcode.setBarHeight(10);
			BufferedImage image = localJBarcode.createBarcode(value);
			// System.out.println(image.getHeight());
			// System.out.println(image.getWidth());
			return getBytes(image);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 获取单例的对象
	private static BarcodeEncoder getInstance(Class<?> clazz) throws Exception {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		Constructor<?> privateConstructor = constructors[0];
		privateConstructor.setAccessible(true);
		return (BarcodeEncoder) privateConstructor.newInstance();

	}

	// 获取图片字节码数组
	private static byte[] getBytes(BufferedImage paramBufferedImage)
			throws IOException {
		return ImageUtil.encode(paramBufferedImage, "jpeg", 96, 96);
	}

	/**
	 * 生成导引单条码base64
	 *
	 * @Title: getDydBarcode
	 * @param patientcode
	 * @return
	 * @throws Exception
	 *             String
	 * @author xuhp
	 * @since 2017-5-9 V 1.0
	 */
	public static String getDydBarcode(String patientcode) throws Exception {
		JBarcode localJBarcode = new JBarcode(getInstance(Code39Encoder.class),
				WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
		localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
		localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
		localJBarcode.setCheckDigit(false);
		localJBarcode.setShowCheckDigit(false);
		localJBarcode.setShowText(false);// 不显示下方文字
		localJBarcode.setBarHeight(10);
		byte[] bytes = getBytes(localJBarcode.createBarcode(patientcode));
		return "data:image/jpg;base64,"
		+ new String(Base64.encodeBase64(bytes), "UTF-8");
	}
	
	public static byte[] getHomePageBarcode(String patientcode) throws Exception {
		JBarcode localJBarcode = new JBarcode(getInstance(Code39Encoder.class),
				WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
		localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
		localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
		localJBarcode.setCheckDigit(false);
		localJBarcode.setShowCheckDigit(false);
		localJBarcode.setShowText(false);// 不显示下方文字
		localJBarcode.setBarHeight(8);
		return getBytes(localJBarcode.createBarcode(patientcode));
	}
}
