package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WzZybs;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——职业病史(WzZybs)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:48
 */
public interface WzZybsService extends IService<WzZybs> {

    /**
     * 分页查询[KS问诊——职业病史]列表
     *
     * @param page  分页参数
     * @param param WzZybs查询参数
     * @return 分页数据
     */
    IPage<WzZybs> getList(PageParam<WzZybs> page, WzZybs param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzZybs getInfoById(String id);

}

