package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Peisorgreservationreceipt;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * QT发票表(Peisorgreservationreceipt)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:07
 */
public interface PeisorgreservationreceiptMapper extends BaseMapper<Peisorgreservationreceipt> {

    /**
     * 分页查询[QT发票表]列表
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
