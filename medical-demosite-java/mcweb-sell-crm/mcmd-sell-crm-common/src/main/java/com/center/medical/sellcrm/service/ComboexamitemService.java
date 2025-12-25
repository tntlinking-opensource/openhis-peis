package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Comboexamitem;

/**
 * 用于判断职业小结(Comboexamitem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:26
 */
public interface ComboexamitemService extends IService<Comboexamitem> {

    /**
     * 分页查询[用于判断职业小结]列表
     *
     * @param page  分页参数
     * @param param Comboexamitem查询参数
     * @return 分页数据
     */
    IPage<Comboexamitem> getList(PageParam<Comboexamitem> page, Comboexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Comboexamitem getInfoById(String id);

}

