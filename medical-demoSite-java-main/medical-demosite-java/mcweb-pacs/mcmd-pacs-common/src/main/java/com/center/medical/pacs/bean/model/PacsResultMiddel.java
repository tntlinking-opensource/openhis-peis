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
package com.center.medical.pacs.bean.model;



import lombok.Data;

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
@Data
public class PacsResultMiddel implements Serializable {
	private static final long serialVersionUID = -8432655117866463031L;
	//fields
	private Integer id;
	private String patientCode;//体检号
	private String patientName;//体检者姓名
	private String examFeeItemCode;//收费项目代码
	private Date examDateTime;//检查时间
	private String examDoctor;//检查师
	private String examResultDesc;//检查结果描述
	private String examResultSummary;//检查结果总结
	private String examResultIsNormal;//检查结果是否正常
	private String imageFullPath;//文件路径
	private String transfterTarget;
	private Integer f_ResultTransfered;
	private String userName;


	public PacsResultMiddel(String patientCode, String patientName, String examFeeItemCode, Date examDateTime, String examDoctor, String examResultDesc, String examResultSummary, Integer f_ResultTransfered, String userName) {
		this.patientCode = patientCode;
		this.patientName = patientName;
		this.examFeeItemCode = examFeeItemCode;
		this.examDateTime = examDateTime;
		this.examDoctor = examDoctor;
		this.examResultDesc = examResultDesc;
		this.examResultSummary = examResultSummary;
		this.f_ResultTransfered = f_ResultTransfered;
		this.userName = userName;
	}

	public PacsResultMiddel() {
	}
}
