package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.HealthResultParam;
import com.center.medical.statistics.bean.vo.HealthResultVo;

import java.util.List;

/**
 * QT体检者表(Peispatient)服务接口
 *
 * @author ay
 * @since 2023-10-25 09:06:03
 */
public interface HealthResultService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<HealthResultVo> getPage(PageParam<HealthResultVo> page, HealthResultParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 导出健康检查结果附表
     * @param param
     * @return
     */
    List<HealthResultVo> getExportData(HealthResultParam param);
}

