package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.CardMemberMedical;

/**
 * 会员卡体检卡关联表(CardMemberMedical)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:36
 */
public interface CardMemberMedicalService extends IService<CardMemberMedical> {

    /**
     * 分页查询[会员卡体检卡关联表]列表
     *
     * @param page  分页参数
     * @param param CardMemberMedical查询参数
     * @return 分页数据
     */
    IPage<CardMemberMedical> getList(PageParam<CardMemberMedical> page, CardMemberMedical param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CardMemberMedical getInfoById(String id);

}

