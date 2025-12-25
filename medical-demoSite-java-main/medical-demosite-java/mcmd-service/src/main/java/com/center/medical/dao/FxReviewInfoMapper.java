package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.FxReviewInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 职业健康检查职业病危害效应相关指标异常需要复查人员(FxReviewInfo)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:38
 */
public interface FxReviewInfoMapper extends BaseMapper<FxReviewInfo> {

    /**
     * 分页查询[职业健康检查职业病危害效应相关指标异常需要复查人员]列表
     *
     * @param page  分页参数
     * @param param FxReviewInfo查询参数
     * @return 分页数据
     */
    IPage<FxReviewInfo> getList(PageParam<FxReviewInfo> page, @Param("param") FxReviewInfo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxReviewInfo getInfoById(@Param("id") String id);

}
