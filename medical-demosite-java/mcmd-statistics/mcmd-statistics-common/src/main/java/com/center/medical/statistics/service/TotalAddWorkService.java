package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.TotalAddWorkParam;
import com.center.medical.statistics.bean.vo.TotalAddWorkVo;

import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)表服务接口
 *
 * @author ay
 * @since 2023-04-19 19:06:09
 */
public interface TotalAddWorkService extends IService<Peispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TotalAddWorkVo> getList(PageParam<TotalAddWorkVo> page, TotalAddWorkParam param);


    /**
     * 导出加项统计
     * @param param
     * @return
     */
    List<TotalAddWorkVo> getExportData(TotalAddWorkParam param);
}

