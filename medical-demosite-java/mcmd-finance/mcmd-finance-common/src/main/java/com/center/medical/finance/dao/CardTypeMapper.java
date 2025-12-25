package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.CardType;
import org.apache.ibatis.annotations.Param;

/**
 * 卡类型(CardType)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:16
 */
public interface CardTypeMapper extends BaseMapper<CardType> {

    /**
     * 分页查询[卡类型]列表
     *
     * @param page  分页参数
     * @param param CardType查询参数
     * @return 分页数据
     */
    IPage<CardType> getList(PageParam<CardType> page, @Param("param") CardType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CardType getInfoById(@Param("id") String id);

}
