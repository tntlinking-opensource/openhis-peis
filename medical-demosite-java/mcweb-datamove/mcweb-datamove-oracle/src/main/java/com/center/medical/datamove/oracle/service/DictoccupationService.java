package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Dictoccupation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业表(Dictoccupation)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:13
 */
public interface DictoccupationService extends IService<Dictoccupation> {

    /**
     * 分页查询[JC职业表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Dictoccupation> getPage(PageParam<Dictoccupation> page, Dictoccupation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Dictoccupation getInfoById(String id);

}

