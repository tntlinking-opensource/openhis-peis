package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Memberintegral;
import com.center.medical.member.bean.param.MemberintegralParam;

import java.util.List;

/**
 * 会员卡积分明细表(Memberintegral)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-24 09:59:57
 */
public interface MemberintegralService extends IService<Memberintegral> {

    /**
     * 分页查询[会员卡积分明细表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Memberintegral> getPage(PageParam<Memberintegral> page, MemberintegralParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Memberintegral getInfoById(String id);

    /**
     * 根据条件筛选用户积分数据
     *
     * @param param
     * @return
     */
    List<Memberintegral> getList(MemberintegralParam param);
}

