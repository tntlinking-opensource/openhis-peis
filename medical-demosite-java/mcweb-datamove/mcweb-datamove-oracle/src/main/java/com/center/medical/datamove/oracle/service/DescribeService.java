package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Describe;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS科室描述、检查结果表(Describe)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:09
 */
public interface DescribeService extends IService<Describe> {

    /**
     * 分页查询[KS科室描述、检查结果表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Describe> getPage(PageParam<Describe> page, Describe param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Describe getInfoById(String id);

}

