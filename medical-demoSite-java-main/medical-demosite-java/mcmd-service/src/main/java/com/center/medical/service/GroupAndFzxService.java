package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.GroupAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 分组分中心(GroupAndFzx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:14
 */
public interface GroupAndFzxService extends IService<GroupAndFzx> {

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param GroupAndFzx查询参数
     * @return 分页数据
     */
    IPage<GroupAndFzx> getList(PageParam<GroupAndFzx> page, GroupAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    GroupAndFzx getInfoById(String id);

}

