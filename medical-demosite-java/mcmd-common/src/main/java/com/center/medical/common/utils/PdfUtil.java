package com.center.medical.common.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.center.medical.common.exception.ServiceException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

public class PdfUtil {
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
        String pdfRotate =
                ToolUtil.getPropert("upload.properties", "pdfRotate");
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

    public static List<String> pdfToBase64(String remoteUrl) {
        String localPath = "logs/" + UUID.randomUUID() + ".pdf";
        List<String> imgList = new ArrayList<>();
        try {
            URL url = new URL(remoteUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            File file = new File(localPath);
            byte[] buffer = new byte[1024];
            int length = -1;
            OutputStream outputStream = new FileOutputStream(file);

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();
            System.out.println("File downloaded successfully.");

            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            //旋转度数
            String pdfRotate =
                    ToolUtil.getPropert("upload.properties", "pdfRotate");
            //只支持一页
            int pageCount = doc.getNumberOfPages();
            System.out.println("pageCount=" + pageCount);
            for (int i = 0; i < pageCount; i++) {
                String targetFilePath = "logs\\" + file.getName() + "_" + i + ".jpg";
                File targetFile = new File(targetFilePath);
                //第二个参数 Windows native DPI  调大后图片更清晰 需要调大内存
                BufferedImage image = renderer.renderImageWithDPI(i, 300);
                if (StringUtils.isNotEmpty(pdfRotate)) {
                    Thumbnails.of(image).scale(1.0).rotate(Double.parseDouble(pdfRotate))
                            .toFile(targetFile);
                } else {
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", os);

                    byte[] imageBytes = os.toByteArray();
                    imgList.add(Base64.getEncoder().encodeToString(imageBytes));
                }
            }
            doc.close();
            file.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgList;
        //System.out.println(JSONUtil.toJsonStr(imgList));
    }

    public static String pdfToBase64(String remoteUrl, Integer curPage) {
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
            String pdfRotate = "";
            //只支持一页
            pageCount = doc.getNumberOfPages();
            System.out.println("pageCount=" + pageCount);
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
                // 处理 FileNotFoundException 异常http://121.42.142.245:8081/Uploads/UPLOAD/2025/6/20250626151803955-工程师测试.pdf
                throw new ServiceException("报告文件不存在或者损坏，请联系客服！");
            } else {
                // 处理其他 IOException 异常
                throw new ServiceException("获取报告文件失败，请重试！");
            }
        }
        return imgList.get(0);
    }

    public static void main(String[] args) {
        try {
            Date date = new Date();
            String url  = "http://121.42.142.245:8081/Uploads/UPLOAD/2025/6/20250626151803955-工程师测试.pdf";
            url = url.substring(0, url.lastIndexOf("/")) + URLEncoder.encode(url.substring(url.lastIndexOf("/")), "UTF-8");
            System.out.println("转图片开始"+ url);
            List<String> pdfToBase64Dto = pdfToBase64(url);
            for (String s : pdfToBase64Dto) {
                System.out.println("图片："+s.substring(0, 30));
            }
            System.out.println("转图片完成:" + DateUtil.between(date, new Date(), DateUnit.SECOND));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            System.out.println("转图片开始");
//            System.out.println(pdfToBase64("http://121.42.142.245:8081/Uploads/UPLOAD/2025/6/20250625105939021-%E5%BF%83%E7%94%B5%E5%9B%BE%E6%B5%8B%E8%AF%9525.pdf", 1));
////            pdfToBase64("https://www.zkgjtijian.com/01/file/wordexp/personal/20200423/010000850340/report_010000850340_15_26_26_155.pdf");
//            System.out.println("转图片完成");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
