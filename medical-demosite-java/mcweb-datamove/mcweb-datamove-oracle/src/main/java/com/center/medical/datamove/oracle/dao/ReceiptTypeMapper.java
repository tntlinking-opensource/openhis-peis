package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.ReceiptType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 发票类型(ReceiptType)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:24:12
 */
public interface ReceiptTypeMapper extends BaseMapper<ReceiptType> {

    /**
     * 分页查询[发票类型]列表
     *
     * @param page  分页参数
     * @param param ReceiptType查询参数
     * @return 分页数据
     */
    IPage<ReceiptType> getPage(PageParam<ReceiptType> page, @Param("param") ReceiptType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ReceiptType getInfoById(@Param("id") String id);

}
