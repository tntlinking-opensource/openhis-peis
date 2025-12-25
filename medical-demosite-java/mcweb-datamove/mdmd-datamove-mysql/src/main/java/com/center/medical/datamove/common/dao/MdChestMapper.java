package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdChest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 订单柜子信息(MdChest)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:16
 */
public interface MdChestMapper extends BaseMapper<MdChest> {

    /**
     * 分页查询[订单柜子信息]列表
     *
     * @param page  分页参数
     * @param param MdChest查询参数
     * @return 分页数据
     */
    IPage<MdChest> getPage(PageParam<MdChest> page, @Param("param") MdChest param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdChest getInfoById(@Param("id") String id);

}
