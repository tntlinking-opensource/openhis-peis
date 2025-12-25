package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.VisitMain;
import com.center.medical.member.bean.param.BelowSampleParam;
import com.center.medical.member.bean.vo.BelowSampleEditVo;
import com.center.medical.member.bean.vo.BelowSampleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-07 08:31:21
 */
public interface BelowSampleMapper extends BaseMapper<VisitMain> {

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param VisitMain查询参数
     * @return 分页数据
     */
    IPage<BelowSampleVo> getList(PageParam<BelowSampleVo> page, @Param("param") BelowSampleParam param);

    /**
     * 导出Excel
     *
     * @param param
     * @return
     */
    List<BelowSampleVo> export(@Param("param") BelowSampleParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BelowSampleEditVo getInfoById(@Param("id") String id);

}
