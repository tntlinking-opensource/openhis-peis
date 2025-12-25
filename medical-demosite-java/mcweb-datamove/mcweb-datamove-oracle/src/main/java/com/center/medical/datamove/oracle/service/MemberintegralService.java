package com.center.medical.datamove.oracle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.oracle.bean.model.Memberintegral;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 会员卡积分明细表(Memberintegral)服务接口
 *
 * @author ay
 * @since 2023-07-18 09:22:06
 */
public interface MemberintegralService extends IService<Memberintegral> {

    /**
     * 分页查询[会员卡积分明细表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Memberintegral> getPage(PageParam<Memberintegral> page, Memberintegral param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Memberintegral getInfoById(String id);

}

