package com.center.medical.common.utils;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * ZipUtils
 * 目录最后都不带'/'
 */
public class ZipUtils {
	
	private static final int  BUFFER_SIZE = 2 * 1024;
	public final static String TEMP_PATH="/file/temp";
	public final static String IMPORT_PATH="/file/pacs-import";
	public final static String DATA_FILENAME="data.txt";
	public final static String CHARSET="UTF-8";
	public final static String SEPARATOR="/";
	public final static Charset CHAR=Charset.forName(CHARSET);
	public final static String ZIP=".zip";
	public final static String JPG=".jpg";
	public final static String DCM=".dcm";
	/**
	 * 压缩成ZIP 方法1
	 * @param srcDir 压缩文件夹路径 
	 * @param out    压缩文件输出流
	 * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
	 * 							false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
			throws RuntimeException{
		
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null ;
		try {
			zos = new ZipOutputStream(out,CHAR);
			File sourceFile = new File(srcDir);
			compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) +" ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 压缩成ZIP 方法2
	 * @param srcFiles 需要压缩的文件列表
	 * @param out 	        压缩文件输出流
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	public static void toZip(List<File> srcFiles , OutputStream out)throws RuntimeException {
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null ;
		try {
			zos = new ZipOutputStream(out);
			for (File srcFile : srcFiles) {
				byte[] buf = new byte[BUFFER_SIZE];
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int len;
				FileInputStream in = new FileInputStream(srcFile);
				while ((len = in.read(buf)) != -1){
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			}
			long end = System.currentTimeMillis();
			System.out.println("压缩完成，耗时：" + (end - start) +" ms");
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 递归压缩方法
	 * @param sourceFile 源文件
	 * @param zos		 zip输出流
	 * @param name		 压缩后的名称
	 * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
	 * 							false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws Exception
	 */
	private static void compress(File sourceFile, ZipOutputStream zos, String name,
			boolean KeepDirStructure) throws Exception{
		byte[] buf = new byte[BUFFER_SIZE];
		if(sourceFile.isFile()){
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1){
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if(listFiles == null || listFiles.length == 0){
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if(KeepDirStructure){
					// 空文件夹的处理
					zos.putNextEntry(new ZipEntry(name + SEPARATOR));
					// 没有文件，不需要文件的copy
					zos.closeEntry();
				}
				
			}else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name + SEPARATOR + file.getName(),KeepDirStructure);
					} else {
						compress(file, zos, file.getName(),KeepDirStructure);
					}
					
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		/** 测试压缩方法1  */
		FileOutputStream fos1 = new FileOutputStream(new File("d:/mytest01.zip"));
		ZipUtils.toZip("D:/压缩测试", fos1,true);
		
		/** 测试压缩方法2  
		List<File> fileList = new ArrayList<>();
		fileList.add(new File("D:/Java/jdk1.7.0_45_64bit/bin/jar.exe"));
		fileList.add(new File("D:/Java/jdk1.7.0_45_64bit/bin/java.exe"));
		FileOutputStream fos2 = new FileOutputStream(new File("c:/mytest02.zip"));
		ZipUtils.toZip(fileList, fos2);*/
	}
	
	public static void writeFile(String data,String path,boolean append) throws IOException{
		File file=new File(path);
		if(!file.exists()){
			file.createNewFile();
		}
		try(FileOutputStream out=new FileOutputStream(file,append);
				FileChannel channel=out.getChannel();){
			out.write(data.getBytes(CHARSET));
		}
	}
	
	public static void copyFile(String source,String target)throws IOException{
		File file=new File(target);
		if(!file.exists()){
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		try(FileInputStream in=new FileInputStream(source);
				FileOutputStream out=new FileOutputStream(file);
				FileChannel inc=in.getChannel();
				FileChannel outc=out.getChannel()){
			ByteBuffer buffer=ByteBuffer.allocate(1024);
			int n=inc.read(buffer);
			while(n!=-1){
				System.out.println(n);
				buffer.flip();
				outc.write(buffer);
				buffer.clear();
				n=inc.read(buffer);
			}
		}
	}
	
	public static String getFileName(String path){
		int index=path.lastIndexOf(SEPARATOR)==-1?path.lastIndexOf(File.separator):path.lastIndexOf(SEPARATOR);
		return path.substring(index);
	}
	
	public static String uncompress(File file)throws IOException{
		String path=null;
		try(ZipFile zip=new ZipFile(file,CHAR)){
			String folder=file.getParent();//解压位置目录(不以\结尾)
			Enumeration<? extends ZipEntry> entrys =zip.entries();
			while (entrys.hasMoreElements()) {
				ZipEntry entry = entrys.nextElement();
				String entryName=entry.getName();//         elingtong/WebContent/LingUI/ueditor/jsp/lib/commons-codec-1.9.jar
				if(path==null){
					path=folder+SEPARATOR+entryName.split(SEPARATOR)[0];
				}
				if(entry.isDirectory()){
					continue;
				}
				File target=new File(folder+SEPARATOR+entryName);
				File parent=target.getParentFile();
				if(!parent.exists()){
					parent.mkdirs();
				}
				if(!target.exists()){
					target.createNewFile();
				}
				try(InputStream in=zip.getInputStream(entry);
						FileOutputStream out=new FileOutputStream(target, false);){
					byte[] b = new byte[1024];
					int len;
					while ((len = in.read(b, 0, b.length)) != -1) {
						out.write(b, 0, len);
					}
				}
			}
		}
		return path;
	}
	
	public static List<Map<String,Object>> readFiles(String direct)throws IOException{
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		File root=new File(direct);
		File[] folders=root.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if(pathname.isDirectory()){
					return true;
				}
				return false;
			}
		});
		for(File folder:folders){
			File dataFile=new File(folder.getPath()+SEPARATOR+DATA_FILENAME);
			if(!dataFile.exists()){
				continue;
			}
			StringBuilder builder=new StringBuilder();
			try(FileInputStream is=new FileInputStream(dataFile);
					InputStreamReader isr=new InputStreamReader(is, CHAR);
					BufferedReader br=new BufferedReader(isr);){
				String lineTxt=null;
				while((lineTxt = br.readLine()) != null){
					builder.append(lineTxt);
				}
			}
			
			Map<String,Object> map=new HashMap<String, Object>();
			list.add(map);
			map.put("json", builder.toString().trim());
			map.put("direct", folder.getPath().substring(folder.getPath().lastIndexOf(File.separator)+1));
			File[] pics=folder.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if(pathname.isFile()&&
							(pathname.getName().endsWith(DCM)||pathname.getName().endsWith(JPG))){
						return true;
					}
					return false;
				}
			});
			if(pics.length>0){
				List<String> fileNames=new ArrayList<String>();
				for(File pic:pics){
					fileNames.add(getFileName(pic.getPath()));
				}
				map.put("fileNames", fileNames);
			}
		}
		
		return list;
	}
	
	public static File createFile(String fallPath){
		File file=new File(fallPath);
		File parent=file.getParentFile();
		if(!parent.exists()){
			parent.mkdirs();
		}
		return file;
	}
	/**
	 * 浏览器下载zip
	 * @throws Exception 
	 */
	public static void downZip(String title,List<File> files) throws Exception {
		HttpServletResponse response  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        response = getResponse(response, request, title);
        try(OutputStream ouputStream = response.getOutputStream();){
        	ZipUtils.toZip(files, ouputStream); 
        }
	}
	
	public static HttpServletResponse getResponse(HttpServletResponse response
			,HttpServletRequest request,String title) throws Exception {
		 response.setContentType("application/octet-stream; charset=utf-8");
        if (request.getHeader("User-Agent").toLowerCase()  
                .indexOf("firefox") > 0) {  
            title = new String(title.getBytes("UTF-8"), "ISO8859-1"); // firefox浏览器  
        } else if (request.getHeader("User-Agent").toUpperCase()  
                .indexOf("MSIE") > 0) {  
            title = URLEncoder.encode(title, "UTF-8");// IE浏览器  
        }else if (request.getHeader("User-Agent").toUpperCase()  
                .indexOf("CHROME") > 0) {  
            title = new String(title.getBytes("UTF-8"), "ISO8859-1");// 谷歌  
        } else {
            title = URLEncoder.encode(title, "UTF-8");
        }
        response.setHeader("Content-Disposition", "attachment;filename="+title+".zip");
        return response;
	}



	public static boolean isImageUrlExists(String imageUrl) {
		try {
			// 创建一个 URL 对象
			URL url = new URL(imageUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设置请求方法为 HEAD，以减少数据传输
			connection.setRequestMethod("HEAD");
			// 设置连接超时和读取超时
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			// 发送请求
			connection.connect();

			// 获取响应码
			int responseCode = connection.getResponseCode();
			// 检查响应码是否为 200 (HTTP_OK) 或 206 (HTTP_PARTIAL)
			return (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_PARTIAL);
		} catch (IOException e) {
			// 处理异常，返回 false 表示图片不存在
			return false;
		}
	}
}