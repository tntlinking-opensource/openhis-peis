package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrVationAndFzx;

import java.util.List;

/**
 * 团体任务分中心（不会被同步）(MdVationAndFzx)服务接口
 *
 * @author ay
 * @since 2024-04-11 11:00:25
 */
public interface OrVationAndFzxService extends IService<OrVationAndFzx> {

    /**
     * 分页查询[团体任务分中心（不会被同步）]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrVationAndFzx> getPage(PageParam<OrVationAndFzx> page, OrVationAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrVationAndFzx getInfoById(String id);

    /**
     * 通过任务id和分中心id查询
     * @param vationId
     * @param fzxId
     * @return
     */
    OrVationAndFzx getByVationIdAndFzx(String vationId, String fzxId);

    /**
     * 通过任务id查询
     * @param vationId
     * @return
     */
    List<OrVationAndFzx> getByVationId(String vationId);
}

