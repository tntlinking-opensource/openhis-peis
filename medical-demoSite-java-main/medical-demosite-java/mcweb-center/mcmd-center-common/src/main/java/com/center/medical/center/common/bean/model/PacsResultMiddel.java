/**
 * @PacsResult.java
 * @com.lingnet.mec.entity
 * @Description：
 * 
 * @author xuhp 
 * @copyright  2016
 * @version V
 * @since 2016-10-26
 */
package com.center.medical.center.common.bean.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

/** 
 * @ClassName: PacsResultMiddel 
 * @Description: Pacs数据 
 * @author xuhp
 * @date 2016-10-26 下午2:08:04 
 *  
 */
@TableName("Pacs_ResultZhongKang")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacsResultMiddel implements Serializable {
	private static final long serialVersionUID = -8432655117866463031L;
	//fields
	@TableId(value="id",type = IdType.AUTO)
	private Integer id;

	@TableField("PatientCode")
	private String patientCode;//体检号

	@TableField("PatientName")
	private String patientName;//体检者姓名

	@TableField("ExamFeeItem_Code")
	private String examFeeItemCode;//收费项目代码

	@TableField("ExamDateTime")
	private Date examDateTime;//检查时间

	@TableField("ExamDoctor")
	private String examDoctor;//检查师

	@TableField("ExamResultDesc")
	private String examResultDesc;//检查结果描述

	@TableField("ExamResultSummary")
	private String examResultSummary;//检查结果总结

	@TableField("ExamResultIsNormal")
	private String examResultIsNormal;//检查结果是否正常

	@TableField("ImageFullPath")
	private String imageFullPath;//文件路径

	@TableField("TransfterTarget")
	private String transfterTarget;

	@TableField("F_ResultTransfered")
	private Integer f_ResultTransfered;

	@TableField("userName")
	private String userName;

}
