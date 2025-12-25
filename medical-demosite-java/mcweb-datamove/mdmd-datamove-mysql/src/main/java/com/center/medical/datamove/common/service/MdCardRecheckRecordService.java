package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdCardRecheckRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 十周年卡复查金额记录表(MdCardRecheckRecord)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:45:15
 */
public interface MdCardRecheckRecordService extends IService<MdCardRecheckRecord> {

    /**
     * 分页查询[十周年卡复查金额记录表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdCardRecheckRecord> getPage(PageParam<MdCardRecheckRecord> page, MdCardRecheckRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCardRecheckRecord getInfoById(String id);

}

