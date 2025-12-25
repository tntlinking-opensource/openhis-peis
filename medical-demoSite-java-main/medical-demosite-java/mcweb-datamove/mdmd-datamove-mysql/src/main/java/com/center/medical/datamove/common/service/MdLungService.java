package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdLung;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS肺功能(MdLung)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:25
 */
public interface MdLungService extends IService<MdLung> {

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdLung> getPage(PageParam<MdLung> page, MdLung param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdLung getInfoById(String id);

}

