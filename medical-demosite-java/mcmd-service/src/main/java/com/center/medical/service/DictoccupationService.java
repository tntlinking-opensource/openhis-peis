package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Dictoccupation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 职业类型表(Dictoccupation)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:19
 */
public interface DictoccupationService extends IService<Dictoccupation> {

    /**
     * 分页查询[职业类型表]列表
     *
     * @param page  分页参数
     * @param param Dictoccupation查询参数
     * @return 分页数据
     */
    IPage<Dictoccupation> getList(PageParam<Dictoccupation> page, Dictoccupation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Dictoccupation getInfoById(String id);

}

