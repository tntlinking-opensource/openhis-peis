package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.PositiveQueryParam;
import com.center.medical.statistics.bean.vo.PositiveQueryVo;

import java.util.List;

/**
 * 职业健康检查结果结论附表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface PositiveQueryService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PositiveQueryVo> getList(PageParam<PositiveQueryVo> page, PositiveQueryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 导出单位职业健康检查结果附表
     * @param param
     * @return
     */
    List<PositiveQueryVo> getExportData(PositiveQueryParam param);

    /**
     * 获取检查项目
     * @param obj
     * @return
     */
    String getHarmStr(Object obj);
}

