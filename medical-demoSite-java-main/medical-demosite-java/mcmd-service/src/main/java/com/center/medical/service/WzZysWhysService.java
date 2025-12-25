package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WzZysWhys;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊—职业史—危害因素(WzZysWhys)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:05
 */
public interface WzZysWhysService extends IService<WzZysWhys> {

    /**
     * 分页查询[KS问诊—职业史—危害因素]列表
     *
     * @param page  分页参数
     * @param param WzZysWhys查询参数
     * @return 分页数据
     */
    IPage<WzZysWhys> getList(PageParam<WzZysWhys> page, WzZysWhys param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzZysWhys getInfoById(String id);

}

