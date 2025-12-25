package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdZyHarmClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 职业危害因素分类(MdZyHarmClass)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:29
 */
public interface MdZyHarmClassService extends IService<MdZyHarmClass> {

    /**
     * 分页查询[职业危害因素分类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdZyHarmClass> getPage(PageParam<MdZyHarmClass> page, MdZyHarmClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdZyHarmClass getInfoById(String id);

}

