package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Card;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * CW卡(Card)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:17:29
 */
public interface CardMapper extends BaseMapper<Card> {

    /**
     * 分页查询[CW卡]列表
     *
     * @param page  分页参数
     * @param param Card查询参数
     * @return 分页数据
     */
    IPage<Card> getPage(PageParam<Card> page, @Param("param") Card param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Card getInfoById(@Param("id") String id);

}
