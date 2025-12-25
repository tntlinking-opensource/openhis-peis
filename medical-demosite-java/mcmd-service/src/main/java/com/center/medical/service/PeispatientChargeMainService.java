package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 体检者费用主表(PeispatientChargeMain)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:24
 */
public interface PeispatientChargeMainService extends IService<PeispatientChargeMain> {

    /**
     * 分页查询[体检者费用主表]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeMain查询参数
     * @return 分页数据
     */
    IPage<PeispatientChargeMain> getList(PageParam<PeispatientChargeMain> page, PeispatientChargeMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeispatientChargeMain getInfoById(String id);

}

