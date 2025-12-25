package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.Createorder;

/**
 * 订单表(Createorder)服务接口
 *
 * @author ay
 * @since 2023-07-25 18:20:58
 */
@DataSource(value = DataSourceType.SLAVE)
public interface OrCreateorderService extends IService<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Createorder> getPage(PageParam<Createorder> page, Createorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(String id);

    /**
     * 根据订单号获取记录详情
     *
     * @param ddh 订单号
     * @return 详情信息
     */
    Createorder getInfoByDdh(String ddh);

}

