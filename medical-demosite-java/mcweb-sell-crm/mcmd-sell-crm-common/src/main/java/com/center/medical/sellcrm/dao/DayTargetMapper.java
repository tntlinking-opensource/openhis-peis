package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Selltarget;
import com.center.medical.sellcrm.bean.param.DayTargetParam;
import com.center.medical.sellcrm.bean.vo.DayTargetVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * XS销售目标(CrmSelltarget)数据库访问层
 *
 * @author ay
 * @since 2024-01-22 11:13:07
 */
public interface DayTargetMapper extends BaseMapper<Selltarget> {

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param param CrmSelltarget查询参数
     * @return 分页数据
     */
    IPage<DayTargetVo> getPage(PageParam<DayTargetVo> page, @Param("param") DayTargetParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Selltarget getInfoById(@Param("id") String id);

    /**
     * 获取总结数据
     * @param param
     * @return
     */
    Double getSummaryData( @Param("param") DayTargetParam param);

    /**
     * 导出销售日目标
     * @param param
     * @return
     */
    List<DayTargetVo> exportData( @Param("param") DayTargetParam param);
}
