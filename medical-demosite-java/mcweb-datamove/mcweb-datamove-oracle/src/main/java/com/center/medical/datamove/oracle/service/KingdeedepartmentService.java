package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Kingdeedepartment;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (Kingdeedepartment)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:19:26
 */
public interface KingdeedepartmentService extends IService<Kingdeedepartment> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Kingdeedepartment> getPage(PageParam<Kingdeedepartment> page, Kingdeedepartment param);


}

