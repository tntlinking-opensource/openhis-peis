package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFxReviewInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业健康检查职业病危害效应相关指标异常需要复查人员(MdFxReviewInfo)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
public interface MdFxReviewInfoMapper extends BaseMapper<MdFxReviewInfo> {

    /**
     * 分页查询[职业健康检查职业病危害效应相关指标异常需要复查人员]列表
     *
     * @param page  分页参数
     * @param param MdFxReviewInfo查询参数
     * @return 分页数据
     */
    IPage<MdFxReviewInfo> getPage(PageParam<MdFxReviewInfo> page, @Param("param") MdFxReviewInfo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxReviewInfo getInfoById(@Param("id") String id);

}
