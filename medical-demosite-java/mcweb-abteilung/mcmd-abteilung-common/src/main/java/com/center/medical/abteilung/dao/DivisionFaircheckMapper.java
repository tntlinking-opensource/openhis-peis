package com.center.medical.abteilung.dao;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Items;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:50
 */
public interface DivisionFaircheckMapper extends BaseMapper<Peispatient> {

    /**
     * 体检者表格数据
     * @param page
     * @param ksId
     * @param start
     * @param end
     * @return
     */
    IPage<Peispatient> listData(PageParam<Peispatient> page,@Param("ksId") String ksId, @Param("start")DateTime start,@Param("end") DateTime end);

    /**
     * 查询ItemAndBase
     * @param patientCode
     * @param ksId
     * @return
     */
    List<Items> selectItemAndBase(@Param("patientCode")String patientCode,@Param("ksId") String ksId);
}
