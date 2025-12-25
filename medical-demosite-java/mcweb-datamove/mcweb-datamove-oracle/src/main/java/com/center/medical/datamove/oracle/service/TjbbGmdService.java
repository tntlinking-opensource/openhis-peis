package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.TjbbGmd;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS骨密度体检报表(TjbbGmd)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:25
 */
public interface TjbbGmdService extends IService<TjbbGmd> {

    /**
     * 分页查询[KS骨密度体检报表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TjbbGmd> getPage(PageParam<TjbbGmd> page, TjbbGmd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TjbbGmd getInfoById(String id);

}

