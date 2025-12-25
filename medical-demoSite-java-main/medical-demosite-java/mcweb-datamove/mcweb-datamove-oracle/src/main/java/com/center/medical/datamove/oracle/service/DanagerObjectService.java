package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.DanagerObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * TJ危害因素收费项目(DanagerObject)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:08
 */
public interface DanagerObjectService extends IService<DanagerObject> {

    /**
     * 分页查询[TJ危害因素收费项目]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DanagerObject> getPage(PageParam<DanagerObject> page, DanagerObject param);


}

