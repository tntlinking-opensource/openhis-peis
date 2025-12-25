/**
 * @ReportUtil.java
 * @com.lingnet.util
 * @Description：
 *
 * @author xuhp
 * @copyright  2017
 * @version V
 * @since 2017-5-31
 */
package com.center.medical.report.utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: ReportUtil
 * @Description:
 * @author xuhp
 * @date 2017-5-31 上午8:26:40
 *
 */

public class ReportUtil {
	/**
	 * 生成PDF 如果同名文件已存在，且没有其他程序占用，会覆盖
	 * */
	public void createPdfReport(String filePath,
			List<JasperPrint> jasperPrintList) throws JRException, IOException {
		JRPdfExporter exporter = new JRPdfExporter();
		ExporterInput exporterInput = SimpleExporterInput
				.getInstance(jasperPrintList);
		exporter.setExporterInput(exporterInput);
		// exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
		// jasperPrintList);
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				file);
		exporter.setExporterOutput(exporterOutput);
		// exporter.setParameter(JRExporterParameter.OUTPUT_FILE, file);
		exporter.exportReport();
	}

	/** 生成Word */
	public void createDOCReport(String filePath,
			List<JasperPrint> jasperPrintList) throws JRException {
		// JRExporter exporter = new JRRtfExporter();
		JRDocxExporter exporter = new JRDocxExporter();
		// exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
		// jasperPrintList);
		ExporterInput exporterInput = SimpleExporterInput
				.getInstance(jasperPrintList);
		exporter.setExporterInput(exporterInput);
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				file);
		exporter.setExporterOutput(exporterOutput);
		// exporter.setParameter(JRExporterParameter.OUTPUT_FILE, file);
		exporter.exportReport();
	}

	/** 生成Excel */
	public void createXLSXReport(String filePath,
			List<JasperPrint> jasperPrintList) throws JRException {
		JRXlsxExporter exporter = new JRXlsxExporter();
		// JRAbstractExporter exporter = new JRXlsxExporter();
		ExporterInput exporterInput = SimpleExporterInput
				.getInstance(jasperPrintList);
		exporter.setExporterInput(exporterInput);
		// exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
		// jasperPrintList);
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				file);
		exporter.setExporterOutput(exporterOutput);
		// exporter.setParameter(JRExporterParameter.OUTPUT_FILE, file);
		// exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
		// // 删除记录最下面的空行
		// exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);//
		// 删除多余的ColumnHeader
		// exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);//
		// 显示边框
		exporter.exportReport();
	}

	/***/
	public JasperPrint getJasperPrint(String jasperFilePath,
			Map<String, Object> parameters, JRDataSource jrds)
					throws JRException {
		if (ObjectUtils.isEmpty(parameters)){
			parameters = new HashMap<>();
		}
		JasperReport report = (JasperReport) JRLoader.loadObject(new File(
				jasperFilePath));
		JRAbstractLRUVirtualizer virtualizer = new JRGzipVirtualizer(2);
		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
		//待处理异常
		JasperPrint jasperPrint = JasperFillManager.fillReport(report,
				parameters, jrds);
		return jasperPrint;
	}

	public static void main(String[] args) {
		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			// List<Object>list=new ArrayList<Object>();
			List<Map<String, Object>> tablelist = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < 3; i++) {
				Map<String, Object> inmap = new HashMap<String, Object>();
				Collection<Map<String, ?>> list1 = new Vector();
				// List<Map<String,String>> list1=new
				// ArrayList<Map<String,String>>();
				parameters.put("list1", list1);
				Map<String, String> xm = new HashMap<String, String>();
				xm.put("name", "小明");
				xm.put("age", "10");
				xm.put("sex", "男");
				xm.put("wh", "明");
				list1.add(xm);
				xm = new HashMap<String, String>();
				xm.put("name", "小明2");
				xm.put("age", "10");
				xm.put("sex", "男");
				xm.put("wh", "明");
				list1.add(xm);
				xm = new HashMap<String, String>();
				xm.put("name", "小明3");
				xm.put("age", "10");
				xm.put("sex", "男");
				xm.put("wh", "明");
				list1.add(xm);
				xm = new HashMap<String, String>();
				xm.put("name", "小明4");
				xm.put("age", "10");
				xm.put("sex", "男");
				xm.put("wh", "明");
				list1.add(xm);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("field1", list1);
				map.put("f", "ddd");
				// list.add(map);
				parameters.put("list1", list1);
				inmap.put("inlist", list1);
				inmap.put("ks", "ks" + i);
				tablelist.add(inmap);
			}

			parameters.put("table1", tablelist);
			Collection<Map<String, String>> picCol = new Vector<Map<String, String>>();
			for (int i = 0; i < 3; i++) {
				Map<String, String> picm = new HashMap<String, String>();
				picm.put("path", "D:\\ce.jpg");
				picCol.add(picm);
			}
			parameters.put("piclist", picCol);

			// 多模板合并 只需在jasperPrintList中不断添加即可
			List<JasperPrint> jasperPrintList = new ArrayList();
			ReportUtil ru = new ReportUtil();
			// jasperPrintList.add(ru.getJasperPrint("D:\\report1.jasper"
			// ,parameters
			// ,new JREmptyDataSource()
			// ));

			jasperPrintList.add(ru.getJasperPrint("D:\\report1.jasper",
					parameters, new JRBeanCollectionDataSource(tablelist)));
			ru.createPdfReport("D://TEST.pdf", jasperPrintList);// 导出文件
			System.out.println("ok");
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * Error evaluating expression for source text: new
		 * net.sf.jasperreports.
		 * engine.data.JRMapCollectionDataSource($P{table1})
		 * 设置PARAMETER为COLLECTION。提示CONSTRUCTOR错误，可以NEW看一下参数类型 new
		 * net.sf.jasperreports
		 * .engine.data.JRMapCollectionDataSource($P{table1})
		 */
	}
}
