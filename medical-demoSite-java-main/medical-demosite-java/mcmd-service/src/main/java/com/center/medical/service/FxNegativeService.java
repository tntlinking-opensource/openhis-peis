package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.FxNegative;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 综合分析-阴性小结(FxNegative)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:40
 */
public interface FxNegativeService extends IService<FxNegative> {

    /**
     * 分页查询[综合分析-阴性小结]列表
     *
     * @param page  分页参数
     * @param param FxNegative查询参数
     * @return 分页数据
     */
    IPage<FxNegative> getList(PageParam<FxNegative> page, FxNegative param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    FxNegative getInfoById(String id);

}

