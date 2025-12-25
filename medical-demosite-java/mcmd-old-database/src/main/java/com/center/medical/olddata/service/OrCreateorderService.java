package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.param.OnlineImportParam;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrCreateorder;
import com.center.medical.olddata.bean.param.ImportTjPeopleParam;

import java.util.List;

/**
 * 订单表(Createorder)服务接口
 *
 * @author ay
 * @since 2023-07-25 18:20:58
 */
@DataSource(value = DataSourceType.SLAVE)
public interface OrCreateorderService extends IService<OrCreateorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrCreateorder> getPage(PageParam<OrCreateorder> page, OrCreateorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrCreateorder getInfoById(String id);

    /**
     * 根据订单号获取记录详情
     *
     * @param ddh 订单号
     * @return 详情信息
     */
    OrCreateorder getInfoByDdh(String ddh);

    /**
     * 查询未完成的订单
     * @return
     */
    List<OrCreateorder> getIncomplete(ImportTjPeopleParam param);

    /**
     * 获取线上导入数据
     * @param param
     * @return
     */
    List<OrCreateorder> getOnlineImport(OnlineImportParam param);
}

