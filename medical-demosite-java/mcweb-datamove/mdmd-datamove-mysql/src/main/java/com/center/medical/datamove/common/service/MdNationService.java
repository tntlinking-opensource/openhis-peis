package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdNation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC民族(MdNation)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
public interface MdNationService extends IService<MdNation> {

    /**
     * 分页查询[JC民族]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdNation> getPage(PageParam<MdNation> page, MdNation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdNation getInfoById(String id);

}

