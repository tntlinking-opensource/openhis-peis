package com.center.medical.machine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.machine.bean.dto.CurrentDateInfoDto;
import com.center.medical.machine.bean.dto.FindDeptDto;
import com.center.medical.machine.bean.dto.PersonalReportDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
public interface ReadCardMapper extends BaseMapper<Peispatient> {


    /**
     * 查找科室加项体检号
     *
     * @param idcardno
     * @return
     */
    String checkAddNo(@Param("idcardno") String idcardno);

    /**
     * 查询科室信息
     *
     * @param depIds
     * @return
     */
    List<FindDeptDto> findDept(@Param("patientcode") String patientcode, @Param("depIds") String[] depIds);

    /**
     * 健康体检打印健康报告，职业体检打印职业报告，综合体检两份报告都打印
     *
     * @param idcardno
     * @return
     */
    List<PersonalReportDto> getPersonalReportList(@Param("idcardno") String idcardno);


    /**
     * 按身份证获取当前日期信息
     *
     * @param idcardno
     * @return
     */
    CurrentDateInfoDto getCurrentDateInfoByIdCardNo(@Param("idcardno") String idcardno);

    /**
     * 查找登记时间是今天的体检号
     *
     * @param idcardno
     * @return
     */
    List<String> unregisteredCode(@Param("idcardno") String idcardno);
}
