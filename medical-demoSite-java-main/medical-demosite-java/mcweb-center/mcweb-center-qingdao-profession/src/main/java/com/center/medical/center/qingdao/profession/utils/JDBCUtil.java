/**
 * @JDBCUtil.java
 * @com.lingnet.util
 * @Description：
 * 
 * @author xuhp 
 * @copyright  2018
 * @version V
 * @since 2018年5月10日
 */
package com.center.medical.center.qingdao.profession.utils;

import com.center.medical.center.qingdao.profession.entity.properties.QjkProperties;

import javax.persistence.Column;
import javax.persistence.Table;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** 
 * @ClassName: JDBCUtil 
 * @Description: jdbc 
 * @author xuhp
 * @date 2018年5月10日 上午8:51:05 
 *  
 */

public class JDBCUtil {
	/**
	 * 获取连接
	 * @Title: getConnection
	 * @return
	 * @throws Exception 
	 * Connection 
	 * @author xuhp
	 * @since 2018年5月10日 V 1.0
	 */
	public static Connection getConnection(QjkProperties qjkProperties) throws Exception{

		String driver = qjkProperties.getDriverClassName();
		String url = qjkProperties.getUrl();
		String configname = qjkProperties.getUsername();
		String password = qjkProperties.getPassword();
		Class.forName(driver);//注册驱动
		Connection connection = DriverManager.getConnection(url, configname, password);
		return connection;
	}
	
	/**
	 * 执行语句
	 * @Title: excute 
	 * @param connection
	 * @param sql
	 * @param args 
	 * void 
	 * @author xuhp
	 * @since 2018年5月10日 V 1.0
	 */
	public static void excute(Connection connection,String sql, Object... args)throws Exception {
		//这句直接报错
//		System.out.println("isValid:"+connection.isValid(0)+"=======isClosed:"+connection.isClosed()+"=======");
		try(PreparedStatement preparedStatement =connection.prepareStatement(sql);) {
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			preparedStatement.executeUpdate();
		} 
	}
	/**
	 * 查询单个值
	 * @Title: getForValue 
	 * @param connection
	 * @param sql
	 * @param args
	 * @return 
	 * E 
	 * @author xuhp
	 * @since 2018年5月10日 V 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <E> E getForValue(Connection connection,String sql, Object... args)throws Exception {
//	jar包重复	System.out.println("isValid:"+connection.isValid(0)+"=======isClosed:"+connection.isClosed()+"=======");
		// 得到结果集: 该结果集应该只有一行, 且只有一列
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			try(ResultSet resultSet = preparedStatement.executeQuery();){
				if(resultSet.next()){
					return (E) resultSet.getObject(1);
				}
			}
		} 
		return null;
	}
	
	public static List<List<Object>> getList(Connection connection,String sql, Object... args) throws SQLException{
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			try(ResultSet resultSet = preparedStatement.executeQuery();){
				List<List<Object>> result=new ArrayList<List<Object>>();
				ResultSetMetaData md = resultSet.getMetaData();
		        int columnCount = md.getColumnCount();
				while(resultSet.next()){
					List<Object> list=new ArrayList<Object>();
					for(int i=1;i<=columnCount;i++){
						list.add(resultSet.getObject(i));
					}
					result.add(list);
				}
				return result;
			}
		}
	}
	public static List<Object[]> getArray(Connection connection,String sql, Object... args) throws SQLException{
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql);){
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
			try(ResultSet resultSet = preparedStatement.executeQuery();){
				List<Object[]> result=new ArrayList<Object[]>();
				ResultSetMetaData md = resultSet.getMetaData();
		        int columnCount = md.getColumnCount();
				while(resultSet.next()){
					Object[] list=new Object[columnCount];
					for(int i=1;i<=columnCount;i++){
						list[i-1]=resultSet.getObject(i);
					}
					result.add(list);
				}
				return result;
			}
		}
	}
	/**
	 * sql插入实体类（sqlserver设置自增会自动生成主键,oracle需要设置主键default为sys_guid()）
	 * @Title: insert 
	 * @param conn
	 * @param obj
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException 
	 * void 
	 * @author xuhp
	 * @since 2018年5月10日 V 1.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void insert(Connection conn,Object obj) throws Exception{
		Class clazz=obj.getClass();
		Table tab=(Table) clazz.getAnnotation(Table.class);
		String tableName=tab.name();
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder builder=new StringBuilder();
		StringBuilder valueBuilder=new StringBuilder();
		builder.append("INSERT INTO  "+tableName+"  (");
		valueBuilder.append(" VALUES (");
		List<Object> list=new ArrayList<Object>();
		for(int i=0,l=fields.length;i<l;i++){
			Field field=fields[i];
			String fieldName=field.getName();
			if("serialVersionUID".equals(fieldName)){continue;}
			PropertyDescriptor pd=null;
			try {
				pd = new PropertyDescriptor(fieldName, clazz);
			} catch (Exception e) {
				pd = new PropertyDescriptor(fieldName, clazz,"get"+fieldName,"set"+fieldName);
			}
			Method rM = pd.getReadMethod();//获得读方法
			Column column=rM.getAnnotation(Column.class);
			if(column!=null){
				fieldName=column.name();
			}
			Object value=rM.invoke(obj);
			if(value!=null){
				builder.append(fieldName+",");
				valueBuilder.append("?,");
				list.add(value);
			}
		}
		builder.deleteCharAt(builder.length()-1);
		builder.append(") ");
		valueBuilder.deleteCharAt(valueBuilder.length()-1);
		valueBuilder.append(")");
		builder.append(valueBuilder);
		String sql= builder.toString();
		excute(conn, sql, list.toArray());
	}
	
	/**
	 * 无效的列索引：PreparedStatement参数从1开始
	 * @Title: main 
	 * @param args 
	 * void  1000000 无size ：105325    500 ：218516     10000：330153
	 * 100000  无size:44825   500:60000    1000:30000
	 * @author xuhp
	 * @since 2018年5月21日 V 1.0
	 */
	public static void main(String[] args) {
		try {
//			long s=System.currentTimeMillis();
//			Connection conn=getConnection("jy");
//
//			CallableStatement proc = conn.prepareCall(
//					"{call basic_info_add_his("
//					+  "?,?,?,?,?"
//					+ ",?,?,?,?,?"
//					+ ",?,?,?,?,?"
//					+ ",?,?,?,?,?"
//					+ ",?,?,?,?,?"
//					+ ",?,?,?)}"
//					);
//			for(int i=0;i<10;i++) {
//				proc.setObject(1, "name"+i);//N'张三’, 传入姓名
//				proc.setObject(2, "男");//N'女', 传入性别（男、女）
//				proc.setObject(3, i);//23, 传入年龄,int
//				proc.setObject(4, "岁");//N'岁', 传入年龄单位(岁、月、周、天)
//				proc.setObject(5, "彩超");//N'彩超', 传入子检查类别（根据工作站设置，见注释1,必传项目） 目前固定彩超
//				proc.setObject(6, null);// N'心脏', 传入检查部位
//				proc.setObject(7, "2011-08-15 08:01:59");//N'2011-08-15 08:01:59', 传入患者被安排的检查时间（DateTime型，格式”YYYY-mm-DD HH:mm:SS” ,必传项目）
//				proc.setObject(8, "2011-08-15 08:01:59");//N'2011-08-15', 传入执行登记的时间（DateTime型，格式”YYYY-mm-DD HH:mm:SS” ,必传项目）
//				proc.setObject(9, null);// N'lczd', 传入临床诊断（病情诊断）
//				proc.setObject(10, null);//N'13805210001', 传入患者联系方式（地址电话等）
//				proc.setObject(11, null);//N'定期复查', 传入备注信息
//				proc.setObject(12, null);// N'mzh', 传入门诊号
//				proc.setObject(13, null);//N'zyh', 传入住院号
//				proc.setObject(14, null);//N'bq', 传入病区
//				proc.setObject(15, null);//N'ch', 传入床号
//				proc.setObject(16, "待检查");//N'待检查', 传入固定值’待检查’ ,必传项目
//				proc.setObject(17, "HIS");//N'HIS', 传入固定值’HIS’ ,必传项目
//				proc.setObject(18, "01000125790"+i);//N'', 传入HIS查询号，如检查号、门诊号、住院号、申请单号等，供HIS查询检查结果。
//				proc.setObject(19, "01000125790"+i);//N'', 传入HIS查询号，如检查号、门诊号、住院号、申请单号等，供HIS查询检查结果（可以和hisno相同或不同）。
//				proc.setObject(20, "US");//N'US', 传入固定值’US’（注意大写）,必传项目
//				proc.setObject(21, null);//N'自费', 传入费别（自费、公费等）
//				proc.setObject(22, 1);//1, 传入检查类别ID（根据工作站设置，见注释2）,必传项目
//				proc.setObject(23, 34);//34, 传入子检查类别ID（根据工作站设置，见注释3）,必传项目
//				proc.setObject(24, null);//N'', 传入空值’’, 传入NULL,必传项目
//				proc.setObject(25, "超声检查");//N'超声检查', 传入固定值’超声检查’ ,必传项目
//				proc.setObject(26, null);//N'内科',传入申请科室
//				proc.setObject(27, null);//N'张',传入申请医生
//				proc.setObject(28, null);//100.50 ,传入检查费
//
//				proc.addBatch();
//			}
//			proc.executeBatch();
//			proc.close();
			/**
			System.out.println(System.currentTimeMillis()-s);
			getList(conn,
					"SELECT "+
				"    OldSpecimen.patSex, "+
				"    OldSpecimen.PatExt3, "+
			"	    OldSpecimen.ReceiveDate, "+
			"	    OldSpecimen.ReceiveTime  "+ 
			"	FROM [CliMIS].[dbo].[B_Test] B_Test "+
				"	INNER JOIN  [CliMIS].[dbo].[B_TestProfile] B_TestProfile ON ( B_TestProfile.TestIndex =  B_Test.code)   "+
				"	INNER JOIN [CliMIS].[dbo].[OldValue] OldValue ON ( OldValue.TestIndex = B_Test.code ) "+
				"	INNER JOIN [CliMIS].[dbo].[OldSpecimen] OldSpecimen ON ( OldSpecimen.NDate = OldValue.NDate AND OldSpecimen.SpecimenNo = OldValue.SpecimenNo ) "+  
				"	LEFT JOIN [CliMIS].[dbo].[operator] doc ON ( OldSpecimen.DocterIndex = doc.code ) "+
			"		LEFT JOIN [CliMIS].[dbo].[operator] review ON( OldSpecimen.ReviewerIndex = review.code ) "+
			"		LEFT JOIN [CliMIS].[dbo].[operator] pat ON ( OldSpecimen.PatExt3 = pat.code ) "+
			"	WHERE  "+
			      "  ( OldSpecimen.PatIndex IN ('1','3') ) and "+
			       " ( B_TestProfile.ProfileTestIndex ='4' )  and  "+
			       " ( B_TestProfile.TestIndex ='5' )  and  "+
			      "  ( B_Test.Profile =0 ) "+
			      "   ORDER BY OldSpecimen.ReceiveDate DESC,OldSpecimen.ReceiveTime DESC "
			        
					);
			System.out.println(System.currentTimeMillis()-s);
			*/
			
			
//			Connection conn=getConnection(null);
//			conn.setAutoCommit(false);
//			PreparedStatement ps=conn.prepareStatement("insert into advance_visit (patientcode,visit_id,id) values (?,?,?) ");
//			long s=System.currentTimeMillis();
//			for(int i=0;i<100000;i++){
//				ps.setString(1, "001");
//				ps.setString(2, null);
//				ps.setString(3, UUID.randomUUID().toString().replaceAll("-",""));
//				ps.addBatch();
//			}
//			ps.executeBatch();
//			PreparedStatement ps2=conn.prepareStatement("insert into app_preno (cid,fzx,id) values (?,?,?) ");
//			for(int i=0;i<100000;i++){
//				ps2.setString(1, "001");
//				ps2.setString(2, null);
//				ps2.setString(3, UUID.randomUUID().toString().replaceAll("-",""));
//				ps2.addBatch();
//			}
//			ps2.executeBatch();
//			conn.commit();
//			System.out.println(System.currentTimeMillis()-s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
