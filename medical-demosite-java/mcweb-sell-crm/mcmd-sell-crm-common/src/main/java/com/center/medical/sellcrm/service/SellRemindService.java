package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.SellRemind;
import com.center.medical.sellcrm.bean.vo.SellRemindVo;

/**
 * 销售提醒(SellRemind)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:26
 */
public interface SellRemindService extends IService<SellRemind> {

    /**
     * 分页查询[销售提醒]列表
     *
     * @param page     分页参数
     * @param username 查询参数
     * @return 分页数据
     */
    IPage<SellRemindVo> getPage(PageParam<SellRemindVo> page, String username);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SellRemind getInfoById(String id);

}

