package com.center.medical.report.bean.utils;

import org.apache.commons.io.IOUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTAltChunk;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MargeDoc {

	public void mergeDocxOld(List<String> list, String path) {
		List<InputStream> inList = new ArrayList<InputStream>();
		for (int i = 0; i < list.size(); i++) {
			try {
				inList.add(new FileInputStream(list.get(i)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		try {
			// mergeDocxTo(inList, path);
			InputStream inputStream = mergeDocxTo(inList, path);
			saveTemplate(inputStream, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mergePicDocxOld(List<String> list, String path) {
		List<InputStream> inList = new ArrayList<InputStream>();
		for (int i = 0; i < list.size(); i++) {
			try {
				inList.add(new FileInputStream(list.get(i)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				InputStream inputStream = mergeDocxTo(inList, path);
				saveTemplate(inputStream, path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (list != null && list.size() > 0) {
			for (String f : list) {
				DeleteFileUtil.deleteFile(f);
			}
		}
	}

	public void mergeDocx(List<String> list, String path) {
		try {
			WordprocessingMLPackage target = null;
			// String pathdir=path.substring(0,path.lastIndexOf("/"));
			// File file = new File(pathdir);// D;/temp 为一个目录
			// File generated= file.createTempFile("generated", ".docx",file);
			File generated = createTempFile(".docx");
			int chunkId = 0;
			for (int i = 0; i < list.size(); i++) {
				System.out.println("开始合并:"+i+":"+list.get(i));
				FileInputStream is = new FileInputStream(list.get(i));
				if (is != null) {
					if (target == null) {
						// Copy first (master) document
						OutputStream os = new FileOutputStream(generated);
						os.write(IOUtils.toByteArray(is));
						os.close();
						target = WordprocessingMLPackage.load(generated);
						System.out.println(list.get(i)+"合并成功");
					} else {
						// Attach the others (Alternative input parts)
						insertDocx(target.getMainDocumentPart(),
								IOUtils.toByteArray(is), chunkId++);
						System.out.println(list.get(i)+"合并成功");
					}
				}
				is.close();
			}
			System.out.println("开始保存文件");
			target.save(generated);//这句原来在循环内，可能出现文件已被占用
			System.out.println("开始复制文件:"+generated.getName() + " path:" + generated.getPath()
					+ " length:"+ generated.length());
			saveTemplate(new FileInputStream(generated), path);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/** chunkId可能与path由多少部分组成有关，如果不正确会导致合并位置错乱、重复 */
	public void mergeDocxTj(List<String> list, String path, int chunkId) {
		try {
			WordprocessingMLPackage target = null;
			// String pathdir=path.substring(0,path.lastIndexOf("/"));
			// File file = new File(pathdir);// D;/temp 为一个目录
			// File generated= file.createTempFile("generated", ".docx",file);
			File generated = createTempFile(".docx");
			for (int i = 0; i < list.size(); i++) {
				FileInputStream is = new FileInputStream(list.get(i));
				if (is != null) {
					if (target == null) {
						// Copy first (master) document
						OutputStream os = new FileOutputStream(generated);
						os.write(IOUtils.toByteArray(is));
						os.close();
						target = WordprocessingMLPackage.load(generated);
					} else {
						// Attach the others (Alternative input parts)
						// chunkId++;
						insertDocx(target.getMainDocumentPart(),
								IOUtils.toByteArray(is), chunkId++);
					}
				}
				target.save(generated);
				is.close();
			}
			saveTemplate(new FileInputStream(generated), path);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void mergePicDocx(List<String> list, String path) {
		try {
			WordprocessingMLPackage target = null;
			// String pathdir=path.substring(0,path.lastIndexOf("/"));
			// File file = new File(pathdir);// D;/temp 为一个目录
			// File generated= file.createTempFile("generated", ".docx",file);
			File generated = createTempFile(".docx");
			int chunkId = 0;
			for (int i = 0; i < list.size(); i++) {
				FileInputStream is = new FileInputStream(list.get(i));
				if (is != null) {
					if (target == null) {
						// Copy first (master) document
						OutputStream os = new FileOutputStream(generated);
						os.write(IOUtils.toByteArray(is));
						os.close();
						target = WordprocessingMLPackage.load(generated);
					} else {
						// Attach the others (Alternative input parts)
						insertDocx(target.getMainDocumentPart(),
								IOUtils.toByteArray(is), chunkId++);
					}
				}
				target.save(generated);
				is.close();
			}
			saveTemplate(new FileInputStream(generated), path);
			if (list != null && list.size() > 0) {
				for (String f : list) {
					DeleteFileUtil.deleteFile(f);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public InputStream mergeDocxTo(final List<InputStream> streams, String path)
			throws Docx4JException, IOException {

		WordprocessingMLPackage target = null;
		/*
		 * File generated = new File(path); if (!generated.exists()) {
		 * generated.createNewFile(); }
		 */
		// path=path.substring(0,path.lastIndexOf("/"));
		// File file = new File(path);// D;/temp 为一个目录
		// File generated= file.createTempFile("generated", ".docx",file);
		File generated = createTempFile(".docx");
		// final File generated = File.createTempFile("generated", ".docx");
		// temp = generated.getPath();
		int chunkId = 0;
		Iterator<InputStream> it = streams.iterator();
		while (it.hasNext()) {
			InputStream is = it.next();
			if (is != null) {
				if (target == null) {
					// Copy first (master) document
					OutputStream os = new FileOutputStream(generated);
					os.write(IOUtils.toByteArray(is));
					os.close();
					target = WordprocessingMLPackage.load(generated);
				} else {
					// Attach the others (Alternative input parts)
					chunkId++;
					insertDocx(target.getMainDocumentPart(),
							IOUtils.toByteArray(is), chunkId++);
				}
			}
		}
		if (target != null) {
			// target.save(generated);
			return new FileInputStream(generated);
		} else {
			return null;
		}
	}

	// 插入文档
	private void insertDocx(MainDocumentPart main, byte[] bytes, int chunkId) {
		try {
			AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(
					new PartName("/part" + chunkId + ".docx"));
			// afiPart.setContentType(new
			// ContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"));
			afiPart.setBinaryData(bytes);
			Relationship altChunkRel = main.addTargetPart(afiPart);

			CTAltChunk chunk = Context.getWmlObjectFactory().createCTAltChunk();
			chunk.setId(altChunkRel.getId());

			main.addObject(chunk);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private File createTempFile(String suffix) throws IOException {
		String realPath = System.getProperty("user.dir") + "/temp/file/wordexp/group_medical/temporary" ;

		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		File dir = new File(realPath + "/mergeDocx/" + sdf.format(now));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file=new File(dir+"/"
				+(UUID.randomUUID().toString().replaceAll("-", ""))
				+suffix
				);
		if(file.exists()) {
			file.delete();
		}
		file.createNewFile();
		return file;
	}

	public void saveTemplate(InputStream fis, String toDocPath) {
		System.out.println("开始保存模板文件:"+toDocPath);
		FileOutputStream fos;
		int bytesum = 0;
		int byteread = 0;
		try {
			fos = new FileOutputStream(toDocPath);
			byte[] buffer = new byte[2048];
			while ((byteread = fis.read(buffer)) != -1) {
				bytesum += byteread; // 字节数 文件大小
				fos.write(buffer, 0, byteread);
			}
			fis.close();
			fos.close();
			System.out.println("模板文件保存成功,字节大小:"+ bytesum);
		} catch (Exception e1) {
			System.out.println("模板文件保存失败");
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) throws Docx4JException, IOException {
		MargeDoc wordUtil = new MargeDoc();
		String template = "D:/docs";
		List<String> list = new ArrayList<String>();
		list.add(template + "/1.docx");
		list.add(template + "/2.docx");
		list.add(template + "/3.docx");
		list.add(template + "/4.docx");
		wordUtil.mergeDocx(list, template + "/out.docx");
	}
}
