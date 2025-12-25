package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdWsjg;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 外送机构(MdWsjg)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:22
 */
public interface MdWsjgService extends IService<MdWsjg> {

    /**
     * 分页查询[外送机构]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdWsjg> getPage(PageParam<MdWsjg> page, MdWsjg param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWsjg getInfoById(String id);

}

