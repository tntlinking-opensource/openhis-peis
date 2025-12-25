package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSectionResultTwo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS科室检查结果表-结论词、小结(MdSectionResultTwo)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:36
 */
public interface MdSectionResultTwoService extends IService<MdSectionResultTwo> {

    /**
     * 分页查询[KS科室检查结果表-结论词、小结]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSectionResultTwo> getPage(PageParam<MdSectionResultTwo> page, MdSectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionResultTwo getInfoById(String id);

}

