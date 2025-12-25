package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdUtsideHand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS外送手动结果表(MdUtsideHand)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:27
 */
public interface MdUtsideHandMapper extends BaseMapper<MdUtsideHand> {

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param MdUtsideHand查询参数
     * @return 分页数据
     */
    IPage<MdUtsideHand> getPage(PageParam<MdUtsideHand> page, @Param("param") MdUtsideHand param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdUtsideHand getInfoById(@Param("id") String id);

}
