package com.center.medical.enterprise.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.enterprise.bean.dto.AgeDistributionDto;
import com.center.medical.enterprise.bean.dto.PhysicalDistributionDto;
import com.center.medical.enterprise.bean.dto.staffDto;
import com.center.medical.enterprise.bean.model.Peispatient;
import com.center.medical.enterprise.common.util.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:50
 */
public interface PeispatientMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getPage(PageParam<Peispatient> page, @Param("param") Peispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 通过体检号获取记录
     *
     * @param patientCode
     * @return
     */
    Peispatient getByPatientCode(@Param("patientCode") String patientCode);


    /**
     * 获取体检人数
     * @param startTime
     * @param endTime
     * @param customerId
     * @return
     */
    Long getPeopleNum(@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("customerId") String customerId);

    /**
     * 获取人员构成
     * @param aLong
     * @return
     */
    List<staffDto> getStaff(@Param("customerId") String customerId);

    /**
     * 获取年龄分布
     * @param customerId
     * @return
     */
    List<AgeDistributionDto> getAgeDistribution(@Param("customerId") String customerId);

    /**
     * 体检分布情况
     * @param customerId
     * @return
     */
    List<PhysicalDistributionDto> getPhysicalDistribution(@Param("customerId") String customerId);

    /**
     * 获取同一个档案号的其他体检号
     * @param patientarchiveno
     * @param patientcode
     * @return
     */
    List<String> getOtherCode(@Param("patientarchiveno") String patientarchiveno,@Param("patientcode") String patientcode);
}
