package com.center.medical.pay.dao;

import com.center.medical.bean.model.Peispatient;
import com.center.medical.pay.bean.dto.AllItemsDto;
import com.center.medical.pay.bean.dto.OrderInfoByPatientCodeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表(Createorder)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 14:51:36
 */
public interface TongLianProxyMapper {


    /**
     * 检查最近是否有未支付订单
     *
     * @param patientCode
     * @return
     */
    Integer checkNotPayRecentOrder(@Param("patientCode") String patientCode);

    /**
     * 获取体检者表
     *
     * @param patientCode
     * @return
     */
    Peispatient getByPatientCode(@Param("patientCode") String patientCode);

    /**
     * 或缺订单部分详情
     *
     * @param patientCode
     * @return
     */
    OrderInfoByPatientCodeDto getOrderInfoByPatientCode(@Param("patientCode") String patientCode);

    /**
     * 获取支付项目
     *
     * @param patientCode
     * @return
     */
    List<AllItemsDto> getAllItems(@Param("patientCode") String patientCode);

    /**
     * 获取体检号的最大序列
     *
     * @param patientCode
     * @return
     */
    Integer getChargeMaxNumIndex(@Param("patientCode") String patientCode);
}
