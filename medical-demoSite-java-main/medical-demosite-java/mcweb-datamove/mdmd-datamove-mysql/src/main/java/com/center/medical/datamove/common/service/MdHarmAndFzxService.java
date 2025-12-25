package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdHarmAndFzx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 危害因素和分中心(MdHarmAndFzx)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
public interface MdHarmAndFzxService extends IService<MdHarmAndFzx> {

    /**
     * 分页查询[危害因素和分中心]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdHarmAndFzx> getPage(PageParam<MdHarmAndFzx> page, MdHarmAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdHarmAndFzx getInfoById(String id);

}

