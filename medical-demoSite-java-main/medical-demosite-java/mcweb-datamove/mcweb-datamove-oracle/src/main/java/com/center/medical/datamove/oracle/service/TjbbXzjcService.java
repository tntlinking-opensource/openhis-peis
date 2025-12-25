package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.TjbbXzjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS血脂检测体检报表(TjbbXzjc)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:30
 */
public interface TjbbXzjcService extends IService<TjbbXzjc> {

    /**
     * 分页查询[KS血脂检测体检报表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TjbbXzjc> getPage(PageParam<TjbbXzjc> page, TjbbXzjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TjbbXzjc getInfoById(String id);

}

