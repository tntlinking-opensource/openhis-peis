package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFxPersonnelview;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 综合分析-人员一览表(MdFxPersonnelview)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
public interface MdFxPersonnelviewService extends IService<MdFxPersonnelview> {

    /**
     * 分页查询[综合分析-人员一览表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFxPersonnelview> getPage(PageParam<MdFxPersonnelview> page, MdFxPersonnelview param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxPersonnelview getInfoById(String id);

}

