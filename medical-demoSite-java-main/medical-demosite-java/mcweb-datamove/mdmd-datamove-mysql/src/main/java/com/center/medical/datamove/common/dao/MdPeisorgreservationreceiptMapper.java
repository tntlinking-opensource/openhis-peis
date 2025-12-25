package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeisorgreservationreceipt;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 发票(MdPeisorgreservationreceipt)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:10
 */
public interface MdPeisorgreservationreceiptMapper extends BaseMapper<MdPeisorgreservationreceipt> {

    /**
     * 分页查询[发票]列表
     *
     * @param page  分页参数
     * @param param MdPeisorgreservationreceipt查询参数
     * @return 分页数据
     */
    IPage<MdPeisorgreservationreceipt> getPage(PageParam<MdPeisorgreservationreceipt> page, @Param("param") MdPeisorgreservationreceipt param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeisorgreservationreceipt getInfoById(@Param("id") String id);

}
