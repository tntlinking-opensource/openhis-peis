package com.center.medical.common.utils;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.StrUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.catalina.manager.Constants.CHARSET;


/**
 * @program: mecreg
 * @description:
 * @author: yuane
 * @create: 2020-07-08 16:28
 */
public class QrCodeUtils {
    /**
     * TODO 根据给定的内容生成二维码
     *
     * @param content 二维码内容
     * @return 生成的二维码
     * @throws WriterException
     */
    public static BufferedImage createImage(String content)
            throws WriterException {
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  // 纠错等级
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);  // 二维码两边空白区域大小
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
        return image;
    }

    /**
     * @description: 生成带有底部文字说明的二维码，返回不带前缀的base64
     * @param text 二维码内容
     * @param remark 二维码底部文字描述
     * @param logoFile 二维码logo文件
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param font 二维码底部文字描述字体
     * @param margin 二维码边距
     */
    public static String generateQRCodeBase64(String text, String remark, File logoFile, Integer width, Integer height ,Integer margin, String font, Integer fontSize) throws Exception {
        // 设置二维码参数
        if(width==null)width=100;
        if(height==null)height=100;
        if(margin==null)margin=1;
        if(fontSize==null)fontSize=10;
        if(StrUtil.isEmpty(font))font="宋体";//宋体生成的文字比黑体、华文楷体好看
        Map<EncodeHintType, Object> hints = new ConcurrentHashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 高纠错等级
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, margin); // 边框

        // 创建BitMatrix对象
        BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        // 动态计算增加的高度用于备注文本
        int textHeight = (remark != null && !remark.isEmpty()) ? 30 : 0;

        // 创建带有二维码和备注文本的 BufferedImage
        BufferedImage qrImage = new BufferedImage(width, height + textHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = qrImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height + textHeight); // 设置背景为白色

        // 绘制二维码到 BufferedImage
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrImage.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        // 添加Logo到二维码
        if (logoFile != null && logoFile.exists()) {
            addLogoToQRCode(qrImage, logoFile, width, height);
        }

        // 添加备注文本
        if (remark != null && !remark.isEmpty()) {
            addTextToQRCode(qrImage, remark, width, height, textHeight,font,fontSize);
        }

        return ImgUtil.toBase64(qrImage,"png");

        // 保存二维码图片到指定路径
//        Path filePath = Paths.get(path);
//        Files.createDirectories(filePath.getParent()); // 确保目录存在
//        ImageIO.write(qrImage, "PNG", new File(path));
    }

    // 添加Logo到二维码
    private static void addLogoToQRCode(BufferedImage qrImage, File logoFile, int qrWidth, int qrHeight) throws Exception {
        // 读取Logo图片
        BufferedImage logoImage = ImageIO.read(logoFile);

        // 计算Logo的缩放比例和新宽高
        int logoWidth = Math.min(logoImage.getWidth(), qrWidth / 5);  // 缩小至二维码宽度的1/5
        int logoHeight = Math.min(logoImage.getHeight(), qrHeight / 5);

        // 计算Logo绘制的左上角位置，使其居中
        int x = (qrWidth - logoWidth) / 2;
        int y = (qrHeight - logoHeight) / 2;

        // 绘制白色边框背景
        Graphics2D g2 = qrImage.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x - 5, y - 5, logoWidth + 10, logoHeight + 10, 10, 10);

        // 绘制Logo到二维码中心
        g2.drawImage(logoImage.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH), x, y, null);
        g2.dispose();
    }

    // 在二维码底部添加文本
    private static void addTextToQRCode(BufferedImage image, String text, int width, int qrHeight, int textHeight,String font, int fontSize) {
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.BLACK);
        g2.setFont(new Font(font, Font.PLAIN, fontSize));  // 设置字体

        // 获取文本的宽度以便居中对齐
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (width - textWidth) / 2;

        // 调整 y 坐标位置，将文本稍微上移
        int padding = 10;  // 增加一个 padding 值，让文本上移一点，避免贴得太近
        int y = qrHeight + (textHeight - fm.getHeight()) / 2 + fm.getAscent() - padding;

        // 绘制文本
        g2.drawString(text, x, y);
        g2.dispose(); // 释放资源
    }
}