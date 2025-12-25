package com.center.medical.datamove.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdPeispatientChargeMain;

/**
 * 体检者费用主表(MdPeispatientChargeMain)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:53:34
 */
public interface MdPeispatientChargeMainService extends IService<MdPeispatientChargeMain> {

    /**
     * 分页查询[体检者费用主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientChargeMain> getPage(PageParam<MdPeispatientChargeMain> page, MdPeispatientChargeMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientChargeMain getInfoById(String id);

    /**
     * 保存或修改
     *
     * @param map
     */
    void saOrUp(MdPeispatientChargeMain map);
}

