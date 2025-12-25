package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdFxPositive;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 综合分析-阳性小结(MdFxPositive)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:19
 */
public interface MdFxPositiveService extends IService<MdFxPositive> {

    /**
     * 分页查询[综合分析-阳性小结]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdFxPositive> getPage(PageParam<MdFxPositive> page, MdFxPositive param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFxPositive getInfoById(String id);

}

