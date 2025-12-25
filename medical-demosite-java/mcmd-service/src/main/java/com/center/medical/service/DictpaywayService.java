package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Dictpayway;
import com.center.medical.common.utils.page.PageParam;

/**
 * JC支付方式表(Dictpayway)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:16
 */
public interface DictpaywayService extends IService<Dictpayway> {

    /**
     * 分页查询[JC支付方式表]列表
     *
     * @param page  分页参数
     * @param param Dictpayway查询参数
     * @return 分页数据
     */
    IPage<Dictpayway> getList(PageParam<Dictpayway> page, Dictpayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Dictpayway getInfoById(String id);

    /**
     * 新增或修改数据
     *
     * @param dictpayway
     * @return
     */
    String saveOrUpdateDictpayway(Dictpayway dictpayway);

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    String removeDictpayway(String ids);

    /**
     * 客户升级
     *
     * @return
     */
    String upgrade();
}

