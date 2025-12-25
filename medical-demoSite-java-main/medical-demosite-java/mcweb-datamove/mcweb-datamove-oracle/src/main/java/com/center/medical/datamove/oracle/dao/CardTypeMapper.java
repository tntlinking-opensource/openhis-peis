package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.CardType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * CW卡类型(CardType)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:33
 */
public interface CardTypeMapper extends BaseMapper<CardType> {

    /**
     * 分页查询[CW卡类型]列表
     *
     * @param page  分页参数
     * @param param CardType查询参数
     * @return 分页数据
     */
    IPage<CardType> getPage(PageParam<CardType> page, @Param("param") CardType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CardType getInfoById(@Param("id") String id);

}
