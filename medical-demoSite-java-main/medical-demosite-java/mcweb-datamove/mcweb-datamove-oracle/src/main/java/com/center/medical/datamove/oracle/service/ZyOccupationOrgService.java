package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.ZyOccupationOrg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业病体检机构(ZyOccupationOrg)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:31:00
 */
public interface ZyOccupationOrgService extends IService<ZyOccupationOrg> {

    /**
     * 分页查询[JC职业病体检机构]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ZyOccupationOrg> getPage(PageParam<ZyOccupationOrg> page, ZyOccupationOrg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键orgCode
     * @return 详情信息
     */
    ZyOccupationOrg getInfoById(Object id);

}

