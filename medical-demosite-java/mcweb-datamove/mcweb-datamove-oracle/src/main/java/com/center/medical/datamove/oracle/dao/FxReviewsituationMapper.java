package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.FxReviewsituation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 本次职业健康检查复查人员复查情况一览表(FxReviewsituation)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:18:59
 */
public interface FxReviewsituationMapper extends BaseMapper<FxReviewsituation> {

    /**
     * 分页查询[本次职业健康检查复查人员复查情况一览表]列表
     *
     * @param page  分页参数
     * @param param FxReviewsituation查询参数
     * @return 分页数据
     */
    IPage<FxReviewsituation> getPage(PageParam<FxReviewsituation> page, @Param("param") FxReviewsituation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FxReviewsituation getInfoById(@Param("id") String id);

}
