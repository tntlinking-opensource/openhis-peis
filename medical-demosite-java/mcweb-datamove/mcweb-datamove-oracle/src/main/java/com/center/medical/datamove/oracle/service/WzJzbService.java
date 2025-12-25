package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.WzJzb;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——家族病(WzJzb)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:30:50
 */
public interface WzJzbService extends IService<WzJzb> {

    /**
     * 分页查询[KS问诊——家族病]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WzJzb> getPage(PageParam<WzJzb> page, WzJzb param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzJzb getInfoById(String id);

}

