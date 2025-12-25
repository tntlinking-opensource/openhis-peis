package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Consulation;
import com.center.medical.member.bean.param.ConStatisticsParam;
import com.center.medical.member.bean.param.ConsulationParam;
import com.center.medical.member.bean.vo.StatisticsListVo;
import org.apache.ibatis.annotations.Param;

/**
 * 科室/总检咨询(Consulation)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:32
 */
public interface ConsulationMapper extends BaseMapper<Consulation> {

    /**
     * 分页查询[科室/总检咨询]列表
     *
     * @param page  分页参数
     * @param param Consulation查询参数
     * @return 分页数据
     */
    IPage<Consulation> getList(PageParam<Consulation> page, @Param("param") ConsulationParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Consulation getInfoById(@Param("id") String id);

    /**
     * 咨询与随访统计分页查询
     * @param page
     * @param param
     * @return
     */
    IPage<StatisticsListVo> getStatisticsListData(PageParam<StatisticsListVo> page, @Param("param") ConStatisticsParam param);
}
