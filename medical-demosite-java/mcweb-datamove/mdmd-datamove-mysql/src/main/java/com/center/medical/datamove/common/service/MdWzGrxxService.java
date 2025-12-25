package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdWzGrxx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——个人基本信息(MdWzGrxx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:23
 */
public interface MdWzGrxxService extends IService<MdWzGrxx> {

    /**
     * 分页查询[KS问诊——个人基本信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdWzGrxx> getPage(PageParam<MdWzGrxx> page, MdWzGrxx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzGrxx getInfoById(String id);

}

