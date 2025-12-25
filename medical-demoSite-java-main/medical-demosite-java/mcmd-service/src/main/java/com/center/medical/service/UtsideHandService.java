package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.UtsideHand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS外送手动结果表(UtsideHand)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:33
 */
public interface UtsideHandService extends IService<UtsideHand> {

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param UtsideHand查询参数
     * @return 分页数据
     */
    IPage<UtsideHand> getList(PageParam<UtsideHand> page, UtsideHand param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    UtsideHand getInfoById(String id);

}

