package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdTjbbXtjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS血糖检测体检报表(MdTjbbXtjc)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:03
 */
public interface MdTjbbXtjcService extends IService<MdTjbbXtjc> {

    /**
     * 分页查询[KS血糖检测体检报表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdTjbbXtjc> getPage(PageParam<MdTjbbXtjc> page, MdTjbbXtjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTjbbXtjc getInfoById(String id);

}

