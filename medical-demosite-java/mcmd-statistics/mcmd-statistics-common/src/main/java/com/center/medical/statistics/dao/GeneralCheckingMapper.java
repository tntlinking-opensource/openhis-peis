package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.GeneralCheckingParam;
import com.center.medical.statistics.bean.vo.AnalyseTotalVo;
import com.center.medical.statistics.bean.vo.GeneralCheckingVo;
import org.apache.ibatis.annotations.Param;

/**
 * 总检工作量(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-19 18:07:47
 */
public interface GeneralCheckingMapper extends BaseMapper<Peispatient> {

    /**
    * 分页查询 健康
    *
    * @param page 分页参数
    * @param param Peispatient查询参数
    * @return 分页数据
    */
    IPage<GeneralCheckingVo> getList(PageParam<GeneralCheckingVo> page, @Param("param") GeneralCheckingParam param);


    /**
     * 分页查询 职业
     * @param page
     * @param param
     * @return
     */
    IPage<GeneralCheckingVo> getList2(PageParam<GeneralCheckingVo> page, @Param("param") GeneralCheckingParam param);

    /**
     * 汇总数据 健康
     * @param page
     * @param param
     * @return
     */
    IPage<AnalyseTotalVo> getTotalList(PageParam<AnalyseTotalVo> page, @Param("param") GeneralCheckingParam param);

    /**
     * 汇总数据 职业
     * @param page
     * @param param
     * @return
     */
    IPage<AnalyseTotalVo> getTotalList2(PageParam<AnalyseTotalVo> page, @Param("param") GeneralCheckingParam param);
}
