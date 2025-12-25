package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Financeinput;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 销售财务录入表(Financeinput)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:02
 */
public interface FinanceinputService extends IService<Financeinput> {

    /**
     * 分页查询[销售财务录入表]列表
     *
     * @param page  分页参数
     * @param param Financeinput查询参数
     * @return 分页数据
     */
    IPage<Financeinput> getList(PageParam<Financeinput> page, Financeinput param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Financeinput getInfoById(String id);

}

