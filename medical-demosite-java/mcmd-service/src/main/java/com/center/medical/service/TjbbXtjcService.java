package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.TjbbXtjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS血糖检测体检报表(TjbbXtjc)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:32
 */
public interface TjbbXtjcService extends IService<TjbbXtjc> {

    /**
     * 分页查询[KS血糖检测体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbXtjc查询参数
     * @return 分页数据
     */
    IPage<TjbbXtjc> getList(PageParam<TjbbXtjc> page, TjbbXtjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TjbbXtjc getInfoById(String id);

}

