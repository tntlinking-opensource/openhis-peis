package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Receiveandsell;

/**
 * 客户公共池领取与领取人员关联表(Receiveandsell)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:22
 */
public interface ReceiveandsellService extends IService<Receiveandsell> {

    /**
     * 分页查询[客户公共池领取与领取人员关联表]列表
     *
     * @param page  分页参数
     * @param param Receiveandsell查询参数
     * @return 分页数据
     */
    IPage<Receiveandsell> getList(PageParam<Receiveandsell> page, Receiveandsell param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Receiveandsell getInfoById(String id);

}

