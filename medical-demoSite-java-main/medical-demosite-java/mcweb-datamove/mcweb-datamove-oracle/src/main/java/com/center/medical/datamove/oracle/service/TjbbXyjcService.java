package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.TjbbXyjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS血压检测(TjbbXyjc)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:28
 */
public interface TjbbXyjcService extends IService<TjbbXyjc> {

    /**
     * 分页查询[KS血压检测]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TjbbXyjc> getPage(PageParam<TjbbXyjc> page, TjbbXyjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TjbbXyjc getInfoById(String id);

}

