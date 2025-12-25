package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.WzZys;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——职业史(WzZys)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:30:53
 */
public interface WzZysService extends IService<WzZys> {

    /**
     * 分页查询[KS问诊——职业史]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WzZys> getPage(PageParam<WzZys> page, WzZys param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzZys getInfoById(String id);

}

