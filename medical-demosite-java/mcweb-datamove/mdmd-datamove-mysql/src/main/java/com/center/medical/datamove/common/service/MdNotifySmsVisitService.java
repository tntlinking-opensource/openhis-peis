package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdNotifySmsVisit;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 短信回访表(MdNotifySmsVisit)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
public interface MdNotifySmsVisitService extends IService<MdNotifySmsVisit> {

    /**
     * 分页查询[短信回访表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdNotifySmsVisit> getPage(PageParam<MdNotifySmsVisit> page, MdNotifySmsVisit param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdNotifySmsVisit getInfoById(String id);

}

