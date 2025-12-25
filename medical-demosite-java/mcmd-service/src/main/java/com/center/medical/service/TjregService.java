package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Tjreg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS一般检查(Tjreg)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:29
 */
public interface TjregService extends IService<Tjreg> {

    /**
     * 分页查询[KS一般检查]列表
     *
     * @param page  分页参数
     * @param param Tjreg查询参数
     * @return 分页数据
     */
    IPage<Tjreg> getList(PageParam<Tjreg> page, Tjreg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Tjreg getInfoById(String id);

}

