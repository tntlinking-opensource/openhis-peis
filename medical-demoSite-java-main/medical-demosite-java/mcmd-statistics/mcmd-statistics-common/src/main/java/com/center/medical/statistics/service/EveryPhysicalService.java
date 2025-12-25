package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.vo.EPTotalVo;
import com.center.medical.statistics.bean.vo.EveryPhysicalVo;

import java.util.List;

/**
 * 每日体检量统计(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface EveryPhysicalService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<EveryPhysicalVo> getList(PageParam<EveryPhysicalVo> page, BaseParam param);

    /**
     * 导出每日体检量统计
     * @param param
     * @return
     */
    List<EveryPhysicalVo> exportData(BaseParam param);

    /**
     * 页面下方总计数据
     * @param param
     * @return
     */
    EPTotalVo getTotal(BaseParam param);
}

