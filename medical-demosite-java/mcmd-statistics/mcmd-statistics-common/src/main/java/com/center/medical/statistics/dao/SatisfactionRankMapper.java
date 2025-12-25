package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Satisfaction;
import com.center.medical.statistics.bean.param.SatisfactionRankParam;
import com.center.medical.statistics.bean.vo.SatisfactionRankVo;
import org.apache.ibatis.annotations.Param;

/**
 * 科室满意度排名(Satisfaction)表数据库访问层
 *
 * @author ay
 * @since 2023-04-20 10:01:49
 */
public interface SatisfactionRankMapper extends BaseMapper<Satisfaction> {

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page  分页参数
     * @param param Satisfaction查询参数
     * @return 分页数据
     */
    IPage<SatisfactionRankVo> getList(PageParam<SatisfactionRankVo> page, @Param("param") SatisfactionRankParam param);



}
