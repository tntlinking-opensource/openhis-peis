package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdCreateorder;

import java.util.List;

/**
 * 订单表(MdCreateorder)服务接口
 *
 * @author ay
 * @since 2023-07-25 18:18:42
 */
@DataSource(value = DataSourceType.MASTER)
public interface MdCreateorderService extends IService<MdCreateorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCreateorder> getPage(PageParam<MdCreateorder> page, MdCreateorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCreateorder getInfoById(String id);

    /**
     * 添加或修改
     *
     * @param map
     */
    void saOrUp(MdCreateorder map);

    /**
     * 批量保存
     * @param mdCreateorderList
     */
    void saOrUpList(List<MdCreateorder> mdCreateorderList);

    /**
     * 通过订单号查询
     * @param ddh
     * @return
     */
    MdCreateorder getByDdh(String ddh);
}

