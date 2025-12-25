package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.TotalComboParam;
import com.center.medical.statistics.bean.vo.TotalComboVo;

import java.util.List;

/**
 * 每月每日套餐统计(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface TotalComboService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TotalComboVo> getList(PageParam<TotalComboVo> page, TotalComboParam param);


    /**
     * 导出体检套餐统计
     * @param param
     * @return
     */
    List<TotalComboVo> getExportData(TotalComboParam param);
}

