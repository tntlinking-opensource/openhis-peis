package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Peisorgreservationreceipt;
import org.apache.ibatis.annotations.Param;

/**
 * 发票(Peisorgreservationreceipt)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-02 11:41:03
 */
public interface PeisorgreservationreceiptMapper extends BaseMapper<Peisorgreservationreceipt> {

    /**
     * 分页查询[发票]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservationreceipt查询参数
     * @return 分页数据
     */
    IPage<Peisorgreservationreceipt> getPage(PageParam<Peisorgreservationreceipt> page, @Param("param") Peisorgreservationreceipt param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservationreceipt getInfoById(@Param("id") String id);

}
