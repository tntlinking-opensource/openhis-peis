package com.center.medical.machine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 自助登记机(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
public interface RegistrationMachineMapper extends BaseMapper<Peispatient> {


    /**
     * 通过套餐id查询是否存在
     * @param idTjtc
     * @return
     */
    Integer existBxxmByTcId(@Param("tcId") String idTjtc);

    /**
     * 查询收费项目数量
     * @param patientCode
     * @return
     */
    Integer getItemNumByPatientCode(@Param("patientCode")String patientCode);

    /**
     * 通过体检号获取支付名称
     * @param patientCode
     * @return
     */
    String getPayWayNameByPatientCode(@Param("patientCode")String patientCode);

    /**
     * 获取领取名称
     * @param idInformway
     * @return
     */
    List<String> getMethodName(@Param("idInformway")String idInformway);

    /**
     * 获取体检者对应的收费项目信息 按餐序、科室、标本类型排序
     * @param patientCode
     * @param cid
     * @param fieldName
     * @return
     */
    List<Map<String, Object>> getModelItems2(@Param("patientCode")String patientCode,@Param("cid") String cid,@Param("fieldName") String fieldName);


    /**
     * 查询重复项目
     * @param inspectId
     * @param itemIds
     * @return
     */
    List<String> getRepeatItems(@Param("inspectId")String inspectId,@Param("itemIds") String[] itemIds);

    /**
     * 无关联科室已检
     * @param patientcode
     * @return
     */
    List<Peispatientfeeitem> noRelatedDepartments(@Param("patientcode")String patientcode);
}
