package com.center.medical.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.FamilyList;
import com.center.medical.app.common.util.PageParam;

/**
 * 家人列表(FamilyList)服务接口
 *
 * @author ay
 * @since 2024-03-13 14:31:01
 */
public interface FamilyListService extends IService<FamilyList> {

    /**
     * 分页查询[家人列表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FamilyList> getPage(PageParam<FamilyList> page, FamilyList param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    FamilyList getInfoById(String id);

}

