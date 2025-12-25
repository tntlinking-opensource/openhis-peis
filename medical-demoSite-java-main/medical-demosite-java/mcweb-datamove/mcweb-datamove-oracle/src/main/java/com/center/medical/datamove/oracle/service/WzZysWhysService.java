package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.WzZysWhys;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊—职业史—危害因素(WzZysWhys)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:30:53
 */
public interface WzZysWhysService extends IService<WzZysWhys> {

    /**
     * 分页查询[KS问诊—职业史—危害因素]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WzZysWhys> getPage(PageParam<WzZysWhys> page, WzZysWhys param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzZysWhys getInfoById(String id);

}

