package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdSshy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 创建团体客户要选择的所属行业在这里维护(MdSshy)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:47
 */
public interface MdSshyService extends IService<MdSshy> {

    /**
     * 分页查询[创建团体客户要选择的所属行业在这里维护]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSshy> getPage(PageParam<MdSshy> page, MdSshy param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSshy getInfoById(String id);

}

