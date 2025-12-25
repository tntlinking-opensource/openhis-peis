package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPeispatienthistory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者（history）表(MdPeispatienthistory)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
public interface MdPeispatienthistoryService extends IService<MdPeispatienthistory> {

    /**
     * 分页查询[体检者（history）表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatienthistory> getPage(PageParam<MdPeispatienthistory> page, MdPeispatienthistory param);


}

