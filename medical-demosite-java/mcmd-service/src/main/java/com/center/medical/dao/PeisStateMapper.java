package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PeisState;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者上传状态(PeisState)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
public interface PeisStateMapper extends BaseMapper<PeisState> {

    /**
     * 分页查询[体检者上传状态]列表
     *
     * @param page  分页参数
     * @param param PeisState查询参数
     * @return 分页数据
     */
    IPage<PeisState> getList(PageParam<PeisState> page, @Param("param") PeisState param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeisState getInfoById(@Param("id") String id);

    /**
     * 根据体检号和上传标志 获取记录详情
     *
     * @param patientcode
     * @param scbs
     * @return
     */
    PeisState getInfoByPatientcode(@Param("patientcode") String patientcode, @Param("scbs") int scbs);

    /**
     * 根据体检号获取记录详情
     *
     * @param patientcode
     * @return
     */
    PeisState getByPatientcode(@Param("patientcode") String patientcode);
}
