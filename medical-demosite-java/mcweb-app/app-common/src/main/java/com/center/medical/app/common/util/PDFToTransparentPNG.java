package com.center.medical.app.common.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFToTransparentPNG {

    public static void main(String[] args) {
        try {
            convertPDFToTransparentPNG("/Users/macbook/codes/customer/zhongkang/zhongkang-medical/medical-center/zhongkang-medical/doc/logs/123.dwg", "/Users/macbook/codes/customer/zhongkang/zhongkang-medical/medical-center/zhongkang-medical/doc/mine.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertPDFToTransparentPNG(String inputPath, String outputPath) throws IOException {
        PDDocument document = PDDocument.load(new File(inputPath));
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        int pageCount = document.getNumberOfPages();

        for (int pageIndex = 0; pageIndex < pageCount; ++pageIndex) {
            BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 1080, ImageType.RGB);
            BufferedImage transparentImage = makeImageTransparent(image);
            ImageIO.write(transparentImage, "png", new File(outputPath));
        }

        document.close();
    }

    private static BufferedImage makeImageTransparent(BufferedImage image) {
        BufferedImage transparentImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = transparentImage.createGraphics();
        transparentImage = g2d.getDeviceConfiguration().createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);

        g2d.dispose();
        g2d = transparentImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return transparentImage;
    }
}