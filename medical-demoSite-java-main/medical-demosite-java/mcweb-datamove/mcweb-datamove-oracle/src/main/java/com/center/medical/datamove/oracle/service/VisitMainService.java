package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.VisitMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:52
 */
public interface VisitMainService extends IService<VisitMain> {

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<VisitMain> getPage(PageParam<VisitMain> page, VisitMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    VisitMain getInfoById(String id);

}

