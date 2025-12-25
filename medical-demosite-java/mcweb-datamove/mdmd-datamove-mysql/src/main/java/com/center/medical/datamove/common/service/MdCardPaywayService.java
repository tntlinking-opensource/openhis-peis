package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdCardPayway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 卡办理收款方式表(MdCardPayway)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
public interface MdCardPaywayService extends IService<MdCardPayway> {

    /**
     * 分页查询[卡办理收款方式表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCardPayway> getPage(PageParam<MdCardPayway> page, MdCardPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCardPayway getInfoById(String id);

}

