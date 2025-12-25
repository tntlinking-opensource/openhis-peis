package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.OutsideHand;
import org.apache.ibatis.annotations.Param;

/**
 * KS外送手动结果表(OutsideHand)表数据库访问层
 *
 * @author ay
 * @since 2022-12-07 15:11:14
 */
public interface OutsideHandMapper extends BaseMapper<OutsideHand> {

    /**
     * 分页查询[KS外送手动结果表]列表
     *
     * @param page  分页参数
     * @param param OutsideHand查询参数
     * @return 分页数据
     */
    IPage<OutsideHand> getList(PageParam<OutsideHand> page, @Param("param") OutsideHand param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OutsideHand getInfoById(@Param("id") String id);

}
