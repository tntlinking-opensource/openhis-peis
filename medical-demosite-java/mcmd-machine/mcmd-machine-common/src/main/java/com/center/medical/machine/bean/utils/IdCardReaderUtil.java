package com.center.medical.machine.bean.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 身份证读卡器公共类
 */
public class IdCardReaderUtil {
	public static final String USER_DIR=System.getProperty("user.dir");

	public final static String HUAXU="HUAXU";
    public final static String HUASHI="HUASHI";
    public final static String SHENSI="SHENSI";
    public final static String SHENSIX="SS728M05";
    
    /**
	 * 将lisense.dat文件放置到当前工作目录
	 */
	public static void putLisense(String licenseName) throws Exception{
		String target=SHENSIX.equals(licenseName)?
				("C://"+"license.dat")
				:(USER_DIR+File.separator+"license.dat");
		File lisense=new File(target);
		if(!lisense.exists()) {
			String source=Thread.currentThread().getContextClassLoader().getResource("")
					.toURI().getPath()
					+licenseName
					+"_license.dat";
			File file=new File(source);
			if(!file.isFile()||!file.exists()) {
				throw new RuntimeException("classes目录下缺少license.dat文件。");
			}
			lisense.setWritable(true);
			lisense.setReadable(true);
			lisense.setExecutable(true);
			lisense.createNewFile();
			try(FileInputStream in=new FileInputStream(file);
					FileOutputStream out=new FileOutputStream(target);
					FileChannel inc=in.getChannel();
					FileChannel outc=out.getChannel()){
				ByteBuffer buffer=ByteBuffer.allocate(1024);
				int n=inc.read(buffer);
				while(n!=-1){
					buffer.flip();
					outc.write(buffer);
					buffer.clear();
					n=inc.read(buffer);
				}
			}
		}
	}
	/**
	 * 如果使用过其他读卡器，在当前工作目下可能有其它读卡器的license.dat，
	 * 删除已有的license.dat
	 */
	public static void rebuildLisense(String licenseName) throws Exception{
		log("重新放置license.dat："+licenseName);
		String target=USER_DIR+File.separator+"license.dat";
		File lisense=new File(target);
		if(lisense.exists()){
			lisense.delete();
		}
		putLisense(licenseName);
	}
	/**
	 * 记录日志
	 */
	public static void log(String msg) {
		System.out.println(msg);
	}
	/**
	 * 抛出异常信息
	 */
	public static void error(String msg) {
		throw new RuntimeException(msg);
	}
	/**
	 * 获取项目根目录
	 */
	public  static String getPath() {
		try {
			return Thread.currentThread().getContextClassLoader()
					 .getResource("")
					 .toURI().getPath()
					 .substring(1);//去掉开头斜杠
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 将yyyymmdd转换为yyyy-mm-dd或yyyy.mm.dd
	 */
	public static String getDateStr(String date,String split){
		if(StringUtils.isEmpty(date)||date.length()<7)return date;
		return date.substring(0,4)
				+split
				+date.substring(4,6)
				+split
				+date.substring(6);
	}
}
