package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.DivisionWorkParam;
import com.center.medical.statistics.bean.vo.DivisionWorkVo;

import java.util.List;

/**
 * 科室工作量(Peispatientfeeitem)表服务接口
 *
 * @author ay
 * @since 2023-04-18 19:47:26
 */
public interface DivisionWorkloadService extends IService<Peispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DivisionWorkVo> getList(PageParam<DivisionWorkVo> page, DivisionWorkParam param);


    /**
     * 导出科室工作量统计
     * @param param
     * @return
     */
    List<DivisionWorkVo> exportData(DivisionWorkParam param);
}

