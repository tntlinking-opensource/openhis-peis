package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdTjbbXzjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS血脂检测体检报表(MdTjbbXzjc)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:06
 */
public interface MdTjbbXzjcService extends IService<MdTjbbXzjc> {

    /**
     * 分页查询[KS血脂检测体检报表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdTjbbXzjc> getPage(PageParam<MdTjbbXzjc> page, MdTjbbXzjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjbbXzjc getInfoById(String id);

}

