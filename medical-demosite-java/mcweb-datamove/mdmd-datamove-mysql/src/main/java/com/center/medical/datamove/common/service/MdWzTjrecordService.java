package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdWzTjrecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——体检记录(MdWzTjrecord)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:26
 */
public interface MdWzTjrecordService extends IService<MdWzTjrecord> {

    /**
     * 分页查询[KS问诊——体检记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdWzTjrecord> getPage(PageParam<MdWzTjrecord> page, MdWzTjrecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzTjrecord getInfoById(String id);

}

