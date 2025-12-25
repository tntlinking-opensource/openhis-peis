package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFailTotalVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KF迟捡、阳性、不合格样本回访(MdFailTotalVisit)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:16
 */
public interface MdFailTotalVisitMapper extends BaseMapper<MdFailTotalVisit> {

    /**
     * 分页查询[KF迟捡、阳性、不合格样本回访]列表
     *
     * @param page  分页参数
     * @param param MdFailTotalVisit查询参数
     * @return 分页数据
     */
    IPage<MdFailTotalVisit> getPage(PageParam<MdFailTotalVisit> page, @Param("param") MdFailTotalVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFailTotalVisit getInfoById(@Param("id") String id);

}
