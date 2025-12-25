package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Dlqk;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS锻炼情况(Dlqk)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:18:18
 */
public interface DlqkService extends IService<Dlqk> {

    /**
     * 分页查询[KS锻炼情况]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Dlqk> getPage(PageParam<Dlqk> page, Dlqk param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Dlqk getInfoById(String id);

}

