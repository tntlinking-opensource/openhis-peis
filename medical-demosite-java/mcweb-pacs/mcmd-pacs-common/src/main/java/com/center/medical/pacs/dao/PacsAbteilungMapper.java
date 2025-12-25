package com.center.medical.pacs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacs.bean.dto.PacsAbteilungPatientListDto;
import com.center.medical.pacs.bean.param.PatientListParam;
import com.center.medical.pacs.bean.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-15 9:41
 */
@Repository
public interface PacsAbteilungMapper extends BaseMapper<PacsResult> {

    IPage<PacsAbteilungPatientListDto> getPatientList(PageParam page, @Param("patientListParam") PatientListParam patientListParam);

    List<PacsAbteilungItemListVo> getItemList(@Param("patientcode") String patientcode, @Param("deptNo") String deptNo);

    List<PacsAbteilungSignListVo> getSignList(@Param("patientcode") String patientcode, @Param("itemId") String itemId);

    List<PacsAbteilungAbteilunListVo> getAbteilunList(@Param("patientcode") String patientcode, @Param("userNo") String userNo);

    IPage<PacsAbteilungHistoryListVo> getHistoryList(PageParam page, @Param("patientcode") String patientcode, @Param("deptNo") String deptNo);

    PacsAbteilungPatientTotalVo getPatientTotal(@Param("patientListParam") PatientListParam patientListParam);

    @DataSource(value = DataSourceType.SLAVE)
    List<PacsAbteilungHistoryListVo> getHistoryListOracle(@Param("patientcode") String patientcode, @Param("ids") List<String> ids
            , @Param("describe") String describe,@Param("idcardno") String idcardno , @Param("deptNo") String deptNo);

    List<PacsAbteilungHistoryListVo> getHistoryListMysql(@Param("patientcode") String patientcode, @Param("deptNo") String deptNo, @Param("describe") String describe);

    /**
     * 查询新系统历史数据
     * @param page
     * @param patientcode
     * @param deptNo
     * @return
     */
    IPage<PacsAbteilungHistoryListVo> getHistoryListAll(PageParam page, @Param("patientcode") String patientcode, @Param("deptNo") String deptNo);

    /**
     * 查询oracle历史数据
     * @param patientcode
     * @param ids
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<PacsAbteilungHistoryListVo> getHistoryListOracleHis(@Param("patientcode") String patientcode, @Param("ids") List<String> ids
            , @Param("describe") String describe,@Param("idcardno") String idcardno);

    /**
     * 查询老系统历史小结
     * @param idcardno
     * @param deptNo
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    PacsAbteilungHistoryListVo getHistorySummary(@Param("idcardno") String idcardno,@Param("deptNo") String deptNo);
}
