package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdVisitMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(MdVisitMain)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:30
 */
public interface MdVisitMainService extends IService<MdVisitMain> {

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdVisitMain> getPage(PageParam<MdVisitMain> page, MdVisitMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdVisitMain getInfoById(String id);

}

