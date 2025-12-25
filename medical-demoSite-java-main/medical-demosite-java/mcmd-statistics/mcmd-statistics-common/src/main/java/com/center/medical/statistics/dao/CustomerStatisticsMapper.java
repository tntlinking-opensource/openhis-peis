package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.CustomerStatisticsParam;
import com.center.medical.statistics.bean.param.PhysicalExaminerParam;
import com.center.medical.statistics.bean.vo.CustomerStatisticsVo;
import com.center.medical.statistics.bean.vo.PhysicalExaminerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 来检客户统计(Peispatient)数据库访问层
 *
 * @author ay
 * @since 2023-12-18 15:52:57
 */
public interface CustomerStatisticsMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<CustomerStatisticsVo> getPage(PageParam<CustomerStatisticsVo> page, @Param("param") CustomerStatisticsParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 获取体检者
     * @param page
     * @param param
     * @return
     */
    IPage<PhysicalExaminerVo> getPhysicalExaminer(PageParam<PhysicalExaminerVo> page, @Param("param") PhysicalExaminerParam param);

    /**
     * 导出订单信息
     * @param param
     * @return
     */
    List<CustomerStatisticsVo> exportOrg( @Param("param") CustomerStatisticsParam param);

    /**
     * 导出人员信息
     * @param param
     * @return
     */
    List<PhysicalExaminerVo> exportPer( @Param("param") PhysicalExaminerParam param);
}
