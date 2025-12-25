package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeisOl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者线上信息(PeisOl)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:34
 */
public interface PeisOlService extends IService<PeisOl> {

    /**
     * 分页查询[体检者线上信息]列表
     *
     * @param page  分页参数
     * @param param PeisOl查询参数
     * @return 分页数据
     */
    IPage<PeisOl> getList(PageParam<PeisOl> page, PeisOl param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeisOl getInfoById(String id);

    /**
     * 通知成功改变状态
     * @param id
     */
    void tagNoticeWechatCode(String id);
}

