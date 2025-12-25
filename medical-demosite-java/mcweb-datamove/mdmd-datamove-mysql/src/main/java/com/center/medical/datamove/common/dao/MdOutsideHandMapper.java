package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdOutsideHand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS外送手动结果表(MdOutsideHand)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:31
 */
public interface MdOutsideHandMapper extends BaseMapper<MdOutsideHand> {

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param MdOutsideHand查询参数
     * @return 分页数据
     */
    IPage<MdOutsideHand> getPage(PageParam<MdOutsideHand> page, @Param("param") MdOutsideHand param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdOutsideHand getInfoById(@Param("id") String id);

}
