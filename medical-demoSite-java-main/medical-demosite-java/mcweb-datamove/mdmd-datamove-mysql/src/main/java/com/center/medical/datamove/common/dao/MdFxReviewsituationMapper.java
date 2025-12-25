package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFxReviewsituation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 本次职业健康检查复查人员复查情况一览表(MdFxReviewsituation)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
public interface MdFxReviewsituationMapper extends BaseMapper<MdFxReviewsituation> {

    /**
     * 分页查询[本次职业健康检查复查人员复查情况一览表]列表
     *
     * @param page  分页参数
     * @param param MdFxReviewsituation查询参数
     * @return 分页数据
     */
    IPage<MdFxReviewsituation> getPage(PageParam<MdFxReviewsituation> page, @Param("param") MdFxReviewsituation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxReviewsituation getInfoById(@Param("id") String id);

}
