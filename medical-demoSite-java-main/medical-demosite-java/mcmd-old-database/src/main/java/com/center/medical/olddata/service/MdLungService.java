package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdLung;

/**
 * KS肺功能(MdLung)服务接口
 *
 * @author ay
 * @since 2024-06-05 16:02:44
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

