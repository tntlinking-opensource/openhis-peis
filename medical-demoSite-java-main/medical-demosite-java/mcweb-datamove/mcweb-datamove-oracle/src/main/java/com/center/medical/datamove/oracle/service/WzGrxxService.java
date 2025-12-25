package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.WzGrxx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * KS问诊——个人基本信息(WzGrxx)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:30:49
 */
public interface WzGrxxService extends IService<WzGrxx> {

    /**
     * 分页查询[KS问诊——个人基本信息]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<WzGrxx> getPage(PageParam<WzGrxx> page, WzGrxx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    WzGrxx getInfoById(String id);

}

