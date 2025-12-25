package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Selltarget;
import com.center.medical.sellcrm.bean.param.SellDateParam;
import com.center.medical.sellcrm.bean.param.SelltargetParam;
import com.center.medical.sellcrm.bean.vo.SellDateMonthVo;
import com.center.medical.sellcrm.bean.vo.SellDateQuarterVo;
import com.center.medical.sellcrm.bean.vo.SellDateVo;
import com.center.medical.sellcrm.bean.vo.SelltargetVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * XS销售目标(Selltarget)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:23
 */
public interface SelltargetMapper extends BaseMapper<Selltarget> {

    /**
     * 分页查询[XS销售目标]列表
     *
     * @param page  分页参数
     * @param selltargetParam Selltarget查询参数
     * @return 分页数据
     */
    IPage<SelltargetVo> getPage(PageParam<SelltargetVo> page, @Param("param") SelltargetParam selltargetParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Selltarget getInfoById(@Param("id") String id);

    /**
     * 获取总结数据
     * @param selltargetParam
     * @return
     */
    List<SelltargetVo> getSummaryData(@Param("param")SelltargetParam selltargetParam);

    /**
     * 不分页查全部
     * @param selltargetParam
     * @return
     */
    List<SelltargetVo> getAllList(@Param("param")SelltargetParam selltargetParam);

    /**
     * 获取销售同期对比年
     * @param param
     * @return
     */
    IPage<SellDateVo> getSellDateYear(PageParam<SellDateVo> page,@Param("param") SellDateParam param);

    /**
     * 获取销售同期对季度
     * @param param
     * @return
     */
    IPage<SellDateQuarterVo> getSellDateQuarter(PageParam<SellDateQuarterVo> page, @Param("param") SellDateParam param);

    /**
     * 获取销售同期对月
     * @param param
     * @return
     */
    IPage<SellDateMonthVo> getSellDateMonth(PageParam<SellDateMonthVo> page, @Param("param") SellDateParam param);
}
