package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.BaseAdiconExamItemCode;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * (BaseAdiconExamItemCode)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:12:25
 */
public interface BaseAdiconExamItemCodeService extends IService<BaseAdiconExamItemCode> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<BaseAdiconExamItemCode> getPage(PageParam<BaseAdiconExamItemCode> page, BaseAdiconExamItemCode param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseAdiconExamItemCode getInfoById(String id);

}

