package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Carmanage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检车管理(Carmanage)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:17:36
 */
public interface CarmanageService extends IService<Carmanage> {

    /**
     * 分页查询[体检车管理]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Carmanage> getPage(PageParam<Carmanage> page, Carmanage param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Carmanage getInfoById(String id);

}

