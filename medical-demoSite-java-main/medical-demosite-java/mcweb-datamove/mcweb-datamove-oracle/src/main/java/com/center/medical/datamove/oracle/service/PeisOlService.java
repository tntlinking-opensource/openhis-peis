package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.PeisOl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者线上信息(PeisOl)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:57
 */
public interface PeisOlService extends IService<PeisOl> {

    /**
     * 分页查询[体检者线上信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<PeisOl> getPage(PageParam<PeisOl> page, PeisOl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeisOl getInfoById(String id);

}

