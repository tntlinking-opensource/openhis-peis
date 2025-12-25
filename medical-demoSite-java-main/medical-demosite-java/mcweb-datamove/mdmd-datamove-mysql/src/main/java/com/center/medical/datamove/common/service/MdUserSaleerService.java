package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdUserSaleer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 用户-销售关联表 (MdUserSaleer)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:25
 */
public interface MdUserSaleerService extends IService<MdUserSaleer> {

    /**
     * 分页查询[用户-销售关联表 ]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdUserSaleer> getPage(PageParam<MdUserSaleer> page, MdUserSaleer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUserSaleer getInfoById(String id);

}

