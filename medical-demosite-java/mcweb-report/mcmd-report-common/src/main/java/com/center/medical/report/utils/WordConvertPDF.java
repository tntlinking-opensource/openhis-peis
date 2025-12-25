package com.center.medical.report.utils;

import com.center.medical.common.utils.ToolUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将word文件转换成PDF文件
 *
 * @ClassName: WordConvertPDF
 * @Description:
 * @author yinzl
 * @date 2016年9月7日 下午3:35:23
 *
 */
public class WordConvertPDF {
	static final int wdDoNotSaveChanges = 0;// 不保存待定的更改。
	static final int wdFormatPDF = 17;// word转PDF 格式

	/**
	 * 将word文件转换成PDF文件
	 *
	 * @Title: wordToPDF
	 *            void
	 * @author yinzl
	 * @since 2016年9月7日 V 1.0
	 */
	public void wordToPDF(String source, String target) {
		AsposeUtil.word2pdf(source, target);
	}
	
	public static void mergePdfFilesForReport(String[] files, String newfile) 
    		throws InvalidPasswordException, IOException{ 
		PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
//    	pdfMergerUtility.setDestinationFileName(newfile);
//    	PDDocument destination=new PDDocument();
//    	destination.save(newfile);
//    	File desFile=new File(newfile);
//    	desFile.createNewFile();
    	PDDocument destination=PDDocument.load(new File(files[0]));
    	//空白页
    	PDPage emptyPage=new PDPage();
    	emptyPage.setCropBox(PDRectangle.A4);
    	PDFont font = PDType1Font.HELVETICA_BOLD;
        try (PDPageContentStream contents = new PDPageContentStream(destination, emptyPage))
        {
            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(100, 700);
            contents.showText(" ");
            contents.endText();
        }
    	if(isOdd(destination.getNumberOfPages())){
    		destination.addPage(emptyPage);
    	}
    	for(int i=1,l=files.length;i<l;i++){
    		String path=files[i];
    		PDDocument document=PDDocument.load(new File(path));
//    		System.out.println(document.getNumberOfPages());
    		if(isOdd(document.getNumberOfPages())&&i!=l-1){//如果是奇数  增加一页空白页
    			document.addPage(emptyPage);
    		}
//			document.save("S:\\file\\wordexp\\personal\\20170929\\JZ0000020001\\b.pdf");
			pdfMergerUtility.appendDocument(destination, document);
//    		pdfMergerUtility.addSource(path);
    	}
    	destination.save(newfile);
//		pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
	}

	public static void mergePdfFiles(String[] files, String newfile) 
    		throws InvalidPasswordException, IOException{ 
		PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
    	PDDocument destination=PDDocument.load(new File(files[0]));
    	for(int i=1,l=files.length;i<l;i++){
    		String path=files[i];
    		PDDocument document=PDDocument.load(new File(path));
			pdfMergerUtility.appendDocument(destination, document);
    	}
    	destination.save(newfile);
	}
	
	public static boolean isOdd(int a){   
		if((a&1) == 1){   
		    return true;   
		}   
		return false;   
    }
	
	public static void mergePdfFilesForReport(List<HashMap<String,String>> files
			, String newfile) 
    		throws InvalidPasswordException, IOException{ 
		PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
		Map<String,String> map0=files.get(0);
		PDDocument destination = new PDDocument();
		PDDocument one=PDDocument.load(new File(map0.get("path")));
		pdfMergerUtility.appendDocument(destination,one);
        one.close();
//    	PDDocument destination=PDDocument.load(new File(map0.get("path")));
    	//空白页
    	PDPage emptyPage=new PDPage();
    	emptyPage.setCropBox(PDRectangle.A4);
    	PDFont font = PDType1Font.HELVETICA_BOLD;
        try (PDPageContentStream contents = new PDPageContentStream(destination, emptyPage))
        {
            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(100, 700);
            contents.showText(" ");
            contents.endText();
        }
        System.out.println("getNumberOfPages:"+destination.getNumberOfPages());
        if("1".equals(map0.get("odd"))) {
        	if(isOdd(destination.getNumberOfPages())){
        		destination.addPage(emptyPage);
        	}
        }
        String scaleStr= "0.23";
        Double scale=StringUtils.isEmpty(scaleStr)?null:Double.parseDouble(scaleStr);
        String realPath=ToolUtil.getPropert("doc_config.properties", "real_path");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd");
    	for(int i=1,l=files.size();i<l;i++){
    		HashMap<String,String> map=files.get(i);
    		String path=map.get("path");
    		PDDocument document=PDDocument.load(new File(path));
    		/**
    		for(int z=0;z<document.getNumberOfPages();z++) {
    			PDPage page=document.getPage(z);
    			page.setCropBox(PDRectangle.A4);//解决合并的Pdf纸张大小不一样的问题,超出A4的部分会被截掉
    		}
    		*/
    		
    		//海康pdf大小不一，体检系统必须是第一个
    		if(scale!=null&&path.indexOf("pacsPdf")>0) {
    			PDFRenderer renderer = new PDFRenderer(document);
    			for(int z=0;z<document.getNumberOfPages();z++) {
    				BufferedImage image = renderer.renderImageWithDPI(z, 300);
    				pageAddImage(destination
    						//与Thumbnails无关，直接Image还是报错
    						,Thumbnails.of(image).scale(scale).asBufferedImage()
    						);
    				
    				//下面这种方式有时会报错 java.io.IOException: COSStream has been closed and cannot be read. Perhaps its enclosing PDDocument has been closed?
//    				String tempPath=realPath
//    						+File.separator+"temppdfimg"
//    						+File.separator+sdf2.format(new Date())
//    						+File.separator+sdf.format(new Date())+UUID.randomUUID()
//    						+".jpg";
//    				File tempFile=new File(tempPath);
//    				File tempFolder=tempFile.getParentFile();
//    				if(!tempFolder.exists()) {
//    					tempFolder.mkdirs();
//    				}
//    				Thumbnails.of(image).scale(scale).toFile(tempPath
//    						);
//    				System.out.println("临时图片长度:"+tempFile.length());
//    				
//    				PDPage pdp=new PDPage();
//    				pdp.setCropBox(PDRectangle.A4);
//    				try (
//						PDPageContentStream contentStream 
//						= new PDPageContentStream(destination
//								, pdp);
//    						){
//    					PDImageXObject pdImage = PDImageXObject.createFromFile(tempPath, destination);
//    					contentStream.drawImage(pdImage, 0, 0,PDRectangle.A4.getWidth(),PDRectangle.A4.getHeight());
//    				}
//    				destination.addPage(pdp);
        		}
    		}else {
    			pdfMergerUtility.appendDocument(destination, document);
    		}
    		document.close();
    		
    		//不能先加空白页，再转图片，会报上面的错误
    		if("1".equals(map.get("odd"))&&isOdd(document.getNumberOfPages())&&i!=l-1){//如果是奇数  增加一页空白页
    			destination.addPage(emptyPage);
    		}
    	}
//    	destination.save(newfile);
    	pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        destination.save(newfile);
        destination.close();
	}
	
	
	private static void pageAddImage(PDDocument newPdf, BufferedImage image) throws IOException {
        //PDPage page = new PDPage(PDRectangle.A4);
        PDPage page = new PDPage();
        page.setCropBox(PDRectangle.A4);
        newPdf.addPage(page);

        float width = page.getMediaBox().getWidth();
        float height = page.getMediaBox().getHeight();
        float scale = page.getMediaBox().getWidth() / image.getWidth();
        scale = Math.min(1, scale);

        float imgWidth = image.getWidth() * scale;
        float imgHeight = image.getHeight() * scale;

        try (PDPageContentStream pageContentStream = new PDPageContentStream(newPdf, page)) {
            PDImageXObject pdImage = LosslessFactory.createFromImage(newPdf, image);
            pageContentStream.drawImage(pdImage, (width - imgWidth) / 2, height - image.getHeight() * scale, imgWidth, imgHeight);
        }
    }
}
