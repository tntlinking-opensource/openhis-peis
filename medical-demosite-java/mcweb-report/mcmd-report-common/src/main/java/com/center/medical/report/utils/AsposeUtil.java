package com.center.medical.report.utils;

import com.aspose.words.*;

import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * aspose合并pdf
 * 问题  :
 * ireport表格部分，转pdf后右侧多出一块。文字 有的加粗有的不加粗
 * poi生成的表格，列宽度会变成平均分
 */
public class AsposeUtil {
    public static void main(String[] args) {
        word2pdf("C:/Users/Administrator/Desktop/1.docx", "C:/Users/Administrator/Desktop/xxxx.pdf");//word转pdf
        System.out.println("end");
        //word转图片格式
//	    	try {
//				File file = new File("C:/Users/Administrator/Desktop/1.doc");
//	 
//				InputStream inStream = new FileInputStream(file);
//				List<BufferedImage> wordToImg = wordToImg(inStream,2);//
//				BufferedImage mergeImage = mergeImage(false, wordToImg);
//	 
//				ImageIO.write(mergeImage, "jpg"
//						, new File("C:/Users/Administrator/Desktop/xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.png")); //将其保存在C:/imageSort/targetPIC/下
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
    }


    /**
     * @Description: 验证aspose.word组件是否授权：无授权的文件有水印标记
     */
    public static boolean isWordLicense() {
        boolean result = false;
        try {
            String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(s.getBytes());
            //InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("license.xml");
            com.aspose.words.License license = new com.aspose.words.License();
            license.setLicense(inputStream);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @Description: word和txt文件转换图片
     */
    public static List<BufferedImage> wordToImg(InputStream inputStream, int pageNum) throws Exception {
        if (!isWordLicense()) {
            return null;
        }
        try {
            Document doc = new Document(inputStream);
            ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG);
            options.setPrettyFormat(true);
            options.setUseAntiAliasing(true);
            options.setUseHighQualityRendering(true);
            int pageCount = doc.getPageCount();
            if (pageCount > pageNum) {//生成前pageCount张
                pageCount = pageNum;
            }
            List<BufferedImage> imageList = new ArrayList<BufferedImage>();
            for (int i = 0; i < pageCount; i++) {
                OutputStream output = new ByteArrayOutputStream();
                options.setPageIndex(i);

                doc.save(output, options);
                ImageInputStream imageInputStream = javax.imageio.ImageIO.createImageInputStream(parse(output));
                imageList.add(javax.imageio.ImageIO.read(imageInputStream));

            }
            return imageList;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //outputStream转inputStream
    public static ByteArrayInputStream parse(OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }


    /**
     * 合并任数量的图片成一张图片
     *
     * @param isHorizontal true代表水平合并，fasle代表垂直合并
     * @param imgs         待合并的图片数组
     * @return
     * @throws IOException
     */
    public static BufferedImage mergeImage(boolean isHorizontal, List<BufferedImage> imgs) throws IOException {
        // 生成新图片
        BufferedImage destImage = null;
        // 计算新图片的长和高
        int allw = 0, allh = 0, allwMax = 0, allhMax = 0;
        // 获取总长、总宽、最长、最宽
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);
            allw += img.getWidth();

            if (imgs.size() != i + 1) {
                allh += img.getHeight() + 5;
            } else {
                allh += img.getHeight();
            }


            if (img.getWidth() > allwMax) {
                allwMax = img.getWidth();
            }
            if (img.getHeight() > allhMax) {
                allhMax = img.getHeight();
            }
        }
        // 创建新图片
        if (isHorizontal) {
            destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);
        } else {
            destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D g2 = (Graphics2D) destImage.getGraphics();
        g2.setBackground(Color.LIGHT_GRAY);
        g2.clearRect(0, 0, allw, allh);
        g2.setPaint(Color.RED);

        // 合并所有子图片到新图片
        int wx = 0, wy = 0;
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);
            int w1 = img.getWidth();
            int h1 = img.getHeight();
            // 从图片中读取RGB
            int[] ImageArrayOne = new int[w1 * h1];
            ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
            if (isHorizontal) { // 水平方向合并
                destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            } else { // 垂直方向合并
                destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            }
            wx += w1;
            wy += h1 + 5;
        }


        return destImage;
    }

	/**
	 * java.lang.IllegalStateException: Invalid license signature. Please make sure the license file was not modified.
	 */
    public static void word2pdf(String docPath, String savePath) {

        try {
            String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
            ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
            License license = new License();
            license.setLicense(is);
            com.aspose.words.Document document = new com.aspose.words.Document(docPath);
            /**这样转出来的pdf非常大，有十几mb,正常情况下是几十kb
            PdfSaveOptions options = new PdfSaveOptions();
            options.setEmbedFullFonts(true);
            options.setUseCoreFonts(true);
            options.setFontEmbeddingMode(PdfFontEmbeddingMode.EMBED_ALL);
            options.setTextCompression(PdfTextCompression.FLATE);
            options.setPageMode(PdfPageMode.USE_OUTLINES);
            //options.setCompliance(PdfCompliance.PDF_17);
            options.setImageCompression(PdfImageCompression.JPEG);
            options.setJpegQuality(100);
            document.save(new FileOutputStream(new File(savePath)),options);
            */
            //参考官网，使用默认选项转换pdf（https://docs.aspose.com/words/java/converting-to-fixed-page-format/）
            document.save(new FileOutputStream(new File(savePath)),SaveOptions.createSaveOptions(SaveFormat.PDF) );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
