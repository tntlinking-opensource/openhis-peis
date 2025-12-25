package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdZyOccupationOrg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业病体检机构(MdZyOccupationOrg)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
public interface MdZyOccupationOrgService extends IService<MdZyOccupationOrg> {

    /**
     * 分页查询[JC职业病体检机构]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdZyOccupationOrg> getPage(PageParam<MdZyOccupationOrg> page, MdZyOccupationOrg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键orgCode
     * @return 详情信息
     */
    MdZyOccupationOrg getInfoById(String id);

}

