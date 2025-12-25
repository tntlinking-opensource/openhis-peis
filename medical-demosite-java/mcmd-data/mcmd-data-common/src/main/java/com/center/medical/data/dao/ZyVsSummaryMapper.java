package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.enums.MedicalType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZyVsSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * JC职业病处理意见(ZyVsSummary)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:16
 */
public interface ZyVsSummaryMapper extends BaseMapper<ZyVsSummary> {

    /**
     * 分页查询[JC职业病处理意见]列表
     *
     * @param page  分页参数
     * @param param ZyVsSummary查询参数
     * @return 分页数据
     */
    IPage<ZyVsSummary> getPage(PageParam<ZyVsSummary> page, @Param("param") ZyVsSummary param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ZyVsSummary getInfoById(@Param("id") String id);

    /**
     * 根据主键id获取记录详情，没删除的
     *
     * @param id
     * @return
     */
    ZyVsSummary getZyVsSummaryById(String id);

    /**
     * 根据条件获取职业病处理意见ID
     *
     * @param medicaltype 职业体检类别：0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急
     * @param odList      危害因素ID集合
     * @param itemIds     收费项目ID集合
     * @return
     * @see MedicalType
     */
    List<String> getIdList(@Param("medicaltype") String medicaltype, @Param("odList") List<String> odList, @Param("itemIds") List<String> itemIds);

    /**
     * 根据条件获取职业处理意见 对象集合
     * @param regimentationNote
     * @param occupationSummary
     * @param occupationDiagnosis
     * @return
     */
    List<ZyVsSummary> getZyVsSummarySearchData(@Param("regimentationNote")String regimentationNote,@Param("occupationSummary") String occupationSummary,@Param("occupationDiagnosis") String[] occupationDiagnosis);
}
