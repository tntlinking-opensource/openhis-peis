package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Financeinput;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 财务录入表(Financeinput)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:45
 */
public interface FinanceinputService extends IService<Financeinput> {

    /**
     * 分页查询[财务录入表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Financeinput> getPage(PageParam<Financeinput> page, Financeinput param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Financeinput getInfoById(String id);

}

