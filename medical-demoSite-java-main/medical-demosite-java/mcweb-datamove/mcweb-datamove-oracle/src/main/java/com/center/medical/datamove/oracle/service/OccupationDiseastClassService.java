package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.OccupationDiseastClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC职业病种分类(OccupationDiseastClass)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:18
 */
public interface OccupationDiseastClassService extends IService<OccupationDiseastClass> {

    /**
     * 分页查询[JC职业病种分类]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OccupationDiseastClass> getPage(PageParam<OccupationDiseastClass> page, OccupationDiseastClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OccupationDiseastClass getInfoById(String id);

}

