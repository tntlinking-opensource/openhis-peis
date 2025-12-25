package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatienthistory;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者（history）表(Peispatienthistory)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
public interface PeispatienthistoryService extends IService<Peispatienthistory> {

    /**
     * 分页查询[体检者（history）表]列表
     *
     * @param page  分页参数
     * @param param Peispatienthistory查询参数
     * @return 分页数据
     */
    IPage<Peispatienthistory> getList(PageParam<Peispatienthistory> page, Peispatienthistory param);


    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    Peispatienthistory getInForById(String id);


}

