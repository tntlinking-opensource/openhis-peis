package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.DrugstorePrescribe;
import com.center.medical.abteilung.bean.param.DrugstorePreParam;
import com.center.medical.abteilung.bean.vo.DrugstorePreVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 开药记录(DrugstorePrescribe)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:18
 */
public interface DrugstorePrescribeMapper extends BaseMapper<DrugstorePrescribe> {

    /**
     * 分页查询[开药记录]列表
     *
     * @param page  分页参数
     * @param param DrugstorePrescribe查询参数
     * @return 分页数据
     */
    IPage<DrugstorePreVo> getList(PageParam<DrugstorePreVo> page, @Param("param") DrugstorePreParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrugstorePrescribe getInfoById(@Param("id") String id);

    /**
     * 分页查询药房管理售药统计
     *
     * @param page
     * @param param
     * @return
     */
    IPage<DrugstorePreVo> getStatisticsListData(PageParam<DrugstorePreVo> page, @Param("param") DrugstorePreParam param);

    /**
     * 获取导出数据
     *
     * @param param
     * @return
     */
    List<DrugstorePreVo> getExportData(@Param("param") DrugstorePreParam param);

    /**
     * 快捷开药-右侧-获得已开药记录
     *
     * @param patientcode
     * @return
     */
    List<DrugstorePreVo> getAddedData(@Param("patientcode") String patientcode, @Param("username") String username);

    /**
     * 获取上次体检开的什么药
     *
     * @param patientcode
     * @param patientarchiveno
     * @return
     */
    String getLastDrugs(@Param("patientcode") String patientcode, @Param("patientarchiveno") String patientarchiveno);
}
