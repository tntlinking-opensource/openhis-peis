package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Receiveandsell;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 客户公共池领取与领取人员关联表(Receiveandsell)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:24:14
 */
public interface ReceiveandsellService extends IService<Receiveandsell> {

    /**
     * 分页查询[客户公共池领取与领取人员关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Receiveandsell> getPage(PageParam<Receiveandsell> page, Receiveandsell param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Receiveandsell getInfoById(String id);

}

