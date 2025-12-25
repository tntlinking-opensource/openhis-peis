package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.TjbbGmd;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS骨密度体检报表(TjbbGmd)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:38
 */
public interface TjbbGmdService extends IService<TjbbGmd> {

    /**
     * 分页查询[KS骨密度体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbGmd查询参数
     * @return 分页数据
     */
    IPage<TjbbGmd> getList(PageParam<TjbbGmd> page, TjbbGmd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TjbbGmd getInfoById(String id);

}

