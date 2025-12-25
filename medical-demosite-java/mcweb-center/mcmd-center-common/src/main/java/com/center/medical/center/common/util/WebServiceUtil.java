/**
 * @WebServiceUtil.java
 * @cn.com.adicon
 * @Description：
 * 
 * @author xuhp 
 * @copyright  2017
 * @version V
 * @since 2017-1-13
 */
package com.center.medical.center.common.util;

import com.center.medical.center.common.bean.model.AdiconWebServiceSoap;
import com.center.medical.center.common.service.AdiconWebService;
import com.center.medical.common.utils.redis.RedisUtil;

/** 
 * @ClassName: WebServiceUtil 
 * @Description: 调用艾迪康webservice接口
 * @author xuhp
 * @date 2017-1-13 上午8:53:31 
 *  
 */
//医院条码：体检号
public class WebServiceUtil {

	public static String NODATA_MESSAGE="{\"message\":\"操作失败，无数据可查\"}";

	public String getKey(String loginid,String password) {
		//如果缓存有token直接拿
		if (RedisUtil.hasKey("adicon_key")) {
			return (String) RedisUtil.get("adicon_key");
		}
		//没有去取
		try {
			AdiconWebService test=new AdiconWebService();
			AdiconWebServiceSoap soap=test.getAdiconWebServiceSoap();
			System.out.println("loginid:"+loginid);
			System.out.println("password:"+password);
			/*获取有效授权码*/
			String key=soap.login(loginid, password);
			System.out.println("艾迪康登陆获取key:"+key);
			//有效期设置为1天
			RedisUtil.set("adicon_key", key, 24 * 60 * 60);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (String) RedisUtil.get("adicon_key");
	}


	/**
	 * 根据医院条码获取体检结果，可能会有以前的结果，需要按时间取最新的
	 * @Title: getJSONReportItemListByCustomerBarocde 
	 * @param patientcode
	 * @return 
	 * String 
	 * @author xuhp
	 * @since 2019年2月25日 V 1.0
	 */
	public String getJSONReportItemListByCustomerBarocde(String patientcode,String loginid,String password){
		try {
			String key = getKey(loginid,password);
			System.out.println("key:"+key);
			AdiconWebService test=new AdiconWebService();
			AdiconWebServiceSoap soap=test.getAdiconWebServiceSoap();
			String report_list=soap.getJSONReportItemListByCustomerBarocde(patientcode, key);
			System.out.println("report_list:"+report_list);
			return report_list;
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}




	public byte[] getByteReport(String id,String loginid,String password){
		try {
			String key = getKey(loginid,password);
			System.out.println("key:"+key);
			AdiconWebService test = new AdiconWebService();
			AdiconWebServiceSoap soap = test.getAdiconWebServiceSoap();
			byte[] report = soap.getByteReport(id, key);
			return report;
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}




	public byte[] getByteJpegReportById(String id,String loginid,String password){
		try {
			String key = getKey(loginid,password);
			System.out.println("key:"+key);
			AdiconWebService test = new AdiconWebService();
			AdiconWebServiceSoap soap = test.getAdiconWebServiceSoap();
			byte[] report = soap.GetByteJpegReportById(id, key);
			return report;
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void test(){
//		System.out.println(getJSONReportItemListByCustomerBarocde("30003150"));
//		AdiconWebService test=new AdiconWebService();
//		AdiconWebServiceSoap soap=test.getAdiconWebServiceSoap();
		/*获取有效授权码*/
//		String key=soap.login("421070", "zkgj8210");
//		System.out.println(key);
		/*获取可下载标本列表
		 * 时间格式  YYYY-MM-DD
		 * Key＝有效授权码，BeginDateTime＝起始时间， EndDateTime＝结束时间，TypeDateTime＝1=按采集时间统计，2＝按报告时间统计；AgainFlag:1=重新下载已下载过的标本，0＝只下载未下载的标本
		 */
		//String report_list=soap.getReportList(key, "2016-10-13", "2016-10-14", "2", "1");
//		String report_list=soap.getJSONReportItemListByCustomerBarocde("00904755", key+"d");
//		System.out.println(report_list);
	}
	public static void main(String args[]){
		test();
	}
}