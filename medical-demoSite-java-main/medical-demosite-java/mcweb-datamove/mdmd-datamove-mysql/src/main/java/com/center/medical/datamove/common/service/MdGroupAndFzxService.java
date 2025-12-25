package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdGroupAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 分组分中心(MdGroupAndFzx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
public interface MdGroupAndFzxService extends IService<MdGroupAndFzx> {

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdGroupAndFzx> getPage(PageParam<MdGroupAndFzx> page, MdGroupAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdGroupAndFzx getInfoById(String id);

}

