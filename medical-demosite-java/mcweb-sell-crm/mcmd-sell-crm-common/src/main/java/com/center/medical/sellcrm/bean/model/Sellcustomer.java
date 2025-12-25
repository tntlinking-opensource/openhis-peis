package com.center.medical.sellcrm.bean.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.center.medical.bean.enums.CustomerStatus;
import com.center.medical.bean.enums.JinanStatus;
import com.center.medical.common.annotation.Excel;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 我的客户表(Sellcustomer)表实体类
 *
 * @author 路飞船长
 * @since 2022-11-08 17:52:16
 */
@Data
@TableName("crm_sellcustomer")
@ApiModel(value = "Sellcustomer", description = "我的客户实体类")
public class Sellcustomer extends Model<Sellcustomer> implements Serializable {
    private static final long serialVersionUID = 245298408537239313L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "客户单位名称")
    @Xss(message = "用户账号不能包含脚本字符")
    @Excel(name = "客户单位名称(必填)")
    private String khdwmc;

    @ApiModelProperty(value = "客户单位输入码")
    private String khdwsrm;

    @ApiModelProperty(value = "客户单位联系人")
    @Excel(name = "客户单位联系人(必填)")
    private String khdwlxr;

    @ApiModelProperty(value = "客户电话")
    @Excel(name = "客户电话(必填)")
    private String khdh;

    @ApiModelProperty(value = "法人单位名称")
    private String frdwmc;

    @ApiModelProperty(value = "法定代表人")
    private String fddbr;

    @ApiModelProperty(value = "邮政编码")
    @Excel(name = "邮政编码(非必填)")
    private String yzbm;

    @ApiModelProperty(value = "企业规模")
    private String qygm;

    @ApiModelProperty(value = "企业经济类型")
    private String qyjjlx;

    @ApiModelProperty(value = "职业卫生负责人")
    private String zywsfzr;

    @ApiModelProperty(value = "客户单位注册地址")
    @Excel(name = "客户单位注册地址(非必填)")
    private String khdwzcdz;

    @ApiModelProperty(value = "职业卫生管理机构")
    private String zywsgljg;

    @ApiModelProperty(value = "单位机构代码")
    @Excel(name = "单位机构代码(非必填)")
    private String dwjgdm;

    @ApiModelProperty(value = "社会信用代码")
    @Excel(name = "社会信用代码(必填)")
    private String socialCreditCode;

    @ApiModelProperty(value = "销售经理")
    private String xsjl;

    @ApiModelProperty(value = "注册类型")
    private String zclx;

    @ApiModelProperty(value = "所属行业")
    private String sshy;

    @ApiModelProperty(value = "隶属关系")
    private String lsgx;

    @ApiModelProperty(value = "上级主管单位")
    private String sjzgdw;

    @ApiModelProperty(value = "实际从业人数")
    private String sjcyrs;

    @ApiModelProperty(value = "流动人数")
    private String ldrs;

    @ApiModelProperty(value = "生产工人数")
    private String scgrs;

    @ApiModelProperty(value = "职业病危害因素人数")
    private String zybwhysrs;

    @ApiModelProperty(value = "职业病危害作业场所数")
    private String zybwhzycss;

    @ApiModelProperty(value = "职业病危害因素类别")
    private String zybwhyslb;

    @ApiModelProperty(value = "职业病危害因素")
    private String zybwhys;

    @ApiModelProperty(value = "工艺流程")
    private String gylc;

    @ApiModelProperty(value = "主要原辅料")
    private String zyyfl;

    @ApiModelProperty(value = "体检团体类型")
    private Integer tjttlx;

    @ApiModelProperty(value = "主要产品")
    private String zycp;

    @ApiModelProperty(value = "客户上次体检单位地址")
    private String khsctjdwdz;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "升级日期")
    private Date sjrq;


    /**
     * @see CustomerStatus
     */
    @ApiModelProperty(value = "客户状态：0潜在 1正式 2释放")
    private Integer khzt;

    @ApiModelProperty(value = "是否删除：0.未删除 1.已删除")
    private Integer isDelete;

    @ApiModelProperty(value = "客户公共池领取ID")
    private String clientid;

    @ApiModelProperty(value = "领导分配ID")
    private String ldfpid;

    @ApiModelProperty(value = "销售经理ID")
    private String xsjlid;

    @ApiModelProperty(value = "分中心ID")
    private String fzxid;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdate;

    @ApiModelProperty(value = "修改日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifydate;

    @ApiModelProperty(value = "流失标志（1、已流失）")
    private Integer lost;

    @ApiModelProperty(value = "数字形式团体ID，方便财务导出后处理数据使用")
    private Integer intId;

    @ApiModelProperty(value = "简述")
    private String briefText;

    @ApiModelProperty(value = "省级代码")
    private String province;

    @ApiModelProperty(value = "市级代码")
    private String city;

    @ApiModelProperty(value = "区级代码")
    private String district;

    @ApiModelProperty(value = "街道代码")
    private String street;

    @ApiModelProperty(value = "行业类别小类代码")
    private String indusTypeCode;

    @ApiModelProperty(value = "经济类型编码")
    private String economyCode;

    @ApiModelProperty(value = "企业规模编码")
    private String crptSizeCode;

    @ApiModelProperty(value = "职工人数")
    private Integer workForce;

    @ApiModelProperty(value = "接触危害因素人数")
    private Integer holdCardMan;

    @ApiModelProperty(value = "生产工人数")
    private Integer workmanNum;

    @ApiModelProperty(value = "接触职业病危害因素女工人数")
    private Integer workmistressNum;

    @ApiModelProperty(value = "经营面积")
    private String workArea;

    @ApiModelProperty(value = "注册资金")
    private String registerFund;

    @ApiModelProperty(value = "职业卫生安全负责人")
    private String safetyPrincipal;

    @ApiModelProperty(value = "建厂日期")
    private Date buildDate;

    @ApiModelProperty(value = "检测联系人")
    private String linkman1;

    @ApiModelProperty(value = "检测联系人职务")
    private String position1;

    @ApiModelProperty(value = "检测联系人电话")
    private String linkphone1;

    @ApiModelProperty(value = "体检联系人")
    private String linkman2;

    @ApiModelProperty(value = "体检联系人职务")
    private String position2;

    @ApiModelProperty(value = "体检联系人电话")
    private String linkphone2;

    @ApiModelProperty(value = "职业卫生安全联系人职务")
    private String safeposition;

    @ApiModelProperty(value = "职业卫生安全联系人电话")
    private String safephone;

    @ApiModelProperty(value = "隶属关系")
    private String subjeConn;

    @ApiModelProperty(value = "作业场所地址")
    private String enrolAddress;

    @ApiModelProperty(value = "作业场所邮政编码")
    private String enrolPostalcode;

    @ApiModelProperty(value = "职业卫生管理机构")
    private String occManaOffice;

    /**
     * @see JinanStatus
     */
    @ApiModelProperty(value = "济南市职业卫生综合管理平台上传状态 -1未录入必填字段不能上传 0待上传 1已上传  2上传失败")
    private Integer jinanStatus;

    @ApiModelProperty(value = "济南市职业卫生综合管理平台上传失败信息")
    private String jinanMsg;

    @ApiModelProperty(value = "行业类别门类代码")
    private String indusTypeCode1;

    @ApiModelProperty(value = "行业类别大类代码")
    private String indusTypeCode2;

    @ApiModelProperty(value = "行业类别种类代码")
    private String indusTypeCode3;

    @ApiModelProperty(value = "法人联系电话")
    private String phone;

    @ApiModelProperty(value = "用工单位统一社会信用代码")
    private String rauSocialCreditCode;

    @ApiModelProperty(value = "用工单位经济类型编码")
    private String rauEconomyCode;

    @ApiModelProperty(value = "用工单位行业类别门类代码")
    private String rauIndusTypeCode1;

    @ApiModelProperty(value = "用工单位行业类别大类代码")
    private String rauIndusTypeCode2;

    @ApiModelProperty(value = "用工单位行业类别种类代码")
    private String rauIndusTypeCode3;

    @ApiModelProperty(value = "用工单位行业类别小类代码")
    private String rauIndusTypeCode;

    @ApiModelProperty(value = "用工单位企业规模编码")
    private String rauQygm;

    @ApiModelProperty(value = "用工单位省级代码")
    private String rauProvince;

    @ApiModelProperty(value = "用工单位市级代码")
    private String rauCity;

    @ApiModelProperty(value = "用工单位区级代码")
    private String rauDistrict;

    @ApiModelProperty(value = "用工单位街道代码")
    private String rauStreet;

    @ApiModelProperty(value = "用人单位所属区名称（固定10个）")
    private String unitarea;

    @ApiModelProperty(value = "用工客户单位名称")
    private String rauKhdwmc;

    @ApiModelProperty(value = "用工单位所属区名称（固定10个）")
    private String rauUnitarea;

    @ApiModelProperty(value = "营业执照上的企业名称用工客户单位名称")
    private String licenseName;

    @ApiModelProperty(value = "金蝶ID")
    private String jindieId;

    @TableField(exist = false)
    @ApiModelProperty(value = "金蝶名称")
    private String jindieName;

    @TableField(exist = false)
    @ApiModelProperty(value = "销售经理电话")
    private String xsjldh;

    @TableField(exist = false)
    @ApiModelProperty(value = "职业病危害因素名称")
    private String zybwhyName;

    @TableField(exist = false)
    @ApiModelProperty(value = "文件个数")
    private Integer wjgs;

    @TableField(exist = false)
    @ApiModelProperty(value = "上传文件地址")
    private List<String> filePaths;


    @TableField(exist = false)
    @ApiModelProperty(value = "网站用户名")
    private String username;

    @SuppressWarnings("unchecked")
    public synchronized void copyFrom(Object bean) {

        if (bean == null) {
            return;
        }
        Class<Object> clazz = (Class<Object>) bean.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            if ("serialVersionUID,id,".indexOf(f.getName() + ",") >= 0) {
                continue;
            }
            Object value = invokeGetterMethod(bean, f.getName());
            if (value != null && !value.equals("null")) {
                invokeSetterMethod(this, f.getName(), value);
            }
        }
    }

    public static Object invokeGetterMethod(Object object, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        try {
            Method getterMethod = object.getClass().getMethod(getterMethodName);
            return getterMethod.invoke(object);
        } catch (Exception e) {
            return null;
        }
    }

    public static void invokeSetterMethod(Object object, String propertyName, Object propertyValue) {
        Class<?> setterMethodClass = propertyValue.getClass();
        invokeSetterMethod(object, propertyName, propertyValue, setterMethodClass);
    }

    public static void invokeSetterMethod(Object object, String propertyName, Object propertyValue, Class<?> setterMethodClass) {
        //返回对象中的set方法字符串
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        try {
            //获取对象的方法信息
            Method setterMethod = object.getClass().getMethod(setterMethodName, setterMethodClass);
            //调用对象中方法
            setterMethod.invoke(object, propertyValue);
            return;
        } catch (Exception e) {
            // e.printStackTrace();
        }

        HashMap<Class<?>, Class<?>> mapClass = new HashMap<Class<?>, Class<?>>();
        mapClass.put(Integer.class, int.class);
        mapClass.put(Double.class, double.class);
        mapClass.put(Float.class, float.class);
        mapClass.put(Boolean.class, boolean.class);
        mapClass.put(Byte.class, byte.class);
        mapClass.put(java.sql.Date.class, Date.class);
        mapClass.put(java.sql.Timestamp.class, Date.class);
        try {
            //获取对象的方法信息
            Method setterMethod = object.getClass().getMethod(setterMethodName, mapClass.get(setterMethodClass));
            //调用对象中方法
            setterMethod.invoke(object, propertyValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
