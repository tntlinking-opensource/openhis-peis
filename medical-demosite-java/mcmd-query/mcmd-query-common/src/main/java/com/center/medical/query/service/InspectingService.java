package com.center.medical.query.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.InspectingParam;
import com.center.medical.query.bean.vo.InspectingVo;
import com.center.medical.query.bean.vo.LoadFormVo;

import java.util.List;

/**
 * 在检人员信息(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface InspectingService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<InspectingVo> getList(PageParam<InspectingVo> page, InspectingParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    LoadFormVo getInfoById(String id);

    /**
     * 导出在检人员名单 数据
     *
     * @param param
     * @return
     */
    List<InspectingVo> getExportData(InspectingParam param);
}

