package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientAndFzx;

import java.util.List;

/**
 * (PeispatientAndFzx)服务接口
 *
 * @author ay
 * @since 2024-04-17 10:37:32
 */
public interface OrPeispatientAndFzxService extends IService<OrPeispatientAndFzx> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrPeispatientAndFzx> getPage(PageParam<OrPeispatientAndFzx> page, OrPeispatientAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatientAndFzx getInfoById(String id);

    /**
     * 通过体检者id查询
     * @param pid
     * @return
     */
    List<OrPeispatientAndFzx> getByPid(String pid);
}

