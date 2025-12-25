package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdUserHarmClass;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 总检医生-危害因素分类关联表 (MdUserHarmClass)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:17
 */
public interface MdUserHarmClassService extends IService<MdUserHarmClass> {

    /**
     * 分页查询[总检医生-危害因素分类关联表 ]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdUserHarmClass> getPage(PageParam<MdUserHarmClass> page, MdUserHarmClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUserHarmClass getInfoById(String id);

}

