package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.JiaXiangDto;
import com.center.medical.finance.bean.dto.VariableCostRateDto;
import com.center.medical.finance.bean.param.HSPageParam;
import com.center.medical.finance.bean.param.TotalListParam;
import com.center.medical.finance.bean.vo.HSPageVo;
import com.center.medical.finance.bean.vo.TotalListVo;
import com.center.medical.sellcrm.bean.model.Createorder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 销售提成核算-销售团检统计(Createorder)表数据库访问层
 *
 * @author ay
 * @since 2023-04-04 16:53:57
 */
public interface HealthyStatisticsMapper extends BaseMapper<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    IPage<HSPageVo> getList(PageParam<HSPageVo> page, @Param("param") HSPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(@Param("id") String id);

    /**
     * 查询统收费用
     *
     * @param order
     * @param param
     * @return
     */
    String getTongJi(@Param("ddh") String order, @Param("param") HSPageParam param);

    /**
     * 加项费用、加项人数
     *
     * @param order
     * @param param
     * @return
     */
    JiaXiangDto getJiaXiang(@Param("ddh") String order, @Param("param") HSPageParam param);

    /**
     * 查询右边列表
     *
     * @param param
     * @return
     */
    List<TotalListVo> getTotalList(@Param("param") TotalListParam param);

    /**
     * 导出销售团检统计
     *
     * @param param
     * @return
     */
    List<HSPageVo> getExportData(@Param("param") HSPageParam param);

    /**
     * 获取变动成本率
     * @param id
     * @return
     */
    List<VariableCostRateDto> getVariableCostRate(@Param("id") String id,@Param("taskId") String taskId);

    /**
     * 获取分组下的人数
     * @param id   任务id
     * @param tcid 套餐id
     * @return
     */
    Double getGroupCount(@Param("id") String id, @Param("tcid") String tcid);
}
