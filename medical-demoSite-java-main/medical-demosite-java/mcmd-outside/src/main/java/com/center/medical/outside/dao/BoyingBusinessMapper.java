package com.center.medical.outside.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Attachment;
import com.center.medical.outside.bean.model.BoyingGetPatientInfoData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 博英心电信息管理系统对接
 * @author xhp
 * @since 2025-05-23 15:50
 */
@Repository
public interface BoyingBusinessMapper extends BaseMapper<Attachment> {
    /**
     * 心电信息管理系统发起查询申请单请求后，HIS根据传入的参数查询并返回符合条件的申请单。
     * 只要通过接口回传过结果，就不再能从这个接口查出来
     * 推断：已审核的不查出来
     * @param patientcode
     * @return
     */
    List<BoyingGetPatientInfoData> getPatientInfo(@Param("patientcode") String patientcode);
}
