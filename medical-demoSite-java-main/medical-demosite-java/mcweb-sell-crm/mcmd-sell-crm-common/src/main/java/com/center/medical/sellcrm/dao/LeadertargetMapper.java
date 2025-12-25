package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Leadertarget;
import com.center.medical.sellcrm.bean.param.LeadertargetParam;
import com.center.medical.sellcrm.bean.vo.LTSummaryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 领导目标表(Leadertarget)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:36
 */
public interface LeadertargetMapper extends BaseMapper<Leadertarget> {

    /**
     * 分页查询[领导目标表]列表
     *
     * @param page  分页参数
     * @param leadertargetParam Leadertarget查询参数
     * @return 分页数据
     */
    IPage<Leadertarget> getList(PageParam<Leadertarget> page, @Param("param") LeadertargetParam leadertargetParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Leadertarget getInfoById(@Param("id") String id);


    /**
     * 查询导出的销售年度目标数据
     * @param leadertargetParam
     * @return
     */
    List<Leadertarget> getExportData(@Param("param")LeadertargetParam leadertargetParam);

    /**
     * 获取总结数据
     * @param param
     * @return
     */
    List<LTSummaryVo> getSummaryData(@Param("param")LeadertargetParam param);
}
