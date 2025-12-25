package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.ZyOccupationOrg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业病体检机构(ZyOccupationOrg)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:23
 */
public interface ZyOccupationOrgService extends IService<ZyOccupationOrg> {

    /**
     * 分页查询[JC职业病体检机构]列表
     *
     * @param page  分页参数
     * @param param ZyOccupationOrg查询参数
     * @return 分页数据
     */
    IPage<ZyOccupationOrg> getList(PageParam<ZyOccupationOrg> page, ZyOccupationOrg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id orgCode主键
     */
    ZyOccupationOrg getInfoById(String id);

}

