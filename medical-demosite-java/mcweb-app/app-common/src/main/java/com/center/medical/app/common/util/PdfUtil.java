package com.center.medical.app.common.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.center.medical.app.common.bean.PdfToBase64Dto;
import com.center.medical.app.common.exception.AppBindException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class PdfUtil {


    /**
     * 获取配置文件里的配置 String property 配置文件名称 String name 需要获取的配置参数名称 return String
     * 配置参数
     **/
    public static String getPropert(String property, String name) {
        String url = "";
        try {
            ClassLoader cls = Thread.currentThread().getContextClassLoader();
            InputStream in = cls.getResourceAsStream(property);
            Properties p = new Properties();

            p.load(in);
            if (p.containsKey(name)) {
                url = p.getProperty(name);
            }
        } catch (Exception e) {
        }

        return url;
    }

    /**
     * pdf转图片
     */
    public static File pdf2png(
            String folder
            , String filenameWithoutSuffix
            , String pictureSuffix
    ) throws Exception {
        // 将pdf装图片 并且自定义图片得格式大小
        File file = new File(folder + File.separator + filenameWithoutSuffix + ".pdf");
        PDDocument doc = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(doc);
//        int pageCount = doc.getNumberOfPages();
        //旋转度数
        String pdfRotate = getPropert("upload.properties", "pdfRotate");
        String targetFilePath = folder
                + "\\" + filenameWithoutSuffix
                + "." + pictureSuffix;
        File targetFile = new File(targetFilePath);
        //只支持一页
        for (int i = 0; i < 1; i++) {
            //第二个参数 Windows native DPI  调大后图片更清晰 需要调大内存
            BufferedImage image = renderer.renderImageWithDPI(i, 300);
            if (StringUtils.isNotEmpty(pdfRotate)) {
                Thumbnails.of(image).scale(1.0).rotate(Double.parseDouble(pdfRotate))
                        .toFile(targetFile);
            } else {
                ImageIO.write(image, pictureSuffix, targetFile);
            }
        }
        doc.close();
        return targetFile;
    }

    public static PdfToBase64Dto pdfToBase64(String remoteUrl, Integer curPage) {
//        System.out.println("开始转换：" + new Date() + "：" + remoteUrl);
        String fileName = remoteUrl.substring(remoteUrl.lastIndexOf("/"));
        String localPath = "logs" + fileName;
        List<String> imgList = new ArrayList<>();
        int pageCount = 0;
        try {
            File file = new File(localPath);
            if (!FileUtil.exist(file)) {
                URL url = new URL(remoteUrl);
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                if (inputStream.available() == 0) {
                    throw new FileNotFoundException("报告文件不存在或者损坏，请联系客服！");
                }
                file = new File(localPath);
                byte[] buffer = new byte[1024];
                int length = -1;
                OutputStream outputStream = new FileOutputStream(file);

                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
                inputStream.close();
            }

//            System.out.println("File downloaded successfully.");

            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            //旋转度数
            String pdfRotate = getPropert("upload.properties", "pdfRotate");
            //只支持一页
            pageCount = doc.getNumberOfPages();
//            System.out.println("pageCount=" + pageCount);
//            for (int i = 0; i < pageCount; i++) {
            String targetFilePath = "logs/" + file.getName() + "_" + (curPage - 1) + ".jpg";
            File targetFile = new File(targetFilePath);
            //第二个参数 Windows native DPI  调大后图片更清晰 需要调大内存
            BufferedImage image = renderer.renderImageWithDPI(curPage - 1, 300);
            if (StringUtils.isNotEmpty(pdfRotate)) {
                Thumbnails.of(image).scale(1.0).rotate(Double.parseDouble(pdfRotate))
                        .toFile(targetFile);
            } else {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", os);
                //保存图片
//                ImageIO.write(image, "jpg", targetFile);

                byte[] imageBytes = os.toByteArray();
                imgList.add(Base64.getEncoder().encodeToString(imageBytes));
            }
//            }
            doc.close();
            if (pageCount == curPage) {
//                System.out.println("删除pdf");
                file.delete();
            }
            targetFile.delete();

        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                // 处理 FileNotFoundException 异常
                throw new AppBindException("报告文件不存在或者损坏，请联系客服！");
            } else {
                // 处理其他 IOException 异常
                throw new AppBindException("获取报告文件失败，请重试！");
            }
        }
//        System.out.println("转图片完成" + new Date());
        PdfToBase64Dto pdfToBase64Dto = new PdfToBase64Dto(imgList.get(0), pageCount, curPage, 1);
        return pdfToBase64Dto;
    }

    public static void main(String[] args) {
        try {
            Date date = new Date();
            System.out.println("转图片开始");
            PdfToBase64Dto pdfToBase64Dto = pdfToBase64("https://www.zkgjtijian.com/06/file/wordexp/personal/20230625/060000354657/report_060000354657_14_12_01_571.pdf", 1);
            for (int i = 2; i <= pdfToBase64Dto.getTotal(); i++) {
                pdfToBase64("https://www.zkgjtijian.com/06/file/wordexp/personal/20230625/060000354657/report_060000354657_14_12_01_571.pdf", i);
            }
            System.out.println("转图片完成:" + DateUtil.between(date, new Date(), DateUnit.SECOND));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
