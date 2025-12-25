package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Memberintegral;
import com.center.medical.member.bean.param.MemberintegralParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员卡积分明细表(Memberintegral)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-24 09:59:57
 */
public interface MemberintegralMapper extends BaseMapper<Memberintegral> {

    /**
     * 分页查询[会员卡积分明细表]列表
     *
     * @param page  分页参数
     * @param param Memberintegral查询参数
     * @return 分页数据
     */
    IPage<Memberintegral> getPage(PageParam<Memberintegral> page, @Param("param") MemberintegralParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Memberintegral getInfoById(@Param("id") String id);

    /**
     * 根据条件筛选用户积分数据
     *
     * @param param
     * @return
     */
    List<Memberintegral> getList(@Param("param") MemberintegralParam param);
}
