package com.center.medical.datamove.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.datamove.common.bean.model.MdMemberintegral;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

/**
 * 会员卡积分明细表(MdMemberintegral)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
public interface MdMemberintegralService extends IService<MdMemberintegral> {

    /**
     * 分页查询[会员卡积分明细表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdMemberintegral> getPage(PageParam<MdMemberintegral> page, MdMemberintegral param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdMemberintegral getInfoById(String id);

}

