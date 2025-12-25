package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdPeispatientChargeOther;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者其他缴费(MdPeispatientChargeOther)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:48:11
 */
public interface MdPeispatientChargeOtherService extends IService<MdPeispatientChargeOther> {

    /**
     * 分页查询[体检者其他缴费]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientChargeOther> getPage(PageParam<MdPeispatientChargeOther> page, MdPeispatientChargeOther param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientChargeOther getInfoById(String id);

}

