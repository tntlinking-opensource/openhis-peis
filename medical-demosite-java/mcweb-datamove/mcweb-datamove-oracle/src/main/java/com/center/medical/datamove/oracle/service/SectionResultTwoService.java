package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.SectionResultTwo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS科室检查结果表————结论词、小结(SectionResultTwo)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:46
 */
public interface SectionResultTwoService extends IService<SectionResultTwo> {

    /**
     * 分页查询[KS科室检查结果表————结论词、小结]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SectionResultTwo> getPage(PageParam<SectionResultTwo> page, SectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionResultTwo getInfoById(String id);

}

