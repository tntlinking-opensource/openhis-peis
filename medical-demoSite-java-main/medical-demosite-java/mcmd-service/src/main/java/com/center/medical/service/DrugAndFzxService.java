package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.DrugAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 药品分中心映射(DrugAndFzx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:33
 */
public interface DrugAndFzxService extends IService<DrugAndFzx> {

    /**
     * 分页查询[药品分中心映射]列表
     *
     * @param page  分页参数
     * @param param DrugAndFzx查询参数
     * @return 分页数据
     */
    IPage<DrugAndFzx> getList(PageParam<DrugAndFzx> page, DrugAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrugAndFzx getInfoById(String id);

}

