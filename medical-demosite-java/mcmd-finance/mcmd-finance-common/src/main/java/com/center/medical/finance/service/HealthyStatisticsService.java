package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.HSPageParam;
import com.center.medical.finance.bean.param.TotalListParam;
import com.center.medical.finance.bean.vo.HSPageVo;
import com.center.medical.finance.bean.vo.TotalListVo;
import com.center.medical.sellcrm.bean.model.Createorder;

import java.util.List;

/**
 * 销售提成核算-销售团检统计(Createorder)表服务接口
 *
 * @author ay
 * @since 2023-04-04 16:53:57
 */
public interface HealthyStatisticsService extends IService<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<HSPageVo> getList(PageParam<HSPageVo> page, HSPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(String id);

    /**
     * 查询右边列表
     *
     * @param param
     * @return
     */
    List<TotalListVo> getTotalList(TotalListParam param);

    /**
     * 导出销售团检统计
     *
     * @param param
     * @return
     */
    List<HSPageVo> getExportData(HSPageParam param);
}

