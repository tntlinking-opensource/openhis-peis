package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.ZyFhcsGrClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC个人防护用品种类(ZyFhcsGrClass)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:48
 */
public interface ZyFhcsGrClassService extends IService<ZyFhcsGrClass> {

    /**
     * 分页查询[JC个人防护用品种类]列表
     *
     * @param page  分页参数
     * @param param ZyFhcsGrClass查询参数
     * @return 分页数据
     */
    IPage<ZyFhcsGrClass> getList(PageParam<ZyFhcsGrClass> page, ZyFhcsGrClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ZyFhcsGrClass getInfoById(String id);

}

