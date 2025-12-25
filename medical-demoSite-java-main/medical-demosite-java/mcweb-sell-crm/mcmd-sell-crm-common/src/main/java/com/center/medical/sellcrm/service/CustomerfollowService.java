package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Customerfollow;
import com.center.medical.sellcrm.bean.param.CustomerfollowPram;

import java.util.List;

/**
 * 客户跟踪表(Customerfollow)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:48
 */
public interface CustomerfollowService extends IService<Customerfollow> {

    /**
     * 分页查询阶段跟踪列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Customerfollow> getPage(PageParam<Customerfollow> page, CustomerfollowPram param);

    /**
     * 获取指定客户跟踪记录
     *
     * @param cumId 客户ID
     * @return 所有数据
     */
    List<Customerfollow> getListByCumId(String cumId);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Customerfollow getInfoById(String id);

    /**
     * 新增/编辑操作
     *
     * @param customerfollow
     * @return
     */
    Boolean saOrUp(Customerfollow customerfollow);
}

