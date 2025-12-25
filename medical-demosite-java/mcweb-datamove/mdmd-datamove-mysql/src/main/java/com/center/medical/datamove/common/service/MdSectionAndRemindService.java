package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSectionAndRemind;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 科室提醒和科室关联表(MdSectionAndRemind)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:30
 */
public interface MdSectionAndRemindService extends IService<MdSectionAndRemind> {

    /**
     * 分页查询[科室提醒和科室关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSectionAndRemind> getPage(PageParam<MdSectionAndRemind> page, MdSectionAndRemind param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionAndRemind getInfoById(String id);

}

