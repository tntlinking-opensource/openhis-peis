/**
 * 关于操作Word生成表格和替换文字工具类
 * @WordTool.java
 * @com.lingnet.util
 * @Description：
 *
 * @author jxk
 * @copyright  2015
 * @version V
 * @since 2015-9-30
 */
package com.center.medical.report.bean.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.center.medical.bean.model.Report;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.system.service.ISysDeptService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Clob;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 适应于替换docx格式的段落文本替换
 *
 * @ClassName: WordTool
 * @Description:
 * @author yinzl
 * @date 2016年9月19日 上午11:49:28
 *
 */
@Service("wordTool")
public class WordTool {


	private FileInputStream fileInput;

	private FileOutputStream fileOutput;

	private XWPFDocument doc;
	@Resource(name = "sysDeptServiceImpl")
	private ISysDeptService reportDao;// 报告主表
	@Resource(name = "toolUtil")
	public ToolUtil toolUtil;

	/**
	 *
	 * @Title: 获得一个wordtool实例
	 * @param fileInPutPath
	 *            模板标识符docx路径
	 * @param fileOutPutPath
	 *            生成客户需要替换的docx文件路径
	 * @return
	 * @throws Exception
	 *             WordTool
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public static WordTool getInstance(String fileInPutPath,
			String fileOutPutPath) throws Exception {
		WordTool wordTool = (WordTool) SpringUtil.getBean("wordTool");
		try {
			if (fileInPutPath != null) {
				wordTool.setFileInput(new FileInputStream(fileInPutPath));
				wordTool.setDoc(new XWPFDocument(wordTool.getFileInput()));
			}
			if (fileOutPutPath != null) {
				wordTool.setFileOutput(new FileOutputStream(fileOutPutPath));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wordTool;
	}

	/**
	 *
	 * @Title: 将替换完毕的word输出到指定的目录下
	 * @param params
	 *            模板中的所有标签
	 * @param
	 * @param fileName
	 *            输出后的文件的名字
	 * @param doc
	 *            word模板实例
	 * @param url
	 *            输出后的文件路径
	 * @param wordTool
	 *            工具类
	 * @return boolean
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public boolean exportTable(Map<String, Object> params, String fileName,
							   XWPFDocument doc, String url, WordTool wordTool, String depName,
							   Report rep, Integer isHead) {
		try {
			// 替换段落里面的变量
			this.replaceInPara(doc, params, wordTool, depName, rep, isHead);
			// 替换表格里面的变量
			this.replaceInTable(doc, params, wordTool, depName, rep, isHead);

			fileOutput = new FileOutputStream(url);

			doc.write(fileOutput);

			fileOutput.flush();

			this.close(fileOutput);

			this.close(fileInput);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 替换段落里面的变量提供的公共方法
	 *
	 * @Title: exportTable
	 * @param params
	 *            需要替换的文本的标示符
	 * @throws Exception
	 *             void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public void exportTable(Map<String, Object> params, String depName,
			Report rep, Integer isHead) throws Exception {
		// 替换段落里面的变量
		this.replaceInPara(doc, params, null, depName, rep, isHead);
		// 替换表格里面的变量
		this.replaceInTable(doc, params, null, depName, rep, isHead);

		doc.write(fileOutput);

	}

	/**
	 * 获得模板中的模块列表
	 *
	 * @Title: getParamModules
	 * @param doc
	 *            WORD模板
	 * @return
	 * @throws Exception
	 *             List<String>
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public List<String> getParamModules(XWPFDocument doc) throws Exception {
		List<String> modules = new ArrayList<String>();
		// 读取段落中的参数
		Iterator<XWPFParagraph> paramIterator = doc.getParagraphsIterator();
		while (paramIterator.hasNext()) {
			XWPFParagraph para = paramIterator.next();
			String paramText = para.getParagraphText();
			paramText = (paramText == null ? "" : paramText);
			if (this.matcher(paramText).find() && paramText.indexOf("-") >= 0) {// 截取参数模块名
				String module = paramText.substring(paramText.indexOf("[") + 1,
						paramText.indexOf("-"));
				if (!modules.contains(module)) {
					modules.add(module);
				}
				if ("图片".equals(module)) {
					modules.add(paramText.substring(paramText.indexOf("[") + 1,
							paramText.indexOf("]")));
				}
			}
		}
		// 读取表格中的参数
		Iterator<XWPFTable> tabIterator = doc.getTablesIterator();
		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paras;
		while (tabIterator.hasNext()) {// 所有表
			table = tabIterator.next();
			rows = table.getRows();
			for (XWPFTableRow row : rows) {// 某张表
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {// 某张表的所有行
					paras = cell.getParagraphs();
					for (XWPFParagraph para : paras) {// 某张表的某行的所有单元格
						String paramText = para.getParagraphText();
						paramText = (paramText == null ? "" : paramText);
						if (this.matcher(paramText).find()
								&& paramText.indexOf("-") >= 0) {// 截取参数模块名
							String module = paramText.substring(
									paramText.indexOf("[") + 1,
									paramText.indexOf("-"));
							if (!modules.contains(module)) {
								modules.add(module);
							}
						}
					}
				}
			}
		}
		return modules;
	}

	/**
	 * 结束word输出操作
	 *
	 * @Title: finish
	 * @throws Exception
	 *             void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public void finish() throws Exception {

		fileOutput.flush();

		this.close(fileOutput);

		this.close(fileInput);
	}

	/**
	 * 设置表格的边线
	 *
	 * @Title: setTableBorders
	 * @param table
	 * @return XWPFTable
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public XWPFTable setTableBorders(XWPFTable table) {
		String tablecolor = ToolUtil.getPropert("doc_config.properties",
				"table_color");
		String tablesize = ToolUtil.getPropert("doc_config.properties",
				"table_size");
		CTTbl ttbl = table.getCTTbl();

		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl
				.getTblPr();

		CTTblBorders tblBorders = tblPr.isSetTblBorders() ? tblPr
				.getTblBorders() : tblPr.addNewTblBorders();

				CTBorder hBorder = tblBorders.addNewInsideH();
				hBorder.setVal(STBorder.Enum.forString("single"));
				hBorder.setSz(new BigInteger(tablesize));
				hBorder.setColor(tablecolor);
				CTBorder vBorder = tblBorders.addNewInsideV();
				vBorder.setVal(STBorder.Enum.forString("single"));
				vBorder.setSz(new BigInteger(tablesize));
				vBorder.setColor(tablecolor);
				CTBorder rBorder = tblBorders.addNewRight();
				rBorder.setVal(STBorder.Enum.forString("single"));
				rBorder.setSz(new BigInteger(tablesize));
				rBorder.setColor(tablecolor);
				CTBorder tBorder = tblBorders.addNewTop();
				tBorder.setVal(STBorder.Enum.forString("single"));
				tBorder.setSz(new BigInteger(tablesize));
				tBorder.setColor(tablecolor);
				CTBorder lBorder = tblBorders.addNewLeft();
				lBorder.setVal(STBorder.Enum.forString("single"));
				lBorder.setSz(new BigInteger(tablesize));
				lBorder.setColor(tablecolor);
				CTBorder bBorder = tblBorders.addNewBottom();
				bBorder.setVal(STBorder.Enum.forString("single"));// "double"双线 single单线
				bBorder.setSz(new BigInteger(tablesize));
				bBorder.setColor(tablecolor);

				tblBorders.setLeft(lBorder);
				tblBorders.setTop(tBorder);
				tblBorders.setRight(rBorder);
				tblBorders.setBottom(bBorder);
				tblBorders.setInsideH(hBorder);
				tblBorders.setInsideV(vBorder);

				return table;
	}

	public void setParagraphAlignInfo(XWPFParagraph p,
			ParagraphAlignment pAlign, TextAlignment valign) {
		if (pAlign != null) {
			p.setAlignment(pAlign);
		}
		if (valign != null) {
			p.setVerticalAlignment(valign);
		}
	}

	/**
	 * 关于多行table数据的插入行操作
	 *
	 * @Title: updateTables
	 * @param doc
	 *            模板实例
	 * @param list
	 *            是表格的那部分模板标记数据
	 * @param key
	 *            要替换的标记
	 * @param fileOutPutPath
	 * @return
	 * @throws Exception
	 *             XWPFDocument
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public XWPFDocument updateTables(XWPFDocument doc,
			List<HashMap<String, Object>> list, String key,
			String fileOutPutPath) throws Exception {
		// 读取表格中的参数
		Iterator<XWPFTable> tabIterator = doc.getTablesIterator();
		XWPFTable table;
		XWPFTableRow row;
		XWPFTableRow onerow = null;
		XWPFTableCell cell;
		XWPFTableCell onecell;
		HashMap map = new HashMap();
		if (list.size() > 0 && list.get(0).containsKey("DESCRIBE.FEE_NAME")) {
			map.put(list.get(0).get("DESCRIBE.FEE_NAME"), "");
		}
		while (tabIterator.hasNext()) {
			table = tabIterator.next();
			if (this.matchertable(table.getText()).find()
					&& table.getText().contains(key)) {
				table = setTableBorders(table);

				if (!("WZ_ZYS".equals(key) || "WZ_ZYBS".equals(key))) {
					onerow = table.getRow(2);
					if (onerow == null) {
						continue;
					}
					int j = table.getNumberOfRows() - 2;
					if (j < list.size()) {
						for (int i = j; i < list.size(); i++) {
							if (list.get(i)
									.containsKey("DESCRIBE.FEE_NAME" + i)) {
								if (!map.containsKey(list.get(i).get(
										"DESCRIBE.FEE_NAME" + i))) {
									map.put(list.get(i)
											.get("DESCRIBE.FEE_NAME"), "");
									row = table.createRow();
									int size = row.getTableCells().size();
									cell = row.getCell(0);
									cell.setText("$t[DESCRIBE.FEE_NAME" + i
											+ "]");
									mergeCellsHorizontal(row, 0, size - 1);
								}
							}

							row = table.createRow();
							int size = row.getTableCells().size();
							for (int ii = 0; ii < size; ii++) {
								cell = row.getCell(ii);
								onecell = onerow.getCell(ii);
								if (this.matcher(onecell.getText()).find()) {
									String module = onecell.getText()
											.substring(
													onecell.getText().indexOf(
															"[") + 1,
															onecell.getText().indexOf(
																	"]"));
									cell.setText("$t[" + module + i + "]");
								}
							}
						}
					} else {
						for (int i = 0; i < list.size(); i++) {
							table.removeRow((j - i));
						}
					}
				} else {
					/** 职业性问诊 职业史、职业病史 */
					onerow = table.getRow(1);
					int j = table.getNumberOfRows() - 1;// 模板中已有一行
					if (j < list.size()) {
						for (int i = j; i < list.size(); i++) {
							row = table.createRow();
							int size = row.getTableCells().size();
							for (int ii = 0; ii < size; ii++) {
								cell = row.getCell(ii);
								onecell = onerow.getCell(ii);
								if (this.matcher(onecell.getText()).find()) {
									String module = onecell.getText()
											.substring(
													onecell.getText().indexOf(
															"[") + 1,
															onecell.getText().indexOf(
																	"]"));
									cell.setText("$t[" + module + i + "]");
								}
							}
						}
					} else if (j > list.size()) {
						// for (int i = 0; i < list.size(); i++) {
						// table.removeRow((j - i));
						// }
						for (int i = list.size(); i < j; i++) {
							table.removeRow(i + 1);
						}
					}

				}

			}
		}
		fileOutput = new FileOutputStream(fileOutPutPath);
		doc.write(fileOutput);
		this.close(fileOutput);
		return WordTool.getInstance(fileOutPutPath, null).getDoc();
	}

	public XWPFDocument updateTables2(XWPFDocument doc,
			List<HashMap<String, Object>> list, String key,
			String fileOutPutPath, Integer oneRowNum) throws Exception {
		// 读取表格中的参数
		Iterator<XWPFTable> tabIterator = doc.getTablesIterator();
		XWPFTable table;
		XWPFTableRow row;
		XWPFTableRow onerow = null;
		XWPFTableCell cell;
		XWPFTableCell onecell;
		while (tabIterator.hasNext()) {
			table = tabIterator.next();
			if (this.matchertable(table.getText()).find()
					&& table.getText().contains(key)) {
				table = setTableBorders(table);
				onerow = table.getRow(oneRowNum);
				XWPFParagraph onepara = onerow.getCell(0).getParagraphs()
						.get(0);
				// LineSpacingRule onerule=onepara.getSpacingLineRule();
				XWPFRun onerun = onepara.getRuns().get(0);
				int size1 = onerun.getFontSize();
				// double spacingbetween=onepara.getSpacingBetween();//行间距
				// int position=onerun.getTextPosition();
				int j = table.getNumberOfRows() - 1;// 模板中已有一行
				if (j < list.size()) {
					for (int i = j; i < list.size(); i++) {
						row = table.createRow();
						int size = row.getTableCells().size();
						for (int ii = 0; ii < size; ii++) {
							cell = row.getCell(ii);
							onecell = onerow.getCell(ii);
							if (this.matcher(onecell.getText()).find()) {
								String module = onecell.getText().substring(
										onecell.getText().indexOf("[") + 1,
										onecell.getText().indexOf("]"));
								if (size1 != -1) {

								} else {

								}
								List<XWPFParagraph> paras = cell
										.getParagraphs();
								List<XWPFRun> runs = paras.get(0).getRuns();
								XWPFRun run = runs.size() > 0 ? runs.get(0)
										: paras.get(0).createRun();
								run.setFontSize(size1);// 令字体大小等于第一行的字体大小
								run.setText("$t[" + module + i + "]");
								// cell.setText("$t[" + module + i + "]");
							}
						}
					}
				} else if (j > list.size()) {
					for (int i = list.size(); i < j; i++) {
						table.removeRow(i + 1);
					}
				}

			}
		}
		fileOutput = new FileOutputStream(fileOutPutPath);
		doc.write(fileOutput);
		this.close(fileOutput);
		return WordTool.getInstance(fileOutPutPath, null).getDoc();
	}

	// word跨列合并单元格
	public void mergeCellsHorizontal(XWPFTableRow row, int fromCell, int toCell) {
		for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
			XWPFTableCell cell = row.getCell(cellIndex);
			if (cellIndex == fromCell) {
				// The first merged cell is set with RESTART merge value
				cell.getCTTc().addNewTcPr().addNewHMerge()
				.setVal(STMerge.RESTART);
			} else {
				// Cells which join (merge) the first one, are set with CONTINUE
				cell.getCTTc().addNewTcPr().addNewHMerge()
				.setVal(STMerge.CONTINUE);
			}
		}
	}

	/**
	 * 关于多行table数据的插入行操作 table.getRow(0）
	 *
	 * @Title: updateTables
	 * @param doc
	 *            模板实例
	 * @param list
	 *            是表格的那部分模板标记数据
	 * @param key
	 *            要替换的标记
	 * @param fileOutPutPath
	 * @return
	 * @throws Exception
	 *             XWPFDocument
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	@SuppressWarnings("rawtypes")
	public XWPFDocument updateTablesToInspectColor(XWPFDocument doc,
			List<HashMap<String, Object>> list, String key,
			String fileOutPutPath) throws Exception {
		// 读取表格中的参数
		Iterator<XWPFTable> tabIterator = doc.getTablesIterator();
		XWPFTable table;
		XWPFTableRow row;
		XWPFTableRow onerow;
		XWPFTableCell cell;
		XWPFTableCell onecell;
		while (tabIterator.hasNext()) {
			table = tabIterator.next();
			if (this.matchertable(table.getText()).find()
					&& table.getText().contains(key)) {
				table = setTableBorders(table);
				onerow = table.getRow(0);
				if (onerow == null) {
					continue;
				}
				int j = table.getNumberOfRows() - 1;
				if (j < list.size()) {
					for (int i = j; i < list.size(); i++) {
						row = table.createRow();
						for (int ii = 0; ii < row.getTableCells().size(); ii++) {
							cell = row.getCell(ii);
							onecell = onerow.getCell(ii);
							if (this.matcher(onecell.getText()).find()) {
								String module = onecell.getText().substring(
										onecell.getText().indexOf("[") + 1,
										onecell.getText().indexOf("]"));
								if (i == 0) {
									cell.setText("$t[" + module + "]");
								} else {
									cell.setText("$t[" + module + (i - 1) + "]");
								}

								// if("CONSULT".equals(module)){
								HashMap map = list.get(i);
								if (map != null) {
									String stat = null;
									String reportValue = null;
									if (i == 0) {
										stat = (String) map.get("CONSULT");
										reportValue = map.get("RESULT") == null ? ""
												: map.get("RESULT").toString();
									} else {
										stat = (String) map.get("CONSULT"
												+ (i - 1));
										reportValue = map.get("RESULT"
												+ (i - 1)) == null ? "" : map
														.get("RESULT" + (i - 1))
														.toString();
									}
									// if(stat!=null){
									if ("↑".equals(stat) || "↓".equals(stat)
											|| reportValue.indexOf("+") != -1
											|| reportValue.indexOf("阳性") != -1) {
										// 设置底色
										CTTc cttc = cell.getCTTc();
										CTTcPr ctPr = cttc.addNewTcPr();
										CTShd ctshd = ctPr.addNewShd();
										ctshd.setFill("FFDAB9");
									}
									// }
								}
								// }

							}
						}
					}
					table.removeRow(0);
				} else {
					for (int i = 0; i < list.size(); i++) {
						table.removeRow((j - i));
					}
				}
			}
		}
		fileOutput = new FileOutputStream(fileOutPutPath);
		doc.write(fileOutput);
		this.close(fileOutput);
		return WordTool.getInstance(fileOutPutPath, null).getDoc();
	}

	/**
	 * 关于多行table数据的插入行操作 table.getRow(0）
	 *
	 * @Title: updateTables
	 * @param doc
	 *            模板实例
	 * @param list
	 *            是表格的那部分模板标记数据
	 * @param key
	 *            要替换的标记
	 * @param fileOutPutPath
	 * @return
	 * @throws Exception
	 *             XWPFDocument
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public XWPFDocument updateTablesToInspect(XWPFDocument doc,
			List<HashMap<String, Object>> list, String key,
			String fileOutPutPath) throws Exception {
		// 读取表格中的参数
		Iterator<XWPFTable> tabIterator = doc.getTablesIterator();
		XWPFTable table;
		XWPFTableRow row;
		XWPFTableRow onerow;
		XWPFTableCell cell;
		XWPFTableCell onecell;
		while (tabIterator.hasNext()) {
			table = tabIterator.next();
			if (this.matchertable(table.getText()).find()
					&& table.getText().contains(key)) {
				table = setTableBorders(table);
				onerow = table.getRow(0);
				if (onerow == null) {
					continue;
				}
				int j = table.getNumberOfRows() - 1;
				if (j < list.size()) {
					for (int i = j; i < list.size() - 1; i++) {
						row = table.createRow();
						for (int ii = 0; ii < row.getTableCells().size(); ii++) {
							cell = row.getCell(ii);
							onecell = onerow.getCell(ii);
							if (this.matcher(onecell.getText()).find()) {
								String module = onecell.getText().substring(
										onecell.getText().indexOf("[") + 1,
										onecell.getText().indexOf("]"));

								cell.setText("$t[" + module + i + "]");

							}
						}
					}
				} else {
					for (int i = 0; i < list.size(); i++) {
						table.removeRow((j - i));
					}
				}
			}
		}
		fileOutput = new FileOutputStream(fileOutPutPath);
		doc.write(fileOutput);
		this.close(fileOutput);
		return WordTool.getInstance(fileOutPutPath, null).getDoc();
	}

	/** 职业头模板2 */
	public XWPFDocument updateTablesToZyxwz(XWPFDocument doc,
			List<HashMap<String, Object>> list, String key,
			String fileOutPutPath) throws Exception {
		// 读取表格中的参数
		Iterator<XWPFTable> tabIterator = doc.getTablesIterator();
		XWPFTable table;
		XWPFTableRow row;
		XWPFTableRow onerow;
		XWPFTableCell cell;
		XWPFTableCell onecell;
		while (tabIterator.hasNext()) {
			table = tabIterator.next();
			if (this.matchertable(table.getText()).find()
					&& table.getText().contains(key)) {
				// table = setTableBorders(table);
				onerow = table.getRow(1);
				if (onerow == null) {
					continue;
				}
				int j = table.getNumberOfRows() - 1;
				if (j < list.size()) {
					for (int i = j; i < list.size(); i++) {
						row = table.createRow();
						for (int ii = 0; ii < row.getTableCells().size(); ii++) {
							cell = row.getCell(ii);
							onecell = onerow.getCell(ii);
							if (this.matcher(onecell.getText()).find()) {
								String module = onecell.getText().substring(
										onecell.getText().indexOf("[") + 1,
										onecell.getText().indexOf("]"));

								cell.setText("$t[" + module + (i - 1) + "]");

							}
						}
					}
				} else {
					// for (int i = 0; i < list.size(); i++) {
					// table.removeRow((j - i));
					// }
					for (int i = list.size(); i < j; i++) {
						table.removeRow((j - i + 1));
					}
				}
			}
		}
		fileOutput = new FileOutputStream(fileOutPutPath);
		doc.write(fileOutput);
		this.close(fileOutput);
		return WordTool.getInstance(fileOutPutPath, null).getDoc();
	}

	/**
	 * 关于多行table数据的插入行操作(有换行拆分的表格)
	 *
	 * @Title: updateTables
	 * @param doc
	 *            模板实例
	 * @param list
	 *            是表格的那部分模板标记数据
	 * @param key
	 *            要替换的标记
	 * @param fileOutPutPath
	 * @return
	 * @throws Exception
	 *             XWPFDocument
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public XWPFDocument updateTablesTwo(XWPFDocument doc,
			List<HashMap<String, Object>> list, String key,
			String fileOutPutPath) throws Exception {
		// 读取表格中的参数
		Iterator<XWPFTable> tabIterator = doc.getTablesIterator();
		XWPFTable table;
		XWPFTableRow row;
		XWPFTableRow onerow;
		XWPFTableCell cell;
		XWPFTableCell onecell;
		while (tabIterator.hasNext()) {
			table = tabIterator.next();
			if (this.matchertable(table.getText()).find()
					&& table.getText().contains(key)) {
				table = setTableBorders(table);
				onerow = table.getRow(2);
				if (onerow == null) {
					continue;
				}
				int j = table.getNumberOfRows() - 1;
				if (j < list.size()) {
					for (int i = j; i < list.size(); i++) {
						row = table.createRow();
						int size = row.getTableCells().size();
						for (int ii = 0; ii < size; ii++) {
							cell = row.getCell(ii);
							onecell = onerow.getCell(ii);
							if (this.matcher(onecell.getText()).find()) {
								String module = onecell.getText().substring(
										onecell.getText().indexOf("[") + 1,
										onecell.getText().indexOf("]"));
								cell.setText("$t[" + module + i + "]");
							}
						}
					}
				} else {
					for (int i = 0; i < list.size(); i++) {
						table.removeRow((j - i));
					}
				}
			}
		}
		fileOutput = new FileOutputStream(fileOutPutPath);
		doc.write(fileOutput);
		this.close(fileOutput);
		return WordTool.getInstance(fileOutPutPath, null).getDoc();
	}

	/**
	 * 替换段落里面的变量
	 *
	 * @Title: replaceInPara
	 * @param doc
	 *            要替换的文档模板
	 * @param params
	 *            要替换的数据
	 * @param wordTool
	 *            工具类 void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected void replaceInPara(XWPFDocument doc, Map<String, Object> params,
			WordTool wordTool, String depName, Report rep, Integer isHead) {
		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
		XWPFParagraph para;
		while (iterator.hasNext()) {
			para = iterator.next();
			this.replaceInPara(para, params, wordTool, depName, rep, isHead);
		}
	}

	/**
	 * 替换段落里面的变量
	 *
	 * @Title: replaceInPara
	 * @param para
	 *            要替换的段落
	 * @param params
	 *            要替换的段落对应的数据
	 * @param isHead
	 *            是否为导出头
	 * @param wordTool
	 *            void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected void replaceInPara(XWPFParagraph para,
			Map<String, Object> params, WordTool wordTool, String depName,
			Report rep, Integer isHead) {
		Matcher matcher;
		// 处理段落
		String sss = para.getParagraphText();
		if (this.matcher(sss).find()) {
			String runText = para.getParagraphText();
			String bm = runText;
			matcher = this.matcher(runText);
			if (matcher.find()) {
				while ((matcher = this.matcher(runText)).find()) {
					String str = matcher.group(0);
					str = str.substring(str.indexOf("$") + 1,
							str.indexOf("$") + 2);
					String match = matcher.group(1);
					if (matcher.group(1).indexOf("head") != -1
							|| matcher.group(1).indexOf("barcode") != -1) {

						if (matcher.group(1).indexOf("head") != -1) {
							String width = null;
							try {
								width = ToolUtil.getPropert(
										"pic_size.properties", "head.width");
							} catch (Exception e) {
								e.printStackTrace();
								updateReportGenerate("获取头像图片宽异常", rep);
							}
							String heigth = null;
							try {
								heigth = ToolUtil.getPropert(
										"pic_size.properties", "head.height");
							} catch (Exception e) {
								updateReportGenerate("获取头像图片高异常", rep);
								e.printStackTrace();
							}
							try {
								String s = String.valueOf(params.get("head"));
								if (!"null".equals(s) && s != null
										&& !"".equals(s)) {
									wordTool.setDoc(this.createPictur(width,
											heigth, s, matcher.group(1),
											wordTool, rep));
									runText = matcher.replaceFirst("");
									break;
								} else {
									runText = matcher.replaceFirst("");
									break;

								}
							} catch (Exception e) {
								e.printStackTrace();
								updateReportGenerate("头像图片插入文档异常", rep);
								runText = matcher.replaceFirst("");
								break;
							}
						} else if (matcher.group(1).indexOf("barcode") != -1) {
							String width = null;
							try {
								width = ToolUtil.getPropert(
										"pic_size.properties", "barcode.width");
							} catch (Exception e) {
								e.printStackTrace();
							}
							String heigth = null;
							try {
								heigth = ToolUtil
										.getPropert("pic_size.properties",
												"barcode.height");
							} catch (Exception e) {
								e.printStackTrace();
							}
							try {
								String s = String
										.valueOf(params.get("barcode"));
								if (!"null".equals(s) && s != null
										&& !"".equals(s)) {
									wordTool.setDoc(this.createPictur(width,
											heigth, s, matcher.group(1),
											wordTool, rep));
									runText = matcher.replaceFirst("");
									break;

								} else {
									runText = matcher.replaceFirst("");
									break;
								}
							} catch (Exception e) {
								updateReportGenerate("条形码图片插入文档异常", rep);
								e.printStackTrace();
							}
							runText = matcher.replaceFirst("");
						} else {
							runText = matcher.replaceFirst("");
							break;
						}
					} else if ("M_NUM".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"m_num.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"m_num.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("M_NUM");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("M_NUM");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("M_NUM")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					}  else if ("PIC7".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"pic7.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"pic7.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("PIC7");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("PIC7");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("PIC7")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					}else if ("W_NUM".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"w_num.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"w_num.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("W_NUM");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("W_NUM");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("W_NUM")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("T_NUM".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"t_num.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"t_num.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("T_NUM");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("T_NUM");
								// String name =
								// url.substring(url.lastIndexOf("\\")+1);
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("T_NUM")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("M_W_T".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"m_w_t.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"m_w_t.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("M_W_T");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("M_W_T");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("M_W_T")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("M_T_P".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"m_t_p.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"m_t_p.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("M_T_P");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("M_T_P");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("M_T_P")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("W_T_P".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"w_t_p.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"w_t_p.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("W_T_P");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("W_T_P");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("W_T_P")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("T_T_P".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"t_t_p.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"t_t_p.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("T_T_P");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("T_T_P");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("T_T_P")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("ITEMS_M_W_T".equals(match)) {

						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"items_m_w_t.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"items_m_w_t.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("ITEMS_M_W_T");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("ITEMS_M_W_T");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("ITEMS_M_W_T")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}

						runText = matcher.replaceFirst("");
					} else if ("ABNORMAL_M".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"abnormal_m.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"abnormal_m.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("ABNORMAL_M");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("ABNORMAL_M");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("ABNORMAL_M")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("ABNORMAL_W".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"abnormal_w.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"abnormal_w.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("ABNORMAL_W");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("ABNORMAL_W");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("ABNORMAL_W")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("ABNORMAL_T".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"abnormal_t.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"abnormal_t.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("ABNORMAL_T");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("ABNORMAL_T");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("ABNORMAL_T")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("dctpic".equals(match)) {
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"dctpic.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"dctpic.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							Object s = params.get("dctpic");
							if (!"null".equals(s) && s != null) {
								String url = "" + params.get("dctpic");
								wordTool.setDoc(this.setPictur(width, heigth,
										url, matcher.group(1), wordTool, rep));
							}
						} catch (Exception e) {
							updateReportGenerate(params.get("dctpic")
									+ "图片插入文档异常", rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("signPic".equals(match)) {// 医师签名
						String width = null;
						String heigth = null;
						try {
							width = ToolUtil.getPropert("pic_size.properties",
									"signPic.width");
							heigth = ToolUtil.getPropert("pic_size.properties",
									"signPic.heigth");
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							String ss = matcher.group(1).replaceAll(" ", "");//
							Object o = params.get(ss);
							if (o != null) {
								String s = o.toString();
								String url = null;
								if (!StringUtils.isEmpty(s)
										&& !"null".equals(s)) {
									s = s.trim();

									url = s;
								}
								if (!StringUtils.isEmpty(url)) {
									wordTool.setDoc(this.setPictur(width,
											heigth, url, ss, wordTool, rep));
								}
							}
						} catch (Exception e) {
							updateReportGenerate("图片插入文档异常" + e.getMessage(),
									rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else if ("p".equals(str)) {
						String width = null;
						String heigth = null;

						if (!StringUtils.isEmpty(depName)
								&& !"null".equals(depName)) {

							if (match.equals("testPic")) {
								width = findWidth("外送项目");
								heigth = findHeigth("外送项目");
							} else {
								width = findWidth(depName);
								heigth = findHeigth(depName);
							}
						} else {
							width = "100";
							heigth = "100";
						}

						try {
							String ss = matcher.group(1).replaceAll(" ", "");//
							Object o = params.get(ss);
							if (o != null) {
								String s = o.toString();
								String url = null;
								if (!StringUtils.isEmpty(s)
										&& !"null".equals(s)) {
									s = s.trim();

									url = s;
								}
								if (!StringUtils.isEmpty(url)) {
									wordTool.setDoc(this.setPictur(width,
											heigth, url, ss, wordTool, rep));
								}
							}
						} catch (Exception e) {
							updateReportGenerate("图片插入文档异常" + e.getMessage(),
									rep);
							e.printStackTrace();
						}
						runText = matcher.replaceFirst("");
					} else {
						runText = getRunText(matcher, params);
					}
				}
				// 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
				// 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
				if (bm.indexOf("$p") == -1) {
					Map<String, Object> style = getTextStyle(para);// 用于设置字符串的格式
					emptyPara(para);
					// 用于设置字符串的格式
					XWPFRun run = para.insertNewRun(0);
					if (isHead == 0) {// 换行
						if (runText.indexOf("\n") != -1) {
							String con[] = runText.replaceAll("\r", "").split(
									"\n");
							// boolean fbreak="$[HARM_CLASS]".equals(bm)
							// ||"$[HARM_DEPARTMENT]".equals(bm)
							// ||"$[ON_JOB_STATUS]".equals(bm)
							// ||"$[DIAGNOSIS_FROM]".equals(bm)
							// ||"$[LEAK_DETECTION] ".equals(bm)
							// ||"$[HAGARD_DESCRIBE]".equals(bm);//不换行
							for (int m = 0; m < con.length; m++) {
								String text = con[m];

								setTextStyle(run, style);
								// 用于设置字符串的格式

								run.setText(text);
								// if(!fbreak){
								if (m != con.length - 1) {
									run.addBreak();// 换两行
								}
								// run.addCarriageReturn();
							}
						} else {
							// setTextStyleLine(run, style);
							setTextStyle(run, style);
							// 用于设置字符串的格式
							run.setText(runText);

						}
					} else if (isHead.intValue() == 10) {// 职业个检尾部
						if (runText.indexOf("\n") != -1) {
							String con[] = runText.replaceAll("\r", "").split(
									"\n");
							for (String text : con) {
								if (!"".equals(text.trim())) {
									setTextStyle(run, style);
									// 用于设置字符串的格式

									run.setText(text);
									run.addBreak();
									// run.addCarriageReturn();//换两行
								}
							}
						} else {
							// setTextStyleLine(run, style);
							setTextStyle(run, style);
							// 用于设置字符串的格式
							run.setText(runText);

						}
					} else { // if (isHead==1){//不换行
						if ("$[totaloffer]".equals(bm)) {
							// if(runText.indexOf("\n")!=-1){
							// String con[] = runText.replaceAll("\r",
							// "").split("\n");
							String con[] = runText.replaceAll("\r", "").split(
									"\n");
							for (String text : con) {
								setTextStyle(run, style);
								// 用于设置字符串的格式
								run.setText(text);
								run.addCarriageReturn();
							}
							// }
						} else if ("$[tjjl]".equals(bm)) {
							if (runText.indexOf("\n") != -1) {
								String con[] = runText.replaceAll("\r", "")
										.split("\n");
								for (String text : con) {
									setTextStyle(run, style);
									// 用于设置字符串的格式
									run.setText(text);
									run.addCarriageReturn();
								}
							}
						} else if (bm.indexOf("$t[UNIT") != -1
								&& runText.indexOf("≤") != -1) {
							setTextStylefs(run, style);
							// 用于设置字符串的格式
							run.setText(runText);
						} else {
							setTextStyle(run, style);
							// 用于设置字符串的格式
							run.setText(runText);
						}
					}
				}

			}
		}
	}

	/**
	 * 根据图片科室名字查询其图片的高
	 *
	 * @Title: findHeigth
	 * @param depName
	 * @return String
	 * @author YINZL
	 * @since 2016年12月8日 V 1.0
	 */
	private String findHeigth(String depName) {
		String key = reportDao.getDepSrm(depName).toLowerCase() + ".heigth";
		String heigth = ToolUtil.getPropert("pic_size.properties", key);
		/*
		 * String heigth = null; if("眼底照相".equals(depName)){ heigth =
		 * ToolUtil.getPropert("pic_size.properties","ydzx.heigth"); } else if
		 * ("外送项目".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","wsxm.heigth"); }else if
		 * ("彩超".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","cc.heigth"); }else if
		 * ("放射科(DR)".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","dr.heigth"); }else if
		 * ("放射科(CR)".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","cr.heigth"); }else if
		 * ("数字钼靶".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","szmb.heigth"); }else if
		 * ("放射科(CT)".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","ct.heigth"); }else if
		 * ("动脉硬化室".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","dmyh.heigth"); }else if
		 * ("心电图室".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","xdt.heigth"); }else if
		 * ("骨密度检测".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","gmd.heigth"); }else if
		 * ("人体成分".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","rtcffx.heigth"); }else if
		 * ("经颅多普勒室".equals(depName)) { heigth =
		 * ToolUtil.getPropert("pic_size.properties","jldpl.heigth"); }
		 */
		return heigth.trim();
	}

	/**
	 * 根据图片科室名字查询其图片的宽
	 *
	 * @Title: findWidth
	 * @param depName
	 * @return String
	 * @author YINZL
	 * @since 2016年12月8日 V 1.0
	 */
	private String findWidth(String depName) {
		String key = reportDao.getDepSrm(depName).toLowerCase() + ".width";
		String width = ToolUtil.getPropert("pic_size.properties", key);
		/**
		 * String width = null; if("眼底照相".equals(depName)){ width =
		 * ToolUtil.getPropert("pic_size.properties","ydzx.width"); } else if
		 * ("外送项目".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","wsxm.width"); }else if
		 * ("彩超".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","cc.width"); }else if
		 * ("放射科(DR)".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","dr.width"); }else if
		 * ("放射科(CR)".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","cr.width"); }else if
		 * ("数字钼靶".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","szmb.width"); }else if
		 * ("放射科(CT)".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","ct.width"); }else if
		 * ("动脉硬化室".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","dmyh.width"); }else if
		 * ("心电图室".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","xdt.width"); }else if
		 * ("骨密度检测".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","gmd.width"); }else if
		 * ("人体成分".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","rtcffx.width"); }else if
		 * ("经颅多普勒室".equals(depName)) { width =
		 * ToolUtil.getPropert("pic_size.properties","jldpl.width"); }
		 */
		return width.trim();
	}

	/**
	 * 需要先删除当前run
	 *
	 * @Title: emptyPara
	 * @param para
	 *            段落 void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected void emptyPara(XWPFParagraph para) {
		List<XWPFRun> runs = para.getRuns();
		int size = runs.size();
		for (int i = 0; i < size; i++) {
			para.removeRun(0);
		}
	}

	/**
	 * 获得当前run的样式
	 *
	 * @Title: getTextStyle
	 * @param para
	 *            段落
	 * @return Map<String,Object>
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected Map<String, Object> getTextStyle(XWPFParagraph para) {
		Map<String, Object> style = new HashMap<String, Object>();
		List<XWPFRun> runs = para.getRuns();
		if (runs.size() > 0) {
			XWPFRun run = runs.get(0);
			style.put("bold", run.isBold());
			style.put("italic", run.isItalic());
			style.put("strike", run.isStrike());
			style.put("fontfamily", run.getFontFamily());
			style.put("fontsize",
					run.getFontSize() < 0 ? 11 : run.getFontSize());// 实际存在小数字体大小，会被转成整数(二号=6.7号，实际导出时变为7号)
			style.put("underline", run.getUnderline());
		}

		return style;
	}

	/**
	 * 获得当前run的文本
	 *
	 * @Title: getRunText
	 * @param matcher
	 * @param params
	 * @return String
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	@SuppressWarnings("static-access")
	protected String getRunText(Matcher matcher, Map<String, Object> params) {
		String runText = "";
		String paramname = matcher.group(1);
		String paramvalue = String
				.valueOf(params.get(paramname) != null ? params.get(paramname)
						: "");
		paramvalue = (paramvalue == null) ? "" : paramvalue;
		if (paramvalue.indexOf("@en") >= 0) {// 判断是否是英文型号，如果是则去掉英文符号@en
			paramvalue = paramvalue.substring(0, paramvalue.indexOf("@en"));
		}
		try {// 高亚杰修改与20151212 用于解决matcher特殊字符报错的问题
			runText = matcher.replaceFirst(paramvalue);
		} catch (Exception e) {
			runText = Matcher.quoteReplacement(paramvalue);
		}
		// runText = matcher.replaceFirst(paramvalue);
		return runText;
	}

	/**
	 * 设置当前run的样式
	 *
	 * @Title: setTextStyle
	 * @param run
	 * @param style
	 *            void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected void setTextStyle(XWPFRun run, Map<String, Object> style) {
		run.setBold((Boolean) style.get("bold"));
		run.setItalic((Boolean) style.get("italic"));
		run.setStrike((Boolean) style.get("strike"));
		run.setFontFamily((String) style.get("fontfamily"));
		/*
		 * run.setFontFamily((String) style.get("fontfamily")); CTRPr rpr =
		 * run.getCTR().isSetRPr() ? run.getCTR().getRPr() :
		 * run.getCTR().addNewRPr(); CTFonts fonts = rpr.isSetRFonts() ?
		 * rpr.getRFonts() : rpr.addNewRFonts(); fonts.setAscii("仿宋");
		 * fonts.setEastAsia("仿宋"); fonts.setHAnsi("仿宋");
		 */

		run.setFontSize((Integer) style.get("fontsize"));
		// run.setTextPosition((Integer)style.get("textposition"));
		run.setUnderline((UnderlinePatterns) style.get("underline"));
	}

	/**
	 * 设置当前run的样式
	 *
	 * @Title: setTextStyle
	 * @param run
	 * @param style
	 *            void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected void setTextStylefs(XWPFRun run, Map<String, Object> style) {
		run.setBold(true);
		run.setItalic((Boolean) style.get("italic"));
		run.setStrike((Boolean) style.get("strike"));
		run.setFontFamily((String) style.get("fontfamily"));
		CTRPr rpr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run
				.getCTR().addNewRPr();
		CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr
				.addNewRFonts();
		fonts.setAscii("仿宋");
		fonts.setEastAsia("仿宋");
		fonts.setHAnsi("仿宋");
		run.setFontSize((Integer) style.get("fontsize"));
		run.setUnderline((UnderlinePatterns) style.get("underline"));
	}

	/**
	 * 设置当前run的样式
	 *
	 * @Title: setTextStyle
	 * @param run
	 * @param style
	 *            void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected void setTextStyleLine(XWPFRun run, Map<String, Object> style) {
		run.setBold((Boolean) style.get("bold"));
		run.setItalic((Boolean) style.get("italic"));
		run.setStrike((Boolean) style.get("strike"));
		run.setFontFamily((String) style.get("fontfamily"));
		run.setFontSize((Integer) style.get("fontsize"));
		CTRPr pRpr = run.getCTR().addNewRPr();
		CTUnderline u = pRpr.isSetU() ? pRpr.getU() : pRpr.addNewU();
		// u.setVal(STUnderline.Enum.forInt(Math.abs(underLineStyle % 19)));
		u.setColor("7A7A7A");
	}

	/**
	 * 替换表格里面的变量
	 *
	 * @Title: replaceInTable
	 * @param doc
	 *            要替换的模板实例
	 * @param params
	 *            要替换的模板标记所对应的数据
	 * @param wordTool
	 *            工具类 void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected void replaceInTable(XWPFDocument doc, Map<String, Object> params,
			WordTool wordTool, String depName, Report rep, Integer isHead) {
		Iterator<XWPFTable> iterator = doc.getTablesIterator();

		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paras;
		while (iterator.hasNext()) {
			table = iterator.next();
			rows = table.getRows();
			for (XWPFTableRow row : rows) {
				cells = row.getTableCells();
				for (XWPFTableCell cell : cells) {
					paras = cell.getParagraphs();
					for (XWPFParagraph para : paras) {
						this.replaceInPara(para, params, wordTool, depName,
								rep, isHead);
					}
				}
			}
		}
	}

	/**
	 * 写入一个表格实验品
	 *
	 * @Title: writeTable
	 * @param doc
	 *            要替换的文档
	 * @param params
	 * @param wordTool
	 *            void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected void writeTable(XWPFDocument doc, Map<String, Object> params,
			WordTool wordTool) {

		XWPFTable table2 = doc.createTable(4, 2);
		// table.setCellMargins(50, 0, 50,3000);//top, left, bottom, right
		// table.setInsideHBorder(XWPFBorderType.NONE, 0, 0, "");//去除单元格间的横线
		table2.getRow(0).getCell(0).setText("字段一:");
		table2.getRow(0).getCell(1).setText("字段二:");
		table2.getRow(1).getCell(0).setText("字段三:");
		table2.getRow(1).getCell(1).setText("字段四:");

	}

	/**
	 * 生成图片
	 *
	 * @Title: setPictur
	 * @param width
	 *            图宽
	 * @param height
	 *            图高
	 * @param picUrl
	 *            图片路径
	 * @param picName
	 *            图片名字 文件图片的${}标示
	 * @param wordTool
	 *            工具类
	 * @return
	 * @throws Exception
	 *             XWPFDocument
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public XWPFDocument setPictur(String width, String height, String picUrl,
			String picName, WordTool wordTool, Report rep) throws Exception {

		Map<String, Object> pictur = new HashMap<String, Object>();
		// 图片样式设置
		Map<String, Object> header = new HashMap<String, Object>();
		header.put("width", width); // 图片长度
		header.put("height", height); // 图片宽度
		header.put("type", "png"); // 图片格式
		try {
			// if(picUrl.indexOf("\\\\XXX.XXX.XXX.XXX")!=-1){
			// picUrl=picUrl.replace("\\\\XXX.XXX.XXX.XXX\\",
			// "\\\\XXX.XXX.XXX.XXX\\");
			// }

			// File inFile=new File(ToolUtil.getPacsPath(picUrl));
			// if(picUrl.startsWith("image")){
			// String path = ToolUtil.getPropert("doc_config.properties",
			// "real_path");
			// picUrl = path+picUrl;
			// }
			URL url = new URL(picUrl);
			// 打开URL连接
			URLConnection urlConnection = url.openConnection();
			// 获取输入流
			InputStream file = urlConnection.getInputStream();
			byte[] str = wordTool.inputStream2ByteArray(file, true);
			header.put("content", str);// 图片路径
		} catch (Exception e) {
			e.printStackTrace();
			updateReportGenerate(picUrl + "下图片转换异常", rep);
			return wordTool.getDoc();
		}

		pictur.put(picName, header); // 文件图片的模板名称-图片标示
		pictur.put("paraname", picName);// 图片参数的参数名
		// 接收旧的doc，插入图片，返回新的doc
		return wordTool.exportPictures(pictur, wordTool.getDoc());

	}

	/**
	 * 生成图片(用于保存head和brondcode
	 *
	 * @Title: createPictur
	 * @param width
	 *            图宽
	 * @param height
	 *            图高
	 * @param
	 *
	 * @param picName
	 *            图片名字 文件图片的${}标示
	 * @param wordTool
	 *            工具类
	 * @return
	 * @throws Exception
	 *             XWPFDocument
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	@SuppressWarnings({ "resource", "unused" })
	public XWPFDocument createPictur(String width, String height, String pic,
			String picName, WordTool wordTool, Report rep) throws Exception {

		Map<String, Object> pictur = new HashMap<String, Object>();
		// 图片样式设置
		Map<String, Object> header = new HashMap<String, Object>();
		header.put("width", width); // 图片长度
		header.put("height", height); // 图片宽度
		header.put("type", "jpg"); // 图片格式
		try {
			if (!StringUtils.isEmpty(pic)) {
				pic = pic.substring(pic.indexOf(",") + 1);
				BASE64Decoder decoder = new BASE64Decoder();

				// Base64解码
				byte[] b = decoder.decodeBuffer(pic);
				header.put("content", b);// 图片路径

				ByteArrayInputStream inn = new ByteArrayInputStream(b);
				byte[] buffer = new byte[10240];
				FileOutputStream out = new FileOutputStream("D:/ce.jpg");
				int bytesum = 0;
				int byteread = 0;
				while ((byteread = inn.read(buffer)) != -1) {
					bytesum += byteread;
					out.write(buffer, 0, byteread); // 文件写操作
				}
			}
		} catch (Exception e) {
			updateReportGenerate("图片编码解析异常", rep);
			e.printStackTrace();
			return wordTool.getDoc();
		}
		pictur.put(picName, header); // 文件图片的模板名称-图片标示
		pictur.put("paraname", picName);// 图片参数的参数名
		// 接收旧的doc，插入图片，返回新的doc
		return wordTool.exportPictures(pictur, wordTool.getDoc());

	}

	/**
	 * 根据指定的图片参数值、doc模板，生成新的doc模板
	 *
	 * @Title: exportPictures
	 * @param param
	 *            需要替换的图片变量
	 * @param pfddoc
	 *            XWPFDocument文档
	 * @return XWPFDocument
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public XWPFDocument exportPictures(Map<String, Object> param,
			XWPFDocument pfddoc) {

		try {
			if (param != null && param.size() > 0) {
				// 处理段落中的图片
				List<XWPFParagraph> paragraphList = pfddoc.getParagraphs();
				pfddoc = processParagraphs(paragraphList, param, pfddoc);
				// 处理表格中的图片
				Iterator<XWPFTable> it = pfddoc.getTablesIterator();
				while (it.hasNext()) {
					XWPFTable table = it.next();
					List<XWPFTableRow> rows = table.getRows();
					// 查找相应的位置
					for (XWPFTableRow row : rows) {
						List<XWPFTableCell> cells = row.getTableCells();
						for (XWPFTableCell cell : cells) {
							List<XWPFParagraph> paragraphListTable = cell
									.getParagraphs();
							// 插入图片
							pfddoc = processParagraphs(paragraphListTable,
									param, pfddoc);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pfddoc;
	}

	/**
	 * 处理段落
	 *
	 * @Title: processParagraphs
	 * @param paragraphList
	 * @param param
	 *            需要替换的图片变量
	 * @param pfddoc
	 *            模板实例
	 * @return XWPFDocument
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	protected XWPFDocument processParagraphs(List<XWPFParagraph> paragraphList,
			Map<String, Object> param, XWPFDocument pfddoc) {
		Matcher matcherpic;
		if (paragraphList != null && paragraphList.size() >= 0) {
			for (XWPFParagraph paragraph : paragraphList) {
				// 验证图片的标签规则
				String str = paragraph.getParagraphText();
				if (this.matcherpic(str).find()) {
					String runText = paragraph.getParagraphText();
					int i = runText.indexOf("$p["
							+ param.get("paraname").toString());
					if (i == -1) {
						continue;
					}
					matcherpic = this.matcherpic(runText.substring(i));
					if (matcherpic.find()
							&& matcherpic.group(1)
							.equals(param.get("paraname"))) {// 判断要替换的参数是否和图片对应
						String s = matcherpic.group(1);
						for (Entry<String, Object> entry : param.entrySet()) {
							Object value = entry.getValue();
							if (value instanceof String) {// 文本替换
							} else if (value instanceof Map) {// 图片替换
								Map pic = (Map) value;
								int width = 300;
								int height = 300;
								if (pic.get("width") != null
										&& !pic.get("width").equals("")) {
									width = Integer.parseInt(pic.get("width")
											.toString().trim()); // 宽度
								}
								if (pic.get("height") != null
										&& !pic.get("height").equals("")) {
									height = Integer.parseInt(pic.get("height")
											.toString().trim()); // 宽度
								}
								int picType = Document.PICTURE_TYPE_JPEG; // 图片类型
								byte[] byteArray = (byte[]) pic.get("content"); // 图片路径
								ByteArrayInputStream byteInputStream = new ByteArrayInputStream(
										byteArray);
								try {
									String blipId = pfddoc.addPictureData(
											byteInputStream, picType); // 插入的位置
									int id = pfddoc
											.getNextPicNameNumber(Document.PICTURE_TYPE_PNG);
									if (runText.indexOf(" ") != -1) {
										String[] stl = runText.split(" ");
										if (stl.length > 0) {
											Matcher matchertt = this
													.matcherpic(stl[stl.length - 1]);
											if (matchertt.find()
													&& matchertt
													.group(1)
													.equals(param
															.get("paraname"))) {
												List<XWPFRun> runs = paragraph
														.getRuns();
												int size = runs.size();
												paragraph.removeRun(0);
											}
											createPicture(blipId, picType,
													width, height, paragraph);
											return pfddoc;
										} else {
											emptyPara(paragraph);
											createPicture(blipId, picType,
													width, height, paragraph);
											return pfddoc;
										}
									} else {
										emptyPara(paragraph);
										createPicture(blipId, picType, width,
												height, paragraph);
										return pfddoc;
									}

									// 插入图片
									// return exportPicture(ind, width, height,
									// paragraph, pfddoc);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}

					}
				}
			}
		}
		return pfddoc;
	}

	public void createPicture(String blipId, int id, int width, int height,
			XWPFParagraph paragraph) {
		final int EMU = 9525;
		width *= EMU;
		height *= EMU;
		// String blipId =
		// getAllPictures().get(id).getPackageRelationship().getId();
		/*
		 * if (paragraph == null) { paragraph = createParagraph(); }
		 */
		CTInline inline = paragraph.createRun().getCTR().addNewDrawing()
				.addNewInline();
		String picXml = ""
				+ "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
				+ "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
				+ id
				+ "\" name=\"img_"
				+ id
				+ "\"/>"
				+ "            <pic:cNvPicPr/>"
				+ "         </pic:nvPicPr>"
				+ "         <pic:blipFill>"
				+ "            <a:blip r:embed=\""
				+ blipId
				+ "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
				+ "            <a:stretch>"
				+ "               <a:fillRect/>"
				+ "            </a:stretch>"
				+ "         </pic:blipFill>"
				+ "         <pic:spPr>"
				+ "            <a:xfrm>"
				+ "               <a:off x=\"0\" y=\"0\"/>"
				+ "               <a:ext cx=\""
				+ width
				+ "\" cy=\""
				+ height
				+ "\"/>"
				+ "            </a:xfrm>"
				+ "            <a:prstGeom prst=\"rect\">"
				+ "               <a:avLst/>"
				+ "            </a:prstGeom>"
				+ "         </pic:spPr>"
				+ "      </pic:pic>"
				+ "   </a:graphicData>" + "</a:graphic>";
		// CTGraphicalObjectData graphicData =
		// inline.addNewGraphic().addNewGraphicData();
		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(picXml);
		} catch (XmlException xe) {
			xe.printStackTrace();
		}
		inline.set(xmlToken);
		// graphicData.set(xmlToken);
		inline.setDistT(100);
		inline.setDistB(100);
		inline.setDistL(100);
		inline.setDistR(100);
		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(width);
		extent.setCy(height);
		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(id);
		docPr.setName("img_ " + id);
		docPr.setDescr("Picture");
	}

	public void createPictureCxCy(String blipId, int id, long cx, long cy,
			XWPFParagraph paragraph) {
		CTInline inline = paragraph.createRun().getCTR().addNewDrawing()
				.addNewInline();
		String picXml = ""
				+ "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
				+ "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "         <pic:nvPicPr>" + "            <pic:cNvPr id=\""
				+ id
				+ "\" name=\"Generated\"/>"
				+ "            <pic:cNvPicPr/>"
				+ "         </pic:nvPicPr>"
				+ "         <pic:blipFill>"
				+ "            <a:blip r:embed=\""
				+ blipId
				+ "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
				+ "            <a:stretch>"
				+ "               <a:fillRect/>"
				+ "            </a:stretch>"
				+ "         </pic:blipFill>"
				+ "         <pic:spPr>"
				+ "            <a:xfrm>"
				+ "               <a:off x=\"0\" y=\"0\"/>"
				+ "               <a:ext cx=\""
				+ cx
				+ "\" cy=\""
				+ cy
				+ "\"/>"
				+ "            </a:xfrm>"
				+ "            <a:prstGeom prst=\"rect\">"
				+ "               <a:avLst/>"
				+ "            </a:prstGeom>"
				+ "         </pic:spPr>"
				+ "      </pic:pic>"
				+ "   </a:graphicData>" + "</a:graphic>";
		// CTGraphicalObjectData graphicData =
		// inline.addNewGraphic().addNewGraphicData();
		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(picXml);
		} catch (XmlException xe) {
			xe.printStackTrace();
		}
		inline.set(xmlToken);
		// graphicData.set(xmlToken);
		inline.setDistT(0);
		inline.setDistB(0);
		inline.setDistL(0);
		inline.setDistR(0);
		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(cx);
		extent.setCy(cy);
		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(id);
		docPr.setName("docx_img_ " + id);
		docPr.setDescr("docx Picture");
	}

	/**
	 * 直接输出图片
	 *
	 * @Title: exportPicture
	 * @param id
	 *            图片的ID
	 * @param width
	 *            图片的宽度
	 * @param height
	 *            图片的高度
	 * @param paragraph
	 * @param pfddoc
	 * @return XWPFDocument
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0 方法适用于poi3.7版本 deprecated by 没有地方使用过此方法
	 *        且getPackageRelationship方发只存在于低版本
	 */
	@Deprecated
	public XWPFDocument exportPicture(int id, int width, int height,
			XWPFParagraph paragraph, XWPFDocument pfddoc) {
		final int EMU = 9525;
		width *= EMU;
		height *= EMU;
		String blipId = null;
		// pfddoc.getAllPictures().get(id)
		// .getPackageRelationship().getId();
		CTInline inline = paragraph.createRun().getCTR().addNewDrawing()
				.addNewInline();
		String picXml = ""
				+ "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
				+ "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
				+ "         <pic:nvPicPr>" + "<pic:cNvPr id=\""
				+ id
				+ "\" name=\"Generated\"/>"
				+ "            <pic:cNvPicPr/>"
				+ "         </pic:nvPicPr>"
				+ "         <pic:blipFill>"
				+ "            <a:blip r:embed=\""
				+ blipId
				+ "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
				+ "            <a:stretch>"
				+ "               <a:fillRect/>"
				+ "            </a:stretch>"
				+ "         </pic:blipFill>"
				+ "         <pic:spPr>"
				+ "            <a:xfrm>"
				+ "               <a:off x=\"0\" y=\"0\"/>"
				+ "               <a:ext cx=\""
				+ width
				+ "\" cy=\""
				+ height
				+ "\"/>"
				+ "            </a:xfrm>"
				+ "            <a:prstGeom prst=\"rect\">"
				+ "               <a:avLst/>"
				+ "            </a:prstGeom>"
				+ "         </pic:spPr>"
				+ "      </pic:pic>"
				+ "   </a:graphicData>" + "</a:graphic>";

		// inline.addNewGraphic().addNewGraphicData();
		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(picXml);
		} catch (XmlException xe) {
			xe.printStackTrace();
		}
		inline.set(xmlToken);

		inline.setDistT(0);
		inline.setDistB(0);
		inline.setDistL(0);
		inline.setDistR(0);

		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(width);
		extent.setCy(height);

		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(id);
		docPr.setName("图片" + id);
		docPr.setDescr("测试");

		return pfddoc;
	}

	/**
	 * 替换页眉里面的变量
	 *
	 * @Title: replaceInHeader
	 * @param doc
	 *            要替换的文档
	 * @param params
	 * @param wordTool
	 * @return XWPFDocument
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	public XWPFDocument replaceInHeader(XWPFDocument doc,
			Map<String, Object> params, WordTool wordTool, String depName,
			Report rep, Integer isHead) {
		// 获得文档里的所有页眉
		Iterator<XWPFHeader> iterator = doc.getHeaderList().iterator();
		XWPFHeader header;
		while (iterator.hasNext()) {
			header = iterator.next();
			// 获取页眉中的段落
			Iterator<XWPFParagraph> parIterator = header.getParagraphs()
					.iterator();
			XWPFParagraph paragraph;
			while (parIterator.hasNext()) {
				paragraph = parIterator.next();
				this.replaceInPara(paragraph, params, wordTool, depName, rep,
						isHead);
			}

			// 获得页眉里的表格
			Iterator<XWPFTable> tableIterator = header.getTables().iterator();
			XWPFTable table;
			List<XWPFTableRow> rows;
			List<XWPFTableCell> cells;
			List<XWPFParagraph> paras;
			// 遍历表格
			while (tableIterator.hasNext()) {
				table = tableIterator.next();
				rows = table.getRows();
				for (XWPFTableRow row : rows) {
					cells = row.getTableCells();
					for (XWPFTableCell cell : cells) {
						paras = cell.getParagraphs();
						for (XWPFParagraph para : paras) {
							this.replaceInPara(para, params, wordTool, depName,
									rep, isHead);
						}
					}
				}
			}
		}
		return doc;
	}

	/**
	 * 将输入流中的数据写入字节数组
	 *
	 * @Title: inputStream2ByteArray
	 * @param in
	 *            输入流
	 * @param isClose
	 *            是否要关闭流
	 * @return byte[]
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected byte[] inputStream2ByteArray(InputStream in, boolean isClose) throws IOException {
//		byte[] byteArray = null;
//		try {
//			int total = in.available();
//			byteArray = new byte[total];
//			in.read(byteArray);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (isClose) {
//				try {
//					in.close();
//				} catch (Exception e2) {
//				}
//			}
//		}
//		return byteArray;
		// 将输入流中的数据写入字节数组
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, bytesRead);
		}

		// 关闭流
		in.close();

		// 获取字节数组
		byte[] imageBytes = byteArrayOutputStream.toByteArray();
		return imageBytes;
	}

	/**
	 * 正则匹配模板字符串规则
	 *
	 * @Title: matcher
	 * @param str
	 * @return Matcher
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected Matcher matcher(String str) {
		Pattern pattern = Pattern.compile("\\$[tp]?\\[(.+?)\\]",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	/**
	 * 正则匹配模板图片规则
	 *
	 * @Title: matcherpic
	 * @param str
	 * @return Matcher
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected Matcher matcherpic(String str) {
		Pattern pattern = Pattern.compile("\\$[p]\\[(.+?)\\]");
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	/**
	 * 正则匹配模板table规则
	 *
	 * @Title: matchertable
	 * @param str
	 * @return Matcher
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected Matcher matchertable(String str) {
		Pattern pattern = Pattern.compile("\\$[t]\\[(.+?)\\]");
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	/**
	 * 正则匹配模板多table规则
	 *
	 * @Title: matcherntable
	 * @param str
	 * @return Matcher
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected Matcher matcherntable(String str) {
		Pattern pattern = Pattern.compile("\\$[n]\\[(.+?)\\]");
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}

	/**
	 * 关闭输入流
	 *
	 * @Title: close
	 * @param is
	 *            void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected void close(FileInputStream is) {
		try {
			if (is != null && !is.getFD().valid()) {
				is.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 关闭输入流
	 *
	 * @Title: close
	 * @param os
	 *            void
	 * @author yinzl
	 * @since 2016年9月19日 V 1.0
	 */
	protected void close(FileOutputStream os) {
		try {
			if (os != null && !os.getFD().valid()) {
				os.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 更新一下报告的错误日志
	 *
	 * @Title: updateReportGenerate
	 * @param errorMessage
	 * @param rep
	 *            void
	 * @author YINZL
	 * @since 2017年1月10日 V 1.0
	 */
	public void updateReportGenerate(String errorMessage, Report rep) {
		try {
			if (rep != null) {
				rep.setGenerateHint((rep.getGenerateHint() == null ? "" : rep
						.getGenerateHint()) + ";" + errorMessage);
				rep.setGenerateDate(new Date());

				// rep.setGenerateName(toolUtil.getUser().getName());
//				reportDao.update(rep);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * String类型转换成Clob类型
//	 *
//	 * @Title: StringToClob
//	 * @param string
//	 * @return Clob
//	 * @author YINZL
//	 * @since 2017年1月10日 V 1.0
//	 */
//	public Clob StringToClob(final String string) {
//
//		if (null == string || string.trim().length() == 0) {
//			return null;
//		}
//		return new org.hibernate.lob.ClobImpl(string);
//	}

	/**
	 * Clob类型转换成String类型
	 *
	 * @Title: ClobToString
	 * @param clob
	 * @return String
	 * @author YINZL
	 * @since 2017年1月10日 V 1.0
	 */
	public String ClobToString(final Clob clob) {

		if (clob == null) {
			return null;
		}

		Reader is = null;
		try {
			is = clob.getCharacterStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(is);

		String str = null;
		try {
			str = br.readLine(); // 读取第一行
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringBuffer sb = new StringBuffer();
		while (str != null) { // 如果没有到达流的末尾，则继续读取下一行
			sb.append(str);
			try {
				str = br.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String returnString = sb.toString();

		return returnString;
	}

	/**
	 * 电测听
	 *
	 * @Title: exportTableDct
	 * @param params
	 * @param fileName
	 * @param doc
	 * @param url
	 * @param wordTool
	 * @param depName
	 * @param rep
	 * @param isHead
	 * @param hasBone
	 * @return boolean
	 * @author xuhp
	 * @since 2017-5-9 V 1.0
	 */
	public boolean exportTableDct(Map<String, Object> params, String fileName,
			XWPFDocument doc, String url, WordTool wordTool, String depName,
			Report rep, Integer isHead, Boolean hasBone) {
		try {
			if (!hasBone) {
				Iterator<XWPFTable> tabIterator = doc.getTablesIterator();
				XWPFTable table = tabIterator.next();
				int rowNum = table.getNumberOfRows();
				table.removeRow(rowNum - 1);
				table.removeRow(rowNum - 2);
			}
			// 替换段落里面的变量
			this.replaceInPara(doc, params, wordTool, depName, rep, isHead);
			// 替换表格里面的变量
			this.replaceInTable(doc, params, wordTool, depName, rep, isHead);

			fileOutput = new FileOutputStream(url);

			doc.write(fileOutput);

			fileOutput.flush();

			this.close(fileOutput);

			this.close(fileInput);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public FileInputStream getFileInput() {
		return fileInput;
	}

	public void setFileInput(FileInputStream fileInput) {
		this.fileInput = fileInput;
	}

	public FileOutputStream getFileOutput() {
		return fileOutput;
	}

	public void setFileOutput(FileOutputStream fileOutput) {
		this.fileOutput = fileOutput;
	}

	public XWPFDocument getDoc() {
		return doc;
	}

	public void setDoc(XWPFDocument doc) {
		this.doc = doc;
	}
}
