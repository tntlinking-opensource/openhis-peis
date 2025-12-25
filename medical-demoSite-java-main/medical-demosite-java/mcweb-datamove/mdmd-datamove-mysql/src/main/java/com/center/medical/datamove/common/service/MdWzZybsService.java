package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdWzZybs;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——职业病史(MdWzZybs)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:26
 */
public interface MdWzZybsService extends IService<MdWzZybs> {

    /**
     * 分页查询[KS问诊——职业病史]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdWzZybs> getPage(PageParam<MdWzZybs> page, MdWzZybs param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdWzZybs getInfoById(String id);

}

