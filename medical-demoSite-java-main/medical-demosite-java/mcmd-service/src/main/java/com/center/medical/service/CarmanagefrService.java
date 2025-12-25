package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Carmanagefr;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检车与外出体检车上的人员关联表(Carmanagefr)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:06
 */
public interface CarmanagefrService extends IService<Carmanagefr> {

    /**
     * 分页查询[体检车与外出体检车上的人员关联表]列表
     *
     * @param page  分页参数
     * @param param Carmanagefr查询参数
     * @return 分页数据
     */
    IPage<Carmanagefr> getList(PageParam<Carmanagefr> page, Carmanagefr param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Carmanagefr getInfoById(String id);

}

