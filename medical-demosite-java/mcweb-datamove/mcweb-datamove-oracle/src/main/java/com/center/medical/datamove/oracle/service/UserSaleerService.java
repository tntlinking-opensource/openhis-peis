package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.UserSaleer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (UserSaleer)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:25:45
 */
public interface UserSaleerService extends IService<UserSaleer> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<UserSaleer> getPage(PageParam<UserSaleer> page, UserSaleer param);


}

