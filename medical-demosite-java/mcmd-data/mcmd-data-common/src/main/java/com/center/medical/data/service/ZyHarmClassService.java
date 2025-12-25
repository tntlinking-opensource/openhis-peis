package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZyHarmClass;

/**
 * 职业危害因素分类(ZyHarmClass)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:00
 */
public interface ZyHarmClassService extends IService<ZyHarmClass> {

    /**
     * 分页查询[职业危害因素分类]列表
     *
     * @param page  分页参数
     * @param param ZyHarmClass查询参数
     * @return 分页数据
     */
    IPage<ZyHarmClass> getList(PageParam<ZyHarmClass> page, ZyHarmClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    ZyHarmClass getInfoById(String id);

    /**
     * 添加或修改
     * @param zyHarmClass
     * @return
     */
    String saveOrUpdateDwHarm(ZyHarmClass zyHarmClass);


    /**
     * 获得所有的职业危害因素
     * @param page
     * @param inputCode
     * @return
     */
    IPage<ZyHarmClass> findAllHarmclass(PageParam<ZyHarmClass> page, String inputCode);

}

