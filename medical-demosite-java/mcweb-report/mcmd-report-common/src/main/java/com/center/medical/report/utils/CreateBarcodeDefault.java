package com.center.medical.report.utils;

import org.apache.commons.codec.binary.Base64;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.BarcodeEncoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;

/**
 * 根据录入的字符串生成指定的一维条形码的byte[]
 * 适配JDK 1.8 + Windows/Linux双环境，修复Image read failed和InvalidFontException问题
 *
 * @ClassName: CreateBarcodeDefault
 * @Description: Code39一维条形码生成工具
 * @author YINZL
 * @modified 2026-01-08 适配双环境字体路径
 * @date 2016年12月8日 下午2:32:04
 */
public class CreateBarcodeDefault {
	// 新增日志（替代System.err，便于线上排查）
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateBarcodeDefault.class);
	// Code39支持的字符正则（统一常量，避免魔法值）
	private static final String CODE39_VALID_PATTERN = "[^0-9A-Z\\-\\. \\$\\/\\+\\%]";
	// 空值默认条形码内容
	private static final String DEFAULT_BARCODE_VALUE = "DEFAULT";
	// 图片格式常量（统一为PNG，避免JPG/PNG混用）
	private static final String IMAGE_FORMAT = "png";
	private static final int IMAGE_DPI = 96;

	// 静态代码块：自动适配Windows/Linux字体路径（核心修复）
	static {
		try {
			// 1. 判断当前操作系统
			String osName = System.getProperty("os.name").toLowerCase();
			String fontPath = "";
			String jasperFontDir = "";

			// 2. 适配不同系统的字体路径
			if (osName.contains("windows")) {
				// Windows系统：使用系统默认字体目录
				fontPath = "C:\\Windows\\Fonts";
				jasperFontDir = fontPath;
				LOGGER.info("当前系统：Windows，字体路径：{}", fontPath);
			} else if (osName.contains("linux")) {
				// Linux/容器系统：匹配Docker挂载的字体目录
				fontPath = "/usr/share/fonts/zh_CN";
				jasperFontDir = "/usr/share/fonts/zh_CN"; // 修正为容器内实际的字体目录
				LOGGER.info("当前系统：Linux（容器），字体路径：{}，Jasper字体目录：{}", fontPath, jasperFontDir);
			}else {
				LOGGER.warn("未知系统：{}，使用默认字体配置", osName);
			}

			// 3. 设置JVM字体路径（仅当路径有效时）
			if (!fontPath.isEmpty()) {
				System.setProperty("java.awt.fonts.path", fontPath);
				System.setProperty("sun.java2d.fontpath", fontPath);
			}

			// 4. 设置JasperReports字体路径（跨平台）
			if (!jasperFontDir.isEmpty()) {
				System.setProperty("jasperreports.fonts.dir", jasperFontDir);
			}

			// 5. 通用JVM配置（双环境兼容）
			System.setProperty("sun.java2d.opengl", "false"); // 禁用硬件加速
			System.setProperty("javax.imageio.useDefaultImageIOPlugins", "true"); // 强制内置ImageIO编码器
			System.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true"); // 忽略缺失字体（兜底）
			System.setProperty("net.sf.jasperreports.default.font.name", "SimHei"); // 默认字体

		} catch (Exception e) {
			LOGGER.error("初始化字体配置失败", e);
		}
	}

	/**
	 * 生成默认Code39格式一维码（无校验位）
	 * JDK 1.8适配：返回空字节数组而非null，避免Jasper接收null字节流报错
	 *
	 * @param value 条形码内容
	 * @return 条形码图片字节数组（PNG格式），异常时返回空数组
	 */
	public static byte[] createBarcodeDefault(String value) {
		try {
			byte[] bytes = createBarcode(Code39Encoder.class, value, false);
			// 校验字节流有效性，无效则返回空白图片
			return validateImageBytes(bytes);
		} catch (Exception e) {
			LOGGER.error("生成默认条形码失败，内容：{}", value, e);
			// 终极兜底：返回1x1空白PNG字节流
			return generateBlankPngBytes();
		}
	}

	/**
	 * 生成指定编码器的一维码
	 * JDK 1.8适配：异常返回空数组+字节流校验，避免null传递
	 *
	 * @param clazz      编码器类（如Code39Encoder.class）
	 * @param value      条形码内容
	 * @param checkDigit 是否启用校验位
	 * @return 条形码字节数组，异常返回空数组
	 */
	public static byte[] createBarcode(Class<?> clazz, String value, boolean checkDigit) {
		// 预处理文本，过滤非法字符（JDK 1.8适配isBlank逻辑）
		String validValue = preprocessCode39Text(value);

		try {
			JBarcode localJBarcode = new JBarcode(getInstance(clazz),
					WidthCodedPainter.getInstance(),
					EAN13TextPainter.getInstance());
			localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
			localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
			localJBarcode.setCheckDigit(checkDigit);
			localJBarcode.setShowCheckDigit(checkDigit);
			localJBarcode.setBarHeight(10);

			// 生成条形码图片（捕获JDK 1.8下的渲染异常）
			BufferedImage image = localJBarcode.createBarcode(validValue);
			// 替换ImageUtil为ImageIO（JDK 1.8标准API）
			return convertImageToBytes(image);
		} catch (InvalidAtributeException e) {
			LOGGER.error("生成条形码失败！编码器：{}，有效值：{}", clazz.getName(), validValue, e);
			return new byte[0];
		} catch (Exception e) {
			LOGGER.error("生成条形码异常，编码器：{}，有效值：{}", clazz.getName(), validValue, e);
			return new byte[0];
		}
	}

	/**
	 * Code39文本预处理：转大写+过滤非法字符+空值兜底
	 * JDK 1.8适配：替换isBlank()为手动判断（JDK 1.8 String无isBlank方法）
	 */
	private static String preprocessCode39Text(String originalValue) {
		// JDK 1.8 兼容isBlank逻辑：判断null/空字符串/全空格
		if (originalValue == null || originalValue.trim().length() == 0) {
			LOGGER.warn("条形码内容为空，使用默认值：{}", DEFAULT_BARCODE_VALUE);
			return DEFAULT_BARCODE_VALUE;
		}
		// 1. 转大写（解决小写字母问题）
		String upperValue = originalValue.toUpperCase();
		// 2. 过滤Code39不支持的字符
		String validValue = upperValue.replaceAll(CODE39_VALID_PATTERN, "");
		// 兜底：过滤后为空则用默认值（JDK 1.8兼容）
		if (validValue.trim().length() == 0) {
			LOGGER.warn("条形码内容过滤后为空（原始值：{}），使用默认值：{}", originalValue, DEFAULT_BARCODE_VALUE);
			validValue = DEFAULT_BARCODE_VALUE;
		}
		return validValue;
	}

	/**
	 * 反射获取编码器实例（JDK 1.8适配：兼容反射语法）
	 */
	private static BarcodeEncoder getInstance(Class<?> clazz) throws Exception {
		Constructor<?> constructor = clazz.getDeclaredConstructor();
		constructor.setAccessible(true); // JDK 1.8 允许访问私有构造器
		return (BarcodeEncoder) constructor.newInstance();
	}

	/**
	 * 原方法保留重载（JDK 1.8兼容），内部替换为标准ImageIO
	 */
	private static byte[] getBytes(BufferedImage paramBufferedImage) throws IOException {
		return convertImageToBytes(paramBufferedImage);
	}

	/**
	 * 核心修复：JDK 1.8下将BufferedImage转为PNG字节流（兼容Linux）
	 * 替代ImageUtil，使用JDK 1.8标准API
	 */
	private static byte[] convertImageToBytes(BufferedImage image) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			// JDK 1.8 显式指定PNG格式，强制使用内置编码器
			ImageIO.write(image, IMAGE_FORMAT, outputStream);
			outputStream.flush();
			return outputStream.toByteArray();
		} catch (Exception e) {
			LOGGER.error("JDK 1.8图片转字节流失败", e);
			return generateBlankPngBytes();
		}
	}

	/**
	 * 生成导引单条码Base64（JDK 1.8适配）
	 * 修复：兼容JDK 1.8的Charset，避免UTF-8编码问题
	 *
	 * @param patientcode 患者编码
	 * @return Base64编码的条形码（data:image/png;base64,xxx）
	 * @throws Exception 生成失败时抛出异常
	 */
	public static String getDydBarcode(String patientcode) throws Exception {
		String validPatientCode = preprocessCode39Text(patientcode);

		JBarcode localJBarcode = new JBarcode(getInstance(Code39Encoder.class),
				WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
		localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
		localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
		localJBarcode.setCheckDigit(false);
		localJBarcode.setShowCheckDigit(false);
		localJBarcode.setShowText(false);// 不显示下方文字
		localJBarcode.setBarHeight(10);

		byte[] bytes = convertImageToBytes(localJBarcode.createBarcode(validPatientCode));
		// 校验字节流有效性
		bytes = validateImageBytes(bytes);
		// JDK 1.8 兼容：使用StandardCharsets.UTF_8（JDK 1.8已支持）
		String base64Str = new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
		// 修正格式：PNG图片对应image/png，而非image/jpg
		return "data:image/png;base64," + base64Str;
	}

	/**
	 * 生成首页专用条形码（JDK 1.8适配）
	 *
	 * @param patientcode 患者编码
	 * @return 条形码字节数组
	 * @throws Exception 生成失败时抛出异常
	 */
	public static byte[] getHomePageBarcode(String patientcode) throws Exception {
		String validPatientCode = preprocessCode39Text(patientcode);

		JBarcode localJBarcode = new JBarcode(getInstance(Code39Encoder.class),
				WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
		localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
		localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
		localJBarcode.setCheckDigit(false);
		localJBarcode.setShowCheckDigit(false);
		localJBarcode.setShowText(false);// 不显示下方文字
		localJBarcode.setBarHeight(8);

		byte[] bytes = convertImageToBytes(localJBarcode.createBarcode(validPatientCode));
		// 校验字节流有效性，无效则返回空白图片
		return validateImageBytes(bytes);
	}

	// -------------------------- JDK 1.8 专属适配方法 --------------------------

	/**
	 * 校验图片字节数组是否有效（JDK 1.8适配，修复ImageIO.read兼容问题）
	 * 避免传递空/残缺字节流给Jasper
	 */
	private static byte[] validateImageBytes(byte[] imageBytes) {
		// 1. JDK 1.8 校验字节数组是否为null/空/过短（正常PNG至少50字节）
		if (imageBytes == null || imageBytes.length < 50) {
			LOGGER.warn("图片字节流无效（长度：{}），返回空白图片", imageBytes == null ? "null" : imageBytes.length);
			return generateBlankPngBytes();
		}
		// 2. JDK 1.8 校验是否为合法PNG格式（修复ImageIO.read兼容问题）
		try (ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes)) {
			// JDK 1.8 兼容写法：通过ByteArrayInputStream读取字节流（替代直接传byte[]）
			if (ImageIO.read(bais) == null) {
				LOGGER.warn("字节流无法解析为PNG图片，返回空白图片");
				return generateBlankPngBytes();
			}
		} catch (Exception e) {
			LOGGER.error("JDK 1.8校验图片字节流失败", e);
			return generateBlankPngBytes();
		}
		return imageBytes;
	}

	/**
	 * 生成1x1空白PNG字节流（JDK 1.8适配，终极兜底）
	 * 避免Jasper报Image read failed
	 */
	private static byte[] generateBlankPngBytes() {
		try {
			// JDK 1.8 兼容的BufferedImage创建方式（避免透明通道问题）
			BufferedImage blankImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = blankImage.createGraphics();
			// JDK 1.8 设置抗锯齿（避免图片渲染异常）
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1, 1);
			g.dispose();
			// 转为PNG字节流（JDK 1.8标准API）
			return convertImageToBytes(blankImage);
		} catch (Exception e) {
			LOGGER.error("JDK 1.8生成空白图片失败", e);
			return new byte[0];
		}
	}
}