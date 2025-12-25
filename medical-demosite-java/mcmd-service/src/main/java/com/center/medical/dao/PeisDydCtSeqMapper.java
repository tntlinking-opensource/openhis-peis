package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PeisDydCtSeq;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 体检者导引单CT排序(PeisDydCtSeq)数据库访问层
 *
 * @author ay
 * @since 2023-09-01 18:47:31
 */
public interface PeisDydCtSeqMapper extends BaseMapper<PeisDydCtSeq> {

    /**
     * 分页查询[体检者导引单CT排序]列表
     *
     * @param page  分页参数
     * @param param PeisDydCtSeq查询参数
     * @return 分页数据
     */
    IPage<PeisDydCtSeq> getPage(PageParam<PeisDydCtSeq> page, @Param("param") PeisDydCtSeq param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisDydCtSeq getInfoById(@Param("id") String id);


    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    PeisDydCtSeq getByPatientcode(@Param("patientCode") String patientCode);

    /**
     * 获取当天最后一个序号
     * @param date
     * @return
     */
    Integer getLastSeq(@Param("time") Date date);
}
