package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Carmanage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检车管理(Carmanage)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:35
 */
public interface CarmanageService extends IService<Carmanage> {

    /**
     * 分页查询[体检车管理]列表
     *
     * @param page  分页参数
     * @param param Carmanage查询参数
     * @return 分页数据
     */
    IPage<Carmanage> getList(PageParam<Carmanage> page, Carmanage param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Carmanage getInfoById(String id);

}

