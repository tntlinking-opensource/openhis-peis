package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.WzGrxx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——个人基本信息(WzGrxx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:25
 */
public interface WzGrxxService extends IService<WzGrxx> {

    /**
     * 分页查询[KS问诊——个人基本信息]列表
     *
     * @param page  分页参数
     * @param param WzGrxx查询参数
     * @return 分页数据
     */
    IPage<WzGrxx> getList(PageParam<WzGrxx> page, WzGrxx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzGrxx getInfoById(String id);

}

