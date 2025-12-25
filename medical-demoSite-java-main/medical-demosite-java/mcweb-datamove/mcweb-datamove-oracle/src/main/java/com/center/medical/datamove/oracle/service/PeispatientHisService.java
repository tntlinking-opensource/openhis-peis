package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.PeispatientHis;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * QT体检者表(PeispatientHis)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:21
 */
public interface PeispatientHisService extends IService<PeispatientHis> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeispatientHis> getPage(PageParam<PeispatientHis> page, PeispatientHis param);


}

