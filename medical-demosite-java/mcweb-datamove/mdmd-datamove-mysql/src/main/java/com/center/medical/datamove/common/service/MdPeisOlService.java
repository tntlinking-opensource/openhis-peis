package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPeisOl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者线上信息(MdPeisOl)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
public interface MdPeisOlService extends IService<MdPeisOl> {

    /**
     * 分页查询[体检者线上信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeisOl> getPage(PageParam<MdPeisOl> page, MdPeisOl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisOl getInfoById(String id);

}

