package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdGroupBalance;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检团体结算表(MdGroupBalance)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:20
 */
public interface MdGroupBalanceMapper extends BaseMapper<MdGroupBalance> {

    /**
     * 分页查询[体检团体结算表]列表
     *
     * @param page  分页参数
     * @param param MdGroupBalance查询参数
     * @return 分页数据
     */
    IPage<MdGroupBalance> getPage(PageParam<MdGroupBalance> page, @Param("param") MdGroupBalance param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdGroupBalance getInfoById(@Param("id") String id);

}
