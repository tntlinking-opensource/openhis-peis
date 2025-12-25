package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPacsResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * PACS-数据(MdPacsResult)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:35
 */
public interface MdPacsResultService extends IService<MdPacsResult> {

    /**
     * 分页查询[PACS-数据]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPacsResult> getPage(PageParam<MdPacsResult> page, MdPacsResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsResult getInfoById(String id);

}

