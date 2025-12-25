package com.center.medical.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.center.medical.common.constant.FileTypePath;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * easypoi导出
 *导出以文本方式存储的数值 改成  数值,如体检结账单：list<map<string,string>>fields改成list<map<string,double>>试试
 *实测不行，只能改回老方式导出
 */
public class EasyPoiUtil {
	public static String FINANCE_COUNT_TEMPLATE="财务收费统计.xls";
	public static String FINANCE_COUNT_TOTAL_TEMPLATE="财务收费汇总.xls";
	public static String PERSONAL_TOTAL_TEMPLATE="个检销售统计.xls";
	public static String PERSONAL_TOTAL_INFO_TEMPLATE="个检销售统计明细.xls";
	public static String SELL_GROUP_TEMPLATE="销售团检统计.xls";
	public static String ASSOCIATE_TEMPLATE="报告交接统计.xls";
	public static String GUIDANCE_LIST_HEALTH="应急导引单健康模板.xls";
	public static String GUIDANCE_LIST_DISEASE="应急导引单职业模板.xls";
	
	/**
	 * 浏览器直接下载excel
	 * @param templatePath
	 * @param param
	 * @param fields
	 * @param title
	 * @throws Exception
	 */
	public static void exportTemplate(String templatePath,Map<String,Object>param
			,List<Map<String,String>> fields,String title) throws Exception {
		HttpServletResponse response  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		HttpServletRequest request= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        TemplateExportParams params = new TemplateExportParams(
				FileTypePath.REAL_PATH
        		+"/template/file/excel/export/"
        		+templatePath
                );
        param.put("maplist", fields);
        Workbook workbook = ExcelExportUtil.exportExcel(params, param);
        response = ExcelUtil.getResponse(response, request, title);
        try(OutputStream ouputStream = response.getOutputStream();  ){
        	workbook.write(ouputStream);  
            ouputStream.flush();  
            ouputStream.close();  
        }
    }
	
	/**
	 * 导出excel到服务器
	 * @param templatePath
	 * @param param
	 * @param fields
	 * @param title
	 * @throws Exception
	 */
	public static Workbook exportTemplateLocal(String templatePath,Map<String,Object>param
			,List<Map<String,String>> fields,String title,String realPath
			,String timeStamp) throws Exception {
        TemplateExportParams params = new TemplateExportParams(
        		FileTypePath.REAL_PATH
        		+templatePath
                );
        param.put("maplist", fields);
		Workbook workbook = ExcelExportUtil.exportExcel(params, param);
		// TODO: 未完成本地生成及上传到阿里云
		return workbook;
    }
	
	public static String getString(Object obj) {
		return obj==null?"":obj.toString();
	}
}
