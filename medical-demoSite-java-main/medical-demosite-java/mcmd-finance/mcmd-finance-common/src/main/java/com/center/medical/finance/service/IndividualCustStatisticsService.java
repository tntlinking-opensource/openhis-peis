package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ICPageParam;
import com.center.medical.finance.bean.vo.ICPageVo;

import java.util.List;

/**
 * 客服销售统计(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-06 16:22:37
 */
public interface IndividualCustStatisticsService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ICPageVo> getList(PageParam<ICPageVo> page, ICPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 导出客服销售统计
     *
     * @param param
     * @return
     */
    List<ICPageVo> getExportData(ICPageParam param);
}

