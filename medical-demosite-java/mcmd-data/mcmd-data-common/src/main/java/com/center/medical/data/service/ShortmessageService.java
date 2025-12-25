package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Shortmessage;

/**
 * JC短信信息表(Shortmessage)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
public interface ShortmessageService extends IService<Shortmessage> {

    /**
     * 分页查询[JC短信信息表]列表
     *
     * @param page  分页参数
     * @param param Shortmessage查询参数
     * @return 分页数据
     */
    IPage<Shortmessage> getList(PageParam<Shortmessage> page, Shortmessage param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Shortmessage getInfoById(String id);

    /**
     * 保存或修改
     * @param shortmessage
     * @return
     */
    Boolean saveOrUpdateShortmessage(Shortmessage shortmessage);

    /**
     * 逻辑删除
     * @param ids
     * @return
     */
    String removeShortmessage(String ids);
}

