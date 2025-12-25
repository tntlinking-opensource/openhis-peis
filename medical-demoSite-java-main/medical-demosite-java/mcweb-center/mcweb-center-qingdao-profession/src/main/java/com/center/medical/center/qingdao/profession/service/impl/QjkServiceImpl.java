package com.center.medical.center.qingdao.profession.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.center.medical.center.qingdao.profession.entity.dto.*;
import com.center.medical.center.qingdao.profession.entity.properties.QjkProperties;
import com.center.medical.center.qingdao.profession.mapper.QjkMapper;
import com.center.medical.center.qingdao.profession.service.QjkService;
import com.center.medical.center.qingdao.profession.utils.JDBCUtil;
import com.center.medical.center.qingdao.profession.utils.Render;
import com.center.medical.center.qingdao.profession.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * QT体检者表(Peispatient)服务实现类
 *
 * @author ay
 * @since 2024-05-07 15:54:16
 */
@Service
@AllArgsConstructor
@Slf4j
public class QjkServiceImpl implements QjkService {

    private final QjkMapper qjkMapper;
    private static Map<String,String> harms=new HashMap<String, String>();//key:id  value:name

    private QjkProperties qjkProperties;



    /**
     * 上传
     * @param startDate
     * @param endDate
     */
    @Override
    public void upload(Date startDate, Date endDate) {
        log.info("开始上传区疾控!");
        //是否开启
        if ("true".equals(qjkProperties.getOpen())){
            List<HarmDto> hs = qjkMapper.getHarmList();
            for(HarmDto h:hs) {
                harms.put(h.getId(), h.getHarmName());
            }
            String rn = "\n";//换行符
            //查询出来 需要上传的体检者
            List<TjRenM> tjRenMList = qjkMapper.getTjRenM(startDate,endDate);
            log.info("需要上传的体检者有：{}"+tjRenMList.size());
            if (CollectionUtil.isNotEmpty(tjRenMList)){
                List<String> patientCodes = tjRenMList.stream()
                        .map(TjRenM::getPatientcode)
                        .collect(Collectors.toList());
                //体检项目数据
                log.info("查询体检项目数据");
                List<TjRenDData> tjRenDData = qjkMapper.getTjRenDData(patientCodes);
                List<TjRenDData> tjRenDData2 = qjkMapper.getTjRenDData2(patientCodes);
                tjRenDData.addAll(tjRenDData2);

                //科室小结数据
                log.info("查询科室小结数据");
                List<TjRenMKeshiXJ> tjRenMKeshiXJS = qjkMapper.getTjRenMKeshiXJS(patientCodes);
                //科室总结数据 需要拼接并合并到科室小结数据
                List<TjRenKeShiZj> tjRenKeShiZjs = qjkMapper.getTjRenKeShiZj(patientCodes);
                for (TjRenKeShiZj tjRenKeShiZj : tjRenKeShiZjs) {
                    TjRenMKeshiXJ tjRenMKeshiXJ = new TjRenMKeshiXJ();
                    tjRenMKeshiXJ.setPatientcode(tjRenKeShiZj.getPatientcode());
                    tjRenMKeshiXJ.setDepId(tjRenKeShiZj.getDepId());
                    tjRenMKeshiXJ.setFeeId(tjRenKeShiZj.getItemId());
                    String conclusion=null;
                    if(tjRenKeShiZj.getExamresultdesc()!=null&&tjRenKeShiZj.getExamresultsummary()!=null&&tjRenKeShiZj.getItemName()!=null) {
                        conclusion="["+tjRenKeShiZj.getItemName()+"]所见:"+rn
                                + Render.getClob(tjRenKeShiZj.getExamresultdesc())+rn
                                +"["+tjRenKeShiZj.getItemName()+"]提示:"+rn
                                +Render.getClob(tjRenKeShiZj.getExamresultsummary())+rn;
                    }
                    tjRenMKeshiXJ.setZyConclusions(conclusion);
                    tjRenMKeshiXJ.setLrr(tjRenKeShiZj.getLrr());
                    tjRenMKeshiXJ.setWriteTime(tjRenKeShiZj.getWriteTime());
                    tjRenMKeshiXJS.add(tjRenMKeshiXJ);
                }
                //获取接害信息
                log.info("查询获取接害信息");
                List<TjRenMJieHai> tjRenMJieHai = qjkMapper.getTjRenMJieHai(patientCodes);


                // TODO: wait 插入到中间库，把查询出来的数据再组装插入那8张表里
                try {
                    log.info("开始插入数据");
                    insert(tjRenMList,tjRenDData,tjRenMKeshiXJS,tjRenMJieHai,qjkProperties);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


                //插入完成后修改状态
                qjkMapper.tagPatients(patientCodes);
                log.info("上传区疾控结束!");
            }
        }




    }


    private void insert(List patients,List examList
            ,List sectionList,List zysList,QjkProperties qjkProperties) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new java.sql.Date(System.currentTimeMillis());
        String place = qjkProperties.getPlace();
        try(Connection conn= JDBCUtil.getConnection(qjkProperties);
            PreparedStatement ps=conn.prepareStatement(
                    "insert into Tj_RenM (单据编号,体检编号,姓名,性别,年龄,民族,籍贯,电话,手机,邮箱,地址,婚姻,身份证号,出生年,出生月,出生日期,社会统一信用代码"+
                            ",单位名称,单位地址,单位性质,所属行业,车间,工种,总工龄,接害工龄,接害因素,登记人,登记日期,查体类型,查体分类,体检日期,来源体检号,复查体检,主检医生"+
                            ",主检签发日期,最终结论分类,合格,状态,备注,云数据新加,定义人,定义日期,定义时间,体检点名称)values("+
                            "?,?,?,?,?,?,?,?,?,?,"+
                            "?,?,?,?,?,?,?,?,?,?,"+
                            "?,?,?,?,?,?,?,?,?,?,"+
                            "?,?,?,?,?,?,?,?,?,?,"+
                            "?,?,?,?) "
            );
            PreparedStatement ps2=conn.prepareStatement(
                    "insert into TJ_RenD_Data(体检编号,项目编号,项目名称,"+
                            "国家表名称,组合编号,组合名称,体检结果,总检单位,参考范围,"+
                            "参考上限,参考下限,默认结果,单位,体检科室编号,体检科室名称,"+
                            "正常,体检师,输入,输入时间,显示,报告序号,"+
                            "序号,定义人,定义日期)values("+
                            "?,?,?,?,?,"+
                            "?,?,?,?,?,"+
                            "?,?,?,?,?,"+
                            "?,?,?,?,?,"+
                            "?,?,?,?"+
                            ")"
            );
            PreparedStatement ps3=conn.prepareStatement(
                    "insert into TJ_RenM_JieHai ("+
                            "体检编号,接害名称,定义人,定义日期"+
                            ")values(?,?,?,?)"
            );
            PreparedStatement ps4=conn.prepareStatement(
                    "insert into TJ_RenM_KeshiXJ("+
                            "体检编号,科室编号,组合编号,"+
                            "科室小结,定义人,定义日期"+
                            ")values(?,?,?,?,?,?)"
            );
            PreparedStatement ps5=conn.prepareStatement(
                    "insert into TJ_RenM_Pic("+
                            "体检编号,照片,定义人,定义日期"+
                            ")values(?,?,?,?)"
            );
            PreparedStatement ps6=conn.prepareStatement(
                    "insert into TJ_rENm_zjjl("+
                            "体检编号,检查结论,结论分类,科室小结,"+
                            "结论分类序号,定义人,定义日期"+
                            ")values(?,?,?,?,?,?,?)"
            );
            PreparedStatement ps7=conn.prepareStatement(
                    "insert into TJ_RenM_ZJJY("+
                            "体检编号,总检建议,定义人,定义日期"+
                            ")values(?,?,?,?)"
            );
            PreparedStatement ps8=conn.prepareStatement(
                    "insert into TJ_RenM_ZYS("+
                            "身份证号,序号,开始日期,结束日期,工作单位,"+
                            "工种,有害因素,防护措施,定义人,定义日期"+
                            ")values(?,?,?,?,?,"+
                            "?,?,?,?,?)"
            );
        ){
            conn.setAutoCommit(false);
            for(int i=0;i<patients.size();i++) {

                Object[] os = getObjectArray(patients.get(i));
                Object o = os[15];
                String jh=getHarms(o);

                //ps1
                ps.setObject(1, os[0]);
                ps.setObject(2, os[0]);
                ps.setObject(3, checkLength(os[1],20));//姓名
                ps.setObject(4, Render.getSex(os[2]));
                ps.setObject(5, os[3]);//年龄
                ps.setObject(6, os[4]);//民族
                ps.setObject(7, os[5]);//籍贯
                String phone = hidePhone(os[6]);
                ps.setObject(8, phone);//电话
                ps.setObject(9, phone);//手机
                ps.setObject(10, null);//邮箱
                ps.setObject(11, checkLength(os[7],50));//地址
                ps.setObject(12, os[8]);//婚姻
                ps.setObject(13, os[9]);//身份证号
                if(os[10]!=null) {
                    Date birthdate=(Date) os[10];
                    Calendar c=Calendar.getInstance();
                    c.setTime(birthdate);
                    ps.setObject(14, c.get(Calendar.YEAR));
                    ps.setObject(15, c.get(Calendar.MONTH)+1);
                    String dateTimeString = sdf.format(birthdate);
                    ps.setObject(16, dateTimeString);
                }else {
                    ps.setObject(14, null);
                    ps.setObject(15, null);
                    ps.setObject(16, null);
                }

                ps.setObject(17,checkLength(os[29],100));//社会统一信用代码
                ps.setObject(18, checkLength(os[11],100));//单位名称
                ps.setObject(19, null);//单位地址
                ps.setObject(20, null);//单位性质
                ps.setObject(21, null);//所属行业
                ps.setObject(22, null);//车间
                ps.setObject(23, checkLength(os[12],50));//工种
                ps.setObject(24, os[13]);//总工龄
                ps.setObject(25, os[14]);//接害工龄
                ps.setObject(26, checkLength(jh,500));
                ps.setObject(27, checkLength(os[16],50));//登记人
                ps.setObject(28, sdf.format(os[17]));//登记时间
                ps.setObject(29,"职业健康查体");
                ps.setObject(30, Render.getMedicalType(os[18]));//查体分类
                ps.setObject(31, sdf.format(os[17]));//体检日期
                ps.setObject(32, os[19]);//来源体检号
                ps.setObject(33,os[20]!=null&&"3".equals(os[20].toString()));//复查体检
                ps.setObject(34, checkLength(os[21],50));//主键医生
                ps.setObject(35, sdf.format(os[22]));//主检时间
                ps.setObject(36, checkLength(os[23]==null?Render.getClob(os[27]):os[28],100));//最终结论分类
                ps.setObject(37, true);//合格
                ps.setObject(38, 3);
                ps.setObject(39, null);//备注
                ps.setObject(40, 0);
                ps.setObject(41, checkLength(os[16],100));
                ps.setObject(42, sdf.format(os[17]));
                ps.setObject(43, now);
                ps.setObject(44, place);//体检点名称
                ps.addBatch();

                //ps3
                for(String jhys:jh.split("、")) {
                    ps3.setObject(1, os[0]);
                    ps3.setObject(2, checkLength(jhys,50));
                    ps3.setObject(3, checkLength(os[16],20));
                    ps3.setObject(4, sdf.format(os[17]));
                    ps3.addBatch();
                }

                Object photo=os[24];
                //ps5
                if(photo!=null) {
                    ps5.setObject(1, os[0]);
                    String base64=Render.getClob(photo);
                    BASE64Decoder decoder = new BASE64Decoder();
                    byte[] bytes1 = decoder.decodeBuffer(base64);
                    InputStream is = new ByteArrayInputStream(bytes1);
                    ps5.setBinaryStream(2, is, is.available());
                    ps5.setObject(3, checkLength(os[16],20));//定义人
                    ps5.setObject(4,sdf.format(os[17]));//定义日期
                    ps5.addBatch();
                }

                //ps6
                ps6.setObject(1, os[0]);
                ps6.setObject(2, checkLength(os[25],1000));//检查结论
                ps6.setObject(3, null);//结论分类
                ps6.setObject(4,Render.getClob(os[26]) );//科室小结
                ps6.setObject(5,1 );//结论分类序号
                ps6.setObject(6,checkLength(os[16],20) );//总检医生
                ps6.setObject(7,sdf.format(os[17]));//总检时间
                ps6.addBatch();

                //ps7
                ps7.setObject(1, os[0]);
                ps7.setObject(2, os[23]==null?Render.getClob(os[27])
                        :Render.getClob(os[28]));
                ps7.setObject(3, checkLength(os[16],20));
                ps7.setObject(4,sdf.format(os[17]));
                ps7.addBatch();
            }
            for(int i=0;i<examList.size();i++) {
                Object[] os = getObjectArray(examList.get(i));
                //ps2
                ps2.setObject(1, os[0]);//体检编号
                ps2.setObject(2, checkLength(os[1],20));//项目编号
                ps2.setObject(3, checkLength(os[2],50));//项目名称
                ps2.setObject(4, null);//国家表名称
                ps2.setObject(5, checkLength(os[4],20));//组合编号
                ps2.setObject(6, checkLength(os[3],50));//组合名称
                ps2.setObject(7, checkLength(os[5],2000));//结果
                ps2.setObject(8, null);//总检单位
                ps2.setObject(9,  checkLength(os[8],50));//参考范围
                ps2.setObject(10, null);//参考上限
                ps2.setObject(11, null);//参考下限
                ps2.setObject(12, null);//默认结果
                ps2.setObject(13, null);//单位
                ps2.setObject(14, checkLength(os[7],20));//科室编号
                ps2.setObject(15, checkLength(os[6],50));//科室名称
                ps2.setObject(16, true);//正常
                ps2.setObject(17, checkLength(os[9],20));//体检师
                ps2.setObject(18, true);//输入
                ps2.setObject(19, null);//输入时间
                ps2.setObject(20, true);//显示
                ps2.setObject(21, 1);//报告序号
                ps2.setObject(22, 1);//序号
                ps2.setObject(23, null);//定义人
                ps2.setObject(24, null);//定义日期
                ps2.addBatch();
            }
            for(int i=0;i<sectionList.size();i++) {
                Object[] os = getObjectArray(sectionList.get(i));
                //ps4
                ps4.setObject(1, os[0]);//体检编号
                ps4.setObject(2, checkLength(os[1],20));//科室编号
                ps4.setObject(3, checkLength(os[2],20));//组合编号
                ps4.setObject(4,Render.getClob(os[3]));//科室小结
                ps4.setObject(5,checkLength(os[4],20) );//科室医生
                ps4.setObject(6, ObjectUtils.isEmpty(os[5])?null:sdf.format(os[5]));//录入时间
                ps4.addBatch();
            }
            for(int i=0;i<zysList.size();i++) {
                Object[] os = getObjectArray(zysList.get(i));
                //ps8
                ps8.setObject(1, os[0]);
                ps8.setObject(2, null);//序号
                ps8.setObject(3, sdf.format(os[1]));//开始日期
                ps8.setObject(4, sdf.format(os[2]));//结束日期
                ps8.setObject(5,checkLength(os[3],100) );//工作单位
                ps8.setObject(6,checkLength(os[4],50) );//工种
                ps8.setObject(7,checkLength(getHarms(os[5]),200));//有害因素
                ps8.setObject(8,Render.getOccupationDefend(os[6]) );
                ps8.setObject(9,checkLength(os[7],20) );//登记人
                ps8.setObject(10,sdf.format(os[8]) );//登记日期
                ps8.addBatch();
            }

            log.info("-------开始插入数据-------");
            ps.executeBatch();
            ps2.executeBatch();
            ps3.executeBatch();
            ps4.executeBatch();
            ps5.executeBatch();
            ps6.executeBatch();
            ps7.executeBatch();
            ps8.executeBatch();

            conn.commit();
        }
    }

    /**
     * 获取Object[]
     * @param obj
     * @return
     */
    private Object[] getObjectArray(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return null;
        }

        // 获取对象的所有属性
        Field[] fields = obj.getClass().getDeclaredFields();

        // 创建一个 List 来存储属性值
        List<Object> values = new ArrayList<>();

        // 将属性值按照声明顺序添加到 List 中
        for (Field field : fields) {
            // 排除默认属性 class
            if (!field.getName().equals("serialVersionUID")) {
                field.setAccessible(true); // 设置属性为可访问
                Object value = field.get(obj); // 获取属性值
                values.add(value);
            }
        }

        // 将 List 转换为 Object 数组
        Object[] objectArray = values.toArray();

        return objectArray;
    }


    public static String getHarms(Object harmId) {
        if(harmId==null)return "";
        String[] jharr=harmId.toString().split(",");
        List<String> jhnames=new ArrayList<String>();
        for(String jhid:jharr) {
            jhnames.add(harms.get(jhid));
        }
        String jh= StringUtils.join(jhnames, "、");
        return jh;
    }
    /**
     * 判断是否超出长度限制,如果超出长度就截取
     * @param value
     * @param limit
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String checkLength(Object value,int limit) throws Exception {
        String str=Render.getClob(value);
        //gbk一个汉字占两个字节  utf占3个
        if(str.getBytes("gbk").length>limit) {
            return StringUtil.subBytes(str, limit);
        }else {
            return str;
        }
    }



    public static String hidePhone(Object obj) {
        //中间隐藏四位
        if(obj==null) {
            return null;
        }
        String phone=obj.toString();
        int l=phone.length();
        if(l<=4) {
            return "****";
        }
        return phone.substring(0, ((l-1)/2)-2)+"****"+phone.substring(((l-1)/2)+2);
    }
}

