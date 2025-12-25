package com.center.medical.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 超过6****行会报错
 * 大数据量导致内存溢出
 * @ClassName: ExcelUtil 
 * @Description: excel导入、导出工具 
 * @author adam
 * @date 2015-9-29 下午4:35:17 
 *
 */
@SuppressWarnings("deprecation")
public class ExcelUtil {
    
    /**
     * 
     * @Title: export excel导出方法
     * @param title excel 标题和名称
     * @param topData   表格上部分数据
     * @param tableCaption 表格标题头
     * @param tableData 表格内容
     * @param footData  表格脚
     * void 
     * @author adam
     * @since 2015-9-29 V 1.0
     */
    @SuppressWarnings({ "rawtypes" })
    public static void export(String title,ArrayList topData,String[] tableCaption,ArrayList<ArrayList> tableData,ArrayList footData){
        HttpServletResponse response  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            XSSFWorkbook hwb = new XSSFWorkbook(); // 修改: HSSFWorkbook -> XSSFWorkbook
            XSSFSheet sheet = hwb.createSheet(title); // 修改: HSSFSheet -> XSSFSheet
            
            int cellLenght=tableCaption.length;//列宽
            int row_num=0;
            //添加空白头
//            sheet.addMergedRegion(new CellRangeAddress(row_num, (short) 0, row_num,(short)(cellLenght-1)));   
            XSSFRow row = sheet.createRow(0); // 修改: HSSFRow -> XSSFRow
//            row_num++;
            
            //标题
            sheet.addMergedRegion(new CellRangeAddress(row_num, row_num, (short) 0,(short)(cellLenght-1)));   
            row = sheet.createRow(row_num);
            row.setHeight((short) 600);
            XSSFCell cell =row.createCell(0); // 修改: HSSFCell -> XSSFCell
            cell.setCellValue(title); // 跨单元格显示的数据   
            cell.setCellStyle(ExcelUtil.STYLE1(hwb,20));   
            row_num++;
            
            //表头部分
            int index=row_num;//excel已经生成的行数
            if(topData!=null){
                for(int i=0;i<topData.size();i++){//循环生成
                    if(i%2==0){
                        int index_i=index+i/2;
                        sheet.addMergedRegion(new CellRangeAddress(index_i, index_i,(short) 0, (short)(cellLenght/2-1)));   
                        sheet.addMergedRegion(new CellRangeAddress(index_i, index_i,(short)(cellLenght/2), (short)(cellLenght-1)));   
                        row = sheet.createRow(index_i);
                        cell =row.createCell(0);
                        cell.setCellValue(topData.get(i).toString()); 
                        cell =row.createCell((short)(cellLenght/2));
                        if(i+1<topData.size()){
                            cell.setCellValue(topData.get(i+1).toString()); 
                        }
                        row_num++;
                    }
                }
            }
            
            //表格栏目
            row = sheet.createRow(row_num);
            for(int i = 0;i<cellLenght;i++){
                cell = row.createCell(i);
                cell.setCellValue(tableCaption[i]);
                cell.setCellStyle(ExcelUtil.STYLE2(hwb));
                sheet.setColumnWidth((short)i, tableCaption[i].getBytes().length*256*5/3);
                //sheet.autoSizeColumn(cellname[i].length()*2*256);
            }
            row_num++;
            
            //遍历生成表格数据(放入数字类型，导出后就可以正常求和)
            CellStyle cellStyle = ExcelUtil.STYLE2(hwb);
            for(int i = 0;i<tableData.size();i++){
                row = sheet.createRow(i+row_num);
                for(int j=0;j<(tableData.get(i)).size();j++){
                    row.createCell(j).setCellStyle(cellStyle);
                    Object o=tableData.get(i).get(j);
                    if (o instanceof Integer) {  
                        row.getCell(j).setCellValue((Integer)o);
                    } else if (o instanceof Float) {  
                        row.getCell(j).setCellValue((Float)o);
                    } else if (o instanceof Double) {  
                        row.getCell(j).setCellValue((Double)o);
                    } else if (o instanceof BigDecimal) {  
                        row.getCell(j).setCellValue(((BigDecimal)o).doubleValue());
                    } else if (o instanceof Long) {  
                         row.getCell(j).setCellValue((Long)o);
                    } else if(o instanceof Date) {
                        Date date = (Date) o;  
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                        row.getCell(j).setCellValue(sdf.format(date));  
                    } else if(o==null){
                        row.getCell(j).setCellValue("");
                    } else {
                        row.getCell(j).setCellValue(o.toString());
                    }
                }
            }
    
            //表脚部分
            int row_index=row_num+1;
            if(tableData!=null){
                row_index +=tableData.size();
            }
    
            if(footData!=null){
                int footDataSize=footData.size();//页脚个数
                
                row = sheet.createRow(row_index);
                for(int i=0;i<footDataSize;i++){
                    int cell_start= i*(cellLenght/footDataSize);
                    int cell_end=(i+1)*(cellLenght/footDataSize)-1;
                    if(i==footDataSize-1){
                        cell_end=cellLenght-1;
                    }
                    sheet.addMergedRegion(new CellRangeAddress(row_index,row_index, (short)cell_start,(short)cell_end)); 
                    cell =row.createCell(cell_start);
                    cell.setCellValue((String)footData.get(i)); 
                }

            }
            
            XSSFPrintSetup printSetup = sheet.getPrintSetup();//A4纸  // 修改: HSSFPrintSetup -> XSSFPrintSetup
            printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);  // 修改: HSSFPrintSetup.A4_PAPERSIZE -> PrintSetup.A4_PAPERSIZE
            
            response = getResponse(response, request, title);
            OutputStream ouputStream = response.getOutputStream();  
            
            hwb.write(ouputStream);  
            ouputStream.flush();  
            ouputStream.close();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
    
    @SuppressWarnings({ "rawtypes" })
    public static void exportToLocal(String title,ArrayList topData,String[] tableCaption,ArrayList<ArrayList> tableData,ArrayList footData,FileOutputStream ouputStream ){
        try {
            XSSFWorkbook hwb = new XSSFWorkbook();
            XSSFSheet sheet = hwb.createSheet(title);
            
            int cellLenght=tableCaption.length;//列宽
            int row_num=0;
            //添加空白头
            XSSFRow row = sheet.createRow(0);
//            row_num++;
            
            //标题
            sheet.addMergedRegion(new CellRangeAddress(row_num, row_num, (short) 0,(short)(cellLenght-1)));   
            row = sheet.createRow(row_num);
            row.setHeight((short) 600);
            XSSFCell cell =row.createCell(0);
            cell.setCellValue(title); // 跨单元格显示的数据   
            cell.setCellStyle(ExcelUtil.STYLE1(hwb,20));   
            row_num++;
            
            //表头部分
            int index=row_num;//excel已经生成的行数
            if(topData!=null){
                for(int i=0;i<topData.size();i++){//循环生成
                    if(i%2==0){
                        int index_i=index+i/2;
                        sheet.addMergedRegion(new CellRangeAddress(index_i, index_i,(short) 0, (short)(cellLenght/2-1)));   
                        sheet.addMergedRegion(new CellRangeAddress(index_i, index_i,(short)(cellLenght/2), (short)(cellLenght-1)));   
                        row = sheet.createRow(index_i);
                        cell =row.createCell(0);
                        cell.setCellValue(topData.get(i).toString()); 
                        cell =row.createCell((short)(cellLenght/2));
                        if(i+1<topData.size()){
                            cell.setCellValue(topData.get(i+1).toString()); 
                        }
                        row_num++;
                    }
                }
            }
            
            
            //表格栏目
            row = sheet.createRow(row_num);
            for(int i = 0;i<cellLenght;i++){
                cell = row.createCell(i);
                cell.setCellValue(tableCaption[i]);
                cell.setCellStyle(ExcelUtil.STYLE2(hwb));
                sheet.setColumnWidth((short)i, tableCaption[i].getBytes().length*256*5/3);
                //sheet.autoSizeColumn(cellname[i].length()*2*256);
            }
            row_num++;
            
            //遍历生成表格数据
            CellStyle cellStyle = ExcelUtil.STYLE2(hwb);
            for(int i = 0;i<tableData.size();i++){
                row = sheet.createRow(i+row_num);
                for(int j=0;j<(tableData.get(i)).size();j++){
                    row.createCell(j).setCellStyle(cellStyle);
                    Object o=tableData.get(i).get(j);
                    if (o instanceof Integer) {  
                        row.getCell(j).setCellValue((Integer)o);
                    } else if (o instanceof Float) {  
                        row.getCell(j).setCellValue((Float)o);
                    } else if (o instanceof Double) {  
                        row.getCell(j).setCellValue((Double)o);
                    } else if (o instanceof Long) {  
                         row.getCell(j).setCellValue((Long)o);
                    } else if(o instanceof Date) {
                        Date date = (Date) o;  
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                        row.getCell(j).setCellValue(sdf.format(date));  
                    } else if(o==null){
                        row.getCell(j).setCellValue("");
                    } else {
                        row.getCell(j).setCellValue(o.toString());
                    }
                }
            }
    
            
            //表脚部分
            int row_index=row_num+1;
            if(tableData!=null){
                row_index +=tableData.size();
            }
    
            if(footData!=null){
                int footDataSize=footData.size();//页脚个数
                
                row = sheet.createRow(row_index);
                for(int i=0;i<footDataSize;i++){
                    int cell_start= i*(cellLenght/footDataSize);
                    int cell_end=(i+1)*(cellLenght/footDataSize)-1;
                    if(i==footDataSize-1){
                        cell_end=cellLenght-1;
                    }
                    sheet.addMergedRegion(new CellRangeAddress(row_index,row_index, (short)cell_start,(short)cell_end)); 
                    cell =row.createCell(cell_start);
                    cell.setCellValue((String)footData.get(i)); 
                }

            }
            
            XSSFPrintSetup printSetup = sheet.getPrintSetup();//A4纸
            printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);  
            
            hwb.write(ouputStream);  
            ouputStream.flush();  
            ouputStream.close();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    /**
     * @Title: 多个sheet页Excel导出 
     * @param fileName 文件名
     * @param title excel 标题和名称
     * @param topData   表格上部分数据
     * @param tableCaption 表格标题头
     * @param tableData 表格内容
     * @param footData  表格脚
     * void 
     * @author zhanghj
     * @since 2016-8-30 V 1.0
     * <p>例如:</p>
     * <pre>
     *        // 表格内容集合
     *        ArrayList<ArrayList>[] tableDatas = new ArrayList[2];
     *        tableDatas[0] = tableData;
     *        tableDatas[1] = tableDataTwo;
     *
     *        //表格脚部分
     *        ArrayList footData=new ArrayList();
     *        String dcr=ToolUtil.userName();
     *        Date dcrq=new Date();
     *        footData.add("导出人： "+ ExcelUtil.toString(dcr));
     *        footData.add("导出日期： "+ ExcelUtil.toString(dcrq));
     *        ArrayList[] footDatas=new ArrayList[2];
     *        footDatas[0] = footData;
     *        footDatas[1] = footData;
     *
     *        // 标题1
     *        String[] tableCaption ={""};
     *
     *        // 标题2
     *        String[] patientCaption ={""};
     *        String[][] tableCaptions = {tableCaption, patientCaption};
     *        String[] title = {"xxxx", "xxxxxxx"};
     *        String time=new SimpleDateFormat("yyyyMM-dd_HH_mm_ss").format(Calendar.getInstance().getTime());
     *        ExcelUtil.exports("xxxxx"+time, title, null, tableCaptions, tableDatas, footDatas);
     * </pre>
     */
    @SuppressWarnings("rawtypes")
    public static void exports(String fileName, String[] title, ArrayList[] topData, String[][] tableCaption, ArrayList<ArrayList>[] tableData, ArrayList[] footData) {
        HttpServletResponse response  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            XSSFWorkbook hwb = new XSSFWorkbook();
            // 循环遍历
            for (int i = 0; i < title.length; i++) {
                exportBase(hwb, title[i], null == topData? null:topData[i], tableCaption[i], tableData[i], footData[i]);
            }
            response = getResponse(response, request, fileName);
            OutputStream ouputStream = response.getOutputStream();  
            
            hwb.write(ouputStream);  
            ouputStream.flush();  
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: export excel导出方法
     * @param hwb 表格类
     * @param title excel 标题和名称
     * @param topData   表格上部分数据
     * @param tableCaption 表格标题头
     * @param tableData 表格内容
     * @param footData  表格脚
     * void 
     * @author zhanghj
     * @since 2016-8-30 V 1.0
     */
    @SuppressWarnings("rawtypes")
    public static void exportBase(XSSFWorkbook hwb, String title,ArrayList topData,String[] tableCaption,ArrayList<ArrayList> tableData,ArrayList footData) { // 修改: HSSFWorkbook -> XSSFWorkbook
        XSSFSheet sheet = hwb.createSheet(title); // 修改: HSSFSheet -> XSSFSheet

        int cellLenght = tableCaption.length;// 列宽
        int row_num = 0;
        // 添加空白头
        // sheet.addMergedRegion(new CellRangeAddress(row_num, (short) 0,
        // row_num,(short)(cellLenght-1)));
        XSSFRow row = sheet.createRow(0); // 修改: HSSFRow -> XSSFRow
        // row_num++;

        // 标题
        sheet.addMergedRegion(new CellRangeAddress(row_num, row_num,(short) 0, 
                (short) (cellLenght - 1)));
        row = sheet.createRow(row_num);
        row.setHeight((short) 600);
        XSSFCell cell = row.createCell(0); // 修改: HSSFCell -> XSSFCell
        cell.setCellValue(title); // 跨单元格显示的数据
        cell.setCellStyle(ExcelUtil.STYLE1(hwb, 20));
        row_num++;

        // 表头部分
        int index = row_num;// excel已经生成的行数
        if (topData != null) {
            for (int i = 0; i < topData.size(); i++) {// 循环生成
                if (i % 2 == 0) {
                    int index_i = index + i / 2;
                    sheet.addMergedRegion(new CellRangeAddress(index_i, index_i,(short) 0,  (short) (cellLenght / 2 - 1)));
                    sheet.addMergedRegion(new CellRangeAddress(index_i,  index_i,(short) (cellLenght / 2), (short) (cellLenght - 1)));
                    row = sheet.createRow(index_i);
                    cell = row.createCell(0);
                    cell.setCellValue(topData.get(i).toString());
                    cell = row.createCell((short) (cellLenght / 2));
                    if (i + 1 < topData.size()) {
                        cell.setCellValue(topData.get(i + 1).toString());
                    }
                    row_num++;
                }
            }
        }

        // 表格栏目
        row = sheet.createRow(row_num);
        for (int i = 0; i < cellLenght; i++) {
            cell = row.createCell(i);
            cell.setCellValue(tableCaption[i]);
            cell.setCellStyle(ExcelUtil.STYLE2(hwb));
            sheet.setColumnWidth((short) i, tableCaption[i].getBytes().length * 256 * 5 / 3);
            // sheet.autoSizeColumn(cellname[i].length()*2*256);
        }
        row_num++;

        // 遍历生成表格数据
        CellStyle cellStyle = ExcelUtil.STYLE2(hwb);
        for (int i = 0; i < tableData.size(); i++) {
            row = sheet.createRow(i + row_num);
            for (int j = 0; j < (tableData.get(i)).size(); j++) {
                row.createCell(j).setCellStyle(cellStyle);
                Object o = tableData.get(i).get(j);
                if (o instanceof Integer) {
                    row.getCell(j).setCellValue((Integer) o);
                } else if (o instanceof Float) {
                    row.getCell(j).setCellValue((Float) o);
                } else if (o instanceof Double) {
                    row.getCell(j).setCellValue((Double) o);
                } else if (o instanceof Long) {
                    row.getCell(j).setCellValue((Long) o);
                } else if (o instanceof Date) {
                    Date date = (Date) o;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    row.getCell(j).setCellValue(sdf.format(date));
                } else if (o == null) {
                    row.getCell(j).setCellValue("");
                } else {
                    row.getCell(j).setCellValue(o.toString());
                }
            }
        }

        // 表脚部分
        int row_index = row_num + 1;
        if (tableData != null) {
            row_index += tableData.size();
        }

        if (footData != null) {
            int footDataSize = footData.size();// 页脚个数

            row = sheet.createRow(row_index);
            for (int i = 0; i < footDataSize; i++) {
                int cell_start = i * (cellLenght / footDataSize);
                int cell_end = (i + 1) * (cellLenght / footDataSize) - 1;
                if (i == footDataSize - 1) {
                    cell_end = cellLenght - 1;
                }
                sheet.addMergedRegion(new CellRangeAddress(row_index, row_index, (short) cell_start, (short) cell_end));
                cell = row.createCell(cell_start);
                cell.setCellValue((String) footData.get(i));
            }
        }
    }

     
    
    //============================== 表格样式 ===================================//
    
    /**
     * 
     * @Title: STYLE1 无边框，水平垂直居中
     * @param hwb
     * @return 
     * HSSFCellStyle 
     * @author adam
     * @since 2015-9-29 V 1.0
     */
    public static XSSFCellStyle STYLE1(XSSFWorkbook hwb){ // 修改: HSSFWorkbook -> XSSFWorkbook
        XSSFCellStyle style = hwb.createCellStyle(); // 修改: HSSFCellStyle -> XSSFCellStyle
        style.setAlignment(HorizontalAlignment.CENTER );//水平居中  
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中  
        return style;
    }
    
    /**
     * 
     * @Title: STYLE1 无边框，水平垂直居中,字体大小
     * @param hwb
     * @param fontHeight
     * @return 
     * HSSFCellStyle 
     * @author adam
     * @since 2015-9-29 V 1.0
     */
    public static XSSFCellStyle STYLE1(XSSFWorkbook hwb,int fontHeight){ // 修改: HSSFWorkbook -> XSSFWorkbook
        XSSFCellStyle style=ExcelUtil.STYLE1(hwb); // 修改: HSSFCellStyle -> XSSFCellStyle
        
        //生成一个字体
        XSSFFont font=hwb.createFont(); // 修改: HSSFFont -> XSSFFont
        font.setColor(IndexedColors.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
        font.setFontHeightInPoints((short)fontHeight);
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
        //把字体应用到当前的样式
        style.setFont(font);
        return style;
    }
    
    /**
     * 
     * @Title: STYLE2 黑色边框，水平垂直居中
     * @param hwb
     * @return 
     * HSSFCellStyle 
     * @author adam
     * @since 2015-9-29 V 1.0
     */
    public static XSSFCellStyle STYLE2(XSSFWorkbook hwb){ // 修改: HSSFWorkbook -> XSSFWorkbook
        XSSFCellStyle style=ExcelUtil.STYLE1(hwb); // 修改: HSSFCellStyle -> XSSFCellStyle
        style.setBorderBottom(BorderStyle.valueOf((short)1));  
        style.setBorderLeft(BorderStyle.valueOf((short)1));  
        style.setBorderRight(BorderStyle.valueOf((short)1));  
        style.setBorderTop(BorderStyle.valueOf((short)1));  
        style.setBottomBorderColor(IndexedColors.BLACK.index); //边框颜色
        return style;
    }    
    
    /**
     * 
     * @Title: STYLE2  黑色边框，水平垂直居中
     * @param hwb
     * @param fontHeight
     * @return 
     * HSSFCellStyle 
     * @author adam
     * @since 2015-9-29 V 1.0
     */
    public static XSSFCellStyle STYLE2(XSSFWorkbook hwb,int fontHeight){ // 修改: HSSFWorkbook -> XSSFWorkbook
        XSSFCellStyle style=ExcelUtil.STYLE2(hwb); // 修改: HSSFCellStyle -> XSSFCellStyle
        
        //生成一个字体
        XSSFFont font=hwb.createFont(); // 修改: HSSFFont -> XSSFFont
        font.setColor(IndexedColors.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
        font.setFontHeightInPoints((short)fontHeight);
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
        //把字体应用到当前的样式
        style.setFont(font);
        return style;
    }

    /**
     * @Title: 无边框，水平垂直居中，行高自定义
     * @param hwb
     * @param fontHeight 默认为单元格行高:-1
     * @param fontStyle 字体样式 0默认正常字体
     * @return
     * HSSFCellStyle
     * @author zhanghj
     * @since 2016-11-10 V 1.0
     */
    public static XSSFCellStyle STYLE3(XSSFWorkbook hwb, int fontHeight, short fontStyle){ // 修改: HSSFWorkbook -> XSSFWorkbook
        XSSFCellStyle style=ExcelUtil.STYLE1(hwb); // 修改: HSSFCellStyle -> XSSFCellStyle

        //生成一个字体
        XSSFFont font=hwb.createFont(); // 修改: HSSFFont -> XSSFFont
        font.setColor(IndexedColors.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
        if (-1 != fontHeight) {
            font.setFontHeightInPoints((short)fontHeight);
        }
        if (0 != fontStyle) {
        	font.setBold(true);
//            font.setBoldweight(fontStyle);         //字体增粗
        }
        //把字体应用到当前的样式
        style.setFont(font);
        return style;
    }

    /**
     * @Title: 黑色边框，水平垂直居中，行高自定义
     * @param hwb
     * @param fontHeight 默认为单元格行高:-1
     * @param fontStyle 字体样式 0默认正常字体
     * @param borderWidth 资格边框宽度
     * @param alignment 对齐方式
     * @return
     * HSSFCellStyle
     * @author zhanghj
     * @since 2016-11-10 V 1.0
     */
    public static XSSFCellStyle allBoderStyle(XSSFWorkbook hwb, int fontHeight, short fontStyle, int[] borderWidth,HorizontalAlignment alignment){ // 修改: HSSFWorkbook -> XSSFWorkbook
        XSSFCellStyle style=ExcelUtil.STYLE3(hwb, fontHeight, fontStyle); // 修改: HSSFCellStyle -> XSSFCellStyle
        style.setBorderTop(BorderStyle.valueOf((short)borderWidth[0]));  
        style.setBorderRight(BorderStyle.valueOf((short)borderWidth[1]));  
        style.setBorderBottom(BorderStyle.valueOf((short)borderWidth[2]));  
        style.setBorderLeft(BorderStyle.valueOf((short)borderWidth[3]));  
        style.setAlignment(alignment);
        return style;
    }

    /**
     * 
     * @Title: toString 
     * @param o
     * @return 
     * String 
     * @author adam
     * @since 2015-9-30 V 1.0
     */
    public static String toString(Object o){
        if(o instanceof Date){
            SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");
            return o==null?"":sdf.format(o);
        }
        return o==null?"":o.toString();
    }

    /** 
     * @Title: exportAll 
     * @param mc 仓库代号
     * @param rq 查询日期
     * @param topData 表头
     * @param tableCaption  表格标题头
     * @param arrlist 数据
     * @param footData 表格脚
     * void 
     * @author lsp
     * @since 2015-12-1 V 1.0
     */
    @SuppressWarnings({ "rawtypes" })
    public static void exportAll( String mc,String rq, ArrayList topData,
            String[] tableCaption, List<ArrayList<ArrayList>> arrlist,
            ArrayList footData) {

        HttpServletResponse response  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            XSSFWorkbook hwb = new XSSFWorkbook();
            for(int sj = 0 ; sj < arrlist.size() ; sj++){
                ArrayList<ArrayList> tableData = arrlist.get(sj);
                String title =mc.split(",")[sj]+"库存报表"+rq;
                XSSFSheet sheet = hwb.createSheet(title);
                
                int cellLenght=tableCaption.length;//列宽
                int row_num=0;
                //添加空白头
                sheet.addMergedRegion(new CellRangeAddress(row_num, row_num,(short) 0, (short)(cellLenght-1)));   
                XSSFRow row = sheet.createRow(0);
                row.setHeight((short) 600);
                row_num++;
                
                //标题
                sheet.addMergedRegion(new CellRangeAddress(row_num,row_num, (short) 0, (short)(cellLenght-1)));   
                row = sheet.createRow(row_num);
                XSSFCell cell =row.createCell(0);
                cell.setCellValue(title); // 跨单元格显示的数据   
                cell.setCellStyle(ExcelUtil.STYLE1(hwb,20));   
                row_num++;
                
                //表头部分
                int index=row_num;//excel已经生成的行数
                if(topData!=null){
                    for(int i=0;i<topData.size();i++){//循环生成
                        if(i%2==0){
                            int index_i=index+i/2;
                            sheet.addMergedRegion(new CellRangeAddress(index_i, index_i,(short) 0, (short)(cellLenght/2-1)));   
                            sheet.addMergedRegion(new CellRangeAddress(index_i, index_i, (short)(cellLenght/2),(short)(cellLenght-1)));   
                            row = sheet.createRow(index_i);
                            cell =row.createCell(0);
                            cell.setCellValue(topData.get(i).toString()); 
                            cell =row.createCell((short)(cellLenght/2));
                            if(i+1<topData.size()){
                                cell.setCellValue(topData.get(i+1).toString()); 
                            }
                            row_num++;
                        }
                    }
                }
                
                
                //表格栏目
                row = sheet.createRow(row_num);
                for(int i = 0;i<cellLenght;i++){
                    cell = row.createCell(i);
                    cell.setCellValue(tableCaption[i]);
                    cell.setCellStyle(ExcelUtil.STYLE2(hwb));
                    sheet.setColumnWidth((short)i, tableCaption[i].getBytes().length*256*5/3);
                    //sheet.autoSizeColumn(cellname[i].length()*2*256);
                }
                row_num++;
                
                //遍历生成表格数据
                CellStyle cellStyle = ExcelUtil.STYLE2(hwb);
                for(int i = 0;i<tableData.size();i++){
                    row = sheet.createRow(i+row_num);
                    for(int j=0;j<(tableData.get(i)).size();j++){
                        row.createCell(j).setCellStyle(cellStyle);
                        Object o=tableData.get(i).get(j);
                        if (o instanceof Integer) {  
                            row.getCell(j).setCellValue((Integer)o);
                        } else if (o instanceof Float) {  
                            row.getCell(j).setCellValue((Float)o);
                        } else if (o instanceof Double) {  
                            row.getCell(j).setCellValue((Double)o);
                        } else if (o instanceof Long) {  
                             row.getCell(j).setCellValue((Long)o);
                        } else if(o instanceof Date) {
                            Date date = (Date) o;  
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                            row.getCell(j).setCellValue(sdf.format(date));  
                        } else if(o==null){
                            row.getCell(j).setCellValue("");
                        } else {
                            row.getCell(j).setCellValue(o.toString());
                        }
                    }
                }
        
                
                //表脚部分
                int row_index=row_num+1;
                if(tableData!=null){
                    row_index +=tableData.size();
                }
        
                if(footData!=null){
                    int footDataSize=footData.size();//页脚个数
                    
                    row = sheet.createRow(row_index);
                    for(int i=0;i<footDataSize;i++){
                        int cell_start= i*(cellLenght/footDataSize);
                        int cell_end=(i+1)*(cellLenght/footDataSize)-1;
                        if(i==footDataSize-1){
                            cell_end=cellLenght-1;
                        }
                        sheet.addMergedRegion(new CellRangeAddress(row_index, row_index,(short)cell_start,(short)cell_end)); 
                        cell =row.createCell(cell_start);
                        cell.setCellValue((String)footData.get(i)); 
                    }

                }
//                response.setContentType("application/x-msdownload");
                
            }
            String bt = "库存报表";
            response = getResponse(response, request, bt);
            OutputStream ouputStream = response.getOutputStream();  
            
            hwb.write(ouputStream);  
            ouputStream.flush();  
            ouputStream.close();
               
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public enum SheetType {
        Single,
        More
    }

    public static void exportMore(String fileName, SheetType sheetType, String[] sheetTitles, String[] titles, ArrayList<ArrayList<Object>>[] tableData, 
            ArrayList<String[]> tableCaption, int[] rows, ArrayList<String> footData,int[]columnWidth) {
        HttpServletResponse response  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            XSSFWorkbook hwb = new XSSFWorkbook();
            XSSFCellStyle[] titlesCellStyle = new XSSFCellStyle[6];
            int[] allBorderWidth = {1,1,1,1};
            int[] topBorderWidth = {1,0,0,0};
            titlesCellStyle[0] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.CENTER);
            int[] bottomBorderWidth = {0,0,1,0};
            titlesCellStyle[1] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.CENTER);
           
            titlesCellStyle[2] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.CENTER);
            titlesCellStyle[3] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.CENTER);
            titlesCellStyle[4] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.CENTER);
            titlesCellStyle[5] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.CENTER);
            XSSFCellStyle[] bodyCellStyle = new XSSFCellStyle[6];
            bodyCellStyle[0] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.LEFT);
            	//	STYLE3(hwb, -1, (short)0);
            bodyCellStyle[1] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.LEFT);
            bodyCellStyle[2] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.LEFT);
            bodyCellStyle[3] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.LEFT);
            bodyCellStyle[4] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.LEFT);
            bodyCellStyle[5] = allBoderStyle(hwb, -1, (short)0, allBorderWidth,HorizontalAlignment.LEFT);
            // 循环遍历
            exportMoreBase(sheetType, sheetTitles, titles, tableData, tableCaption, rows, footData, hwb, titlesCellStyle, bodyCellStyle,columnWidth);
            response = getResponse(response, request, fileName);
            OutputStream ouputStream = response.getOutputStream();  
            
            hwb.write(ouputStream);  
            ouputStream.flush();  
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: 多信息导出
     * @param sheetType 导出类型
     * @param sheetTitles sheet页标题
     * @param titles 标题
     * @param tableData 数据
     * @param tableCaption 数据主题
     * @param rows
     * @param hwb
     * void
     * @author zhanghj
     * @since 2016-11-12 V 1.0
     */
    public static void exportMoreBase(SheetType sheetType, String[] sheetTitles, String[] titles, ArrayList<ArrayList<Object>>[] tableData, 
            ArrayList<String[]> tableCaption, int[] rows, ArrayList<String> footData, XSSFWorkbook hwb, XSSFCellStyle[] titlesCellStyle, XSSFCellStyle[] bodyCellStyle
            ,int[]columnWidth) {
        int curRow = 0;
        XSSFRow row = null;
        XSSFCell cell = null;
        // 定义sheet页的名称
        XSSFSheet sheet = hwb.createSheet(sheetTitles[0]);
        switch (sheetType) {
        case Single:
            if (0 == sheetTitles.length) {
                
            }
            // 判断每一个列表是否间隔
            int r = 0;
            if (null == rows || 0 == rows.length) {
                rows = new int[1];
                rows[0] = r;
            }
            for (int i = 0; i < rows.length; i++) {
                int cellLenght = tableCaption.get(i).length;// 列宽
                // 获取每一个列表的开始行
                curRow += rows[i];
                if (!StringUtils.isBlank(titles[i])) {
                    // 标题(合并单元格)
                    sheet.addMergedRegion(new CellRangeAddress(curRow, curRow, (short) 0, (short) (cellLenght - 1)));
                    row = sheet.createRow(curRow);
                    row.setHeight((short) 300);
                    cell = row.createCell(0);
                    cell.setCellValue(titles[i]); // 跨单元格显示的数据
                    cell.setCellStyle(STYLE1(hwb));
                    curRow++;
                }

                // 表头部分
                /*int index = curRow;// excel已经生成的行数
                if (titles != null) {
                    for (int ii = 0; ii < titles.length; ii++) {// 循环生成
                        if (ii % 2 == 0) {
                            int index_i = index + ii / 2;
                            sheet.addMergedRegion(new CellRangeAddress(index_i, (short) 0, index_i, (short) (cellLenght / 2 - 1)));
                            sheet.addMergedRegion(new CellRangeAddress(index_i, (short) (cellLenght / 2), index_i, (short) (cellLenght - 1)));
                            row = sheet.createRow(index_i);
                            cell = row.createCell(0);
                            cell.setCellValue(topData.get(ii).toString());
                            cell = row.createCell((short) (cellLenght / 2));
                            if (i + 1 < topData.size()) {
                                cell.setCellValue(topData.get(ii + 1).toString());
                            }
                            row_num++;
                        }
                    }
                }*/
                // 标题栏目样式
                row = sheet.createRow(curRow);
                for (int iii = 0; iii < cellLenght; iii++) {
                    cell = row.createCell(iii);
                    cell.setCellValue(tableCaption.get(i)[iii]);
                    cell.setCellStyle(titlesCellStyle[i]);
                    sheet.setColumnWidth((short) iii, tableCaption.get(i)[iii].getBytes().length * 256 * 5 / 3);
                    // sheet.autoSizeColumn(cellname[i].length()*2*256);
                }
                curRow++;

                // 遍历生成表格数据
                for (int j = 0; j < tableData[i].size(); j++) {
                    row = sheet.createRow(j + curRow);
                    for (int k = 0; k < (tableData[i].get(j)).size(); k++) {
                        row.createCell(k).setCellStyle(bodyCellStyle[i]);
                        Object o = tableData[i].get(j).get(k);
                        if (o instanceof Integer) {
                            row.getCell(k).setCellValue((Integer) o);
                        } else if (o instanceof Float) {
                            row.getCell(k).setCellValue((Float) o);
                        } else if (o instanceof Double) {
                            row.getCell(k).setCellValue((Double) o);
                        } else if (o instanceof Long) {
                            row.getCell(k).setCellValue((Long) o);
                        } else if (o instanceof Date) {
                            Date date = (Date) o;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            row.getCell(k).setCellValue(sdf.format(date));
                        } else if (o == null) {
                            row.getCell(k).setCellValue("");
                        } else {
                            row.getCell(k).setCellValue(o.toString());
                        }
                    }
                }
                curRow += tableData[i].size();
            }
            break;

        case More:
            break;
        default:
            break;
        }

        if(footData!=null){
            int cellLength = titles[0].length();
            int footDataSize=footData.size();//页脚个数
            row = sheet.createRow(curRow+1);
            for(int i=0;i<footDataSize;i++){
                int cell_start= i*(cellLength/footDataSize);
                int cell_end=(i+1)*(cellLength/footDataSize)-1;
                if(i==footDataSize-1){
                    cell_end=cellLength-1;
                }
                sheet.addMergedRegion(new CellRangeAddress(curRow, curRow,(short)cell_start,(short)cell_end)); 
                cell =row.createCell(cell_start);
                cell.setCellValue((String)footData.get(i)); 
            }

        }
        
        
        if(columnWidth!=null){
        	for(int i=0;i<columnWidth.length;i++){
        		sheet.setColumnWidth((short) i,columnWidth[i]);
        	}
        }
    }

    /**
     * @Title: 读取Excel文件内容 
     * @param fromLine 起始行数（从1开始）
     * @return
     * @throws Exception 
     * List<String[]> 
     * @author zhanghj
     * @since 2016-8-30 V 1.0
     */
    public static List<String[]> readData(int fromLine, int toLine, String endSuffix, File uploafFile) throws Exception {
        // 创建对Excel工作簿文件的引用
        // 项目下面的路径
        // 根据文件后缀判断是xls还是xlsx
        Workbook wookbook = createWorkBook(endSuffix, new FileInputStream(uploafFile));
        if (null == wookbook) {
            throw new Exception("导入文件必须是以xls或者xlsx结尾");
        }

        // 在Excel文档中，第一张工作表的缺省索引是0
        Sheet sheet = wookbook.getSheet("Sheet1");
        if(sheet==null){
            throw new Exception("Excel上传内容页名称必须为Sheet1！");
        }
        // 获取到Excel文件中的所有行数（物理行数，不包括空行、隔行）
        int rows = sheet.getPhysicalNumberOfRows();
        // 得到起始行
        int start = fromLine-1;
        int end = rows-1;// 结束行比excel行数少一行，修改的话，改成 toLine 参数
        // 判断起始行与结束行
        if (start > end) {
            throw new Exception("起始行数不能大于结束行数！");
        }
        List<String[]> list = new ArrayList<String[]>();
        if (end <= rows-1) {
            // 循环遍历
            for (int i = start; i <= end; i++) {
                // 读取左上端单元格
                Row row = sheet.getRow(i);
                // 行不为空
                if (row != null) {
                    // 获取到Excel文件中的所有的列（比最后一列列标大1）
                    int cells = row.getLastCellNum();
                    if(cells==-1){
                        throw new Exception("模板错误，请删除多余的空行！");
                    }
                    String value = "";
                    // 遍历列
                    String[] values = new String[cells];
                    for (int j = 0; j < cells; j++) {
                        // 获取到列的值
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            // 判断单元格样式
                            switch (cell.getCellType()) {
                            case FORMULA:// 公式
								try {
									value = new DecimalFormat("0.####").format(cell.getNumericCellValue());
								} catch (Exception e) {
									e.printStackTrace();
									throw e;
								}
                                break;
                            case NUMERIC:// 数值类型
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    if (date != null) {
                                        value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                    } else {
                                        value = "";
                                    }
                                } else {
                                    value = new DecimalFormat("0.####").format(cell.getNumericCellValue());
                                }
                                break;
                            case STRING:// 字符串类型
                                value = cell.getStringCellValue();
                                break;
                            default:
                                value = "";
                                break;
                            }
                        } else {
                            value = "";
                        }
                        // 把列放入数组
                        values[j] = value;
                    }

                    // 把行数据放入list
                    list.add(values);
                }
            }
        }

        return list;
    }

    /**
     * @Title: 判断是xls文件还是xlsx文件 
     * @param endSuffix 文件后缀
     * @param is
     * @return
     * @throws IOException 
     * Workbook 
     * @author zhanghj
     * @since 2016-8-29 V 1.0
     */
    private static Workbook createWorkBook(String endSuffix, InputStream is) throws IOException {
        //需要在盖方法中判断是xls文件还是xlsx文件
        if (endSuffix.toLowerCase().endsWith("xls")) {
            return new HSSFWorkbook(is);
        }
        if (endSuffix.toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(is);
        }

        return null;
    }

    /**
     * @Title: 文件头设置
     * @param response
     * @param request
     * @param title
     * @return
     * @throws UnsupportedEncodingException
     * HttpServletResponse
     * @author zhanghj
     * @since 2016-11-19 V 1.0
     */
    public static HttpServletResponse getResponse(HttpServletResponse response,
            HttpServletRequest request, String title)
            throws UnsupportedEncodingException {
//        response.setContentType("application/x-msdownload");
        response.setContentType("application/vnd.ms-excel; charset=utf-8");
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
        response.setHeader("Content-Disposition", "attachment;filename="+title+".xls");

        return response;
    }
    
    @SuppressWarnings({ "rawtypes" })
    public static void export(String title,ArrayList topData,String tableCaption,ArrayList<ArrayList> tableData,ArrayList footData){
        HttpServletResponse response  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            XSSFWorkbook hwb = new XSSFWorkbook();
            XSSFSheet sheet = hwb.createSheet(title);
            
            int cellLenght=17;//列宽
            int row_num=0;
            //添加空白头
//            sheet.addMergedRegion(new CellRangeAddress(row_num, (short) 0, row_num,(short)(cellLenght-1)));   
            XSSFRow row = sheet.createRow(0);
//            row_num++;
            
            //标题
            sheet.addMergedRegion(new CellRangeAddress(row_num,row_num, (short) 0, (short)(cellLenght-1)));   
            row = sheet.createRow(row_num);
            row.setHeight((short) 600);
            XSSFCell cell =row.createCell(0);
            cell.setCellValue(title); // 跨单元格显示的数据   
            cell.setCellStyle(ExcelUtil.STYLE1(hwb,20));   
            row_num++;
            
            //表头部分
            int index=row_num;//excel已经生成的行数
            if(topData!=null){
                for(int i=0;i<topData.size();i++){//循环生成
                    if(i%2==0){
                        int index_i=index+i/2;
                        sheet.addMergedRegion(new CellRangeAddress(index_i, index_i,(short) 0, (short)(cellLenght/2-1)));   
                        sheet.addMergedRegion(new CellRangeAddress(index_i, index_i,(short)(cellLenght/2), (short)(cellLenght-1)));   
                        row = sheet.createRow(index_i);
                        cell =row.createCell(0);
                        cell.setCellValue(topData.get(i).toString()); 
                        cell =row.createCell((short)(cellLenght/2));
                        if(i+1<topData.size()){
                            cell.setCellValue(topData.get(i+1).toString()); 
                        }
                        row_num++;
                    }
                }
            }
            
            
            //表格栏目
            row = sheet.createRow(row_num);
            int row_num2=row_num+1;
            XSSFRow row2=sheet.createRow(row_num2);
            for(int i = 0;i<cellLenght;i++){
                cell = row.createCell(i);
                //cell.setCellValue(tableCaption[i]);
                cell.setCellStyle(ExcelUtil.STYLE2(hwb));
                cell = row2.createCell(i);
                cell.setCellStyle(ExcelUtil.STYLE2(hwb));
                sheet.setColumnWidth((short)i, 8*256*5/3);
                //sheet.autoSizeColumn(cellname[i].length()*2*256);
            }
            sheet.addMergedRegion(new CellRangeAddress(row_num,row_num2,(short)0,(short)0));
            row.getCell(0).setCellValue("序号");
            sheet.addMergedRegion(new CellRangeAddress(row_num,row_num2,(short)1,(short)1));
            row.getCell(1).setCellValue(tableCaption);
            sheet.addMergedRegion(new CellRangeAddress(row_num,row_num,(short)2,(short)3));
            row.getCell(2).setCellValue("上岗前体检");
            row2.getCell(2).setCellValue("体检人数");
            row2.getCell(3).setCellValue("禁忌症人数 ");
            sheet.addMergedRegion(new CellRangeAddress(row_num,row_num,(short)4,(short)7));
            row.getCell(4).setCellValue("在岗体检");
            row2.getCell(4).setCellValue("体检人数");
            row2.getCell(5).setCellValue("禁忌症人数 ");
            row2.getCell(6).setCellValue("可疑职业病人数 ");
            row2.getCell(7).setCellValue("需要观察复查人数 ");
            sheet.addMergedRegion(new CellRangeAddress(row_num,row_num,(short)8,(short)10));
            row.getCell(8).setCellValue("离岗体检");
            row2.getCell(8).setCellValue("体检人数");
            row2.getCell(9).setCellValue("可疑职业病人数 ");
            row2.getCell(10).setCellValue("需要观察复查人数 ");
            sheet.addMergedRegion(new CellRangeAddress(row_num,row_num,(short)11,(short)13));
            row.getCell(11).setCellValue("应急体检");
            row2.getCell(11).setCellValue("体检人数");
            row2.getCell(12).setCellValue("可疑职业病人数 ");
            row2.getCell(13).setCellValue("需要观察复查人数 ");
            sheet.addMergedRegion(new CellRangeAddress(row_num,row_num,(short)14,(short)16));
            row.getCell(14).setCellValue("离岗随访");
            row2.getCell(14).setCellValue("体检人数");
            row2.getCell(15).setCellValue("可疑职业病人数 ");
            row2.getCell(16).setCellValue("需要观察复查人数 ");
//            sheet.addMergedRegion(new CellRangeAddress(row_num,row_num2,(short)17,(short)17));
//            row.getCell(17).setCellValue("分中心");
            row_num=row_num+2;
            
            //遍历生成表格数据
            CellStyle cellStyle = ExcelUtil.STYLE2(hwb);
            for(int i = 0;i<tableData.size();i++){
                row = sheet.createRow(i+row_num);
                for(int j=0;j<(tableData.get(i)).size();j++){
                    row.createCell(j).setCellStyle(cellStyle);
                    Object o=tableData.get(i).get(j);
                    if (o instanceof Integer) {  
                        row.getCell(j).setCellValue((Integer)o);
                    } else if (o instanceof Float) {  
                        row.getCell(j).setCellValue((Float)o);
                    } else if (o instanceof Double) {  
                        row.getCell(j).setCellValue((Double)o);
                    } else if (o instanceof Long) {  
                         row.getCell(j).setCellValue((Long)o);
                    } else if(o instanceof Date) {
                        Date date = (Date) o;  
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                        row.getCell(j).setCellValue(sdf.format(date));  
                    } else if(o==null){
                        row.getCell(j).setCellValue("");
                    } else {
                        row.getCell(j).setCellValue(o.toString());
                    }
                }
            }
    
            
            //表脚部分
            int row_index=row_num+1;
            if(tableData!=null){
                row_index +=tableData.size();
            }
    
            if(footData!=null){
                int footDataSize=footData.size();//页脚个数
                
                row = sheet.createRow(row_index);
                for(int i=0;i<footDataSize;i++){
                    int cell_start= i*(cellLenght/footDataSize);
                    int cell_end=(i+1)*(cellLenght/footDataSize)-1;
                    if(i==footDataSize-1){
                        cell_end=cellLenght-1;
                    }
                    sheet.addMergedRegion(new CellRangeAddress(row_index, row_index,(short)cell_start,(short)cell_end)); 
                    cell =row.createCell(cell_start);
                    cell.setCellValue((String)footData.get(i)); 
                }

            }
            
            XSSFPrintSetup printSetup = sheet.getPrintSetup();//A4纸
            printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);  
            
            response = getResponse(response, request, title);
            OutputStream ouputStream = response.getOutputStream();  
            
            hwb.write(ouputStream);  
            ouputStream.flush();  
            ouputStream.close();   
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void exportLocal(String title,String[] tableCaption,ArrayList<ArrayList> tableData,String filePath){
        ZipUtils.createFile(filePath);
        try (OutputStream ouputStream = new FileOutputStream(filePath);){
            XSSFWorkbook hwb = new XSSFWorkbook(); // 修改: HSSFWorkbook -> XSSFWorkbook
            XSSFSheet sheet = hwb.createSheet(title); // 修改: HSSFSheet -> XSSFSheet

            int cellLenght=tableCaption.length;//列宽
            int row_num=0;
            XSSFRow row = sheet.createRow(0); // 修改: HSSFRow -> XSSFRow

            //标题
            sheet.addMergedRegion(new CellRangeAddress(row_num, row_num, (short) 0,(short)(cellLenght-1)));
            row = sheet.createRow(row_num);
            row.setHeight((short) 600);
            XSSFCell cell =row.createCell(0); // 修改: HSSFCell -> XSSFCell
            cell.setCellValue(title); // 跨单元格显示的数据
            cell.setCellStyle(ExcelUtil.STYLE1(hwb,20));
            row_num++;


            //表格栏目
            row = sheet.createRow(row_num);
            for(int i = 0;i<cellLenght;i++){
                cell = row.createCell(i);
                cell.setCellValue(tableCaption[i]);
                cell.setCellStyle(ExcelUtil.STYLE2(hwb));
                sheet.setColumnWidth((short)i, tableCaption[i].getBytes().length*256*5/3);
            }
            row_num++;

            //遍历生成表格数据(放入数字类型，导出后就可以正常求和)
            CellStyle cellStyle = ExcelUtil.STYLE2(hwb);
            for(int i = 0;i<tableData.size();i++){
                row = sheet.createRow(i+row_num);
                for(int j=0;j<(tableData.get(i)).size();j++){
                    row.createCell(j).setCellStyle(cellStyle);
                    Object o=tableData.get(i).get(j);
                    if (o instanceof Integer) {
                        row.getCell(j).setCellValue((Integer)o);
                    } else if (o instanceof Float) {
                        row.getCell(j).setCellValue((Float)o);
                    } else if (o instanceof Double) {
                        row.getCell(j).setCellValue((Double)o);
                    } else if (o instanceof BigDecimal) {
                        row.getCell(j).setCellValue(((BigDecimal)o).doubleValue());
                    } else if (o instanceof Long) {
                        row.getCell(j).setCellValue((Long)o);
                    } else if(o instanceof Date) {
                        Date date = (Date) o;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        row.getCell(j).setCellValue(sdf.format(date));
                    } else if(o==null){
                        row.getCell(j).setCellValue("");
                    } else {
                        row.getCell(j).setCellValue(o.toString());
                    }
                }
            }

            XSSFPrintSetup printSetup = sheet.getPrintSetup();//A4纸  // 修改: HSSFPrintSetup -> XSSFPrintSetup
            printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);  // 修改: HSSFPrintSetup.A4_PAPERSIZE -> PrintSetup.A4_PAPERSIZE

            hwb.write(ouputStream);
            ouputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
