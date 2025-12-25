package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFinanceinput;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 销售财务录入表(MdFinanceinput)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:17
 */
public interface MdFinanceinputService extends IService<MdFinanceinput> {

    /**
     * 分页查询[销售财务录入表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFinanceinput> getPage(PageParam<MdFinanceinput> page, MdFinanceinput param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFinanceinput getInfoById(String id);

}

