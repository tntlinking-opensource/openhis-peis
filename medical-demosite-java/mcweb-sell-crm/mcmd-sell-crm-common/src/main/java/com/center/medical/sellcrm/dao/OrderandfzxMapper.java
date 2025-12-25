package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Orderandfzx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单与分中心关联表(Orderandfzx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:08
 */
public interface OrderandfzxMapper extends BaseMapper<Orderandfzx> {

    /**
     * 分页查询[订单与分中心关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandfzx查询参数
     * @return 分页数据
     */
    IPage<Orderandfzx> getPage(PageParam<Orderandfzx> page, @Param("param") Orderandfzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Orderandfzx getInfoById(@Param("id") String id);

    /**
     * 通过订单id查询关联的分中心名称
     * @param ddid
     * @return
     */
    List<String> getBranchNameByDdid(@Param("ddid") String ddid);
}
