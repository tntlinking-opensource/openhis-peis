package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.vo.STHistoryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ZJ总检主表(SectionTotal)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-08 17:32:56
 */
public interface SectionTotalMapper extends BaseMapper<SectionTotal> {

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param SectionTotal查询参数
     * @return 分页数据
     */
    IPage<SectionTotal> getPage(PageParam<SectionTotal> page, @Param("param") SectionTotal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionTotal getInfoById(@Param("id") String id);

    /**
     * 获取总检历史记录
     *
     * @param smId             总检id
     * @param dh               体检类型：0.健康 1.职业
     * @param patientarchiveno 档案id
     * @return
     */
    List<STHistoryVo> getHistory(@Param("smId") String smId, @Param("dh") int dh, @Param("patientarchiveno") String patientarchiveno);

    /**
     * 分页获取总检历史记录
     *
     * @param page             分页对象
     * @param smId             总检id
     * @param dh               体检类型：0.健康 1.职业
     * @param patientarchiveno 档案id
     * @return
     */
    IPage<STHistoryVo> getHistoryData(PageParam<STHistoryVo> page, @Param("smId") String smId, @Param("dh") String dh, @Param("patientarchiveno") String patientarchiveno);


    /**
     * 查询mysql历史数据
     * @param smId
     * @param dh
     * @param patientarchiveno
     * @return
     */
    List<STHistoryVo> getMysqlHistoryData(@Param("smId") String smId, @Param("dh") String dh, @Param("patientarchiveno") String patientarchiveno);

    /**
     * 查询oracle历史数据
     * @param smId
     * @param dh
     * @param id
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<STHistoryVo> getOracleHistoryData(@Param("smId") String smId, @Param("dh") String dh, @Param("idcardno") String idcardno);


    /**
     * oracleHis历史数据
     * @param smId
     * @param dh
     * @param id
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<STHistoryVo> getOracleHisHistoryData(@Param("smId") String smId, @Param("dh") String dh, @Param("idcardno") String idcardno);
}
