package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Printtype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 销售打印分类设置(Printtype)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:23:38
 */
public interface PrinttypeService extends IService<Printtype> {

    /**
     * 分页查询[销售打印分类设置]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Printtype> getPage(PageParam<Printtype> page, Printtype param);


}

