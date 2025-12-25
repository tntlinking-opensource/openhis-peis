package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ISListDataParam;
import com.center.medical.finance.bean.param.ISPageParam;
import com.center.medical.finance.bean.vo.ExportOneVo;
import com.center.medical.finance.bean.vo.ISListDataVo;
import com.center.medical.finance.bean.vo.ISPageVo;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-06 10:40:40
 */
public interface IndividualStatisticsService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ISPageVo> getList(PageParam<ISPageVo> page, ISPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 导出个检销售统计
     *
     * @param param
     * @return
     */
    List<ExportOneVo> getExportData(ISPageParam param);

    /**
     * 导出多条
     *
     * @param param
     * @return
     */
    List<ISPageVo> getExportsData(ISPageParam param);

    /**
     * 获取关联的数据
     * @param page
     * @param param
     * @return
     */
    IPage<ISListDataVo> getListData(PageParam<ISListDataVo> page, ISListDataParam param);

    /**
     * 导出右侧关联数据
     * @param param
     * @return
     */
    List<ISListDataVo> exportListData(ISListDataParam param);
}

