package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Memberintegral;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 会员卡积分明细表(Memberintegral)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:06
 */
public interface MemberintegralMapper extends BaseMapper<Memberintegral> {

    /**
     * 分页查询[会员卡积分明细表]列表
     *
     * @param page  分页参数
     * @param param Memberintegral查询参数
     * @return 分页数据
     */
    IPage<Memberintegral> getPage(PageParam<Memberintegral> page, @Param("param") Memberintegral param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Memberintegral getInfoById(@Param("id") String id);

}
