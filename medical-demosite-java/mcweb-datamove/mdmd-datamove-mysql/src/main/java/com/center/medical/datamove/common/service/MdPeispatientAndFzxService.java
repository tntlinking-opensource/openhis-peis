package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPeispatientAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 分组分中心(MdPeispatientAndFzx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:11
 */
public interface MdPeispatientAndFzxService extends IService<MdPeispatientAndFzx> {

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientAndFzx> getPage(PageParam<MdPeispatientAndFzx> page, MdPeispatientAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientAndFzx getInfoById(String id);

}

