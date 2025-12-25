package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.TjbbXyjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS血压检测(TjbbXyjc)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:27
 */
public interface TjbbXyjcService extends IService<TjbbXyjc> {

    /**
     * 分页查询[KS血压检测]列表
     *
     * @param page  分页参数
     * @param param TjbbXyjc查询参数
     * @return 分页数据
     */
    IPage<TjbbXyjc> getList(PageParam<TjbbXyjc> page, TjbbXyjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TjbbXyjc getInfoById(String id);

}

