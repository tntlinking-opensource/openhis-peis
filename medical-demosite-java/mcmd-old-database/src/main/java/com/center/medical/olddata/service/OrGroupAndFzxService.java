package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrGroupAndFzx;

import java.util.List;

/**
 * (GroupAndFzx)服务接口
 *
 * @author ay
 * @since 2024-04-11 10:44:32
 */
public interface OrGroupAndFzxService extends IService<OrGroupAndFzx> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrGroupAndFzx> getPage(PageParam<OrGroupAndFzx> page, OrGroupAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrGroupAndFzx getInfoById(String id);

    /**
     * 通过分组id和分中心id查询
     * @param groupId
     * @param fzxId
     * @return
     */
    OrGroupAndFzx getByGroupIdAndFzx(String groupId, String fzxId);

    /**
     * 通过分组id查询
     * @param groupId
     * @return
     */
    List<OrGroupAndFzx> getByGroupId(String groupId);
}

