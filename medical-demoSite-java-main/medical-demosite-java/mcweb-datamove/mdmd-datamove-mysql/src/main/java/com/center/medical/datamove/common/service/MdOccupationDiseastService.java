package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdOccupationDiseast;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业病名称(MdOccupationDiseast)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
public interface MdOccupationDiseastService extends IService<MdOccupationDiseast> {

    /**
     * 分页查询[JC职业病名称]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdOccupationDiseast> getPage(PageParam<MdOccupationDiseast> page, MdOccupationDiseast param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOccupationDiseast getInfoById(String id);

}

