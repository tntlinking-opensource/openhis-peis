package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.finance.bean.dto.CollectiveCollectionDto;
import com.center.medical.finance.bean.dto.EpayWaysDto;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 金蝶上传
 * @author xhp
 * @since 2023-05-18 9:24
 */
@Repository
public interface KdUploadMapper extends BaseMapper<Sellcustomer> {
    /**
     * 上传个检+团检
     * @param startTime
     * @param endTime
     * @return
     */
    List<EpayWaysDto> statisticsOfAllInspectionPaidInExpensesOfTheDay(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 积分和体检卡月度个检结算
     * @param startTime
     * @param endTime
     * @return
     */
    List<EpayWaysDto> statisticsOfALLInspectionPaidInExpensesOfTheMonth(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<CollectiveCollectionDto> getCollectiveCollection(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
