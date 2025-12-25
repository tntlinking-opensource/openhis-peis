package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdGroupAndFzx;

import java.util.List;

/**
 * 分组分中心(MdGroupAndFzx)服务接口
 *
 * @author ay
 * @since 2024-04-11 10:49:53
 */
public interface MdGroupAndFzxService extends IService<MdGroupAndFzx> {

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdGroupAndFzx> getPage(PageParam<MdGroupAndFzx> page, MdGroupAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdGroupAndFzx getInfoById(String id);

    /**
     * 通过分组id和分中心id查询
     * @param groupId
     * @param fzxId
     * @return
     */
    MdGroupAndFzx getByGroupIdAndFzx(String groupId, String fzxId);

    /**
     * 批量插入
     * @param mdGroupAndFzxList
     */
    void saOrUpList(List<MdGroupAndFzx> mdGroupAndFzxList);

    /**
     * 通过分组id查询
     * @param groupId
     * @return
     */
    List<MdGroupAndFzx> getByGroupId(String groupId);
}

