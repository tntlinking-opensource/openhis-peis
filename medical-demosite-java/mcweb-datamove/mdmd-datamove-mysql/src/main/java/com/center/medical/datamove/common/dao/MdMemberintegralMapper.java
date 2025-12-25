package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdMemberintegral;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 会员卡积分明细表(MdMemberintegral)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
public interface MdMemberintegralMapper extends BaseMapper<MdMemberintegral> {

    /**
     * 分页查询[会员卡积分明细表]列表
     *
     * @param page  分页参数
     * @param param MdMemberintegral查询参数
     * @return 分页数据
     */
    IPage<MdMemberintegral> getPage(PageParam<MdMemberintegral> page, @Param("param") MdMemberintegral param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdMemberintegral getInfoById(@Param("id") String id);

}
