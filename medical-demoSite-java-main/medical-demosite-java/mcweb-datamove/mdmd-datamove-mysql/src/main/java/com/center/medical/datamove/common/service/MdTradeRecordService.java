package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdTradeRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 交易记录(MdTradeRecord)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:49:13
 */
public interface MdTradeRecordService extends IService<MdTradeRecord> {

    /**
     * 分页查询[交易记录]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdTradeRecord> getPage(PageParam<MdTradeRecord> page, MdTradeRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTradeRecord getInfoById(String id);

}

